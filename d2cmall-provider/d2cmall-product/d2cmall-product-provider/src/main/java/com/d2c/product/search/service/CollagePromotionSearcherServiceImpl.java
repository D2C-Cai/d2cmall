package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.product.search.model.SearcherCollagePromotion;
import com.d2c.product.search.query.CollagePromotionSearcherBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class CollagePromotionSearcherServiceImpl implements CollagePromotionSearcherService {

    private static final Log logger = LogFactory.getLog(CollagePromotionSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    @Override
    public int insert(SearcherCollagePromotion collagePromotion) {
        IndexRequestBuilder builder = elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                TYPE_COLLAGE, collagePromotion.getId().toString());
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(content, collagePromotion);
            content.endObject();
            builder.setSource(content);
            builder.execute().actionGet();
            logger.info("ID为" + collagePromotion.getId() + "的模块建立索引成功! Name:" + collagePromotion.getName());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherCollagePromotion collagePromotion) {
        String id = collagePromotion.getId().toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_COLLAGE, id).execute()
                .actionGet();
        this.insert(collagePromotion);
        return 1;
    }

    @Override
    public PageResult<SearcherCollagePromotion> search(CollagePromotionSearcherBean searcher, PageModel page) {
        // 列表取上架的即将开始和进行中的,结束时间>现在
        QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("endDate").gt(new Date().getTime()));
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_COLLAGE).setSearchType(SearchType.DEFAULT).setQuery(qb);
        buildBoolFilterBuilder(builder, searcher);
        builder = builder.setSearchType(SearchType.DEFAULT).setFrom((page.getPageNumber() - 1) * page.getPageSize())
                .setSize(page.getPageSize()).setExplain(true);
        SearchResponse response = builder.execute().actionGet();
        List<SearcherCollagePromotion> list = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long PromotionId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherCollagePromotion promotion = new SearcherCollagePromotion();
                ElasticSearchConfig.transMap2Bean(source, promotion);
                promotion.setId(PromotionId);
                list.add(promotion);
            }
        }
        PageResult<SearcherCollagePromotion> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(list);
        return pager;
    }

    private void buildBoolFilterBuilder(SearchRequestBuilder builder, CollagePromotionSearcherBean searcher) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("status", 1));
        builder.setPostFilter(boolFilter);
        builder.addSort("sort", SortOrder.DESC);
    }

    @Override
    public int removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_COLLAGE)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        return 1;
    }

    @Override
    public int remove(Long id) {
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_COLLAGE, id.toString())
                .execute().actionGet();
        logger.info("ID为" + id + "的活动已删除索引!");
        return 1;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("status", status).endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_COLLAGE, id.toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + id + "的商品状态更新成功! status:" + status);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("sort", sort).endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_COLLAGE, id.toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + id + "的排序更新成功! sort:" + sort);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public SearcherCollagePromotion findById(Long id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_COLLAGE, id.toString()).execute().actionGet();
        SearcherCollagePromotion collagePromotion = new SearcherCollagePromotion();
        if (response != null && response.isExists()) {
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, collagePromotion);
                collagePromotion.setId(id);
            }
        }
        return collagePromotion;
    }

}
