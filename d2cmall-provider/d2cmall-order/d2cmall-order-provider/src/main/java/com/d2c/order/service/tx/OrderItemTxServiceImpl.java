package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.behavior.services.DepictDataService;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.logger.model.OrderLog;
import com.d2c.logger.model.OrderLog.OrderLogType;
import com.d2c.logger.service.OrderLogService;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.service.MemberIntegrationService;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.model.PartnerBill;
import com.d2c.order.service.CustomerCompensationService;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.PartnerBillService;
import com.d2c.order.service.RequisitionItemService;
import com.d2c.product.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Service(protocol = {"dubbo"})
public class OrderItemTxServiceImpl implements OrderItemTxService {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private PartnerBillService partnerBillService;
    @Autowired
    private MemberIntegrationService memberIntegrationService;
    @Autowired
    private CustomerCompensationService customerCompensationService;
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;
    @Reference
    private PartnerTxService partnerTxService;
    @Reference
    private DepictDataService depictDataService;

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCloseByOrder(Long itemId, String closeReason, String operator, OrderStatus status, String itemStatus) {
        OrderItem orderItem = orderItemService.findById(itemId);
        if (status.getCode() == OrderStatus.WaitingForPay.getCode()) {
            // 待付款订单，释放库存
            productSkuService.doFreezeStock(orderItem.getProductId(), orderItem.getProductSkuId(),
                    orderItem.getProductQuantity() * -1);
        }
        if (status.getCode() == OrderStatus.WaitingForDelivery.getCode()) {
            // 已付款订单，归还库存
            productSkuService.doRevertStock(orderItem.getProductId(), orderItem.getProductSkuId(),
                    orderItem.getProductQuantity(), orderItem.getPop());
        }
        int success = orderItemService.doCloseByOrder(orderItem.getId(), operator, closeReason, itemStatus);
        // 关闭调拨单
        if (success > 0 && orderItem.getRequisition() > 0) {
            redisHandler.delete("orderitem_count_" + orderItem.getBuyerMemberId());
            requisitionItemService.doCloseByOrderItem(itemId, operator, closeReason);
            partnerBillService.doClose(itemId);
        }
        return success;
    }

    /**
     * 系统确认收货，只签收确认订单，包含申请退款退货的单据不能处理。
     *
     * @param itemId
     * @param operator
     */
    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSysSuccess(Long itemId, String operator, boolean off, BigDecimal diffAmount) {
        OrderItem item = orderItemService.findById(itemId);
        int success = 0;
        item.setStatus(ItemStatus.SUCCESS.name());
        success = orderItemService.doSuccess(itemId, diffAmount);
        if (success > 0) {
            redisHandler.delete("orderitem_count_" + item.getBuyerMemberId());
            item.setDiffAmount(diffAmount);
            // 订单完结，增加积分
            MemberIntegration integration = new MemberIntegration(PointRuleTypeEnum.ORDER, item.getBuyerMemberId(),
                    item.getBuyerMemberName(), item.getId(), item.getCreateDate());
            memberIntegrationService.addIntegration(integration, PointRuleTypeEnum.ORDER,
                    item.getActualAmount().subtract(diffAmount), null, null);
            createOrderLog(item.getOrderId(), item.getId(), OrderLogType.Success, operator, "交易完成");
            PartnerBill partnerBill = partnerBillService.findByOrderItemId(itemId);
            if (partnerBill != null) {
                partnerTxService.doBillSuccess(partnerBill.getId(), operator, off, diffAmount,
                        item.getPartnerAmount().compareTo(new BigDecimal(0)) > 0, item.getPartnerAmount());
            }
            // 赔偿金额大于0的
            if (item.getCompensationAmount().compareTo(new BigDecimal(0)) > 0) {
                customerCompensationService.updateStatusByOrderItem(item.getId());
            }
            // depictDataService.addByOrderItem(item);
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doAutoSuccess(Long itemId, String operator, boolean off, BigDecimal diffAmount) {
        return this.doSysSuccess(itemId, operator, off, diffAmount);
    }

    private int createOrderLog(Long orderId, Long orderItemId, OrderLogType logType, String operator, String info) {
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderId);
        orderLog.setCreator(operator);
        orderLog.setCreateDate(new Date());
        orderLog.setOrderItemId(orderItemId);
        orderLog.setInfo(info);
        orderLog.setOrderLogType(logType);
        orderLogService.insert(orderLog);
        return 1;
    }

}
