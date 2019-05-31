package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 */
@Table(name = "p_product")
public class Product extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 商品编码分类
     */
    private String saleCategory = SaleCategory.NORMALPRODUCT.name();
    /**
     * 生产类型
     */
    @AssertColumn("购买类型不能为空")
    private String productSaleType;
    /**
     * 销售类型
     */
    private String productSellType;
    /**
     * 贸易类型 COMMON(一般) CROSS(跨境)
     */
    private String productTradeType;
    /**
     * 贸易方式 0:直邮，1、保税，2、海淘 ，3、国内贸易 ，4、个人清关
     */
    private Integer importType = 0;
    /**
     * 商品来源
     */
    private String source;
    /**
     * 1、正常商品，3、货到商品，5、秒杀商品，-1、礼包商品
     */
    private Integer status = 1;
    /**
     * D2C货号
     */
    @AssertColumn("货号不能为空")
    private String inernalSn;
    /**
     * 商品名称
     */
    @AssertColumn("商品名称不能为空")
    private String name;
    /**
     * 商品副标题
     */
    private String subTitle;
    /**
     * 商品头图
     */
    private String introPic;
    /**
     * 头图样式 0:长方形，1、正方形
     */
    private Integer picStyle = 0;
    /**
     * 发货说明
     */
    @AssertColumn(value = "商品备注不得超过40个字符", nullable = true, max = 40)
    private String remark;
    /**
     * 售后说明
     */
    private String afterMemo;
    /**
     * 商品 SEO
     */
    private String seo;
    /**
     * 外部货号/设计师货号
     */
    private String externalSn;
    /**
     * 外部名称/设计师名称
     */
    private String originalName;
    /**
     * 系列 ID
     */
    private Long seriesId;
    /**
     * 设计师ID
     */
    @AssertColumn("店铺不能为空")
    private Long designerId;
    /**
     * 设计师编号
     */
    private String designerCode;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 一级分类ID
     */
    private Long topCategoryId;
    /**
     * 一级分类
     */
    private String topCategory;
    /**
     * 二级分类ID
     */
    @AssertColumn("商品类型不能为空")
    private Long productCategoryId;
    /**
     * 二级分类
     */
    private String productCategory;
    /**
     * 销售属性
     */
    private String salesproperty;
    /**
     * 主规格组ID
     */
    private Long sp1GroupId;
    /**
     * 副规格组ID
     */
    private Long sp2GroupId;
    /**
     * 关联作品的ID非必选
     */
    private Long compositionId;
    /**
     * 标签 多个按","分隔
     */
    private String tags;
    /**
     * 是否允许添加到购物车 1:是 0:否
     */
    private Integer cart = 1;
    /**
     * 是否允许使用优惠券 1:是 0:否
     */
    private Integer coupon = 1;
    /**
     * 是否允许退换货 1:是 0:否
     */
    private Integer after = 1;
    /***
     * 是否允许门店试衣 1:是 0:否
     */
    private Integer subscribe = 1;
    /**
     * 是否投放广告 1:是 0:否
     */
    private Integer ad = 0;
    /**
     * 是否允许货到付款 1:是 0:否
     */
    private Integer cod = 0;
    /**
     * 是否主推商品 1:是 0:否
     */
    private Integer topical = 0;
    /**
     * 定时上下架 1:是 0:否
     */
    private Integer timing = 0;
    /**
     * 是否允许搜索 1:是 0:否
     */
    private Integer search = 1;
    /**
     * 商品状态标志（0:下架, 1:上架, -1:删除）
     */
    private Integer mark = 0;
    /**
     * 是否包税 1:包税 0:不包税
     */
    private Integer taxation = 1;
    /**
     * 是否包邮 1:包邮 0:不包邮
     */
    private Integer shipping = 0;
    /**
     * 上架时间
     */
    private Date upMarketDate;
    /**
     * 下架时间
     */
    private Date downMarketDate;
    /**
     * 上架人
     */
    private String upMan;
    /**
     * 下架人
     */
    private String downMan;
    /**
     * 预计发货时间（日期）
     */
    private Date estimateDate;
    /**
     * 预计发货时间（天）
     */
    private Integer estimateDay;
    /**
     * 店铺排序
     */
    private Integer sort = 0;
    /**
     * 同步时间
     */
    private String syncStamp;
    /**
     * 最低价
     */
    private BigDecimal minPrice;
    /**
     * 最高价
     */
    private BigDecimal maxPrice;
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    /**
     * 吊牌价格
     */
    @AssertColumn(value = "商品吊牌价不能为空", mineq = 0)
    private BigDecimal originalPrice;
    /***
     * 一口价价格
     */
    private BigDecimal aPrice;
    /**
     * 限时购价格
     */
    private BigDecimal flashPrice;
    /**
     * 拼团价格
     */
    private BigDecimal collagePrice;
    /**
     * 考拉价格
     */
    private BigDecimal kaolaPrice;
    /**
     * 税费金额，多个,号隔开
     */
    private String taxPrice;
    /**
     * 商品活动ID
     */
    private Long goodPromotionId;
    /**
     * 商品活动排序
     */
    private Integer gpSort;
    /**
     * 订单活动ID
     */
    private Long orderPromotionId;
    /**
     * 订单活动排序
     */
    private Integer opSort;
    /**
     * 限时购活动ID
     */
    private Long flashPromotionId;
    /**
     * 限时购活动排序
     */
    private Integer fpSort;
    /**
     * 拼团活动ID
     */
    private Long collagePromotionId;
    /**
     * 真实库存
     */
    private Integer syncStore = 0;
    /**
     * 第三方库存
     */
    private Integer popStore = 0;
    /**
     * 视频地址
     */
    private String video;
    /**
     * 商品推荐值
     */
    private Double recom;
    /**
     * 是否运营推荐商品 1:是 0:否
     */
    @Column(updatable = false)
    private Integer operRecom = 0;
    /**
     * 间接返利系数
     */
    @Deprecated
    @AssertColumn("间接返利比不能为空")
    private BigDecimal firstRatio = new BigDecimal(0);
    /**
     * 直接返利系数
     */
    @AssertColumn("直接返利比不能为空")
    private BigDecimal secondRatio = new BigDecimal(0);
    /**
     * 总返利系数
     */
    @AssertColumn("总返利系数不能为空")
    private BigDecimal grossRatio = new BigDecimal(1);

    public String getStatusName() {
        switch (status) {
            case 1:
                return "正常商品";
            case 3:
                return "货到商品";
            case 5:
                return "秒杀商品";
            case -1:
                return "礼包商品";
        }
        return "";
    }

    public void setStatusName() {
    }

    /**
     * 获取商品价格区间(最低价格 ~ 最高价格)
     */
    public String getPriceRegion() {
        BigDecimal min = this.getMinPrice();
        BigDecimal max = this.getMaxPrice();
        return min + "~" + max;
    }

    public void setPriceRegion() {
    }

    /**
     * 获取商品销售价
     */
    public BigDecimal getSalePrice() {
        return minPrice;
    }

    public void setSalePrice() {
    }

    public String getSaleCategory() {
        return saleCategory;
    }

    public void setSaleCategory(String saleCategory) {
        this.saleCategory = saleCategory;
    }

    public String getProductSaleType() {
        return productSaleType;
    }

    public void setProductSaleType(String productSaleType) {
        this.productSaleType = productSaleType;
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

    public Integer getImportType() {
        return importType;
    }

    public void setImportType(Integer importType) {
        this.importType = importType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInernalSn() {
        return inernalSn;
    }

    public void setInernalSn(String inernalSn) {
        this.inernalSn = inernalSn;
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

    public String getIntroPic() {
        return introPic;
    }

    public void setIntroPic(String introPic) {
        this.introPic = introPic;
    }

    public Integer getPicStyle() {
        return picStyle;
    }

    public void setPicStyle(Integer picStyle) {
        this.picStyle = picStyle;
    }

    public String getAfterMemo() {
        return afterMemo;
    }

    public void setAfterMemo(String afterMemo) {
        this.afterMemo = afterMemo;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        this.designerId = designerId;
    }

    public String getDesignerCode() {
        return designerCode;
    }

    public void setDesignerCode(String designerCode) {
        this.designerCode = designerCode;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public Long getTopCategoryId() {
        return topCategoryId;
    }

    public void setTopCategoryId(Long topCategoryId) {
        this.topCategoryId = topCategoryId;
    }

    public String getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(String topCategory) {
        this.topCategory = topCategory;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getSalesproperty() {
        return salesproperty;
    }

    public void setSalesproperty(String salesproperty) {
        this.salesproperty = salesproperty;
    }

    public Long getSp1GroupId() {
        return sp1GroupId;
    }

    public void setSp1GroupId(Long sp1GroupId) {
        this.sp1GroupId = sp1GroupId;
    }

    public Long getSp2GroupId() {
        return sp2GroupId;
    }

    public void setSp2GroupId(Long sp2GroupId) {
        this.sp2GroupId = sp2GroupId;
    }

    public Long getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Long compositionId) {
        this.compositionId = compositionId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
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

    public Integer getAd() {
        return ad;
    }

    public void setAd(Integer ad) {
        this.ad = ad;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public Integer getTopical() {
        return topical;
    }

    public void setTopical(Integer topical) {
        this.topical = topical;
    }

    public Integer getTiming() {
        return timing;
    }

    public void setTiming(Integer timing) {
        this.timing = timing;
    }

    public Integer getSearch() {
        return search;
    }

    public void setSearch(Integer search) {
        this.search = search;
    }

    public Integer getTaxation() {
        return taxation;
    }

    public void setTaxation(Integer taxation) {
        this.taxation = taxation;
    }

    public Integer getShipping() {
        return shipping;
    }

    public void setShipping(Integer shipping) {
        this.shipping = shipping;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Date getUpMarketDate() {
        return upMarketDate;
    }

    public void setUpMarketDate(Date upMarketDate) {
        this.upMarketDate = upMarketDate;
    }

    public Date getDownMarketDate() {
        return downMarketDate;
    }

    public void setDownMarketDate(Date downMarketDate) {
        this.downMarketDate = downMarketDate;
    }

    public String getUpMan() {
        return upMan;
    }

    public void setUpMan(String upMan) {
        this.upMan = upMan;
    }

    public String getDownMan() {
        return downMan;
    }

    public void setDownMan(String downMan) {
        this.downMan = downMan;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Integer getEstimateDay() {
        return estimateDay;
    }

    public void setEstimateDay(Integer estimateDay) {
        this.estimateDay = estimateDay;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSyncStamp() {
        return syncStamp;
    }

    public void setSyncStamp(String syncStamp) {
        this.syncStamp = syncStamp;
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

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
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

    public BigDecimal getKaolaPrice() {
        return kaolaPrice;
    }

    public void setKaolaPrice(BigDecimal kaolaPrice) {
        this.kaolaPrice = kaolaPrice;
    }

    public String getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Long getGoodPromotionId() {
        return goodPromotionId;
    }

    public void setGoodPromotionId(Long goodPromotionId) {
        this.goodPromotionId = goodPromotionId;
    }

    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
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

    public Integer getSyncStore() {
        return syncStore;
    }

    public void setSyncStore(Integer syncStore) {
        this.syncStore = syncStore;
    }

    public Integer getPopStore() {
        return popStore;
    }

    public void setPopStore(Integer popStore) {
        this.popStore = popStore;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Double getRecom() {
        return recom;
    }

    public void setRecom(Double recom) {
        this.recom = recom;
    }

    public Integer getOperRecom() {
        return operRecom;
    }

    public void setOperRecom(Integer operRecom) {
        this.operRecom = operRecom;
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

    /**
     * 获取商品封面图(头图中的第一张)
     */
    public String getProductImageCover() {
        String mainPic = "";
        if (this.introPic != null) {
            mainPic = introPic.split(",")[0];
        }
        return mainPic;
    }

    public void setProductImageCover() {
    }

    /**
     * 商品图片
     */
    public String getProductImageListFirst() {
        if (this.introPic != null) {
            return introPic.split(",")[0];
        }
        return null;
    }

    public void setProductImageListFirst() {
    }

    /**
     * 商品图片
     */
    public String[] getProductImageList() {
        if (this.introPic != null) {
            return introPic.split(",");
        }
        return null;
    }

    public void setProductImageList() {
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        if (remark != null) {
            this.remark = remark.trim();
        }
        this.remark = remark;
    }

    /**
     * NORMALPRODUCT(D2C编码) POPPRODUCT(任意编码)
     */
    public static enum SaleCategory {
        NORMALPRODUCT, POPPRODUCT;

        public static SaleCategory fromString(String value) {
            try {
                return SaleCategory.valueOf(value.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * 生产类型 BUYOUT(买断) CONSIGN(代销) SELF(自产) COOPERATIVE(合作款)
     */
    public static enum ProductSaleType {
        BUYOUT, CONSIGN, SELF, COOPERATIVE;
    }

    /**
     * 销售类型 SPOT(现货) PRESELL(预售) CUSTOM(定制)
     */
    public static enum ProductSellType {
        SPOT, PRESELL, CUSTOM;
    }

    /**
     * 贸易类型 COMMON(一般) CROSS(跨境)
     */
    public static enum ProductTradeType {
        COMMON, CROSS;
    }

    /**
     * 商品来源 D2CMALL(D2C) KAOLA(考拉),CAOMEI(草莓),MILAN(米兰)
     */
    public static enum ProductSource {
        D2CMALL, KAOLA, CAOMEI, MILAN;
    }

}