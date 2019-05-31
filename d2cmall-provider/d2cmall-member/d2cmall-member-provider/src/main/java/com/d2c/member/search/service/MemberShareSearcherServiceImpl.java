package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.query.MemberShareSearchBean;
import com.d2c.member.search.support.MemberShareHelp;
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
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHitsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Service(protocol = "dubbo")
public class MemberShareSearcherServiceImpl implements MemberShareSearcherService {

    private static final Log logger = LogFactory.getLog(MemberShareSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberShare memberShare) {
        try {
            if (memberShare == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            memberShare.setScore(memberShare.getLikes() + memberShare.getVlikes() + memberShare.getComments());
            ElasticSearchConfig.buildContent(builder, memberShare);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE, memberShare.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + memberShare.getId() + "的买家秀建立索引成功!");
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
    public SearcherMemberShare findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE, id).execute().actionGet();
        SearcherMemberShare memberShare = null;
        if (response != null && response.isExists()) {
            Long memberShareId = Long.valueOf(response.getId());
            memberShare = new SearcherMemberShare();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                memberShare.setId(memberShareId);
                for (Iterator<Map.Entry<String, Object>> it = source.entrySet().iterator(); it.hasNext(); ) {
                    Map.Entry<String, Object> entry = it.next();
                    try {
                        BeanUtilsBean.getInstance().setProperty(memberShare, entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            }
        }
        return memberShare;
    }

    @Override
    public List<SearcherMemberShare> findByIds(String[] ids) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBERSHARE).addIds(ids)).execute().actionGet();
        List<SearcherMemberShare> list = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long memberShareId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberShare memberShare = new SearcherMemberShare();
                ElasticSearchConfig.transMap2Bean(source, memberShare);
                memberShare.setId(memberShareId);
                list.add(memberShare);
            }
        }
        return list;
    }

    @Override
    public PageResult<SearcherMemberShare> findByMemberIds(List<Long> memberIds, PageModel page, Integer status) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (status != null) {
            boolFilter.must(FilterBuilders.termFilter("status", status));
            builder.setPostFilter(boolFilter);
        }
        builder.addSort("top", SortOrder.DESC);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setQuery(QueryBuilders.termsQuery("memberId", memberIds))
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).execute()
                .actionGet();
        List<SearcherMemberShare> memberShares = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long memberShareId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberShare memberShare = new SearcherMemberShare();
                ElasticSearchConfig.transMap2Bean(source, memberShare);
                memberShare.setId(memberShareId);
                memberShares.add(memberShare);
            }
        }
        PageResult<SearcherMemberShare> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(memberShares);
        return pager;
    }

    @Override
    public PageResult<SearcherMemberShare> search(MemberShareSearchBean search, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE).setSearchType(SearchType.DEFAULT).setQuery(qb);
        buildBoolFilterBuilder(builder, search);
        for (int i = 0; search.getSortFields() != null && i < search.getSortFields().length; i++) {
            builder.addSort(search.getSortFields()[i], search.getOrders()[i]);
        }
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberShare> memberShares = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long memberShareId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberShare memberShare = new SearcherMemberShare();
                ElasticSearchConfig.transMap2Bean(source, memberShare);
                memberShare.setId(memberShareId);
                memberShares.add(memberShare);
            }
        }
        PageResult<SearcherMemberShare> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(memberShares);
        return pager;
    }

    @Override
    public int count(MemberShareSearchBean search) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE).setSearchType(SearchType.DEFAULT);
        buildBoolFilterBuilder(builder, search);
        SearchResponse response = builder.setSearchType(SearchType.COUNT).execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    private void buildBoolFilterBuilder(SearchRequestBuilder builder, MemberShareSearchBean searcher) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (searcher.getMemberId() != null && searcher.getAllList() == true) {
            boolFilter.should(FilterBuilders.termFilter("memberId", searcher.getMemberId()),
                    FilterBuilders.termFilter("status", 1));
            if (searcher.getTopicId() != null) {
                boolFilter.must(FilterBuilders.termFilter("topicId", searcher.getTopicId()));
            }
            builder.setPostFilter(boolFilter);
        } else if (searcher.getMemberId() != null) {
            boolFilter.must(FilterBuilders.termFilter("memberId", searcher.getMemberId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getStatus() != null) {
            boolFilter.must(FilterBuilders.termFilter("status", searcher.getStatus()));
            builder.setPostFilter(boolFilter);
        }
        // 买家秀中的productId没用
        if (searcher.getProductId() != null) {
            boolFilter.must(FilterBuilders.termFilter("productIds", searcher.getProductId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getDesignerId() != null) {
            boolFilter.must(FilterBuilders.termFilter("designerId", searcher.getDesignerId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getTagId() != null) {
            boolFilter.must(FilterBuilders.termFilter("tags", searcher.getTagId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getCity() != null) {
            boolFilter.must(FilterBuilders.termFilter("city", searcher.getCity()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getResourceType() != null) {
            boolFilter.must(FilterBuilders.termFilter("resourceType", searcher.getResourceType()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getTopicId() != null && searcher.getAllList() == false) {
            boolFilter.must(FilterBuilders.termFilter("topicId", searcher.getTopicId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getType() != null) {
            boolFilter.must(FilterBuilders.termFilter("type", searcher.getType()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getRole() != null) {
            boolFilter.must(FilterBuilders.termFilter("role", searcher.getRole()));
            builder.setPostFilter(boolFilter);
        }
    }

    @Override
    public int update(SearcherMemberShare memberShare) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", memberShare.getId());
            if (memberShare.getUrl() != null) {
                builder.field("url", memberShare.getUrl());
            }
            if (memberShare.getPic() != null) {
                builder.field("pic", memberShare.getPic());
            }
            if (memberShare.getLikes() != null) {
                builder.field("likes", memberShare.getLikes());
            }
            if (memberShare.getComments() != null) {
                builder.field("comments", memberShare.getComments());
            }
            if (memberShare.getVlikes() != null) {
                builder.field("vlikes", memberShare.getVlikes());
            }
            if (memberShare.getTop() != null) {
                builder.field("top", memberShare.getTop());
            }
            if (memberShare.getDesignerId() != null) {
                builder.field("designerId", memberShare.getDesignerId());
            }
            if (memberShare.getProductId() != null) {
                builder.field("productId", memberShare.getProductId());
            }
            if (memberShare.getStatus() != null) {
                builder.field("status", memberShare.getStatus());
            }
            if (memberShare.getTags() != null) {
                builder.field("tags", memberShare.getTags());
            }
            if (memberShare.getPicTag() != null) {
                builder.field("picTag", memberShare.getPicTag());
            }
            if (memberShare.getRole() != null) {
                builder.field("role", memberShare.getRole());
            }
            if (memberShare.getVideo() != null) {
                builder.field("video", memberShare.getVideo());
            }
            if (memberShare.getScore() != null) {
                builder.field("score", memberShare.getScore());
            }
            if (memberShare.getVerifyDate() != null) {
                builder.field("verifyDate", ElasticSearchConfig.transDate2Long(memberShare.getVerifyDate()));
            }
            if (memberShare.getProductIds() != null) {
                builder.field("productIds", memberShare.getProductIds());
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE, memberShare.getId().toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + memberShare.getId() + "的买家秀更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherMemberShare memberShare) {
        this.remove(memberShare.getId());
        return this.insert(memberShare);
    }

    @Override
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("memberId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberShare memberShare = new SearcherMemberShare();
                ElasticSearchConfig.transMap2Bean(source, memberShare);
                memberShare.setId(Long.valueOf(hit.getId()));
                UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE, memberShare.getId().toString())
                        .setDoc("headPic", headPic);
                bulkRequest.add(headPicRequest);
                UpdateRequestBuilder nickNameRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE, memberShare.getId().toString())
                        .setDoc("nickname", nickName);
                bulkRequest.add(nickNameRequest);
            }
        }
        bulkRequest.execute().actionGet();
    }

    @Override
    public int remove(Long memberShareId) {
        String id = memberShareId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE, id).execute()
                .actionGet();
        logger.info("ID为" + id + "的买家秀已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MEMBERSHARE)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public int doWatched(Long id) {
        SearcherMemberShare share = findById(id.toString());
        if (share != null) {
            try {
                XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", id.toString())
                        .field("watched", share.getWatched() + 1).endObject();
                elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE, id.toString()).setDoc(builder)
                        .execute().actionGet();
                logger.info("ID为" + id + "的专题更新索引成功!");
                return 1;
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return 0;
    }

    @Override
    public PageResult<SearcherMemberShare> findHotShare(String type, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE).setSearchType(SearchType.DEFAULT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.rangeFilter("createDate").gte(calendar.getTimeInMillis()));
        boolFilter.must(FilterBuilders.rangeFilter("createDate").lte(new Date().getTime()));
        boolFilter.must(FilterBuilders.termFilter("resourceType", type));
        boolFilter.must(FilterBuilders.termFilter("status", 1));
        builder.setPostFilter(boolFilter);
        builder.addSort("score", SortOrder.DESC);
        SearchResponse response = builder.setFrom((page.getPageNumber() - 1) * page.getPageSize())
                .setSize(page.getPageSize()).setExplain(true).execute().actionGet();
        List<SearcherMemberShare> memberShares = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long memberShareId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberShare memberShare = new SearcherMemberShare();
                ElasticSearchConfig.transMap2Bean(source, memberShare);
                memberShare.setId(memberShareId);
                memberShares.add(memberShare);
            }
        }
        PageResult<SearcherMemberShare> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(memberShares);
        return pager;
    }

    @Override
    public List<MemberShareHelp> findHotMember(String type, PageModel page) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MEMBERSHARE)
                .setSearchType(SearchType.DEFAULT);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("memberId")
                .order(Terms.Order.compound(Terms.Order.aggregation("sum_score", false))).size(page.getPageSize());
        TopHitsBuilder top = AggregationBuilders.topHits("top").addSort("createDate", SortOrder.DESC).setSize(1);
        SumBuilder sb = AggregationBuilders.sum("sum_score").field("score");
        aggregation.subAggregation(sb).subAggregation(top);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if ("designer".equals(type)) {
            qb.must(QueryBuilders.termQuery("role", 1));
        } else {
            qb.mustNot(QueryBuilders.termQuery("role", 1));
        }
        qb.must(QueryBuilders.termQuery("status", 1));
        qb.must(QueryBuilders.rangeQuery("createDate").from(calendar.getTimeInMillis()).to(new Date().getTime()));
        SearchResponse response = responsebuilder.setQuery(qb).addAggregation(aggregation).setSize(0).execute()
                .actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms agg = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = agg.getBuckets().iterator();
        List<MemberShareHelp> list = new ArrayList<>();
        while (typeBucketIt.hasNext()) {
            Bucket buck = typeBucketIt.next();
            MemberShareHelp memberShareHelp = new MemberShareHelp();
            memberShareHelp
                    .setHot(Double.valueOf(((((Sum) buck.getAggregations().get("sum_score")).getValue()))).longValue());
            TopHits topHits = buck.getAggregations().get("top");
            for (SearchHit hit : topHits.getHits().getHits()) {
                Long shareId = Long.valueOf(hit.getId());
                Map<String, Object> source = hit.getSource();
                if (!source.isEmpty()) {
                    SearcherMemberShare memberShare = new SearcherMemberShare();
                    ElasticSearchConfig.transMap2Bean(source, memberShare);
                    memberShare.setId(shareId);
                    memberShareHelp.setHeadPic(memberShare.getHeadPic());
                    memberShareHelp.setNickName(memberShare.getNickname());
                    memberShareHelp.setMemberId(memberShare.getMemberId());
                }
            }
            list.add(memberShareHelp);
        }
        return list;
    }

    @Override
    public List<MemberShareHelp> findMemberOrderByCount(String type, Integer pageSize) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MEMBERSHARE)
                .setSearchType(SearchType.QUERY_THEN_FETCH);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("memberId")
                .order(Terms.Order.compound(Terms.Order.aggregation("countMemberId", false))).size(pageSize);
        SumBuilder count = AggregationBuilders.sum("countMemberId").field("status");
        TopHitsBuilder top = AggregationBuilders.topHits("top").addSort("createDate", SortOrder.DESC);
        aggregation.subAggregation(count);
        aggregation.subAggregation(top);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -2);
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.must(QueryBuilders.termQuery("role", "designer".equals(type) ? 1 : 0));
        qb.must(QueryBuilders.termQuery("status", 1));
        qb.must(QueryBuilders.rangeQuery("createDate").from(calendar.getTimeInMillis()).to(new Date().getTime()));
        SearchResponse response = responsebuilder.setQuery(qb).addAggregation(aggregation).setSize(0).execute()
                .actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms agg = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = agg.getBuckets().iterator();
        List<MemberShareHelp> list = new ArrayList<>();
        while (typeBucketIt.hasNext()) {
            Bucket buck = typeBucketIt.next();
            MemberShareHelp memberShareHelp = new MemberShareHelp();
            memberShareHelp.setHot(buck.getDocCount());
            TopHits topHits = buck.getAggregations().get("top");
            for (SearchHit hit : topHits.getHits().getHits()) {
                Long shareId = Long.valueOf(hit.getId());
                Map<String, Object> source = hit.getSource();
                if (!source.isEmpty()) {
                    SearcherMemberShare memberShare = new SearcherMemberShare();
                    ElasticSearchConfig.transMap2Bean(source, memberShare);
                    memberShare.setId(shareId);
                    memberShareHelp.setHeadPic(memberShare.getHeadPic());
                    memberShareHelp.setNickName(memberShare.getNickname());
                    memberShareHelp.setMemberId(memberShare.getMemberId());
                }
            }
            list.add(memberShareHelp);
        }
        return list;
    }

    @Override
    public SearcherMemberShare findLastedByMemberId(Long memberId) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE).setSearchType(SearchType.DEFAULT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("status", 1));
        boolFilter.must(FilterBuilders.termFilter("memberId", memberId));
        builder.setPostFilter(boolFilter);
        builder.addSort("top", SortOrder.DESC);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSize(1).execute().actionGet();
        SearcherMemberShare memberShare = new SearcherMemberShare();
        for (SearchHit hit : response.getHits()) {
            Long memberShareId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, memberShare);
                memberShare.setId(memberShareId);
            }
        }
        return memberShare;
    }

    @Override
    public Long countByTopicId(Long topicId) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE).setSearchType(SearchType.COUNT)
                .setQuery(QueryBuilders.termQuery("topicId", topicId)).setExplain(true).execute().actionGet();
        return response.getHits().getTotalHits();
    }

    @Override
    public int updateRoleByMemberId(Long memberInfoId, Integer role) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBERSHARE).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("memberId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberShare memberShare = new SearcherMemberShare();
                ElasticSearchConfig.transMap2Bean(source, memberShare);
                memberShare.setId(Long.valueOf(hit.getId()));
                UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBERSHARE, memberShare.getId().toString())
                        .setDoc("role", role);
                bulkRequest.add(headPicRequest);
            }
        }
        bulkRequest.execute().actionGet();
        return 1;
    }

}
