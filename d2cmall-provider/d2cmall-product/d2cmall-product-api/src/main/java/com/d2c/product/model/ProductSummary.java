package com.d2c.product.model;

import com.d2c.common.api.annotation.AssertColumn;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 商品统计
 */
@Table(name = "p_product_summary")
public class ProductSummary extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 产品Id
     */
    @AssertColumn("商品不能为空")
    private Long productId;
    /**
     * 总销量
     */
    private Integer sales = 0;
    /**
     * 商品查看数
     */
    private Integer views = 0;
    /**
     * 商品加入购物车
     */
    private Integer carts = 0;
    /**
     * 商品加入订单数
     */
    private Integer orders = 0;
    /**
     * 喜欢的数量
     */
    private Integer likes = 0;
    /**
     * 虚拟喜欢的数量
     */
    private Integer vlikes = 0;
    /**
     * 评论数量
     */
    private Integer comments = 0;
    /**
     * 咨询数量
     */
    private Integer consults = 0;
    /**
     * 展示喜欢的数量
     */
    @Transient
    private Integer tlikes = 0;
    /**
     * 最近一个月销量
     */
    private Integer recentlySales = 0;
    /**
     * 7天分销销量
     */
    private Integer partnerSales = 0;

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCarts() {
        return carts;
    }

    public void setCarts(Integer carts) {
        this.carts = carts;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getVlikes() {
        return vlikes;
    }

    public void setVlikes(Integer vlikes) {
        this.vlikes = vlikes;
    }

    public Integer getTlikes() {
        return tlikes;
    }

    public void setTlikes(Integer tlikes) {
        this.tlikes = tlikes;
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

    public Integer getRecentlySales() {
        return recentlySales;
    }

    public void setRecentlySales(Integer recentlySales) {
        this.recentlySales = recentlySales;
    }

    public Integer getPartnerSales() {
        return partnerSales;
    }

    public void setPartnerSales(Integer partnerSales) {
        this.partnerSales = partnerSales;
    }

}
