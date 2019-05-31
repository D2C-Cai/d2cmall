package com.d2c.order.service;

import java.util.Date;
import java.util.List;

public interface HolidayService {

    int replaceInto(Date date);

    List<Date> findList(Date currenDate);

}
