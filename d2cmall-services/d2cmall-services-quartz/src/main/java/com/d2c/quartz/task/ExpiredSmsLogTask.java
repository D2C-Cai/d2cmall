package com.d2c.quartz.task;

import com.d2c.logger.service.SmsLogService;
import com.d2c.member.service.LoginService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredSmsLogTask extends BaseTask {

    @Autowired
    private SmsLogService smsLogService;
    @Autowired
    private LoginService loginService;

    @Scheduled(fixedDelay = 60 * 1000 * 20)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.deleteExpireLog();
    }

    /**
     * 超出20分钟短信日志删除
     */
    private void deleteExpireLog() {
        try {
            smsLogService.deleteExpireLog();
            loginService.deleteExpireLog();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
