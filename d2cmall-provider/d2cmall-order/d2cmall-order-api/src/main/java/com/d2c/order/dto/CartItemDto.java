package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.order.handle.PromotionCalculateItem;
import com.d2c.order.model.CartItem;
import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.model.ProductSku;
import com.d2c.product.model.Promotion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartItemDto extends CartItem implements IPromotionInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 商品SKU
     */
    private ProductSku productSku;
    /**
     * 订单满减活动
     */
    private Long orderPromotionId;
    /**
     * 商品的活动
     */
    private Long goodPromotionId;
    /**
     * 组合商品活动
     */
    private Long productCombId;
    /**
     * 限时购活动
     */
    private Long flashPromotionId;
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
     * 优惠金额（优惠单价=商品金额-优惠金额）
     */
    private BigDecimal promotionPrice = new BigDecimal("0.00");
    /**
     * 商品活动优惠金额
     */
    private BigDecimal promotionAmount = new BigDecimal("0.00");
    /**
     * 商品总商品金额
     */
    private BigDecimal totalProductAmount = new BigDecimal("0.00");

    public CartItemDto() {
    }

    public CartItemDto(CartItem item) {
        BeanUtils.copyProperties(item, this);
    }

    /**
     * 计算本项总金额(单价x数量) 目前不支持购第1件优惠,后面的原价,这类优惠方式
     */
    @Override
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = super.getPrice().multiply(new BigDecimal(this.getQuantity()));
        return totalPrice;
    }

    public boolean isOver() {
        boolean over = false;
        if (this.getProductSku() != null && this.getProductSku().getStatus() <= 0) {
            over = true;
        }
        return over;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    @Override
    public Long getOrderPromotionId() {
        return orderPromotionId;
    }

    public void setOrderPromotionId(Long orderPromotionId) {
        this.orderPromotionId = orderPromotionId;
    }

    @Override
    public Long getGoodPromotionId() {
        return goodPromotionId;
    }

    public void setGoodPromotionId(Long goodPromotionId) {
        this.goodPromotionId = goodPromotionId;
    }

    @Override
    public Long getProductCombId() {
        return productCombId;
    }

    public void setProductCombId(Long productCombId) {
        this.productCombId = productCombId;
    }

    @Override
    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
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

    public List<Promotion> getChoiceOrderPromotions() {
        return choiceOrderPromotions;
    }

    public void setChoiceOrderPromotions(List<Promotion> choiceOrderPromotions) {
        this.choiceOrderPromotions = choiceOrderPromotions;
    }

    @Override
    public BigDecimal getaPrice() {
        return aPrice;
    }

    public void setaPrice(BigDecimal aPrice) {
        this.aPrice = aPrice;
    }

    @Override
    public BigDecimal getFlashPrice() {
        return flashPrice;
    }

    public void setFlashPrice(BigDecimal flashPrice) {
        this.flashPrice = flashPrice;
    }

    @Override
    public Integer getFlashStock() {
        return flashStock;
    }

    public void setFlashStock(Integer flashStock) {
        this.flashStock = flashStock;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
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

    @Override
    public BigDecimal getProductPrice() {
        return this.getPrice();
    }

    public String getColorValue() {
        if (this.getSp1() != null) {
            return JSONObject.parseObject(this.getSp1()).get("value").toString();
        } else {
            return "";
        }
    }

    public String getSizeValue() {
        if (this.getSp2() != null) {
            return JSONObject.parseObject(this.getSp2()).get("value").toString();
        } else {
            return "";
        }
    }

    /**
     * 初始化活动
     *
     * @param goodPromotionId
     * @param orderPromotionId
     * @param flashPromotionId
     * @param aPrice
     * @param flashPrice
     * @param flashStock
     */
    public void initCondition(Long goodPromotionId, Long orderPromotionId, Long flashPromotionId, BigDecimal aPrice,
                              BigDecimal flashPrice, Integer flashStock) {
        this.setGoodPromotionId(goodPromotionId);
        this.setOrderPromotionId(orderPromotionId);
        this.setFlashPromotionId(flashPromotionId);
        this.setaPrice(aPrice);
        this.setFlashPrice(flashPrice);
        this.setFlashStock(flashStock);
    }

    /**
     * 初始化活动
     *
     * @param calitem
     */
    public CartItemDto initPromotion(PromotionCalculateItem calItem) {
        if (calItem == null) {
            return this;
        }
        // 经销商启用的是吊牌价
        this.setPrice(calItem.getProductPrice());
        this.setPromotionAmount(calItem.getPromotionAmount());
        this.setPromotionPrice(calItem.getPromotionPrice());
        this.setTotalProductAmount(calItem.getTotalProductAmount());
        if (calItem.getGoodPromotion() != null) {
            this.setGoodPromotionId(calItem.getGoodPromotion().getId());
            this.setPromotionPrice(calItem.getPromotionPrice());
            this.setPromotionAmount(calItem.getPromotionAmount());
            this.setGoodPromotion(calItem.getGoodPromotion());
        } else {
            this.setGoodPromotionId(0L);
        }
        if (calItem.getFlashPromotion() != null) {
            this.setFlashPromotionId(calItem.getFlashPromotion().getId());
            this.setPromotionPrice(calItem.getPromotionPrice());
            this.setPromotionAmount(calItem.getPromotionAmount());
            this.setFlashPromotion(calItem.getFlashPromotion());
            this.setFlashStock(calItem.getFlashStock());
        } else {
            this.setFlashPromotionId(0L);
        }
        if (calItem.getOrderPromotion() != null) {
            this.setOrderPromotionId(calItem.getOrderPromotion().getId());
            this.setOrderPromotion(calItem.getOrderPromotion());
        } else {
            this.setOrderPromotionId(0L);
        }
        this.setChoiceOrderPromotions(calItem.getChoiceOrderPromotions());
        return this;
    }

    public String getDeviceName() {
        if (StringUtils.isNotBlank(this.getDevice())) {
            return DeviceTypeEnum.valueOf(this.getDevice()).getDisplay();
        }
        return "电脑PC";
    }

    public JSONObject toJson() {
        JSONObject itemObj = new JSONObject();
        itemObj.put("id", this.getId());
        itemObj.put("productId", this.getProductId());
        itemObj.put("productSn", this.getProductSn());
        itemObj.put("productName", super.getProductName());
        itemObj.put("productImg", this.getSp1Img());
        itemObj.put("productSkuSn", this.getProductSkuSn());
        itemObj.put("designerId", this.getDesignerId());
        itemObj.put("designer", this.getDesignerName());
        itemObj.put("quantity", this.getQuantity());
        itemObj.put("after", this.getAfter());
        itemObj.put("productPrice", this.getPrice());
        itemObj.put("oldPrice", this.getOldPrice());
        itemObj.put("originalPrice", this.getOriginalPrice());
        itemObj.put("minPrice", this.getPromotionPrice().compareTo(new BigDecimal(0)) > 0
                ? this.getPrice().subtract(this.getPromotionPrice()) : this.getPrice());
        itemObj.put("productTradeType",
                this.getProductTradeType() == null ? ProductTradeType.COMMON.name() : this.getProductTradeType());
        itemObj.put("taxation", this.getTaxation());
        itemObj.put("taxPrice", this.getTaxPrice() == null ? 0 : this.getTaxPrice());
        ProductSku sku = this.getProductSku();
        if (sku != null) {
            itemObj.put("mark", sku.getStatus());
            itemObj.put("color", sku.getColorValue());
            itemObj.put("colorId", sku.getColorId());
            itemObj.put("size", sku.getSizeValue());
            itemObj.put("sizeId", sku.getSizeId());
            itemObj.put("skuId", sku.getId());
            itemObj.put("availableStore", sku.getAvailableStore());
            itemObj.put("freezeStore", sku.getFreezeStore());
        }
        return this.toPromotionJson(itemObj);
    }

    public JSONObject toPromotionJson(JSONObject itemObj) {
        itemObj.put("goodPromotionId", this.getGoodPromotionId());
        if (this.getGoodPromotion() != null) {
            itemObj.put("goodPromotion", this.getGoodPromotion().toSimpleJson());
        }
        itemObj.put("orderPromotionId", this.getOrderPromotionId());
        if (this.getOrderPromotion() != null) {
            itemObj.put("orderPromotion", this.getOrderPromotion().toSimpleJson());
        }
        itemObj.put("flashPromotionId", this.getFlashPromotionId());
        if (this.getFlashPromotion() != null) {
            itemObj.put("flashPromotion", this.getFlashPromotion().toJson());
            itemObj.put("availableStore", this.getFlashStock());
        }
        List<JSONObject> clist = new ArrayList<>();
        if (this.getChoiceOrderPromotions() != null) {
            for (Promotion cop : this.getChoiceOrderPromotions()) {
                clist.add(cop.toSimpleJson());
            }
        }
        itemObj.put("choiceOrderPromotions", clist);
        itemObj.put("promotionPrice", this.getPromotionAmount());
        itemObj.put("totalPrice", this.getTotalProductAmount().subtract(this.getPromotionAmount()));
        return itemObj;
    }

}
