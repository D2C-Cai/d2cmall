package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.product.search.model.SearcherSeries;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

@Service(protocol = "dubbo")
public class SeriesSearcherServiceImpl implements SeriesSearcherService {

    private static final Log logger = LogFactory.getLog(SeriesSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherSeries series) {
        XContentBuilder content;
        try {
            content = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(content, series);
            content.endObject();
            elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                    SeriesSearcherService.TYPE_SERIES, series.getId().toString()).setSource(content).execute()
                    .actionGet();
            logger.info("ID为" + series.getId() + "的系列建立索引成功!series：" + series.getName());
        } catch (IOException e) {
            logger.error(e);
        }
        return 1;
    }

    @Override
    public int rebuild(SearcherSeries series) {
        this.remove(series.getId());
        return this.insert(series);
    }

    @Override
    public int remove(Long id) {
        DeleteResponse result = elasticSearchConfig.getClient()
                .prepareDelete(ElasticSearchConfig.INDEX_NAME, SeriesSearcherService.TYPE_SERIES, id.toString())
                .execute().actionGet();
        logger.info("ID为" + id + "的系列删除成功!");
        if (result.isFound()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public SearcherSeries findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, SeriesSearcherService.TYPE_SERIES, id).execute()
                .actionGet();
        SearcherSeries series = new SearcherSeries();
        if (response != null && response.isExists()) {
            Long seriesId = Long.valueOf(response.getId());
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                try {
                    ElasticSearchConfig.transMap2Bean(source, series);
                    series.setId(seriesId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return series;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(SeriesSearcherService.TYPE_SERIES).setQuery(QueryBuilders.matchAllQuery()).execute()
                .actionGet();
    }

}
