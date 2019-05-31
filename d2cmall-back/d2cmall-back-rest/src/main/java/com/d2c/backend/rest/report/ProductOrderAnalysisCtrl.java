package com.d2c.backend.rest.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.report.model.ProductOrderAnalysis;
import com.d2c.report.query.ProductOrderAnalysisSearcher;
import com.d2c.report.service.ProductOrderAnalysisService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/report/productOrderAnalysis")
public class ProductOrderAnalysisCtrl extends BaseCtrl<ProductOrderAnalysisSearcher> {

    @Reference
    private ProductOrderAnalysisService productOrderAnalysisService;

    @Override
    protected Response doList(ProductOrderAnalysisSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductOrderAnalysis> pager = productOrderAnalysisService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(ProductOrderAnalysisSearcher searcher) {
        BeanUt.trimString(searcher);
        return productOrderAnalysisService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "productOrderAnalysis";
    }

    @Override
    protected List<Map<String, Object>> getRow(ProductOrderAnalysisSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        List<Map<String, Object>> list = productOrderAnalysisService.findExport(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> map = null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        for (Map<String, Object> item : list) {
            map = new HashMap<>();
            map.put("时间", item.get("orderDate") == null ? "" : sf.format(item.get("orderDate")));
            map.put("品牌", item.get("brand"));
            map.put("商品名称", item.get("productName"));
            map.put("商品款号", item.get("productSn"));
            Integer saleQuatity = new BigDecimal(item.get("saleQuantity").toString()).intValue();
            Integer deliverQuatity = new BigDecimal(item.get("deliverQuantity").toString()).intValue();
            Integer closeQuantity = new BigDecimal(item.get("closeQuantity").toString()).intValue();
            map.put("销售数量", saleQuatity);
            map.put("发货数量", deliverQuatity);
            map.put("订单关闭商品数量", closeQuantity);
            map.put("未发货数量", saleQuatity - deliverQuatity);
            map.put("运营小组", item.get("operation"));
            rowList.add(map);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "订单商品日统计报表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"时间", "品牌", "商品名称", "商品款号", "销售数量", "发货数量", "订单关闭商品数量", "运营小组"};
    }

    @Override
    protected Response doHelp(ProductOrderAnalysisSearcher searcher, PageModel page) {
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
