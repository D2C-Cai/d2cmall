package com.d2c.order.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.HolidayMapper;
import com.d2c.order.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("holidayService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class HolidayServiceImpl extends ListServiceImpl<Holiday> implements HolidayService {

    @Autowired
    private HolidayMapper holidayMapper;

    @Override
    public List<Date> findList(Date currenDate) {
        return holidayMapper.findList(currenDate);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int replaceInto(Date holiday) {
        return holidayMapper.replaceInto(holiday);
    }

}
