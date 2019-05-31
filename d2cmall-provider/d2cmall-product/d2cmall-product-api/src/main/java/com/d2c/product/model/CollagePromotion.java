package com.d2c.product.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 拼团活动
 *
 * @author wwn
 */
@Table(name = "o_collage_promotion")
public class CollagePromotion extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 活动名称
     */
    @AssertColumn(nullable = false, value = "活动名称不能为空")
    private String name;
    /**
     * 开始时间
     */
    @AssertColumn(nullable = false, value = "开始时间不能为空")
    private Date beginDate;
    /**
     * 结束时间
     */
    @AssertColumn(nullable = false, value = "结束时间不能为空")
    private Date endDate;
    /**
     * 状态
     */
    private Integer status = 0;
    /**
     * 排序
     */
    private Integer sort = 0;
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
     * 开团时间(单位：分钟)
     */
    @AssertColumn(nullable = false, value = "开团时间不能为空")
    private Integer expireTime;
    /**
     * 要求几人成团
     */
    @AssertColumn(nullable = false, mineq = 2, value = "拼团人数最少2人")
    private Integer memberCount;
    /**
     * 达标几人成团
     */
    private Integer realCount = 0;
    /**
     * 同一个商品同一用户可以买几次
     */
    private Integer memberBuyLimit = 0;
    /**
     * 同一商品支持开团数
     */
    private Integer productCreatedLimit = 0;
    /**
     * 当前开团数
     */
    private Integer currentCount = 0;
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

    public Integer getRealCount() {
        return realCount;
    }

    public void setRealCount(Integer realCount) {
        this.realCount = realCount;
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

    public Integer getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(Integer currentCount) {
        this.currentCount = currentCount;
    }

    public Integer isOver() {
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
        JSONObject json = new JSONObject();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("beginDate", this.getBeginDate() != null ? this.getBeginDate().getTime() : null);
        json.put("endDate", this.getEndDate() != null ? this.getEndDate().getTime() : null);
        json.put("status", this.getStatus());
        json.put("sort", this.getSort());
        json.put("memberCount", this.getMemberCount());
        json.put("sharePic", this.getSharePic());
        json.put("shareTitle", this.getShareTitle());
        json.put("shareContent", this.getShareContent());
        json.put("promotionStatus", this.isOver());
        json.put("productCreatedLimit", this.getProductCreatedLimit());
        return json;
    }

}
