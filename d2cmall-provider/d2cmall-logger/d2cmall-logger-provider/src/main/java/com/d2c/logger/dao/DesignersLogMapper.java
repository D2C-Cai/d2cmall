package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.DesignersLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DesignersLogMapper extends SuperMapper<DesignersLog> {

    List<DesignersLog> findByDesignersId(@Param("designersId") Long designersId, @Param("page") PageModel page);

    int countByDesignersId(@Param("designersId") Long designersId);

}
