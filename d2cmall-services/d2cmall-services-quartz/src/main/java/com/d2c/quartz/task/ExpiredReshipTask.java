package com.d2c.quartz.task;

import com.d2c.order.model.Reship;
import com.d2c.order.service.ReshipService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpiredReshipTask extends BaseTask {

    @Autowired
    private ReshipService reshipService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.autoClose();
    }

    private void autoClose() {
        List<Reship> list = reshipService.findNotDeliveryClose();
        for (Reship reship : list) {
            try {
                reshipService.doSysCloseReship(reship.getId(), "定时器", "用户发货超时");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
