package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.BrandLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandLogMapper extends SuperMapper<BrandLog> {

    List<BrandLog> findByDesignerId(@Param("designerId") Long designerId, @Param("page") PageModel page);

    int countByDesignerId(Long designerId);

}
