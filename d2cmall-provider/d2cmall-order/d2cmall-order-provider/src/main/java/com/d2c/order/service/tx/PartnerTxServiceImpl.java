package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.WeixinPushService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.third.wechat.WeixinPushEntity;
import com.d2c.member.model.Account;
import com.d2c.member.model.Partner;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.PartnerInviteService;
import com.d2c.member.service.PartnerService;
import com.d2c.order.handle.PartnerRebateStepHandler;
import com.d2c.order.model.*;
import com.d2c.order.model.PartnerCash.PayType;
import com.d2c.order.model.PartnerItem.PartnerLogType;
import com.d2c.order.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(protocol = {"dubbo"})
public class PartnerTxServiceImpl implements PartnerTxService {

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerItemService partnerItemService;
    @Autowired
    private PartnerBillService partnerBillService;
    @Autowired
    private WeixinPushService weixinPushService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PartnerCashService partnerCashService;
    @Autowired
    private PartnerInviteService partnerInviteService;
    @Autowired
    private PartnerGiftService partnerGiftService;
    @Autowired
    private PartnerWithholdService partnerWithholdService;
    @Autowired
    private AccountItemService accountItemService;

    private void insertLog(PartnerLogType logType, BigDecimal amount, Long partnerId, Long sourceId, String sourceSn,
                           String operator) {
        PartnerItem partnerItem = new PartnerItem(logType);
        partnerItem.setAmount(amount);
        partnerItem.setPartnerId(partnerId);
        partnerItem.setSourceId(sourceId);
        partnerItem.setSourceSn(sourceSn);
        partnerItemService.insert(partnerItem);
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doOrderBill(Long partnerId, List<OrderItem> orderItems) {
        Partner partner = partnerService.findById(partnerId);
        if (partner != null && partner.checkAvailable()) {
            for (OrderItem orderItem : orderItems) {
                // 返利单处理
                PartnerBill partnerBill = new PartnerBill(orderItem);
                if (partnerBill.getPartnerRatio().compareTo(new BigDecimal(0)) == 0) {
                    // 返利比设置为0则不生成返利单
                    continue;
                }
                partnerBill.setBuyerCode(orderItem.getBuyerMemberName());
                partnerBillService.insert(partnerBill, partner);
            }
            // 分销开单时间
            return partnerService.updateConsumeDate(partner.getId(), partner.getStatus());
        }
        return 0;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doOrderGift(Long inviterId, Order order, List<OrderItem> orderItems) {
        Partner inviter = partnerService.findById(inviterId);
        if (inviter != null && inviter.checkAvailable()) {
            PartnerGift gift = new PartnerGift();
            gift.setOrderSn(order.getOrderSn());
            gift.setLoginCode(order.getLoginCode());
            gift.setProductId(orderItems.get(0).getProductId());
            gift.setProductName(orderItems.get(0).getProductName());
            gift.setProductPic(orderItems.get(0).getProductImg());
            int quantity = orderItems.get(0).getProductQuantity();
            switch (inviter.getLevel()) {
                case 0:
                    // 邀请人是AM
                    gift.setMasterId(inviter.getId());
                    gift.setMasterRebate(new BigDecimal(300 * quantity));
                    break;
                case 1:
                    // 邀请人是DM
                    if (inviter.getPrestore() > 0 && !inviter.getMemberId().equals(order.getMemberId())) {
                        // 会销DM，预存返还
                        partnerService.updatePrestore(inviter.getId(), -1 * quantity, "sys");
                        this.doGift(inviter.getId(), new BigDecimal(599 * quantity), "sys", order.getId(),
                                order.getOrderSn());
                        // 会销DM，返利金额减少
                        gift.setParentId(inviter.getId());
                        gift.setParentRebate(new BigDecimal(150 * quantity));
                    } else {
                        gift.setParentId(inviter.getId());
                        gift.setParentRebate(new BigDecimal(250 * quantity));
                    }
                    if (inviter.getMasterId() != null) {
                        Partner am = partnerService.findById(inviter.getMasterId());
                        if (am != null && am.checkAvailable()) {
                            gift.setMasterId(am.getId());
                            gift.setMasterRebate(new BigDecimal(50 * quantity));
                        }
                    }
                    break;
                case 2:
                    // 邀请人是买手
                    gift.setPartnerId(inviter.getId());
                    gift.setPartnerRebate(new BigDecimal(150 * quantity));
                    if (inviter.getParentId() != null) {
                        Partner parent = partnerService.findById(inviter.getParentId());
                        if (parent != null && parent.checkAvailable()) {
                            if (parent.getLevel() == 0) {
                                gift.setMasterId(parent.getId());
                                gift.setMasterRebate(new BigDecimal(150 * quantity));
                            } else if (parent.getLevel() == 1) {
                                gift.setParentId(parent.getId());
                                gift.setParentRebate(new BigDecimal(100 * quantity));
                                if (inviter.getMasterId() != null) {
                                    Partner am = partnerService.findById(inviter.getMasterId());
                                    if (am != null && am.checkAvailable()) {
                                        gift.setMasterId(am.getId());
                                        gift.setMasterRebate(new BigDecimal(50 * quantity));
                                    }
                                }
                            } else if (parent.getLevel() == 2) {
                                if (inviter.getMasterId() != null) {
                                    Partner am = partnerService.findById(inviter.getMasterId());
                                    if (am != null && am.checkAvailable()) {
                                        gift.setMasterId(am.getId());
                                        gift.setMasterRebate(new BigDecimal(150 * quantity));
                                    }
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
            // 礼包返利
            if (gift.getPartnerId() != null) {
                this.doGift(gift.getPartnerId(), gift.getPartnerRebate(), "sys", order.getId(), order.getOrderSn());
            }
            if (gift.getParentId() != null) {
                this.doGift(gift.getParentId(), gift.getParentRebate(), "sys", order.getId(), order.getOrderSn());
            }
            if (gift.getMasterId() != null) {
                this.doGift(gift.getMasterId(), gift.getMasterRebate(), "sys", order.getId(), order.getOrderSn());
            }
            partnerGiftService.insert(gift);
        }
        return 1;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doOrderPoint(Long inviterId, List<OrderItem> orderItems) {
        Partner inviter = partnerService.findById(inviterId);
        if (inviter != null && inviter.checkAvailable()) {
            int quantity = orderItems.get(0).getProductQuantity();
            List<Long> pointIds = new ArrayList<>();
            switch (inviter.getLevel()) {
                case 0:
                    // 邀请人是AM
                    pointIds.add(inviter.getId());
                    break;
                case 1:
                    // 邀请人是DM
                    pointIds = partnerService.findPointRelation(inviter.getPath());
                    break;
                case 2:
                    // 邀请人是买手
                    pointIds = partnerService.findPointRelation(inviter.getPath());
                    pointIds.add(inviter.getId());
                    break;
                default:
                    break;
            }
            // 积分赠送
            if (pointIds.size() > 0) {
                partnerService.updatePoint(pointIds, quantity);
            }
        }
        return 1;
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBillSuccess(Long id, String operator, boolean off, BigDecimal diffAmount, boolean own,
                             BigDecimal partnerAmount) {
        int success = partnerBillService.updateStatus(id, 8);
        if (success > 0) {
            PartnerBill partnerBill = partnerBillService.findById(id);
            Partner partner = partnerService.findById(partnerBill.getPartnerId());
            if (partner == null) {
                return 0;
            }
            Long partnerId = partnerBill.getPartnerId();
            Long parentId = partnerBill.getParentId();
            Long superId = partnerBill.getSuperId();
            Long masterId = partnerBill.getMasterId();
            Long topId = null;
            Long tempId = null;
            if (partner.getLevel() == 0) {
                tempId = partner.getParentId();
            } else {
                if (partnerBill.getMasterId() != null) {
                    Partner master = partnerService.findById(partnerBill.getMasterId());
                    if (master != null) {
                        tempId = master.getParentId();
                    }
                }
            }
            if (tempId != null) {
                Partner top = partnerService.findById(tempId);
                if (top != null && top.getLevel() == 0) {
                    topId = top.getId();
                }
            }
            BigDecimal rebate = partnerBill.getActualAmount().multiply(partnerBill.getPartnerRatio());
            BigDecimal diff = diffAmount.multiply(partnerBill.getPartnerRatio());
            if (off) {
                rebate = rebate.subtract(diff);
            }
            if (own) {
                rebate = partnerAmount;
            }
            final BigDecimal ZERO = new BigDecimal(0);
            PartnerRebateStepHandler handler = new PartnerRebateStepHandler(partner.getLevel());
            BigDecimal[] result = handler.formula(rebate, partnerId, parentId, superId, masterId, topId);
            // BUYER
            if (result[0].compareTo(ZERO) > 0 && !own) {
                partnerService.doRebate(partnerId, result[0], partnerBill.getActualAmount(), operator);
                this.insertLog(PartnerLogType.BILL, result[0].add(diff), partnerId, partnerBill.getId(),
                        partnerBill.getSn(), operator);
                if (off) {
                    this.insertLog(PartnerLogType.DIFF, diff, partnerId, partnerBill.getId(), partnerBill.getSn(),
                            operator);
                }
            }
            // DM
            if (result[1].compareTo(ZERO) > 0) {
                partnerService.doRebate(parentId, result[1], partnerBill.getActualAmount(), operator);
                this.insertLog(PartnerLogType.TEAM, result[1], parentId, partnerBill.getId(), partnerBill.getSn(),
                        operator);
            }
            // DM1
            if (result[2].compareTo(ZERO) > 0) {
                partnerService.doRebate(superId, result[2], partnerBill.getActualAmount(), operator);
                this.insertLog(PartnerLogType.TEAM, result[2], superId, partnerBill.getId(), partnerBill.getSn(),
                        operator);
            }
            // AM
            if (result[3].compareTo(ZERO) > 0) {
                partnerService.doRebate(masterId, result[3], partnerBill.getActualAmount(), operator);
                this.insertLog(PartnerLogType.TEAM, result[3], masterId, partnerBill.getId(), partnerBill.getSn(),
                        operator);
            }
            // AM1
            if (result[4].compareTo(ZERO) > 0) {
                partnerService.doRebate(topId, result[4], partnerBill.getActualAmount(), operator);
                this.insertLog(PartnerLogType.TEAM, result[4], topId, partnerBill.getId(), partnerBill.getSn(),
                        operator);
            }
            if (!partnerBill.getBuyerCode().equals(partnerBill.getPartnerCode())) {
                BigDecimal x = result[0].add(diff).setScale(2, BigDecimal.ROUND_HALF_UP);
                WeixinPushEntity msgObj = new WeixinPushEntity(partner.getOpenId(), "您有一条返利订单已完成",
                        partnerBill.getOrderSn(), new Date(), "返利金额：" + x + "元", "/pages/member/bill/list");
                weixinPushService.send(msgObj);
                PushBean pushBean = new PushBean(partner.getMemberId(), "您有一条返利订单已完成，收益" + x + "元~", 82);
                pushBean.setAppUrl("/to/partner/sales");
                MsgBean msgBean = new MsgBean(partner.getMemberId(), 82, "返利单", "您有一条返利订单已完成，收益" + x + "元~");
                msgBean.setAppUrl("/to/partner/sales");
                msgUniteService.sendPush(pushBean, msgBean);
            }
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PartnerCash doApplyCash(PartnerCash partnerCash) {
        partnerCash = partnerCashService.insert(partnerCash);
        partnerService.doApplyCash(partnerCash.getPartnerId(), partnerCash.getApplyAmount(), 1);
        return partnerCash;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRefuseCash(Long id, String refuseReason, String refuseMan, String confirmOperateMan) {
        int success = partnerCashService.doRefuse(id, refuseReason, refuseMan, confirmOperateMan);
        if (success > 0) {
            PartnerCash partnerCash = partnerCashService.findById(id);
            partnerService.doApplyCash(partnerCash.getPartnerId(), partnerCash.getApplyAmount(), -1);
            Partner partner = partnerService.findById(partnerCash.getPartnerId());
            BigDecimal x = partnerCash.getApplyAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
            WeixinPushEntity msgObj = new WeixinPushEntity(partner.getOpenId(),
                    "提现单号:" + partnerCash.getSn() + " 审核不通过", partnerCash.getCreateDate(), x, "提现失败",
                    "失败原因：" + refuseReason, "/pages/member/withdrawal/list");
            weixinPushService.send(msgObj);
            PushBean pushBean = new PushBean(partner.getMemberId(), "您申请的提现审核不通过，申请金额：¥ " + x + "，原因：" + refuseReason,
                    81);
            pushBean.setAppUrl("/to/partner/cash");
            MsgBean msgBean = new MsgBean(partner.getMemberId(), 81, "提现失败",
                    "您申请的提现审核不通过，申请金额：¥ " + x + "，原因：" + refuseReason);
            msgBean.setAppUrl("/to/partner/cash");
            JSONObject other = new JSONObject();
            other.put("success", -1);
            msgBean.setOther(other.toJSONString());
            msgUniteService.sendPush(pushBean, msgBean);
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPaymentCash(Long id, String paySn, String payMan, Date payDate, String operator) {
        PartnerCash partnerCash = partnerCashService.findById(id);
        int success = partnerCashService.doPay(id, paySn, partnerCash.getApplyAmount(), payMan, payDate);
        if (success > 0) {
            partnerService.doWithdCash(partnerCash.getPartnerId(), partnerCash.getApplyAmount(),
                    partnerCash.getApplyAmount(), operator);
            if (partnerCash.getPayType().equals(PayType.wallet.name())) {
                Account account = accountService.findByMemberId(partnerCash.getMemberId());
                AccountItem accountItem = new AccountItem(partnerCash, account.getId());
                accountItem = accountItemService.insert(accountItem);
                accountService.doUpdateAmount(account.getId(), accountItem.getFactAmount(),
                        accountItem.getFactGiftAmount(), null, null);
            }
            if (partnerCash.getId() != null) {
                PartnerItem partnerItem = new PartnerItem(PartnerLogType.CASH);
                partnerItem.setAmount(partnerCash.getApplyAmount());
                partnerItem.setPartnerId(partnerCash.getPartnerId());
                partnerItem.setSourceId(partnerCash.getId());
                partnerItem.setSourceSn(partnerCash.getSn());
                partnerItem.setCreator(operator);
                partnerItemService.insert(partnerItem);
                Partner partner = partnerService.findById(partnerCash.getPartnerId());
                BigDecimal x = partnerCash.getApplyAmount().setScale(2, BigDecimal.ROUND_HALF_UP);
                WeixinPushEntity msgObj = new WeixinPushEntity(partner.getOpenId(),
                        "提现单号:" + partnerCash.getSn() + " 已提现成功", payDate, x, "提现成功", "支付流水号：" + partnerCash.getPaySn(),
                        "/pages/member/withdrawal/list");
                weixinPushService.send(msgObj);
                PushBean pushBean = new PushBean(partner.getMemberId(), "您的提现已支付，提现金额 ¥ " + x + "，请注意查收~", 81);
                pushBean.setAppUrl("/to/partner/cash");
                MsgBean msgBean = new MsgBean(partner.getMemberId(), 81, "提现成功", "您的提现已支付，提现金额 ¥ " + x + "，请注意查收~");
                msgBean.setAppUrl("/to/partner/cash");
                JSONObject other = new JSONObject();
                other.put("success", 1);
                msgBean.setOther(other.toJSONString());
                msgUniteService.sendMsg(null, pushBean, msgBean, null);
            }
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doAward(Long partnerId, Long inviteId, String toLoginCode, BigDecimal amount, String operator) {
        int success = partnerService.doAward(partnerId, amount, operator);
        if (success > 0) {
            partnerInviteService.doAward(inviteId);
            PartnerItem partnerItem = new PartnerItem(PartnerLogType.OTHER);
            partnerItem.setAmount(amount);
            partnerItem.setPartnerId(partnerId);
            partnerItem.setSourceId(inviteId);
            partnerItem.setSourceSn(toLoginCode);
            partnerItem.setCreator(operator);
            partnerItemService.insert(partnerItem);
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doGift(Long partnerId, BigDecimal amount, String operator, Long orderId, String orderSn) {
        int success = partnerService.doGift(partnerId, amount, operator);
        if (success > 0) {
            PartnerItem partnerItem = new PartnerItem(PartnerLogType.GIFT);
            partnerItem.setAmount(amount);
            partnerItem.setPartnerId(partnerId);
            partnerItem.setSourceId(orderId);
            partnerItem.setSourceSn(orderSn);
            partnerItem.setCreator(operator);
            partnerItemService.insert(partnerItem);
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRebate(Long id, BigDecimal amount, BigDecimal actualAmount, String operator, Long billId,
                        String billSn, PartnerLogType logType) {
        int success = partnerService.doRebate(id, amount, actualAmount, operator);
        if (success > 0) {
            PartnerItem partnerItem = new PartnerItem(logType);
            partnerItem.setAmount(amount);
            partnerItem.setPartnerId(id);
            partnerItem.setSourceId(billId);
            partnerItem.setSourceSn(billSn);
            partnerItemService.insert(partnerItem);
        }
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public PartnerWithhold doWithhold(PartnerWithhold partnerWithhold, PartnerLogType type) {
        partnerWithhold = partnerWithholdService.insert(partnerWithhold);
        if (partnerWithhold.getId() != null) {
            PartnerItem partnerItem = new PartnerItem(type);
            partnerItem.setAmount(partnerWithhold.getAmount());
            partnerItem.setPartnerId(partnerWithhold.getPartnerId());
            partnerItem.setSourceId(partnerWithhold.getId());
            partnerItem.setSourceSn(partnerWithhold.getSn());
            partnerItem.setCreator(partnerWithhold.getCreator());
            partnerItemService.insert(partnerItem);
            partnerService.doWithhold(partnerWithhold.getPartnerId(), partnerWithhold.getAmount(),
                    partnerWithhold.getCreator(), type.getDisplay());
        }
        return partnerWithhold;
    }

}
