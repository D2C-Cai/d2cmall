package com.d2c.logger.dao;

import com.d2c.logger.model.UserOperateLog;
import com.d2c.logger.query.UserOperateLogSearcher;
import com.d2c.mybatis.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

public interface UserOperateLogMapper extends SuperMapper<UserOperateLog> {

    List<Map<String, Object>> count(UserOperateLogSearcher searcher);

}
