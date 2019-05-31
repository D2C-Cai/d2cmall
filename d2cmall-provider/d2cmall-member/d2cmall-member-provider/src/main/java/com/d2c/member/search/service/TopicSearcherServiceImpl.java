package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherTopic;
import com.d2c.member.search.query.TopicSearchBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class TopicSearcherServiceImpl implements TopicSearcherService {

    private static final Log logger = LogFactory.getLog(TopicSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherTopic searcherTopic) {
        try {
            if (searcherTopic == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, searcherTopic);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_TOPIC, searcherTopic.getId().toString())
                    .setSource(builder).execute().actionGet();
            return 1;
        } catch (IOException e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public SearcherTopic findById(Long id) {
        SearchResponse response = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setQuery(QueryBuilders.idsQuery(TYPE_TOPIC).addIds(id.toString())).execute().actionGet();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherTopic searcherTopic = new SearcherTopic();
                ElasticSearchConfig.transMap2Bean(source, searcherTopic);
                Long topicId = Long.valueOf(hit.getId());
                searcherTopic.setId(topicId);
                return searcherTopic;
            }
        }
        return null;
    }

    @Override
    public PageResult<SearcherTopic> search(TopicSearchBean searcher, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_TOPIC).setSearchType(SearchType.DEFAULT).setQuery(QueryBuilders.matchAllQuery());
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (searcher.getStatus() != null) {
            boolFilter.must(FilterBuilders.termFilter("status", searcher.getStatus()));
            builder.setPostFilter(boolFilter);
        }
        builder.addSort("sort", SortOrder.DESC);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherTopic> topics = new ArrayList<SearcherTopic>();
        for (SearchHit hit : response.getHits()) {
            Long topicId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherTopic topic = new SearcherTopic();
                ElasticSearchConfig.transMap2Bean(source, topic);
                topic.setId(topicId);
                topics.add(topic);
            }
        }
        PageResult<SearcherTopic> pager = new PageResult<SearcherTopic>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(topics);
        return pager;
    }

    @Override
    public int remove(Long id) {
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_TOPIC, id.toString())
                .execute().actionGet();
        logger.info("ID为" + id + "的关注设计师已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_TOPIC)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public int update(SearcherTopic topic) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if (topic.getTitle() != null) {
                builder.field("title", topic.getTitle());
            }
            if (topic.getContent() != null) {
                builder.field("content", topic.getContent());
            }
            if (topic.getSort() != null) {
                builder.field("sort", topic.getSort());
            }
            if (topic.getTop() != null) {
                builder.field("top", topic.getTop());
            }
            if (topic.getStatus() != null) {
                builder.field("status", topic.getStatus());
            }
            if (topic.getPic() != null) {
                builder.field("pic", topic.getPic());
            }
            if (topic.getUpMarketDate() != null) {
                builder.field("upMarketDate", ElasticSearchConfig.transDate2Long(topic.getUpMarketDate()));
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_TOPIC, topic.getId().toString()).setDoc(builder)
                    .execute().actionGet();
            logger.info("ID为" + topic.getId() + "的专题更新索引成功!title：" + topic.getTitle());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

}
