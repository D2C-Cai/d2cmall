package com.d2c.member.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.member.search.model.SearcherConsult;
import com.d2c.member.search.query.ConsultSearchBean;
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
public class ConsultsSearcherServiceImpl implements ConsultSearcherService {

    private static final Logger logger = LoggerFactory.getLogger(ConsultsSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherConsult bean) {
        try {
            if (bean == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            bean.setRecomendDate(bean.getRecomendDate() == null ? bean.getCreateDate() : bean.getRecomendDate());
            ElasticSearchConfig.buildContent(builder, bean);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_CONSULT, bean.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("id={},建立成功", bean.getId());
            return 1;
        } catch (ElasticsearchException e) {
            logger.error("Elasticsearch Exception: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public SearcherConsult findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_CONSULT, id).execute().actionGet();
        SearcherConsult SearcherConsult = null;
        if (response != null && response.isExists()) {
            Long desingerId = Long.valueOf(response.getId());
            SearcherConsult = new SearcherConsult();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, SearcherConsult);
                SearcherConsult.setId(desingerId);
            }
        }
        return SearcherConsult;
    }

    @Override
    public int countByProductId(Long productId) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_CONSULT).setSearchType(SearchType.DEFAULT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("status", 2));
        builder.setPostFilter(boolFilter);
        boolFilter.must(FilterBuilders.termFilter("productId", productId));
        builder.setPostFilter(boolFilter);
        SearchResponse response = builder.setSearchType(SearchType.COUNT).execute().actionGet();
        return (int) response.getHits().getTotalHits();
    }

    @Override
    public PageResult<SearcherConsult> search(ConsultSearchBean searcher, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_CONSULT).setSearchType(SearchType.DEFAULT).setTimeout(TimeValue.timeValueMinutes(1));
        buildBoolFilterBuilder(builder, searcher);
        builder.addSort("recomend", SortOrder.DESC);
        builder.addSort("recomendDate", SortOrder.DESC);
        SearchResponse response = builder.setFrom((page.getPageNumber() - 1) * page.getPageSize())
                .setSize(page.getPageSize()).setExplain(true).execute().actionGet();
        List<SearcherConsult> consults = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long consultId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherConsult searcherConsult = new SearcherConsult();
                ElasticSearchConfig.transMap2Bean(source, searcherConsult);
                searcherConsult.setId(consultId);
                consults.add(searcherConsult);
            }
        }
        PageResult<SearcherConsult> pager = new PageResult<SearcherConsult>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(consults);
        return pager;
    }

    /**
     * 查询条件
     */
    private void buildBoolFilterBuilder(SearchRequestBuilder builder, ConsultSearchBean searcher) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (searcher.getProductId() != null) {
            boolFilter.must(FilterBuilders.termFilter("productId", searcher.getProductId()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getStatus() != null) {
            boolFilter.must(FilterBuilders.termFilter("status", String.valueOf(searcher.getStatus())));
            builder.setPostFilter(boolFilter);
        }
        builder.setPostFilter(boolFilter);
    }

    @Override
    public int update(SearcherConsult searcherConsult) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", searcherConsult.getId());
            if (searcherConsult.getReply() != null) {
                builder.field("reply", searcherConsult.getReply());
            }
            if (searcherConsult.getReplyDate() != null) {
                builder.field("replyDate", ElasticSearchConfig.transDate2Long(searcherConsult.getReplyDate()));
            }
            if (searcherConsult.getLastModifyMan() != null) {
                builder.field("lastModifyMan", searcherConsult.getLastModifyMan());
            }
            if (searcherConsult.getStatus() != null) {
                builder.field("status", searcherConsult.getStatus());
            }
            if (searcherConsult.getRecomend() != null) {
                builder.field("recomend", searcherConsult.getRecomend());
                if (searcherConsult.getRecomend().intValue() == 1) {
                    builder.field("recomendDate", ElasticSearchConfig.transDate2Long(new Date()));
                } else {
                    SearcherConsult tepSearcherConsult = this.findById(searcherConsult.getId().toString());
                    builder.field("recomendDate",
                            ElasticSearchConfig.transDate2Long(tepSearcherConsult.getCreateDate()));
                }
            }
            builder.field("modifyDate", ElasticSearchConfig.transDate2Long(new Date()));
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_CONSULT, searcherConsult.getId().toString())
                    .setDoc(builder).execute().actionGet();
            return 1;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return 0;
    }

    @Override
    public void updateStatusByIds(Long[] ids, int status) {
        try {
            for (Long id : ids) {
                XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", id);
                builder.field("status", status);
                builder.field("modifyDate", ElasticSearchConfig.transDate2Long(new Date()));
                builder.endObject();
                elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_CONSULT, id.toString()).setDoc(builder)
                        .execute().actionGet();
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
    }

    @Override
    public int rebuild(SearcherConsult SearcherConsult) {
        String id = SearcherConsult.getId().toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_CONSULT, id).execute()
                .actionGet();
        insert(SearcherConsult);
        return 1;
    }

    @Override
    public void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_CONSULT).setSearchType(SearchType.DEFAULT)
                .setPostFilter(FilterBuilders.termFilter("memberId", memberInfoId));
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT).setSize(100).execute().actionGet();
        BulkRequestBuilder bulkRequest = elasticSearchConfig.getClient().prepareBulk();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            Long consultId = Long.valueOf(hit.getId());
            if (!source.isEmpty()) {
                SearcherConsult consult = new SearcherConsult();
                ElasticSearchConfig.transMap2Bean(source, consult);
                consult.setId(consultId);
                UpdateRequestBuilder headPicRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_CONSULT, consult.getId().toString())
                        .setDoc("headPic", headPic);
                bulkRequest.add(headPicRequest);
                UpdateRequestBuilder nickNameRequest = elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_CONSULT, consult.getId().toString())
                        .setDoc("nickname", nickName);
                bulkRequest.add(nickNameRequest);
            }
        }
        bulkRequest.execute().actionGet();
    }

    @Override
    public int remove(Long searcherConsultId) {
        String id = searcherConsultId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_CONSULT, id).execute()
                .actionGet();
        logger.info("ID={}的评论已删除索引!", id);
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_CONSULT)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

}
