package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.member.service.AccountService;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.Recharge;
import com.d2c.order.service.AccountItemService;
import com.d2c.order.service.RechargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service(protocol = {"dubbo"})
public class RechargeTxServiceImpl implements RechargeTxService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountItemService accountItemService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doSuccessRecharge(Long rechargeId, String submitor, String paySn) {
        int result = rechargeService.doSubmit(rechargeId, submitor, paySn);
        if (result > 0) {
            // 成功提交
            Recharge recharge = rechargeService.findById(rechargeId);
            accountItemService.doConfirm(recharge.getBillId(), recharge.getSubmitor());
            AccountItem accountItem = accountItemService.findById(recharge.getBillId());
            accountService.doUpdateAmount(accountItem.getSelfAccountId(), accountItem.getFactAmount(),
                    accountItem.getFactGiftAmount(), accountItem.getFactLimitGiftAmount(), accountItem.getLimitDate());
            String subject = "钱包充值到账通知";
            String content = "亲爱的用户，您已成功充值" + recharge.getTotalAmount() + "元，查看钱包余额，请到我的钱包中查看";
            PushBean pushBean = new PushBean(recharge.getMemberId(), content, 33);
            pushBean.setAppUrl("/member/account/info");
            MsgBean msgBean = new MsgBean(recharge.getMemberId(), 33, subject, content);
            msgBean.setAppUrl("/member/account/info");
            msgUniteService.sendPush(pushBean, msgBean);
        } else {
            throw new BusinessException("充值发生异常，充值不成功！");
        }
        return result;
    }

    @Override
    @TxTransaction(isStart = false)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doPaySuccess(Long paymentId, String paySn, Integer paymentType, String orderSn, BigDecimal payedAmount) {
        Recharge recharge = rechargeService.findBySn(orderSn);
        if (recharge == null) {
            logger.error("[Recharge] 充值单：" + orderSn + "不存在！");
            return 0;
        }
        if (recharge.getStatus() == 1) {
            logger.error("[Recharge] 充值单：" + orderSn + "已经支付成功了！");
            return 0;
        }
        int success = 0;
        if (recharge.getRechargeAmount().intValue() == payedAmount.intValue()) {
            success = this.doSuccessRecharge(recharge.getId(), "sys", paySn);
            if (success > 0) {
                accountItemService.updateTransactionInfo(recharge.getBillId(), recharge.getId(),
                        "充值信息：" + recharge.getPaySn());
            }
        }
        return success;
    }

}
