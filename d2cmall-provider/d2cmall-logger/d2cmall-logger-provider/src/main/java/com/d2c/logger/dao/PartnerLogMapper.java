package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.PartnerLog;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerLogMapper extends SuperMapper<PartnerLog> {

    List<PartnerLog> findByPartnerId(@Param("partnerId") Long partnerId, @Param("page") PageModel page);

    int countByPartnerId(Long partnerId);

}
