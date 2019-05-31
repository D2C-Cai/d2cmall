package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时刷新用户每日任务
 *
 * @author wull
 */
@Component
public class ExpiredMemberTaskExecTask extends BaseTask {

    @Reference
    private MemberTaskExecService memberTaskExecService;

    @Scheduled(cron = "30 3 0 * * ?")
    public void execute() {
        super.exec();
    }

    @Override
    public void execImpl() {
        memberTaskExecService.refreshOnDayTask();
    }

}
