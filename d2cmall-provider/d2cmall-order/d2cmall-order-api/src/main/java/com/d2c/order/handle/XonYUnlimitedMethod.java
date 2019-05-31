package com.d2c.order.handle;

import com.d2c.order.model.Recharge;
import com.d2c.order.model.RechargeRule;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 满送上不封顶
 */
public class XonYUnlimitedMethod implements XonYProcessPriceBehavior {

    @Override
    public void process(RechargeRule rule, Recharge recharge) {
        if (rule.isOver()) {
            return;
        }
        // 计算商品总价
        BigDecimal total = recharge.getRechargeAmount();
        // 类似 200+2
        String[] solutions = rule.getSolution().split("\\+");
        BigDecimal X = new BigDecimal(solutions[0]);
        BigDecimal Y = new BigDecimal(solutions[1]);
        BigDecimal multiple = total.divide(X, 0, RoundingMode.FLOOR);
        BigDecimal on = Y.multiply(multiple);
        // 获取价格范围
        // 做满送
        recharge.setRuleId(rule.getId());
        recharge.setRuleName(rule.getName());
        recharge.setGiftAmount(on);
        // 仅限在期限内使用
        if (rule.getLimited() > 0) {
            recharge.setLimited(rule.getLimited());
            recharge.setLimitDate(rule.getEndTime());
        }
    }

    @Override
    public String getName() {
        return "满送上不封顶";
    }

}
