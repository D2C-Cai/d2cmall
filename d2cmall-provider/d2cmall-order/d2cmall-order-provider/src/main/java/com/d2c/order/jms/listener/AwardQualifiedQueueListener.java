package com.d2c.order.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.member.model.MemberLotto.LotteryOpportunityEnum;
import com.d2c.member.service.MemberLottoService;
import com.d2c.product.model.AwardSession;
import com.d2c.product.service.AwardSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;
import java.util.List;

@Component
public class AwardQualifiedQueueListener extends AbsMqListener {

    @Autowired
    private MemberLottoService memberLottoService;
    @Autowired
    private AwardSessionService awardSessionService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long memberId = Long.parseLong(mapMsg.getString("memberId"));
            String type = mapMsg.getString("lotteryOpportunityEnum");
            List<AwardSession> list = awardSessionService.findByLotterySource(0, null);
            LotteryOpportunityEnum lotteryOpportunityEnum = LotteryOpportunityEnum.valueOf(type);
            Date now = new Date();
            for (AwardSession session : list) {
                if (now.after(session.getBeginDate()) && now.before(session.getEndDate())) {
                    memberLottoService.updateMemberLottery(memberId, lotteryOpportunityEnum, session.getId());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.AWARD_QUALIFIED;
    }

}
