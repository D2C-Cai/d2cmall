package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.MemberTaskMapper;
import com.d2c.member.model.MemberTask;
import com.d2c.member.query.MemberTaskSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("memberTaskService")
public class MemberTaskServiceImpl extends ListServiceImpl<MemberTask> implements MemberTaskService {

    @Autowired
    private MemberTaskMapper memberTaskMapper;

    public List<MemberTask> findTaskList() {
        // MemberTaskSearcher query = new MemberTaskSearcher();
        // query.setExec(true);
        // return findQuery(query, new PageModel(true, "sort"));
        return memberTaskMapper.findTaskList();
    }

    public MemberTask findByCode(String code) {
        return findOneByFieldName("code", code);
    }

    public MemberTask findById(Long id) {
        return findOneById(id);
    }

    public PageResult<MemberTask> findBySearch(MemberTaskSearcher query, PageModel pager) {
        pager.setSort("type|asc", "sort|asc");
        return findPageResult(query, pager);
    }

    /**
     * 刷新用户限时任务, 定时更新可执行状态的开始结束时间内的任务
     */
    public void updateExecOnTimeTask() {
        memberTaskMapper.updateExecStart();
        memberTaskMapper.updateExecEnd();
    }

    public MemberTask insert(MemberTask bean) {
        return this.save(bean);
    }

    public int update(MemberTask bean) {
        return updateNotNull(bean);
    }

    public int delete(Long id) {
        return this.deleteById(id);
    }

}
