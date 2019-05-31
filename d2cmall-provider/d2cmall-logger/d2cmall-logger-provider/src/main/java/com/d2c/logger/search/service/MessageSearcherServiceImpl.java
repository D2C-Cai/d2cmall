package com.d2c.logger.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.logger.search.model.SearcherMessage;
import com.d2c.logger.search.support.MessageReadTimeHelp;
import com.d2c.logger.search.support.MessageSearchHelp;
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
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

@Service(protocol = "dubbo")
public class MessageSearcherServiceImpl implements MessageSearcherService {

    private static final Log logger = LogFactory.getLog(MessageSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherMessage message) {
        try {
            if (message.getStatus() == null)
                return 0;
            // 如果是非上架状态不建立索引
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, message);
            builder.field("majorType", message.getType() / 10);
            builder.endObject();
            elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                    MessageSearcherService.TYPE_MESSAGE, message.getId().toString()).setSource(builder).execute()
                    .actionGet();
            logger.info("ID为" + message.getId() + "的消息建立索引成功!标签：" + message.getTitle());
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
    public SearcherMessage findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_MESSAGE, id).execute().actionGet();
        SearcherMessage message = new SearcherMessage();
        if (response != null && response.isExists()) {
            String messageId = String.valueOf(response.getId());
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, message);
                message.setId(Long.parseLong(messageId));
                return message;
            }
        }
        return message;
    }

    public List<MessageSearchHelp> searchGroupByMajorType(Long recId, MessageReadTimeHelp readTime) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MESSAGE);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("agg").field("majorType")
                .order(Terms.Order.aggregation("newMessage", false))
                .subAggregation(AggregationBuilders.max("newMessage").field("createDate"))
                .subAggregation(AggregationBuilders.topHits("top").addSort("createDate", SortOrder.DESC).setSize(1));
        SearchResponse response = responsebuilder
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("recId", new long[]{0L, recId})))
                .addAggregation(aggregation).setExplain(true).execute().actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms gradeTerms = (Terms) aggMap.get("agg");
        Iterator<Bucket> typeBucketIt = gradeTerms.getBuckets().iterator();
        List<MessageSearchHelp> messages = new ArrayList<MessageSearchHelp>();
        Map<Long, Long> map = new HashMap<Long, Long>();
        if (typeBucketIt.hasNext()) {
            map = searchUnReadCountGroupByMajorType(recId);
        }
        while (typeBucketIt.hasNext()) {
            Bucket buck = typeBucketIt.next();
            MessageSearchHelp messageSearchHelp = new MessageSearchHelp();
            messageSearchHelp.setMajorType(Long.parseLong(buck.getKey()));
            Long majorType = Long.parseLong(buck.getKey());
            messageSearchHelp.setUnReadCount(map.get(majorType) == null ? 0 : map.get(majorType));
            if (majorType == 6) {
                messageSearchHelp.setUnReadCount(searchUnReadCountByMajorType(recId, 6L, readTime.getMajorTypeTime6()));
            } else if (majorType == 7) {
                messageSearchHelp.setUnReadCount(searchUnReadCountByMajorType(recId, 7L, readTime.getMajorTypeTime7()));
            }
            TopHits topHits = buck.getAggregations().get("top");
            for (SearchHit hit : topHits.getHits().getHits()) {
                Long messageId = Long.valueOf(hit.getId());
                Map<String, Object> source = hit.getSource();
                if (!source.isEmpty()) {
                    SearcherMessage message = new SearcherMessage();
                    ElasticSearchConfig.transMap2Bean(source, message);
                    message.setId(messageId);
                    messageSearchHelp.setSearcherMessage(message);
                }
            }
            messages.add(messageSearchHelp);
        }
        return messages;
    }

    private Map<Long, Long> searchUnReadCountGroupByMajorType(Long recId) {
        SearchRequestBuilder responsebuilder = elasticSearchConfig.getClient()
                .prepareSearch(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MESSAGE).setSearchType(SearchType.COUNT);
        AggregationBuilder<?> aggregation = AggregationBuilders.terms("typeAgg").field("majorType");
        SearchResponse response = responsebuilder
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("recId", new long[]{recId, 0L}))
                        .must(QueryBuilders.matchPhraseQuery("status", 0)))
                .addAggregation(aggregation).execute().actionGet();
        Map<String, Aggregation> aggMap = response.getAggregations().asMap();
        Terms agg = (Terms) aggMap.get("typeAgg");
        Iterator<Bucket> typeBucketIt = agg.getBuckets().iterator();
        Map<Long, Long> map = new HashMap<Long, Long>();
        while (typeBucketIt.hasNext()) {
            Bucket buck = typeBucketIt.next();
            map.put(Long.parseLong(buck.getKey()), buck.getDocCount());
        }
        return map;
    }

    private Long searchUnReadCountByMajorType(Long recId, Long majorType, Date lastReadTime) {
        if (lastReadTime == null) {
            lastReadTime = new Date();
        }
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MESSAGE).setSearchType(SearchType.COUNT).setQuery(QueryBuilders.matchAllQuery());
        SearchResponse response = builder
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termsQuery("recId", new long[]{recId, 0L}))
                        .must(QueryBuilders.matchPhraseQuery("status", 0))
                        .must(QueryBuilders.matchPhraseQuery("majorType", majorType))
                        .must(QueryBuilders.rangeQuery("createDate").gt(lastReadTime.getTime())))
                .execute().actionGet();
        return (long) response.getHits().getTotalHits();
    }

    @Override
    public PageResult<SearcherMessage> search(Long recId, Long majorType, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MESSAGE).setSearchType(SearchType.DEFAULT).setQuery(QueryBuilders.matchAllQuery());
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (recId != null) {
            boolFilter.must(FilterBuilders.inFilter("recId", new long[]{recId, 0L}));
            builder.setPostFilter(boolFilter);
        }
        if (majorType != null) {
            boolFilter.must(FilterBuilders.termFilter("majorType", majorType));
            builder.setPostFilter(boolFilter);
        }
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMessage> messages = new ArrayList<SearcherMessage>();
        for (SearchHit hit : response.getHits()) {
            Long messageId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMessage message = new SearcherMessage();
                ElasticSearchConfig.transMap2Bean(source, message);
                message.setId(messageId);
                messages.add(message);
            }
        }
        PageResult<SearcherMessage> pager = new PageResult<SearcherMessage>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(messages);
        return pager;
    }

    @Override
    public int count(Long recId, Integer status, MessageReadTimeHelp readTime) {
        int count = 0;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MESSAGE).setSearchType(SearchType.COUNT).setQuery(QueryBuilders.matchAllQuery());
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (recId != null) {
            boolFilter.must(FilterBuilders.inFilter("recId", new long[]{recId, 0L}));
            builder.setPostFilter(boolFilter);
        }
        if (status != null) {
            boolFilter.must(FilterBuilders.termFilter("status", status));
            builder.setPostFilter(boolFilter);
        }
        if (status != null && status == 0 && readTime != null) {
            boolFilter.mustNot(FilterBuilders.inFilter("majorType", new long[]{6L, 7L}));
            builder.setPostFilter(boolFilter);
            count += searchUnReadCountByMajorType(recId, 6L, readTime.getMajorTypeTime6());
            count += searchUnReadCountByMajorType(recId, 7L, readTime.getMajorTypeTime7());
        }
        SearchResponse response = builder.execute().actionGet();
        count += (int) response.getHits().getTotalHits();
        return count;
    }

    @Override
    public PageResult<SearcherMessage> searchByType(Long recId, long[] types, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MESSAGE).setSearchType(SearchType.DEFAULT).setQuery(QueryBuilders.matchAllQuery());
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termsFilter("type", types));
        boolFilter.must(FilterBuilders.termFilter("recId", recId));
        builder.setPostFilter(boolFilter);
        builder.addSort("createDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherMessage> messages = new ArrayList<SearcherMessage>();
        for (SearchHit hit : response.getHits()) {
            Long messageId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherMessage message = new SearcherMessage();
                ElasticSearchConfig.transMap2Bean(source, message);
                message.setId(messageId);
                messages.add(message);
            }
        }
        PageResult<SearcherMessage> pager = new PageResult<SearcherMessage>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(messages);
        return pager;
    }

    @Override
    public int countUnReadByType(Long recId, long[] types) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MESSAGE).setSearchType(SearchType.COUNT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termsFilter("type", types));
        boolFilter.must(FilterBuilders.termFilter("recId", recId));
        boolFilter.must(FilterBuilders.termFilter("status", 0));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    @Override
    public int updateStatus(Long messageId, Integer status) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", messageId);
            if (status != null) {
                builder.field("status", status);
            }
            builder.endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME,
                    MessageSearcherService.TYPE_MESSAGE, messageId.toString()).setDoc(builder).execute().actionGet();
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherMessage message) {
        this.remove(message.getId());
        return insert(message);
    }

    @Override
    public int remove(Long messageId) {
        String id = messageId.toString();
        elasticSearchConfig.getClient()
                .prepareDelete(ElasticSearchConfig.INDEX_NAME, MessageSearcherService.TYPE_MESSAGE, id).execute()
                .actionGet();
        logger.info("ID为" + id + "的消息已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MESSAGE)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public int doReadByMajor(Long memberId, Integer majorType) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("recId", memberId));
        boolFilter.must(FilterBuilders.termFilter("status", 0));
        boolFilter.must(FilterBuilders.termFilter("majorType", majorType));
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_MESSAGE).setSearchType(SearchType.DEFAULT).setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(3000).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        if (response.getHits().getTotalHits() > 0) {
            for (SearchHit hit : response.getHits()) {
                Map<String, Object> source = hit.getSource();
                if (!source.isEmpty()) {
                    Long messageId = Long.valueOf(hit.getId());
                    UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                            .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_MESSAGE, messageId.toString())
                            .setDoc("status", 1);
                    bulkRequest.add(headPicRequest);
                }
            }
            bulkRequest.execute().actionGet();
        }
        return 1;
    }

    @Override
    public int doDeleteExpire(Date date) {
        if (date == null)
            return 0;
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_MESSAGE)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("createDate").lt(date.getTime())))
                .execute().actionGet();
        return 1;
    }

}
