package com.d2c.order.handle;

import com.d2c.product.model.Promotion;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

/**
 * X_OFF_Y_FULLPCS(5, "满件折扣", 1)
 */
public class XoffYFullPscMethod implements XoffYProcessPriceBehavior {

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
        // 商品打的折数
        BigDecimal Y = new BigDecimal(1);
        for (String solution : solutions) {
            String[] codition = solution.split("-");
            if (totalCount >= Integer.parseInt(codition[0]) && Y.compareTo(new BigDecimal(codition[1])) > 0)
                Y = new BigDecimal(codition[1]);
        }
        return new BigDecimal(1).subtract(Y);
    }

    @Override
    public String getName() {
        return "满件折扣";
    }

    @Override
    public BigDecimal splitAmount(BigDecimal currenPromotion, BigDecimal totalProductAmount, Promotion promotion,
                                  Set<PromotionCalculateItem> set, PromotionCalculateResult result) {
        Iterator<PromotionCalculateItem> iterator = set.iterator();
        while (iterator.hasNext()) {
            PromotionCalculateItem item = iterator.next();
            item.setOrderPromotionAmount(
                    currenPromotion.multiply(item.getTotalProductAmount().subtract(item.getPromotionAmount()))
                            .setScale(2, BigDecimal.ROUND_HALF_UP));
            item.setOrderPromotionName(promotion.getName());
        }
        currenPromotion = currenPromotion.multiply(totalProductAmount);
        StringBuilder promotionNames = new StringBuilder();
        promotionNames.append(
                promotion.getName() + "  优惠金额：&yen;" + currenPromotion.setScale(2, BigDecimal.ROUND_HALF_UP) + "; ");
        result.setOrderPromotionNames(promotionNames.toString());
        return currenPromotion;
    }

}
