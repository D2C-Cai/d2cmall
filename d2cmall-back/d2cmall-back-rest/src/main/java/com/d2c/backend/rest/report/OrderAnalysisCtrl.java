package com.d2c.backend.rest.report;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.query.OrderAnalysisSearcher;
import com.d2c.order.service.OrderReportService;
import com.d2c.util.file.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/report/orderanalysis")
public class OrderAnalysisCtrl extends BaseCtrl<OrderAnalysisSearcher> {

    @Autowired
    private OrderReportService orderReportService;

    /**
     * 只按年月分
     */
    @Override
    protected Response doList(OrderAnalysisSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Map<String, Object>> pager = new PageResult<Map<String, Object>>(page);
        if (searcher != null && searcher.type != null && searcher.type == 3) {
            pager = orderReportService.findThirdPayList(searcher, page);
        } else {
            pager = orderReportService.findOrderAnalysis(searcher, page);
        }
        SuccessResponse result = new SuccessResponse(pager);
        result.put("searcher", searcher);
        return result;
    }

    @Override
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public Response export(OrderAnalysisSearcher searcher, PageModel pageModel) {
        SuccessResponse result = new SuccessResponse();
        try {
            result.setMsg("导出成功！");
            String fileName = this.getLoginedAdmin().getUsername() + "_" + getFileName();
            String[] titleNames = getExportTitles();
            if (searcher != null && searcher.type != null) {
                if (searcher.type == 2) {
                    fileName = this.getLoginedAdmin().getUsername() + "_" + getFileName1();
                    titleNames = getExportTitles1();
                } else if (searcher.type == 3) {
                    fileName = this.getLoginedAdmin().getUsername() + "_" + getFileName2();
                    titleNames = getExportTitles2();
                }
            }
            CSVUtil csvUtil = new CSVUtil();
            csvUtil.setFileName(fileName);
            csvUtil.writeTitleToFile(titleNames);
            // 一次导出500条，如果有的话
            PageResult<Object> pageResult = new PageResult<Object>(pageModel);
            pageModel.setPageSize(500);
            int pagerNumber = 1;
            int totalCount = count(searcher);
            pageResult.setTotalCount(totalCount);
            boolean exportSuccess = true;
            // 在服务器端生产excel文件
            do {
                pageModel.setPageNumber(pagerNumber);
                csvUtil.writeRowToFile(getRow(searcher, pageModel));
                pagerNumber = pagerNumber + 1;
            } while (pagerNumber <= pageResult.getPageCount() && exportSuccess);
            // 返回文件地址
            createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
            // 保存导出记录
            saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), this.getExportFileType());
            return result;
        } catch (Exception e) {
            result.setMessage("导出不成功！");
            result.setStatus(-1);
        }
        return result;
    }

    @Override
    protected int count(OrderAnalysisSearcher searcher) {
        return orderReportService.countOrderAnalysis(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "OrderAnalysis";
    }

    @Override
    protected List<Map<String, Object>> getRow(OrderAnalysisSearcher searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = new PageResult<Map<String, Object>>(page);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (searcher.type != null && searcher.type == 3) {
            pager = orderReportService.findThirdPayList(searcher, page);
            for (Map<String, Object> datas : pager.getList()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("年份", datas.get("year"));
                map.put("月份", datas.get("month"));
                map.put("设备", datas.get("device"));
                map.put("用户数量", datas.get("memberCount"));
                map.put("订单数量", datas.get("orderCount"));
                map.put("商品明细数量", datas.get("quantity"));
                map.put("订单金额", datas.get("amount"));
                map.put("退款数量", datas.get("refundCount"));
                map.put("退款金额", datas.get("refundAmount"));
                map.put("退款退货数量", datas.get("reshipCount"));
                map.put("退款退货金额", datas.get("reshipAmount"));
                map.put("换货数量", datas.get("exchangeCount"));
                map.put("换货金额", datas.get("exchangeAmount"));
                list.add(map);
            }
        } else {
            pager = orderReportService.findOrderAnalysis(searcher, page);
            for (Map<String, Object> datas : pager.getList()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("年份", datas.get("year"));
                map.put("月份", datas.get("month"));
                if (searcher.type != null && searcher.type == 1) {
                    map.put("设备", datas.get("device"));
                }
                map.put("用户数量", datas.get("memberCount"));
                map.put("订单数量", datas.get("orderCount"));
                map.put("商品明细数量", datas.get("quantity"));
                map.put("订单金额", datas.get("amount"));
                map.put("首单用户数量", datas.get("firstMemberCount"));
                map.put("首单用户订单数量", datas.get("firstOrderCount"));
                map.put("首单用户商品明细数量", datas.get("firstQuantity"));
                map.put("首单用户订单金额", datas.get("firstAmount"));
                map.put("复购用户数量", Integer.parseInt(datas.get("memberCount").toString())
                        - Integer.parseInt(datas.get("firstMemberCount").toString()));
                map.put("复购用户订单数量", Integer.parseInt(datas.get("orderCount").toString())
                        - Integer.parseInt(datas.get("firstOrderCount").toString()));
                map.put("复购用户商品明细数量", Integer.parseInt(datas.get("quantity").toString())
                        - Integer.parseInt(datas.get("firstQuantity").toString()));
                map.put("复购用户订单金额", new BigDecimal(datas.get("amount").toString())
                        .subtract(new BigDecimal(datas.get("firstAmount").toString())));
                list.add(map);
            }
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "用户下单按年月终端分析表";
    }

    private String getFileName1() {
        return "用户下单按年月分析表";
    }

    private String getFileName2() {
        return "第三方支付订单分析";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"年份", "月份", "设备", "用户数量", "订单数量", "商品明细数量", "订单金额", "首单用户数量", "首单用户订单数量", "首单用户商品明细数量",
                "首单用户订单金额", "复购用户数量", "复购用户订单数量", "复购用户商品明细数量", "复购用户订单金额"};
    }

    protected String[] getExportTitles1() {
        return new String[]{"年份", "月份", "用户数量", "订单数量", "商品明细数量", "订单金额", "首单用户数量", "首单用户订单数量", "首单用户商品明细数量",
                "首单用户订单金额", "复购用户数量", "复购用户订单数量", "复购用户商品明细数量", "复购用户订单金额"};
    }

    protected String[] getExportTitles2() {
        return new String[]{"年份", "月份", "设备", "用户数量", "订单数量", "商品明细数量", "订单金额", "退款数量", "退款金额", "退款退货数量", "退款退货金额",
                "换货数量", "换货金额"};
    }

    @Override
    protected Response doHelp(OrderAnalysisSearcher searcher, PageModel page) {
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
