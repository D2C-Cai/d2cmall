package com.d2c.logger.service;

import com.d2c.logger.dao.UpyunTaskMapper;
import com.d2c.logger.model.UpyunTask;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "upyunTaskService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class UpyunTaskServiceImpl extends ListServiceImpl<UpyunTask> implements UpyunTaskService {

    @Autowired
    private UpyunTaskMapper upyunTaskMapper;

    @Override
    public UpyunTask findByTaskIds(String taskIds) {
        return upyunTaskMapper.findByTaskIds(taskIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public UpyunTask insert(UpyunTask upyunTask) {
        UpyunTask old = upyunTaskMapper.findByTaskIds(upyunTask.getTaskIds());
        if (old == null) {
            return this.save(upyunTask);
        }
        old.setSourceId(upyunTask.getSourceId());
        old.setSourceType(upyunTask.getSourceType());
        this.updateNotNull(old);
        return old;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public UpyunTask callBackInsert(UpyunTask upyunTask) {
        UpyunTask old = upyunTaskMapper.findByTaskIds(upyunTask.getTaskIds());
        if (old != null) {
            old.setStatus(1);
            old.setVideo(upyunTask.getVideo());
            old.setPic(upyunTask.getPic());
            this.updateNotNull(old);
        } else {
            old = this.save(upyunTask);
        }
        return old;
    }

    @Override
    public List<String> findPicsByTaskIds(String[] taskIds) {
        return upyunTaskMapper.findPicsByTaskIds(taskIds);
    }

}
