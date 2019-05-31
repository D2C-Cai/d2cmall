package com.d2c.flame.controller.behavior;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.behavior.services.EventQueryService;
import com.d2c.behavior.services.ProductHistoryService;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.page.base.PageSort;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.flame.controller.base.BaseSessionController;
import com.d2c.member.model.MemberInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户行为
 *
 * @author wull
 */
@RestController
@RequestMapping(value = "/v3/api/behavior/event/find")
public class EventQueryController extends BaseSessionController {

    @Reference(timeout = 10000)
    private EventQueryService eventQueryService;
    @Reference(timeout = 10000)
    private ProductHistoryService productHistoryService;

    /**
     * 我的足迹 - 用户浏览历史记录
     * <p>
     * API: /v3/api/behavior/event/find/product
     * <p>
     * 浏览日期分组，并且当日去重
     */
    @ResponseBody
    @RequestMapping(value = "/product")
    @SuppressWarnings("rawtypes")
    public ResponseResult findProductEventList(PageModel page) {
        MemberInfo member = getLoginMemberInfo();
        page.setPageSort(PageSort.build("eventTime|desc"));
        PageResult events = productHistoryService.findHistoryDays(member.getId(), page);
        return ResultHandler.successAppPage("products", events);
    }

    /**
     * 我的足迹 - 用户浏览历史记录总数
     * <p>
     * API: /v3/api/behavior/event/find/product/count
     * <p>
     * 缓存浏览数据
     */
    @ResponseBody
    @RequestMapping(value = "/product/count")
    public ResponseResult countProductEvent() {
        MemberInfo member = getLoginMemberInfo();
        long count = productHistoryService.countHistoryDays(member.getId());
        return ResultHandler.successApp("products", count);
    }

    /**
     * 我的足迹 - 删除用户足迹
     * <p>
     * API: /v3/api/behavior/event/find/product/delete/{historyId}
     */
    @ResponseBody
    @RequestMapping(value = "/product/delete/{historyId}")
    public ResponseResult deleteHistoryById(@PathVariable String historyId) {
        getLoginMemberInfo();
        productHistoryService.deleteHistoryById(historyId);
        return new ResponseResult();
    }

    /**
     * 我的足迹 - 删除用户所有足迹
     * <p>
     * API: /v3/api/behavior/event/find/product/delete/all
     */
    @ResponseBody
    @RequestMapping(value = "/product/delete/all")
    public ResponseResult deleteAllHistory(@PathVariable String historyId) {
        MemberInfo member = getLoginMemberInfo();
        productHistoryService.deleteAllHistory(member.getId());
        return new ResponseResult();
    }

}
