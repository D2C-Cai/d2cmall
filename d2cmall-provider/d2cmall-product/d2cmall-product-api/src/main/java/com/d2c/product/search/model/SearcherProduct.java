package com.d2c.product.search.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.PreDTO;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.model.Promotion.PromotionType;
import com.d2c.product.search.view.ProductVO;
import com.d2c.util.string.StringUtil;
import org.elasticsearch.common.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

public class SearcherProduct extends PreDTO {
    // soonPrice 未来价 currentPrice 当前价 minPrice 销售价 originalPrice 吊牌价
    // soonPrice 未来价 minPrice 当前价 salePrice 销售价 originalPrice 吊牌价
    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 销售价
     */
    private BigDecimal minPrice;
    /**
     * 最高价
     */
    private BigDecimal maxPrice;
    /**
     * 吊牌价
     */
    private BigDecimal originalPrice;
    /**
     * 一口价
     */
    private BigDecimal aPrice;
    /**
     * 限时购价格
     */
    private BigDecimal flashPrice;
    // 商品活动
    /**
     * 商品活动ID
     */
    private Long promotionId;
    /**
     * 商品活动类型
     */
    private Integer promotionType;
    /**
     * 商品活动上下架
     */
    private Integer promotionMark;
    /**
     * 商品活动开始时间
     */
    private Date startDate;
    /**
     * 商品活动结束时间
     */
    private Date endDate;
    /**
     * 商品活动名称
     */
    private String promotionName;
    /**
     * 商品活动方案
     */
    private String promotionSolu;
    /**
     * 商品活动前缀
     */
    private String promotionPrefix;
    /**
     * 提前显示价格
     */
    private Integer advance;
    // 订单活动
    /**
     * 订单活动ID
     */
    private Long orderPromotionId;
    /**
     * 订单活动类型
     */
    private Integer orderPromotionType;
    /**
     * 订单活动状态
     */
    private Integer orderPromotionMark;
    /**
     * 订单活动开始时间
     */
    private Date orderStartDate;
    /**
     * 订单活动结束时间
     */
    private Date orderEndDate;
    // 限时购活动
    /**
     * 限时购活动ID
     */
    private Long flashPromotionId;
    /**
     * 限时购状态
     */
    private Integer flashMark;
    /**
     * 限时购开始时间
     */
    private Date flashStartDate;
    /**
     * 限时购结束时间
     */
    private Date flashEndDate;
    // 拼团活动
    /**
     * 拼团活动ID
     */
    private Long collagePromotionId;
    /**
     * 拼团状态
     */
    private Integer collageMark;
    /**
     * 拼团开始时间
     */
    private Date collageStartDate;
    /**
     * 拼团结束时间
     */
    private Date collageEndDate;
    /**
     * 拼团价格
     */
    private BigDecimal collagePrice;
    /**
     * 商品活动排序
     */
    private Integer gpSort;
    /**
     * 订单活动排序
     */
    private Integer opSort;
    /**
     * 限时购活动排序
     */
    private Integer fpSort;
    /**
     * 搜索关键字
     */
    private String seo;
    /**
     * 名称
     */
    private String name;
    /**
     * 款号
     */
    private String inernalSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 系列ID
     */
    private Long seriesId;
    /**
     * 一级分类
     */
    private String topCategory;
    /**
     * 二级分类
     */
    private String productCategory;
    /**
     * 一级分类ID
     */
    private Long topCategoryId;
    /**
     * 二级分类ID
     */
    private Long productCategoryId;
    /**
     * 标签，多个按","分隔
     */
    private String tags;
    /**
     * 站内排序
     */
    private Integer sort;
    /**
     * 是否有库存 0：无库存 1：自营pop都有
     */
    private Integer store;
    /**
     * 是否有现货 0：没有 1：有
     */
    private Integer spot;
    /**
     * 是否建立索引
     */
    private Integer search;
    /**
     * 是否允许加入购物车
     */
    private Integer cart;
    /**
     * 状态
     */
    private Integer mark;
    /**
     * 置顶
     */
    private Integer topical;
    /**
     * 上架时间
     */
    private Date upMarketDate;
    /**
     * 系列上架时间
     */
    private Date seriesUpDate;
    /**
     * 设计师名称
     */
    private String designer;
    /**
     * 品牌名称
     */
    private String brand;
    /**
     * 品牌设计风格
     */
    private String brandStyle;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 商品图片
     */
    private String productImageCover;
    /**
     * 销量
     */
    private Integer sales;
    /**
     * 广告图片
     */
    private String introPic;
    /**
     * 商品状态
     */
    private Integer status;
    /**
     * 评论数量
     */
    private Integer comments;
    /**
     * 咨询数量
     */
    private Integer consults;
    /**
     * 是否是收藏的商品
     */
    private Integer collectioned;
    /**
     * 销售属性
     */
    private String salesproperty;
    /**
     * 销售类型 SPOT(现货) PRESELL(预售) CUSTOM(定制)
     */
    private String productSellType;
    /**
     * 贸易类型
     */
    private String productTradeType;
    /**
     * 是否售后
     */
    private Integer after;
    /**
     * 是否门店试衣
     */
    private Integer subscribe;
    /**
     * 商品推荐值
     */
    private Double recomScore;
    /**
     * 是否运营推荐商品 1:是 0:否
     */
    private Integer operRecom;
    /**
     * 商品介绍
     */
    private String introduction;
    /**
     * 商品推荐语
     */
    private String recommendation;
    /**
     * 最近一个月销量
     */
    private Integer recentlySales;
    /**
     * 7天分销销量
     */
    private Integer partnerSales;
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

    public SearcherProduct() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getaPrice() {
        return aPrice;
    }

    public void setaPrice(BigDecimal aPrice) {
        this.aPrice = aPrice;
    }

    public BigDecimal getFlashPrice() {
        return flashPrice == null ? new BigDecimal(0) : flashPrice;
    }

    public void setFlashPrice(BigDecimal flashPrice) {
        this.flashPrice = flashPrice;
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

    public Integer getPromotionMark() {
        return promotionMark;
    }

    public void setPromotionMark(Integer promotionMark) {
        this.promotionMark = promotionMark;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionSolu() {
        return promotionSolu;
    }

    public void setPromotionSolu(String promotionSolu) {
        this.promotionSolu = promotionSolu;
    }

    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
    }

    public Integer getFlashMark() {
        return flashMark;
    }

    public void setFlashMark(Integer flashMark) {
        this.flashMark = flashMark;
    }

    public Date getFlashStartDate() {
        return flashStartDate;
    }

    public void setFlashStartDate(Date flashStartDate) {
        this.flashStartDate = flashStartDate;
    }

    public Date getFlashEndDate() {
        return flashEndDate;
    }

    public void setFlashEndDate(Date flashEndDate) {
        this.flashEndDate = flashEndDate;
    }

    public Long getCollagePromotionId() {
        return collagePromotionId;
    }

    public void setCollagePromotionId(Long collagePromotionId) {
        this.collagePromotionId = collagePromotionId;
    }

    public Integer getCollageMark() {
        return collageMark;
    }

    public void setCollageMark(Integer collageMark) {
        this.collageMark = collageMark;
    }

    public Date getCollageStartDate() {
        return collageStartDate;
    }

    public void setCollageStartDate(Date collageStartDate) {
        this.collageStartDate = collageStartDate;
    }

    public Date getCollageEndDate() {
        return collageEndDate;
    }

    public void setCollageEndDate(Date collageEndDate) {
        this.collageEndDate = collageEndDate;
    }

    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }

    public Integer getGpSort() {
        return gpSort;
    }

    public void setGpSort(Integer gpSort) {
        this.gpSort = gpSort;
    }

    public Integer getOpSort() {
        return opSort;
    }

    public void setOpSort(Integer opSort) {
        this.opSort = opSort;
    }

    public Integer getFpSort() {
        return fpSort;
    }

    public void setFpSort(Integer fpSort) {
        this.fpSort = fpSort;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public String getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getTopCategoryId() {
        return topCategoryId;
    }

    public void setTopCategoryId(Long topCategoryId) {
        this.topCategoryId = topCategoryId;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getSpot() {
        return spot;
    }

    public void setSpot(Integer spot) {
        this.spot = spot;
    }

    public Integer getSearch() {
        return search;
    }

    public void setSearch(Integer search) {
        this.search = search;
    }

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getTopical() {
        return topical;
    }

    public void setTopical(Integer topical) {
        this.topical = topical;
    }

    public Date getUpMarketDate() {
        return upMarketDate;
    }

    public void setUpMarketDate(Date upMarketDate) {
        this.upMarketDate = upMarketDate;
    }

    public Date getSeriesUpDate() {
        return seriesUpDate;
    }

    public void setSeriesUpDate(Date seriesUpDate) {
        this.seriesUpDate = seriesUpDate;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandStyle() {
        return brandStyle;
    }

    public void setBrandStyle(String brandStyle) {
        this.brandStyle = brandStyle;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductImageCover() {
        return productImageCover;
    }

    public void setProductImageCover(String productImageCover) {
        this.productImageCover = productImageCover;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getIntroPic() {
        return introPic;
    }

    public void setIntroPic(String introPic) {
        this.introPic = introPic;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getCollectioned() {
        return collectioned;
    }

    public void setCollectioned(Integer collectioned) {
        this.collectioned = collectioned;
    }

    public String getSalesproperty() {
        return salesproperty;
    }

    public void setSalesproperty(String salesproperty) {
        this.salesproperty = salesproperty;
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

    public Integer getAfter() {
        return after;
    }

    public void setAfter(Integer after) {
        this.after = after;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Double getRecomScore() {
        return recomScore;
    }

    public void setRecomScore(Double recomScore) {
        this.recomScore = recomScore;
    }

    public Integer getOperRecom() {
        return operRecom;
    }

    public void setOperRecom(Integer operRecom) {
        this.operRecom = operRecom;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public Integer getRecentlySales() {
        return recentlySales;
    }

    public void setRecentlySales(Integer recentlySales) {
        this.recentlySales = recentlySales;
    }

    public BigDecimal getFirstRatio() {
        return firstRatio == null ? new BigDecimal(0) : firstRatio;
    }

    public void setFirstRatio(BigDecimal firstRatio) {
        this.firstRatio = firstRatio;
    }

    public BigDecimal getSecondRatio() {
        return secondRatio == null ? new BigDecimal(0) : secondRatio;
    }

    public void setSecondRatio(BigDecimal secondRatio) {
        this.secondRatio = secondRatio;
    }

    public BigDecimal getGrossRatio() {
        return grossRatio == null ? new BigDecimal(0) : grossRatio;
    }

    public void setGrossRatio(BigDecimal grossRatio) {
        this.grossRatio = grossRatio;
    }

    public Boolean getIsOperRecom() {
        if (operRecom != null) {
            return operRecom > 0;
        }
        return null;
    }

    public Integer getPartnerSales() {
        return partnerSales;
    }

    public void setPartnerSales(Integer partnerSales) {
        this.partnerSales = partnerSales;
    }

    public Integer getOrderPromotionType() {
        return orderPromotionType;
    }

    public void setOrderPromotionType(Integer orderPromotionType) {
        this.orderPromotionType = orderPromotionType;
    }

    public Integer getOrderPromotionMark() {
        return orderPromotionMark;
    }

    public void setOrderPromotionMark(Integer orderPromotionMark) {
        this.orderPromotionMark = orderPromotionMark;
    }

    public Date getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(Date orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }

    public BigDecimal getCollagePrice() {
        return collagePrice;
    }

    public void setCollagePrice(BigDecimal collagePrice) {
        this.collagePrice = collagePrice;
    }

    public String getPromotionPrefix() {
        return promotionPrefix;
    }

    public void setPromotionPrefix(String promotionPrefix) {
        this.promotionPrefix = promotionPrefix;
    }

    public Integer getAdvance() {
        return advance;
    }

    public void setAdvance(Integer advance) {
        this.advance = advance;
    }

    /**
     * 获取商品封面图(头图中的第一张)
     */
    public String getMainPic() {
        String mainPic = "";
        if (this.productImageCover != null) {
            mainPic = productImageCover.split(",")[0];
        }
        return mainPic;
    }

    public ProductVO toView() {
        return ProductVO.convert(this);
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject(BeanUt.toMap(toView()));
        return obj;
    }

    public JSONObject toCollectionJson() {
        JSONObject obj = new JSONObject();
        obj.put("productId", this.getId());
        obj.put("productName", this.getName());
        obj.put("productPic", this.getProductImageCover());
        obj.put("productMark", this.getMark());
        obj.put("designers", this.getDesigner());
        obj.put("store", this.getStore());
        obj.put("salePrice", this.getMinPrice());
        obj.put("currentPrice", this.getCurrentPrice());
        obj.put("originalPrice", this.getOriginalPrice());
        obj.put("productTradeType", this.getProductTradeType());
        obj.put("soonPromotion", this.getSoonPromotion());
        if (!this.isPromotionOver()) {
            obj.put("promotionId", this.getPromotionId());
            obj.put("promotionType", this.getPromotionType());
            obj.put("promotionTypeName", PromotionType.getStatus(this.getPromotionType()).getName());
        }
        if (!this.isOrderPromotionOver()) {
            obj.put("orderPromotionId", this.getOrderPromotionId());
            obj.put("orderPromotionType", this.getOrderPromotionType() == null ? -1 : this.getOrderPromotionType());
            obj.put("orderPromotionTypeName", PromotionType.getStatus(this.getOrderPromotionType()).getName());
        }
        return obj;
    }

    public JSONObject toSimpleJson() {
        JSONObject obj = new JSONObject();
        obj.put("productName", this.getName());
        obj.put("img", this.getProductImageCover());
        obj.put("price", this.getCurrentPrice());
        obj.put("store", this.getStore());
        obj.put("mark", this.getMark());
        return obj;
    }

    public JSONObject toRelationJson() {
        JSONObject obj = new JSONObject();
        obj.put("productId", this.getProductId());
        obj.put("name", this.getName());
        obj.put("pic", this.getMainPic());
        obj.put("brand", this.getBrand());
        obj.put("store", this.getStore());
        obj.put("originalPrice", this.getOriginalPrice());
        obj.put("promotionPrice", this.getPromotionPrice());
        obj.put("flashPrice", this.getFlashPrice());
        obj.put("isOver", this.isPromotionOver());
        return obj;
    }

    public JSONObject toSectionJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getProductId());
        obj.put("frontPic", this.getProductImageCover());
        obj.put("shortTitle", this.getName());
        obj.put("price", this.getCurrentPrice());
        obj.put("salePrice", this.getMinPrice());
        obj.put("brand", this.getBrand());
        return obj;
    }

    /**
     * 商品当前价
     *
     * @return
     */
    public BigDecimal getCurrentPrice() {
        if (!this.isFlashPromotionOver()) {
            return this.getFlashPrice();
        } else if (!this.isPromotionOver()) {
            return this.getPromotionPrice();
        }
        return this.minPrice;
    }

    /**
     * 商品活动价
     *
     * @return
     */
    public BigDecimal getPromotionPrice() {
        if (this.getPromotionType() == null || this.isPromotionOver()) {
            return this.minPrice;
        }
        return processPromotion(this.minPrice, this.aPrice);
    }

    /**
     * 商品活动已结束
     *
     * @return
     */
    public boolean isPromotionOver() {
        Date date = new Date();
        if (this.getPromotionMark() == null || this.getPromotionMark() == 0) {
            return true;
        }
        if (this.getStartDate() == null || this.getEndDate() == null) {
            return true;
        }
        if (date.getTime() >= this.getStartDate().getTime() && date.getTime() <= this.getEndDate().getTime()) {
            return false;
        }
        return true;
    }

    /**
     * 订单活动是否结束
     *
     * @return
     */
    public boolean isOrderPromotionOver() {
        Date date = new Date();
        if (this.getOrderPromotionMark() == null || this.getOrderPromotionMark() == 0) {
            return true;
        }
        if (this.getOrderStartDate() == null || this.getOrderEndDate() == null) {
            return true;
        }
        if (date.getTime() >= this.getOrderStartDate().getTime()
                && date.getTime() <= this.getOrderEndDate().getTime()) {
            return false;
        }
        return true;
    }

    /**
     * 限时购已结束
     *
     * @return
     */
    public boolean isFlashPromotionOver() {
        Date date = new Date();
        if (this.getFlashMark() == null || this.getFlashMark() == 0) {
            return true;
        }
        if (this.getFlashStartDate() == null || this.getFlashEndDate() == null) {
            return true;
        }
        if (date.getTime() >= this.getFlashStartDate().getTime()
                && date.getTime() <= this.getFlashEndDate().getTime()) {
            return false;
        }
        return true;
    }

    /**
     * 拼团已结束
     *
     * @return
     */
    public boolean isCollagePromotionOver() {
        Date date = new Date();
        if (this.getCollageMark() == null || this.getCollageMark() == 0) {
            return true;
        }
        if (this.getCollageStartDate() == null || this.getCollageEndDate() == null) {
            return true;
        }
        if (date.getTime() >= this.getCollageStartDate().getTime()
                && date.getTime() <= this.getCollageEndDate().getTime()) {
            return false;
        }
        return true;
    }

    /**
     * 活动即将开始
     *
     * @return
     */
    public boolean isPromotionSoon() {
        Date date = new Date();
        if (this.getPromotionMark() != null && this.getPromotionMark() > 0 && this.getStartDate() != null
                && date.getTime() < this.getStartDate().getTime()) {
            return true;
        }
        return false;
    }

    public boolean isFlashPromotionSoon() {
        Date date = new Date();
        if (this.getFlashMark() == null || this.getFlashMark() == 0) {
            return true;
        }
        if (this.getFlashStartDate() == null || this.getFlashEndDate() == null) {
            return true;
        }
        if (date.getTime() < this.getFlashStartDate().getTime()) {
            return false;
        }
        return true;
    }

    /**
     * 即将价
     *
     * @return
     */
    public BigDecimal getSoonPrice() {
        if (this.isPromotionSoon()) {
            return processPromotion(this.minPrice, this.aPrice);
        }
        if (this.getPromotionType() != null && !this.isPromotionOver()) {
            return processPromotion(this.minPrice, this.aPrice);
        }
        return this.minPrice;
    }

    public JSONObject getSoonPromotion() {
        JSONObject obj = new JSONObject();
        if (!this.isFlashPromotionSoon() || !this.isFlashPromotionOver()) {
            obj.put("soonPromotionPrefix", "快抢价");
            obj.put("soonPromotionDate", this.getFlashStartDate().getTime());
            obj.put("soonPromotionPrice", this.getFlashPrice());
        } else if ((this.isPromotionSoon() || !this.isPromotionOver()) && this.getAdvance() != null
                && this.getAdvance() == 1 && StringUtils.isNotBlank(this.getPromotionPrefix())) {
            obj.put("soonPromotionPrefix", this.getPromotionPrefix());
            obj.put("soonPromotionDate", this.getStartDate().getTime());
            obj.put("soonPromotionPrice", this.getSoonPrice());
        }
        return obj;
    }

    /**
     * 处理活动
     *
     * @return
     */
    public BigDecimal processPromotion(BigDecimal salePrice, BigDecimal aPrice) {
        if (this.getPromotionType() == 0) {
            if (StringUtil.isBlack(this.promotionSolu)) {
                promotionSolu = "1";
            }
            BigDecimal rate = new BigDecimal(this.promotionSolu);
            return (salePrice.multiply(rate)).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (this.getPromotionType() == 1) {
            if (StringUtil.isBlack(this.promotionSolu)) {
                promotionSolu = "0";
            }
            BigDecimal off = new BigDecimal(this.promotionSolu);
            return salePrice.subtract(off);
        } else if (this.getPromotionType() == 4) {
            return aPrice;
        } else {
            return salePrice;
        }
    }

    /**
     * 颜色列表
     */
    public JSONArray getColors() {
        JSONArray colors = new JSONArray();
        String sp = this.getSalesproperty();
        if (sp != null) {
            JSONObject spJson = JSONObject.parseObject(sp);
            colors = spJson.getJSONArray("sp1");
        }
        return colors;
    }

    /**
     * 尺码列表
     */
    public JSONArray getSizes() {
        JSONArray sizes = new JSONArray();
        String sp = this.getSalesproperty();
        if (sp != null) {
            JSONObject spJson = JSONObject.parseObject(sp);
            sizes = spJson.getJSONArray("sp2");
        }
        return sizes;
    }

    /**
     * 0、正常商品，1、到货通知，2、下架商品，3、售空商品
     */
    @Deprecated
    public int analyseAppStatus() {
        if (this.getStatus() == 3) {
            return 1;
        }
        if (this.getMark() <= 0) {
            return 2;
        }
        if (this.getStore() <= 0) {
            return 3;
        }
        return 0;
    }

    public boolean isNotShow() {
        return mark <= 0 || search <= 0;
    }

}
