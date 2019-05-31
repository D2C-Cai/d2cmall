package com.d2c.quartz.task;

import com.d2c.member.service.MemberTaskService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时刷新用户限时任务
 *
 * @author wull
 */
@Component
public class ExpiredMemberTaskTask extends BaseTask {

    @Autowired
    private MemberTaskService memberTaskService;

    @Scheduled(cron = "0 6 0/1 * * ?")
    public void execute() {
        super.exec();
    }

    @Override
    public void execImpl() {
        memberTaskService.updateExecOnTimeTask();
    }

}
