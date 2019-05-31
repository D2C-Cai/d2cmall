package com.d2c.logger.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.logger.search.model.SearcherMemberSum;
import com.d2c.util.string.PinYinUtil;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Service(protocol = "dubbo")
public class SearchKeySearcherServiceImpl implements SearchKeySearcherService {

    private static final Logger logger = LoggerFactory.getLogger(SearchKeySearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberSum searchSum, Integer mark) {
        // 如果是非上架状态不建立索引
        if (searchSum == null)
            return 0;
        insertElastic(searchSum, mark);
        String keyword = searchSum.getKeyword();
        if (PinYinUtil.containChinese(keyword)) {
            searchSum.setKeyword(PinYinUtil.getFirstSpell(keyword).toLowerCase());
            insertElastic(searchSum, mark);
            searchSum.setKeyword(PinYinUtil.getFullSpell(keyword).toLowerCase());
            insertElastic(searchSum, mark);
        }
        return 1;
    }

    private int insertElastic(SearcherMemberSum searchSum, Integer mark) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                    .field("keyword", searchSum.getKeyword().toLowerCase())
                    .field("type", searchSum.getType().toString()).field("sourceId", searchSum.getSourceId())
                    .field("sort", searchSum.getSort() == null ? 0 : searchSum.getSort())
                    .field("number", searchSum.getNumber() == null ? 0 : searchSum.getNumber())
                    .field("displayName", searchSum.getDisplayName().replace("null", ""))
                    .field("sys",
                            (searchSum.getSystem() == null || !searchSum.getSystem().equals(Boolean.TRUE)) ? 0 : 1)
                    .endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_SEACHER,
                            searchSum.getType() + "_" + searchSum.getSourceId() + "_" + searchSum.getId() + "_" + mark)
                    .setSource(builder).execute().actionGet();
            logger.info("展示名字={}", searchSum.getDisplayName());
            logger.info("搜索关键字={}创建成功", searchSum.getKeyword());
            return 1;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return 0;
    }

    @Override
    public Set<String> search(String keywords) {
        QueryBuilder qb;
        if (PinYinUtil.containChinese(keywords)) {
            qb = QueryBuilders.multiMatchQuery(keywords, "keyword^1", "displayName^1").analyzer("ik")
                    .minimumShouldMatch("10").boost(1);
        } else {
            qb = QueryBuilders.prefixQuery("keyword", keywords);
        }
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_SEACHER).setSearchType(SearchType.DEFAULT).setQuery(qb);
        builder.addSort("sort", SortOrder.DESC);
        builder.addSort("number", SortOrder.DESC);
        SearchResponse response = builder.setFrom(0).setSize(10).setExplain(true).execute().actionGet();
        Set<String> result = new TreeSet<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            result.add((String) source.get("displayName"));
        }
        return result;
    }

    @Override
    public int removeByKey(String keyword) {
        QueryBuilder qb = QueryBuilders.multiMatchQuery(keyword, "keyword").analyzer("ik");
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_SEACHER)
                .setQuery(qb).execute().actionGet();
        return 1;
    }

    @Override
    public int removeByTypeAndSourceId(String type, String sourceId) {
        QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("type", type))
                .must(QueryBuilders.termQuery("sourceId", sourceId));
        DeleteByQueryRequestBuilder deleteByQueryRequestBuilder = elasticSearchConfig.getClient()
                .prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME);
        deleteByQueryRequestBuilder.setQuery(qb);
        deleteByQueryRequestBuilder.setTypes(TYPE_SEACHER).execute().actionGet();
        return 1;
    }

    @Override
    public int removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_SEACHER)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        return 1;
    }

}
