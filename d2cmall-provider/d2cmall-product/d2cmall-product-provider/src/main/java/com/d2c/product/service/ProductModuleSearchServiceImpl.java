package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dao.ProductMapper;
import com.d2c.product.dao.ProductSummaryMapper;
import com.d2c.product.model.*;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.model.SearcherRecProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.search.service.ProductSearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Service("productModuleSearchService")
public class ProductModuleSearchServiceImpl implements ProductModuleSearchService {

    @Reference
    private ProductSearcherService productSearcherService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSummaryMapper productSummaryMapper;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SeriesService seriesService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private ProductDetailService productDetailService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private CollagePromotionService collagePromotionService;

    /**
     * 获取索引模型
     *
     * @param productId
     * @return
     */
    @Override
    public SearcherProduct getSearchDto(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        ProductDetail detail = productDetailService.findByProductId(productId);
        ProductSummary summary = productSummaryMapper.findByProductId(productId);
        return getSearchDto(product, summary, detail);
    }

    /**
     * 获取索引模型
     *
     * @param product
     * @param summary
     * @return
     */
    @Override
    public SearcherProduct getSearchDto(Product product, ProductSummary summary, ProductDetail detail) {
        SearcherProduct searcherProduct = new SearcherProduct();
        searcherProduct.setId(product.getId());
        searcherProduct.setProductId(product.getId());
        searcherProduct.setMinPrice(product.getMinPrice());
        searcherProduct.setMaxPrice(product.getMaxPrice());
        searcherProduct.setOriginalPrice(product.getOriginalPrice());
        searcherProduct.setSeo(product.getSeo());
        searcherProduct.setName(product.getName());
        searcherProduct.setInernalSn(product.getInernalSn());
        searcherProduct.setExternalSn(product.getExternalSn());
        searcherProduct.setSubTitle(product.getSubTitle());
        searcherProduct.setTopCategory(product.getTopCategory());
        searcherProduct.setProductCategory(product.getProductCategory());
        searcherProduct.setTopCategoryId(product.getTopCategoryId());
        searcherProduct.setProductCategoryId(product.getProductCategoryId());
        searcherProduct.setTags(product.getTags());
        searcherProduct.setSort(product.getSort());
        searcherProduct.setStore(product.getSyncStore() + product.getPopStore());
        searcherProduct.setAfter(product.getAfter());
        searcherProduct.setSubscribe(product.getSubscribe());
        searcherProduct.setSearch(product.getSearch());
        searcherProduct.setMark(product.getMark());
        searcherProduct.setTopical(product.getTopical());
        searcherProduct.setUpMarketDate(product.getUpMarketDate());
        searcherProduct.setProductSellType(product.getProductSellType());
        searcherProduct.setProductTradeType(product.getProductTradeType());
        searcherProduct.setProductImageCover(product.getProductImageCover());
        searcherProduct.setStatus(product.getStatus());
        searcherProduct.setCart(product.getCart());
        searcherProduct.setSalesproperty(product.getSalesproperty());
        searcherProduct.setOperRecom(product.getOperRecom());
        searcherProduct.setRecomScore(product.getRecom());
        searcherProduct.setCreateDate(product.getCreateDate());
        searcherProduct.setModifyDate(product.getModifyDate());
        searcherProduct.setaPrice(product.getaPrice());
        searcherProduct.setFlashPrice(product.getFlashPrice());
        searcherProduct.setFirstRatio(product.getFirstRatio());
        searcherProduct.setSecondRatio(product.getSecondRatio());
        searcherProduct.setGrossRatio(product.getGrossRatio());
        searcherProduct.setGpSort(product.getGpSort());
        searcherProduct.setOpSort(product.getOpSort());
        searcherProduct.setFpSort(product.getFpSort());
        Brand brand = brandService.findById(product.getDesignerId());
        if (brand != null) {
            searcherProduct.setBrand(brand.getName());
            searcherProduct.setDesigner(brand.getDesigners());
            searcherProduct.setDesignerId(product.getDesignerId());
        }
        Series series = seriesService.findById(product.getSeriesId());
        if (series != null) {
            searcherProduct.setSeriesId(product.getSeriesId());
            searcherProduct.setBrandStyle(series.getStyle());
            if (series.getUpDateTime() == null) {
                searcherProduct.setSeriesUpDate(product.getUpMarketDate());
            } else {
                searcherProduct.setSeriesUpDate(series.getUpDateTime());
            }
        }
        if (product.getGoodPromotionId() != null && product.getGoodPromotionId() > 0) {
            Promotion productPromotion = promotionService.findSimpleById(product.getGoodPromotionId());
            if (productPromotion != null) {
                searcherProduct.setPromotionId(productPromotion.getId());
                searcherProduct.setPromotionMark(productPromotion.getEnable() ? 1 : 0);
                searcherProduct.setPromotionType(productPromotion.getPromotionType());
                searcherProduct.setPromotionName(productPromotion.getName());
                searcherProduct.setPromotionSolu(productPromotion.getSolution());
                searcherProduct.setStartDate(productPromotion.getStartTime());
                searcherProduct.setEndDate(productPromotion.getEndTime());
                searcherProduct.setAdvance(productPromotion.getAdvance());
                searcherProduct.setPromotionPrefix(productPromotion.getPrefix());
            }
        }
        if (product.getOrderPromotionId() != null && product.getOrderPromotionId() > 0) {
            Promotion orderPromotion = promotionService.findSimpleById(product.getOrderPromotionId());
            if (orderPromotion != null) {
                searcherProduct.setOrderPromotionId(product.getOrderPromotionId());
                searcherProduct.setOrderPromotionMark(orderPromotion.getEnable() ? 1 : 0);
                searcherProduct.setOrderPromotionType(orderPromotion.getPromotionType());
                searcherProduct.setOrderStartDate(orderPromotion.getStartTime());
                searcherProduct.setOrderEndDate(orderPromotion.getEndTime());
            }
        }
        if (product.getFlashPromotionId() != null && product.getFlashPromotionId() > 0) {
            FlashPromotion flashPromotion = flashPromotionService.findById(product.getFlashPromotionId());
            if (flashPromotion != null) {
                searcherProduct.setFlashPromotionId(product.getFlashPromotionId());
                searcherProduct.setFlashStartDate(flashPromotion.getStartDate());
                searcherProduct.setFlashEndDate(flashPromotion.getEndDate());
                searcherProduct.setFlashMark(flashPromotion.getStatus());
            }
        }
        if (product.getCollagePromotionId() != null && product.getCollagePromotionId() > 0) {
            CollagePromotion collagePromotion = collagePromotionService.findById(product.getCollagePromotionId());
            if (collagePromotion != null) {
                searcherProduct.setCollagePromotionId(product.getCollagePromotionId());
                searcherProduct.setCollageStartDate(collagePromotion.getBeginDate());
                searcherProduct.setCollageEndDate(collagePromotion.getEndDate());
                searcherProduct.setCollageMark(collagePromotion.getStatus());
                searcherProduct.setCollagePrice(product.getCollagePrice());
            }
        }
        if (summary != null) {
            searcherProduct.setSales(summary.getSales());
            searcherProduct.setComments(summary.getComments());
            searcherProduct.setConsults(summary.getConsults());
            searcherProduct.setRecentlySales(summary.getRecentlySales());
            searcherProduct.setPartnerSales(summary.getPartnerSales());
        }
        if (detail != null) {
            searcherProduct.setRecommendation(detail.getRecommendation());
            searcherProduct.setIntroduction(detail.getIntroduction());
        }
        if (product.getSyncStore() <= 0) {
            searcherProduct.setSpot(0);
        } else {
            searcherProduct.setSpot(1);
        }
        JSONObject category = new JSONObject();
        if (product.getProductCategory() != null) {
            JSONArray array = JSONArray.parseArray(product.getProductCategory());
            category = (JSONObject) array.get(array.size() - 1);
        }
        searcherProduct.setCategoryName(category == null ? "" : category.getString("name"));
        return searcherProduct;
    }

    @Override
    public SearcherProduct getSearchDto(Long productId, Promotion promotion) {
        SearcherProduct searcherProduct = new SearcherProduct();
        searcherProduct.setId(productId);
        if (promotion.getPromotionScope() == 0) {
            searcherProduct.setPromotionId(promotion.getId());
            searcherProduct.setPromotionMark(promotion.getEnable() ? 1 : 0);
            searcherProduct.setPromotionType(promotion.getPromotionType());
            searcherProduct.setPromotionName(promotion.getName());
            searcherProduct.setPromotionSolu(promotion.getSolution());
            searcherProduct.setStartDate(promotion.getStartTime());
            searcherProduct.setEndDate(promotion.getEndTime());
            searcherProduct.setAdvance(promotion.getAdvance());
            searcherProduct.setPromotionPrefix(promotion.getPrefix());
        } else if (promotion.getPromotionScope() == 1) {
            searcherProduct.setOrderPromotionId(promotion.getId());
            searcherProduct.setOrderPromotionMark(promotion.getEnable() ? 1 : 0);
            searcherProduct.setOrderPromotionType(promotion.getPromotionType());
            searcherProduct.setOrderStartDate(promotion.getStartTime());
            searcherProduct.setOrderEndDate(promotion.getEndTime());
        }
        return searcherProduct;
    }

    @Override
    public SearcherProduct getSearchDto(Long productId, FlashPromotion flashPromotion) {
        SearcherProduct searcherProduct = new SearcherProduct();
        Product oldProduct = productMapper.selectByPrimaryKey(productId);
        if (oldProduct != null) {
            searcherProduct.setFlashPrice(oldProduct.getFlashPrice());
            searcherProduct.setFlashPromotionId(flashPromotion.getId());
            searcherProduct.setFlashStartDate(flashPromotion.getStartDate());
            searcherProduct.setFlashEndDate(flashPromotion.getEndDate());
            searcherProduct.setFlashMark(flashPromotion.getStatus());
            searcherProduct.setId(productId);
        }
        return searcherProduct;
    }

    @Override
    public SearcherProduct getSearchDto(Long productId, CollagePromotion collagePromotion) {
        SearcherProduct searcherProduct = new SearcherProduct();
        Product oldProduct = productMapper.selectByPrimaryKey(productId);
        if (oldProduct != null) {
            searcherProduct.setCollagePromotionId(collagePromotion.getId());
            searcherProduct.setCollageStartDate(collagePromotion.getBeginDate());
            searcherProduct.setCollageEndDate(collagePromotion.getEndDate());
            searcherProduct.setCollageMark(collagePromotion.getStatus());
            searcherProduct.setCollagePrice(oldProduct.getCollagePrice());
            searcherProduct.setId(productId);
        }
        return searcherProduct;
    }

    @Override
    public int rebuild(Long productId) {
        int result = productSearcherService.rebuild(this.getSearchDto(productId));
        if (result > 0) {
            result = this.updateStore(productId);
        }
        return result;
    }

    @Override
    public int insert(Long productId) {
        int result = productSearcherService.insert(this.getSearchDto(productId));
        if (result > 0) {
            result = this.updateStore(productId);
        }
        return result;
    }

    @Override
    public int remove(Long productId) {
        int result = productSearcherService.remove(productId);
        ProductProSearchQuery searcher = new ProductProSearchQuery();
        PageModel page = new PageModel();
        searcher.setProductIds(Arrays.asList(productId.toString()));
        PageResult<SearcherRecProduct> pager = productSearcherQueryService.searchRec(searcher, page);
        for (SearcherRecProduct p : pager.getList()) {
            productSearcherService.removeRec(productId, p.getLiveId());
        }
        return result;
    }

    @Override
    public int update(SearcherProduct product) {
        return productSearcherService.update(product);
    }

    @Override
    public int updateMark(Long productId, int mark, Date upMarketDate) {
        int success = productSearcherService.updateMark(productId, mark, upMarketDate);
        ProductProSearchQuery searcher = new ProductProSearchQuery();
        PageModel page = new PageModel();
        searcher.setProductIds(Arrays.asList(productId.toString()));
        PageResult<SearcherRecProduct> pager = productSearcherQueryService.searchRec(searcher, page);
        for (SearcherRecProduct p : pager.getList()) {
            productSearcherService.updateRecMark(productId, p.getLiveId(), mark);
        }
        return success;
    }

    @Override
    public int updateTags(Long productId, String tags) {
        return productSearcherService.updateTags(productId, tags);
    }

    @Override
    public int updateStore(Long productId) {
        int result;
        Map<String, Object> storeMap = productService.findProductStoreBySku(productId);
        int store = Integer.parseInt(storeMap.get("store").toString());
        int pop = Integer.parseInt(storeMap.get("pop").toString());
        int storeUpdate = 0, spotUpdate = 0;
        if (store + pop > 0) {
            storeUpdate = 1;
        }
        if (store > 0) {
            spotUpdate = 1;
        }
        result = productSearcherService.updateStore(productId, storeUpdate, spotUpdate);
        return result;
    }

    @Override
    public int updateAfter(Long productId, int after) {
        return productSearcherService.updateAfter(productId, after);
    }

    @Override
    public int updateSubscribe(Long productId, int subscribe) {
        return productSearcherService.updateSubscribe(productId, subscribe);
    }

    @Override
    public int updateRecentlySales(Long productId, Integer recentlySales) {
        return productSearcherService.updateRecentlySales(productId, recentlySales);
    }

    @Override
    public int updatePromotion(Long productId, Promotion promotion) {
        if (promotion.getPromotionScope() == 0) {
            return this.updateGoodPromotion(productId, promotion);
        } else if (promotion.getPromotionScope() == 1) {
            return this.updateOrderPromotion(productId, promotion);
        }
        return 0;
    }

    private int updateGoodPromotion(Long productId, Promotion productPromotion) {
        return productSearcherService.updateGoodPromotion(this.getSearchDto(productId, productPromotion));
    }

    private int updateOrderPromotion(Long productId, Promotion orderPromotion) {
        return productSearcherService.updateOrderPromotion(this.getSearchDto(productId, orderPromotion));
    }

    @Override
    public int updateFlashPromotion(Long productId, FlashPromotion flashPromotion) {
        return productSearcherService.updateFlashPromotion(this.getSearchDto(productId, flashPromotion));
    }

    @Override
    public int updateCollagePromotion(Long productId, CollagePromotion collagePromotion) {
        return productSearcherService.updateCollagePromotion(this.getSearchDto(productId, collagePromotion));
    }

    @Override
    public int updatePartnerSales(Long productId, Integer sales) {
        return productSearcherService.updatePartnerSales(productId, sales);
    }

}
