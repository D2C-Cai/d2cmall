package com.d2c.report.services;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.report.mongo.model.PartnerReportDO;

import java.util.Date;

/**
 * 买手销售数据
 *
 * @author wull
 */
public interface PartnerReportService {

    public PageResult<PartnerReportDO> findPageQuery(MongoQuery query, PageModel pager);

    public Object buildReportOnDay(Date date);

}
 