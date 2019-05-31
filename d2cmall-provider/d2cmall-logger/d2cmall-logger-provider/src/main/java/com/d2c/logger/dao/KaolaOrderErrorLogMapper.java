package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.KaolaOrderErrorLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KaolaOrderErrorLogMapper extends SuperMapper<KaolaOrderErrorLog> {

    List<KaolaOrderErrorLog> findBySearcher(@Param("pager") PageModel pager);

    int countBySearcher();

}
