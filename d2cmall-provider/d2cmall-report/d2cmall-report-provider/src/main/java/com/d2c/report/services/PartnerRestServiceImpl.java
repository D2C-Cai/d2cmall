package com.d2c.report.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.member.model.Partner;
import com.d2c.report.mongo.dao.PartnerSaleDayMongoDao;
import com.d2c.report.mongo.dao.PartnerSaleMongoDao;
import com.d2c.report.mongo.dao.PartnerSaleMonthMongoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 执行分销商任务
 *
 * @author wull
 */
@Service(protocol = {"dubbo", "rest"})
public class PartnerRestServiceImpl implements PartnerRestService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PartnerSaleTimeServiceImpl partnerSaleTimeService;
    @Autowired
    private PartnerSaleServiceImpl partnerSaleService;
    @Autowired
    private PartnerSaleMongoDao partnerSaleMongoDao;
    @Autowired
    private PartnerSaleDayMongoDao partnerSaleDayMongoDao;
    @Autowired
    private PartnerSaleMonthMongoDao partnerSaleMonthMongoDao;

    @Override
    public String buildSale() {
        Integer count = partnerSaleService.buildReport();
        return "重建经销商数据:" + count;
    }

    @Override
    public String buildSaleDay(Integer day) {
        Date date = DateUt.dayBack(day);
        PageBucket<Partner> bucket = partnerSaleTimeService.getPartnerBucket();
        bucket.forEach(partner -> {
            partnerSaleTimeService.buildPartnerDayPool(partner, date);
        });
        return "重建 " + DateUt.date2str(date) + " Day 经销商数据:" + bucket.getCount();
    }

    @Override
    public String buildSaleMonth(Integer month) {
        Date date = DateUt.monthBack(month);
        PageBucket<Partner> bucket = partnerSaleTimeService.getPartnerBucket();
        bucket.forEach(partner -> {
            partnerSaleTimeService.buildPartnerMonthPool(partner, date);
        });
        return "重建 " + DateUt.month2str(date) + " Month 经销商数据:" + bucket.getCount();
    }

    @Override
    public String rebuildAll() {
        PageBucket<Partner> bucket = partnerSaleService.getPartnerBucket();
        cleanAll();
        Date start = DateUt.str2minute("2017-12-20 05:00");
        Date end = new Date();
        int dint = 0, mint = 0;
        while (bucket.hasNext()) {
            Partner partner = bucket.next();
            partnerSaleService.buildReportPool(partner);
            if (start.before(partner.getCreateDate())) {
                start = partner.getCreateDate();
            }
            Date dstart = start;
            while (end.after(dstart)) {
                partnerSaleTimeService.buildPartnerDayPool(partner, dstart);
                dstart = DateUt.dayAdd(dstart, 1);
                dint++;
            }
            Date mstart = start;
            while (DateUt.monthEnd(end).after(mstart)) {
                partnerSaleTimeService.buildPartnerMonthPool(partner, mstart);
                mstart = DateUt.monthAdd(mstart, 1);
                mint++;
            }
        }
        return "重建 " + bucket.getCount() + " 个经销商数据, 重建" + dint + "条日统计数据, 重建" + mint + "条月统计数据";
    }

    void cleanAll() {
        partnerSaleMongoDao.cleanAll();
        partnerSaleDayMongoDao.cleanAll();
        partnerSaleMonthMongoDao.cleanAll();
    }

}
