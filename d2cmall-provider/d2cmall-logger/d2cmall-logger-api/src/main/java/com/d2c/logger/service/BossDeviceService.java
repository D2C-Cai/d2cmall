package com.d2c.logger.service;

import com.d2c.logger.model.BossDevice;

public interface BossDeviceService {

    int doInsert(BossDevice bossDevice);

    BossDevice findByMemberId(Long memberId);

}
