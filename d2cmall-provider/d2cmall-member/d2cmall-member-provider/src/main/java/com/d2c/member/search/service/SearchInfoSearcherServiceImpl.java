package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherMemberInfo;
import com.d2c.member.search.query.MemberInfoSearchBean;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service(protocol = "dubbo")
public class SearchInfoSearcherServiceImpl implements SearchInfoSearcherService {

    private static final Logger logger = LoggerFactory.getLogger(SearchInfoSearcherService.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberInfo searchInfo) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, searchInfo);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_SEACHERINFO, searchInfo.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("搜索关键字={}创建成功", searchInfo.getKeyword());
            return 1;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return 0;
    }

    @Override
    public PageResult<Map<String, Object>> findGroupBySearcher(MemberInfoSearchBean searcher, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_SEACHERINFO).setSearchType(SearchType.COUNT);
        BoolQueryBuilder boolqueryBuilder = QueryBuilders.boolQuery();
        if (searcher.getKeyword() != null) {
            boolqueryBuilder.must(QueryBuilders.queryString(searcher.getKeyword().toLowerCase()).field("keyword"));
        }
        if (searcher.getBeginDate() != null) {
            boolqueryBuilder.must(QueryBuilders.rangeQuery("createDate").from(searcher.getBeginDate().getTime()));
        }
        if (searcher.getEndDate() != null) {
            boolqueryBuilder.must(QueryBuilders.rangeQuery("createDate").to(searcher.getEndDate().getTime()));
        }
        builder.setQuery(boolqueryBuilder);
        TermsBuilder teamAgg = AggregationBuilders.terms("keyword").field("notAnalyzed").size(searcher.getTop());
        builder.addAggregation(teamAgg);
        SearchResponse response = builder.execute().actionGet();
        Map<String, Aggregation> asMap = response.getAggregations().getAsMap();
        StringTerms tersAgg = (StringTerms) asMap.get("keyword");
        Iterator<Bucket> teamBucketIt = tersAgg.getBuckets().iterator();
        Bucket buck = null;
        PageResult<Map<String, Object>> pager = new PageResult<Map<String, Object>>(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int i = 0;
        while (teamBucketIt.hasNext()) {
            buck = teamBucketIt.next();
            if ((i++ >= (page.getStartNumber())) && (i <= page.getStartNumber() + page.getPageSize())) { // 代码分页，聚合不支持分页
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("keyword", buck.getKey());
                map.put("count", buck.getDocCount());
                list.add(map);
            }
        }
        Integer toltalCount = tersAgg.getBuckets().size();
        pager.setTotalCount(toltalCount > searcher.getTop() ? searcher.getTop() : toltalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_SEACHERINFO)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        return 1;
    }

}
