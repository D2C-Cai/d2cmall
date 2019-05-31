package com.d2c.content.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.content.model.SplashScreen;
import com.d2c.content.service.SplashScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;

@Component
public class SplashScreenQueueListener extends AbsMqListener {

    @Autowired
    private SplashScreenService splashScreenService;

    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long id = mapMsg.getLong("id");
            Integer mark = mapMsg.getInt("mark");
            Long date = mapMsg.getLong("date");
            if (mark.intValue() == 1) {
                this.doUpSplashScreen(id, date);
            }
            if (mark.intValue() == 0) {
                this.doDownSplashScreen(id, date);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void doUpSplashScreen(Long id, long date) {
        SplashScreen splashScreen = splashScreenService.findById(id);
        if (splashScreen != null && splashScreen.getStatus() == 0) {
            if (splashScreen.getBeginDate() != null && splashScreen.getBeginDate().compareTo(new Date(date)) <= 0) {
                if (splashScreen.getEndDate() == null || splashScreen.getEndDate().before(new Date())) {
                    splashScreenService.doTiming(id, 0);
                }
                splashScreenService.updateStatus(id, 1, splashScreen.getLastModifyMan() + " : SYS消息通知");
            }
        }
    }

    private void doDownSplashScreen(Long id, long date) {
        SplashScreen splashScreen = splashScreenService.findById(id);
        if (splashScreen != null && splashScreen.getStatus() == 1) {
            if (splashScreen.getEndDate() != null && splashScreen.getEndDate().compareTo(new Date(date)) <= 0) {
                splashScreenService.doTiming(id, 0);
                splashScreenService.updateStatus(id, 0, splashScreen.getLastModifyMan() + " : SYS消息通知");
            }
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.TIMING_SCREEN;
    }

}
