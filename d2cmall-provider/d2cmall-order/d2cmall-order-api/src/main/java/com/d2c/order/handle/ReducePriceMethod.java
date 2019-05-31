package com.d2c.order.handle;

import com.d2c.product.model.Promotion;

import java.math.BigDecimal;

/**
 * REDUCE_PRICE(1, "直减", 0)
 */
public class ReducePriceMethod implements ProcessPriceBehavior {

    @Override
    public BigDecimal process(Promotion promotion, PromotionCalculateItem calItem) {
        if (promotion.isOver()) {
            return new BigDecimal(0);
        }
        BigDecimal off = new BigDecimal(promotion.getSolution());
        if (calItem.getProductPrice().compareTo(off) < 0) {
            return new BigDecimal(0);
        }
        calItem.setPromotionPrice(off);
        calItem.setPromotionAmount(
                off.multiply(new BigDecimal(calItem.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP));
        calItem.setPromotionName(promotion.getName());
        return off;
    }

    @Override
    public String getName() {
        return "直减";
    }

}
