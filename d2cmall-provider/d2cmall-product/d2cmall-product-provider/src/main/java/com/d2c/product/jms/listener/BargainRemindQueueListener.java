package com.d2c.product.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.SmsBean;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.service.BargainPromotionService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;

@Component
public class BargainRemindQueueListener extends AbsMqListener {

    @Autowired
    private BargainPromotionService bargainPromotionService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long memberId = Long.parseLong(mapMsg.getString("memberId"));
            Long promotionId = Long.parseLong(mapMsg.getString("promotionId"));
            String mobile = mapMsg.getString("mobile");
            BargainPromotion promotion = bargainPromotionService.findById(promotionId);
            if (promotion != null) {
                String info = "";
                if (promotion.getBeginDate().getTime() > new Date().getTime() + 25 * 60 * 1000) {
                    info = "您关注的砍价活动预计" + DateUtil.convertDate2Str(promotion.getBeginDate(), "HH:mm:ss")
                            + "开始！ 邀请好友助力！享受" + promotion.getMinPrice() + "元疯抢！" + promotion.getName()
                            + " http://m.d2cmall.com/bargain/detail/" + promotion.getId() + " ";
                }
                String content = info;
                SmsBean smsBean = new SmsBean(null, mobile, SmsLogType.REMIND, content);
                msgUniteService.sendMsg(smsBean, null, null, null);
                bargainPromotionService.deleteRemind(memberId, promotionId);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.BARGAIN_REMIND;
    }

}
