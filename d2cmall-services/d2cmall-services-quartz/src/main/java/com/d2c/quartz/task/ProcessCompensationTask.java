package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.Compensation;
import com.d2c.order.model.CustomerCompensation.CompensationType;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.service.CompensationService;
import com.d2c.order.service.CustomerCompensationService;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.RequisitionItemService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class ProcessCompensationTask extends BaseTask {

    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private CompensationService compensationService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CustomerCompensationService customerCompensationService;

    /**
     * 赔偿
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doDesignerCompensation();
        this.doStoreCompensation();
        this.doCustomerCompensation();
    }

    /**
     * 设计师赔偿
     */
    private void doDesignerCompensation() {
        // 设计师超过一定时间不操作需赔偿，按 min(300,30%),所以10%+3%+3%算是1+7=8天，到第八天就到顶了
        // 查找距离当前时间1天还未赔偿的调拨单
        Date deadline = DateUtil.getIntervalDay(new Date(), -8);
        try {
            this.processPager(100, new EachPage<RequisitionItem>() {
                @Override
                public int count() {
                    return requisitionItemService.countDesignerExpired(deadline);
                }

                @Override
                public PageResult<RequisitionItem> search(PageModel page) {
                    return requisitionItemService.findDesignerExpired(page, deadline);
                }

                @Override
                public boolean each(RequisitionItem object) {
                    Compensation compensation = new Compensation(object);
                    compensation.setCreator("sys");
                    compensation = compensationService.insert(compensation, null);
                    return (compensation != null && compensation.getId() != null && compensation.getId() > 0) ? true
                            : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 门店赔偿
     */
    private void doStoreCompensation() {
        // 查找距离当前时间1天还未赔偿的调拨单
        PageModel page = new PageModel();
        PageResult<RequisitionItem> pager = new PageResult<>(page);
        do {
            pager = requisitionItemService.findStoreExpired(page);
            for (RequisitionItem item : pager.getList()) {
                try {
                    Compensation compensation = new Compensation(item);
                    compensation.setCreator("sys");
                    compensation.setEstimateDate(DateUtil.add(item.getRequisitionDate(), Calendar.DAY_OF_MONTH, 1));
                    compensationService.insert(compensation, null);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            page.setP(page.getP() + 1);
        } while (pager.isNext());
    }

    /**
     * 用户赔偿
     */
    private void doCustomerCompensation() {
        // 超过当前时间未发货的都是超期的
        Date dealine = DateUtil.getIntervalDay(new Date(), -8);
        PageModel page = new PageModel();
        PageResult<OrderItem> pager = new PageResult<>(page);
        do {
            pager = orderItemService.findCompensation(page, dealine);
            for (OrderItem orderItem : pager.getList()) {
                try {
                    // 没有申请退款的
                    if (orderItem.getStatus().equals(ItemStatus.NORMAL.name()) && orderItem.getRefundId() == null) {
                        customerCompensationService.doOrderItemCompensation(orderItem, "sys",
                                CompensationType.UNSOLVED.getCode());
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            page.setP(page.getP() + 1);
        } while (pager.isNext());
    }

}
