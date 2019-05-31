package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
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
import com.d2c.quartz.task.base.EachPage;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExpiredPartnerTask extends BaseTask {

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerStoreService partnerStoreService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private WeixinPushService weixinPushService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doCancelPartner();
    }

    /**
     * 无业绩试用期已到的买手
     */
    private void doCancelPartner() {
        try {
            this.processPager(100, new EachPage<Partner>() {
                @Override
                public int count() {
                    return partnerService.countExpired();
                }

                @Override
                public PageResult<Partner> search(PageModel page) {
                    return partnerService.findExpired(page);
                }

                @Override
                public boolean each(Partner object) {
                    if (object.getConsumeDate() == null) {
                        int result = partnerService.doCancel(object.getId(), object.getMemberId(), "定时器");
                        Partner parent = partnerService.findById(object.getParentId());
                        PartnerStore partnerStore = partnerStoreService.findByPartnerId(object.getId());
                        if (partnerStore != null) {
                            // 自己
                            WeixinPushEntity obj = new WeixinPushEntity(object.getOpenId(), "您的店铺已被关店",
                                    partnerStore.getName() + "店铺", "店铺已被关闭", DateUtil.secondFormatCn(new Date()) + "关闭",
                                    "正式关店", "购买指定商品即可重新开店", null);
                            weixinPushService.send(obj);
                            PushBean pushBean = new PushBean(object.getMemberId(),
                                    "您的买手店已被关店，在小程序“D2C买手服务中心”购买指定商品即可重新开店", 86);
                            MsgBean msgBean = new MsgBean(object.getMemberId(), 86, "关店提醒",
                                    "您的买手店已被关店，在小程序“D2C买手服务中心”购买指定商品即可重新开店");
                            msgBean.setPic(partnerStore.getPic());
                            msgUniteService.sendPush(pushBean, msgBean);
                            // 上级
                            if (parent != null) {
                                WeixinPushEntity parentObj = new WeixinPushEntity(object.getOpenId(),
                                        "您的买手（" + object.getName() + "）已被关店", partnerStore.getName() + "店铺", "店铺已被关闭",
                                        DateUtil.secondFormatCn(new Date()) + "关闭", "正式关店", "点击查看详细",
                                        "/pages/member/team/list?level=2");
                                weixinPushService.send(parentObj);
                                PushBean parentPushBean = new PushBean(parent.getMemberId(),
                                        "您的买手（" + object.getName() + "）已被关店，去小程序“D2C买手服务中心”或D2C APP中可查看", 86);
                                pushBean.setAppUrl("/to/partner/team");
                                MsgBean parentMsgBean = new MsgBean(parent.getMemberId(), 86, "关店提醒",
                                        "您的买手（" + object.getName() + "）已被关店，去小程序“D2C买手服务中心”或D2C APP中可查看");
                                msgBean.setPic(partnerStore.getPic());
                                msgBean.setAppUrl("/to/partner/team");
                                msgUniteService.sendPush(parentPushBean, parentMsgBean);
                            }
                        }
                        return result > 0 ? true : false;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
