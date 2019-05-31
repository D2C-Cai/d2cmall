package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.mongo.model.MemberTakeDO;
import com.d2c.member.mongo.model.MemberTakeDO.MarkType;
import com.d2c.member.mongo.services.MemberTakeService;
import com.d2c.order.model.Order;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.OrderQueryService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/membertake")
public class MemberTakeController extends BaseController {

    @Reference
    private MemberTakeService memberTakeService;
    @Autowired
    private OrderQueryService orderService;
    private Date beginDate = DateUtil.str2second("2018-11-10 20:00:00");
    private Date spitDate1 = DateUtil.str2second("2018-11-11 10:00:00");
    private Date spitDate2 = DateUtil.str2second("2018-11-11 16:00:00");
    private Date endDate = DateUtil.str2second("2018-11-12 00:00:00");
    private Date drawDate = DateUtil.str2second("2018-11-12 12:00:00");
    private BigDecimal fivek = new BigDecimal(5000);
    // private Date beginDate = DateUtil.str2second("2018-11-10 17:10:00");
    // private Date spitDate1 = DateUtil.str2second("2018-11-10 17:15:00");
    // private Date spitDate2 = DateUtil.str2second("2018-11-10 17:20:00");
    // private Date endDate = DateUtil.str2second("2018-11-10 17:38:00");
    // private Date drawDate = DateUtil.str2second("2018-11-10 17:38:00");
    // private BigDecimal fivek = new BigDecimal(5000);

    /**
     * 我的双11购物奖励
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/payment/award/1111"}, method = RequestMethod.GET)
    public String detail(ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderSearcher searcher = new OrderSearcher();
        searcher.setStartPayDate(beginDate);
        searcher.setEndPayDate(endDate);
        searcher.setMemberId(memberInfo.getId());
        List<Order> list = orderService.findSimpleBySearch(searcher, new PageModel(1, PageModel.MAX_PAGE_SIZE));
        BigDecimal amount1 = new BigDecimal(0);
        BigDecimal amount2 = new BigDecimal(0);
        BigDecimal amount3 = new BigDecimal(0);
        for (Order o : list) {
            if (o.getPaymentTime().compareTo(beginDate) > 0 && o.getPaymentTime().compareTo(spitDate1) < 0) {
                amount1 = amount1.add(o.getPaidAmount());
            } else if (o.getPaymentTime().compareTo(spitDate1) > 0 && o.getPaymentTime().compareTo(spitDate2) < 0) {
                amount2 = amount2.add(o.getPaidAmount());
            } else if (o.getPaymentTime().compareTo(spitDate2) > 0 && o.getPaymentTime().compareTo(endDate) < 0) {
                amount3 = amount3.add(o.getPaidAmount());
            }
        }
        List<MemberTakeDO> memberTakeDOs = memberTakeService.findByMemberIdAndType(memberInfo.getId(),
                MarkType.AWARD181111.name());
        Set<String> sets = new HashSet<>();
        memberTakeDOs.forEach(m -> sets.add(m.getMark()));
        Date now = new Date();
        model.put("amount1", amount1);
        model.put("amount2", amount2);
        model.put("amount3", amount3);
        model.put("mark1", !sets.contains("1") && amount1.compareTo(fivek) > 0 ? 1 : 0);
        model.put("mark2", !sets.contains("2") && amount2.compareTo(fivek) > 0 ? 1 : 0);
        model.put("mark3", !sets.contains("3") && amount3.compareTo(fivek) > 0 ? 1 : 0);
        model.put("status1", now.before(beginDate) ? 0 : (now.after(spitDate1) ? 2 : 1));
        model.put("status2", now.before(spitDate1) ? 0 : (now.after(spitDate2) ? 2 : 1));
        model.put("status3", now.before(spitDate2) ? 0 : (now.after(endDate) ? 2 : 1));
        model.put("status", now.after(drawDate) ? 1 : 0);
        model.put("drawDate", drawDate);
        return "cms/1111_award";
    }

    /**
     * 抽取双11购物奖励
     *
     * @param model
     * @param mark
     * @return
     */
    @RequestMapping(value = "/payment/award/1111/insert", method = RequestMethod.POST)
    public String insert(ModelMap model, Integer mark) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (mark == null || mark < 0 || mark > 3) {
            throw new BusinessException("参数异常");
        }
        Date now = new Date();
        if (now.after(drawDate)) {
            throw new BusinessException("该活动已结束");
        }
        OrderSearcher searcher = new OrderSearcher();
        searcher.setMemberId(memberInfo.getId());
        if (mark == 1) {
            searcher.setStartPayDate(beginDate);
            searcher.setEndPayDate(spitDate1);
        } else if (mark == 2) {
            searcher.setStartPayDate(spitDate1);
            searcher.setEndPayDate(spitDate2);
        } else if (mark == 3) {
            searcher.setStartPayDate(spitDate2);
            searcher.setEndPayDate(endDate);
        }
        List<Order> list = orderService.findSimpleBySearch(searcher, new PageModel(1, PageModel.MAX_PAGE_SIZE));
        BigDecimal amount = new BigDecimal(0);
        for (Order o : list) {
            amount = amount.add(o.getPaidAmount());
        }
        if (amount.compareTo(fivek) < 0) {
            throw new BusinessException("您实付金额不足5000，不能抽奖");
        }
        MemberTakeDO memberTakeDO = new MemberTakeDO(memberInfo, MarkType.AWARD181111, String.valueOf(mark));
        memberTakeService.insert(memberTakeDO);
        return "";
    }

}
