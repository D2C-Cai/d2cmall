package com.d2c.logger.dao;

import com.d2c.logger.model.BossDevice;
import com.d2c.mybatis.mapper.SuperMapper;

public interface BossDeviceMapper extends SuperMapper<BossDevice> {

    int doInsert(BossDevice bossDevice);

    BossDevice findByMemberId(Long memberId);

}