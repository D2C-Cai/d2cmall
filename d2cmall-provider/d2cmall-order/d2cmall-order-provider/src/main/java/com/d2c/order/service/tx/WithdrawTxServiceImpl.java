package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.model.Account;
import com.d2c.member.service.AccountService;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.WithdrawCash;
import com.d2c.order.service.AccountItemService;
import com.d2c.order.service.WithdrawCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(protocol = {"dubbo"})
public class WithdrawTxServiceImpl implements WithdrawTxService {

    @Autowired
    private WithdrawCashService withdrawCashService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountItemService accountItemService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSuccessWithdraw(WithdrawCash drawCash) {
        Account account = accountService.findById(drawCash.getAccountId());
        if (account == null) {
            throw new BusinessException("账户不存在！");
        }
        if (account.getCashAmount().compareTo(drawCash.getActualPay()) < 0) {
            throw new BusinessException("账户余额不足，提现不成功！");
        }
        int result = withdrawCashService.doSuccess(drawCash);
        if (result > 0) {
            accountItemService.doConfirm(drawCash.getBillId(), drawCash.getPayer());
            AccountItem accountItem = accountItemService.findById(drawCash.getBillId());
            accountService.doUpdateAmount(accountItem.getSelfAccountId(), accountItem.getFactAmount(),
                    accountItem.getFactGiftAmount(), null, null);
        } else {
            throw new BusinessException("提现发生异常，提现不成功！");
        }
        return result;
    }

}
