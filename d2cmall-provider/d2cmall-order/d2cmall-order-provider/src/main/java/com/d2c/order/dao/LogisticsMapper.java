package com.d2c.order.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Logistics;
import com.d2c.order.model.LogisticsCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogisticsMapper extends SuperMapper<Logistics> {

    int update(Logistics logostics);

    Logistics findBySnAndCom(@Param("sn") String sn, @Param("com") String com, @Param("type") String type);

    LogisticsCompany findCompanyByName(@Param("name") String name);

    List<Logistics> findAllBySn(@Param("sn") String sn, @Param("com") String com);

}
