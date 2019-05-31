package com.d2c.logger.service;

import com.d2c.logger.model.MemberStoreLog;

public interface MemberStoreLogService {

    MemberStoreLog findByLoginCode(String loginCode);

    MemberStoreLog insert(MemberStoreLog memberStoreLog);

}
