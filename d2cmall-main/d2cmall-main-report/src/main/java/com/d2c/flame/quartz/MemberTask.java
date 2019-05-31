package com.d2c.flame.quartz;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.member.query.MemberSearcher;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class MemberTask {

    private final static String redis_key = "report_member";
    private static boolean member_executed = true;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;

    /**
     * 会员数据
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void execute() {
        try {
            if (member_executed) {
                member_executed = false;
                Map<String, Object> map = new HashMap<>();
                Date today = new Date();
                // 今日数据
                Date startDate = DateUtil.getStartOfDay(today);
                Date endDate = DateUtil.getEndOfDay(today);
                map.put("todayResult", this.getMemberResult(startDate, endDate));
                // 昨日数据
                Date yesterday = DateUtil.add(today, Calendar.DATE, -1);
                Date sYesterdayDate = DateUtil.getStartOfDay(yesterday);
                Date eYesterdayDate = DateUtil.getEndOfDay(yesterday);
                map.put("yesterdayResult", this.getMemberResult(sYesterdayDate, eYesterdayDate));
                // 本周数据
                Date sWeekDate = DateUtil.getStartOfWeek(today);
                Date eWeekDate = DateUtil.getEndOfWeek(today);
                sWeekDate = DateUtil.getStartOfDay(sWeekDate);
                eWeekDate = DateUtil.getEndOfDay(eWeekDate);
                map.put("thisWeekResult", this.getMemberResult(sWeekDate, eWeekDate));
                // 本月数据
                Date sMonthDate = DateUtil.getStartOfMonth(today);
                Date eMonthDate = DateUtil.getEndOfMonth(today);
                sMonthDate = DateUtil.getStartOfDay(sMonthDate);
                eMonthDate = DateUtil.getEndOfDay(eMonthDate);
                map.put("thisMonthResult", this.getMemberResult(sMonthDate, eMonthDate));
                // 数据总览
                MemberSearcher searcher = new MemberSearcher();
                map.put("memberAllResult", memberService.findCountGroupBySource(searcher));
                map.put("memberinfoAllResult", memberInfoService.findCountGroupByDevice(searcher));
                redisHandler.set(redis_key, map);
            }
        } finally {
            member_executed = true;
        }
    }

    /**
     * 会员表格数据详情
     *
     * @param date
     * @return
     */
    private List<Map<String, Object>> getMemberResult(Date startDate, Date endDate) {
        MemberSearcher searcher = new MemberSearcher();
        searcher.setStartDate(startDate);
        searcher.setEndDate(endDate);
        return memberInfoService.findCountGroupByDevice(searcher);
    }

}
