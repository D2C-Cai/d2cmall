package com.d2c.report.services.rest;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.base.utils.DateUt;
import com.d2c.report.services.PartnerReportServiceImpl;
import com.d2c.report.services.PartnerSaleTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 买手报表测试
 *
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class PartnerReportRestServiceImpl implements PartnerReportRestService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PartnerSaleTimeService partnerSaleTimeService;
    @Autowired
    private PartnerReportServiceImpl partnerReportService;

    @Override
    public Object build(Integer back) {
        if (back == null || back <= 0) back = 1;
        Date day = DateUt.dayBack(back);
        if (partnerSaleTimeService.countPartnerDay(day) == 0) {
            logger.info(DateUt.date2str(day) + "-当天日表数据未统计，开始统计...");
            partnerSaleTimeService.buildReportOnDay(day);
        }
        logger.info(DateUt.date2str(day) + "-开始统计当日买手报表...");
        return partnerReportService.buildReportOnDay(day);
    }

    @Override
    public Object test() {
        Date day = DateUt.dayBack(1);
//		partnerSaleTimeService.buildReportOnDay(day);
        Object bean = partnerReportService.buildReportOnDay(day);
        logger.info("-------> bean: " + bean);
        return bean;
    }

}
