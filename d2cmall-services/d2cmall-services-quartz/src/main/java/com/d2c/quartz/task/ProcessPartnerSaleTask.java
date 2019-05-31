package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.report.services.PartnerSaleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时买手统计数据
 * <p>
 * 每2个小时
 *
 * @author wull
 */
@Component
public class ProcessPartnerSaleTask extends BaseTask {

    @Reference
    private PartnerSaleService partnerSaleService;

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void execute() {
        super.exec();
    }

    @Override
    public void execImpl() {
        partnerSaleService.buildReport();
    }

}
