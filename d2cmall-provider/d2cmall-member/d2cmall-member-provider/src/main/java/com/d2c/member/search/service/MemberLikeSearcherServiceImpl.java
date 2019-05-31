package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherMemberLike;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class MemberLikeSearcherServiceImpl implements MemberLikeSearcherService {

    private static final Log logger = LogFactory.getLog(MemberLikeSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberLike memberLike) {
        try {
            if (memberLike == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, memberLike);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_LIKE,
                            memberLike.getMemberId() + "_" + memberLike.getShareId())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + memberLike.getMemberId() + "喜欢的买家秀建立索引成功!share:" + memberLike.getShareName());
            return 1;
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public Map<Long, SearcherMemberLike> findByIds(String[] ids) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_LIKE).addIds(ids)).setSize(ids.length).execute()
                .actionGet();
        Map<Long, SearcherMemberLike> memberLikes = new HashMap<Long, SearcherMemberLike>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberLike memberLike = new SearcherMemberLike();
                ElasticSearchConfig.transMap2Bean(source, memberLike);
                memberLikes.put(memberLike.getShareId(), memberLike);
            }
        }
        return memberLikes;
    }

    @Override
    public PageResult<SearcherMemberLike> findByMemberId(Long memberId, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_LIKE).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("memberId", memberId));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberLike> tops = new ArrayList<SearcherMemberLike>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberLike memberLike = new SearcherMemberLike();
                ElasticSearchConfig.transMap2Bean(source, memberLike);
                tops.add(memberLike);
            }
        }
        PageResult<SearcherMemberLike> pager = new PageResult<SearcherMemberLike>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public PageResult<SearcherMemberLike> findByShareId(Long shareId, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_LIKE).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("shareId", shareId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setPostFilter(boolFilter)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberLike> tops = new ArrayList<SearcherMemberLike>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberLike memberLike = new SearcherMemberLike();
                ElasticSearchConfig.transMap2Bean(source, memberLike);
                tops.add(memberLike);
            }
        }
        PageResult<SearcherMemberLike> pager = new PageResult<SearcherMemberLike>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public SearcherMemberLike findByMemberAndShareId(Long memberInfoId, Long shareId) {
        String id = memberInfoId + "_" + shareId;
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_LIKE).addIds(id)).execute().actionGet();
        SearcherMemberLike memberLike = new SearcherMemberLike();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, memberLike);
            }
        }
        return memberLike;
    }

    @Override
    public int rebuild(SearcherMemberLike memberLike) {
        remove(memberLike.getMemberId(), memberLike.getShareId());
        return insert(memberLike);
    }

    @Override
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_LIKE).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("memberId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberLike memberLike = new SearcherMemberLike();
                ElasticSearchConfig.transMap2Bean(source, memberLike);
                UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_LIKE,
                                memberLike.getMemberId().toString() + "_" + memberLike.getShareId().toString())
                        .setDoc("headPic", headPic);
                bulkRequest.add(headPicRequest);
                UpdateRequestBuilder nickNameRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_LIKE,
                                memberLike.getMemberId().toString() + "_" + memberLike.getShareId().toString())
                        .setDoc("nickName", nickName);
                bulkRequest.add(nickNameRequest);
            }
        }
        bulkRequest.execute().actionGet();
    }

    @Override
    public int remove(Long memberId, Long shareId) {
        String id = memberId + "_" + shareId;
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_LIKE, id).execute()
                .actionGet();
        logger.info("ID为" + id + "的关注设计师已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MEMBER_LIKE)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

}
