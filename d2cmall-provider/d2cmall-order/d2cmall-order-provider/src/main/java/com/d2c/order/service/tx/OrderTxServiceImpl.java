package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.OrderLog;
import com.d2c.logger.model.OrderLog.OrderLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.OrderLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.RedPacketsItem;
import com.d2c.order.mongo.service.BargainPriceService;
import com.d2c.order.service.CouponService;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.OrderService;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.order.third.kaola.enums.CloseReason;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.service.ProductSkuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service(protocol = {"dubbo"})
public class OrderTxServiceImpl implements OrderTxService {

    private static final Log logger = LogFactory.getLog(OrderTxServiceImpl.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private BargainPriceService bargainPriceService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Reference
    private AccountTxService accountTxService;
    @Reference
    private OrderItemTxService orderItemTxService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public OrderDto doCreateOrder(OrderDto order, String coupons, Integer redPacket) {
        if (order.getOrderItems() == null || order.getOrderItems().size() < 1) {
            throw new BusinessException("商品明细不存在，订单创建不成功！");
        }
        // 冻结红包
        if (redPacket != null && redPacket == 1 && order.getRedAmount().compareTo(new BigDecimal(0)) > 0) {
            RedPacketsItem redPacketsItem = accountTxService.doFreezeRed(order.getMemberId(),
                    order.getRedAmount().multiply(new BigDecimal(-1)));
            order.setRedId(redPacketsItem.getId());
        }
        // 创建订单
        OrderDto newOrderDto = new OrderDto();
        order.setOrderStatus(OrderStatus.WaitingForPay.getCode());
        Order newOrder = orderService.insert(order);
        BeanUtils.copyProperties(newOrder, newOrderDto);
        List<OrderItemDto> orderItems = new ArrayList<>();
        for (OrderItemDto orderItem : order.getOrderItems()) {
            // 冻结商品库存
            int result = productSkuService.doFreezeStock(orderItem.getProductId(), orderItem.getProductSkuId(),
                    orderItem.getQuantity());
            if (result <= 0) {
                logger.error("数据库，skuId:" + orderItem.getProductSkuId() + "名称：" + orderItem.getProductName()
                        + "库存不足，下单不成功！");
                throw new BusinessException(orderItem.getProductName() + "库存不足，下单不成功！");
            }
            // 创建订单明细信息
            orderItem.setOrderIdAndSn(newOrder.getId(), newOrder.getOrderSn());
            orderItem = orderItemService.insert(orderItem);
            orderItems.add(orderItem);
        }
        newOrderDto.setOrderItems(orderItems);
        // 冻结优惠券
        if (!StringUtils.isEmpty(coupons) && order.getCouponAmount().compareTo(new BigDecimal(0)) > 0) {
            BigDecimal couponMoney = couponService.doLockCoupon(newOrder.getId(), newOrder.getOrderSn(),
                    newOrder.getTotalAmount(), coupons);
            if (couponMoney.compareTo(new BigDecimal(0)) <= 0) {
                orderService.updateCouponAmount(newOrder.getId(), null, new BigDecimal(0));
            }
        }
        return newOrderDto;
    }

    /**
     * 关闭订单 1.未付款订单的关闭 2.拼团失败订单的关闭
     *
     * @param order
     * @return
     * @throws Exception
     */
    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCloseOrder(Long orderId, String closeReason, String operator, OrderStatus closeStatus) {
        Order order = orderService.findById(orderId);
        int success = orderService.updateCloseInfo(orderId, closeReason, operator, closeStatus.getCode());
        if (success > 0) {
            List<OrderItem> items = orderItemService.findByOrderId(orderId);
            Set<Long> warehouseIds = new HashSet<>();
            for (OrderItem item : items) {
                if (closeStatus.equals(OrderStatus.UserClose)) {
                    item.setStatus(ItemStatus.CLOSE.name());
                } else if (closeStatus.equals(OrderStatus.MallClose)) {
                    item.setStatus(ItemStatus.MALLCLOSE.name());
                } else {
                    throw new BusinessException("订单状态不匹配，不能关闭订单！");
                }
                item.setItemCloseMan(operator);
                item.setLastModifyMan(operator);
                if (item.getItemCloseReason() == null) {
                    item.setItemCloseReason(item.getItemCloseReason() + "，" + closeReason);
                }
                orderItemTxService.doCloseByOrder(item.getId(), closeReason, operator,
                        OrderStatus.getStatus(order.getOrderStatus()), item.getStatus());
                if (item.getProductSource() != null && item.getProductSource().equals(ProductSource.KAOLA.name())) {
                    warehouseIds.add(item.getWarehouseId());
                }
            }
            // 考拉商品取消
            for (Long warehouseId : warehouseIds) {
                try {
                    KaolaClient.getInstance().closeOrder(order.getOrderSn() + "-" + warehouseId, CloseReason.Other);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            // 砍价订单取消
            if (order.getType().equals(OrderType.bargain.name()) && order.getBargainId() != null) {
                bargainPriceService.updateBargainForCancel(String.valueOf(order.getBargainId()));
            }
            if (OrderStatus.UserClose.equals(closeStatus) || OrderStatus.MallClose.equals(closeStatus)) {
                // 返还优惠券
                if (order.getCouponId() != null) {
                    if (OrderStatus.WaitingForDelivery.equals(OrderStatus.getStatus(order.getOrderStatus()))) {
                        couponService.doReleaseCoupon(order.getId());
                    } else if (OrderStatus.WaitingForPay.equals(OrderStatus.getStatus(order.getOrderStatus()))) {
                        couponService.doUnlockCoupon(order.getId());
                    }
                }
                // 返还红包
                if (order.getRedId() != null) {
                    accountTxService.doBackRed(order.getRedId());
                }
            }
            if (order.getSubstitute() != null && order.getSubstitute() == 1) {
                String subject = "代付提醒";
                String content = "您的订单已超时关闭，请重新发起订单，让好友帮您付款吧！";
                PushBean pushBean = new PushBean(order.getMemberId(), content, 28);
                pushBean.setAppUrl("/details/order/" + order.getOrderSn());
                MsgBean msgBean = new MsgBean(order.getMemberId(), 28, subject, content);
                msgBean.setAppUrl("/details/order/" + order.getOrderSn());
                msgUniteService.sendPush(pushBean, msgBean);
            }
            // 记录订单日志
            createOrderLog(order.getId(), OrderLogType.Close, operator, "平台关闭订单" + "，关闭原因：" + closeReason);
        }
        return success;
    }

    /**
     * 过期未支付订单的关闭
     *
     * @param orderId
     * @return
     */
    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int doCloseExpireOrder(Long orderId) {
        return this.doCloseOrder(orderId, "超时关闭的订单", "定时器SYS", OrderStatus.MallClose);
    }

    /**
     * 拼团失败订单的关闭
     *
     * @param orderSn
     * @return
     */
    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int doCloseByCollage(String orderSn) {
        Order order = orderService.findByOrderSn(orderSn);
        if (order == null) {
            return 0;
        }
        return this.doCloseOrder(order.getId(), "拼团失败的订单", "定时器SYS", OrderStatus.MallClose);
    }

    /**
     * 记录订单日志
     *
     * @param orderId
     * @param logType
     * @param operator
     * @param info
     */
    private void createOrderLog(Long orderId, OrderLogType logType, String operator, String info) {
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderId);
        orderLog.setCreator(operator);
        orderLog.setCreateDate(new Date());
        orderLog.setInfo(info);
        orderLog.setOrderLogType(logType);
        try {
            orderLogService.insert(orderLog);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
