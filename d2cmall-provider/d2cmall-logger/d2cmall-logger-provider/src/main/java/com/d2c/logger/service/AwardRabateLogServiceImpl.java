package com.d2c.logger.service;

import com.d2c.logger.model.AwardRabateLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("awardRabateLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AwardRabateLogServiceImpl extends ListServiceImpl<AwardRabateLog> implements AwardRabateLogService {

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public AwardRabateLog insert(AwardRabateLog awardRabateLog) throws Exception {
        try {
            awardRabateLog = this.save(awardRabateLog);
        } catch (Exception e) {
            throw new Exception("该笔返利记录已经存在");
        }
        return awardRabateLog;
    }

    @Override
    public AwardRabateLog findByUniqueMark(String uniqueMark) {
        return this.findOneByFieldName("unique_mark", uniqueMark);
    }

}
