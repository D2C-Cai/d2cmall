package com.d2c.logger.dao;

import com.d2c.logger.model.EmailLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

public interface EmailLogMapper extends SuperMapper<EmailLog> {

    int updateSend(Long id);

    EmailLog findBySourceIdAndType(@Param("sourceId") Long sourceId, @Param("type") String type);

}
