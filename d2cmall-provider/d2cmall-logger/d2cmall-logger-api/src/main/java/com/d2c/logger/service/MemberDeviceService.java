package com.d2c.logger.service;

import com.d2c.logger.model.MemberDevice;

public interface MemberDeviceService {

    int doInsert(MemberDevice memberDevice);

    MemberDevice findByMemberId(Long memberId);

}
