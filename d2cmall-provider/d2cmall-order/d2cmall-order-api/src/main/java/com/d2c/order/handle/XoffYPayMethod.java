package com.d2c.order.handle;

import com.d2c.product.model.Promotion;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * N元任选
 *
 * @author Administrator
 */
public class XoffYPayMethod implements XoffYProcessPriceBehavior {

    /**
     * 返回优惠金额
     */
    @Override
    public BigDecimal process(Promotion promotion, Set<PromotionCalculateItem> set) {
        BigDecimal zero = new BigDecimal(0);
        if (set == null || set.size() <= 0) {
            return zero;
        }
        if (promotion.isOver()) {
            return zero;
        }
        // 先算出总件数和总金额
        int quantity = 0;
        BigDecimal totalAmount = new BigDecimal(0);
        Map<BigDecimal, Integer> map = new TreeMap<>();
        for (PromotionCalculateItem item : set) {
            quantity = quantity + item.getQuantity();
            totalAmount = totalAmount.add(item.getTotalProductAmount().subtract(item.getPromotionAmount()));
            BigDecimal productPrice = item.getProductPrice().subtract(item.getPromotionPrice());
            if (map.containsKey(productPrice)) {
                map.put(productPrice, map.get(productPrice) + item.getQuantity());
            } else {
                map.put(productPrice, item.getQuantity());
            }
        }
        // 参与活动价
        BigDecimal amount = new BigDecimal(0);
        // 最大满足件数
        String[] solutions = promotion.getSolution().split(",");
        int X = 0;
        for (String solution : solutions) {
            String[] codition = solution.split("-");
            if (quantity >= Integer.parseInt(codition[0]) && X < Integer.parseInt(codition[0])) {
                X = Integer.parseInt(codition[0]);
                amount = new BigDecimal(codition[1]);
            }
        }
        // 选择需要另付的商品的金额（价格最低的开始算）
        int otherCount = quantity - X;
        if (otherCount > 0) {
            // treeMap按升序排
            for (BigDecimal price : map.keySet()) {
                if (otherCount - map.get(price) <= 0) {
                    amount = amount.add(price.multiply(new BigDecimal(otherCount)));
                    break;
                } else {
                    amount = amount.add(price.multiply(new BigDecimal(map.get(price))));
                    otherCount = otherCount - map.get(price);
                }
            }
        }
        // 设置满n支付0元这种，就当做不参加活动，当amount>totalAmount,外面有控制
        if (amount.compareTo(zero) <= 0) {
            return zero;
        }
        return totalAmount.subtract(amount);
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
            // 最后一件减
            if (set.size() == i && set.size() > 1) {
                item.setOrderPromotionAmount(
                        currenPromotion.subtract(rateResult).setScale(2, BigDecimal.ROUND_HALF_UP));
                item.setOrderPromotionName(promotion.getName());
                break;
            }
            // 每种商品优惠=每种商品总原价-每种商品总原价\商品总价*实付总价
            BigDecimal ratePromotion = itemAmount.divide(totalProductAmount, 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal promotionAmount = ratePromotion.multiply(currenPromotion).setScale(2, BigDecimal.ROUND_HALF_UP);
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

    @Override
    public String getName() {
        return "N元任选";
    }

}
