package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 砍价活动
 */
@Table(name = "o_bargain_promotion")
public class BargainPromotion extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 封面图片
     */
    private String coverPic;
    /**
     * 活动开始时间
     */
    private Date beginDate;
    /**
     * 活动结束时间
     */
    private Date endDate;
    /**
     * 上下架
     */
    private Integer mark = 0;
    /**
     * 排序
     */
    private Integer sort = 0;
    /**
     * 虚拟人数
     */
    private Integer virtualMan = 0;
    /**
     * 实际砍价人数
     */
    private Integer actualMan = 0;
    /**
     * 活动说明
     */
    private String description;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 最低价
     */
    private BigDecimal minPrice;
    /**
     * 分享图片
     */
    private String sharePic;
    /**
     * 分享标题
     */
    private String shareTitle;
    /**
     * 分享描述
     */
    private String shareDes;

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

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getVirtualMan() {
        return virtualMan;
    }

    public void setVirtualMan(Integer virtualMan) {
        this.virtualMan = virtualMan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
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

    public String getShareDes() {
        return shareDes;
    }

    public void setShareDes(String shareDes) {
        this.shareDes = shareDes;
    }

    public Integer getActualMan() {
        return actualMan;
    }

    public void setActualMan(Integer actualMan) {
        this.actualMan = actualMan;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public boolean isOver() {
        if (this.getMark() == 1 && this.getBeginDate().compareTo(new Date()) <= 0
                && this.getEndDate().compareTo(new Date()) >= 0) {
            return false;
        }
        return true;
    }

}
