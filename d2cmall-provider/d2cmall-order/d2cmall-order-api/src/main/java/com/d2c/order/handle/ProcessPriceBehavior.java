package com.d2c.order.handle;

import com.d2c.product.model.Promotion;

import java.math.BigDecimal;

public interface ProcessPriceBehavior {

    BigDecimal process(Promotion promotion, PromotionCalculateItem calItem);

    String getName();

}
