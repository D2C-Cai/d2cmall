package com.d2c.member.mongo.services;

import com.d2c.member.mongo.dao.CollectCardTaskMongoDao;
import com.d2c.member.mongo.model.CollectCardTaskDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("collectCardTaskService")
public class CollectCardTaskServiceImpl implements CollectCardTaskService {

    @Autowired
    private CollectCardTaskMongoDao collectCardTaskMongoDao;

    @Override
    public CollectCardTaskDO insert(CollectCardTaskDO collectCardTaskDo) {
        if (collectCardTaskMongoDao.findMine(collectCardTaskDo.getMemberId(), collectCardTaskDo.getType(),
                true) == null) {
            collectCardTaskDo = collectCardTaskMongoDao.insert(collectCardTaskDo);
        }
        return collectCardTaskDo;
    }

    @Override
    public CollectCardTaskDO findMine(Long memberId, String type, Boolean today) {
        return collectCardTaskMongoDao.findMine(memberId, type, today);
    }

    @Override
    public Boolean doReduceByMemberId(Long memberId) {
        return collectCardTaskMongoDao.doReduceByMemberId(memberId);
    }

}
