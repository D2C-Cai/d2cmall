package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.dao.AccountLogMapper;
import com.d2c.logger.model.AccountLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("accountLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AccountLogServiceImpl extends ListServiceImpl<AccountLog> implements AccountLogService {

    @Autowired
    private AccountLogMapper accountLogMapper;

    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public AccountLog insert(AccountLog accountLog) {
        return save(accountLog);
    }

    @Override
    public List<AccountLog> findByAccountId(Long accountId, PageModel page) {
        return accountLogMapper.findByAccountId(accountId, page);
    }

}
