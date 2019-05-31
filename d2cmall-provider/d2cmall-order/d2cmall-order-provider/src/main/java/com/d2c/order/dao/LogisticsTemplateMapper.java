package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.dto.LogisticsTemplateDto;
import com.d2c.order.model.LogisticsTemplate;
import com.d2c.order.query.LogisticsTemplateSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogisticsTemplateMapper extends SuperMapper<LogisticsTemplate> {

    int countBySearcher(@Param("searcher") LogisticsTemplateSearcher searcher);

    List<LogisticsTemplate> findBySearcher(@Param("searcher") LogisticsTemplateSearcher searcher,
                                           @Param("page") PageModel page);

    int updateStatus(@Param("id") Long templateId, @Param("status") Integer status, @Param("admin") String admin);

    int updateTemplate(@Param("template") LogisticsTemplateDto template);

    LogisticsTemplate findByDeliveryCode(@Param("deliveryCode") String deliveryCode);

}