package com.d2c.flame.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.utils.DateUt;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.report.mongo.model.PartnerSaleDO;
import com.d2c.report.mongo.model.PartnerSaleDayDO;
import com.d2c.report.mongo.model.PartnerSaleMonthDO;
import com.d2c.report.query.PartnerSaleMgQuery;
import com.d2c.report.services.PartnerSaleService;
import com.d2c.report.services.PartnerSaleTimeService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 买手数据分析表
 *
 * @author wull
 */
@RestController
@RequestMapping(value = "/v3/api/analyze/partner")
public class PartnerAnalyzeController extends BaseController {

    @Reference
    private PartnerSaleService partnerSaleService;
    @Reference
    private PartnerSaleTimeService partnerSaleTimeService;

    /**
     * 经销商数据
     * <p>
     * API: /v3/api/analyze/partner/{partnerId}
     */
    @ResponseBody
    @RequestMapping(value = "/{partnerId}", method = RequestMethod.GET)
    public Response findPartnerSale(@PathVariable Long partnerId) {
        PartnerSaleDO bean = partnerSaleService.findPartnerSaleById(partnerId);
        return ResultHandler.success(bean);
    }

    /**
     * 经销商团队数据列表
     * <p>
     * API: /v3/api/analyze/partner/list
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response findPartnerSaleQuery(PartnerSaleMgQuery query, PageModel pager) {
        PageResult<PartnerSaleDO> result = partnerSaleService.findPageQuery(query, pager);
        return ResultHandler.success(result);
    }

    /**
     * 销售概况 - 今天实时数据
     * <p>
     * API: /v3/api/analyze/partner/{partnerId}/today
     *
     * @body {@link PartnerSaleDayDO} 买手每日销售数据
     */
    @ResponseBody
    @RequestMapping(value = "/{partnerId}/today", method = RequestMethod.GET)
    public Response findCurrentPartnerDay(@PathVariable Long partnerId) {
        PartnerSaleDayDO bean = partnerSaleTimeService.findCurrentPartnerDay(partnerId);
        return ResultHandler.success(bean);
    }

    /**
     * 销售概况 - 单天的数据
     * <p>
     * API: /v3/api/analyze/partner/{partnerId}/day
     *
     * @param day 几天前的数据， 今天，昨天
     * @body {@link PartnerSaleDayDO} 买手每日销售数据
     */
    @ResponseBody
    @RequestMapping(value = "/{partnerId}/day", method = RequestMethod.GET)
    public Response findPartnerDay(@PathVariable Long partnerId, Integer day) {
        if (day == null)
            day = 0;
        PartnerSaleDayDO bean = partnerSaleTimeService.findPartnerDay(partnerId, DateUt.dayBack(day));
        return ResultHandler.success(bean);
    }

    /**
     * 销售概况 - 多天数据合并, 不包括今天
     * <p>
     * API: /v3/api/analyze/partner/{partnerId}/day/merge
     *
     * @param day 7日, 30 日, 全部 day为null
     * @body {@link PartnerSaleDayDO} 买手每日销售数据
     */
    @ResponseBody
    @RequestMapping(value = "/{partnerId}/day/merge", method = RequestMethod.GET)
    public Response findSaleDayMerge(@PathVariable Long partnerId, Integer day) {
        Date date = null;
        if (day != null) {
            date = DateUt.dayBack(day - 1);
        }
        PartnerSaleDayDO bean = partnerSaleTimeService.findPartnerDayMerge(partnerId, date);
        return ResultHandler.success(bean);
    }

    /**
     * 月销售数据列表
     * <p>
     * API: /v3/api/analyze/partner/{partnerId}/month/list
     *
     * @body {@link PartnerSaleMonthDO} 买手每月销售数据
     */
    @ResponseBody
    @RequestMapping(value = "/{partnerId}/month/list", method = RequestMethod.GET)
    public Response findPartnerMonthList(@PathVariable Long partnerId, PageModel pager) {
        List<PartnerSaleMonthDO> list = partnerSaleTimeService.findPagePartnerMonth(partnerId, pager);
        return ResultHandler.success(list);
    }

    /**
     * 销售概况 - 本月的数据
     * <p>
     * API: /v3/api/analyze/partner/{partnerId}/month
     *
     * @param day 几月前的数据， 本月， 上一月
     * @body {@link PartnerSaleMonthDO} 买手每月销售数据
     */
    @ResponseBody
    @RequestMapping(value = "/{partnerId}/month", method = RequestMethod.GET)
    public Response findPartnerMonth(@PathVariable Long partnerId, Integer month) {
        if (month == null)
            month = 0;
        PartnerSaleMonthDO bean = partnerSaleTimeService.findPartnerMonth(partnerId, DateUt.monthBack(month));
        return ResultHandler.success(bean);
    }

    /**
     * 销售概况 - 多月合并数据
     * <p>
     * API: /v3/api/analyze/partner/{partnerId}/month/merge
     *
     * @param month 1月， 3个月， 半年内数据, null为本月
     * @body {@link PartnerSaleMonthDO} 买手每月销售数据
     */
    @ResponseBody
    @RequestMapping(value = "/{partnerId}/month/merge", method = RequestMethod.GET)
    public Response findSaleMonthMerge(@PathVariable Long partnerId, Integer month) {
        if (month == null) {
            return findPartnerMonth(partnerId, 0);
        }
        PartnerSaleMonthDO bean = partnerSaleTimeService.findPartnerMonthMerge(partnerId, DateUt.monthBack(month));
        return ResultHandler.success(bean);
    }

}
