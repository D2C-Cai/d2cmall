package com.d2c.content.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.search.model.SearcherTheme;
import com.d2c.content.search.query.ThemeSearcherBean;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Service(protocol = "dubbo")
public class ThemeSearcherServiceImpl implements ThemeSearcherService {

    private static final Log logger = LogFactory.getLog(ThemeSearcherService.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherTheme searcherTheme) {
        try {
            if (searcherTheme == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, searcherTheme);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_THEME, searcherTheme.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + searcherTheme.getId() + "的专题建立索引成功!");
            return 1;
        } catch (ElasticsearchException e) {
            logger.error(e);
            logger.info("Elasticsearch Exception: " + e.toString());
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    public SearcherTheme findById(Long id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_THEME, id.toString()).execute().actionGet();
        SearcherTheme searcherTheme = null;
        if (response != null && response.isExists()) {
            Long memberShareId = Long.valueOf(response.getId());
            searcherTheme = new SearcherTheme();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                searcherTheme.setId(memberShareId);
                for (Iterator<Map.Entry<String, Object>> it = source.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, Object> entry = it.next();
                    try {
                        BeanUtilsBean.getInstance().setProperty(searcherTheme, entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            }
        }
        return searcherTheme;
    }

    @Override
    public int remove(Long themeId) {
        String id = themeId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_THEME, id).execute()
                .actionGet();
        logger.info("ID为" + id + "的专题已删除索引!");
        return 1;
    }

    @Override
    public PageResult<SearcherTheme> search(ThemeSearcherBean search, PageModel page) {
        BoolQueryBuilder bq = QueryBuilders.boolQuery();
        if (search.getKeyword() != null) {
            bq = bq.must(QueryBuilders.multiMatchQuery(search.getKeyword(), "keyword^4", "title^3").analyzer("ik")
                    .analyzer("ik"));
        }
        bq.must(buildBoolFilterBuilder(search));
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_THEME).setSearchType(SearchType.DEFAULT).setQuery(bq);
        builder.addSort("sort", SortOrder.DESC);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherTheme> themes = new ArrayList<SearcherTheme>();
        for (SearchHit hit : response.getHits()) {
            Long memberShareId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherTheme searcherTheme = new SearcherTheme();
                ElasticSearchConfig.transMap2Bean(source, searcherTheme);
                searcherTheme.setId(memberShareId);
                themes.add(searcherTheme);
            }
        }
        PageResult<SearcherTheme> pager = new PageResult<SearcherTheme>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(themes);
        return pager;
    }

    private BoolQueryBuilder buildBoolFilterBuilder(ThemeSearcherBean searcher) {
        BoolQueryBuilder bq = QueryBuilders.boolQuery();
        if (searcher.getTagId() != null) {
            bq.must(QueryBuilders.termQuery("tagId", searcher.getTagId()));
        }
        if (searcher.getTitle() != null) {
            bq.must(QueryBuilders.termQuery("title", searcher.getTitle()));
        }
        if (searcher.getStatus() != null) {
            bq.must(QueryBuilders.termQuery("status", searcher.getStatus()));
        }
        if (searcher.getType() != null) {
            bq.must(QueryBuilders.termQuery("type", searcher.getType().toLowerCase()));
        }
        if (searcher.getFix() != null) {
            bq.must(QueryBuilders.termQuery("fix", searcher.getFix()));
        }
        Date now = new Date();
        bq.must(QueryBuilders.rangeQuery("beginDate").lt(now.getTime()))
                .must(QueryBuilders.rangeQuery("endDate").gt(now.getTime()));
        return bq;
    }

    @Override
    public int rebuild(SearcherTheme searcherTheme) {
        this.remove(searcherTheme.getId());
        return this.insert(searcherTheme);
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_THEME)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public Map<Long, Long> countGroupByTag() {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_THEME).setSearchType(SearchType.COUNT);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("tagAgg").field("tagId");
        SearchResponse response = responsebuilder
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("status", 1)))
                .addAggregation(aggregation).execute().actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms agg = (Terms) aggMap.get("tagAgg");
        Iterator<Bucket> typeBucketIt = agg.getBuckets().iterator();
        Map<Long, Long> map = new HashMap<Long, Long>();
        while (typeBucketIt.hasNext()) {
            Bucket buck = typeBucketIt.next();
            map.put(Long.parseLong(buck.getKey()), buck.getDocCount());
        }
        return map;
    }

    @Override
    public int updateRecommend(Long id, Integer recommend) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", id.toString())
                    .field("recommend", recommend).endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_THEME, id.toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + id + "的专题更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", id.toString())
                    .field("status", status).endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_THEME, id.toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + id + "的专题更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", id.toString())
                    .field("sort", sort).endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_THEME, id.toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + id + "的专题更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateFixByTagId(Long tagId, Integer fix) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_THEME).setSearchType(SearchType.DEFAULT)
                .setQuery(QueryBuilders.termQuery("tagId", tagId));
        SearchResponse response = builder.setSize(100).setExplain(true).execute().actionGet();
        if (response.getHits().getTotalHits() > 0) {
            BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
            for (SearchHit hit : response.getHits()) {
                Map<String, Object> source = hit.getSource();
                if (!source.isEmpty()) {
                    Long id = Long.valueOf(hit.getId());
                    UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                            .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_THEME, id.toString())
                            .setDoc("fix", fix);
                    bulkRequest.add(headPicRequest);
                }
            }
            bulkRequest.execute().actionGet();
        }
        return 1;
    }

}
