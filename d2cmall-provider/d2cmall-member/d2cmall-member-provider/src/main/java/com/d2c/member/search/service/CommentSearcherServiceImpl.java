package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherComment;
import com.d2c.member.search.query.CommentSearchBean;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class CommentSearcherServiceImpl implements CommentSearcherService {

    private static final Logger logger = LoggerFactory.getLogger(CommentSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherComment comment) {
        try {
            if (comment == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            comment.setRecomendDate(
                    comment.getRecomendDate() == null ? comment.getCreateDate() : comment.getRecomendDate());
            ElasticSearchConfig.buildContent(builder, comment);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENT, comment.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("id={},content={},建立成功", comment.getId(), comment.getContent());
            return 1;
        } catch (ElasticsearchException e) {
            logger.error("Elasticsearch Exception: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SearcherComment findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENT, id).execute().actionGet();
        SearcherComment comment = null;
        if (response != null && response.isExists()) {
            Long desingerId = Long.valueOf(response.getId());
            comment = new SearcherComment();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, comment);
                comment.setId(desingerId);
            }
        }
        return comment;
    }

    @Override
    public List<SearcherComment> findTop3Pic(Long productId) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_COMMENT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("productId", productId));
        boolFilter.must(FilterBuilders.termFilter("verified", true));
        boolFilter.must(FilterBuilders.existsFilter("pic"));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(3)
                .addSort("recomend", SortOrder.DESC).addSort("recomendDate", SortOrder.DESC).setExplain(true).execute()
                .actionGet();
        List<SearcherComment> comments = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherComment searcherComment = new SearcherComment();
                ElasticSearchConfig.transMap2Bean(source, searcherComment);
                comments.add(searcherComment);
            }
        }
        return comments;
    }

    @Override
    public PageResult<SearcherComment> findCombComment(long[] productIds, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_COMMENT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("verified", true));
        builder.setPostFilter(boolFilter);
        builder.addSort("recomend", SortOrder.DESC);
        builder.addSort("recomendDate", SortOrder.DESC);
        builder.setPostFilter(FilterBuilders.termsFilter("productId", productIds));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherComment> comments = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long desingerId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherComment comment = new SearcherComment();
                ElasticSearchConfig.transMap2Bean(source, comment);
                comment.setId(desingerId);
                comments.add(comment);
            }
        }
        PageResult<SearcherComment> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(comments);
        return pager;
    }

    @Override
    public PageResult<SearcherComment> search(CommentSearchBean search, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_COMMENT).setSearchType(SearchType.DEFAULT).setTimeout(TimeValue.timeValueMinutes(1));
        buildBoolFilterBuilder(builder, search);
        builder.addSort("recomend", SortOrder.DESC);
        builder.addSort("recomendDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherComment> comments = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long desingerId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherComment comment = new SearcherComment();
                ElasticSearchConfig.transMap2Bean(source, comment);
                comment.setId(desingerId);
                comments.add(comment);
            }
        }
        PageResult<SearcherComment> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(comments);
        return pager;
    }

    @Override
    public int update(SearcherComment searcherComment) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", searcherComment.getId());
            if (searcherComment.getName() != null) {
                builder.field("name", searcherComment.getName());
            }
            if (searcherComment.getContent() != null) {
                builder.field("content", searcherComment.getContent());
            }
            if (searcherComment.getTel() != null) {
                builder.field("tel", searcherComment.getTel());
            }
            if (searcherComment.getTitle() != null) {
                builder.field("title", searcherComment.getTitle());
            }
            if (searcherComment.getPic() != null) {
                builder.field("pic", searcherComment.getPic());
            }
            if (searcherComment.getProductImg() != null) {
                builder.field("productImg", searcherComment.getProductImg());
            }
            if (searcherComment.getNickName() != null) {
                builder.field("nickName", searcherComment.getNickName());
            }
            if (searcherComment.getProductScore() != null) {
                builder.field("productScore", searcherComment.getProductScore());
            }
            if (searcherComment.getPackageScore() != null) {
                builder.field("packageScore", searcherComment.getPackageScore());
            }
            if (searcherComment.getDeliveryServiceScore() != null) {
                builder.field("deliveryServiceScore", searcherComment.getDeliveryServiceScore());
            }
            if (searcherComment.getSkuProperty() != null) {
                builder.field("skuProperty", searcherComment.getSkuProperty());
            }
            if (searcherComment.getProductId() != null) {
                builder.field("productId", searcherComment.getProductId());
            }
            if (searcherComment.getProductSkuId() != null) {
                builder.field("productSkuId", searcherComment.getProductSkuId());
            }
            if (searcherComment.getDeliverySpeedScore() != null) {
                builder.field("deliverySpeedScore", searcherComment.getDeliverySpeedScore());
            }
            if (searcherComment.getVerified() != null) {
                builder.field("verified", searcherComment.getVerified());
            }
            if (searcherComment.getHeadPic() != null) {
                builder.field("headPic", searcherComment.getHeadPic());
            }
            if (searcherComment.getRecomend() != null) {
                builder.field("recomend", searcherComment.getRecomend());
                if (searcherComment.getRecomend().intValue() == 1) {
                    builder.field("recomendDate", ElasticSearchConfig.transDate2Long(new Date()));
                } else {
                    SearcherComment tmpSearcherComment = this.findById(searcherComment.getId().toString());
                    builder.field("recomendDate",
                            ElasticSearchConfig.transDate2Long(tmpSearcherComment.getCreateDate()));
                }
            }
            builder.field("modifyDate", ElasticSearchConfig.transDate2Long(new Date()));
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENT, searcherComment.getId().toString())
                    .setDoc(builder).execute().actionGet();
            return 1;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return 0;
    }

    @Override
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_COMMENT).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("memberId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            Long commentId = Long.valueOf(hit.getId());
            if (!source.isEmpty()) {
                SearcherComment comment = new SearcherComment();
                ElasticSearchConfig.transMap2Bean(source, comment);
                comment.setId(commentId);
                UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENT, comment.getId().toString())
                        .setDoc("headPic", headPic);
                bulkRequest.add(headPicRequest);
                UpdateRequestBuilder nickNameRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENT, comment.getId().toString())
                        .setDoc("nickname", nickName);
                bulkRequest.add(nickNameRequest);
            }
        }
        bulkRequest.execute().actionGet();
    }

    @Override
    public int rebuild(SearcherComment comment) {
        String id = comment.getId().toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENT, id).execute()
                .actionGet();
        insert(comment);
        return 1;
    }

    @Override
    public int remove(Long commentId) {
        String id = commentId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENT, id).execute()
                .actionGet();
        logger.info("ID={}的评论已删除索引!", id);
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_COMMENT)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public int count(CommentSearchBean search) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_COMMENT).setSearchType(SearchType.DEFAULT);
        buildBoolFilterBuilder(builder, search);
        SearchResponse response = builder.setSearchType(SearchType.COUNT).execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    private void buildBoolFilterBuilder(SearchRequestBuilder builder, CommentSearchBean searcher) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (searcher.getMemberId() != null && searcher.getAllList() == true) {
            boolQuery.should(QueryBuilders.termQuery("memberId", searcher.getMemberId()))
                    .should(QueryBuilders.termQuery("verified", 1));
            builder.setQuery(boolQuery);// TODO
        } else if (searcher.getMemberId() != null) {
            boolFilter.must(FilterBuilders.termFilter("memberId", searcher.getMemberId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getVerified() != null && searcher.getAllList() == false) {
            boolFilter.must(FilterBuilders.termFilter("verified", searcher.getVerified()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getSource() != null) {
            boolFilter.must(FilterBuilders.termFilter("source", searcher.getSource().toLowerCase()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getProductId() != null) {
            boolFilter.must(FilterBuilders.termFilter("productId", searcher.getProductId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getHasPic() != null && searcher.getHasPic() == 0) {
            boolFilter.mustNot(FilterBuilders.existsFilter("pic"));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getHasPic() != null && searcher.getHasPic() == 1) {
            boolFilter.must(FilterBuilders.existsFilter("pic"));
            builder.setPostFilter(boolFilter);
        }
    }

}
