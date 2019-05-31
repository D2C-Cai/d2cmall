package com.d2c.flame.quartz;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.content.query.FeedBackSearcher;
import com.d2c.content.service.FeedBackService;
import com.d2c.member.model.Comment.CommentSource;
import com.d2c.member.query.CommentSearcher;
import com.d2c.member.query.MemberSearcher;
import com.d2c.member.query.PartnerSearcher;
import com.d2c.member.service.CommentService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.PartnerService;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.query.PartnerBillSearcher;
import com.d2c.order.service.OrderQueryService;
import com.d2c.order.service.PartnerBillService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HeaderTask {

    private final static String redis_key = "head_count";
    private static boolean header_executed = true;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerBillService partnerBillService;
    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;

    /**
     * 头部数据
     */
    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void execute() {
        try {
            if (header_executed) {
                header_executed = false;
                Map<String, Object> map = new HashMap<>();
                Date today = new Date();
                Date startDate = DateUtil.getStartOfDay(today);
                Date endDate = DateUtil.getEndOfDay(today);
                int orderCount = this.orderCount(startDate, endDate);
                int memberCount = this.memberCount(startDate, endDate);
                int commentCount = this.commentCount(startDate, endDate);
                int feedbackCount = this.feedBackCount(startDate, endDate);
                int partnerCount = this.partnerCount(startDate, endDate);
                int partnerBillCount = this.partnerBillCount(startDate, endDate);
                map.put("orderCount", orderCount);
                map.put("memberCount", memberCount);
                map.put("commentCount", commentCount);
                map.put("feedbackCount", feedbackCount);
                map.put("partnerCount", partnerCount);
                map.put("partnerBillCount", partnerBillCount);
                redisHandler.set(redis_key, map);
            }
        } finally {
            header_executed = true;
        }
    }

    // 今日付款订单数
    private int orderCount(Date startDate, Date endDate) {
        OrderSearcher os = new OrderSearcher();
        os.setStartDate(startDate);
        os.setEndDate(endDate);
        os.setOrderStatus(
                new OrderStatus[]{OrderStatus.WaitingForDelivery, OrderStatus.Delivered, OrderStatus.Success});
        int orderCount = orderQueryService.countSimpleBySearch(os);
        return orderCount;
    }

    // 今日注册会员数
    private int memberCount(Date startDate, Date endDate) {
        MemberSearcher ms = new MemberSearcher();
        ms.setStartDate(startDate);
        ms.setEndDate(endDate);
        int memberCount = memberInfoService.countBySearch(ms);
        return memberCount;
    }

    // 今日订单评价数
    private int commentCount(Date startDate, Date endDate) {
        CommentSearcher cs = new CommentSearcher();
        cs.setStartDate(startDate);
        cs.setEndDate(endDate);
        cs.setSource(CommentSource.ORDERITEM.toString());
        int commentCount = commentService.countBySearcher(cs);
        return commentCount;
    }

    // 今日投诉反馈数
    private int feedBackCount(Date startDate, Date endDate) {
        FeedBackSearcher fs = new FeedBackSearcher();
        fs.setStartCreateDate(startDate);
        fs.setEndCreateDate(endDate);
        int feedbackCount = feedBackService.countBySearcher(fs);
        return feedbackCount;
    }

    // 今日新增分销数
    private int partnerCount(Date startDate, Date endDate) {
        PartnerSearcher ps = new PartnerSearcher();
        ps.setCreateDateStart(startDate);
        ps.setCreateDateEnd(endDate);
        int partnerCount = partnerService.countBySearcher(ps);
        return partnerCount;
    }

    // 今日新增返利单数
    private int partnerBillCount(Date startDate, Date endDate) {
        PartnerBillSearcher bs = new PartnerBillSearcher();
        bs.setStartDate(startDate);
        bs.setEndDate(endDate);
        int partnerBillCount = partnerBillService.countBySearcher(bs);
        return partnerBillCount;
    }

}
