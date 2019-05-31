package com.d2c.product.search.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.base.utils.DateUt;
import com.d2c.frame.provider.config.ElasticSearchConfig;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.model.SearcherRecProduct;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;

@Service(protocol = "dubbo")
public class ProductSearcherServiceImpl implements ProductSearcherService {

    private static final Log logger = LogFactory.getLog(ProductSearcherServiceImpl.class);
    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Override
    public int insert(SearcherProduct product) {
        this.remove(product.getId());
        IndexRequestBuilder builder = elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                TYPE_PRODUCT, product.getId().toString());
        try {
            doInsert(product, builder);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int insertRec(SearcherProduct product, Long liveId, Integer no) {
        this.removeRec(product.getId(), liveId);
        SearcherRecProduct searcherRecProduct = new SearcherRecProduct();
        searcherRecProduct.setLiveId(liveId);
        searcherRecProduct.setNo(no);
        BeanUtils.copyProperties(product, searcherRecProduct);
        IndexRequestBuilder builder = elasticSearchConfig.getClient().prepareIndex(ElasticSearchConfig.INDEX_NAME,
                TYPE_REC_PRODUCT, product.getId().toString() + "_" + liveId.toString());
        try {
            doInsertRec(searcherRecProduct, builder);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    private void doInsert(SearcherProduct product, IndexRequestBuilder builder) throws IOException {
        XContentBuilder content = XContentFactory.jsonBuilder().startObject();
        product.setProductId(product.getId());
        product.setStore((product.getStore()) > 0 ? 1 : 0);
        product.setMark(product.getMark() > 0 ? 1 : 0);
        product.setPromotionType(product.getPromotionType() == null ? -1 : product.getPromotionType());
        ElasticSearchConfig.buildContent(content, product);
        content.endObject();
        builder.setSource(content);
        builder.execute().actionGet();
        logger.info("ID为" + product.getId() + "的商品建立索引成功! Name:" + product.getName());
    }

    private void doInsertRec(SearcherRecProduct product, IndexRequestBuilder builder) throws IOException {
        XContentBuilder content = XContentFactory.jsonBuilder().startObject();
        product.setProductId(product.getId());
        product.setStore((product.getStore()) > 0 ? 1 : 0);
        product.setMark(product.getMark() > 0 ? 1 : 0);
        product.setPromotionType(product.getPromotionType() == null ? -1 : product.getPromotionType());
        ElasticSearchConfig.buildContent(content, product);
        content.endObject();
        builder.setSource(content);
        builder.execute().actionGet();
        logger.info("ID为" + product.getId() + "的商品建立索引成功! Name:" + product.getName());
    }

    @Override
    public int rebuild(SearcherProduct product) {
        this.remove(product.getId());
        return this.insert(product);
    }

    @Override
    public int update(SearcherProduct product) {
        try {
            XContentBuilder builder = makeBuilder(product.getId());
            if (product.getTopical() != null) {
                builder.field("topical", product.getTopical());
            }
            if (product.getSort() != null) {
                builder.field("sort", product.getSort());
            }
            if (product.getSales() != null) {
                builder.field("sales", product.getSales());
            }
            if (product.getComments() != null) {
                builder.field("comments", product.getComments());
            }
            if (product.getConsults() != null) {
                builder.field("consults", product.getConsults());
            }
            if (product.getTags() != null) {
                builder.field("tags", product.getTags());
            }
            if (product.getUpMarketDate() != null) {
                builder.field("upMarketDate", ElasticSearchConfig.transDate2Long(product.getUpMarketDate()));
            }
            if (product.getSeriesUpDate() != null) {
                builder.field("seriesUpDate", ElasticSearchConfig.transDate2Long(product.getSeriesUpDate()));
            }
            if (product.getTopCategory() != null) {
                builder.field("topCategory", product.getTopCategory());
            }
            if (product.getProductCategory() != null) {
                builder.field("productCategory", product.getProductCategory());
            }
            if (product.getAfter() != null) {
                builder.field("after", product.getAfter());
            }
            if (product.getSubscribe() != null) {
                builder.field("subscribe", product.getSubscribe());
            }
            if (product.getRecomScore() != null) {
                builder.field("recomScore", product.getRecomScore());
            }
            if (product.getOperRecom() != null) {
                builder.field("operRecom", product.getOperRecom());
            }
            if (product.getModifyDate() != null) {
                builder.field("modifyDate", ElasticSearchConfig.transDate2Long(product.getModifyDate()));
            }
            if (product.getTopCategoryId() != null) {
                builder.field("topCategoryId", product.getTopCategoryId());
            }
            if (product.getProductCategoryId() != null) {
                builder.field("productCategoryId", product.getProductCategoryId());
            }
            if (product.getProductSellType() != null) {
                builder.field("productSellType", product.getProductSellType());
            }
            if (product.getGpSort() != null) {
                builder.field("gpSort", product.getGpSort());
            }
            if (product.getOpSort() != null) {
                builder.field("opSort", product.getOpSort());
            }
            if (product.getFpSort() != null) {
                builder.field("fpSort", product.getFpSort());
            }
            if (product.getSalesproperty() != null) {
                builder.field("salesproperty", product.getSalesproperty());
            }
            if (product.getProductImageCover() != null) {
                builder.field("productImageCover", product.getProductImageCover());
            }
            if (product.getIntroPic() != null) {
                builder.field("introPic", product.getIntroPic());
            }
            if (product.getCollagePrice() != null) {
                builder.field("collagePrice", product.getCollagePrice());
            }
            updateSearch(product.getId(), builder);
            logger.info("ID为" + product.getId() + "的商品更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateGoodPromotion(SearcherProduct product) {
        try {
            XContentBuilder builder = makeBuilder(product.getId());
            if (product.getPromotionId() != null) {
                builder.field("promotionId", product.getPromotionId());
            }
            if (product.getPromotionType() != null) {
                builder.field("promotionType", product.getPromotionType());
            }
            if (product.getPromotionMark() != null) {
                builder.field("promotionMark", product.getPromotionMark());
            }
            if (product.getStartDate() != null) {
                builder.field("startDate", ElasticSearchConfig.transDate2Long(product.getStartDate()));
            }
            if (product.getEndDate() != null) {
                builder.field("endDate", ElasticSearchConfig.transDate2Long(product.getEndDate()));
            }
            if (product.getPromotionName() != null) {
                builder.field("promotionName", product.getPromotionName());
            }
            if (product.getPromotionSolu() != null) {
                builder.field("promotionSolu", product.getPromotionSolu());
            }
            if (product.getPromotionPrefix() != null) {
                builder.field("promotionPrefix", product.getPromotionPrefix());
            }
            if (product.getAdvance() != null) {
                builder.field("advance", product.getAdvance());
            }
            updateSearch(product.getId(), builder);
            logger.info("ID为" + product.getId() + "的商品更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int removeGoodPromotion(Long productId) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("promotionId", 0);
            builder.field("promotionType", -1);
            builder.field("promotionMark", 0);
            builder.field("startDate", 0);
            builder.field("endDate", 0);
            builder.field("promotionName", "");
            builder.field("promotionSolu", "");
            updateSearch(productId, builder);
            logger.info("ID为" + productId + "的商品更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateOrderPromotion(SearcherProduct product) {
        try {
            XContentBuilder builder = makeBuilder(product.getId());
            if (product.getOrderPromotionId() != null) {
                builder.field("orderPromotionId", product.getOrderPromotionId());
            }
            if (product.getOrderPromotionType() != null) {
                builder.field("orderPromotionType", product.getOrderPromotionType());
            }
            if (product.getOrderPromotionMark() != null) {
                builder.field("orderPromotionMark", product.getOrderPromotionMark());
            }
            if (product.getOrderStartDate() != null) {
                builder.field("orderStartDate", ElasticSearchConfig.transDate2Long(product.getOrderStartDate()));
            }
            if (product.getOrderEndDate() != null) {
                builder.field("orderEndDate", ElasticSearchConfig.transDate2Long(product.getOrderEndDate()));
            }
            updateSearch(product.getId(), builder);
            logger.info("ID为" + product.getId() + "的商品订单活动更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int removeOrderPromotion(Long productId) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("orderPromotionId", 0);
            builder.field("orderPromotionType", 0);
            builder.field("orderPromotionMark", 0);
            builder.field("orderStartDate", 0);
            builder.field("orderEndDate", 0);
            updateSearch(productId, builder);
            logger.info("ID为" + productId + "的商品更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateFlashPromotion(SearcherProduct searcherProduct) {
        try {
            XContentBuilder builder = makeBuilder(searcherProduct.getId());
            if (searcherProduct.getFlashPromotionId() != null) {
                builder.field("flashPromotionId", searcherProduct.getFlashPromotionId());
            }
            if (searcherProduct.getFlashStartDate() != null) {
                builder.field("flashStartDate",
                        ElasticSearchConfig.transDate2Long(searcherProduct.getFlashStartDate()));
            }
            if (searcherProduct.getFlashEndDate() != null) {
                builder.field("flashEndDate", ElasticSearchConfig.transDate2Long(searcherProduct.getFlashEndDate()));
            }
            if (searcherProduct.getFlashMark() != null) {
                builder.field("flashMark", searcherProduct.getFlashMark());
            }
            if (searcherProduct.getFlashPrice() != null) {
                builder.field("flashPrice", searcherProduct.getFlashPrice());
            }
            updateSearch(searcherProduct.getId(), builder);
            logger.info("ID为" + searcherProduct.getId() + "的商品更新限时购成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int removeFlashPromotion(Long productId) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("flashPromotionId", 0);
            builder.field("flashStartDate", 0);
            builder.field("flashEndDate", 0);
            builder.field("flashMark", -1);
            builder.field("flashPrice", 0);
            updateSearch(productId, builder);
            logger.info("ID为" + productId + "的商品更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateCollagePromotion(SearcherProduct searcherProduct) {
        try {
            XContentBuilder builder = makeBuilder(searcherProduct.getId());
            if (searcherProduct.getCollagePromotionId() != null) {
                builder.field("collagePromotionId", searcherProduct.getCollagePromotionId());
            }
            if (searcherProduct.getCollageStartDate() != null) {
                builder.field("collageStartDate",
                        ElasticSearchConfig.transDate2Long(searcherProduct.getCollageStartDate()));
            }
            if (searcherProduct.getCollageEndDate() != null) {
                builder.field("collageEndDate",
                        ElasticSearchConfig.transDate2Long(searcherProduct.getCollageEndDate()));
            }
            if (searcherProduct.getCollageMark() != null) {
                builder.field("collageMark", searcherProduct.getCollageMark());
            }
            if (searcherProduct.getCollagePrice() != null) {
                builder.field("collagePrice", searcherProduct.getCollagePrice());
            }
            updateSearch(searcherProduct.getId(), builder);
            logger.info("ID为" + searcherProduct.getId() + "的商品更新限时购成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int removeCollagePromotion(Long productId) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("collagePromotionId", 0);
            builder.field("collageStartDate", 0);
            builder.field("collageEndDate", 0);
            builder.field("collageMark", -1);
            builder.field("collagePrice", 0);
            updateSearch(productId, builder);
            logger.info("ID为" + productId + "的商品更新索引成功!");
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateRecom(Long productId, Double recomScore, Boolean operRecom) {
        if (recomScore == null)
            return 0;
        try {
            XContentBuilder builder = makeBuilder(productId);
            if (recomScore != null) {
                builder.field("recomScore", recomScore);
            }
            if (operRecom != null) {
                builder.field("operRecom", operRecom ? 1 : 0);
            }
            updateSearch(productId, builder);
            logger.info("ID为" + productId + "的商品搜索推荐值更新成功! recomScore：" + recomScore);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateMark(Long productId, int mark, Date upMarketDate) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("mark", mark);
            if (mark == 1) {
                builder.field("upMarketDate", ElasticSearchConfig.transDate2Long(upMarketDate));
            }
            builder.field("modifyDate", DateUt.getTimeInMillis());
            UpdateResponse updateResp = updateSearch(productId, builder);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品状态更新成功! Mark:" + mark);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateStore(Long productId, int store, int spot) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("store", store);
            builder.field("spot", spot);
            builder.field("modifyDate", DateUt.getTimeInMillis());
            UpdateResponse updateResp = updateSearch(productId, builder);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品库存更新成功! Store:" + store);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateTags(Long productId, String tags) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("tags", tags);
            UpdateResponse updateResp = updateSearch(productId, builder);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品标签更新成功! tags:" + tags);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateRecMark(Long productId, Long liveId, int mark) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.field("mark", mark);
            UpdateResponse updateResp = updateSearchImpl(builder, productId.toString() + "_" + liveId.toString(),
                    TYPE_PRODUCT);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品状态更新成功! Mark:" + mark);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int doRecommend(Long productId, Long liveId, Integer recommend) {
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.field("recommend", recommend);
            if (recommend == 1) {
                builder.field("recDate", System.currentTimeMillis());
            }
            UpdateResponse updateResp = updateSearchImpl(builder, productId + "_" + liveId, TYPE_REC_PRODUCT);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品推荐更新成功! recommend:" + recommend);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    /**
     * 删除单个索引
     */
    @Override
    public int remove(Long productId) {
        String id = productId.toString();
        DeleteResponse result = elasticSearchConfig.getClient()
                .prepareDelete(ElasticSearchConfig.INDEX_NAME, TYPE_PRODUCT, id).execute().actionGet();
        logger.info("ID为" + id + "的商品删除成功!");
        if (result.isFound()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 删除全部索引
     */
    @Override
    @SuppressWarnings("deprecation")
    public void removeAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_PRODUCT)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    /**
     * 删除单个索引
     */
    @Override
    public int removeRec(Long productId, Long liveId) {
        DeleteResponse result = elasticSearchConfig.getClient().prepareDelete(ElasticSearchConfig.INDEX_NAME,
                TYPE_REC_PRODUCT, productId.toString() + "_" + liveId.toString()).execute().actionGet();
        logger.info("ID为" + productId.toString() + "_" + liveId.toString() + "的商品删除成功!");
        if (result.isFound()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 删除REC全部索引
     */
    @Override
    @SuppressWarnings("deprecation")
    public void removeRecAll() {
        elasticSearchConfig.getClient().prepareDeleteByQuery(ElasticSearchConfig.INDEX_NAME).setTypes(TYPE_REC_PRODUCT)
                .setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
    }

    @Override
    public int updateAfter(Long productId, int after) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("after", after);
            UpdateResponse updateResp = updateSearch(productId, builder);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品售后更新成功! after:" + after);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateSubscribe(Long productId, int subscribe) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("subscribe", subscribe);
            UpdateResponse updateResp = updateSearch(productId, builder);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品售后更新成功! subscribe:" + subscribe);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateIndex(Long id, Integer search) {
        try {
            XContentBuilder builder = makeBuilder(id);
            builder.field("search", search);
            UpdateResponse updateResp = updateSearch(id, builder);
            logger.info(updateResp.getGetResult() + "ID为" + id + "的商品售后更新成功! search:" + search);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateRecentlySales(Long productId, Integer recentlySales) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("recentlySales", recentlySales);
            UpdateResponse updateResp = updateSearch(productId, builder);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品分销销量更新成功! recentlySales:" + recentlySales);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }
    // **************************************************

    @Override
    public int updatePartnerSales(Long productId, int sales) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("partnerSales", sales);
            UpdateResponse updateResp = updateSearch(productId, builder);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品售后更新成功! partnerSales:" + sales);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    @Override
    public int updateFlashSellStock(Long productId, int quantity) {
        try {
            XContentBuilder builder = makeBuilder(productId);
            builder.field("flashSellStock", quantity);
            UpdateResponse updateResp = updateSearch(productId, builder);
            logger.info(updateResp.getGetResult() + "ID为" + productId + "的商品限时购库存更新成功! flashSellStock:" + quantity);
            return 1;
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    private XContentBuilder makeBuilder(Long productId) throws IOException {
        return XContentFactory.jsonBuilder().startObject().field("id", productId);
    }

    private UpdateResponse updateSearch(Long productId, XContentBuilder builder) throws IOException {
        return updateSearchImpl(builder, productId.toString(), TYPE_PRODUCT);
    }

    private UpdateResponse updateSearchImpl(XContentBuilder builder, String id, String type) throws IOException {
        builder.endObject();
        UpdateResponse updateResp = elasticSearchConfig.getClient()
                .prepareUpdate(ElasticSearchConfig.INDEX_NAME, type, id).setDoc(builder).execute().actionGet();
        return updateResp;
    }

}
