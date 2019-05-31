package com.d2c.report.dao;

import com.d2c.mybatis.mapper.BaseMapper;
import com.d2c.report.mongo.dto.SaleStatDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface PartnerSaleMapper extends BaseMapper {

    int countInvite(@Param("partnerId") Long partnerId, @Param("level") Integer level, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    SaleStatDTO findSaleStat(@Param("fieldName") String fieldName, @Param("fieldValue") Object fieldValue,
                             @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}
