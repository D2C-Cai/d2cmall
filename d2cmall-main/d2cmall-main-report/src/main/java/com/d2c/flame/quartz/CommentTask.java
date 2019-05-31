package com.d2c.flame.quartz;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.member.service.CommentService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentTask {

    private final static String redis_key = "report_comment";
    private static boolean comment_executed = true;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;

    /**
     * 评价数据
     */
    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void execute() {
        try {
            if (comment_executed) {
                comment_executed = false;
                Map<String, Object> map = new HashMap<>();
                Date today = new Date();
                // 本月数据
                Date sMonthDate = DateUtil.getStartOfMonth(today);
                Date eMonthDate = DateUtil.getEndOfMonth(today);
                sMonthDate = DateUtil.getStartOfDay(sMonthDate);
                eMonthDate = DateUtil.getEndOfDay(eMonthDate);
                // 商品质量评分
                map.put("productScoreResult",
                        commentService.findCountGroupByScore("product_score", sMonthDate, eMonthDate));
                // 商品包装评分
                map.put("packageScoreResult",
                        commentService.findCountGroupByScore("package_score", sMonthDate, eMonthDate));
                // 配送速度评分
                map.put("deliverySpeedScoreResult",
                        commentService.findCountGroupByScore("delivery_speed_score", sMonthDate, eMonthDate));
                // 物流服务评分
                map.put("deliveryServiceScoreResult",
                        commentService.findCountGroupByScore("delivery_service_score", sMonthDate, eMonthDate));
                redisHandler.set(redis_key, map);
            }
        } finally {
            comment_executed = true;
        }
    }

}
