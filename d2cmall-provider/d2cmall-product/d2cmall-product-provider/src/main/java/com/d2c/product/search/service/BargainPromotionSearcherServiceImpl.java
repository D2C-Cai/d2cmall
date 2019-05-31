package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.product.search.model.SearcherBargainPromotion;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.BargainPromotionSearchBean;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
public class BargainPromotionSearcherServiceImpl implements BargainPromotionSearcherService {

    private static final Log logger = LogFactory.getLog(BargainPromotionSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    @Override
    public int insert(SearcherBargainPromotion bargainPromotion) {
        IndexRequestBuilder builder = elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                TYPE_BARGAIN, bargainPromotion.getId().toString());
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject();
            ElasticSearchConfig.buildContent(content, bargainPromotion);
            content.endObject();
            builder.setSource(content);
            builder.execute().actionGet();
            logger.info("ID为" + bargainPromotion.getId() + "的模块建立索引成功! Name:" + bargainPromotion.getName());
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int rebuild(SearcherBargainPromotion bargainPromotion) {
        String id = bargainPromotion.getId().toString();
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_BARGAIN, id).execute()
                .actionGet();
        this.insert(bargainPromotion);
        return 1;
    }

    @Override
    public PageResult<SearcherBargainPromotion> search(BargainPromotionSearchBean searcher, PageModel page) {
        QueryBuilder qb = null;
        if (searcher.getStatus() != null) {
            Date now = new Date();
            // 即将开始
            if (searcher.getStatus() == 0) {
                qb = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("beginDate").gt(now.getTime()));
            } else if (searcher.getStatus() == 1) {
                // 进行中
                qb = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery("beginDate").lte(now.getTime()))
                        .must(QueryBuilders.rangeQuery("endDate").gte(now.getTime()));
            }
        }
        SearchRequestBuilder builder = elasticSearchConfig.getClient().prepareSearch(ElasticSearchConfig.INDEX_NAME)
                .setTypes(TYPE_BARGAIN).setSearchType(SearchType.DEFAULT).setQuery(qb);
        buildBoolFilterBuilder(builder, searcher);
        builder.addSort("sort", SortOrder.DESC);
        builder = builder.setSearchType(SearchType.DEFAULT).setFrom((page.getPageNumber() - 1) * page.getPageSize())
                .setSize(page.getPageSize()).setExplain(true);
        SearchResponse response = builder.execute().actionGet();
        List<SearcherBargainPromotion> list = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            Long PromotionId = Long.valueOf(hit.getId());
            Map<String, Object> source = hit.getSource();
            if (!source.isEmpty()) {
                SearcherBargainPromotion bargain = new SearcherBargainPromotion();
                ElasticSearchConfig.transMap2Bean(source, bargain);
                bargain.setId(PromotionId);
                if (bargain.getProductId() != null) {
                    SearcherProduct product = productSearcherQueryService.findById(bargain.getProductId().toString());
                    bargain.setProduct(product);
                }
                list.add(bargain);
            }
        }
        PageResult<SearcherBargainPromotion> pager = new PageResult<>(page);
        pager.setTotalCount(Long.valueOf(response.getHits().getTotalHits()).intValue());
        pager.setList(list);
        return pager;
    }

    private void buildBoolFilterBuilder(SearchRequestBuilder builder, BargainPromotionSearchBean searcher) {
        BoolFilterBuilder boolFilter = FilterBuilders.boolFilter();
        boolFilter.must(FilterBuilders.termFilter("mark", 1));
        builder.setPostFilter(boolFilter);
    }

    @Override
    public int removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_BARGAIN)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        return 1;
    }

    @Override
    public int remove(Long id) {
        elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_BARGAIN, id.toString())
                .execute().actionGet();
        logger.info("ID为" + id + "的活动已删除索引!");
        return 1;
    }

    @Override
    public int updateMark(Long id, Integer mark) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("mark", mark).endObject();
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_BARGAIN, id.toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + id + "的商品状态更新成功! Mark:" + mark);
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
            elasticSearchConfig.getClient().prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_BARGAIN, id.toString())
                    .setDoc(builder).execute().actionGet();
            logger.info("ID为" + id + "的商品排序更新成功! sort:" + sort);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateCount(Long id) {
        SearcherBargainPromotion bargain = this.findById(id);
        if (bargain != null) {
            XContentBuilder builder;
            try {
                builder = XContentFactory.jsonBuilder().startObject().field("actualMan", bargain.getActualMan() + 1)
                        .endObject();
                elasticSearchConfig.getClient()
                        .prepareUpdate(ElasticSearchConfig.INDEX_NAME, TYPE_BARGAIN, id.toString()).setDoc(builder)
                        .execute().actionGet();
                logger.info("ID为" + id + "的商品添加砍价人数成功! 当前砍价人数为:" + bargain.getActualMan() + 1);
                return 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public SearcherBargainPromotion findById(Long id) {
        GetResponse response = elasticSearchConfig.getClient()
                .prepareGet(ElasticSearchConfig.INDEX_NAME, TYPE_BARGAIN, id.toString()).execute().actionGet();
        SearcherBargainPromotion bargain = new SearcherBargainPromotion();
        if (response != null && response.isExists()) {
            String promotionId = String.valueOf(response.getId());
            Map<String, Object> source = response.getSource();
            if (source != null && !source.isEmpty()) {
                ElasticSearchConfig.transMap2Bean(source, bargain);
                bargain.setId(Long.parseLong(promotionId));
                if (bargain.getProductId() != null) {
                    SearcherProduct product = productSearcherQueryService.findById(bargain.getProductId().toString());
                    bargain.setProduct(product);
                }
            }
        }
        return bargain;
    }

}
