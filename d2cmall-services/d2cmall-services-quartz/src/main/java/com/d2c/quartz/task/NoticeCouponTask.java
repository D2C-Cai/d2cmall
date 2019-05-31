package com.d2c.quartz.task;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.order.service.CouponQueryService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.properties.QuartzProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class NoticeCouponTask extends BaseTask {

    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private QuartzProperties props;

    @Scheduled(cron = "0 30 11 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doCouponRemind();
    }

    /**
     * N天后将过期优惠券提醒
     */
    private void doCouponRemind() {
        try {
            if (props.getMsg() == 1) {
                this.doCouponRemind(7);
                this.doCouponRemind(3);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void doCouponRemind(int days) {
        List<Map<String, Object>> list = couponQueryService.findExpireCoupon(days);
        if (list.size() > 0) {
            for (Map<String, Object> map : list) {
                this.couponRemindMQ(map);
            }
        }
    }

    private void couponRemindMQ(Map<String, Object> map) {
        Long memberId = Long.valueOf(map.get("memberId").toString());
        String count = map.get("count").toString();
        String expiredate = map.get("expire_date").toString().substring(0, 10);
        Double amount = Double.parseDouble(map.get("amount").toString());
        JSONObject obj = new JSONObject();
        BigDecimal money = new BigDecimal(amount);
        obj.put("money", money.setScale(2, BigDecimal.ROUND_HALF_UP));
        String subject = "优惠券到期提醒";
        String content = "您在D2C有" + count + "张优惠券即将于" + expiredate + "过期，请记得在有效期内使用，以免浪费。";
        PushBean pushBean = new PushBean(memberId, content, 32);
        MsgBean msgBean = new MsgBean(memberId, 32, subject, content);
        msgBean.setAppUrl("/coupon/memberCoupon");
        msgBean.setOther(obj.toJSONString());
        msgUniteService.sendPush(pushBean, msgBean);
    }

}
