package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherMemberCollection;
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
public class MemberCollectionSearcherServiceImpl implements MemberCollectionSearcherService {

    private static final Log logger = LogFactory.getLog(MemberCollectionSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberCollection memberCollection) {
        try {
            if (memberCollection == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, memberCollection);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_COLLECTION,
                            memberCollection.getMemberId() + "_" + memberCollection.getProductId())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + memberCollection.getMemberId() + "收藏的商品建立索引成功!product:"
                    + memberCollection.getProductName());
            return 1;
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public Map<Long, SearcherMemberCollection> findByIds(String[] ids) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_COLLECTION).addIds(ids)).setSize(ids.length).execute()
                .actionGet();
        Map<Long, SearcherMemberCollection> memberCollections = new HashMap<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberCollection memberCollection = new SearcherMemberCollection();
                ElasticSearchConfig.transMap2Bean(source, memberCollection);
                memberCollections.put(memberCollection.getProductId(), memberCollection);
            }
        }
        return memberCollections;
    }

    @Override
    public List<Long> findByProductIds(Long productId) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .addField("memberId").setTypes(TYPE_MEMBER_COLLECTION).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("productId", productId));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setExplain(true).execute().actionGet();
        // .setFrom((page.getPageNumber() - 1) *
        // page.getPageSize()).setSize(page.getPageSize())
        List<Long> memberIds = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            SearchHitField source = hit.getFields().get("memberId");
            memberIds.add(Long.parseLong(source.getValue().toString()));
        }
        return memberIds;
    }

    @Override
    public List<Long> findProductIdsInCollection(Long memberId, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_COLLECTION).setSearchType(SearchType.DEFAULT).setQuery(qb);
        builder.setPostFilter(FilterBuilders.termFilter("memberId", memberId));
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<Long> productIds = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                productIds.add(((Number) source.get("productId")).longValue());
            }
        }
        return productIds;
    }

    @Override
    public PageResult<SearcherMemberCollection> findByMemberId(Long id, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_COLLECTION).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("memberId", id));
        builder.setPostFilter(boolFilter);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMemberCollection> tops = new ArrayList<>();
        List<String> productIds = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberCollection memberCollection = new SearcherMemberCollection();
                ElasticSearchConfig.transMap2Bean(source, memberCollection);
                tops.add(memberCollection);
                productIds.add(memberCollection.getProductId().toString());
            }
        }
        PageResult<SearcherMemberCollection> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tops);
        return pager;
    }

    @Override
    public int countByMemberId(Long memberId) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_COLLECTION).setSearchType(SearchType.COUNT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("memberId", memberId));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    @Override
    public SearcherMemberCollection findByMemberAndProductId(Long memberInfoId, Long productId) {
        String id = memberInfoId + "_" + productId;
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_COLLECTION).addIds(id)).execute().actionGet();
        SearcherMemberCollection memberCollection = new SearcherMemberCollection();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, memberCollection);
            }
        }
        return memberCollection;
    }

    @Override
    public int rebuild(SearcherMemberCollection memberCollection) {
        remove(memberCollection.getMemberId(), memberCollection.getProductId());
        return insert(memberCollection);
    }

    @Override
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_COLLECTION).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("memberId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberCollection memberCollection = new SearcherMemberCollection();
                ElasticSearchConfig.transMap2Bean(source, memberCollection);
                UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_COLLECTION,
                                memberCollection.getMemberId().toString() + "_" + memberCollection.getProductId())
                        .setDoc("headPic", headPic);
                bulkRequest.add(headPicRequest);
                UpdateRequestBuilder nickNameRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_COLLECTION,
                                memberCollection.getMemberId().toString() + "_" + memberCollection.getProductId())
                        .setDoc("nickname", nickName);
                bulkRequest.add(nickNameRequest);
            }
        }
        bulkRequest.execute().actionGet();
    }

    @Override
    public int remove(Long memberId, Long productId) {
        String id = memberId + "_" + productId;
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_COLLECTION, id)
                .execute().actionGet();
        logger.info("ID为" + id + "的收藏商品已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_COLLECTION).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

}
