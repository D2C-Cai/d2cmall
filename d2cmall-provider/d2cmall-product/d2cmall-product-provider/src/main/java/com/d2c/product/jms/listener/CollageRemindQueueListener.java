package com.d2c.product.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.service.CollagePromotionService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;

@Component
public class CollageRemindQueueListener extends AbsMqListener {

    @Autowired
    private CollagePromotionService collagePromotionService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long memberId = Long.parseLong(mapMsg.getString("memberId"));
            Long promotionId = Long.parseLong(mapMsg.getString("promotionId"));
            String mobile = mapMsg.getString("mobile");
            CollagePromotion promotion = collagePromotionService.findById(promotionId);
            if (promotion != null && promotion.getStatus() == 1) {
                String info = "";
                if (promotion.getBeginDate().getTime() >= new Date().getTime() + 5 * 60 * 1000) {
                    info = "您关注的拼团活动预计" + DateUtil.convertDate2Str(promotion.getBeginDate(), "HH:mm:ss")
                            + "开始！ 拼团商品即将开始，邀请好友一起拼";
                    String content = info;
                    SmsBean smsBean = new SmsBean(null, mobile, SmsLogType.REMIND, content);
                    MsgBean msgBean = new MsgBean(memberId, 61, "拼团活动即将开始，邀请好友一起拼", content);
                    msgBean.setAppUrl("/collage/" + promotionId + "/" + promotion.getProductId());
                    msgBean.setPic(promotion.getProductImage());
                    msgUniteService.sendMsg(smsBean, null, msgBean, null);
                }
                collagePromotionService.deleteRemind(memberId, promotionId);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.COLLAGE_REMIND;
    }

}
