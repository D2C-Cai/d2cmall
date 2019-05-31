package com.d2c.backend.rest.report;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.query.MemberSearcher;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.order.service.OrderReportService;
import com.d2c.report.dto.WeeklyReportDto;
import com.d2c.report.query.WeeklyReportSearcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/report/weeklyreport")
public class WeeklyReportCtrl extends BaseCtrl<WeeklyReportSearcher> {

    @Resource
    private OrderReportService orderReportService;
    @Resource
    private MemberInfoService memberInfoService;
    @Resource
    private MemberService memberService;

    @Override
    protected Response doList(WeeklyReportSearcher searcher, PageModel page) {
        SuccessResponse result = new SuccessResponse(page);
        WeeklyReportDto dto = getData(searcher);
        result.put("weeklyReport", dto);
        return result;
    }

    private WeeklyReportDto getData(WeeklyReportSearcher searcher) {
        int buyerCount = orderReportService.countBuyerCount(searcher.getBegainDate(), searcher.getEndDate());
        BigDecimal salesAmount = orderReportService.findSalesAmount(searcher.getBegainDate(), searcher.getEndDate());
        int rePurchaseBuyerCount = orderReportService.countRebuyBuyerCount(searcher.getBegainDate(),
                searcher.getEndDate());
        BigDecimal rePurchaseSalesAmount = orderReportService.findRebuySalesAmount(searcher.getBegainDate(),
                searcher.getEndDate());
        int oldCustomerFirstOrderBuyerCount = orderReportService.countOldCustomerBuyerCount(searcher.getBegainDate(),
                searcher.getEndDate());
        BigDecimal oldCustomerFirstOrderSalesAmount = orderReportService
                .findOldCustomerSalesAmount(searcher.getBegainDate(), searcher.getEndDate());
        int newCustomerFirstOrderBuyerCount = orderReportService.countNewCustomerBuyerCount(searcher.getBegainDate(),
                searcher.getEndDate());
        BigDecimal newCustomerFirstOrderSalesAmount = orderReportService
                .findNewCustomerSalesAmount(searcher.getBegainDate(), searcher.getEndDate());
        int totalCount = memberInfoService.countRegistered(searcher.getBegainDate(), searcher.getEndDate());
        MemberSearcher memberSearcher = new MemberSearcher();
        memberSearcher.setD2c(0);
        memberSearcher.setStartDate(searcher.getBegainDate());
        memberSearcher.setEndDate(searcher.getEndDate());
        int totalTourist = memberService.countBySeach(memberSearcher);
        BigDecimal partnerBySelfAmount = orderReportService.findPartnerAmount(searcher.getBegainDate(),
                searcher.getEndDate(), 0);
        BigDecimal partnerForOtherAmount = orderReportService.findPartnerAmount(searcher.getBegainDate(),
                searcher.getEndDate(), 1);
        BigDecimal ordinaryAmount = orderReportService.findOrdinaryAmount(searcher.getBegainDate(),
                searcher.getEndDate());
        int newPartnerCount = orderReportService.findPartnerCount(searcher.getBegainDate(), searcher.getEndDate(), 0);
        int oldPartnerCount = orderReportService.findPartnerCount(searcher.getBegainDate(), searcher.getEndDate(), 1);
        int newOrdinaryCount = orderReportService.findPartnerCount(searcher.getBegainDate(), searcher.getEndDate(), 3);
        int oldOrdinaryCount = orderReportService.findPartnerCount(searcher.getBegainDate(), searcher.getEndDate(), 4);
        WeeklyReportDto dto = new WeeklyReportDto();
        // 用户下单
        dto.setOldMemberCount(rePurchaseBuyerCount);
        dto.setOldMemberAmount(rePurchaseSalesAmount);
        dto.setNewMemberCount(newCustomerFirstOrderBuyerCount);
        dto.setNewMemberAmount(newCustomerFirstOrderSalesAmount);
        dto.setOldMemberFirstCount(oldCustomerFirstOrderBuyerCount);
        dto.setOldMemberFirstAmount(oldCustomerFirstOrderSalesAmount);
        dto.setTotalCount(buyerCount);
        dto.setTotalAmount(salesAmount);
        dto.setTotalRegister(totalCount);
        dto.setTotalTourist(totalTourist);
        // 分销金额
        dto.setPartnerBySelfAmount(partnerBySelfAmount);
        dto.setPartnerForOtherAmount(partnerForOtherAmount);
        dto.setOrdinaryAmount(ordinaryAmount);
        // 分销用户
        dto.setNewPartnerCount(newPartnerCount);
        dto.setOldPartnerCount(oldPartnerCount);
        dto.setNewOrdinaryCount(newOrdinaryCount);
        dto.setOldOrdinaryCount(oldOrdinaryCount);
        return dto;
    }

    @Override
    protected int count(WeeklyReportSearcher searcher) {
        return 0;
    }

    @Override
    protected String getExportFileType() {
        return "weeklyreport";
    }

    @Override
    protected List<Map<String, Object>> getRow(WeeklyReportSearcher searcher, PageModel page) {
        WeeklyReportDto dto = getData(searcher);
        Map<String, Object> map = new HashMap<>();
        map.put("老客复购人数", dto.getOldMemberCount());
        map.put("老客复购金额", dto.getOldMemberAmount());
        map.put("新客首购人数", dto.getNewMemberCount());
        map.put("新客首购金额", dto.getNewMemberAmount());
        map.put("老客首购人数", dto.getOldMemberFirstCount());
        map.put("老客首购金额", dto.getOldMemberAmount());
        map.put("总购买人数", dto.getTotalCount());
        map.put("总购买金额", dto.getTotalAmount());
        map.put("会员", dto.getTotalRegister());
        map.put("游客", dto.getTotalTourist());
        map.put("分销自购金额", dto.getPartnerBySelfAmount());
        map.put("分销他购金额", dto.getPartnerForOtherAmount());
        map.put("普通订单金额", dto.getOrdinaryAmount());
        map.put("小程序新分销用户购买人数", dto.getNewPartnerCount());
        map.put("小程序新普通用户购买人数", dto.getNewOrdinaryCount());
        map.put("小程序老分销用户购买人数", dto.getOldPartnerCount());
        map.put("小程序老普通用户购买人数", dto.getOldOrdinaryCount());
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        return list;
    }

    @Override
    protected String getFileName() {
        return "周报";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"老客复购人数", "老客复购金额", "新客首购人数", "新客首购金额", "老客首购人数", "老客首购金额", "总购买人数", "总购买金额", "会员", "游客",
                "分销自购金额", "分销他购金额", "普通订单金额", "小程序新分销用户购买人数", "小程序新普通用户购买人数", "小程序老分销用户购买人数", "小程序老普通用户购买人数"};
    }

    @Override
    protected Response doHelp(WeeklyReportSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
