package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.utils.StringUt;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.handle.PromotionCalculateItem;
import com.d2c.order.model.*;
import com.d2c.order.model.Exchange.ExchangeStatus;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.model.Reship.ReshipStatus;
import com.d2c.order.model.base.ICouponInterface;
import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.product.model.Product;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.Product.ProductTradeType;
import com.d2c.product.model.ProductSku;
import com.d2c.product.model.ProductSkuStockSummary;
import com.d2c.util.date.DateUtil;

import java.math.BigDecimal;
import java.util.Map;

public class OrderItemDto extends OrderItem implements IPromotionInterface, ICouponInterface {

    private static final long serialVersionUID = 1L;
    /**
     * 购物车项ID
     */
    private Long cartItemId;
    /**
     * 商品SKU
     */
    private ProductSku productSku;
    /**
     * 商品SKU库存汇总
     */
    private ProductSkuStockSummary productSkuStockSummary;
    /**
     * 退货单
     */
    private Reship reship;
    /**
     * 退款单
     */
    private Refund refund;
    /**
     * 换货单
     */
    private Exchange exchange;
    /**
     * 订单
     */
    private Order order;
    /**
     * 优惠金额（优惠单价=商品金额-优惠金额）
     */
    private BigDecimal promotionPrice;
    /**
     * 税费金额，多个,号隔开
     */
    private String taxPrice;
    /**
     * 运营小组
     */
    private String operation;
    /**
     * 会员账号
     */
    private String loginCode;

    public OrderItemDto() {
    }

    public OrderItemDto(Product product, CartItemDto cartItem) {
        this.setProductId(cartItem.getProductId());
        this.setProductSn(cartItem.getProductSn());
        this.setProductName(cartItem.getProductName());
        this.setProductImg(cartItem.getProductImg());
        this.setProductPrice(cartItem.getPrice());
        this.setOriginalPrice(cartItem.getOriginalPrice());
        this.setProductQuantity(cartItem.getQuantity());
        this.setDeliveryQuantity(cartItem.getQuantity());
        this.setProductSkuId(cartItem.getProductSkuId());
        this.setProductSkuSn(cartItem.getProductSkuSn());
        this.setProductSkuName(cartItem.getProductSkuName());
        this.setProductRemark(cartItem.getProductRemark());
        this.setSp1(cartItem.getSp1());
        this.setSp2(cartItem.getSp2());
        this.setSp3(cartItem.getSp3());
        this.setDesignerId(cartItem.getDesignerId());
        this.setDesignerCode(cartItem.getDesignerCode());
        this.setDesignerName(cartItem.getDesignerName());
        this.setCartItemId(cartItem.getId());
        this.setBuyerMemberId(cartItem.getBuyMemberId());
        this.setProductCombId(cartItem.getProductCombId());
        this.setCartItemId(cartItem.getId());
        this.setCod(product.getCod());
        this.setAfter(product.getAfter());
        this.setTaxation(product.getTaxation());
        this.setTaxPrice(product.getTaxPrice());
        this.setShipping(product.getShipping());
        this.setSaleCategory(product.getSaleCategory());
        this.setProductSaleType(product.getProductSaleType());
        this.setProductSellType(product.getProductSellType());
        this.setProductTradeType(product.getProductTradeType());
        this.setProductSource(product.getSource());
        this.setExternalSn(product.getExternalSn());
        this.setCoupon(product.getCoupon());
    }

    public ItemStatus getItemStatus() {
        return ItemStatus.getItemStatusByName(this.getStatus());
    }

    public String getItemStatusName() {
        if (this.getLocked() == 1) {
            return "已锁定";
        }
        Reship reship = this.getReship();
        Refund refund = this.getRefund();
        Exchange exchange = this.getExchange();
        if (reship != null && reship.getReshipStatus() < 8) {
            return reship.getReshipStatusName();
        }
        if (refund != null) {
            return refund.getRefundStatusName();
        }
        if (exchange != null) {
            return exchange.getExchangeStatusName();
        }
        ItemStatus status = ItemStatus.getItemStatusByName(this.getStatus());
        if (status.equals(ItemStatus.NORMAL) && this.getRequisition() == 2) {
            return "设计师已发货";
        }
        return status.getName();
    }

    /**
     * APP订单明细状态显示
     *
     * @return
     */
    public String getAppStatusName() {
        Reship reship = this.getReship();
        Refund refund = this.getRefund();
        Exchange exchange = this.getExchange();
        if (reship != null && reship.getReshipStatus() < 8) {
            return reship.getReshipStatusName();
        }
        if (refund != null) {
            return refund.getRefundStatusName();
        }
        if (exchange != null) {
            return exchange.getExchangeStatusName();
        }
        ItemStatus status = ItemStatus.getItemStatusByName(this.getStatus());
        if (status.equals(ItemStatus.NORMAL)) {
            if (this.getRequisition() == 1) {
                return "商品正在调拨";
            }
            if (this.getRequisition() == 2) {
                return "设计师已发货";
            }
        }
        return status.getName();
    }

    /**
     * 售后状态显示
     *
     * @return
     */
    public String getAfterStatusName() {
        Reship reship = this.getReship();
        Refund refund = this.getRefund();
        Exchange exchange = this.getExchange();
        if (reship != null && reship.getReshipStatus() < 8) {
            return reship.getReshipStatusName();
        }
        if (refund != null) {
            return refund.getRefundStatusName();
        }
        if (exchange != null) {
            return exchange.getExchangeStatusName();
        }
        return null;
    }

    /**
     * 处于售后状态中和已经售后完成
     *
     * @return
     */
    public String getAftering() {
        String result = "none";
        if (this.getExchangeId() != null && this.getExchange() != null && this.getExchange().getExchangeStatus() >= 0) {
            result = "exchange";
            return result;
        }
        if (this.getReshipId() != null && this.getReship() != null && this.getReship().getReshipStatus() >= 0
                && this.getReship().getReshipStatus() < 8) {
            result = "reship";
            return result;
        }
        if (this.getRefundId() != null && this.getRefund() != null && this.getRefund().getRefundStatus() >= 0) {
            result = "refund";
            return result;
        }
        return result;
    }

    /**
     * 曾经有过售后且售后被拒绝
     *
     * @return
     */
    public String getAftered() {
        String result = "none";
        if (this.getReshipId() != null && this.getReship() != null && this.getReship().getReshipStatus() < 0) {
            result = "reship";
            return result;
        }
        if (this.getRefundId() != null && this.getRefund() != null && this.getRefund().getRefundStatus() < 0) {
            result = "refund";
            return result;
        }
        if (this.getExchangeId() != null && this.getExchange() != null && this.getExchange().getExchangeStatus() < 0) {
            result = "exchange";
            return result;
        }
        return result;
    }

    /**
     * 处于售后状态中和已经售后完成显示的入口
     *
     * @return
     */
    public String getAfteringStatus() {
        if (this.getAftering().equals("reship")) {
            return this.getReship().getReshipStatusName();
        }
        if (this.getAftering().equals("refund")) {
            return this.getRefund().getRefundStatusName();
        }
        if (this.getAftering().equals("exchange")) {
            return this.getExchange().getExchangeStatusName();
        }
        return "售后处理中";
    }

    /**
     * 曾经有过售后且售后被拒绝显示的入口
     *
     * @return
     */
    public String getAfteredStatus() {
        if (this.getAftered().equals("reship")) {
            ReshipStatus rr = ReshipStatus.getStatus(this.getReship().getReshipStatus());
            return rr.getName();
        }
        if (this.getAftered().equals("refund")) {
            RefundStatus rs = RefundStatus.getStatus(this.getRefund().getRefundStatus());
            return rs.getName();
        }
        if (this.getAftered().equals("exchange")) {
            ExchangeStatus exchangeStatus = ExchangeStatus.getStatus(this.getExchange().getExchangeStatus());
            return exchangeStatus.getName();
        }
        return "售后处理中";
    }

    public Reship getReship() {
        return reship;
    }

    public void setReship(Reship reship) {
        this.reship = reship;
    }

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductSku getProductSku() {
        return productSku;
    }

    public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

    public ProductSkuStockSummary getProductSkuStockSummary() {
        return productSkuStockSummary;
    }

    public void setProductSkuStockSummary(ProductSkuStockSummary productSkuStockSummary) {
        this.productSkuStockSummary = productSkuStockSummary;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(String taxPrice) {
        this.taxPrice = taxPrice;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    @Override
    public Long getCouponProductId() {
        return super.getProductId();
    }

    @Override
    public Long getCouponShopId() {
        return super.getDesignerId();
    }

    @Override
    public BigDecimal getaPrice() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BigDecimal getFlashPrice() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getFlashStock() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getQuantity() {
        return this.getProductQuantity();
    }

    /**
     * 初始化活动
     *
     * @param calitem
     */
    public OrderItemDto initPromotion(PromotionCalculateItem calItem) {
        if (calItem == null) {
            return this;
        }
        // 经销商启用的是吊牌价
        this.setProductPrice(calItem.getProductPrice());
        this.setPromotionAmount(calItem.getPromotionAmount());
        this.setPromotionPrice(calItem.getPromotionPrice());
        this.setPromotionName(calItem.getPromotionName());
        this.setOrderPromotionName(calItem.getOrderPromotionName());
        this.setOrderPromotionAmount(calItem.getOrderPromotionAmount());
        if (calItem.getGoodPromotion() != null) {
            this.setGoodPromotionId(calItem.getGoodPromotion().getId());
            this.setPromotionName(calItem.getPromotionName());
            this.setPromotionPrice(calItem.getPromotionPrice());
            this.setPromotionAmount(calItem.getPromotionAmount());
        }
        if (calItem.getFlashPromotion() != null) {
            this.setFlashPromotionId(calItem.getFlashPromotion().getId());
            this.setPromotionPrice(calItem.getPromotionPrice());
            this.setPromotionAmount(calItem.getPromotionAmount());
        }
        if (calItem.getOrderPromotion() != null) {
            this.setOrderPromotionId(calItem.getOrderPromotion().getId());
            this.setOrderPromotionName(calItem.getOrderPromotionName());
            this.setOrderPromotionAmount(calItem.getOrderPromotionAmount());
        }
        return this;
    }

    public JSONObject toJson() {
        JSONObject item = new JSONObject();
        item.put("id", this.getId());
        item.put("type", this.getType());
        item.put("createDate", DateUtil.second2str2(this.getCreateDate()));
        item.put("orderId", this.getOrderId());
        item.put("orderSn", this.getOrderSn());
        item.put("memberId", this.getBuyerMemberId());
        item.put("loginCode", this.getBuyerMemberName() == null ? "" : StringUt.hideMobile(this.getBuyerMemberName()));
        item.put("productPrice", this.getProductPrice());
        item.put("quantity", this.getQuantity());
        item.put("promotionPrice", this.getPromotionAmount());
        item.put("totalPrice", this.getTotalPrice());
        item.put("taxPrice", this.getTaxPrice() == null ? 0 : this.getTaxPrice());
        item.put("itemStatus", this.getAppStatusName());
        item.put("itemStatusCode", ItemStatus.valueOf(this.getStatus()).getCode());
        item.put("collageStatus", this.getCollageStatus());
        item.put("cartItemId", this.getCartItemId());
        item.put("skuId", this.getProductSkuId());
        item.put("skuSn", this.getProductSkuSn());
        item.put("productId", this.getProductId());
        item.put("productImg", this.getSp1Img());
        item.put("productSn", this.getProductSn());
        item.put("productName", this.getProductName());
        item.put("productSaleType", this.getProductSaleType());
        item.put("productSellType", this.getProductSellType());
        item.put("productSource",
                this.getProductSource() == null ? ProductSource.D2CMALL.name() : this.getProductSource());
        item.put("productTradeType",
                this.getProductTradeType() == null ? ProductTradeType.COMMON.name() : this.getProductTradeType());
        item.put("color", this.getSp1Value());
        item.put("size", this.getSp2Value());
        item.put("designerId", this.getDesignerId());
        item.put("designer", this.getDesignerName());
        item.put("commentId", this.getCommentId());
        item.put("isComment", this.isCommented());
        item.put("isCod", this.getCod());
        item.put("isAfter", this.getAfter());
        item.put("isTaxation", this.getTaxation());
        item.put("actualAmount", this.getActualAmount());
        item.put("promotionAmount", this.getPromotionAmount());
        item.put("orderPromotionAmount", this.getOrderPromotionAmount());
        item.put("couponAmount", this.getCouponAmount());
        item.put("redAmount", this.getRedAmount());
        item.put("partnerAmount", this.getPartnerAmount());
        item.put("diffAmount", this.getDiffAmount());
        item.put("taxAmount", this.getTaxAmount());
        item.put("goodPromotionId", this.getGoodPromotionId());
        item.put("orderPromotionId", this.getOrderPromotionId());
        item.put("productCombId", this.getProductCombId());
        item.put("flashPromotionId", this.getFlashPromotionId());
        item.put("deliveryType", this.getDeliveryType());
        item.put("deliverySn", this.getDeliverySn());
        item.put("deliveryCorpCode", this.getDeliveryCorpName());
        item.put("deliveryCorpName", this.getDeliveryCorpName());
        item.put("after", this.getAfter());
        item.put("aftering", this.getAftering());
        item.put("afterApply", this.getAfterApply());
        item.put("reminded", this.getReminded());
        item.put("estimateDate", this.getEstimateDate() == null ? "" : DateUtil.second2str2(this.getEstimateDate()));
        item.put("signDate", this.getSignDate() == null ? "" : DateUtil.second2str2(this.getSignDate()));
        item.put("expectDate", this.getExpectDate() == null ? "" : DateUtil.second2str2(this.getExpectDate()));
        item.put("partnerId", this.getPartnerId());
        item.put("parentId", this.getParentId());
        item.put("superId", this.getSuperId());
        item.put("masterId", this.getMasterId());
        item.put("partnerRatio", this.getPartnerRatio());
        item.put("parentRatio", this.getParentRatio());
        item.put("superRatio", this.getSuperRatio());
        item.put("masterRatio", this.getMasterRatio());
        item.put("warehouseId", this.getWarehouseId());
        item.put("warehouseName", this.getWarehouseName());
        if (this.getPaymentType() != null) {
            item.put("paymentType", PaymentTypeEnum.getByCode(this.getPaymentType()));
            item.put("paymentTypeName", PaymentTypeEnum.getByCode(this.getPaymentType()).getDisplay());
        }
        return item;
    }

    private String getReminded() {
        String type = "账户";
        if (this.getPaymentType() == null) {
            return "";
        }
        if (this.getPaymentType().intValue() != 3) {
            switch (this.getPaymentType()) {
                case 1:
                    type = "支付宝";
                    break;
                case 7:
                    type = "D2C钱包";
                    break;
                case 6:
                case 8:
                case 9:
                    type = "微信钱包";
                    break;
            }
        } else {
            return "";
        }
        String reminded = "该笔订单将原路返回到您的" + type;
        return reminded;
    }

    public Map<String, Integer> getAfterApply() {
        if (this.getPaymentType() != null && this.getPaymentType() == 3) {
            return getCodAfterApply();
        } else {
            return getOnlineAfterApply();
        }
    }

}
