package com.d2c.backend.rest.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.report.mongo.model.PartnerReportDO;
import com.d2c.report.query.PartnerReportMgQuery;
import com.d2c.report.services.PartnerReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/analyze/partner/report")
public class PartnerReportCtrl extends SuperCtrl {

    @Reference
    private PartnerReportService partnerReportService;

    /**
     * 买手每日汇总统计报表
     * <p> API: /rest/analyze/partner/report/list
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Response findPartnerSaleQuery(PartnerReportMgQuery query, PageModel pager) {
        if (pager.getSort() == null) pager.setSort("statDate");
        PageResult<PartnerReportDO> result = partnerReportService.findPageQuery(query, pager);
        return ResultHandler.success(result);
    }

}
