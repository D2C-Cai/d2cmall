package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.logger.model.MemberSearchSum.MemberSearchSumType;
import com.d2c.logger.search.model.SearcherMemberSum;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.product.search.model.SearcherTopCategory;
import com.d2c.product.search.query.CategorySearchBean;
import com.d2c.product.search.support.ProductSearchHelp;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.ElasticsearchException;
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
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class TopCategorySearcherServiceImpl implements TopCategorySearcherService {

    private static final Logger logger = LoggerFactory.getLogger(TopCategorySearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;
    @Reference
    private SearchKeySearcherService searchKeySearcherService;

    @Override
    public int insert(SearcherTopCategory top) {
        try {
            if (top == null)
                return 0;
            // 如果是非上架状态不建立索引
            if (top.getStatus() <= 0)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, top);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_TOPCATEGORY, top.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为={},的顶级分类建立索引成功!topCategory：{}", top.getId(), top.getName());
            SearcherMemberSum searchDto = new SearcherMemberSum();
            searchDto.setSystem(true);
            searchDto.setKeyword(top.getName());
            searchDto.setDisplayName(top.getName());
            searchDto.setType(MemberSearchSumType.TOPCATEGORY.toString());
            searchDto.setSourceId(String.valueOf(top.getId()));
            searchKeySearcherService.insert(searchDto, 1);
            return 1;
        } catch (ElasticsearchException e) {
            logger.error("ElasticsearchException", e.toString());
            logger.info("Elasticsearch Exception: " + e.toString());
        } catch (IOException e) {
            logger.error("ElasticsearchException", e.toString());
        }
        return 0;
    }

    @Override
    public SearcherTopCategory findById(String id) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_TOPCATEGORY).addIds(id)).execute().actionGet();
        SearcherTopCategory pc = null;
        for (SearchHit hit : response.getHits()) {
            Long topId = Long.valueOf(hit.getId());
            pc = new SearcherTopCategory();
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, pc);
                pc.setId(topId);
                return pc;
            }
        }
        return pc;
    }

    @Override
    public List<ProductSearchHelp> findHelpByIds(String[] ids) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME);
        QueryBuilder qb = QueryBuilders.idsQuery(TYPE_TOPCATEGORY).addIds(ids);
        builder.setQuery(qb).setSize(ids.length);
        builder.addSort("sequence", SortOrder.DESC);
        SearchResponse response = builder.execute().actionGet();
        List<ProductSearchHelp> topCategoryList = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                ProductSearchHelp topCategory = new ProductSearchHelp();
                Long topCategoryId = Long.valueOf(hit.getId());
                topCategory.setSourceId(topCategoryId);
                topCategory.setName(String.valueOf(source.get("name")));
                topCategory.setType(TYPE_TOPCATEGORY);
                topCategoryList.add(topCategory);
            }
        }
        return topCategoryList;
    }

    @Override
    public PageResult<SearcherTopCategory> search(CategorySearchBean searcher, PageModel page) {
        QueryBuilder qb = null;
        if (!StringUtils.isEmpty(searcher.getKeyword())) {
            qb = QueryBuilders.multiMatchQuery(searcher.getKeyword(), "name^3", "code^2").analyzer("ik").lenient(true);
        } else {
            qb = QueryBuilders.matchAllQuery();
        }
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_TOPCATEGORY).setSearchType(SearchType.DEFAULT).setQuery(qb);
        builder.addSort("sequence", SortOrder.DESC);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (searcher.getId() != null) {
            boolFilter.must(FilterBuilders.termsFilter("id", searcher.getId()));
            builder.setPostFilter(boolFilter);
        }
        boolFilter.must(FilterBuilders.termFilter("status", 1));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherTopCategory> tops = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long topId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherTopCategory top = new SearcherTopCategory();
                ElasticSearchConfig.transMap2Bean(source, top);
                top.setId(topId);
                tops.add(top);
            }
        }
        PageResult<SearcherTopCategory> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public int update(SearcherTopCategory top) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", top.getId());
            if (top.getName() != null) {
                builder.field("name", top.getName());
            }
            if (top.getCode() != null) {
                builder.field("code", top.getCode());
            }
            if (top.getStatus() != null) {
                builder.field("status", top.getStatus());
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_TOPCATEGORY, top.getId().toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + top.getId() + "的顶级分类更新索引成功!topCategory：" + top.getName());
            return 1;
        } catch (Exception e) {
            logger.error("Exception={}", e.toString());
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherTopCategory top) {
        remove(top.getId());
        if (top.getStatus() > 0) {
            return insert(top);
        }
        return 0;
    }

    @Override
    public int remove(Long topId) {
        String id = topId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_TOPCATEGORY, id).execute()
                .actionGet();
        searchKeySearcherService.removeByTypeAndSourceId(MemberSearchSumType.TOPCATEGORY.toString(), id);
        logger.info("ID为" + id + "的顶级分类已删除索引!");
        return 1;
    }

    @Override
    public int removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_TOPCATEGORY)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        return 1;
    }

}
