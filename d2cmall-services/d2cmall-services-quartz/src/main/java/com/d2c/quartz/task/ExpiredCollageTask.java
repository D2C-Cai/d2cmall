package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.model.Setting;
import com.d2c.order.service.CollageGroupService;
import com.d2c.order.service.CollageOrderService;
import com.d2c.order.service.SettingService;
import com.d2c.order.service.tx.CollageTxService;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.service.CollagePromotionService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import com.d2c.util.serial.SerialNumUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * 拼团超时关闭和支付超时关闭
 *
 * @author wwn
 */
@Component
public class ExpiredCollageTask extends BaseTask {

    @Autowired
    private CollageOrderService collageOrderService;
    @Autowired
    private CollageGroupService collageGroupService;
    @Autowired
    private CollagePromotionService collagePromotionService;
    @Autowired
    private SettingService settingService;
    @Reference
    private CollageTxService collageTxService;

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doCloseExpiredPayOrder();
        this.doCloseExpiredGroup();
    }

    /**
     * 关闭超时支付的订单
     */
    private void doCloseExpiredPayOrder() {
        Setting setting = settingService.findByCode(Setting.COLLAGECLOSECODE);
        // 5分钟
        Long second = 300L;
        try {
            second = Long.parseLong(Setting.defaultValue(setting, "300").toString());
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        // 创建时间>deadline
        Date deadline = new Date(new Date().getTime() - second * 1000);
        try {
            this.processPager(100, new EachPage<CollageOrder>() {
                @Override
                public int count() {
                    return collageOrderService.countExpirePayOrder(deadline);
                }

                @Override
                public PageResult<CollageOrder> search(PageModel page) {
                    return collageOrderService.findExpirePayOrder(deadline, page);
                }

                @Override
                public boolean each(CollageOrder collageOrder) {
                    int result = collageTxService.doClose(collageOrder.getId(),
                            "团id:" + collageOrder.getGroupId() + "超时支付关闭", false);
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 关闭超时的团
     */
    private void doCloseExpiredGroup() {
        Date deadline = new Date();
        try {
            this.processPager(100, new EachPage<CollageGroup>() {
                // endDate<=now，currentCount<memberCount
                @Override
                public int count() {
                    return collageGroupService.countExpireGroup(deadline);
                }

                @Override
                public PageResult<CollageGroup> search(PageModel page) {
                    return collageGroupService.findExpireGroup(deadline, page);
                }

                @Override
                public boolean each(CollageGroup group) {
                    CollagePromotion collagePromotion = collagePromotionService.findById(group.getPromotionId());
                    // 要求达到N人就虚拟成团
                    if (collagePromotion.getRealCount() > 0) {
                        if (group.getCurrentMemberCount().equals(group.getPayMemberCount())) {
                            // 没有人正在付款，且付款人数达到要求
                            if (group.getPayMemberCount() >= collagePromotion.getRealCount()) {
                                // 虚拟成团
                                collageGroupService.doSuccess(group);
                                CollageOrder example = collageOrderService.findOneByGroupId(group.getId());
                                int num = group.getMemberCount() - group.getPayMemberCount();
                                if (num > 0) {
                                    for (int i = 0; i < num; i++) {
                                        this.forgeCollageOrder(example);
                                    }
                                    return true;
                                }
                            } else {
                                // 成团失败
                                int result = collageTxService.doFailGroup(group.getId());
                                return result > 0 ? true : false;
                            }
                        } else {
                            // 存在有人正在付款，不进行操作
                        }
                    } else {
                        // 成团失败
                        int result = collageTxService.doFailGroup(group.getId());
                        return result > 0 ? true : false;
                    }
                    return false;
                }

                private void forgeCollageOrder(CollageOrder example) {
                    CollageOrder order = new CollageOrder();
                    BeanUtils.copyProperties(example, order, "id");
                    String name = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                    order.setType(0);
                    order.setSn(SerialNumUtil.buildOrderSn("0"));
                    order.setMemberId(0L);
                    order.setLoginCode(name);
                    order.setMemberName(name);
                    order.setHeadPic(null);
                    collageOrderService.insert(order);
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
