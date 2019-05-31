package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.RecommendLog;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.RecommendLogService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.model.Account;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.support.OrderInfo;
import com.d2c.member.support.RecommendInfo;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.Payment;
import com.d2c.order.model.RedPacketsItem;
import com.d2c.order.service.AccountItemService;
import com.d2c.order.service.PaymentService;
import com.d2c.order.service.RedPacketsItemService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service(protocol = {"dubbo"})
public class AccountTxServiceImpl implements AccountTxService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountItemService accountItemService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RecommendLogService recommendLogService;
    @Autowired
    private RedPacketsItemService redPacketsItemService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private MsgUniteService msgUniteService;

    private BigDecimal rateCurrency(BigDecimal availableAmount, BigDecimal totalAmount, BigDecimal outAmount) {
        BigDecimal cashAmount = availableAmount.multiply(outAmount).multiply(new BigDecimal("10000"))
                .divide(totalAmount.multiply(new BigDecimal("100")), 0, RoundingMode.HALF_UP)
                .divide(new BigDecimal("100"));
        return cashAmount;
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AccountItem doConsume(OrderInfo orderInfo) {
        Account account = accountService.findByMemberId(orderInfo.getMemberId());
        if (account == null) {
            throw new BusinessException("账户不存在！");
        }
        orderInfo.setAccountId(account.getId());
        orderInfo.setMemberId(account.getMemberId());
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal out = orderInfo.getBillTotalFee();
        if (out.compareTo(zero) < 0) {
            throw new BusinessException("支付金额不能是负数！");
        }
        if (out.compareTo(account.getAvailableTotalAmount()) > 0) {
            throw new BusinessException("账户余额不足，消费不成功！");
        }
        BigDecimal cashAmount = this.rateCurrency(account.getAvailableCashAmount(), account.getAvailableTotalAmount(),
                out);
        BigDecimal giftAmount = out.subtract(cashAmount);
        orderInfo.setCashAmount(cashAmount);
        orderInfo.setGiftAmount(giftAmount);
        // 有期限的赠送金额
        if (account.getLimitDate() != null) {
            orderInfo.setLimited(1);
            orderInfo.setLimitGiftAmount(giftAmount);
        }
        AccountItem accountItem = new AccountItem(orderInfo);
        accountItem = accountItemService.insert(accountItem);
        accountService.doUpdateAmount(account.getId(), accountItem.getFactAmount(), accountItem.getFactGiftAmount(),
                accountItem.getFactLimitGiftAmount(), accountItem.getLimitDate());
        return accountItem;
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AccountItem doRefund(OrderInfo orderInfo) {
        Account account = accountService.findByMemberId(orderInfo.getMemberId());
        if (account == null) {
            throw new BusinessException("账户不存在！");
        }
        orderInfo.setAccountId(account.getId());
        orderInfo.setMemberId(account.getMemberId());
        BigDecimal zero = new BigDecimal("0.00");
        BigDecimal in = orderInfo.getBillTotalFee();
        if (in.compareTo(zero) < 0) {
            throw new BusinessException("支付金额不能是负数！");
        }
        Payment payment = paymentService.findById(orderInfo.getPaymentId());
        AccountItem payItem = accountItemService.findBySn(payment.getAlipaySn());
        if (payItem == null || payItem.getStatus() != 1) {
            throw new BusinessException("非钱包支付，退款不成功，请联系D2C客服！");
        }
        BigDecimal cashAmount = this.rateCurrency(payItem.getAmount(), payItem.getSubTotal(), in);
        BigDecimal giftAmount = in.subtract(cashAmount);
        orderInfo.setCashAmount(cashAmount.negate());
        orderInfo.setGiftAmount(giftAmount.negate());
        // 有期限的赠送金额，退款时不退
        if (payItem.getLimited() > 0) {
            orderInfo.setGiftAmount(new BigDecimal(0));
            orderInfo.setLimitGiftAmount(new BigDecimal(0));
        }
        AccountItem accountItem = new AccountItem(orderInfo);
        accountItem.setSourceId(payItem.getId());
        // 订单中往期退款总额，不能超过订单总额
        BigDecimal sumTotalAmount = accountItemService.sumTotalAmount(accountItem.getSourceId());
        BigDecimal sumTotalGiftAmount = accountItemService.sumTotalGiftAmount(accountItem.getSourceId());
        BigDecimal totalAmount = accountItem.getSubTotal().multiply(new BigDecimal(accountItem.getDirection()));
        BigDecimal backTotal = totalAmount.add(sumTotalAmount).add(sumTotalGiftAmount);
        if (payItem.getSubTotal().subtract(backTotal).compareTo(zero) < 0) {
            throw new BusinessException("退款金额大于收款金额，操作不成功！");
        }
        accountItem = accountItemService.insert(accountItem);
        accountService.doUpdateAmount(account.getId(), accountItem.getFactAmount(), accountItem.getFactGiftAmount(),
                accountItem.getFactLimitGiftAmount(), accountItem.getLimitDate());
        return accountItem;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doRecommend(MemberInfo memberInfo, Long recId, String rebates) {
        MemberInfo recomMemberInfo = memberInfoService.findById(recId);
        Account account = accountService.findByMemberId(recomMemberInfo.getId());
        BigDecimal recommendRebates = new BigDecimal(0);
        if (rebates != null) {
            if (new BigDecimal(rebates).compareTo(new BigDecimal(10)) <= 0) {
                recommendRebates = new BigDecimal(rebates);
            } else {
                recommendRebates = new BigDecimal(10);
            }
        }
        RecommendInfo recommendInfo = new RecommendInfo(BusinessTypeEnum.PROMOTION.toString(),
                PayTypeEnum.GIVE.toString());
        recommendInfo.setAccountId(account.getId());
        recommendInfo.setMemberId(account.getMemberId());
        recommendInfo.setRecomMemberInfoId(recomMemberInfo.getId());
        recommendInfo.setRecommendRebates(recommendRebates);
        AccountItem accountItem = new AccountItem(recommendInfo);
        int success = 0;
        if (accountItem.getGiftAmount().compareTo(new BigDecimal(0)) > 0) {
            accountItem = accountItemService.insert(accountItem);
            accountService.doUpdateAmount(account.getId(), accountItem.getFactAmount(), accountItem.getFactGiftAmount(),
                    null, null);
            memberInfoService.updateRecId(memberInfo.getId(), recId);
        }
        RecommendLog log = new RecommendLog(1, memberInfo.getId(), recId, memberInfo.getDisplayName(),
                accountItem.getGiftAmount(), new Date(), memberInfo.getHeadPic());
        recommendLogService.insert(log);
        return success;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCleanLimit(Account account) {
        int result = accountService.doCleanLimitAmount(account.getId());
        if (result > 0 && account.getLimitGiftAmount().compareTo(new BigDecimal(0)) != 0) {
            AccountItem accountItem = new AccountItem(account);
            accountItemService.insert(accountItem);
        }
        return result;
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public RedPacketsItem doFreezeRed(Long memberId, BigDecimal amount) {
        Account account = accountService.findByMemberId(memberId);
        accountService.doUpdateRedAmount(account.getId(), amount, null);
        RedPacketsItem redPacketsItem = new RedPacketsItem(account);
        redPacketsItem.setAmount(amount);
        return redPacketsItemService.insert(redPacketsItem);
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBackRed(Long id) {
        RedPacketsItem redPacketsItem = redPacketsItemService.findById(id);
        Account account = accountService.findByMemberId(redPacketsItem.getMemberId());
        accountService.doUpdateRedAmount(account.getId(), redPacketsItem.getAmount().multiply(new BigDecimal(-1)),
                null);
        return redPacketsItemService.updateStatus(id, -1);
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public RedPacketsItem doSuccessRed(Long memberId, String businessType, BigDecimal amount) {
        Account account = accountService.findByMemberId(memberId);
        accountService.doUpdateRedAmount(account.getId(), amount, DateUtil.getIntervalMonth(new Date(), 1));
        RedPacketsItem redPacketsItem = new RedPacketsItem(account, businessType);
        redPacketsItem.setAmount(amount);
        redPacketsItem = redPacketsItemService.insert(redPacketsItem);
        if (redPacketsItem.getId() != null) {
            String subject = "红包到账提醒";
            String content = "亲爱的用户，恭喜您获得D2C红包，金额为" + amount + "元，请在有效期" + "之前使用。";
            PushBean pushBean = new PushBean(memberId, content, 35);
            pushBean.setAppUrl("/check/redPacket");
            MsgBean msgBean = new MsgBean(memberId, 35, subject, content);
            msgBean.setAppUrl("/check/redPacket");
            msgUniteService.sendPush(pushBean, msgBean);
        }
        return redPacketsItem;
    }

}
