package com.d2c.product.search.view;

import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.dto.PreDTO;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.product.model.Promotion.PromotionType;
import com.d2c.product.search.model.SearcherProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductVO extends PreDTO {
    // soonPrice 未来价 currentPrice 当前价 minPrice 销售价 originalPrice 吊牌价
    // soonPrice 未来价 minPrice 当前价 salePrice 销售价 originalPrice 吊牌价
    private static final long serialVersionUID = 9153427619124455999L;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 款号
     */
    private String sn;
    /**
     * 名称
     */
    private String name;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 商品图片
     */
    private String img;
    /**
     * 商品图片
     */
    private String mainPic;
    /**
     * 销售价
     */
    private BigDecimal price;
    /**
     * 销售价
     */
    private BigDecimal minPrice;
    /**
     * 最高价
     */
    private BigDecimal maxPrice;
    /**
     * 销售价
     */
    private BigDecimal salePrice;
    /**
     * 销售价
     */
    private BigDecimal sellPrice;
    /**
     * 吊牌价
     */
    private BigDecimal originalPrice;
    /**
     * 限时购价格
     */
    private BigDecimal flashPrice;
    /**
     * 拼团价格
     */
    private BigDecimal collagePrice;
    /**
     * 即将价格
     */
    private BigDecimal soonPrice;
    /**
     * 限时购ID
     */
    private Long flashPromotionId;
    /**
     * 拼团活动ID
     */
    private Long collagePromotionId;
    /**
     * 是否有库存 0：无库存 1：自营pop都有
     */
    private Integer store;
    /**
     * 状态
     */
    private Integer mark;
    /**
     * 是否有现货
     */
    private Boolean isSpot;
    /**
     * 是否置顶
     */
    private Boolean isTopical;
    /**
     * 是否允许加入购物车
     */
    private Integer isCart;
    /**
     * 是否售后
     */
    private Integer isAfter;
    /**
     * 是否门店试衣
     */
    private Integer isSubscribe;
    /**
     * 一级返利系数
     */
    private BigDecimal firstRatio;
    /**
     * 二级返利系数
     */
    private BigDecimal secondRatio;
    /**
     * 总返利系数
     */
    private BigDecimal grossRatio;
    /**
     * 是否是收藏的商品
     */
    private Integer collectioned;
    /**
     * 颜色列表
     */
    private JSONArray colors;
    /**
     * 尺码列表
     */
    private JSONArray sizes;
    /**
     * 销售类型 SPOT(现货) PRESELL(预售) CUSTOM(定制)
     */
    private String productSellType;
    /**
     * 贸易类型
     */
    private String productTradeType;
    /**
     * 品牌名称
     */
    private String brand;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 设计师名称
     */
    private String designer;
    /**
     * 二级分类ID
     */
    private Long categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 评论数量
     */
    private Integer comments;
    /**
     * 咨询数量
     */
    private Integer consults;
    /**
     * 商品推荐值
     */
    private Double recomScore;
    /**
     * 7天分销销量
     */
    private Integer partnerSales;
    /**
     * 商品活动ID
     */
    private Long promotionId;
    /**
     * 商品活动类型
     */
    private Integer promotionType;
    /**
     * 商品活动类型名称
     */
    private String promotionTypeName;
    /**
     * 订单活动ID
     */
    private Long orderPromotionId;
    /**
     * 订单活动类型
     */
    private Integer orderPromotionType;
    /**
     * 订单活动类型名称
     */
    private String orderPromotionTypeName;
    /**
     * 数据获取类型
     *
     * @see SearchFromEnum
     */
    private String searchFrom;
    private Map<String, Object> soonPromotion;

    public static List<ProductVO> convertList(Iterable<SearcherProduct> list) {
        List<ProductVO> resList = new ArrayList<>();
        list.forEach(bean -> {
            resList.add(convert(bean));
        });
        return resList;
    }

    /**
     * 转换对象
     */
    public static ProductVO convert(SearcherProduct bean) {
        ProductVO res = new ProductVO();
        res.setId(bean.getId());
        res.setSn(bean.getInernalSn());
        res.setName(bean.getName());
        res.setSubTitle(bean.getSubTitle());
        res.setImg(bean.getProductImageCover());
        res.setMainPic(bean.getProductImageCover());
        res.setPrice(bean.getCurrentPrice());
        res.setMinPrice(bean.getCurrentPrice());
        res.setMaxPrice(bean.getMaxPrice());
        res.setSalePrice(bean.getMinPrice());
        res.setSellPrice(bean.getMinPrice());
        res.setOriginalPrice(bean.getOriginalPrice());
        res.setFlashPrice(bean.getFlashPrice());
        res.setCollagePrice(bean.getCollagePrice());
        res.setSoonPrice(bean.getSoonPrice());
        res.setStore(bean.getStore());
        res.setMark(bean.getMark());
        res.setIsSpot(ConvertUt.toBoolean(bean.getSpot()));
        res.setIsTopical(ConvertUt.toBoolean(bean.getTopical()));
        res.setIsCart(bean.getCart());
        res.setIsAfter(bean.getAfter());
        res.setIsSubscribe(bean.getSubscribe());
        res.setFirstRatio(bean.getFirstRatio());
        res.setSecondRatio(bean.getSecondRatio());
        res.setGrossRatio(bean.getGrossRatio());
        res.setCollectioned(bean.getCollectioned());
        res.setColors(bean.getColors());
        res.setSizes(bean.getSizes());
        res.setProductSellType(bean.getProductSellType());
        res.setProductTradeType(bean.getProductTradeType());
        res.setBrand(bean.getBrand());
        res.setDesignerId(bean.getDesignerId());
        res.setDesigner(bean.getDesigner());
        res.setCategoryId(bean.getProductCategoryId());
        res.setCategoryName(bean.getCategoryName());
        res.setConsults(bean.getConsults());
        res.setComments(bean.getComments());
        res.setRecomScore(bean.getRecomScore());
        res.setPartnerSales(bean.getPartnerSales());
        res.setSoonPromotion(bean.getSoonPromotion().getInnerMap());
        if (!bean.isPromotionOver()) {
            res.setPromotionId(bean.getPromotionId());
            res.setPromotionType(bean.getPromotionType());
            res.setPromotionTypeName(PromotionType.getStatus(bean.getPromotionType()).getName());
        }
        if (!bean.isOrderPromotionOver()) {
            res.setOrderPromotionId(bean.getOrderPromotionId());
            res.setOrderPromotionType(bean.getOrderPromotionType());
            res.setOrderPromotionTypeName(PromotionType.getStatus(bean.getOrderPromotionType()).getName());
        }
        if (!bean.isFlashPromotionOver()) {
            res.setFlashPromotionId(bean.getFlashPromotionId());
        }
        if (!bean.isCollagePromotionOver()) {
            res.setCollagePromotionId(bean.getCollagePromotionId());
        }
        return res;
    }
    // ********************************************

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getFlashPrice() {
        return flashPrice;
    }

    public void setFlashPrice(BigDecimal flashPrice) {
        this.flashPrice = flashPrice;
    }

    public BigDecimal getCollagePrice() {
        return collagePrice;
    }

    public void setCollagePrice(BigDecimal collagePrice) {
        this.collagePrice = collagePrice;
    }

    public BigDecimal getSoonPrice() {
        return soonPrice;
    }

    public void setSoonPrice(BigDecimal soonPrice) {
        this.soonPrice = soonPrice;
    }

    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
    }

    public Long getCollagePromotionId() {
        return collagePromotionId;
    }

    public void setCollagePromotionId(Long collagePromotionId) {
        this.collagePromotionId = collagePromotionId;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Boolean getIsSpot() {
        return isSpot;
    }

    public void setIsSpot(Boolean isSpot) {
        this.isSpot = isSpot;
    }

    public Boolean getIsTopical() {
        return isTopical;
    }

    public void setIsTopical(Boolean isTopical) {
        this.isTopical = isTopical;
    }

    public Integer getIsCart() {
        return isCart;
    }

    public void setIsCart(Integer isCart) {
        this.isCart = isCart;
    }

    public Integer getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(Integer isAfter) {
        this.isAfter = isAfter;
    }

    public Integer getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public BigDecimal getFirstRatio() {
        return firstRatio;
    }

    public void setFirstRatio(BigDecimal firstRatio) {
        this.firstRatio = firstRatio;
    }

    public BigDecimal getSecondRatio() {
        return secondRatio;
    }

    public void setSecondRatio(BigDecimal secondRatio) {
        this.secondRatio = secondRatio;
    }

    public BigDecimal getGrossRatio() {
        return grossRatio;
    }

    public void setGrossRatio(BigDecimal grossRatio) {
        this.grossRatio = grossRatio;
    }

    public Integer getCollectioned() {
        return collectioned;
    }

    public void setCollectioned(Integer collectioned) {
        this.collectioned = collectioned;
    }

    public JSONArray getColors() {
        return colors;
    }

    public void setColors(JSONArray colors) {
        this.colors = colors;
    }

    public JSONArray getSizes() {
        return sizes;
    }

    public void setSizes(JSONArray sizes) {
        this.sizes = sizes;
    }

    public String getProductSellType() {
        return productSellType;
    }

    public void setProductSellType(String productSellType) {
        this.productSellType = productSellType;
    }

    public String getProductTradeType() {
        return productTradeType;
    }

    public void setProductTradeType(String productTradeType) {
        this.productTradeType = productTradeType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getConsults() {
        return consults;
    }

    public void setConsults(Integer consults) {
        this.consults = consults;
    }

    public Double getRecomScore() {
        return recomScore;
    }

    public void setRecomScore(Double recomScore) {
        this.recomScore = recomScore;
    }

    public Integer getPartnerSales() {
        return partnerSales;
    }

    public void setPartnerSales(Integer partnerSales) {
        this.partnerSales = partnerSales;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public String getPromotionTypeName() {
        return promotionTypeName;
    }

    public void setPromotionTypeName(String promotionTypeName) {
        this.promotionTypeName = promotionTypeName;
    }

    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }

    public Integer getOrderPromotionType() {
        return orderPromotionType;
    }

    public void setOrderPromotionType(Integer orderPromotionType) {
        this.orderPromotionType = orderPromotionType;
    }

    public String getOrderPromotionTypeName() {
        return orderPromotionTypeName;
    }

    public void setOrderPromotionTypeName(String orderPromotionTypeName) {
        this.orderPromotionTypeName = orderPromotionTypeName;
    }

    public String getSearchFrom() {
        return searchFrom;
    }

    public void setSearchFrom(String searchFrom) {
        this.searchFrom = searchFrom;
    }

    public Map<String, Object> getSoonPromotion() {
        return soonPromotion;
    }

    public void setSoonPromotion(Map<String, Object> soonPromotion) {
        this.soonPromotion = soonPromotion;
    }

}
