package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.CollageOrderLog;
import com.d2c.logger.model.CollageOrderLog.CollageLogType;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.CollageOrderLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.support.OrderInfo;
import com.d2c.order.dto.CollageGroupDto;
import com.d2c.order.dto.CollageOrderDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.model.CollageOrder.CollageOrderStatus;
import com.d2c.order.model.CollageOrder.RoleType;
import com.d2c.order.service.CollageGroupService;
import com.d2c.order.service.CollageOrderService;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.service.CollagePromotionService;
import com.d2c.product.service.ProductSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service(protocol = {"dubbo"})
public class CollageTxServiceImpl implements CollageTxService {

    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private CollageOrderService collageOrderService;
    @Autowired
    private CollageOrderLogService collageOrderLogService;
    @Autowired
    private CollageGroupService collageGroupService;
    @Autowired
    private CollagePromotionService collagePromotionService;
    @Reference
    private AccountTxService accountTxService;
    @Reference
    private OrderTxService orderTxService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CollageOrderDto createCollageOrder(CollageOrder collageOrder) {
        CollageOrder oldCollageOrder = collageOrderService.findByMemberIdAndGroupId(collageOrder.getMemberId(),
                collageOrder.getGroupId());
        if (oldCollageOrder != null) {
            throw new BusinessException("请勿重复参团！");
        }
        // 冻结库存
        productSkuService.doFreezeStock(collageOrder.getProductId(), collageOrder.getSkuId(), 1);
        // 更新参团人数
        int result = collageGroupService.updateCurrentMemberCount(collageOrder.getGroupId(), 1);
        if (result == 0) {
            throw new BusinessException("此团已满员，参团不成功！");
        }
        collageOrder = collageOrderService.insert(collageOrder);
        CollageOrderDto dto = new CollageOrderDto();
        BeanUtils.copyProperties(collageOrder, dto);
        CollageOrderLog log = new CollageOrderLog(collageOrder.getId(), CollageLogType.CREATE.name(), "拼团单创建成功",
                collageOrder.getLoginCode());
        collageOrderLogService.insert(log);
        return dto;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CollageOrderDto createCollageGroup(CollageGroup collageGroup, CollageOrder collageOrder) {
        // 创建团信息
        collageGroup = collageGroupService.insert(collageGroup);
        collageOrder.setType(RoleType.INITIATOR.getCode());
        collageOrder.setGroupId(collageGroup.getId());
        CollageOrderDto dto = this.createCollageOrder(collageOrder);
        return dto;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doClose(Long id, String closeMemo, boolean failGroup) {
        CollageOrder collageOrder = collageOrderService.findById(id);
        int success = collageOrderService.doClose(id, closeMemo);
        if (success > 0) {
            collageGroupService.updateCurrentMemberCount(collageOrder.getGroupId(), -1);
            if (!failGroup && collageOrder.getType().equals(CollageOrder.RoleType.INITIATOR.getCode())) {
                // 拼团团未失败情形下，如果是团长要另行处理
                this.processInitiator(collageOrder);
            }
            // 解冻库存
            productSkuService.doFreezeStock(collageOrder.getProductId(), collageOrder.getSkuId(), -1);
            CollageOrderLog log = new CollageOrderLog(id, CollageLogType.CLOSE.name(), "拼团单超时支付关闭", "sys");
            collageOrderLogService.insert(log);
        }
        return success;
    }

    private void processInitiator(CollageOrder collageOrder) {
        CollageGroupDto collageGroupDto = collageGroupService.findDtoById(collageOrder.getGroupId());
        if (collageGroupDto.getCurrentMemberCount() < 1) {
            // 如果现在没人了就直接关掉
            collageGroupService.doFailGroup(collageOrder.getGroupId());
        } else {
            List<CollageOrder> collageOrderList = collageGroupDto.getCollageOrders();
            for (int i = 0; i < collageOrderList.size(); i++) {
                CollageOrder order = collageOrderList.get(i);
                if (order.getStatus().equals(CollageOrder.CollageOrderStatus.PROCESS)
                        || i == collageOrderList.size() - 1) {
                    // 先把现在的团长变成团员
                    int result = collageOrderService.updateType(collageOrder.getId(), RoleType.MEMBER.getCode());
                    if (result > 0) {
                        collageOrderService.updateType(order.getId(), RoleType.INITIATOR.getCode());
                        collageGroupService.updateInitiator(order.getGroupId(), order.getMemberId(),
                                order.getMemberName(), order.getHeadPic());
                        break;
                    }
                }
            }
        }
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doRefund(Long id) {
        CollageOrder collageOrder = collageOrderService.findById(id);
        int success = 0;
        if (collageOrder.getPaymentType() == PaymentTypeEnum.WALLET.getCode()) {
            CollageOrderDto dto = new CollageOrderDto();
            BeanUtils.copyProperties(collageOrder, dto);
            OrderInfo bill = new OrderInfo(BusinessTypeEnum.COLLAGE.name(), PayTypeEnum.REFUND.name());
            BeanUtils.copyProperties(dto, bill);
            bill.setBillSourceTime(new Date());
            AccountItem item = accountTxService.doRefund(bill);
            success = collageOrderService.doRefundSuccess(id, "定时器", "钱包退款至原账户", PaymentTypeEnum.WALLET.getCode(),
                    item.getSn());
        } else {
            success = collageOrderService.doRefund(id);
        }
        if (success > 0) {
            // 关闭订单
            orderTxService.doCloseByCollage(collageOrder.getSn());
            CollageOrderLog log = new CollageOrderLog(id, CollageLogType.WAITFORREFUND.name(), "拼团失败，待退款", "sys");
            collageOrderLogService.insert(log);
            CollagePromotion promotion = collagePromotionService.findById(collageOrder.getPromotionId());
            String content = "很遗憾，您参与的拼团活动 '" + promotion.getName()
                    + "' 拼团失败了，系统将按照原路尽快给你退款，可到D2C全球好设计小程序或D2C APP中试试其他商品~";
            MsgBean msgBean = new MsgBean(collageOrder.getMemberId(), 61, "很遗憾，您参与的拼团活动失败了", content);
            msgBean.setAppUrl("/mycollage/list");
            msgBean.setPic(collageOrder.getProductImage());
            SmsBean smsBean = new SmsBean(null, collageOrder.getLoginCode(), SmsLogType.REMIND, content);
            msgUniteService.sendMsg(smsBean, null, msgBean, null);
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doFailGroup(Long id) {
        CollageGroup group = collageGroupService.findById(id);
        int success = collageGroupService.doFailGroup(id);
        if (success < 1) {
            throw new BusinessException("团号：" + group.getGroupSn() + "关闭失败！");
        }
        List<CollageOrder> list = collageOrderService.findByGroupId(id);
        for (CollageOrder collageOrder : list) {
            if (collageOrder.getStatus().equals(CollageOrderStatus.PROCESS.getCode())) {
                // 已支付走退款
                success = this.doRefund(collageOrder.getId());
            } else if (collageOrder.getStatus().equals(CollageOrderStatus.WAITFORPAY.getCode())) {
                // 未支付直接关闭
                success = this.doClose(collageOrder.getId(), "团ID:" + collageOrder.getGroupId() + "活动结束未支付关闭", true);
            }
            if (success < 1) {
                throw new BusinessException("团订单编号：" + collageOrder.getSn() + "关闭失败！");
            }
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doBackToWallet(Long id, String operator) {
        CollageOrderDto order = collageOrderService.findDtoById(id);
        if (order == null) {
            throw new BusinessException("该拼团单不存在！");
        }
        if (!order.getStatus().equals(CollageOrderStatus.REFUND.getCode())) {
            throw new BusinessException("该拼团单状态不正确！");
        }
        if (!order.getPaymentType().equals(PaymentTypeEnum.WALLET.getCode())) {
            throw new BusinessException("该拼团单支付的方式不是钱包！");
        }
        OrderInfo bill = new OrderInfo(BusinessTypeEnum.COLLAGE.name(), PayTypeEnum.REFUND.name());
        BeanUtils.copyProperties(order, bill);
        bill.setBillSourceTime(new Date());
        AccountItem item = accountTxService.doRefund(bill);
        return collageOrderService.doRefundSuccess(id, operator, "钱包退款至原账户", PaymentTypeEnum.WALLET.getCode(),
                item.getSn());
    }

}
