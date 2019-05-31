package com.d2c.member.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.logger.model.AccountLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.AccountLogService;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.dao.AccountMapper;
import com.d2c.member.model.Account;
import com.d2c.member.query.AccountSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("accountService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AccountServiceImpl extends ListServiceImpl<Account> implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;

    private AccountLog buildAccountLog(Account account) {
        AccountLog log = new AccountLog();
        BeanUtils.copyProperties(account, log, new String[]{"id"});
        log.setAccountId(account.getId());
        return log;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Account insert(Account account) {
        int result = accountMapper.insert(account);
        if (result > 0) {
            account.setMemo("创建账户");
            accountLogService.insert(buildAccountLog(account));
            return account;
        } else {
            throw new BusinessException("创建账户不成功");
        }
    }

    @Override
    public Account findById(Long id) {
        Account account = accountMapper.selectByPrimaryKey(id);
        return account;
    }

    @Override
    public Account findByMemberId(Long id) {
        return accountMapper.findByMemberId(id);
    }

    @Override
    public Account findByAccount(String account) {
        return accountMapper.findByAccount(account);
    }

    @Override
    public PageResult<Account> findBySearch(AccountSearcher searcher, PageModel page) {
        PageResult<Account> pager = new PageResult<>(page);
        int totalCount = accountMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<Account> values = accountMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(values);
        }
        return pager;
    }

    @Override
    public int countBySearch(AccountSearcher searcher) {
        return accountMapper.countBySearch(searcher);
    }

    @Override
    public Map<String, BigDecimal> countAccountAmount() {
        return accountMapper.countAccountAmount();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String checkPassword(Long memberId, String password) {
        Account account = findByMemberId(memberId);
        if (account == null) {
            return "账号不存在";
        }
        if (account.getPassword() == null) {
            return "账号密码未设置";
        }
        if (account.getLocked() == true) {
            return "账号已被锁定，请在2小时后重试";
        }
        Integer count = redisHandler.get("valid_account_times_" + account.getAccount());
        int errorTimes = 1;
        if (count != null) {
            errorTimes = errorTimes + count;
        }
        if (account.getPassword().equalsIgnoreCase(password) && errorTimes <= 5) {
            redisHandler.delete("valid_account_times_" + account.getAccount());
            return "1";
        } else {
            redisHandler.setInHours("valid_account_times_" + account.getAccount(), errorTimes, 2);
            if (errorTimes <= 4) {
                if (errorTimes == 3) {
                    return "您还可以输入2次，2次错误后您的密码将被锁定2小时";
                }
                return "输入密码有误，您还可以输入" + (5 - errorTimes) + "次";
            } else if (errorTimes > 4) {
                this.doLockAccount(account.getId(), true);
                return "输入密码错误已经5次，请点击忘记密码或2小时后重试";
            }
        }
        return "账号不存在";
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSetPassword(Long accountId, String mobile, String nationCode, String newPassword2) {
        Account account = accountMapper.selectByPrimaryKey(accountId);
        if (account == null) {
            throw new BusinessException("账户不存在");
        }
        if (account.getMobile() == null) {
            accountMapper.updateMobile(accountId, mobile, nationCode);
            account.setMemo("修改支付手机");
            accountLogService.insert(buildAccountLog(account));
        }
        // String newPassword = DigestUtils.md5Hex(newPassword2);
        return doChangePassword(account.getId(), null, newPassword2);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doChangePassword(Long accountId, String oldPassword, String newPassword) {
        Account account = accountMapper.selectByPrimaryKey(accountId);
        if (account == null)
            throw new BusinessException("账户不存在");
        account.setMemo("修改支付密码");
        int success = accountMapper.changePassword(accountId, oldPassword, newPassword);
        if (success > 0) {
            redisHandler.delete("valid_account_times_" + account.getAccount());
            accountMapper.doLockAccount(accountId, false);
            accountLogService.insert(buildAccountLog(account));
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doUpdateAmount(Long accountId, BigDecimal cashAmount, BigDecimal giftAmount, BigDecimal limitGiftAmount,
                              Date limitDate) {
        if (giftAmount == null) {
            giftAmount = new BigDecimal("0");
        }
        Account account = accountMapper.selectByPrimaryKey(accountId);
        int success = accountMapper.doInOutAmount(accountId, cashAmount, giftAmount, limitGiftAmount, limitDate);
        if (success > 0) {
            String info = "";
            String title = "";
            BigDecimal x = account.getAvailableTotalAmount().add(cashAmount).add(giftAmount).setScale(2,
                    BigDecimal.ROUND_HALF_UP);
            if (cashAmount.add(giftAmount).compareTo(new BigDecimal(0)) <= 0) {
                info = "尊敬的用户，您的钱包于" + DateUtil.minute2str(new Date()) + "扣款"
                        + (cashAmount.add(giftAmount).negate()).setScale(2, BigDecimal.ROUND_HALF_UP) + "元，账户可用余额为" + x
                        + "元 ，感谢您的信任";
                title = "钱包消费提醒";
            } else {
                info = "尊敬的用户，您钱包账户于" + DateUtil.minute2str(new Date()) + "已成功到账"
                        + cashAmount.add(giftAmount).setScale(2, BigDecimal.ROUND_HALF_UP) + "元，账户可用余额为" + x
                        + "元 ，感谢您的信任";
                title = "钱包充值";
            }
            // 发送消息
            String subject = title;
            String content = info;
            PushBean pushBean = new PushBean(account.getMemberId(), content, 33);
            pushBean.setAppUrl("/member/account/info");
            MsgBean msgBean = new MsgBean(account.getMemberId(), 33, subject, content);
            msgBean.setAppUrl("/member/account/info");
            SmsBean smsBean = new SmsBean(account.getNationCode(), account.getAccount(), SmsLogType.MEMBERMOBILE,
                    content);
            msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
            account.setMemo("调整账户金额之前日志");
            accountLogService.insert(buildAccountLog(account));
        } else {
            throw new BusinessException("交易不成功");
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doLockAccount(Long accountId, Boolean locked) {
        int result = accountMapper.doLockAccount(accountId, locked);
        if (result > 0 && locked) {
            this.unlockAccountMQ(accountId);
        }
        return result;
    }

    private void unlockAccountMQ(Long accountId) {
        long interval = 2 * 60 * 60;
        Map<String, Object> map = new HashMap<>();
        map.put("accountId", accountId);
        MqEnum.UNLOCK_ACCOUNT.send(map, interval);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doUpdateRedAmount(Long accountId, BigDecimal redAmount, Date redDate) {
        int result = accountMapper.doUpdateRedAmount(accountId, redAmount, redDate);
        if (result <= 0) {
            throw new BusinessException("红包使用不成功");
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCleanLimitAmount(Long accountId) {
        int result = accountMapper.doCleanLimitAmount(accountId);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doChangeMobile(Long memberId, String newMobile, String nationCode) {
        return accountMapper.doChangeMobile(memberId, newMobile, nationCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doDelete(String loginCode) {
        return accountMapper.doDelete(loginCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, int status) {
        return accountMapper.updateStatus(id, status);
    }

}
