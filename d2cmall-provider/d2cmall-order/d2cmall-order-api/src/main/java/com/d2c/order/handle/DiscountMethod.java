package com.d2c.order.handle;

import com.d2c.product.model.Promotion;

import java.math.BigDecimal;

/**
 * DISCOUNT(0, "折扣", 0)
 */
public class DiscountMethod implements ProcessPriceBehavior {

    @Override
    public BigDecimal process(Promotion promotion, PromotionCalculateItem calItem) {
        if (promotion.isOver()) {
            return new BigDecimal(0);
        }
        BigDecimal rate = new BigDecimal(promotion.getSolution());
        if (rate.compareTo(new BigDecimal(0)) < 0 || rate.compareTo(new BigDecimal(1)) > 0) {
            return new BigDecimal(0);
        }
        BigDecimal productPrice = calItem.getProductPrice().multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal off = calItem.getProductPrice().subtract(productPrice);
        calItem.setPromotionPrice(off);
        calItem.setPromotionAmount(
                off.multiply(new BigDecimal(calItem.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP));
        calItem.setPromotionName(promotion.getName());
        return off;
    }

    @Override
    public String getName() {
        return "折扣";
    }

}
