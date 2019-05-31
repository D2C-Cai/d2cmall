package com.d2c.order.third.kaola.model;

import java.math.BigDecimal;

public class OrderItem {

    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 条码ID
     */
    private String skuId;
    /**
     * 购买数量
     */
    private int buyAmount;
    /**
     * 税前价
     */
    private BigDecimal channelSalePrice;
    /**
     * 仓库ID（订单确认的时候会返回）
     */
    private String warehouseId;

    public OrderItem(String goodsId, String skuId, int buyAmount, BigDecimal channelSalePrice, String warehouseId) {
        this.goodsId = goodsId;
        this.skuId = skuId;
        this.buyAmount = buyAmount;
        this.channelSalePrice = channelSalePrice;
        this.warehouseId = warehouseId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
    }

    public BigDecimal getChannelSalePrice() {
        return channelSalePrice;
    }

    public void setChannelSalePrice(BigDecimal channelSalePrice) {
        this.channelSalePrice = channelSalePrice;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

}
