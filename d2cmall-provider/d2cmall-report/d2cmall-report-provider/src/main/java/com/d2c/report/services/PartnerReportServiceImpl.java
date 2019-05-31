package com.d2c.report.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.common.base.enums.DateType;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.ConvertUt;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.model.KeyValue;
import com.d2c.common.core.utils.KeyValueUt;
import com.d2c.common.mongodb.build.AggrBuild;
import com.d2c.common.mongodb.utils.AggrUt;
import com.d2c.report.mongo.dao.PartnerReportMongoDao;
import com.d2c.report.mongo.dao.PartnerSaleDayMongoDao;
import com.d2c.report.mongo.dao.PartnerSaleMongoDao;
import com.d2c.report.mongo.dao.PartnerSaleMonthMongoDao;
import com.d2c.report.mongo.model.PartnerReportDO;
import com.d2c.report.mongo.model.PartnerSaleDO;
import com.d2c.report.mongo.model.PartnerSaleDayDO;
import com.d2c.report.mongo.model.PartnerSaleMonthDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 买手每日汇总统计报表
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class PartnerReportServiceImpl implements PartnerReportService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PartnerReportMongoDao partnerReportMongoDao;
    @Autowired
    private PartnerSaleMongoDao partnerSaleMongoDao;
    @Autowired
    private PartnerSaleDayMongoDao partnerSaleDayMongoDao;
    @Autowired
    private PartnerSaleMonthMongoDao partnerSaleMonthMongoDao;

    /**
     * 买手每日汇总统计报表
     */
    public PageResult<PartnerReportDO> findPageQuery(MongoQuery query, PageModel pager) {
        List<PartnerReportDO> list = partnerReportMongoDao.findQueryPage(query, pager);
        long count = partnerReportMongoDao.count(query);
        return new PageResult<>(pager, list, count);
    }
    //*****************************************

    /**
     * 定时任务-买手每日汇总统计报表
     */
    public Object buildReportOnDay(Date date) {
        PartnerReportDO bean = buildReport(date);
        apply(bean, aggrPartnerSale());
        apply(bean, aggrPartnerSaleDay(date));
        apply(bean, aggrPartnerSaleMonth(date));
        return partnerReportMongoDao.save(bean);
    }

    public PartnerReportDO buildReport(Date date) {
        PartnerReportDO bean = new PartnerReportDO(date);
        bean.setId(DateUt.date2str(date));
        bean.setLiveCount(partnerSaleMongoDao.count(new Query(Criteria.where("status").gt(0))));
        bean.setSaleCount(partnerSaleMongoDao.count(new Query(Criteria.where("payAmount").gt(0))));
        bean.setValidCount(partnerSaleMongoDao.count(new Query(Criteria.where("payAmount").gte(1000))));
        bean.setTryAMCount(partnerSaleMongoDao.count(new Query(Criteria.where("level").is(0).and("status").is(0))));
        bean.setSaleAMCount(partnerSaleMongoDao.count(new Query(Criteria.where("level").is(0).and("payAmount").gt(0))));
        bean.setTryDMCount(partnerSaleMongoDao.count(new Query(Criteria.where("level").is(1).and("status").is(0))));
        bean.setSaleDMCount(partnerSaleMongoDao.count(new Query(Criteria.where("level").is(1).and("payAmount").gt(0))));
        bean.setTryPartnerCount(partnerSaleMongoDao.count(new Query(Criteria.where("level").is(2).and("status").is(0))));
        bean.setSalePartnerCount(partnerSaleMongoDao.count(new Query(Criteria.where("level").is(2).and("payAmount").gt(0))));
        bean.setValidPartnerCount(partnerSaleMongoDao.count(new Query(Criteria.where("level").is(2).and("payAmount").gt(1000))));
        bean.setDayAdd(partnerSaleMongoDao.count(new Query(Criteria.where("createDate").gte(DateUt.dayStart(date)))));
        bean.setMonthAdd(partnerSaleMongoDao.count(new Query(Criteria.where("createDate").gte(DateUt.monthStart(date)))));
        bean.setSvCount(partnerSaleMongoDao.count(new Query(Criteria.where("counselorId").ne(null))));
        bean.setNoSvSaleCount(partnerSaleMongoDao.count(new Query(Criteria.where("counselorId").is(null).and("payAmount").gt(0))));
        bean.setNoSvCloseCount(partnerSaleMongoDao.count(new Query(Criteria.where("counselorId").is(null).and("status").lt(0))));
        bean.setDayCloseCounts(aggrDayCloseCounts(date));
        bean.setMonthCloseCount(partnerSaleMonthMongoDao.count(new Query(Criteria.where("expiredDate").gte(DateUt.monthStart(date)).lte(DateUt.monthEnd(date)))));
        return bean;
    }

    public PartnerReportDO aggrPartnerSale() {
        AggrBuild ab = AggrBuild.build();
        ab.add(Aggregation.group().sum("payAmount").as("payAmount").count().as("count"));
        return partnerSaleMongoDao.aggregateOne(ab.newAggregation(), PartnerSaleDO.class, PartnerReportDO.class);
    }

    public PartnerReportDO aggrPartnerSaleDay(Date date) {
        AggrBuild ab = AggrBuild.build();
        ab.and("day").is(DateUt.date2str(date));
        ab.add(Aggregation.group().sum("payAmount").as("dayPayAmount"));
        return partnerSaleDayMongoDao.aggregateOne(ab.newAggregation(), PartnerSaleDayDO.class, PartnerReportDO.class);
    }

    public PartnerReportDO aggrPartnerSaleMonth(Date date) {
        AggrBuild ab = AggrBuild.build();
        ab.and("month").is(DateUt.month2str(date));
        ab.add(Aggregation.group().sum("payAmount").as("monthPayAmount"));
        return partnerSaleMonthMongoDao.aggregateOne(ab.newAggregation(), PartnerSaleMonthDO.class, PartnerReportDO.class);
    }

    public Map<Integer, Long> aggrDayCloseCounts(Date date) {
        AggrBuild ab = AggrBuild.build();
        ab.and("day").is(DateUt.date2str(date));
        ab.and("expiredDate").gte(DateUt.dayBack(date, 7)).lte(DateUt.dayEnd());
        ab.add(Aggregation.project().and("expiredDate").plus(AggrUt.GMT_CHINA_TIMEZONE).as("expiredDate"),
                Aggregation.project().and("expiredDate").dateAsFormattedString(AggrUt.dateAsFormat(DateType.DAY)).as("key"),
                Aggregation.group("key").count().as("value"));
        List<KeyValue> list = partnerSaleDayMongoDao.aggregate(ab.newAggregation(), PartnerSaleDayDO.class, KeyValue.class);
        Map<Object, Object> map = KeyValueUt.toMap(list);
        Map<Integer, Long> dayCloseMap = new LinkedHashMap<>();
        for (int i = 7; i >= 0; i--) {
            dayCloseMap.put(i, ConvertUt.toLong(map.get(DateUt.date2str(DateUt.dayBack(date, i)))));
        }
        return dayCloseMap;
    }

    private PartnerReportDO apply(PartnerReportDO dest, PartnerReportDO orig) {
        return BeanUt.apply(dest, orig);
    }

}
