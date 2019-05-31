package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.Order;
import com.d2c.order.model.Setting;
import com.d2c.order.service.OrderQueryService;
import com.d2c.order.service.SettingService;
import com.d2c.order.service.tx.OrderTxService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredOrderTask extends BaseTask {

    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private SettingService settingService;
    @Reference
    private OrderTxService orderTxService;

    @Scheduled(fixedDelay = 60 * 1000 * 5)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.autoCloseOrder();
        this.autoCloseSeckillOrder();
        this.autoCloseCrossOrder();
    }

    /**
     * 过期普通订单关闭 （24小时）
     */
    private void autoCloseOrder() {
        Setting setting = settingService.findByCode(Setting.ORDERCLOSECODE);
        int closeTime = 24 * 60 * 60;
        try {
            closeTime = Integer.valueOf(Setting.defaultValue(setting, "86400").toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        final int closeSeconds = closeTime;
        try {
            this.processPager(100, new EachPage<Long>() {
                @Override
                public int count() {
                    return orderQueryService.countExpireOrder(closeSeconds,
                            new Order.OrderType[]{Order.OrderType.distribution, Order.OrderType.ordinary}, null);
                }

                @Override
                public PageResult<Long> search(PageModel page) {
                    return orderQueryService.findExpireOrder(closeSeconds,
                            new Order.OrderType[]{Order.OrderType.distribution, Order.OrderType.ordinary}, null,
                            page);
                }

                @Override
                public boolean each(Long object) {
                    int result = orderTxService.doCloseExpireOrder(object);
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 过期特殊订单关闭 （60分钟）
     */
    private void autoCloseSeckillOrder() {
        Setting setting = settingService.findByCode(Setting.SECKILLCLOSECODE);
        int closeTime = 60 * 60;
        try {
            closeTime = Integer.valueOf(Setting.defaultValue(setting, "3600").toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        final int closeSeconds = closeTime;
        try {
            this.processPager(100, new EachPage<Long>() {
                @Override
                public int count() {
                    return orderQueryService.countExpireOrder(closeSeconds,
                            new Order.OrderType[]{Order.OrderType.seckill, Order.OrderType.bargain}, null);
                }

                @Override
                public PageResult<Long> search(PageModel page) {
                    return orderQueryService.findExpireOrder(closeSeconds,
                            new Order.OrderType[]{Order.OrderType.seckill, Order.OrderType.bargain}, null, page);
                }

                @Override
                public boolean each(Long object) {
                    int result = orderTxService.doCloseExpireOrder(object);
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 过期跨境订单关闭 （20分钟）
     */
    private void autoCloseCrossOrder() {
        Setting setting = settingService.findByCode(Setting.CROSSCLOSECODE);
        int closeTime = 20 * 60;
        try {
            closeTime = Integer.valueOf(Setting.defaultValue(setting, "1200").toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        final int closeSeconds = closeTime;
        try {
            this.processPager(100, new EachPage<Long>() {
                @Override
                public int count() {
                    return orderQueryService.countExpireOrder(closeSeconds, null, 1);
                }

                @Override
                public PageResult<Long> search(PageModel page) {
                    return orderQueryService.findExpireOrder(closeSeconds, null, 1, page);
                }

                @Override
                public boolean each(Long object) {
                    int result = orderTxService.doCloseExpireOrder(object);
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
