package com.d2c.report.services;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.report.mongo.model.PartnerSaleDayDO;
import com.d2c.report.mongo.model.PartnerSaleMonthDO;

import java.util.Date;
import java.util.List;

/**
 * 买手每日销售数据
 *
 * @author wull
 */
public interface PartnerSaleTimeService {

    /**
     * 搜索买手日统计列表
     */
    public PageResult<PartnerSaleDayDO> findPageDayQuery(MongoQuery mongoQuery, PageModel pager);

    public PageResult<PartnerSaleDayDO> findPageDayBack(MongoQuery mongoQuery, PageModel pager);

    /**
     * 搜索买手月统计列表
     */
    public PageResult<PartnerSaleMonthDO> findPageMonthQuery(MongoQuery mongoQuery, PageModel pager);

    public PageResult<PartnerSaleMonthDO> findPageMonthBack(MongoQuery mongoQuery, PageModel pager);

    /**
     * 销售概况 - 单天的数据
     */
    public PartnerSaleDayDO findPartnerDay(Long partnerId, Date date);

    /**
     * 销售概况 - 获取今天实时数据
     */
    public PartnerSaleDayDO findCurrentPartnerDay(Long partnerId);

    /**
     * 销售概况 - 合并多天数据
     */
    public PartnerSaleDayDO findPartnerDayMerge(Long partnerId, Date date);

    /**
     * 月销售数据列表，分页
     */
    public List<PartnerSaleMonthDO> findPagePartnerMonth(Long partnerId, PageModel pager);

    /**
     * 销售概况 - 本月数据
     */
    public PartnerSaleMonthDO findPartnerMonth(Long partnerId, Date date);

    /**
     * 销售概况 - 合并多月数据
     */
    public PartnerSaleMonthDO findPartnerMonthMerge(Long partnerId, Date date);

    /**
     * 定时任务-每天统计经销商数据
     */
    public void buildReportOnDay(Date date);

    /**
     * 查询该日期的日买手统计个数
     */
    public Long countPartnerDay(Date date);

}
 