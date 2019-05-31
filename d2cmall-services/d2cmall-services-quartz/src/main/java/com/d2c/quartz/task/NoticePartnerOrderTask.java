package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.service.WeixinPushService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.third.wechat.WeixinPushEntity;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import com.d2c.order.service.OrderService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class NoticePartnerOrderTask extends BaseTask {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private WeixinPushService weixinPushService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Scheduled(cron = "0 0 9,21 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.processPartnerOrder();
    }

    private void processPartnerOrder() {
        int numberPage = 1;
        PageModel page = new PageModel();
        page.setP(numberPage);
        page.setPageSize(100);
        PageResult<Map<String, Object>> pager = new PageResult<>();
        Date now = new Date();
        try {
            do {
                pager = orderService.findPartnerOrder(now, page);
                if (pager.getList() != null && pager.getList().size() > 0) {
                    for (Map<String, Object> map : pager.getList()) {
                        try {
                            Partner partner = partnerService.findById(Long.valueOf(map.get("partner_id").toString()));
                            WeixinPushEntity msgObj = new WeixinPushEntity(partner.getOpenId(), "店铺有未付款订单", now,
                                    Integer.valueOf(map.get("count").toString()), "您好，您的店铺有用户下单还未付款。详情请进入D2C小程序查询",
                                    "/pages/member/bill/list");
                            weixinPushService.send(msgObj);
                            PushBean pushBean = new PushBean(partner.getMemberId(),
                                    "您的买手店有" + Integer.valueOf(map.get("count").toString()) + "笔订单下单未付款，点击查看", 89);
                            pushBean.setAppUrl("/to/partner/sales");
                            MsgBean msgBean = new MsgBean(partner.getMemberId(), 89, "订单未支付",
                                    "您的买手店有" + Integer.valueOf(map.get("count").toString()) + "笔订单下单未付款，点击查看");
                            msgBean.setAppUrl("/to/partner/sales");
                            msgUniteService.sendPush(pushBean, msgBean);
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                page.setPageNumber(page.getPageNumber() + 1);
            } while (pager.isNext());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
