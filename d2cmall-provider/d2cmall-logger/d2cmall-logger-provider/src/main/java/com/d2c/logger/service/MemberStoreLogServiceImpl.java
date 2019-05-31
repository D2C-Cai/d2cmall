package com.d2c.logger.service;

import com.d2c.logger.model.MemberStoreLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("memberStoreLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberStoreLogServiceImpl extends ListServiceImpl<MemberStoreLog> implements MemberStoreLogService {

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MemberStoreLog insert(MemberStoreLog memberStoreLog) {
        return this.save(memberStoreLog);
    }

    @Override
    public MemberStoreLog findByLoginCode(String loginCode) {
        return this.findOneByFieldName("login_code", loginCode);
    }

}
