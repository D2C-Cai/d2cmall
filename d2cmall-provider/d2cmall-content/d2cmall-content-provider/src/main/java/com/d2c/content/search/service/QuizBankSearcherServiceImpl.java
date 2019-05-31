package com.d2c.content.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.search.model.SearcherQuizBank;
import com.d2c.content.search.query.QuizBankSearchBean;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class QuizBankSearcherServiceImpl implements QuizBankSearcherService {

    private static final Log logger = LogFactory.getLog(QuizBankSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherQuizBank searcherQuizBank) {
        IndexRequestBuilder builder = elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                TYPE_QUIZ, searcherQuizBank.getId().toString());
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(content, searcherQuizBank);
            content.endObject();
            builder.setSource(content);
            builder.execute().actionGet();
            logger.info("ID为" + searcherQuizBank.getId() + "的模块建立索引成功! Name:" + searcherQuizBank.getTitle());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public PageResult<SearcherQuizBank> search(QuizBankSearchBean searcher, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_QUIZ).setSearchType(SearchType.DEFAULT).setTimeout(TimeValue.timeValueSeconds(2));
        this.buildBoolFilterBuilder(builder, searcher);
        builder.addSort("modifyDate", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherQuizBank> quizBanks = new ArrayList<SearcherQuizBank>();
        for (SearchHit hit : response.getHits()) {
            String id = String.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherQuizBank quizbank = new SearcherQuizBank();
                ElasticSearchConfig.transMap2Bean(source, quizbank);
                quizbank.setId(Long.parseLong(id));
                quizBanks.add(quizbank);
            }
        }
        PageResult<SearcherQuizBank> pager = new PageResult<SearcherQuizBank>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(quizBanks);
        return pager;
    }

    private void buildBoolFilterBuilder(SearchRequestBuilder builder, QuizBankSearchBean searcher) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (searcher.getType() != null) {
            boolFilter.must(FilterBuilders.termFilter("type", searcher.getType()));
            builder.setPostFilter(boolFilter);
        }
        if (searcher.getMark() != null) {
            boolFilter.must(FilterBuilders.termFilter("mark", searcher.getMark()));
            builder.setPostFilter(boolFilter);
        }
    }

    @Override
    public int updateQuizBank(SearcherQuizBank quizBank) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", quizBank.getId());
            builder.field("title", quizBank.getTitle());
            builder.field("pic", quizBank.getPic());
            builder.field("answer", quizBank.getAnswer());
            builder.field("choice", quizBank.getChoice());
            builder.field("type", quizBank.getType());
            builder.field("modifyDate", ElasticSearchConfig.transDate2Long(quizBank.getModifyDate()));
            builder.endObject();
            UpdateResponse updateResp = elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_QUIZ, quizBank.getId().toString())
                    .setDoc(builder).execute().actionGet();
            logger.info(updateResp.getGetResult() + "ID为" + quizBank.getId() + "的题目更新成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateStatus(Long id, int mark) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", id);
            builder.field("mark", mark);
            builder.endObject();
            UpdateResponse updateResp = elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_QUIZ, id.toString()).setDoc(builder).execute()
                    .actionGet();
            logger.info(updateResp.getGetResult() + "ID为" + id + "的题目状态更新成功! Mark:" + mark);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int remove(String id) {
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_QUIZ, id.toString())
                .execute().actionGet();
        logger.info("ID为" + id + "的题目已删除索引!");
        return 1;
    }

    @Override
    public int removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_QUIZ)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        return 1;
    }

}
