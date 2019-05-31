package com.d2c.product.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.product.model.Product.SaleCategory;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

/**
 * 备选商品
 */
@Table(name = "p_product_option")
public class ProductOption extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 线上该商品ID
     */
    private Long onlineProductId;
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
     * 商品状态
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
     * 是否允许退换货 1:是 0:否
     */
    private Integer after = 1;
    /**
     * 预计发货时间（日期）
     */
    private Date estimateDate;
    /**
     * 预计发货时间（天）
     */
    private Integer estimateDay;
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
     * 视频地址
     */
    private String video;
    /**
     * 尺码对应表
     */
    private String sizeJson;
    /**
     * 试穿报告
     */
    private String tryOnReportJson;
    /**
     * PC端详情
     */
    private String description;
    /**
     * 手机端详情
     */
    private String mobileDesc;
    /**
     * 商品介绍
     */
    private String introduction;
    /**
     * boss推荐语
     */
    private String recommendation;
    /**
     * -1拒绝 0待审核 1已提交审核 8已成功更新
     */
    private Integer mark;
    /***
     * 是否允许门店试衣 1:是 0:否
     */
    private Integer subscribe = 1;
    /**
     * 是否允许添加到购物车 1:是 0:否
     */
    private Integer cart = 1;
    /**
     * 是否允许货到付款 1:是 0:否
     */
    private Integer cod = 0;
    /**
     * 限时购价格
     */
    private BigDecimal flashPrice;
    /**
     * 一级返利系数
     */
    private BigDecimal firstRatio = new BigDecimal(0);
    /**
     * 二级返利系数
     */
    private BigDecimal secondRatio = new BigDecimal(0);
    /**
     * 总返利系数
     */
    private BigDecimal grossRatio = new BigDecimal(1);
    /**
     * 拒绝原因
     */
    private String refuseReason;
    /**
     * pop库存
     */
    private Integer popStore;
    /**
     * 图片类型0:长方形，1、正方形
     */
    private Integer picStyle;
    /**
     * 贸易类型 COMMON(一般)
     */
    private String productTradeType = "COMMON";
    /**
     * 商品来源 D2CMALL(D2C)
     */
    private String source = "D2CMALL";

    public Long getOnlineProductId() {
        return onlineProductId;
    }

    public void setOnlineProductId(Long onlineProductId) {
        this.onlineProductId = onlineProductId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getAfter() {
        return after;
    }

    public void setAfter(Integer after) {
        this.after = after;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getSizeJson() {
        return sizeJson;
    }

    public void setSizeJson(String sizeJson) {
        this.sizeJson = sizeJson;
    }

    public String getTryOnReportJson() {
        return tryOnReportJson;
    }

    public void setTryOnReportJson(String tryOnReportJson) {
        this.tryOnReportJson = tryOnReportJson;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobileDesc() {
        if (mobileDesc != null) {
            mobileDesc = mobileDesc.trim();
        }
        return mobileDesc;
    }

    public void setMobileDesc(String mobileDesc) {
        this.mobileDesc = mobileDesc;
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

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getCart() {
        return cart;
    }

    public void setCart(Integer cart) {
        this.cart = cart;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public BigDecimal getFlashPrice() {
        return flashPrice;
    }

    public void setFlashPrice(BigDecimal flashPrice) {
        this.flashPrice = flashPrice;
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

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Integer getPopStore() {
        return popStore;
    }

    public void setPopStore(Integer popStore) {
        this.popStore = popStore;
    }

    public Integer getPicStyle() {
        return picStyle;
    }

    public void setPicStyle(Integer picStyle) {
        this.picStyle = picStyle;
    }

    public String getProductTradeType() {
        return productTradeType;
    }

    public void setProductTradeType(String productTradeType) {
        this.productTradeType = productTradeType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public Integer getPop() {
        Integer pop = 0;
        if (SaleCategory.NORMALPRODUCT.name().equals(this.getSaleCategory())) {
            pop = 0;
        } else {
            pop = 1;
        }
        return pop;
    }

    /**
     * 颜色列表
     */
    public String getColors() {
        String colors = new String();
        String sp = this.getSalesproperty();
        if (sp != null) {
            JSONObject spJson = JSONObject.parseObject(sp);
            colors = spJson.getJSONArray("sp1").toJSONString();
        }
        return colors;
    }

    /**
     * 尺码列表
     */
    public String getSizes() {
        String sizes = new String();
        String sp = this.getSalesproperty();
        if (sp != null) {
            JSONObject spJson = JSONObject.parseObject(sp);
            sizes = spJson.getJSONArray("sp2").toJSONString();
        }
        return sizes;
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

    /**
     * 获取商品价格区间(最低价格 ~ 最高价格)
     */
    public String getPriceRegion() {
        BigDecimal min = this.getMinPrice();
        BigDecimal max = this.getMaxPrice();
        return min + "~" + max;
    }

    /**
     * 线上商品除了Product类外的其他字段
     */
    @JsonIgnore
    public JSONObject getProductOtherJson() {
        JSONObject obj = new JSONObject();
        obj.put("sizeJson", this.getSizeJson());
        obj.put("tryOnReportJson", this.getTryOnReportJson());
        obj.put("introduction", this.getIntroduction());
        obj.put("recommendation", this.getRecommendation());
        obj.put("description", this.getDescription());
        obj.put("mobileDesc", this.getMobileDesc());
        return obj;
    }

    /**
     * 尺码对应表
     */
    @JsonIgnore
    public JSONObject getSizetable() {
        JSONObject sizetable = new JSONObject();
        if (StringUtils.isNotBlank(getSizeJson())) {
            JSONArray heads = new JSONArray();
            JSONArray bodys = new JSONArray();
            JSONArray body = null;
            JSONObject sizeJson = JSONObject.parseObject(getSizeJson());
            heads = sizeJson.getJSONArray("header");
            heads.add(0, "本款尺码");
            JSONObject data = sizeJson.getJSONObject("data");
            Iterator<?> it = data.keySet().iterator();
            Iterator<?> subIt = null;
            String dataKey = null;
            String subDataKey = null;
            JSONObject subData = null;
            while (it.hasNext()) {
                body = new JSONArray();
                dataKey = String.valueOf(it.next());
                body.add(dataKey);
                subData = data.getJSONObject(dataKey);
                subIt = subData.keySet().iterator();
                while (subIt.hasNext()) {
                    subDataKey = String.valueOf(subIt.next());
                    body.add(subData.getString(subDataKey));
                }
                bodys.add(body);
            }
            sizetable.put("heads", heads);
            sizetable.put("bodys", bodys);
        }
        return sizetable;
    }

}
