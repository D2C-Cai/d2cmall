package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.model.Account;
import com.d2c.member.service.AccountService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.AccountItemMapper;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.Recharge;
import com.d2c.order.model.WithdrawCash;
import com.d2c.order.query.AccountItemSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("accountItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AccountItemServiceImpl extends ListServiceImpl<AccountItem> implements AccountItemService {

    @Autowired
    private AccountItemMapper accountItemMapper;
    @Autowired
    private AccountService accountService;

    @Override
    public AccountItem findById(Long id) {
        return accountItemMapper.selectByPrimaryKey(id);
    }

    @Override
    @TxTransaction
    public AccountItem findBySn(String sn) {
        return accountItemMapper.findBySn(sn);
    }

    @Override
    public int countBySearch(AccountItemSearcher searcher) {
        return accountItemMapper.countBySearch(searcher);
    }

    @Override
    public List<AccountItem> findList(AccountItemSearcher searcher, PageModel page) {
        return accountItemMapper.findBySearch(searcher, page);
    }

    @Override
    public PageResult<AccountItem> findBySearch(AccountItemSearcher searcher, PageModel page) {
        PageResult<AccountItem> pager = new PageResult<>(page);
        int totalCount = accountItemMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<AccountItem> values = accountItemMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(values);
        }
        return pager;
    }

    @Override
    public AccountItem findByTransactionSn(String transactionSn, String payType) {
        return accountItemMapper.findByTransactionSn(transactionSn, payType);
    }

    @Override
    public List<Map<String, Object>> findWalletAmount(Date calculateDate) {
        return accountItemMapper.findWalletAmount(calculateDate);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateTransactionInfo(Long billId, Long transactionId, String transactionInfo) {
        return accountItemMapper.updateTransactionInfo(billId, transactionId, transactionInfo);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AccountItem insert(AccountItem item) {
        Account account = this.checkAccount(item.getSelfAccountId());
        if (account.getAvailableCashAmount().add(item.getFactAmount()).compareTo(new BigDecimal(0)) < 0) {
            throw new BusinessException("钱包余额不足！");
        }
        item.setSelfAccountSn(account.getAccount());
        item = this.save(item);
        return item;
    }

    private Account checkAccount(Long accountId) {
        Account account = accountService.findById(accountId);
        if (account == null) {
            throw new BusinessException("账户不存在！");
        }
        return account;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doConfirm(Long payId, String man) {
        return accountItemMapper.doConfirm(payId, man);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doCancel(Long itemId, String man, String closeInfo) {
        AccountItem item = accountItemMapper.selectByPrimaryKey(itemId);
        this.checkAccount(item.getSelfAccountId());
        int result = accountItemMapper.doCancel(itemId, man, closeInfo);
        return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AccountItem doRecharge(Recharge recharge) {
        this.checkAccount(recharge.getAccountId());
        AccountItem item = new AccountItem(recharge);
        item = this.insert(item);
        if (item.getId() <= 0) {
            throw new BusinessException("充值发生异常，充值不成功！");
        }
        return item;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AccountItem doWithdrawCash(WithdrawCash cash) {
        Account account = this.checkAccount(cash.getAccountId());
        AccountItem item = new AccountItem(cash);
        if (account.getAvailableCashAmount().compareTo(cash.getCashAmount()) < 0) {
            throw new BusinessException("提现余额不足，提现不成功！");
        }
        if (account.getAvailableGiftAmount().compareTo(new BigDecimal(0)) > 0) {
            BigDecimal giftAmount = this.rateCurrency(account.getAvailableGiftAmount(),
                    account.getAvailableCashAmount(), cash.getCashAmount());
            item.setGiftAmount(giftAmount.negate());
        }
        item.setAmount(cash.getCashAmount().negate());
        item = this.insert(item);
        if (item.getId() <= 0) {
            throw new BusinessException("提现发生异常，提现不成功！");
        }
        return item;
    }

    private BigDecimal rateCurrency(BigDecimal availableAmount, BigDecimal totalAmount, BigDecimal outAmount) {
        BigDecimal cashAmount = availableAmount.multiply(outAmount).multiply(new BigDecimal("10000"))
                .divide(totalAmount.multiply(new BigDecimal("100")), 0, RoundingMode.HALF_UP)
                .divide(new BigDecimal("100"));
        return cashAmount;
    }

    @Override
    public BigDecimal sumTotalAmount(Long sourceId) {
        return accountItemMapper.sumTotalAmount(sourceId);
    }

    @Override
    public BigDecimal sumTotalGiftAmount(Long sourceId) {
        return accountItemMapper.sumTotalGiftAmount(sourceId);
    }

}
