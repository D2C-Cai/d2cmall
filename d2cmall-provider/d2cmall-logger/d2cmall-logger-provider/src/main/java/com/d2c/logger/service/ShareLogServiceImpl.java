package com.d2c.logger.service;

import com.d2c.logger.model.ShareLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("shareLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ShareLogServiceImpl extends ListServiceImpl<ShareLog> implements ShareLogService {

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ShareLog insert(ShareLog shareLog) {
        return save(shareLog);
    }

}
