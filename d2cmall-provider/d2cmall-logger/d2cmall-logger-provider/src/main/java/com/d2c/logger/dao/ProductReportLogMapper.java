package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.ProductReportLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductReportLogMapper extends SuperMapper<ProductReportLog> {

    List<ProductReportLog> findByReportId(@Param("reportId") Long reportId, @Param("page") PageModel page);

    int countByReportId(@Param("reportId") Long reportId);

}
