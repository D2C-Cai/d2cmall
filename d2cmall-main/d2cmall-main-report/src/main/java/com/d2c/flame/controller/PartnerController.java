package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.frame.web.control.BaseControl;
import com.d2c.report.mongo.model.PartnerSaleDayDO;
import com.d2c.report.mongo.model.PartnerSaleMonthDO;
import com.d2c.report.query.PartnerSaleDayMgQuery;
import com.d2c.report.query.PartnerSaleMonthMgQuery;
import com.d2c.report.services.PartnerSaleTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/report/partner")
public class PartnerController extends BaseControl {

    @Reference
    private PartnerSaleTimeService partnerSaleTimeService;
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;

    /**
     * 头部数据
     *
     * @param cookie
     * @return
     */
    @RequestMapping(value = "/head", method = RequestMethod.GET)
    public ResponseResult head() {
        ResponseResult result = new ResponseResult();
        result.setData(redisHandler.get("report_partner"));
        return result;
    }

    /**
     * 按日查询经销商团队数据列表
     * <p>
     * API: /report/partner/day/list
     */
    @ResponseBody
    @RequestMapping(value = "/day/list", method = RequestMethod.GET)
    public Response findPartnerSaleDayQuery(PartnerSaleDayMgQuery query, PageModel pager) {
        PageResult<PartnerSaleDayDO> result = partnerSaleTimeService.findPageDayQuery(query, pager);
        return ResultHandler.success(result);
    }

    /**
     * 按月查询经销商团队数据列表
     * <p>
     * API: /report/partner/month/list
     */
    @ResponseBody
    @RequestMapping(value = "/month/list", method = RequestMethod.GET)
    public Response findPartnerSaleQuery(PartnerSaleMonthMgQuery query, PageModel pager) {
        PageResult<PartnerSaleMonthDO> result = partnerSaleTimeService.findPageMonthQuery(query, pager);
        return ResultHandler.success(result);
    }

}
