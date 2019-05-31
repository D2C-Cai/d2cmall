package com.d2c.flame.controller.behavior;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.mongo.dto.EventStatDTO;
import com.d2c.behavior.mongo.dto.EventStatQueryDTO;
import com.d2c.behavior.mongo.dto.EventVisitorDTO;
import com.d2c.behavior.mongo.enums.EventEnum;
import com.d2c.behavior.services.EventQueryService;
import com.d2c.behavior.services.EventStatService;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.enums.DateType;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.core.model.KeyValue;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.frame.web.annotation.SignIgnore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 用户行为
 *
 * @author wull
 */
@RestController
@RequestMapping(value = "/v3/api/behavior/eventstat")
public class EventStatController extends BaseController {

    @Reference(timeout = 15000)
    private EventStatService eventStatService;
    @Reference(timeout = 15000)
    private EventQueryService eventQueryService;

    /**
     * 买手店访客人数查询
     * <p>API: /v3/api/behavior/eventstat/findPartnerUv/{partnerId}
     *
     * @body {@link EventStatQueryDTO} 事件统计查询条件
     */
    @ResponseBody
    @RequestMapping(value = "/findPartnerUv/{partnerId}", method = RequestMethod.GET)
    public Response findPartnerUv(@PathVariable Integer partnerId) {
        EventStatQueryDTO query = new EventStatQueryDTO(EventEnum.PARTNER_VISIT.toString(), "targetId", partnerId);
        EventStatDTO bean = eventStatService.findUvStat(query);
        return ResultHandler.success(bean);
    }

    /**
     * 今天，昨天，30天内事件点击人数
     * <p>API: /v3/api/behavior/eventstat/findUvStat
     *
     * @body {@link EventStatQueryDTO} 事件统计查询条件
     */
    @SignIgnore
    @ResponseBody
    @RequestMapping(value = "/findUvStat", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response findEventStat(@RequestBody EventStatQueryDTO query) {
        AssertUt.notEmpty(query.getEvent(), "事件名称不能为空");
        EventStatDTO bean = eventStatService.findUvStat(query);
        return ResultHandler.success(bean);
    }

    /**
     * 查询几天内的访客数据，默认30天
     * <p>API: /v3/api/behavior/eventstat/findVisitors/{partnerId}
     *
     * @param partnerId 买手店ID
     * @param PageModel page 分页
     */
    @ResponseBody
    @RequestMapping(value = "/findVisitors/{partnerId}", method = RequestMethod.GET)
    public Response findVisitors(@PathVariable Integer partnerId, PageModel page) {
        EventStatQueryDTO query = new EventStatQueryDTO(EventEnum.PARTNER_VISIT.toString(), "targetId", partnerId);
        List<EventVisitorDTO> list = eventStatService.findVisitors(query, page);
        return ResultHandler.success(list);
    }

    /**
     * 查询几天内的访客数据，默认30天
     * <p>API: /v3/api/behavior/eventstat/findVisitors
     *
     * @param PageModel page 分页
     * @body {@link EventStatQueryDTO} 事件统计查询条件
     */
    @SignIgnore
    @ResponseBody
    @RequestMapping(value = "/findVisitors", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response findVisitors(@RequestBody EventStatQueryDTO query, PageModel page) {
        AssertUt.notEmpty(query.getEvent(), "事件名称不能为空");
        List<EventVisitorDTO> list = eventStatService.findVisitors(query, page);
        return ResultHandler.success(list);
    }

    /**
     * 查询所有的n小时内的访客数量
     * <p>API: /v3/api/behavior/eventstat/countVisitors/hour/{hour}
     *
     * @param hour 小时
     */
    @SignIgnore
    @ResponseBody
    @RequestMapping(value = "/countVisitors/hour/{hour}", method = RequestMethod.GET)
    public Response countAllVistiors(@PathVariable Integer hour) {
        if (hour == null) hour = 2;
        Date startDate = DateUt.dateBack(DateType.HOUR, hour);
        List<KeyValue> list = eventQueryService.countVistiors(startDate);
        return ResultHandler.success(list);
    }

    /**
     * 查询用户事件图表
     * <p>API: /v3/api/behavior/eventstat/chart
     *
     * @body {@link EventStatQueryDTO} 事件统计查询条件
     */
    @SignIgnore
    @ResponseBody
    @RequestMapping(value = "/chart", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response findEventChart(@RequestBody EventStatQueryDTO query) {
        Object res = null;
        if (query.getEvent() == null) {
            res = eventStatService.findEventChartMap(query);
        } else {
            res = eventStatService.findEventChart(query);
        }
        return ResultHandler.success(res);
    }

}
