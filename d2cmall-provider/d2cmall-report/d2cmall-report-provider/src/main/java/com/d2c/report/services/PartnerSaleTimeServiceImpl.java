package com.d2c.report.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import com.d2c.report.mongo.dao.PartnerSaleDayMongoDao;
import com.d2c.report.mongo.dao.PartnerSaleMonthMongoDao;
import com.d2c.report.mongo.dto.SaleStatDTO;
import com.d2c.report.mongo.model.PartnerSaleDayDO;
import com.d2c.report.mongo.model.PartnerSaleMonthDO;
import com.d2c.report.mongo.model.base.PartnerSaleTimeDO;
import com.d2c.report.services.base.PartnerSaleBaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

/**
 * 买手每日销售数据
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class PartnerSaleTimeServiceImpl extends PartnerSaleBaseServiceImpl implements PartnerSaleTimeService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PartnerSaleDayMongoDao partnerSaleDayMongoDao;
    @Autowired
    private PartnerSaleMonthMongoDao partnerSaleMonthMongoDao;
    @Reference
    private PartnerService partnerService;

    /**
     * 所有经销商
     */
    public PageBucket<Partner> getPartnerBucket() {
        PageBucket<Partner> bucket = new PageBucket<Partner>() {
            @Override
            public List<Partner> nextList(int page, int pageSize) {
                return partnerService.findPage(page, pageSize);
            }
        };
        return bucket;
    }

    /**
     * 搜索买手日统计列表
     */
    public PageResult<PartnerSaleDayDO> findPageDayQuery(MongoQuery mongoQuery, PageModel pager) {
        List<PartnerSaleDayDO> list = partnerSaleDayMongoDao.findQueryPage(mongoQuery, pager);
        long count = partnerSaleDayMongoDao.count(mongoQuery);
        return new PageResult<>(pager, toView(list), count);
    }

    public PageResult<PartnerSaleDayDO> findPageDayBack(MongoQuery mongoQuery, PageModel pager) {
        List<PartnerSaleDayDO> list = partnerSaleDayMongoDao.findQueryPage(mongoQuery, pager);
        long count = partnerSaleDayMongoDao.count(mongoQuery);
        return new PageResult<>(pager, list, count);
    }

    /**
     * 搜索买手月统计列表
     */
    public PageResult<PartnerSaleMonthDO> findPageMonthQuery(MongoQuery mongoQuery, PageModel pager) {
        List<PartnerSaleMonthDO> list = partnerSaleMonthMongoDao.findQueryPage(mongoQuery, pager);
        long count = partnerSaleMonthMongoDao.count(mongoQuery);
        return new PageResult<>(pager, toView(list), count);
    }

    public PageResult<PartnerSaleMonthDO> findPageMonthBack(MongoQuery mongoQuery, PageModel pager) {
        List<PartnerSaleMonthDO> list = partnerSaleMonthMongoDao.findQueryPage(mongoQuery, pager);
        long count = partnerSaleMonthMongoDao.count(mongoQuery);
        return new PageResult<>(pager, list, count);
    }

    /**
     * 销售概况 - 今天实时数据
     */
    public PartnerSaleDayDO findCurrentPartnerDay(Long partnerId) {
        Partner partner = partnerService.findById(partnerId);
        PartnerSaleDayDO bean = new PartnerSaleDayDO(partner, DateUt.date2str(new Date()));
        initReport(bean);
        return toView(bean);
    }

    /**
     * 销售概况 - 单天的数据
     */
    @CacheMethod
    public PartnerSaleDayDO findPartnerDay(Long partnerId, Date date) {
        PartnerSaleDayDO bean = partnerSaleDayMongoDao.findPartnerSaleDay(partnerId, DateUt.date2str(date));
        return toView(bean);
    }

    /**
     * 销售概况 - 合并多天数据, 不包括今天
     */
    @CacheMethod
    public PartnerSaleDayDO findPartnerDayMerge(Long partnerId, Date date) {
        return mergeSale(partnerSaleDayMongoDao.findPartnerSaleDayList(partnerId, date, new Date()));
    }

    /**
     * 月销售数据列表，分页
     */
    @CacheMethod
    public List<PartnerSaleMonthDO> findPagePartnerMonth(Long partnerId, PageModel pager) {
        List<PartnerSaleMonthDO> list = partnerSaleMonthMongoDao.findPagePartnerMonth(partnerId, pager);
        return toView(list);
    }

    /**
     * 销售概况 - 本月数据
     */
    @CacheMethod
    public PartnerSaleMonthDO findPartnerMonth(Long partnerId, Date date) {
        PartnerSaleMonthDO bean = partnerSaleMonthMongoDao.findPartnerSaleMonth(partnerId, DateUt.month2str(date));
        return toView(bean);
    }

    /**
     * 销售概况 - 合并多月数据
     */
    @CacheMethod
    public PartnerSaleMonthDO findPartnerMonthMerge(Long partnerId, Date date) {
        return mergeSale(partnerSaleMonthMongoDao.findPartnerSaleMonthList(partnerId, date));
    }

    /**
     * 查询该日期的日买手统计个数
     */
    public Long countPartnerDay(Date date) {
        return partnerSaleDayMongoDao.count(new Query(Criteria.where("day").is(DateUt.date2str(date))));
    }
    //***************************************************

    private <T extends PartnerSaleTimeDO> T mergeSale(List<T> list) {
        T res = null;
        for (T bean : list) {
            if (res == null) {
                res = bean;
            } else {
                res.setInviteBuyer(res.getInviteBuyer() + bean.getInviteBuyer());
                res.setInviteDM(res.getInviteDM() + bean.getInviteDM());
                addSaleStat(res.getSaleStat(), bean.getSaleStat());
                addSaleStat(res.getSaleStatDM(), bean.getSaleStatDM());
                addSaleStat(res.getSaleStatSDM(), bean.getSaleStatSDM());
                addSaleStat(res.getSaleStatAM(), bean.getSaleStatAM());
            }
        }
        return toView(res);
    }

    private SaleStatDTO addSaleStat(SaleStatDTO dto, SaleStatDTO add) {
        if (dto == null) {
            return add;
        }
        return dto.add(add);
    }
    //********************************************

    /**
     * 创建买手统计数据
     * <p>定时任务-每天
     */
    public void buildReportOnDay(Date date) {
        PageBucket<Partner> bucket = getPartnerBucket();
        bucket.forEach(bean -> {
            buildPartnerDayPool(bean, date);
            buildPartnerMonthPool(bean, date);
        });
        logger.info("买手日销售统计... 共 " + bucket.getCount() + " 条");
    }

    /**
     * 买手日销售数据统计
     */
    public void buildPartnerDayPool(Partner partner, Date date) {
        MyExecutors.limilPool().execute(new Runnable() {
            @Override
            public void run() {
                String day = DateUt.date2str(date);
                try {
                    PartnerSaleDayDO bean = new PartnerSaleDayDO(partner, day);
                    initReport(bean);
                    partnerSaleDayMongoDao.save(bean);
//					logger.info("买手日销售数据统计...partner: "+ partner.getId() + " 日期:" + day);
                } catch (Exception e) {
                    logger.error("买手日销售数据统计失败...partner: " + partner.getId() + " 日期:" + day, e);
                }
            }
        });
    }

    /**
     * 买手月销售数据统计
     */
    public void buildPartnerMonthPool(Partner partner, Date date) {
        MyExecutors.limilPool().execute(new Runnable() {
            @Override
            public void run() {
                String month = DateUt.month2str(date);
                try {
                    PartnerSaleMonthDO bean = new PartnerSaleMonthDO(partner, month);
                    initReport(bean);
                    partnerSaleMonthMongoDao.save(bean);
//					logger.info("买手月销售统计...partner: "+ partner.getId() + " 月份:" + month);
                } catch (Exception e) {
                    logger.error("买手月销售数据统计失败...partner: " + partner.getId() + " 月份:" + month, e);
                }
            }
        });
    }

}
