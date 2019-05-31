package com.d2c.member.mongo.services;

import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.mongo.model.MemberTaskExecDO;
import com.d2c.member.view.MemberTaskVO;

import java.util.List;

public interface MemberTaskExecService {

    MemberTaskExecDO taskDone(Long memberId, MemberTaskEnum taskEnum);

    MemberTaskExecDO taskDone(Long memberId, String codeType, String subCode);

    String addTaskAward(Long memberId, String code);

    long refreshOnDayTask();

    List<MemberTaskVO> findTaskList(Long memberId);

    public long countAwardState(Long memberId);

}
