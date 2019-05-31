package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.logger.model.MemberSearchSum.MemberSearchSumType;
import com.d2c.logger.search.model.SearcherMemberSum;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.product.search.model.SearcherProductCategory;
import com.d2c.product.search.query.CategorySearchBean;
import com.d2c.product.search.support.ProductSearchHelp;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class ProductCategorySearcherServiceImpl implements ProductCategorySearcherService {

    private static final Log logger = LogFactory.getLog(ProductCategorySearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;
    @Reference
    private SearchKeySearcherService searchKeySearcherService;

    @Override
    public int insert(SearcherProductCategory category) {
        try {
            if (category == null)
                return 0;
            // 如果是非上架状态不建立索引
            if (category.getStatus() <= 0)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, category);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCTCATEGORY, category.getId().toString())
                    .setSource(builder).execute().actionGet();
            SearcherMemberSum searchDto = new SearcherMemberSum();
            searchDto.setSystem(true);
            searchDto.setKeyword(category.getName());
            searchDto.setDisplayName(category.getName());
            searchDto.setType(MemberSearchSumType.CATEGORY.toString());
            searchDto.setSourceId(String.valueOf(category.getId()));
            searchKeySearcherService.insert(searchDto, 1);
            logger.info("ID为" + category.getId() + "的分类建立索引成功!ProductCategory：" + category.getName());
            return 1;
        } catch (ElasticsearchException e) {
            logger.error(e);
            logger.info("Elasticsearch Exception: " + e.toString());
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public SearcherProductCategory findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCTCATEGORY, id).execute().actionGet();
        SearcherProductCategory pc = null;
        if (response != null && response.isExists()) {
            Long categoryId = Long.valueOf(response.getId());
            pc = new SearcherProductCategory();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, pc);
                pc.setId(categoryId);
                return pc;
            }
        }
        return pc;
    }

    @Override
    public List<ProductSearchHelp> findHelpByIds(String[] ids) {
        List<ProductSearchHelp> productCategoryList = new ArrayList<>();
        try {
            SearchRequestBuilder builder = elasticSearchConfig.getClient()
                    .prepareSearch(ElasticSearchConfig.INDEX_NAME);
            QueryBuilder qb = QueryBuilders.idsQuery(TYPE_PRODUCTCATEGORY).addIds(ids);
            builder.setQuery(qb).setSize(ids.length);
            SearchResponse response = builder.execute().actionGet();
            for (SearchHit hit : response.getHits()) {
                Map<String, Object> source = hit.getSource();
                if (!source.isEmpty()) {
                    ProductSearchHelp productCategory = new ProductSearchHelp();
                    Long productCategoryId = Long.valueOf(hit.getId());
                    productCategory.setSourceId(productCategoryId);
                    productCategory.setName(String.valueOf(source.get("name")));
                    productCategory.setType(TYPE_PRODUCTCATEGORY);
                    productCategory.setParentId(String.valueOf(source.get("topId")));
                    productCategoryList.add(productCategory);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productCategoryList;
    }

    @Override
    public PageResult<SearcherProductCategory> search(CategorySearchBean searcher, PageModel page) {
        QueryBuilder qb = null;
        if (!StringUtils.isEmpty(searcher.getKeyword())) {
            qb = QueryBuilders.multiMatchQuery(searcher.getKeyword(), "name^2", "code^1").analyzer("ik").lenient(true);
        }
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_PRODUCTCATEGORY).setSearchType(SearchType.DEFAULT).setQuery(qb);
        if (qb != null) {
            builder.setQuery(qb);
        } else {
            builder.setQuery(QueryBuilders.matchAllQuery());
        }
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (searcher.getId() != null) {
            boolFilter.must(FilterBuilders.termsFilter("id", searcher.getId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getTopId() != null) {
            boolFilter.must(FilterBuilders.termsFilter("topId", new long[]{searcher.getTopId()}));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getParentId() != null) {
            boolFilter.must(FilterBuilders.termsFilter("parentId", new long[]{searcher.getParentId()}));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getPath() != null) {
            boolFilter.must(FilterBuilders.termFilter("_path", searcher.getPath()));
            builder.setPostFilter(boolFilter);
        }
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherProductCategory> tops = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long topId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherProductCategory pc = new SearcherProductCategory();
                ElasticSearchConfig.transMap2Bean(source, pc);
                pc.setId(topId);
                tops.add(pc);
            }
        }
        PageResult<SearcherProductCategory> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public int update(SearcherProductCategory category) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", category.getId());
            if (category.getName() != null) {
                builder.field("name", category.getName());
            }
            if (category.getCode() != null) {
                builder.field("code", category.getCode());
            }
            if (category.getParentId() != null) {
                builder.field("parentId", category.getParentId());
            }
            if (category.getPaths() != null) {
                builder.field("paths", category.getPaths());
            }
            if (category.getDepth() != 0) {
                builder.field("depth", category.getDepth());
            }
            if (category.getTopId() != null) {
                builder.field("topId", category.getTopId());
            }
            if (category.getStatus() != null) {
                builder.field("status", category.getStatus());
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCTCATEGORY, category.getId().toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + category.getId() + "的分类更新索引成功!ProductCategory：" + category.getName());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherProductCategory category) {
        remove(category.getId());
        if (category.getStatus() > 0) {
            return insert(category);
        }
        return 0;
    }

    @Override
    public int remove(Long pcId) {
        String id = pcId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCTCATEGORY, id)
                .execute().actionGet();
        searchKeySearcherService.removeByTypeAndSourceId(MemberSearchSumType.CATEGORY.toString(), id);
        logger.info("ID为" + id + "的分类已删除索引!");
        return 1;
    }

    @Override
    public int removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_PRODUCTCATEGORY).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        return 1;
    }

    @Override
    public JSONArray findByTopId(Long topId, Long parentId) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_PRODUCTCATEGORY).setSearchType(SearchType.DEFAULT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("topId", topId));
        builder.setPostFilter(boolFilter);
        boolFilter.must(FilterBuilders.termFilter("status", 1));
        builder.setPostFilter(boolFilter);
        if (parentId == null) {
            boolFilter.must(FilterBuilders.missingFilter("parentId"));
            builder.setPostFilter(boolFilter);
        } else {
            boolFilter.must(FilterBuilders.termFilter("parentId", parentId));
            builder.setPostFilter(boolFilter);
        }
        SearchResponse response = builder.execute().actionGet();
        JSONArray array = new JSONArray();
        for (SearchHit hit : response.getHits()) {
            Long categoryId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherProductCategory pc = new SearcherProductCategory();
                ElasticSearchConfig.transMap2Bean(source, pc);
                pc.setId(categoryId);
                JSONObject obj = pc.toJson();
                JSONArray children = new JSONArray();
                if (pc.getLeaf() == 0) {
                    children = this.findByTopId(topId, pc.getId());
                }
                obj.put("children", children);
                array.add(obj);
            }
        }
        return array;
    }

}
