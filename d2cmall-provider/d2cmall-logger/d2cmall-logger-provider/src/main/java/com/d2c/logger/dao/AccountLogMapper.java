package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.AccountLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountLogMapper extends SuperMapper<AccountLog> {

    List<AccountLog> findByAccountId(@Param("accountId") Long accountId, @Param("page") PageModel page);

}
