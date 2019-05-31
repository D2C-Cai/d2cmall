package com.d2c.content.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.content.search.model.SearcherSensitiveWords;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.util.string.StringUtil;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Service(protocol = "dubbo")
public class SensitiveWordsSearcherServiceImpl implements SensitiveWordsSearcherService {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveWordsSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherSensitiveWords ssw) {
        this.remove(ssw.getId());
        IndexRequestBuilder builder = elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                TYPE_SEACHER, ssw.getId().toString());
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject().field("sswId", ssw.getId())
                    .field("keyword", ssw.getKeyword()).endObject();
            builder.setSource(content);
            builder.execute().actionGet();
            logger.info("keyword={}索引建立成功！", ssw.getKeyword());
            return 1;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return 0;
    }

    @Override
    public int update(SearcherSensitiveWords ssw) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", ssw.getId());
            if (StringUtil.isNotBlank(ssw.getKeyword())) {
                builder.field("keyword", ssw.getKeyword());
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_SEACHER, ssw.getId().toString()).setDoc(builder)
                    .execute().actionGet();
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherSensitiveWords ssw) {
        this.remove(ssw.getId());
        this.insert(ssw);
        return 0;
    }

    @Override
    public int remove(Long id) {
        String sensitiveWord = id.toString();
        DeleteResponse result = elasticSearchConfig.getClient()
                .prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_SEACHER, sensitiveWord).execute().actionGet();
        if (result.isFound()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_SEACHER)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        return 1;
    }

}
