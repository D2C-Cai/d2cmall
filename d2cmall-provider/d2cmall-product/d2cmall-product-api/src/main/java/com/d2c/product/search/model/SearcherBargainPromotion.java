package com.d2c.product.search.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 砍价活动
 *
 * @author Administrator
 */
public class SearcherBargainPromotion implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 活动id
     */
    private Long id;
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
    private Integer mark;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 虚拟人数
     */
    private Integer virtualMan;
    /**
     * 实际砍价人数
     */
    private Integer actualMan;
    /**
     * 活动说明
     */
    private String description;
    /**
     * 商品id
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
    /**
     * 商品
     */
    private SearcherProduct product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getActualMan() {
        return actualMan;
    }

    public void setActualMan(Integer actualMan) {
        this.actualMan = actualMan;
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

    public SearcherProduct getProduct() {
        return product;
    }

    public void setProduct(SearcherProduct product) {
        this.product = product;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public Integer getStatus() {
        // 活动结束
        if (this.getMark() < 1 || product == null || product.getMark() < 1 || this.getEndDate().before(new Date())) {
            return -1;
        } else if (product.getStore() < 1) {
            // 已砍完
            return 0;
        } else if (this.getBeginDate().after(new Date())) {
            // 即将开始
            return 1;
        } else {
            // 正在进行中
            return 2;
        }
    }

    public int getTotalCount() {
        if (this.getBeginDate() != null && this.getBeginDate().before(new Date())) {
            return this.getVirtualMan() + this.getActualMan();
        }
        return this.getActualMan();
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        if (product != null) {
            obj = product.toSimpleJson();
        }
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("coverPic", this.getCoverPic());
        obj.put("beginDate", this.getBeginDate() == null ? "" : this.getBeginDate());
        obj.put("endDate", this.getEndDate() == null ? "" : this.getEndDate());
        obj.put("sort", this.getSort());
        obj.put("promotionMark", this.getMark());
        obj.put("description", this.getDescription());
        obj.put("productId", this.getProduct());
        obj.put("minPrice", this.getMinPrice());
        obj.put("shareTitle", this.getShareTitle());
        obj.put("sharePic", this.getSharePic());
        obj.put("shareDes", this.getShareDes());
        obj.put("totalCount", this.getTotalCount());
        obj.put("status", this.getStatus());
        return obj;
    }

}
