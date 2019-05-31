package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.AccountLog;

import java.util.List;

public interface AccountLogService {

    /**
     * 添加一天账户日志记录
     *
     * @param accountLog
     * @return
     */
    AccountLog insert(AccountLog accountLog);

    List<AccountLog> findByAccountId(Long accountId, PageModel page);

}
