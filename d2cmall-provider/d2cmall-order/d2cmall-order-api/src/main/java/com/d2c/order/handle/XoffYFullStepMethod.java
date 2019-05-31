package com.d2c.order.handle;

import com.d2c.product.model.Promotion;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

/**
 * X_OFF_Y_FULLSTEP(6, "满件减", 1)
 */
public class XoffYFullStepMethod implements XoffYProcessPriceBehavior {

    @Override
    public BigDecimal process(Promotion promotion, Set<PromotionCalculateItem> set) {
        if (set == null || set.size() <= 0) {
            return new BigDecimal(0);
        }
        if (promotion.isOver()) {
            return new BigDecimal(0);
        }
        // 先算出总件数
        int totalCount = 0;
        for (PromotionCalculateItem item : set) {
            totalCount = totalCount + item.getQuantity();
        }
        String[] solutions = promotion.getSolution().split(",");
        // 优惠金额
        BigDecimal y = new BigDecimal(0);
        for (String solution : solutions) {
            String[] codition = solution.split("-");
            if (totalCount >= Integer.parseInt(codition[0]) && y.compareTo(new BigDecimal(codition[1])) < 0) {
                y = new BigDecimal(codition[1]);
            }
        }
        return y;
    }

    @Override
    public String getName() {
        return "满件减";
    }

    @Override
    public BigDecimal splitAmount(BigDecimal currenPromotion, BigDecimal totalProductAmount, Promotion promotion,
                                  Set<PromotionCalculateItem> set, PromotionCalculateResult result) {
        Iterator<PromotionCalculateItem> iterator = set.iterator();
        int i = 1;
        BigDecimal rateResult = new BigDecimal(0);
        BigDecimal totalPromotion = new BigDecimal(0);
        while (iterator.hasNext()) {
            PromotionCalculateItem item = iterator.next();
            BigDecimal itemAmount = item.getTotalProductAmount().subtract(item.getPromotionAmount());
            if (currenPromotion.compareTo(totalProductAmount) >= 0) {
                item.setOrderPromotionAmount(itemAmount);
                item.setOrderPromotionName(promotion.getName());
                totalPromotion = totalPromotion.add(itemAmount);
                continue;
            }
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
            totalPromotion = currenPromotion;
            i++;
        }
        StringBuilder promotionNames = new StringBuilder();
        promotionNames.append(promotion.getName() + " 优惠金额：&yen;" + totalPromotion.intValue() + "; ");
        result.setOrderPromotionNames(promotionNames.toString());
        return totalPromotion;
    }

}
