package com.d2c.product.query;

import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.query.model.RoleQuery;
import com.d2c.product.search.query.ProductProSearchQuery;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ProductSearcher extends BaseQuery implements RoleQuery {

    private static final long serialVersionUID = 1L;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 设计师ID
     */
    private Long designerId;
    /**
     * 系列ID
     */
    private Long seriesId;
    /**
     * 顶级分类ID
     */
    private Long topId;
    /**
     * 二级分类ID
     */
    private Long categoryId;
    /**
     * 商品标签ID
     */
    private Long tagId;
    /**
     * 商品活动ID
     */
    private Long promotionId;
    /**
     * 订单活动ID
     */
    private Long orderPromotionId;
    /**
     * 组合活动ID
     */
    private Long changePromotionId;
    /**
     * 限时购活动ID
     */
    private Long flashPromotionId;
    /**
     * 相关商品ID
     */
    private Long relationProductId;
    /**
     * 设计师ID
     */
    private List<Long> designers = new ArrayList<>();
    /**
     * 分类ID
     */
    private List<Long> categoryIds = new ArrayList<>();
    /**
     * 商品名称
     */
    private String name;
    /**
     * D2C货号
     */
    private String inernalSn;
    /**
     * 商品SKU
     */
    private String skuSn;
    /**
     * 设计师货号
     */
    private String externalSn;
    /**
     * 设计师名称
     */
    private String designerName;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 商品编码分类 NORMALPRODUCT, POPPRODUCT
     */
    private String saleCategory;
    /**
     * 购买模式 BUYOUT, CONSIGN, SELF
     */
    private String productSaleType;
    /**
     * 销售类型 SPOT, PRESELL, CUSTOM
     */
    private String productSellType;
    /**
     * 贸易类型 COMMON, CROSS
     */
    private String productTradeType;
    /**
     * 商品来源 D2CMALL, KAOL
     */
    private String source;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * D2C货号
     */
    private String[] sn;
    /**
     * 商品状态
     */
    private Integer status;
    /**
     * 上下架状态
     */
    private Integer mark;
    /**
     * 是否广告
     */
    private Integer ad;
    /**
     * 是否主推
     */
    private Integer topical;
    /**
     * 是否推荐
     */
    private Integer operRecom;
    /**
     * 是否试衣
     */
    private Integer subscribe;
    /**
     * 是否支持售后
     */
    private Boolean isAfter;
    /**
     * 是否有活动
     */
    private Boolean hasPromotion;
    /**
     * 是否有返利
     */
    private Integer hasRebate;
    /**
     * 库存警告
     */
    private Integer warnStore;
    /**
     * 是否已关联活动或商品标签
     */
    private Integer relate;
    /**
     * 是否过期 ，1表示商品的预计发货时间在过期范围内
     */
    private Integer expired;
    /**
     * 上下架状态
     */
    private Integer[] marks;
    /**
     * 上架时间
     */
    private Date upMarketDate;
    /**
     * 下架时间
     */
    private Date downMarketDate;
    /**
     * 修改时间大于此时间
     */
    private Date lastDate;
    /**
     * 过期的时间范围
     */
    private Date expiredDate;
    /**
     * 最高价
     */
    private BigDecimal maxPrice;
    /**
     * 最低价
     */
    private BigDecimal promotionPrice;
    /**
     * 同步时间戳
     */
    private String syncStamp;
    /**
     * 排列顺序
     */
    private String orderByStr;
    /**
     * 库存量
     */
    private Integer beginStore;
    private Integer endStore;
    /**
     * 上架时间
     */
    private Date beginUpDate;
    private Date endUpDate;
    /**
     * 下架时间
     */
    private Date beginDownDate;
    private Date endDownDate;
    /**
     * 价格区间
     */
    private Integer min;
    private Integer max;
    /**
     * 商品推荐值区间
     */
    private Double minRecom;
    private Double maxRecom;
    /**
     * 间接返利区间
     */
    private Double minFirst;
    private Double maxFirst;
    /**
     * 直接返利区间
     */
    private Double minSecond;
    private Double maxSecond;
    /**
     * 总返利区间
     */
    private Double minGross;
    private Double maxGross;
    /**
     * 用户上传图片地址
     */
    private String picUrl;

    public ProductSearcher() {
    }

    public void setC(Long id) {
        this.setCategoryId(id);
    }

    public void setD(Long id) {
        if (id != null && id > 0) {
            this.designers.add(id);
        }
        this.setDesignerId(id);
    }

    public void setT(Long t) {
        this.setTopId(t);
    }

    public String getO() {
        return orderByStr;
    }

    public void setO(String order) {
        this.orderByStr = order;
    }

    public void setK(String k) {
        this.keywords = k;
    }

    public void setM(BigDecimal m) {
        this.maxPrice = m;
    }

    public void setN(BigDecimal m) {
        this.promotionPrice = m;
    }

    public Long getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Long designerId) {
        if (designerId != null && designerId <= 0) {
            designerId = null;
        }
        this.designerId = designerId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        if (tagId != null && tagId <= 0) {
            tagId = null;
        }
        this.tagId = tagId;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        if (topId != null && topId <= 0) {
            topId = null;
        }
        this.topId = topId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        if (categoryId != null && categoryId <= 0) {
            categoryId = null;
        }
        this.categoryId = categoryId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        if (promotionId != null && promotionId <= 0) {
            promotionId = null;
        }
        this.promotionId = promotionId;
    }

    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        if (orderPromotionId != null && orderPromotionId <= 0) {
            orderPromotionId = null;
        }
        this.orderPromotionId = orderPromotionId;
    }

    public Long getRelationProductId() {
        return relationProductId;
    }

    public void setRelationProductId(Long relationProductId) {
        if (relationProductId != null && relationProductId <= 0) {
            relationProductId = null;
        }
        this.relationProductId = relationProductId;
    }

    public Long getChangePromotionId() {
        return changePromotionId;
    }

    public void setChangePromotionId(Long changePromotionId) {
        if (changePromotionId != null && changePromotionId <= 0) {
            changePromotionId = null;
        }
        this.changePromotionId = changePromotionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
    }

    public List<Long> getDesigners() {
        return designers;
    }

    public void setDesigners(List<Long> designers) {
        this.designers = designers;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
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

    public String getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(String skuSn) {
        this.skuSn = skuSn;
    }

    public String getExternalSn() {
        return externalSn;
    }

    public void setExternalSn(String externalSn) {
        this.externalSn = externalSn;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String[] getSn() {
        return sn;
    }

    public void setSn(String[] sn) {
        this.sn = sn;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getAd() {
        return ad;
    }

    public void setAd(Integer ad) {
        this.ad = ad;
    }

    public Integer getTopical() {
        return topical;
    }

    public void setTopical(Integer topical) {
        this.topical = topical;
    }

    public Integer getOperRecom() {
        return operRecom;
    }

    public void setOperRecom(Integer operRecom) {
        this.operRecom = operRecom;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Boolean getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(Boolean isAfter) {
        this.isAfter = isAfter;
    }

    public Boolean getHasPromotion() {
        return hasPromotion;
    }

    public void setHasPromotion(Boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    public Integer getHasRebate() {
        return hasRebate;
    }

    public void setHasRebate(Integer hasRebate) {
        this.hasRebate = hasRebate;
    }

    public Integer getWarnStore() {
        return warnStore;
    }

    public void setWarnStore(Integer warnStore) {
        this.warnStore = warnStore;
    }

    public Integer getRelate() {
        return relate;
    }

    public void setRelate(Integer relate) {
        this.relate = relate;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public Integer[] getMarks() {
        return marks;
    }

    public void setMarks(Integer[] marks) {
        this.marks = marks;
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

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getSyncStamp() {
        return syncStamp;
    }

    public void setSyncStamp(String syncStamp) {
        this.syncStamp = syncStamp;
    }

    public String getOrderByStr() {
        return orderByStr;
    }

    public void setOrderByStr(String orderByStr) {
        this.orderByStr = orderByStr;
    }

    public Integer getBeginStore() {
        return beginStore;
    }

    public void setBeginStore(Integer beginStore) {
        this.beginStore = beginStore;
    }

    public Integer getEndStore() {
        return endStore;
    }

    public void setEndStore(Integer endStore) {
        this.endStore = endStore;
    }

    public Date getBeginUpDate() {
        return beginUpDate;
    }

    public void setBeginUpDate(Date beginUpDate) {
        this.beginUpDate = beginUpDate;
    }

    public Date getEndUpDate() {
        return endUpDate;
    }

    public void setEndUpDate(Date endUpDate) {
        this.endUpDate = endUpDate;
    }

    public Date getBeginDownDate() {
        return beginDownDate;
    }

    public void setBeginDownDate(Date beginDownDate) {
        this.beginDownDate = beginDownDate;
    }

    public Date getEndDownDate() {
        return endDownDate;
    }

    public void setEndDownDate(Date endDownDate) {
        this.endDownDate = endDownDate;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Double getMinRecom() {
        return minRecom;
    }

    public void setMinRecom(Double minRecom) {
        this.minRecom = minRecom;
    }

    public Double getMaxRecom() {
        return maxRecom;
    }

    public void setMaxRecom(Double maxRecom) {
        this.maxRecom = maxRecom;
    }

    public Double getMinFirst() {
        return minFirst;
    }

    public void setMinFirst(Double minFirst) {
        this.minFirst = minFirst;
    }

    public Double getMaxFirst() {
        return maxFirst;
    }

    public void setMaxFirst(Double maxFirst) {
        this.maxFirst = maxFirst;
    }

    public Double getMinSecond() {
        return minSecond;
    }

    public void setMinSecond(Double minSecond) {
        this.minSecond = minSecond;
    }

    public Double getMaxSecond() {
        return maxSecond;
    }

    public void setMaxSecond(Double maxSecond) {
        this.maxSecond = maxSecond;
    }

    public Double getMinGross() {
        return minGross;
    }

    public void setMinGross(Double minGross) {
        this.minGross = minGross;
    }

    public Double getMaxGross() {
        return maxGross;
    }

    public void setMaxGross(Double maxGross) {
        this.maxGross = maxGross;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public ProductProSearchQuery convertSearchQuery() {
        ProductProSearchQuery searcherBean = new ProductProSearchQuery();
        searcherBean.setKeyword(this.getKeywords());
        searcherBean.setOrderByStr(this.getOrderByStr());
        searcherBean.setSeriesId(this.getSeriesId());
        searcherBean.setDesignerId(this.getDesignerId());
        searcherBean.setDesignerIds(this.getDesigners());
        searcherBean.setTopId(this.getTopId());
        searcherBean.setCategoryId(this.getCategoryId());
        searcherBean.setTagId(this.getTagId());
        searcherBean.setMinPrice(this.getMin());
        searcherBean.setMaxPrice(this.getMax());
        searcherBean.setMinRecom(this.getMinRecom());
        searcherBean.setMaxRecom(this.getMaxRecom());
        searcherBean.setCategoryIds(this.getCategoryIds());
        searcherBean.setTopical(this.getTopical());
        searcherBean.setAfter(this.getIsAfter());
        searcherBean.setHasPromotion(this.getHasPromotion());
        searcherBean.setSubscribe(this.getSubscribe());
        searcherBean.setPromotionId(this.getPromotionId());
        searcherBean.setOrderPromotionId(this.getOrderPromotionId());
        searcherBean.setFlashPromotionId(this.getFlashPromotionId());
        searcherBean.setBeginUpDate(this.getBeginUpDate());
        searcherBean.setEndUpDate(this.getEndUpDate());
        searcherBean.setInernalSn(toLowerCase(this.getSn()));
        searcherBean.setStatus(this.getStatus());
        if (StringUtils.isNotBlank(this.getProductSellType())) {
            searcherBean.setProductSellType(Arrays.asList(this.getProductSellType().split(",")));
        }
        return searcherBean;
    }

    private String[] toLowerCase(String[] strs) {
        if (strs != null) {
            int i = 0;
            for (String s : strs) {
                if (s != null) {
                    strs[i] = s.toLowerCase();
                }
                i++;
            }
        }
        return strs;
    }

    @Override
    public void setStoreId(Long storeId) {
    }

    @Override
    public void setBrandIds(List<Long> brandIds) {
        this.designers = brandIds;
    }

}
