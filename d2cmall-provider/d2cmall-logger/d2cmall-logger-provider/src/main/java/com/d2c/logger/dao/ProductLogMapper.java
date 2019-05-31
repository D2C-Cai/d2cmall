package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.ProductLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductLogMapper extends SuperMapper<ProductLog> {

    List<ProductLog> findByProductId(@Param("productId") Long productId, @Param("page") PageModel page);

    int countByProductId(Long productId);

}
