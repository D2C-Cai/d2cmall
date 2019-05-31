package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.product.search.model.SearcherProductTag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class ProductTagSearcherServiceImpl implements ProductTagSearcherService {

    private static final Log logger = LogFactory.getLog(ProductTagSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherProductTag tag) {
        try {
            if (tag == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, tag);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCT_TAG, tag.getId().toString())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + tag.getId() + "的商品标签建立索引成功!Name：" + tag.getName());
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
    public SearcherProductTag findById(String id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCT_TAG, id).execute().actionGet();
        SearcherProductTag tag = null;
        if (response != null && response.isExists()) {
            Long tagId = Long.valueOf(response.getId());
            tag = new SearcherProductTag();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, tag);
                tag.setId(tagId);
                return tag;
            }
        }
        return tag;
    }

    @Override
    public PageResult<SearcherProductTag> search(PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_PRODUCT_TAG).setSearchType(SearchType.DEFAULT).setQuery(qb);
        builder.addSort("sort", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherProductTag> tags = new ArrayList<SearcherProductTag>();
        for (SearchHit hit : response.getHits()) {
            Long tagId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherProductTag tag = new SearcherProductTag();
                ElasticSearchConfig.transMap2Bean(source, tag);
                tag.setId(tagId);
                tags.add(tag);
            }
        }
        PageResult<SearcherProductTag> pager = new PageResult<SearcherProductTag>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(tags);
        return pager;
    }

    @Override
    public int update(SearcherProductTag tag) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("id", tag.getId());
            if (tag.getName() != null) {
                builder.field("name", tag.getName());
            }
            if (tag.getSort() != null) {
                builder.field("sort", tag.getSort());
            }
            if (tag.getStatus() != null) {
                builder.field("status", tag.getStatus());
            }
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCT_TAG, tag.getId().toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + tag.getId() + "的商品标签修改索引成功!Name：" + tag.getName());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherProductTag tag) {
        remove(tag.getId());
        return insert(tag);
    }

    @Override
    public int remove(Long tagId) {
        String id = tagId.toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCT_TAG, id).execute()
                .actionGet();
        logger.info("ID为" + id + "的设计师标签已删除索引!");
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_PRODUCT_TAG)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

}
