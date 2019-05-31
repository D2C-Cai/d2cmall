package com.d2c.flame.controller;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.frame.web.control.BaseControl;
import com.d2c.order.dto.O2oSubscribeDto;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.query.O2oSubscribeSearcher;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.O2oSubscribeService;
import com.d2c.order.service.OrderQueryService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/report/order")
public class OrderController extends BaseControl {

    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private O2oSubscribeService o2oSubscribeService;
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
        result.setData(redisHandler.get("report_order"));
        return result;
    }

    /**
     * 今日订单
     *
     * @param cookie
     * @param page
     * @return
     */
    @RequestMapping(value = "/today/list", method = RequestMethod.GET)
    public ResponseResult waitList(PageModel page) {
        ResponseResult result = new ResponseResult();
        Date today = new Date();
        Date startDate = DateUtil.getStartOfDay(today);
        Date endDate = DateUtil.getEndOfDay(today);
        OrderSearcher searcher = new OrderSearcher();
        searcher.setStartDate(startDate);
        searcher.setEndDate(endDate);
        searcher.setOrderStatus(new OrderStatus[]{OrderStatus.WaitingForDelivery, OrderStatus.Delivered});
        PageResult<OrderDto> pager = orderQueryService.findBySearcher(searcher, page);
        result.putPage("pager", pager);
        return result;
    }

    /**
     * 待发货订单
     *
     * @param cookie
     * @param page
     * @return
     */
    @RequestMapping(value = "/wait/list", method = RequestMethod.GET)
    public ResponseResult itemList(PageModel page) {
        ResponseResult result = new ResponseResult();
        OrderSearcher searcher = new OrderSearcher();
        searcher.setOrderStatus(new OrderStatus[]{OrderStatus.WaitingForDelivery});
        PageResult<OrderDto> pager = orderQueryService.findBySearcher(searcher, page);
        result.putPage("pager", pager);
        return result;
    }

    /**
     * 今日预约单
     *
     * @param cookie
     * @param page
     * @return
     */
    @RequestMapping(value = "/o2o/list", method = RequestMethod.GET)
    public ResponseResult o2oList(PageModel page) {
        ResponseResult result = new ResponseResult();
        Date today = new Date();
        Date startDate = DateUtil.getStartOfDay(today);
        Date endDate = DateUtil.getEndOfDay(today);
        O2oSubscribeSearcher searcher = new O2oSubscribeSearcher();
        searcher.setGtStatus(0);
        searcher.setStartDate(startDate);
        searcher.setEndDate(endDate);
        PageResult<O2oSubscribeDto> pager = o2oSubscribeService.findBySearch(searcher, page);
        result.putPage("pager", pager);
        return result;
    }

}
