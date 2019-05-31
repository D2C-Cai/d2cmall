package com.d2c.member.mongo.services;

import com.d2c.member.mongo.model.CollectCardTaskDO;

public interface CollectCardTaskService {

    CollectCardTaskDO insert(CollectCardTaskDO collectCardTaskDo);

    CollectCardTaskDO findMine(Long memberId, String type, Boolean today);

    Boolean doReduceByMemberId(Long memberId);

}
