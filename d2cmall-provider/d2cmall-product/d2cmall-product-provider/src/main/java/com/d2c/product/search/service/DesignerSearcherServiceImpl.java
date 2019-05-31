package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.logger.model.MemberSearchSum.MemberSearchSumType;
import com.d2c.logger.search.model.SearcherMemberSum;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.model.SearcherDesignerTagRelation;
import com.d2c.product.search.query.DesignerSearchBean;
import com.d2c.product.search.support.ProductSearchHelp;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Service(protocol = "dubbo")
public class DesignerSearcherServiceImpl implements DesignerSearcherService {

    private static final Log logger = LogFactory.getLog(DesignerSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;
    @Reference
    private SearchKeySearcherService searchKeySearcherService;
    @Reference
    private DesignerTagRelationSearcherService designerTagRelationSearcherService;

    @Override
    public int insert(SearcherDesigner designer) {
        try {
            if (designer == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, designer);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER, designer.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + designer.getId() + "的设计师建立索引成功!brand：" + designer.getName());
            SearcherMemberSum searchDto = new SearcherMemberSum();
            searchDto.setKeyword(designer.getName().toLowerCase());
            searchDto.setSystem(true);
            searchDto.setType(MemberSearchSumType.DESIGNER.toString());
            searchDto.setDisplayName(searchDto.getKeyword().toLowerCase());
            searchDto.setSourceId(String.valueOf(designer.getId()));
            searchKeySearcherService.insert(searchDto, 1);
            if (designer.getDesigners() != null) {
                searchDto.setDisplayName(designer.getDesigners().toLowerCase());
                searchKeySearcherService.insert(searchDto, 2);
            }
            return 1;
        } catch (ElasticsearchException e) {
            logger.error("Elasticsearch Exception: " + e.toString());
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public SearcherDesigner findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER, id).execute().actionGet();
        SearcherDesigner designer = null;
        if (response != null && response.isExists()) {
            Long desingerId = Long.valueOf(response.getId());
            designer = new SearcherDesigner();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, designer);
                designer.setId(desingerId);
            }
        }
        return designer;
    }

    @Override
    public Map<Long, SearcherDesigner> findMapByIds(String[] ids, DesignerSearchBean search) {
        BoolQueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.idsQuery(TYPE_DESIGNER).addIds(ids));
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (search != null) {
            if (search.getMark() != null) {
                boolFilter.must(FilterBuilders.termFilter("mark", search.getMark()));
                builder.setPostFilter(boolFilter);
            }
            if (search.getCountry() != null) {
                boolFilter.must(FilterBuilders.termFilter("country", search.getCountry()));
                builder.setPostFilter(boolFilter);
            }
            if (search.getDesignArea() != null) {
                boolFilter.must(FilterBuilders.termFilter("designArea", search.getDesignArea()));
                builder.setPostFilter(boolFilter);
            }
            if (search.getStyle() != null) {
                boolFilter.must(FilterBuilders.termFilter("style", search.getStyle()));
                builder.setPostFilter(boolFilter);
            }
            if (search.getPageGroup() != null) {
                boolFilter.must(FilterBuilders.termFilter("pageGroup", search.getPageGroup().toLowerCase()));
                builder.setPostFilter(boolFilter);
            }
            for (int i = 0; search.getSortFields() != null && i < search.getSortFields().length; i++) {
                builder.addSort(search.getSortFields()[i], search.getOrders()[i]);
            }
        }
        SearchResponse response = builder.setSize(ids.length).execute().actionGet();
        Map<Long, SearcherDesigner> designers = new HashMap<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherDesigner designer = new SearcherDesigner();
                Long desingerId = Long.valueOf(hit.getId());
                ElasticSearchConfig.transMap2Bean(source, designer);
                designer.setId(desingerId);
                designers.put(desingerId, designer);
            }
        }
        return designers;
    }

    @Override
    public List<ProductSearchHelp> findHelpByIds(String[] ids) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_DESIGNER).addIds(ids)).setSize(10).execute().actionGet();
        List<ProductSearchHelp> designers = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                ProductSearchHelp designer = new ProductSearchHelp();
                Long desingerId = Long.valueOf(hit.getId());
                designer.setSourceId(desingerId);
                designer.setName(String.valueOf(source.get("name")));
                designer.setType(TYPE_DESIGNER);
                designers.add(designer);
            }
        }
        return designers;
    }

    @Override
    public PageResult<SearcherDesigner> search(DesignerSearchBean search, PageModel page) {
        QueryBuilder qb = null;
        if (search.getIds() != null && search.getIds().length > 0) {
            qb = QueryBuilders.idsQuery(TYPE_DESIGNER).addIds(search.getIds());
        }
        if (!StringUtils.isEmpty(search.getKeyword())) {
            qb = QueryBuilders.multiMatchQuery(search.getKeyword(), "name^3", "designers^2", "seo^1").analyzer("ik")
                    .minimumShouldMatch("70");
        }
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_DESIGNER).setSearchType(SearchType.DEFAULT).setQuery(qb)
                .setTimeout(TimeValue.timeValueMinutes(1));
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (search.getMark() != null) {
            boolFilter.must(FilterBuilders.termFilter("mark", search.getMark()));
            builder.setPostFilter(boolFilter);
        }
        if (search.getCountry() != null) {
            boolFilter.must(FilterBuilders.termFilter("country", search.getCountry()));
            builder.setPostFilter(boolFilter);
        }
        if (search.getDesignArea() != null) {
            boolFilter.must(FilterBuilders.termFilter("designArea", search.getDesignArea()));
            builder.setPostFilter(boolFilter);
        }
        if (search.getStyle() != null) {
            boolFilter.must(FilterBuilders.termFilter("style", search.getStyle()));
            builder.setPostFilter(boolFilter);
        }
        if (search.getPageGroup() != null) {
            boolFilter.must(FilterBuilders.termFilter("pageGroup", search.getPageGroup().toLowerCase()));
            builder.setPostFilter(boolFilter);
        }
        for (int i = 0; search.getSortFields() != null && i < search.getSortFields().length; i++) {
            builder.addSort(search.getSortFields()[i], search.getOrders()[i]);
        }
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherDesigner> designers = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long desingerId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherDesigner designer = new SearcherDesigner();
                ElasticSearchConfig.transMap2Bean(source, designer);
                designer.setId(desingerId);
                designers.add(designer);
            }
        }
        PageResult<SearcherDesigner> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(designers);
        return pager;
    }

    @Override
    public Map<String, List<JSONObject>> findGroupLetter() {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_DESIGNER);
        TermsBuilder teamAgg = AggregationBuilders.terms("pageGroup").field("pageGroup")
                .subAggregation(AggregationBuilders.topHits("top").setSize(10000)).size(27);
        builder.addAggregation(teamAgg);
        SearchResponse response = builder.execute().actionGet();
        Map<String, Aggregation> asMap = response.getAggregations().getAsMap();
        StringTerms tersAgg = (StringTerms) asMap.get("pageGroup");
        Iterator<Bucket> teamBucketIt = tersAgg.getBuckets().iterator();
        Bucket buck = null;
        Map<String, List<JSONObject>> map = new HashMap<>();
        while (teamBucketIt.hasNext()) {
            buck = teamBucketIt.next();
            TopHits topHits = buck.getAggregations().get("top");
            if (!buck.getKey().equals("0") && !buck.getKey().equals("9")) {
                List<JSONObject> designers = new ArrayList<>();
                for (SearchHit hit : topHits.getHits().hits()) {
                    Long desingerId = Long.valueOf(hit.getId());
                    Map<String, Object> source = hit.getSource();
                    if (!source.isEmpty()) {
                        SearcherDesigner designer = new SearcherDesigner();
                        ElasticSearchConfig.transMap2Bean(source, designer);
                        designer.setId(desingerId);
                        designers.add(designer.toSimpleJson());
                    }
                    map.put(buck.getKey(), designers);
                }
            }
        }
        return map;
    }

    @Override
    public int update(SearcherDesigner designer) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", designer.getId());
            if (designer.getName() != null) {
                builder.field("name", designer.getName());
            }
            if (designer.getDesigners() != null) {
                builder.field("designers", designer.getDesigners());
            }
            if (designer.getCountry() != null) {
                builder.field("country", designer.getCountry());
            }
            if (designer.getStyle() != null) {
                builder.field("style", designer.getStyle());
            }
            if (designer.getDesignArea() != null) {
                builder.field("designArea", designer.getDesignArea());
            }
            if (designer.getPageGroup() != null) {
                builder.field("pageGroup", designer.getPageGroup());
            }
            if (designer.getSeo() != null) {
                builder.field("seo", designer.getSeo());
            }
            if (designer.getIntroPic() != null) {
                builder.field("introPic", designer.getIntroPic());
            }
            if (designer.getBigPic() != null) {
                builder.field("bigPic", designer.getBigPic());
            }
            if (designer.getHeadPic() != null) {
                builder.field("headPic", designer.getHeadPic());
            }
            if (designer.getSignPic() != null) {
                builder.field("signPic", designer.getSignPic());
            }
            if (designer.getIntro() != null) {
                builder.field("intro", designer.getIntro());
            }
            if (designer.getSlogan() != null) {
                builder.field("slogan", designer.getSlogan());
            }
            if (designer.getSortDate() != null) {
                builder.field("sortDate", ElasticSearchConfig.transDate2Long(designer.getSortDate()));
            }
            if (designer.getDomain() != null) {
                builder.field("domain", designer.getDomain());
            }
            if (designer.getRecommend() != null) {
                builder.field("recommend", designer.getRecommend());
            }
            if (designer.getVideo() != null) {
                builder.field("video", designer.getVideo());
            }
            if (designer.getBrandCode() != null) {
                builder.field("brandCode", designer.getBrandCode());
            }
            if (designer.getBackgroundUrl() != null) {
                builder.field("backgroundUrl", designer.getBackgroundUrl());
            }
            if (designer.getVfans() != null) {
                builder.field("vfans", designer.getVfans());
            }
            if (designer.getTfans() != null) {
                builder.field("tfans", designer.getTfans());
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER, designer.getId().toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + designer.getId() + "的设计师更新索引成功!brand：" + designer.getName());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateFans(Long designerId, int fans) {
        try {
            SearcherDesigner designer = findById(designerId.toString());
            if (designer != null) {
                XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", designerId);
                builder.field("fans", designer.getFans() + fans);
                builder.field("tfans", designer.getTfans() + fans);
                builder.endObject();
                elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER, designerId.toString())
                        .setDoc(builder).execute().actionGet();
                logger.info("ID为" + designerId + "的设计师更新索引成功!fans：" + fans);
                return 1;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherDesigner designer) {
        String id = designer.getId().toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER, id).execute()
                .actionGet();
        // TODO price
        insert(designer);
        List<SearcherDesignerTagRelation> dtrs = new ArrayList<>();
        DesignerSearchBean searchBean = new DesignerSearchBean();
        searchBean.setDesignerId(designer.getId());
        PageModel page = new PageModel(1, 500);
        PageResult<SearcherDesignerTagRelation> result = designerTagRelationSearcherService.search(searchBean, page);
        dtrs.addAll(result.getList());
        for (int i = 0; i < dtrs.size(); i++) {
            SearcherDesignerTagRelation relation = dtrs.get(i);
            BeanUtils.copyProperties(designer, relation);
            designerTagRelationSearcherService.rebuild(relation);
        }
        return 1;
    }

    @Override
    public int remove(Long designerId) {
        String id = designerId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER, id).execute()
                .actionGet();
        designerTagRelationSearcherService.remove(designerId);
        searchKeySearcherService.removeByTypeAndSourceId(MemberSearchSumType.DESIGNER.toString(),
                String.valueOf(designerId));
        logger.info("ID为" + id + "的设计师已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_DESIGNER)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public int updateSales(Long id, Integer sales) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", id);
            builder.field("sales", sales);
            builder.endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER, id.toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + id + "的品牌更新索引成功!sales：" + sales);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateStyleAndPrice(Long brandId, String style, String price) {
        try {
            SearcherDesigner designer = findById(brandId.toString());
            if (designer != null) {
                XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", brandId);
                builder.field("style", style);
                builder.field("price", price);
                builder.endObject();
                elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER, brandId.toString())
                        .setDoc(builder).execute().actionGet();
                logger.info("ID为" + brandId + "的设计师更新索引成功!");
                return 1;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

}
