package com.d2c.quartz.task;

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
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoticePartnerCancelTask extends BaseTask {

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerStoreService partnerStoreService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private WeixinPushService weixinPushService;

    @Scheduled(cron = "0 0 9,21 * * ?")
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
        // 查询关闭前一周的分销
        Integer intervalDays = 7;
        List<Partner> list = new ArrayList<>();
        list = partnerService.findCancelRemind(intervalDays);
        for (Partner partner : list) {
            try {
                PartnerStore partnerStore = partnerStoreService.findByPartnerId(partner.getId());
                Partner parent = partnerService.findById(partner.getParentId());
                if (partnerStore != null) {
                    // 自己
                    WeixinPushEntity obj = new WeixinPushEntity(partner.getOpenId(), "您的店铺还有" + intervalDays + "天即将关店",
                            partnerStore.getName() + "店铺", "店铺即将被关闭",
                            DateUtil.secondFormatCn(partner.getExpiredDate()) + "关闭", "关闭提醒", "自己下单或分享好友下单即可通过试用期",
                            "/pages/member/index");
                    weixinPushService.send(obj);
                    PushBean pushBean = new PushBean(partner.getMemberId(),
                            "您的买手店还有" + intervalDays + "天即将关店，自己下单或分享好友下单即可通过试用期", 86);
                    pushBean.setAppUrl("/to/partner/mine");
                    MsgBean msgBean = new MsgBean(partner.getMemberId(), 86, "关店提醒",
                            "您的买手店还有" + intervalDays + "天即将关店，自己下单或分享好友下单即可通过试用期");
                    msgBean.setAppUrl("/to/partner/mine");
                    msgBean.setPic(partnerStore.getPic());
                    msgUniteService.sendPush(pushBean, msgBean);
                    // 上级
                    if (parent != null) {
                        WeixinPushEntity parentObj = new WeixinPushEntity(parent.getOpenId(),
                                "您的买手（" + partner.getName() + "）还有" + intervalDays + "天即将被关店",
                                partnerStore.getName() + "店铺", "店铺即将被关闭",
                                DateUtil.secondFormatCn(partner.getExpiredDate()) + "关闭", "关闭提醒", "点击查看详细",
                                "/pages/member/team/list?level=2");
                        weixinPushService.send(parentObj);
                        PushBean parentPushBean = new PushBean(parent.getMemberId(), "您的买手（" + partner.getName() + "）还有"
                                + intervalDays + "天即将被关店，去小程序“D2C买手服务中心”或D2C APP中可查看", 86);
                        pushBean.setAppUrl("/to/partner/team");
                        MsgBean parentMsgBean = new MsgBean(parent.getMemberId(), 86, "关店提醒", "您的买手（"
                                + partner.getName() + "）还有" + intervalDays + "天即将被关店，去小程序“D2C买手服务中心”或D2C APP中可查看");
                        msgBean.setPic(partnerStore.getPic());
                        msgBean.setAppUrl("/to/partner/team");
                        msgUniteService.sendPush(parentPushBean, parentMsgBean);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
