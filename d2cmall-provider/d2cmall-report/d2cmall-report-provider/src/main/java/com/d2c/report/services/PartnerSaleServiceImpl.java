package com.d2c.report.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.cache.redis.annotation.CacheMethod;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.base.bucket.PageBucket;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import com.d2c.report.mongo.dao.PartnerSaleMongoDao;
import com.d2c.report.mongo.dto.SaleStatDTO;
import com.d2c.report.mongo.model.PartnerSaleDO;
import com.d2c.report.query.base.BasePartnerQuery;
import com.d2c.report.services.base.PartnerSaleBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 买手销售数据
 *
 * @author wull
 */
@Service(protocol = "dubbo")
public class PartnerSaleServiceImpl extends PartnerSaleBaseServiceImpl implements PartnerSaleService {

    @Reference
    protected PartnerService partnerService;
    @Autowired
    private PartnerSaleMongoDao partnerSaleMongoDao;

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
     * 搜索买手统计列表
     */
    public PageResult<PartnerSaleDO> findPageQuery(BasePartnerQuery mongoQuery, PageModel pager) {
        List<PartnerSaleDO> list = partnerSaleMongoDao.findPartnerSaleList(mongoQuery, pager);
        long count = partnerSaleMongoDao.countPartnerSale(mongoQuery);
        return new PageResult<>(pager, toView(list), count);
    }

    public PageResult<PartnerSaleDO> findPageBack(BasePartnerQuery mongoQuery, PageModel pager) {
        List<PartnerSaleDO> list = partnerSaleMongoDao.findPartnerSaleList(mongoQuery, pager);
        long count = partnerSaleMongoDao.countPartnerSale(mongoQuery);
        return new PageResult<>(pager, list, count);
    }

    @CacheMethod
    public PartnerSaleDO findPartnerSaleById(Long partnerId) {
        PartnerSaleDO bean = partnerSaleMongoDao.findById(partnerId);
        if (bean == null) {
            bean = buildReportImpl(partnerService.findById(partnerId));
        }
        return toView(bean);
    }
    //**************** PartnerSaleDay ********************

    /**
     * 创建买手统计数据
     * <p>定时任务-每天
     */
    public Integer buildReport() {
        PageBucket<Partner> bucket = getPartnerBucket();
        bucket.forEach(partner -> {
            buildReportPool(partner);
        });
        logger.info("买手定时数据更新... 共 " + bucket.getCount() + " 条");
        return bucket.getCount();
    }

    /**
     * 创建单条买手统计数据
     */
    public void buildReportPool(Partner partner) {
        MyExecutors.limilPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    buildReportImpl(partner);
//					logger.info("买手销售数据统计...partner: "+ partner.getId());
                } catch (Exception e) {
                    logger.error("买手销售数据统计失败...partner: " + partner.getId(), e);
                }
            }
        });
    }

    /**
     * 创建单条买手统计数据
     */
    public PartnerSaleDO buildReportImpl(Partner partner) {
        if (partner == null) return null;
        PartnerSaleDO bean = new PartnerSaleDO(partner);
        initReport(bean);
        bean.setWeekSaleStat(getWeekSaleStat("partner_id", partner.getId()));
        bean.setWeekSaleStatDM(getWeekSaleStat("parent_id", partner.getId()));
        bean.initSale();
        return partnerSaleMongoDao.save(bean);
    }

    private SaleStatDTO getWeekSaleStat(String fieldName, Long partnerId) {
        return findSaleStat(fieldName, partnerId, DateUt.dayBack(7), null);
    }

}
