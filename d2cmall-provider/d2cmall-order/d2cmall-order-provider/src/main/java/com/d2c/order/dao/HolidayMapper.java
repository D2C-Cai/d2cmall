package com.d2c.order.dao;

import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.Holiday;

import java.util.Date;
import java.util.List;

public interface HolidayMapper extends SuperMapper<Holiday> {

    int replaceInto(Date holiday);

    List<Date> findList(Date date);

}
