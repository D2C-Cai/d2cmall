// package com.d2c.product.search.model;
//
// import java.math.BigDecimal;
// import java.util.Date;
//
// import org.springframework.data.elasticsearch.annotations.Document;
//
// import com.alibaba.fastjson.JSONArray;
// import com.alibaba.fastjson.JSONObject;
// import com.d2c.common.search.consts.SearchConst;
// import com.d2c.common.search.model.PreSearchDO;
// import com.d2c.util.date.DateUtil;
//
// @Document(type = "product", indexName = SearchConst.DEFAULT_INDEX)
// public class ProductSearchDO extends PreSearchDO {
//
// private static final long serialVersionUID = 1L;
//
// /**
// * 商品ID
// */
// private Long productId;
// /**
// * 最低价
// */
// private BigDecimal minPrice;
// /**
// * 吊牌价
// */
// private BigDecimal originalPrice;
// /**
// * 活动价(就是销售价)
// */
// private BigDecimal promotionPrice;
// /**
// * 活动开始时间
// */
// private Date startDate;
// /**
// * 活动结束时间
// */
// private Date endDate;
// /**
// * 活动上下架
// */
// private Integer promotionMark;
// /**
// * 活动ID
// */
// private Long promotionId;
// /**
// * 活动名称
// */
// private String promotionName;
// /**
// * 搜索关键字
// */
// private String seo;
// /**
// * 名称
// */
// private String name;
// /**
// * 款号
// */
// private String inernalSn;
// /**
// * 设计师货号
// */
// private String externalSn;
// /**
// * 副标题
// */
// private String subTitle;
// /**
// * 设计师ID
// */
// private Long designerId;
// /**
// * 系列ID
// */
// private Long seriesId;
// /**
// * 一级分类
// */
// private String topCategory;
// /**
// * 二级分类
// */
// private String productCategory;
// /**
// * 一级分类ID
// */
// private Long topCategoryId;
// /**
// * 二级分类ID
// */
// private Long productCategoryId;
// /**
// * 标签，多个按","分隔
// */
// private String tags;
// /**
// * 站内排序
// */
// private Integer sort;
// /**
// * 是否有库存 0：无库存 1：自营pop都有
// */
// private Integer store;
// /**
// * 是否有现货 0：没有 1：有 （版本兼容先设置初始值 TODO）
// */
// private Integer spot;
// /**
// * 是否建立索引
// */
// private Integer search;
// /**
// * 是否允许加入购物车
// */
// private Integer cart;
// /**
// * 状态
// */
// private Integer mark;
// /**
// * 置顶
// */
// private Integer topical;
// /**
// * 上架时间
// */
// private Date upMarketDate;
// /**
// * 系列上架时间
// */
// private Date seriesUpDate;
// /**
// * 设计师名称
// */
// private String designer;
// /**
// * 品牌名称
// */
// private String brand;
// /**
// * 品牌设计风格
// */
// private String brandStyle;
// /**
// * 分类名称
// */
// private String categoryName;
// /**
// * 商品图片
// */
// private String productImageCover;
// /**
// * 销量
// */
// private Integer sales;
// /**
// * 广告图片
// */
// private String introPic;
// /**
// * 商品状态
// */
// private Integer status;
// /**
// * 评论数量
// */
// private Integer comments;
// /**
// * 咨询数量
// */
// private Integer consults;
// /**
// * 是否是收藏的商品
// */
// private Integer collectioned;
// /**
// * 销售属性
// */
// private String salesproperty;
// /**
// * 销售类型 SPOT(现货) PRESELL(预售) CUSTOM(定制)
// */
// private String productSellType;
// /**
// * 是否售后
// */
// private Integer after;
// /**
// * 是否门店试衣
// */
// private Integer subscribe;
// /**
// * 商品推荐值
// */
// private Double recomScore;
// /**
// * 是否运营推荐商品 1:是 0:否
// */
// private Integer operRecom;
// /**
// * 商品介绍
// */
// private String introduction;
// /**
// * boss推荐语
// */
// private String recommendation;
// /**
// * 最近一个月销量
// */
// private Integer recentlySales;
// /**
// * 促销类型
// */
// private Integer promotionType;
//
// public Long getProductId() {
// return productId;
// }
//
// public void setProductId(Long productId) {
// this.productId = productId;
// }
//
// public BigDecimal getMinPrice() {
// return minPrice;
// }
//
// public void setMinPrice(BigDecimal minPrice) {
// this.minPrice = minPrice;
// }
//
// public BigDecimal getOriginalPrice() {
// return originalPrice;
// }
//
// public void setOriginalPrice(BigDecimal originalPrice) {
// this.originalPrice = originalPrice;
// }
//
// public BigDecimal getPromotionPrice() {
// return promotionPrice;
// }
//
// public void setPromotionPrice(BigDecimal promotionPrice) {
// this.promotionPrice = promotionPrice;
// }
//
// public Date getStartDate() {
// return startDate;
// }
//
// public void setStartDate(Date startDate) {
// this.startDate = startDate;
// }
//
// public Date getEndDate() {
// return endDate;
// }
//
// public void setEndDate(Date endDate) {
// this.endDate = endDate;
// }
//
// public Integer getPromotionMark() {
// return promotionMark;
// }
//
// public void setPromotionMark(Integer promotionMark) {
// this.promotionMark = promotionMark;
// }
//
// public Long getPromotionId() {
// return promotionId;
// }
//
// public void setPromotionId(Long promotionId) {
// this.promotionId = promotionId;
// }
//
// public String getPromotionName() {
// return promotionName;
// }
//
// public void setPromotionName(String promotionName) {
// this.promotionName = promotionName;
// }
//
// public String getSeo() {
// return seo;
// }
//
// public void setSeo(String seo) {
// this.seo = seo;
// }
//
// public String getName() {
// return name;
// }
//
// public void setName(String name) {
// this.name = name;
// }
//
// public String getInernalSn() {
// return inernalSn;
// }
//
// public void setInernalSn(String inernalSn) {
// this.inernalSn = inernalSn;
// }
//
// public String getSubTitle() {
// return subTitle;
// }
//
// public void setSubTitle(String subTitle) {
// this.subTitle = subTitle;
// }
//
// public Long getDesignerId() {
// return designerId;
// }
//
// public void setDesignerId(Long designerId) {
// this.designerId = designerId;
// }
//
// public String getTopCategory() {
// return topCategory;
// }
//
// public void setTopCategory(String topCategory) {
// this.topCategory = topCategory;
// }
//
// public String getProductCategory() {
// return productCategory;
// }
//
// public void setProductCategory(String productCategory) {
// this.productCategory = productCategory;
// }
//
// public Long getTopCategoryId() {
// return topCategoryId;
// }
//
// public void setTopCategoryId(Long topCategoryId) {
// this.topCategoryId = topCategoryId;
// }
//
// public Long getProductCategoryId() {
// return productCategoryId;
// }
//
// public void setProductCategoryId(Long productCategoryId) {
// this.productCategoryId = productCategoryId;
// }
//
// public String getTags() {
// return tags;
// }
//
// public void setTags(String tags) {
// this.tags = tags;
// }
//
// public Date getSeriesUpDate() {
// return seriesUpDate;
// }
//
// public Date getUpMarketDate() {
// return upMarketDate;
// }
//
// public void setUpMarketDate(Date upMarketDate) {
// this.upMarketDate = upMarketDate;
// }
//
// public void setSeriesUpDate(Date seriesUpDate) {
// this.seriesUpDate = seriesUpDate;
// }
//
// public String getDesigner() {
// return designer;
// }
//
// public void setDesigner(String designer) {
// this.designer = designer;
// }
//
// public Long getSeriesId() {
// return seriesId;
// }
//
// public void setSeriesId(Long seriesId) {
// this.seriesId = seriesId;
// }
//
// public String getBrand() {
// return brand;
// }
//
// public void setBrand(String brand) {
// this.brand = brand;
// }
//
// public String getCategoryName() {
// return categoryName;
// }
//
// public void setCategoryName(String categoryName) {
// this.categoryName = categoryName;
// }
//
// public String getProductImageCover() {
// return productImageCover;
// }
//
// public void setProductImageCover(String productImageCover) {
// this.productImageCover = productImageCover;
// }
//
// public String getIntroPic() {
// return introPic;
// }
//
// public void setIntroPic(String introPic) {
// this.introPic = introPic;
// }
//
// public Integer getSort() {
// return sort;
// }
//
// public void setSort(Integer sort) {
// this.sort = sort;
// }
//
// public Integer getStore() {
// return store;
// }
//
// public void setStore(Integer store) {
// this.store = store;
// }
//
// public Integer getSpot() {
// return spot;
// }
//
// public void setSpot(Integer spot) {
// this.spot = spot;
// }
//
// public Integer getSearch() {
// return search;
// }
//
// public void setSearch(Integer search) {
// this.search = search;
// }
//
// public Integer getMark() {
// return mark;
// }
//
// public void setMark(Integer mark) {
// this.mark = mark;
// }
//
// public Integer getTopical() {
// return topical;
// }
//
// public void setTopical(Integer topical) {
// this.topical = topical;
// }
//
// public Integer getSales() {
// return sales;
// }
//
// public void setSales(Integer sales) {
// this.sales = sales;
// }
//
// public Integer getStatus() {
// return status;
// }
//
// public void setStatus(Integer status) {
// this.status = status;
// }
//
// public Integer getCart() {
// return cart;
// }
//
// public void setCart(Integer cart) {
// this.cart = cart;
// }
//
// public String getSalesproperty() {
// return salesproperty;
// }
//
// public void setSalesproperty(String salesproperty) {
// this.salesproperty = salesproperty;
// }
//
// /**
// * 获取商品封面图(头图中的第一张)
// */
// public String getMainPic() {
// String mainPic = "";
// if (this.productImageCover != null) {
// mainPic = productImageCover.split(",")[0];
// }
// return mainPic;
// }
//
// public boolean isOver() {
// Date date = new Date();
// if (this.getPromotionMark() == null || this.getPromotionMark() == 0) {
// return true;
// }
// if (this.getStartDate() == null || this.getEndDate() == null) {
// return true;
// }
// if (date.getTime() >= this.getStartDate().getTime() && date.getTime() <=
// this.getEndDate().getTime()) {
// return false;
// }
// return true;
// }
//
// public String getExternalSn() {
// return externalSn;
// }
//
// public void setExternalSn(String externalSn) {
// this.externalSn = externalSn;
// }
//
// public Integer getComments() {
// return comments;
// }
//
// public void setComments(Integer comments) {
// this.comments = comments;
// }
//
// public Integer getConsults() {
// return consults;
// }
//
// public void setConsults(Integer consults) {
// this.consults = consults;
// }
//
// public Integer getCollectioned() {
// return collectioned;
// }
//
// public void setCollectioned(Integer collectioned) {
// this.collectioned = collectioned;
// }
//
// public String getProductSellType() {
// return productSellType;
// }
//
// public void setProductSellType(String productSellType) {
// this.productSellType = productSellType;
// }
//
// public Integer getAfter() {
// return after;
// }
//
// public void setAfter(Integer after) {
// this.after = after;
// }
//
// public Integer getSubscribe() {
// return subscribe;
// }
//
// public void setSubscribe(Integer subscribe) {
// this.subscribe = subscribe;
// }
//
// public Double getRecomScore() {
// return recomScore;
// }
//
// public void setRecomScore(Double recomScore) {
// this.recomScore = recomScore;
// }
//
// public Integer getOperRecom() {
// return operRecom;
// }
//
// public Boolean getIsOperRecom() {
// if (operRecom != null) {
// return operRecom > 0;
// }
// return null;
// }
//
// public void setOperRecom(Integer operRecom) {
// this.operRecom = operRecom;
// }
//
// public String getBrandStyle() {
// return brandStyle;
// }
//
// public void setBrandStyle(String brandStyle) {
// this.brandStyle = brandStyle;
// }
//
// public String getIntroduction() {
// return introduction;
// }
//
// public void setIntroduction(String introduction) {
// this.introduction = introduction;
// }
//
// public String getRecommendation() {
// return recommendation;
// }
//
// public void setRecommendation(String recommendation) {
// this.recommendation = recommendation;
// }
//
// public Integer getRecentlySales() {
// return recentlySales;
// }
//
// public void setRecentlySales(Integer recentlySales) {
// this.recentlySales = recentlySales;
// }
//
// public Integer getPromotionType() {
// return promotionType;
// }
//
// public void setPromotionType(Integer promotionType) {
// this.promotionType = promotionType;
// }
//
// public JSONObject toJson() {
// JSONObject obj = new JSONObject();
// obj.put("id", this.getId());
// obj.put("img", this.getProductImageCover());
// obj.put("price", this.getCurrentPrice());
// obj.put("minPrice", this.getCurrentPrice());
// obj.put("originalPrice", this.getOriginalPrice());
// obj.put("promotionPrice", this.getPromotionPrice());
// obj.put("salePrice", this.getMinPrice());
// obj.put("name", this.getName());
// obj.put("store", this.getStore());
// obj.put("mark", this.getMark());
// obj.put("brand", this.getBrand());
// obj.put("designer", this.getDesigner());
// obj.put("designerId", this.getDesignerId());
// obj.put("consults", this.getConsults());
// obj.put("comments", this.getComments());
// obj.put("collectioned", this.getCollectioned());
// obj.put("colors", this.getColors());
// obj.put("sizes", this.getSizes());
// obj.put("isCart", this.getCart());
// obj.put("isTopical", 1 == this.getTopical());
// obj.put("isCrowd", 2 == this.getStatus());
// obj.put("isSpot", 1 == this.getSpot());
// obj.put("productSellType", this.getProductSellType());
// obj.put("categoryId", this.getProductCategoryId());
// obj.put("categoryName", this.getCategoryName());
// obj.put("recomScore", this.getRecomScore());
// if (!this.isOver()) {
// obj.put("promotionType", this.getPromotionType());
// obj.put("promotionId", this.getPromotionId());
// }
// return obj;
// }
//
// public JSONObject toPromotionJson() {
// JSONObject productPromotion = new JSONObject();
// productPromotion.put("promotionName", this.getPromotionName());
// productPromotion.put("promotionUrl", "/promotion/" + this.getPromotionId());
// productPromotion.put("startTime", this.getStartDate() == null ? "" :
// DateUtil.second2str2(this.getStartDate()));
// productPromotion.put("endTime", this.getEndDate() == null ? "" :
// DateUtil.second2str2(this.getEndDate()));
// productPromotion.put("promotionType", this.getPromotionType());
// productPromotion.put("promotionScope", 0);
// productPromotion.put("isOver", this.isOver());
// return productPromotion;
// }
//
// public JSONObject toRelationJson() {
// JSONObject obj = new JSONObject();
// obj.put("productId", this.getProductId());
// obj.put("originalPrice", this.getOriginalPrice());
// obj.put("promotionPrice", this.getPromotionPrice());
// obj.put("isOver", this.isOver());
// obj.put("pic", this.getMainPic());
// obj.put("brand", this.getBrand());
// obj.put("name", this.getName());
// return obj;
// }
//
// public BigDecimal getCurrentPrice() {
// if (!this.isOver()) {
// return this.promotionPrice;
// }
// return this.minPrice;
// }
//
// /**
// * 颜色列表
// */
// public JSONArray getColors() {
// JSONArray colors = new JSONArray();
// String sp = this.getSalesproperty();
// if (sp != null) {
// JSONObject spJson = JSONObject.parseObject(sp);
// colors = spJson.getJSONArray("sp1");
// }
// return colors;
// }
//
// /**
// * 尺码列表
// */
// public JSONArray getSizes() {
// JSONArray sizes = new JSONArray();
// String sp = this.getSalesproperty();
// if (sp != null) {
// JSONObject spJson = JSONObject.parseObject(sp);
// sizes = spJson.getJSONArray("sp2");
// }
// return sizes;
// }
// }
