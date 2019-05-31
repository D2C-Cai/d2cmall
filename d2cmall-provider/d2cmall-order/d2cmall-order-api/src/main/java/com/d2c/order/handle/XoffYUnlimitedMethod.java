package com.d2c.order.handle;

import com.d2c.product.model.Promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Set;

/**
 * X_OFF_Y_UNLIMITED(3, "满减，上不封顶", 1)
 */
public class XoffYUnlimitedMethod implements XoffYProcessPriceBehavior {

    @Override
    public BigDecimal process(Promotion promotion, Set<PromotionCalculateItem> set) {
        if (set == null || set.size() <= 0) {
            return new BigDecimal(0);
        }
        if (promotion.isOver()) {
            return new BigDecimal(0);
        }
        // 计算商品总价
        BigDecimal total = new BigDecimal(0);
        for (PromotionCalculateItem item : set) {
            total = total.add(item.getTotalProductAmount().subtract(item.getPromotionAmount()));
        }
        String[] solutions = promotion.getSolution().split("-");
        BigDecimal X = new BigDecimal(solutions[0]);
        BigDecimal Y = new BigDecimal(solutions[1]);
        BigDecimal multiple = total.divide(X, 0, RoundingMode.FLOOR);
        BigDecimal off = Y.multiply(multiple);
        return off;
    }

    @Override
    public String getName() {
        return "满减，上不封顶";
    }

    @Override
    public BigDecimal splitAmount(BigDecimal currenPromotion, BigDecimal totalProductAmount, Promotion promotion,
                                  Set<PromotionCalculateItem> set, PromotionCalculateResult result) {
        Iterator<PromotionCalculateItem> iterator = set.iterator();
        int i = 1;
        BigDecimal rateResult = new BigDecimal(0);
        while (iterator.hasNext()) {
            PromotionCalculateItem item = iterator.next();
            BigDecimal itemAmount = item.getTotalProductAmount().subtract(item.getPromotionAmount());
            if (set.size() == i && set.size() > 1) {
                item.setOrderPromotionAmount(
                        currenPromotion.subtract(rateResult).setScale(2, BigDecimal.ROUND_HALF_UP));
                item.setOrderPromotionName(promotion.getName());
                break;
            }
            BigDecimal ratePromotion = itemAmount.divide(totalProductAmount, 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal promotionAmount = currenPromotion.multiply(ratePromotion).setScale(2, BigDecimal.ROUND_HALF_UP);
            item.setOrderPromotionAmount(promotionAmount);
            item.setOrderPromotionName(promotion.getName());
            rateResult = rateResult.add(promotionAmount);
            i++;
        }
        StringBuilder promotionNames = new StringBuilder();
        promotionNames.append(promotion.getName() + " 优惠金额：&yen;" + currenPromotion.intValue() + "; ");
        result.setOrderPromotionNames(promotionNames.toString());
        return currenPromotion;
    }

}
