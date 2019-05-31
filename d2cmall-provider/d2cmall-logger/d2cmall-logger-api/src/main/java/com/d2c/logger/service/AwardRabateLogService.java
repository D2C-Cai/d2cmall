package com.d2c.logger.service;

import com.d2c.logger.model.AwardRabateLog;

public interface AwardRabateLogService {

    AwardRabateLog insert(AwardRabateLog awardRabateLog) throws Exception;

    AwardRabateLog findByUniqueMark(String uniqueMark);

}
