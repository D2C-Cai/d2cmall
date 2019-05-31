package com.d2c.flame.quartz;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.OrderQueryService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class OrderTask {

    private final static String redis_key = "report_order";
    private static boolean order_executed = true;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;

    /**
     * 订单数据
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void execute() {
        try {
            if (order_executed) {
                order_executed = false;
                Map<String, Object> map = new HashMap<>();
                Date today = new Date();
                // 今日数据
                Date startDate = DateUtil.getStartOfDay(today);
                Date endDate = DateUtil.getEndOfDay(today);
                map.put("todayResult", this.getOrderResult(startDate, endDate));
                // 昨日数据
                Date yesterday = DateUtil.add(today, Calendar.DATE, -1);
                Date sYesterdayDate = DateUtil.getStartOfDay(yesterday);
                Date eYesterdayDate = DateUtil.getEndOfDay(yesterday);
                map.put("yesterdayResult", this.getOrderResult(sYesterdayDate, eYesterdayDate));
                // 本周数据
                Date sWeekDate = DateUtil.getStartOfWeek(today);
                Date eWeekDate = DateUtil.getEndOfWeek(today);
                sWeekDate = DateUtil.getStartOfDay(sWeekDate);
                eWeekDate = DateUtil.getEndOfDay(eWeekDate);
                map.put("thisWeekResult", this.getOrderResult(sWeekDate, eWeekDate));
                // 本月数据
                Date sMonthDate = DateUtil.getStartOfMonth(today);
                Date eMonthDate = DateUtil.getEndOfMonth(today);
                sMonthDate = DateUtil.getStartOfDay(sMonthDate);
                eMonthDate = DateUtil.getEndOfDay(eMonthDate);
                map.put("thisMonthResult", this.getOrderResult(sMonthDate, eMonthDate));
                redisHandler.set(redis_key, map);
            }
        } finally {
            order_executed = true;
        }
    }

    /**
     * 订单表格数据详情
     *
     * @param date
     * @return
     */
    private List<Map<String, Object>> getOrderResult(Date startDate, Date endDate) {
        OrderSearcher searcher = new OrderSearcher();
        searcher.setStartDate(startDate);
        searcher.setEndDate(endDate);
        return orderQueryService.findCountAndAmountGroupByDevice(searcher);
    }

}
