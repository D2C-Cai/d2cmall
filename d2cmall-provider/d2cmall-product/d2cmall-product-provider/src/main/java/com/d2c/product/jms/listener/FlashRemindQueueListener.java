package com.d2c.product.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.logger.model.Remind;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.RemindService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.service.FlashPromotionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;

@Component
public class FlashRemindQueueListener extends AbsMqListener {

    @Autowired
    private RemindService remindService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long remindId = mapMsg.getLong("remindId");
            Long flashPromotionId = mapMsg.getLong("flashPromotionId");
            String wapUrl = mapMsg.getString("wapUrl");
            String picUrl = mapMsg.getString("picUrl");
            String productName = mapMsg.getString("productName");
            Remind remind = remindService.findById(remindId);
            FlashPromotion fp = flashPromotionService.findById(flashPromotionId);
            if (remind != null && fp != null && !fp.isEnd()) {
                this.doSendMsg(remind, wapUrl, picUrl, productName);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private int doSendMsg(Remind remind, String wapUrl, String picUrl, String productName) {
        int result = 0;
        if (remind == null) {
            return 1;
        }
        if (remind.getSmsSend() != null && remind.getSmsSend() == true) {
            return 1;
        }
        if (StringUtils.isBlank(remind.getMobile())) {
            return 1;
        }
        String phone = remind.getMobile();
        String subject = remind.getRemindTypeName();
        String content = remind.getContent();
        PushBean pushBean = new PushBean(remind.getMemberId(), content, 29);
        pushBean.setAppUrl("/flashpromotion/product/session");
        MsgBean msgBean = new MsgBean(remind.getMemberId(), 29, subject, content);
        msgBean.setAppUrl("/flashpromotion/product/session");
        msgBean.setPic(picUrl);
        SmsBean smsBean = new SmsBean(null, phone, SmsLogType.REMIND, content + productName + wapUrl);
        msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
        remind.setSmsSend(true);
        remind.setSmsSendDate(new Date());
        result = remindService.update(remind);
        return result;
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.FLASH_REMIND;
    }

}
