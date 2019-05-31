package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherMemberArticle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service(protocol = "dubbo")
public class MemberArticleSearcherServiceImpl implements MemberArticleSearcherService {

    private static final Log logger = LogFactory.getLog(MemberArticleSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMemberArticle memberarticle) {
        try {
            if (memberarticle == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, memberarticle);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_ARTICLE,
                            memberarticle.getMemberId() + "_" + memberarticle.getArticleId())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + memberarticle.getMemberId() + "看过的文章建立索引成功!");
            return 1;
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public Map<Long, SearcherMemberArticle> findByIds(String[] ids) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_MEMBER_ARTICLE).addIds(ids)).setSize(ids.length).execute()
                .actionGet();
        Map<Long, SearcherMemberArticle> memberArticles = new HashMap<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMemberArticle memberArticle = new SearcherMemberArticle();
                ElasticSearchConfig.transMap2Bean(source, memberArticle);
                memberArticles.put(memberArticle.getArticleId(), memberArticle);
            }
        }
        return memberArticles;
    }

    @Override
    public int remove(Long memberId, Long articleId) {
        String id = memberId + "_" + articleId;
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_MEMBER_ARTICLE, id).execute()
                .actionGet();
        logger.info("ID为" + id + "的看过文章已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MEMBER_ARTICLE).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

}
