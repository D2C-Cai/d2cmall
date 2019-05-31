package com.d2c.quartz.task;

import com.d2c.member.service.MemberLottoService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredAwardTask extends BaseTask {

    @Autowired
    private MemberLottoService memberLottoService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        memberLottoService.doClear();
    }

}
