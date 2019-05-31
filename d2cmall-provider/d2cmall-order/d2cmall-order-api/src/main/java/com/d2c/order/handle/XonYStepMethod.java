package com.d2c.order.handle;

import com.d2c.order.model.Recharge;
import com.d2c.order.model.RechargeRule;

import java.math.BigDecimal;

/**
 * 满送
 */
public class XonYStepMethod implements XonYProcessPriceBehavior {

    @Override
    public void process(RechargeRule rule, Recharge recharge) {
        if (rule.isOver()) {
            return;
        }
        // 计算商品总价
        BigDecimal total = recharge.getRechargeAmount();
        // 类似 200+20,400+50,1000+200
        String[] solutions = rule.getSolution().split(",");
        String Y = "0";
        for (String solution : solutions) {
            String[] X = solution.split("\\+");
            if (total.compareTo(new BigDecimal(X[0])) >= 0 && new BigDecimal(X[1]).compareTo(new BigDecimal(Y)) > 0)
                Y = X[1];
        }
        // 获取价格范围
        // 做满减
        BigDecimal on = new BigDecimal(Y);
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
        return "满送";
    }

}
