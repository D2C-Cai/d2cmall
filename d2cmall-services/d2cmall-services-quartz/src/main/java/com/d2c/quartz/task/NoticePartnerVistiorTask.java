package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.services.EventQueryService;
import com.d2c.common.base.enums.DateType;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.model.KeyValue;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.WeixinPushService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.third.wechat.WeixinPushEntity;
import com.d2c.member.model.Partner;
import com.d2c.member.model.PartnerStore;
import com.d2c.member.service.PartnerService;
import com.d2c.member.service.PartnerStoreService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class NoticePartnerVistiorTask extends BaseTask {

    @Reference
    private EventQueryService eventQueryService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private WeixinPushService weixinPushService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private PartnerStoreService partnerStoreService;

    @Scheduled(cron = "0 0 9,11,13,15,17,19,21 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.countVistors();
    }

    private void countVistors() {
        try {
            Calendar cal = Calendar.getInstance();
            Date startDate = new Date();
            if (cal.get(Calendar.HOUR_OF_DAY) == 9) {
                startDate = DateUt.dateBack(DateType.HOUR, 12);
            } else {
                startDate = DateUt.dateBack(DateType.HOUR, 2);
            }
            List<KeyValue> list = eventQueryService.countVistiors(startDate);
            for (KeyValue kv : list) {
                try {
                    Partner partner = partnerService.findById(Long.valueOf(kv.getId().toString()));
                    WeixinPushEntity msgObj = new WeixinPushEntity(partner.getOpenId(), "有人访问了你的买手店", partner.getName(),
                            startDate, new Date(), "访问人数" + kv.getValue() + "人", "赶紧去小程序看看吧", "/pages/center/visitor");
                    weixinPushService.send(msgObj);
                    PartnerStore partnerStore = partnerStoreService.findByPartnerId(partner.getId());
                    if (partnerStore != null) {
                        PushBean pushBean = new PushBean(partner.getMemberId(),
                                "有人访问了您的买手店，访问人数" + kv.getValue() + "人，赶紧去看看吧~", 84);
                        pushBean.setAppUrl("/to/partner/store");
                        MsgBean msgBean = new MsgBean(partner.getMemberId(), 84, "访客统计",
                                "有人访问了您的买手店，访问人数" + kv.getValue() + "人，赶紧去看看吧~");
                        msgBean.setAppUrl("/to/partner/store");
                        msgUniteService.sendPush(pushBean, msgBean);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error("countVistors error!" + e.getMessage());
        }
    }

}
