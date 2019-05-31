package com.d2c.member.dao;

import com.d2c.member.model.MemberTask;
import com.d2c.mybatis.mapper.SuperMapper;

import java.util.List;

public interface MemberTaskMapper extends SuperMapper<MemberTask> {

    public List<MemberTask> findTaskList();

    public void updateExecStart();

    public void updateExecEnd();

}