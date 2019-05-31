package com.d2c.product.search.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class SearcherCollagePromotion implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 开始时间
     */
    private Date beginDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品图片
     */
    private String productImage;
    /**
     * 开团时间
     */
    private Integer expireTime;
    /**
     * 几人团
     */
    private Integer memberCount;
    /**
     * 同一个商品同一用户可以买几次
     */
    private Integer memberBuyLimit;
    /**
     * 同一商品支持开团数
     */
    private Integer productCreatedLimit;
    /**
     * 分享图片
     */
    private String sharePic;
    /**
     * 分享标题
     */
    private String shareTitle;
    /**
     * 分享内容
     */
    private String shareContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getMemberBuyLimit() {
        return memberBuyLimit;
    }

    public void setMemberBuyLimit(Integer memberBuyLimit) {
        this.memberBuyLimit = memberBuyLimit;
    }

    public Integer getProductCreatedLimit() {
        return productCreatedLimit;
    }

    public void setProductCreatedLimit(Integer productCreatedLimit) {
        this.productCreatedLimit = productCreatedLimit;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer promotionStatus() {
        if (this.getStatus() == 1) {
            Date now = new Date();
            if (this.getBeginDate().after(now)) {
                return 0;
            } else if (this.getBeginDate().before(now) && this.getEndDate().after(now)) {
                return 1;
            }
        }
        return -1;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("collagePromotionName", this.getName());
        obj.put("begainDate", this.getBeginDate().getTime());
        obj.put("endDate", this.getEndDate().getTime());
        obj.put("memberCount", this.getMemberCount());
        obj.put("sharePic", this.getSharePic());
        obj.put("shareTitle", this.getShareTitle());
        obj.put("shareContent", this.getShareContent());
        obj.put("promotionStatus", this.promotionStatus());
        return obj;
    }

}
