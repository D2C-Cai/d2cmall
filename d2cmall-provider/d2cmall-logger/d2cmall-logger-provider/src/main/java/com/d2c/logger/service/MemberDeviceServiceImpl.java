package com.d2c.logger.service;

import com.d2c.logger.dao.MemberDeviceMapper;
import com.d2c.logger.model.MemberDevice;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberDeviceService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MemberDeviceServiceImpl extends ListServiceImpl<MemberDevice> implements MemberDeviceService {

    @Autowired
    public MemberDeviceMapper memberDeviceMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doInsert(MemberDevice memberDevice) {
        return memberDeviceMapper.doInsert(memberDevice);
    }

    @Override
    public MemberDevice findByMemberId(Long memberId) {
        return memberDeviceMapper.findByMemberId(memberId);
    }

}
