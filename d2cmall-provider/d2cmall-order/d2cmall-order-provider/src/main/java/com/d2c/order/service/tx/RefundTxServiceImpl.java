package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.RefundLog;
import com.d2c.logger.model.RefundLog.RefundLogType;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.RefundLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.support.OrderInfo;
import com.d2c.order.dto.RefundDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.Refund;
import com.d2c.order.model.Refund.RefundStatus;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.RefundService;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(protocol = {"dubbo"})
public class RefundTxServiceImpl implements RefundTxService {

    @Autowired
    private RefundService refundService;
    @Reference
    private AccountTxService accountTxService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private RefundLogService refundLogService;
    @Autowired
    private OrderItemService orderItemService;
    @Reference
    private OrderItemTxService orderItemTxService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doSuccessRefundWallet(Long refundId, String payer, String ip, int allRefund, Long userId) {
        Refund refund = refundService.findById(refundId);
        refund.setPayMoney(refund.getTotalAmount());
        if (refund.getPayMoney().compareTo(new BigDecimal("0.01")) < 0) {
            refund.setPayer(payer);
            refund.setPaySn(refund.getRefundSn());
            refund.setPayDate(new Date());
            refund.setPayMoney(refund.getTotalAmount());
            refund.setCreatePayDate(new Date());
        } else {
            OrderInfo billInfo = new OrderInfo(BusinessTypeEnum.ORDER.name(), PayTypeEnum.REFUND.name());
            RefundDto dto = new RefundDto();
            BeanUtils.copyProperties(refund, dto);
            BeanUtils.copyProperties(dto, billInfo, "memo");
            AccountItem item = accountTxService.doRefund(billInfo);
            if (item != null) {
                refund.setPayer(payer);
                refund.setPaySn(item.getSn());
                refund.setPayDate(item.getCreateDate());
                refund.setPayMoney(item.getFactTotalAmount());
                refund.setCreatePayDate(new Date());
                refundService.updateWalletAmount(refund.getId(), item.getAmount(), item.getGiftAmount());
            } else {
                throw new BusinessException("退款不成功！");
            }
        }
        int success = this.doSuccessRefund(refundId, PaymentTypeEnum.WALLET.getCode(), new Date(), refund.getPayMoney(),
                refund.getPaySn(), payer, ip, allRefund, userId);
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doThirdSuccess(String refundSn, Date notifyTime, BigDecimal payMoney, String paySn) {
        Refund refund = refundService.findByRefundSn(refundSn);
        int success = 0;
        if (refund != null) {
            success = this.doSuccessRefund(refund.getId(), refund.getOrderPayType(), notifyTime, payMoney, paySn,
                    refund.getPayer(), null, refund.getAllRefund(), null);
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSuccessRefund(Long refundId, Integer backAccountType, Date payDate, BigDecimal payMoney, String paySn,
                               String payer, String ip, int allRefund, Long userId) {
        Refund refund = refundService.findById(refundId);
        if (refund == null) {
            throw new BusinessException("退款单不存在，退款不成功！");
        }
        // 第三方支付(支付宝和微信)
        int result = 0;
        if (refund.getAutoRefund() == 1) {
            result = refundService.doThirdSuccess(refund.getId(), paySn, payDate);
        } else {
            if (refund.getLocked() == 1) {
                throw new BusinessException("退款已经被锁定，退款不成功！");
            }
            if (StringUtils.isBlank(paySn)) {
                throw new BusinessException("流水号不能为空，退款不成功！");
            }
            if (!refund.getRefundStatus().equals(RefundStatus.WAITFORPAYMENT.getCode())) {
                throw new BusinessException("状态不匹配，退款不成功，当前状态为：" + refund.getRefundStatusName());
            }
            if (payMoney.compareTo(refund.getTotalAmount()) > 0) {
                throw new BusinessException("退款金额大于审核金额，退款不成功，退款编号为：" + refund.getRefundSn());
            }
            result = refundService.doSuccess(refund.getId(), backAccountType, payDate, payMoney, paySn, payer,
                    allRefund);
        }
        if (result > 0) {
            if (refund.getReshipId() != null) {
                String closeInfo = "退款退货成功，关闭订单，退款编号：" + refund.getRefundSn();
                orderItemService.doCloseItem(refund.getOrderItemId(), payer, closeInfo);
            } else {
                String closeInfo = "退款成功，关闭订单，退款编号：" + refund.getRefundSn();
                if (allRefund == 1) {
                    // 全额退款，则关闭订单
                    orderItemService.doCloseItem(refund.getOrderItemId(), payer, closeInfo);
                } else if (allRefund == -1) {
                    // 退差价，则完成订单，扣返利
                    orderItemTxService.doSysSuccess(refund.getOrderItemId(), payer, true, payMoney);
                } else {
                    // 退运费，则完成订单，全额返利
                    orderItemTxService.doSysSuccess(refund.getOrderItemId(), payer, false, new BigDecimal(0));
                }
            }
            // 退款退货完成
            String info = "退款已完成，流水号：" + paySn + "，退款时间：" + DateUtil.day2str(payDate) + "，退款金额：" + payMoney;
            int success = orderItemService.updateRefund(refund.getId(), refund.getOrderItemId());
            if (success <= 0) {
                throw new BusinessException("退货或退款单，不能重复申请！");
            }
            if (payMoney.compareTo(new BigDecimal("0")) > 0) {
                Map<String, String> map = new HashMap<>();
                map.put("name", refund.getLoginCode());
                map.put("refundSn", refund.getRefundSn());
                PaymentTypeEnum backPayType = PaymentTypeEnum.getByCode(refund.getBackAccountType());
                this.doSendRefundMsg(refund.getMemberId(), refund.getLoginCode(), refund.getId(), map, "退款成功的通知",
                        "尊敬的D2C会员，您的退款" + payMoney + "元，已经成功退回您的" + backPayType.getDisplay() + "，预计2-5个工作日到账，敬请查收", ip,
                        refund.getProductImg());
            }
            this.insertRefundLog(refund, payer, info, RefundLogType.completed);
            return result;
        } else {
            throw new BusinessException("退货或退款单不成功，状态改变或退款单已经锁定！");
        }
    }

    private void doSendRefundMsg(Long memberInfoId, String loginCode, Long buz, Map<String, String> emailC,
                                 String subject, String msg, String ip, String productPic) {
        String content = msg;
        PushBean pushBean = new PushBean(memberInfoId, content, 22);
        pushBean.setAppUrl("/member/refund/" + buz);
        MsgBean msgBean = new MsgBean(memberInfoId, 22, subject, content);
        msgBean.setAppUrl("/member/refund/" + buz);
        msgBean.setPic(productPic);
        SmsBean smsBean = new SmsBean(null, loginCode, SmsLogType.REMIND, content);
        msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
    }

    private void insertRefundLog(Refund refund, String operator, String info, RefundLogType type) {
        RefundLog refundLog = new RefundLog(refund.getId(), info, type);
        refundLog.setCreator(operator);
        refundLog.setCreateDate(new Date());
        refundLog.setOrderId(refund.getOrderId());
        refundLog.setOrderItemId(refund.getOrderItemId());
        refundLogService.insert(refundLog);
    }

}
