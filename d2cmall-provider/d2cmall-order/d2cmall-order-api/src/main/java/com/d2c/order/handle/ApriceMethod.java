package com.d2c.order.handle;

import com.d2c.product.model.Promotion;

import java.math.BigDecimal;

/**
 * APRICE(4, "一口价", 0)
 */
public class ApriceMethod implements ProcessPriceBehavior {

    @Override
    public BigDecimal process(Promotion promotion, PromotionCalculateItem calItem) {
        if (promotion.isOver()) {
            return new BigDecimal(0);
        }
        if (calItem.getaPrice() == null || calItem.getaPrice().compareTo(new BigDecimal(0)) <= 0
                || calItem.getProductPrice().compareTo(calItem.getaPrice()) < 0) {
            return new BigDecimal(0);
        }
        BigDecimal off = calItem.getProductPrice().subtract(calItem.getaPrice());
        calItem.setPromotionPrice(off);
        calItem.setPromotionAmount(
                off.multiply(new BigDecimal(calItem.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP));
        calItem.setPromotionName(promotion.getName());
        return off;
    }

    @Override
    public String getName() {
        return "一口价";
    }

}
