package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherMemberFollow;
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
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class MemberFollowSearcherServiceImpl implements MemberFollowSearcherService {

    private static final Log logger = LogFactory.getLog(MemberFollowSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberFollow searcherMemberFollow) {
        try {
            if (searcherMemberFollow == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, searcherMemberFollow);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_FOLLOW,
                            searcherMemberFollow.getFromId() + "_" + searcherMemberFollow.getToId())
                    .setSource(builder).execute().actionGet();
            return 1;
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public SearcherMemberFollow findById(String id) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_FOLLOW).addIds(id)).execute().actionGet();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberFollow searcherMemberFollow = new SearcherMemberFollow();
                ElasticSearchConfig.transMap2Bean(source, searcherMemberFollow);
                return searcherMemberFollow;
            }
        }
        return null;
    }

    @Override
    public Map<Long, SearcherMemberFollow> findByIds(String[] ids) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_FOLLOW).addIds(ids)).setSize(ids.length).execute()
                .actionGet();
        Map<Long, SearcherMemberFollow> memberFollows = new HashMap<Long, SearcherMemberFollow>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberFollow memberFollow = new SearcherMemberFollow();
                ElasticSearchConfig.transMap2Bean(source, memberFollow);
                memberFollows.put(memberFollow.getToId(), memberFollow);
            }
        }
        return memberFollows;
    }

    @Override
    public PageResult<SearcherMemberFollow> findByFromId(Long fromId, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_FOLLOW).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("fromId", fromId));
        builder.setPostFilter(boolFilter);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberFollow> tops = new ArrayList<SearcherMemberFollow>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberFollow memberFollow = new SearcherMemberFollow();
                ElasticSearchConfig.transMap2Bean(source, memberFollow);
                tops.add(memberFollow);
            }
        }
        PageResult<SearcherMemberFollow> pager = new PageResult<SearcherMemberFollow>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public int countByFromId(Long fromId) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_FOLLOW).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("fromId", fromId));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setExplain(true).execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    @Override
    public PageResult<SearcherMemberFollow> findByToId(Long toId, Integer friends, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_FOLLOW).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("toId", toId));
        if (friends != null) {
            boolFilter.must(FilterBuilders.termFilter("friends", friends));
        }
        builder.setPostFilter(boolFilter).addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberFollow> tops = new ArrayList<SearcherMemberFollow>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberFollow memberFollow = new SearcherMemberFollow();
                ElasticSearchConfig.transMap2Bean(source, memberFollow);
                tops.add(memberFollow);
            }
        }
        PageResult<SearcherMemberFollow> pager = new PageResult<SearcherMemberFollow>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public int countByToId(Long toId, Integer friends) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_FOLLOW).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("toId", toId));
        if (friends != null) {
            boolFilter.must(FilterBuilders.termFilter("friends", friends));
        }
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setExplain(true).execute().actionGet();
        List<SearcherMemberFollow> tops = new ArrayList<SearcherMemberFollow>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberFollow memberLike = new SearcherMemberFollow();
                ElasticSearchConfig.transMap2Bean(source, memberLike);
                tops.add(memberLike);
            }
        }
        return (int) response.getHits().getTotalHits();
    }

    @Override
    public List<Long> findToIdByFromId(Long fromId) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .addField("toId").setTypes(TYPE_MEMBER_FOLLOW).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("fromId", fromId));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setExplain(true).execute().actionGet();
        List<Long> toIds = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            SearchHitField source = hit.getFields().get("toId");
            toIds.add(Long.parseLong(source.getValue().toString()));
        }
        return toIds;
    }

    @Override
    public int updateFriends(Long fromId, Long toId, int friend) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.field("friends", friend);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_FOLLOW, fromId + "_" + toId)
                    .setDoc(builder).execute().actionGet();
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherMemberFollow searcherMemberFollow) {
        remove(searcherMemberFollow.getFromId() + "_" + searcherMemberFollow.getToId());
        return insert(searcherMemberFollow);
    }

    @Override
    public void doMerge(Long memberSourceId, Long memberTargetId) {
    }

    @Override
    public void doRefreshHeadPic4From(Long memberInfoId, String headPic, String nickName) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_FOLLOW).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("fromId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberFollow memberFollow = new SearcherMemberFollow();
                ElasticSearchConfig.transMap2Bean(source, memberFollow);
                UpdateRequestBuilder request = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_FOLLOW,
                                memberFollow.getFromId().toString() + "_" + memberFollow.getToId().toString())
                        .setDoc("fromHeadPic", headPic);
                bulkRequest.add(request);
                UpdateRequestBuilder nickNameRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_FOLLOW,
                                memberFollow.getFromId().toString() + "_" + memberFollow.getToId().toString())
                        .setDoc("fromNickName", nickName);
                bulkRequest.add(nickNameRequest);
            }
        }
        bulkRequest.execute().actionGet();
    }

    @Override
    public void doRefreshHeadPic4To(Long memberInfoId, String headPic, String nickName) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_FOLLOW).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("toId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberFollow memberFollow = new SearcherMemberFollow();
                ElasticSearchConfig.transMap2Bean(source, memberFollow);
                UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_FOLLOW,
                                memberFollow.getFromId().toString() + "_" + memberFollow.getToId().toString())
                        .setDoc("toHeadPic", headPic);
                bulkRequest.add(headPicRequest);
                UpdateRequestBuilder nickNameRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_FOLLOW,
                                memberFollow.getFromId().toString() + "_" + memberFollow.getToId().toString())
                        .setDoc("toNickName", nickName);
                bulkRequest.add(nickNameRequest);
            }
        }
        bulkRequest.execute().actionGet();
    }

    @Override
    public int remove(String id) {
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_FOLLOW, id).execute()
                .actionGet();
        logger.info("ID为" + id + "的关注设计师已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_FOLLOW).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

}
