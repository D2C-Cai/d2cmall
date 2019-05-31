package com.d2c.quartz.task;

import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.model.Reship;
import com.d2c.order.service.ReshipService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoticeReshipTask extends BaseTask {

    @Autowired
    private ReshipService reshipService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private MemberInfoService memberInfoService;

    @Scheduled(cron = "0 0 14 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.processRemind();
    }

    private void processRemind() {
        int day = 3;
        List<Reship> list = reshipService.findNotDeliveryRemind(day);
        for (Reship reship : list) {
            MemberInfo memeberInfo = memberInfoService.findById(reship.getMemberId());
            try {
                SmsBean smsBean = new SmsBean(memeberInfo.getNationCode(), memeberInfo.getLoginCode(),
                        SmsLogType.REMIND,
                        "亲爱的，距离通过您退货申请已超过" + day + "天，请在" + DateUtil.day2str(reship.getDeliveryExpiredDate())
                                + "号前寄回商品并填写退回物流单号，逾期未填写，退货申请将自动关闭，感谢您的支持！");
                PushBean pushBean = new PushBean(reship.getMemberId(), "亲爱的，距离通过您退货申请已超过" + day + "天，请在"
                        + DateUtil.day2str(reship.getDeliveryExpiredDate()) + "号前寄回商品并填写退回物流单号，逾期未填写，退货申请将自动关闭，感谢您的支持！",
                        23);
                pushBean.setAppUrl("/member/reship/" + reship.getId());
                MsgBean msgBean = new MsgBean(reship.getMemberId(), 23, "退货通知",
                        "亲爱的，距离通过您退货申请已超过" + day + "天，请在" + DateUtil.day2str(reship.getDeliveryExpiredDate())
                                + "号前寄回商品并填写退回物流单号，逾期未填写，退货申请将自动关闭，感谢您的支持！");
                msgBean.setAppUrl("/member/reship/" + reship.getId());
                msgBean.setPic(reship.getProductImg());
                msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
