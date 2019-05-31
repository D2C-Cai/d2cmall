package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberTask;
import com.d2c.member.query.MemberTaskSearcher;

import java.util.List;

public interface MemberTaskService {

    List<MemberTask> findTaskList();

    MemberTask findById(Long id);

    MemberTask findByCode(String code);

    PageResult<MemberTask> findBySearch(MemberTaskSearcher query, PageModel pager);

    MemberTask insert(MemberTask bean);

    int update(MemberTask bean);

    int delete(Long id);

    void updateExecOnTimeTask();

}