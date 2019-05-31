package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.*;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.ExchangeTxService;
import com.d2c.order.service.tx.OrderItemTxService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class ProcessOrderStatusTask extends BaseTask {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Reference
    private OrderItemTxService orderItemTxService;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private SettingService settingService;
    @Reference
    private ExchangeTxService exchangeTxService;
    @Autowired
    private ReshipService reshipService;

    @Scheduled(cron = "0 30 2 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        // 发货，公司寄出15天后默认收货
        this.autoSignOrderItem();
        // 换货，公司寄出15天后默认收货
        this.autoSuccessExchange();
        // 订单明细签收7天后，默认完结
        this.autoSuccessItem();
        // 订单交易完成，后续操作
        this.autoSuccessOrder();
    }
    // /**
    // * 发货3天后，物流信息签收的，自动确认收货
    // */
    // private void autoSignByLogistic() {
    // Setting setting = settingService.findByCode(Setting.ORDERAFTERCLOSE);
    // try {
    // this.processPager(500, new EachPage<OrderItem>() {
    //
    // @Override
    // public int count() {
    // return orderItemService.countDeliveredByDate(new Date(), 3);
    // }
    //
    // @Override
    // public PageResult<OrderItem> search(PageModel page) {
    // return orderItemService.findDeliveredByDate(new Date(), 3, page);
    // }
    //
    // @Override
    // public boolean each(OrderItem object) {
    // if (StringUtils.isNotBlank(object.getDeliverySn())
    // && StringUtils.isNotBlank(object.getDeliveryCorpCode())) {
    // String response = ExpressClient.query(object.getDeliveryCorpCode(),
    // object.getDeliverySn());
    // Result rs = JacksonHelper.fromJSON(response, Result.class);
    // if (rs != null && rs.getState().equals("3")) {
    // int result = orderItemService.doSign(object.getId(), "sys",
    // "物流显示签收，系统确认收货",
    // Integer.parseInt(setting.getValue()));
    // return result > 0 ? true : false;
    // }
    // }
    // return false;
    // }
    //
    // });
    // } catch (Exception e) {
    // logger.error(e.getMessage(), e);
    // }
    // }

    /**
     * 发货，公司寄出15天后默认收货
     */
    private void autoSignOrderItem() {
        Setting setting = settingService.findByCode(Setting.ORDERAFTERCLOSE);
        try {
            this.processPager(500, new EachPage<OrderItem>() {
                @Override
                public int count() {
                    return orderItemService.countDeliveredByDate(new Date(), 15);
                }

                @Override
                public PageResult<OrderItem> search(PageModel page) {
                    return orderItemService.findDeliveredByDate(new Date(), 15, page);
                }

                @Override
                public boolean each(OrderItem object) {
                    if (StringUtils.isNotBlank(object.getDeliverySn())
                            && StringUtils.isNotBlank(object.getDeliveryCorpName())) {
                        int result = orderItemService.doSign(object.getId(), "定时器", "已发货15天，自动确认收货",
                                Integer.parseInt(Setting.defaultValue(setting, new Integer(7)).toString()));
                        return result > 0 ? true : false;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 换货，公司寄出15天后默认收货
     */
    private void autoSuccessExchange() {
        try {
            this.processPager(500, new EachPage<Exchange>() {
                @Override
                public int count() {
                    return exchangeService.countDeliveredByDate(new Date(), 15);
                }

                @Override
                public PageResult<Exchange> search(PageModel page) {
                    return exchangeService.findDeliveredByDate(new Date(), 15, page);
                }

                @Override
                public boolean each(Exchange object) {
                    if (StringUtils.isNotBlank(object.getExchangeDeliveryCorp())
                            && StringUtils.isNotBlank(object.getExchangeDeliverySn())) {
                        int result = exchangeTxService.doReceive(object.getId(), "定时器", "已发货15天，系统确认收货");
                        return result > 0 ? true : false;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 订单明细签收7天后，默认完结
     */
    private void autoSuccessItem() {
        try {
            this.processPager(500, new EachPage<OrderItem>() {
                @Override
                public int count() {
                    return orderItemService.countSignedByDate(new Date());
                }

                @Override
                public PageResult<OrderItem> search(PageModel page) {
                    return orderItemService.findSignedByDate(new Date(), page);
                }

                @Override
                public boolean each(OrderItem object) {
                    if (object.getRefundId() != null) {
                        Refund refund = refundService.findById(object.getRefundId());
                        if (refund != null && refund.getRefundStatus() >= 0) {
                            return false;
                        }
                    }
                    if (object.getReshipId() != null) {
                        Reship reship = reshipService.findById(object.getReshipId());
                        if (reship != null && reship.getReshipStatus() >= 0) {
                            return false;
                        }
                    }
                    if (object.getExchangeId() != null) {
                        Exchange exchange = exchangeService.findById(object.getExchangeId());
                        if (exchange != null && exchange.getExchangeStatus() >= 0) {
                            return false;
                        }
                    }
                    int result = orderItemTxService.doAutoSuccess(object.getId(), "定时器", false, new BigDecimal(0));
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 订单交易完成，后续操作
     */
    private void autoSuccessOrder() {
        try {
            this.processPager(500, new EachPage<Order>() {
                @Override
                public int count() {
                    return orderQueryService.countWaitingSuccessOrder(
                            new OrderStatus[]{OrderStatus.Delivered, OrderStatus.WaitingForDelivery});
                }

                @Override
                public PageResult<Order> search(PageModel page) {
                    return orderQueryService.findWaitingSuccessOrder(
                            new OrderStatus[]{OrderStatus.Delivered, OrderStatus.WaitingForDelivery}, page);
                }

                @Override
                public boolean each(Order object) {
                    // 订单交易完成
                    int result = orderService.doSuccessByItem(object.getId(),
                            OrderStatus.getStatus(object.getOrderStatus()), "定时器");
                    OrderSearcher searcher = new OrderSearcher();
                    searcher.setMemberId(object.getMemberId());
                    searcher.setOrderStatus(new OrderStatus[]{OrderStatus.Success});
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}