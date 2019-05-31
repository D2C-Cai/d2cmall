package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherCommentReply;
import com.d2c.member.search.query.CommentSearchBean;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class CommentReplySearcherServiceImpl implements CommentReplySearcherService {

    private static final Logger logger = LoggerFactory.getLogger(CommentReplySearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherCommentReply commentReply) {
        try {
            if (commentReply == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, commentReply);
            builder.endObject();
            elasticSearchConfig
                    .getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                    CommentReplySearcherService.TYPE_COMMENTREPLY, commentReply.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("id={},content={},建立成功", commentReply.getId(), commentReply.getContent());
            return 1;
        } catch (ElasticsearchException e) {
            logger.error("Elasticsearch Exception: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SearcherCommentReply findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENTREPLY, id).execute().actionGet();
        SearcherCommentReply reply = null;
        if (response != null && response.isExists()) {
            Long desingerId = Long.valueOf(response.getId());
            reply = new SearcherCommentReply();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, reply);
                reply.setId(desingerId);
            }
        }
        return reply;
    }

    @Override
    public PageResult<SearcherCommentReply> search(CommentSearchBean search, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_COMMENTREPLY).setSearchType(SearchType.DEFAULT)
                .setTimeout(TimeValue.timeValueMinutes(1));
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (search.getCommentIds() != null) {
            boolFilter.must(FilterBuilders.termsFilter("commentId", search.getCommentIds()));
            builder.setPostFilter(boolFilter);
        }
        builder.addSort("createDate", SortOrder.ASC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherCommentReply> replys = new ArrayList<SearcherCommentReply>();
        for (SearchHit hit : response.getHits()) {
            Long desingerId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherCommentReply designer = new SearcherCommentReply();
                ElasticSearchConfig.transMap2Bean(source, designer);
                designer.setId(desingerId);
                replys.add(designer);
            }
        }
        PageResult<SearcherCommentReply> pager = new PageResult<SearcherCommentReply>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(replys);
        return pager;
    }

    @Override
    public int update(SearcherCommentReply commentReply) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", commentReply.getId());
            if (commentReply.getContent() != null) {
                builder.field("content", commentReply.getContent());
            }
            if (commentReply.getVerified() != null) {
                builder.field("verified", commentReply.getVerified());
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENTREPLY, commentReply.getId().toString())
                    .setDoc(builder).execute().actionGet();
            return 1;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherCommentReply commentReply) {
        this.remove(commentReply.getId());
        insert(commentReply);
        return 0;
    }

    @Override
    public int remove(Long commentReplyId) {
        String id = commentReplyId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_COMMENTREPLY, id).execute()
                .actionGet();
        logger.info("ID={}的评论已删除索引!", id);
        return 1;
    }

    @Override
    public void removeAll() {
    }

}
