package com.d2c.quartz.task;

import com.d2c.order.service.CouponDefService;
import com.d2c.order.service.CouponService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredCouponTask extends BaseTask {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponDefService couponDefService;

    @Scheduled(cron = "0 30 2 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doCloseExpiredCoupon();
        this.doCloseExpiredCouponDef();
    }

    /**
     * 停用过期优惠券
     */
    private void doCloseExpiredCoupon() {
        try {
            couponService.doCloseExpiredCoupon();
            couponService.doCopy2CouponHistory();
            couponService.doDeleteExpiredCoupon();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 停用过期的优惠券定义
     */
    private void doCloseExpiredCouponDef() {
        try {
            couponDefService.doCloseExpiredCouponDef();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
