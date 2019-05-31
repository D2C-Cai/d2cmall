package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.order.service.OrderReportService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.report.model.SalesAmountAnalysis;
import com.d2c.report.service.SalesAmountAnalysisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReportOrderTask extends BaseTask {

    private static Calendar cal = Calendar.getInstance();
    @Autowired
    private OrderReportService orderReportService;
    @Reference
    private SalesAmountAnalysisService salesAmountAnalysisService;

    @Scheduled(cron = "0 30 5 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.process();
    }

    private void process() {
        SalesAmountAnalysis current = new SalesAmountAnalysis();
        cal.setTimeInMillis(System.currentTimeMillis());
        int currentDay = cal.get(Calendar.DAY_OF_YEAR);
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentSeason = monthAtSeason(currentMonth);
        int currentYear = cal.get(Calendar.YEAR);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
        SalesAmountAnalysis last = salesAmountAnalysisService.findByLastOne();
        int lastDay = 0, lastWeek = 0, lastMonth = 0, lastSeason = 0, lastYear = 0;
        if (last != null) {
            BeanUtils.copyProperties(last, current, new String[]{"id", "createDate", "modifyDate"});
            cal.setTime(last.getCreateDate());
            lastDay = cal.get(Calendar.DAY_OF_YEAR);
            lastMonth = cal.get(Calendar.MONTH) + 1;
            lastSeason = monthAtSeason(currentMonth);
            lastYear = cal.get(Calendar.YEAR);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            lastWeek = cal.get(Calendar.WEEK_OF_YEAR);
        }
        if (currentDay != lastDay) {
            processYesterDay(current);
            if (currentWeek != lastWeek) {
                processLastWeek(current);
            }
            if (currentMonth != lastMonth) {
                processLastMonth(current);
            }
            if (currentSeason != lastSeason) {
                processLastSeason(current);
            }
            if (currentYear != lastYear) {
                processLastYear(current);
            }
            salesAmountAnalysisService.insert(current);
        }
    }

    private void processYesterDay(SalesAmountAnalysis current) {
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        Date endDate = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -1);// 昨天
        Date beginDate = cal.getTime();
        Map<String, BigDecimal> result = doStatistics(beginDate, endDate);
        current.setYesterdayBuyerCount(result.get("buyerCount").intValue());
        current.setYesterdaySalesAmount(result.get("salesAmount"));
        current.setYesterdayRebuyBuyerCount(result.get("rePurchaseBuyerCount").intValue());
        current.setYesterdayRebuySalesAmount(result.get("rePurchaseSalesAmount"));
        current.setYesterdayOldCustomerBuyerCount(result.get("oldCustomerFirstOrderBuyerCount").intValue());
        current.setYesterdayOldCustomerSalesAmount(result.get("oldCustomerFirstOrderSalesAmount"));
        current.setYesterdayNewCustomerBuyerCount(result.get("newCustomerFirstOrderBuyerCount").intValue());
        current.setYesterdayNewCustomerSalesAmount(result.get("newCustomerFirstOrderSalesAmount"));
    }

    private void processLastWeek(SalesAmountAnalysis current) {
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        Date endDate = cal.getTime();
        cal.add(Calendar.WEEK_OF_YEAR, -1);// 上星期
        Date beginDate = cal.getTime();
        Map<String, BigDecimal> result = doStatistics(beginDate, endDate);
        current.setLastWeekBuyerCount(result.get("buyerCount").intValue());
        current.setLastWeekSalesAmount(result.get("salesAmount"));
        current.setLastWeekRebuyBuyerCount(result.get("rePurchaseBuyerCount").intValue());
        current.setLastWeekRebuySalesAmount(result.get("rePurchaseSalesAmount"));
        current.setLastWeekOldCustomerBuyerCount(result.get("oldCustomerFirstOrderBuyerCount").intValue());
        current.setLastWeekOldCustomerSalesAmount(result.get("oldCustomerFirstOrderSalesAmount"));
        current.setLastWeekNewCustomerBuyerCount(result.get("newCustomerFirstOrderBuyerCount").intValue());
        current.setLastWeekNewCustomerSalesAmount(result.get("newCustomerFirstOrderSalesAmount"));
    }

    private void processLastMonth(SalesAmountAnalysis current) {
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        Date endDate = cal.getTime();
        cal.add(Calendar.MONTH, -1);// 上个月
        Date beginDate = cal.getTime();
        Map<String, BigDecimal> result = doStatistics(beginDate, endDate);
        current.setLastMonthBuyerCount(result.get("buyerCount").intValue());
        current.setLastMonthSalesAmount(result.get("salesAmount"));
        current.setLastMonthRebuyBuyerCount(result.get("rePurchaseBuyerCount").intValue());
        current.setLastMonthRebuySalesAmount(result.get("rePurchaseSalesAmount"));
        current.setLastMonthOldCustomerBuyerCount(result.get("oldCustomerFirstOrderBuyerCount").intValue());
        current.setLastMonthOldCustomerSalesAmount(result.get("oldCustomerFirstOrderSalesAmount"));
        current.setLastMonthNewCustomerBuyerCount(result.get("newCustomerFirstOrderBuyerCount").intValue());
        current.setLastMonthNewCustomerSalesAmount(result.get("newCustomerFirstOrderSalesAmount"));
    }

    private void processLastSeason(SalesAmountAnalysis current) {
        cal.setTimeInMillis(System.currentTimeMillis());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        if (current.getLastSeasonBuyerCount() == null || month == 1 || month == 4 || month == 7 || month == 10) {
            cal.clear();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, (monthAtSeason(month) - 1) * 3);
            Date endDate = cal.getTime();
            cal.add(Calendar.MONTH, -3);// 上3个月
            Date beginDate = cal.getTime();
            Map<String, BigDecimal> result = doStatistics(beginDate, endDate);
            current.setLastSeasonBuyerCount(result.get("buyerCount").intValue());
            current.setLastSeasonSalesAmount(result.get("salesAmount"));
            current.setLastSeasonRebuyBuyerCount(result.get("rePurchaseBuyerCount").intValue());
            current.setLastSeasonRebuySalesAmount(result.get("rePurchaseSalesAmount"));
            current.setLastSeasonOldCustomerBuyerCount(result.get("oldCustomerFirstOrderBuyerCount").intValue());
            current.setLastSeasonOldCustomerSalesAmount(result.get("oldCustomerFirstOrderSalesAmount"));
            current.setLastSeasonNewCustomerBuyerCount(result.get("newCustomerFirstOrderBuyerCount").intValue());
            current.setLastSeasonNewCustomerSalesAmount(result.get("newCustomerFirstOrderSalesAmount"));
        }
    }

    private void processLastYear(SalesAmountAnalysis current) {
        cal.setTimeInMillis(System.currentTimeMillis());
        int year = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(Calendar.YEAR, year - 1);
        Date beginDate = cal.getTime();
        cal.set(Calendar.YEAR, year);
        Date endDate = cal.getTime();
        Map<String, BigDecimal> result = doStatistics(beginDate, endDate);
        current.setLastYearBuyerCount(result.get("buyerCount").intValue());
        current.setLastYearSalesAmount(result.get("salesAmount"));
        current.setLastYearRebuyBuyerCount(result.get("rePurchaseBuyerCount").intValue());
        current.setLastYearRebuySalesAmount(result.get("rePurchaseSalesAmount"));
        current.setLastYearOldCustomerBuyerCount(result.get("oldCustomerFirstOrderBuyerCount").intValue());
        current.setLastYearOldCustomerSalesAmount(result.get("oldCustomerFirstOrderSalesAmount"));
        current.setLastYearNewCustomerBuyerCount(result.get("newCustomerFirstOrderBuyerCount").intValue());
        current.setLastYearNewCustomerSalesAmount(result.get("newCustomerFirstOrderSalesAmount"));
    }

    private Map<String, BigDecimal> doStatistics(Date beginDate, Date endDate) {
        Map<String, BigDecimal> result = new HashMap<>();
        int buyerCount = orderReportService.countBuyerCount(beginDate, endDate);
        BigDecimal salesAmount = orderReportService.findSalesAmount(beginDate, endDate);
        int rePurchaseBuyerCount = orderReportService.countRebuyBuyerCount(beginDate, endDate);
        BigDecimal rePurchaseSalesAmount = orderReportService.findRebuySalesAmount(beginDate, endDate);
        int oldCustomerFirstOrderBuyerCount = orderReportService.countOldCustomerBuyerCount(beginDate, endDate);
        BigDecimal oldCustomerFirstOrderSalesAmount = orderReportService.findOldCustomerSalesAmount(beginDate, endDate);
        int newCustomerFirstOrderBuyerCount = orderReportService.countNewCustomerBuyerCount(beginDate, endDate);
        BigDecimal newCustomerFirstOrderSalesAmount = orderReportService.findNewCustomerSalesAmount(beginDate, endDate);
        result.put("buyerCount", new BigDecimal(buyerCount));
        result.put("salesAmount", salesAmount != null ? salesAmount : new BigDecimal(0));
        result.put("rePurchaseBuyerCount", new BigDecimal(rePurchaseBuyerCount));
        result.put("rePurchaseSalesAmount", rePurchaseSalesAmount != null ? rePurchaseSalesAmount : new BigDecimal(0));
        result.put("oldCustomerFirstOrderBuyerCount", new BigDecimal(oldCustomerFirstOrderBuyerCount));
        result.put("oldCustomerFirstOrderSalesAmount",
                oldCustomerFirstOrderSalesAmount != null ? oldCustomerFirstOrderSalesAmount : new BigDecimal(0));
        result.put("newCustomerFirstOrderBuyerCount", new BigDecimal(newCustomerFirstOrderBuyerCount));
        result.put("newCustomerFirstOrderSalesAmount",
                newCustomerFirstOrderSalesAmount != null ? newCustomerFirstOrderSalesAmount : new BigDecimal(0));
        return result;
    }

    private int monthAtSeason(int month) {
        switch (month) {
            case 1:
                return 1;
            case 2:
                return 1;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 2;
            case 6:
                return 2;
            case 7:
                return 3;
            case 8:
                return 3;
            case 9:
                return 3;
            case 10:
                return 4;
            case 11:
                return 4;
            case 12:
                return 4;
        }
        return 0;
    }

}
