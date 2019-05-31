package com.d2c.backend.rest.report;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.query.DesignerOrderSumSearcher;
import com.d2c.order.service.OrderReportService;
import com.d2c.order.service.ReshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/shop/ordersum")
public class OrderSumDesignerCtrl extends BaseCtrl<DesignerOrderSumSearcher> {

    @Autowired
    private OrderReportService orderReportService;
    @Autowired
    private ReshipService reshipService;

    @Override
    protected Response doList(DesignerOrderSumSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Map<String, Object>> pager = orderReportService.findRankingByDesigner(page, searcher);
        for (Map<String, Object> map : pager.getList()) {
            Map<String, Object> reships = reshipService
                    .findAmountByDesigner(Long.parseLong(map.get("designerId").toString()));
            map.putAll(reships);
        }
        SuccessResponse result = new SuccessResponse(pager);
        result.put("searcher", searcher);
        return result;
    }

    @Override
    protected int count(DesignerOrderSumSearcher searcher) {
        int count = orderReportService.countRankingByDesigner(searcher);
        return count;
    }

    @Override
    protected String getExportFileType() {
        return "DesignerOrderSum";
    }

    @Override
    protected List<Map<String, Object>> getRow(DesignerOrderSumSearcher searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = orderReportService.findRankingByDesigner(page, searcher);
        for (Map<String, Object> map : pager.getList()) {
            Map<String, Object> reships = reshipService
                    .findAmountByDesigner(Long.parseLong(map.get("designerId").toString()));
            map.putAll(reships);
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> designerBrand : pager.getList()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("品牌名称", designerBrand.get("name"));
            map.put("设计师编号", designerBrand.get("designerCode"));
            map.put("销售数量", designerBrand.get("orderCount"));
            map.put("销售金额", designerBrand.get("price"));
            map.put("退款退货金额", designerBrand.get("reshipAmount"));
            map.put("退款退货件数", designerBrand.get("reshipCount"));
            map.put("运营小组", designerBrand.get("operation"));
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "设计师实时成交订单数";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"品牌名称", "设计师编号", "销售数量", "销售金额", "退款退货金额", "退款退货件数", "运营小组"};
    }

    @Override
    protected Response doHelp(DesignerOrderSumSearcher searcher, PageModel page) {
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
