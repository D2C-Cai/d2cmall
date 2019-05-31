package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherMemberAttention;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Service(protocol = "dubbo")
public class MemberAttentionSearcherServiceImpl implements MemberAttentionSearcherService {

    private static final Log logger = LogFactory.getLog(MemberAttentionSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberAttention memberAttention) {
        try {
            if (memberAttention == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, memberAttention);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_ATTENTION,
                            memberAttention.getMemberId() + "_" + memberAttention.getDesignerId())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + memberAttention.getMemberId() + "关注的设计师建立索引成功!designer:"
                    + memberAttention.getDesignerName());
            return 1;
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public Map<Long, SearcherMemberAttention> findByIds(String[] ids) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_ATTENTION).addIds(ids)).setSize(ids.length).execute()
                .actionGet();
        Map<Long, SearcherMemberAttention> memberLikes = new HashMap<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberAttention memberLike = new SearcherMemberAttention();
                ElasticSearchConfig.transMap2Bean(source, memberLike);
                memberLikes.put(memberLike.getDesignerId(), memberLike);
            }
        }
        return memberLikes;
    }

    @Override
    public PageResult<SearcherMemberAttention> findByDesignerIds(List<Long> designerIds, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_ATTENTION).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("designerId", designerIds));
        builder.setPostFilter(boolFilter);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberAttention> tops = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberAttention memberAttention = new SearcherMemberAttention();
                ElasticSearchConfig.transMap2Bean(source, memberAttention);
                tops.add(memberAttention);
            }
        }
        PageResult<SearcherMemberAttention> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public PageResult<SearcherMemberAttention> findByDesignerId(Long designerId, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_ATTENTION).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("designerId", designerId));
        builder.setPostFilter(boolFilter);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberAttention> tops = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberAttention memberAttention = new SearcherMemberAttention();
                ElasticSearchConfig.transMap2Bean(source, memberAttention);
                tops.add(memberAttention);
            }
        }
        PageResult<SearcherMemberAttention> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public int coutByDesignerId(Long designerId) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_ATTENTION).setSearchType(SearchType.COUNT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("designerId", designerId));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    @Override
    public PageResult<SearcherMemberAttention> findByMemberId(Long id, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_ATTENTION).setSearchType(SearchType.DEFAULT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("memberId", id));
        builder.setPostFilter(boolFilter);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberAttention> tops = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberAttention memberAttention = new SearcherMemberAttention();
                ElasticSearchConfig.transMap2Bean(source, memberAttention);
                tops.add(memberAttention);
            }
        }
        PageResult<SearcherMemberAttention> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public int countByMemberId(Long memberId) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_ATTENTION).setSearchType(SearchType.COUNT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("memberId", memberId));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    @Override
    public SearcherMemberAttention findByMemberAndDesignerId(Long memberInfoId, Long designerId) {
        String id = memberInfoId + "_" + designerId;
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_ATTENTION).addIds(id)).execute().actionGet();
        SearcherMemberAttention memberAttention = new SearcherMemberAttention();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, memberAttention);
            }
        }
        return memberAttention;
    }

    @Override
    public int rebuild(SearcherMemberAttention memberAttention) {
        remove(memberAttention.getMemberId(), memberAttention.getDesignerId());
        return insert(memberAttention);
    }

    @Override
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_ATTENTION).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("memberId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberAttention memberAttention = new SearcherMemberAttention();
                ElasticSearchConfig.transMap2Bean(source, memberAttention);
                UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_ATTENTION,
                                memberAttention.getMemberId().toString() + "_" + memberAttention.getDesignerId())
                        .setDoc("headPic", headPic);
                bulkRequest.add(headPicRequest);
                UpdateRequestBuilder nickNameRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_ATTENTION,
                                memberAttention.getMemberId().toString() + "_" + memberAttention.getDesignerId())
                        .setDoc("nickname", nickName);
                bulkRequest.add(nickNameRequest);
            }
        }
        bulkRequest.execute().actionGet();
    }

    @Override
    public int remove(Long memberId, Long designerId) {
        String id = memberId + "_" + designerId;
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_ATTENTION, id)
                .execute().actionGet();
        logger.info("ID为" + id + "的关注设计师已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_ATTENTION).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public List<SearcherMemberAttention> findHotBrand(PageModel page) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MEMBER_ATTENTION)
                .setSearchType(SearchType.QUERY_THEN_FETCH);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("designerId")
                .size(page.getPageSize());
        TopHitsBuilder top = AggregationBuilders.topHits("top").addSort("createDate", SortOrder.DESC).setSize(1);
        aggregation.subAggregation(top);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.rangeFilter("createDate").gte(calendar.getTimeInMillis()));
        boolFilter.must(FilterBuilders.rangeFilter("createDate").lte(new Date().getTime()));
        SearchResponse response = responsebuilder.setPostFilter(boolFilter).addAggregation(aggregation).setSize(0)
                .execute().actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms agg = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = agg.getBuckets().iterator();
        List<SearcherMemberAttention> list = new ArrayList<>();
        while (typeBucketIt.hasNext()) {
            Bucket buck = typeBucketIt.next();
            TopHits topHits = buck.getAggregations().get("top");
            for (SearchHit hit : topHits.getHits().getHits()) {
                Map<String, Object> source = hit.getSource();
                if (!source.isEmpty()) {
                    SearcherMemberAttention memberAttention = new SearcherMemberAttention();
                    ElasticSearchConfig.transMap2Bean(source, memberAttention);
                    list.add(memberAttention);
                }
            }
        }
        return list;
    }

}
