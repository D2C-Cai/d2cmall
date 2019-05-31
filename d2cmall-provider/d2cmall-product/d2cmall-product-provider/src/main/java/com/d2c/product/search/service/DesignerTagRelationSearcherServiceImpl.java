package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.product.search.model.SearcherDesignerTagRelation;
import com.d2c.product.search.query.DesignerSearchBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
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
public class DesignerTagRelationSearcherServiceImpl implements DesignerTagRelationSearcherService {

    private static final Log logger = LogFactory.getLog(DesignerTagRelationSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherDesignerTagRelation relation) {
        try {
            if (relation == null)
                return 0;
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(builder, relation);
            builder.endObject();
            elasticSearchConfig.getClient()
                    .prepareIndex(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER_TAG_R,
                            relation.getDesignerId() + "_" + relation.getTagId())
                    .setSource(builder).execute().actionGet();
            logger.info("ID为" + relation.getDesignerId() + "的设计师建立索引成功!brand：" + relation.getDesignerId());
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
    public SearcherDesignerTagRelation findById(Long designerId, Long tagId) {
        String id = designerId + "_" + tagId;
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER_TAG_R, id).execute().actionGet();
        SearcherDesignerTagRelation relation = null;
        if (response != null && response.isExists()) {
            relation = new SearcherDesignerTagRelation();
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, relation);
            }
        }
        return relation;
    }

    @Override
    public PageResult<SearcherDesignerTagRelation> search(DesignerSearchBean search, PageModel page) {
        QueryBuilder qb = null;
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_DESIGNER_TAG_R).setSearchType(SearchType.DEFAULT).setQuery(qb);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (search.getTagId() != null) {
            boolFilter.must(FilterBuilders.termFilter("tagId", search.getTagId()));
            builder.setPostFilter(boolFilter);
        }
        if (search.getDesignerId() != null) {
            boolFilter.must(FilterBuilders.termFilter("designerId", search.getDesignerId()));
            builder.setPostFilter(boolFilter);
        }
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<SearcherDesignerTagRelation> relations = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherDesignerTagRelation relation = new SearcherDesignerTagRelation();
                ElasticSearchConfig.transMap2Bean(source, relation);
                relations.add(relation);
            }
        }
        PageResult<SearcherDesignerTagRelation> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(relations);
        return pager;
    }

    @Override
    public int updateSort(SearcherDesignerTagRelation relation) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if (relation.getSort() != null) {
                builder.field("sort", relation.getSort());
            }
            builder.endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER_TAG_R,
                    relation.getDesignerId() + "_" + relation.getTagId()).setDoc(builder).execute().actionGet();
            logger.info("ID为" + relation.getDesignerId() + "的设计师更新排序成功!brand：" + relation.getDesignerId());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherDesignerTagRelation relation) {
        remove(relation.getDesignerId(), relation.getTagId());
        return insert(relation);
    }

    @Override
    public int remove(Long designerId, Long tagId) {
        String id = designerId + "_" + tagId;
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_DESIGNER_TAG_R, id).execute()
                .actionGet();
        logger.info("ID为" + id + "的设计师已删除索引!");
        return 1;
    }

    @Override
    public int remove(Long designerId) {
        if (designerId == null)
            return 0;
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_DESIGNER_TAG_R)
                .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("designerId", designerId))).execute()
                .actionGet();
        return 1;
    }

    @Override
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_DESIGNER_TAG_R).setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public PageResult<String> findDesignerByTagId(Long tagId, PageModel page) {
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_DESIGNER_TAG_R).setSearchType(SearchType.DEFAULT);
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        if (tagId != null) {
            boolFilter.must(FilterBuilders.termFilter("tagId", tagId));
            builder.setPostFilter(boolFilter);
        }
        builder.addSort("sort", SortOrder.DESC);
        builder.addSort("designerId", SortOrder.DESC);
        SearchResponse response = builder.setSearchType(SearchType.DEFAULT)
                .setFrom((page.getPageNumber() - 1) * page.getPageSize()).setSize(page.getPageSize()).setExplain(true)
                .execute().actionGet();
        List<String> relations = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherDesignerTagRelation relation = new SearcherDesignerTagRelation();
                ElasticSearchConfig.transMap2Bean(source, relation);
                relations.add(relation.getDesignerId().toString());
            }
        }
        PageResult<String> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(relations);
        return pager;
    }

}
