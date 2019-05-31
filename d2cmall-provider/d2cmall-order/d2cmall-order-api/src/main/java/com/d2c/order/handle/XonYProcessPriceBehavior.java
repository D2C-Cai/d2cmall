package com.d2c.order.handle;

import com.d2c.order.model.Recharge;
import com.d2c.order.model.RechargeRule;

/**
 * 满X送Y
 */
public interface XonYProcessPriceBehavior {

    void process(RechargeRule rule, Recharge recharge);

    String getName();

}
