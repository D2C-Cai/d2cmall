package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.Partner;
import com.d2c.member.query.PartnerSearcher;
import com.d2c.member.service.PartnerService;
import com.d2c.order.service.tx.PartnerTxService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class NoticePartnerLoginTask extends BaseTask {

    @Autowired
    private PartnerService partnerService;
    @Reference
    private PartnerTxService partnerTxService;

    @Scheduled(cron = "0 0 10 ? * MON")
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
        PartnerSearcher searcher = new PartnerSearcher();
        Date lastLoginDate = DateUtil.getIntervalDay(new Date(), -7);
        searcher.setLastLoginDateEnd(lastLoginDate);
        this.processPager(100, new EachPage<Partner>() {
            @Override
            public int count() {
                return partnerService.countBySearcher(searcher);
            }

            @Override
            public PageResult<Partner> search(PageModel page) {
                return partnerService.findBySearcher(searcher, page);
            }

            @Override
            public boolean each(Partner object) {
                int random = (int) (Math.random() * 100 + 1);
                partnerTxService.doAward(object.getId(), object.getId(), object.getLoginCode(),
                        new BigDecimal(random).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP),
                        "定时器");
                return true;
            }
        });
    }

}
