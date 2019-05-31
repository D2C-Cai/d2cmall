package com.d2c.flame.quartz;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.member.query.PartnerSearcher;
import com.d2c.member.service.PartnerService;
import com.d2c.order.service.PartnerBillService;
import com.d2c.order.service.PartnerCashService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PartnerTask {

    private final static String redis_key = "report_partner";
    private static boolean partner_executed = true;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerBillService partnerBillService;
    @Autowired
    private PartnerCashService partnerCashService;
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;

    /**
     * 分销数据
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void execute() {
        try {
            if (partner_executed) {
                partner_executed = false;
                Map<String, Object> map = new HashMap<>();
                // 销售概况
                List<Map<String, Object>> partnerBillResult = partnerBillService.findCountGroupByStatus(null, null);
                map.put("partnerBillResult", partnerBillResult);
                // 人员发展
                List<Map<String, Object>> partnerLevelResult = partnerService.findCountGroupByLevel(null, null);
                List<Map<String, Object>> partnerStatusResult = partnerService.findCountGroupByStatus();
                map.put("partnerLevelResult", partnerLevelResult);
                map.put("partnerStatusResult", partnerStatusResult);
                PartnerSearcher searcher = new PartnerSearcher();
                searcher.setConsume(0);
                int unConsumedCount = partnerService.countBySearcher(searcher);
                map.put("unConsumedCount", unConsumedCount);
                // 提现情况
                List<Map<String, Object>> partnerCashResult = partnerCashService.findCountGroupByStatus();
                map.put("partnerCashResult", partnerCashResult);
                Date today = new Date();
                // 今日数据
                Date startDate = DateUtil.getStartOfDay(today);
                Date endDate = DateUtil.getEndOfDay(today);
                map.put("todayResult", this.getPartnerResult(startDate, endDate));
                // 昨日数据
                Date yesterday = DateUtil.add(today, Calendar.DATE, -1);
                Date sYesterdayDate = DateUtil.getStartOfDay(yesterday);
                Date eYesterdayDate = DateUtil.getEndOfDay(yesterday);
                map.put("yesterdayResult", this.getPartnerResult(sYesterdayDate, eYesterdayDate));
                // 本周数据
                Date sWeekDate = DateUtil.getStartOfWeek(today);
                Date eWeekDate = DateUtil.getEndOfWeek(today);
                sWeekDate = DateUtil.getStartOfDay(sWeekDate);
                eWeekDate = DateUtil.getEndOfDay(eWeekDate);
                map.put("thisWeekResult", this.getPartnerResult(sWeekDate, eWeekDate));
                // 本月数据
                Date sMonthDate = DateUtil.getStartOfMonth(today);
                Date eMonthDate = DateUtil.getEndOfMonth(today);
                sMonthDate = DateUtil.getStartOfDay(sMonthDate);
                eMonthDate = DateUtil.getEndOfDay(eMonthDate);
                map.put("thisMonthResult", this.getPartnerResult(sMonthDate, eMonthDate));
                redisHandler.set(redis_key, map);
            }
        } finally {
            partner_executed = true;
        }
    }

    /**
     * 分销表格数据详情
     *
     * @param date
     * @return
     */
    private Map<String, Object> getPartnerResult(Date startDate, Date endDate) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> partnerBillStatusResult = partnerBillService.findCountGroupByStatus(startDate,
                endDate);
        map.put("partnerBillStatusResult", partnerBillStatusResult);
        List<Map<String, Object>> partnerBillLevelResult = partnerBillService.findCountGroupByLevel(startDate, endDate);
        map.put("partnerBillLevelResult", partnerBillLevelResult);
        List<Map<String, Object>> partnerLevelResult = partnerService.findCountGroupByLevel(startDate, endDate);
        map.put("partnerLevelResult", partnerLevelResult);
        return map;
    }

}
