package com.d2c.logger.service;

import com.d2c.logger.model.DiscountSettingLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("discountSettingLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class DiscountSettingLogServiceImpl extends ListServiceImpl<DiscountSettingLog>
        implements DiscountSettingLogService {

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public DiscountSettingLog insert(DiscountSettingLog log) {
        return this.save(log);
    }

}
