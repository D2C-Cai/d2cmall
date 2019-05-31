package com.d2c.order.handle;

import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.model.Promotion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class PromotionCalculateItem implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 一般为对象的ID
     */
    private Long key;
    /**
     * 商品金额
     */
    private BigDecimal productPrice = new BigDecimal("0.00");
    /**
     * 优惠金额（优惠单价=商品金额-优惠金额）
     */
    private BigDecimal promotionPrice = new BigDecimal("0.00");
    /**
     * 一口价
     */
    private BigDecimal aPrice;
    /**
     * 限时价
     */
    private BigDecimal flashPrice;
    /**
     * 限时数量
     */
    private Integer flashStock;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 选中的商品活动
     */
    private Promotion goodPromotion;
    /**
     * 选中的订单活动
     */
    private Promotion orderPromotion;
    /**
     * 选中的限时购活动
     */
    private FlashPromotion flashPromotion;
    /**
     * 可选的订单活动
     */
    private List<Promotion> choiceOrderPromotions;
    /**
     * 活动名称
     */
    private String promotionName;
    /**
     * 订单活动名称
     */
    private String orderPromotionName;
    /**
     * 活动优惠金额
     */
    private BigDecimal promotionAmount = new BigDecimal("0.00");
    /**
     * 总商品金额
     */
    private BigDecimal totalProductAmount = new BigDecimal("0.00");
    /**
     * 满减优惠金额分摊
     */
    private BigDecimal orderPromotionAmount = new BigDecimal("0.00");

    public PromotionCalculateItem() {
    }

    public PromotionCalculateItem(IPromotionInterface pc) {
        if (pc.getId() == null || pc.getId() <= 0) {
            this.setKey(pc.getProductSkuId());
        } else {
            this.setKey(pc.getId());
        }
        this.setProductPrice(pc.getProductPrice());
        this.setaPrice(pc.getaPrice());
        this.setFlashPrice(pc.getFlashPrice());
        this.setQuantity(pc.getQuantity());
        this.setTotalProductAmount(pc.getTotalPrice());
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
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

    public Integer getFlashStock() {
        return flashStock;
    }

    public void setFlashStock(Integer flashStock) {
        this.flashStock = flashStock;
    }

    public List<Promotion> getChoiceOrderPromotions() {
        return choiceOrderPromotions;
    }

    public void setChoiceOrderPromotions(List<Promotion> choiceOrderPromotions) {
        this.choiceOrderPromotions = choiceOrderPromotions;
    }

    public Promotion getGoodPromotion() {
        return goodPromotion;
    }

    public void setGoodPromotion(Promotion goodPromotion) {
        this.goodPromotion = goodPromotion;
    }

    public Promotion getOrderPromotion() {
        return orderPromotion;
    }

    public void setOrderPromotion(Promotion orderPromotion) {
        this.orderPromotion = orderPromotion;
    }

    public FlashPromotion getFlashPromotion() {
        return flashPromotion;
    }

    public void setFlashPromotion(FlashPromotion flashPromotion) {
        this.flashPromotion = flashPromotion;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getTotalProductAmount() {
        return totalProductAmount;
    }

    public void setTotalProductAmount(BigDecimal totalProductAmount) {
        this.totalProductAmount = totalProductAmount;
    }

    public BigDecimal getOrderPromotionAmount() {
        return orderPromotionAmount;
    }

    public void setOrderPromotionAmount(BigDecimal orderPromotionAmount) {
        this.orderPromotionAmount = orderPromotionAmount;
    }

    public String getOrderPromotionName() {
        return orderPromotionName;
    }

    public void setOrderPromotionName(String orderPromotionName) {
        this.orderPromotionName = orderPromotionName;
    }

}
