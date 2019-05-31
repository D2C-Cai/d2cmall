package com.d2c.quartz.task;

import com.d2c.logger.service.MessageService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExpiredMessageTask extends BaseTask {

    @Autowired
    private MessageService messageService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doRemoveExpiredMessage();
    }

    /**
     * 删除超过一个月的消息
     */
    private void doRemoveExpiredMessage() {
        try {
            Date date = DateUtil.getIntervalMonth(new Date(), -1);
            messageService.doDeleteExpire(date);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
