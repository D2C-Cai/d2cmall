package com.d2c.backend.rest.report;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.order.query.ProductOrderSumSearcher;
import com.d2c.order.service.OrderItemService;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/ordersum")
public class OrderSumProductCtrl extends BaseCtrl<ProductOrderSumSearcher> {

    @Autowired
    private OrderItemService orderItemService;

    @Override
    protected Response doList(ProductOrderSumSearcher searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = orderItemService.findRankingByProduct(page, searcher);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(ProductOrderSumSearcher searcher) {
        return orderItemService.countRankingByProduct(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "ProductOrderSum";
    }

    @Override
    protected List<Map<String, Object>> getRow(ProductOrderSumSearcher searcher, PageModel page) {
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        PageResult<Map<String, Object>> pager = orderItemService.findRankingByProduct(page, searcher);
        for (Map<String, Object> map : pager.getList()) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("商品款号", map.get("productSn"));
            cellsMap.put("商品名称", map.get("productName"));
            cellsMap.put("条码", map.get("productSkuSn"));
            cellsMap.put("POP库存", map.get("popStore"));
            cellsMap.put("自营库存", map.get("store"));
            cellsMap.put("设计师编码", map.get("designerCode"));
            cellsMap.put("设计师名称", map.get("designerName"));
            cellsMap.put("总件数", map.get("totalCount"));
            cellsMap.put("总金额", map.get("totalAmount"));
            cellsMap.put("运营小组", map.get("operation"));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "商品消费排行表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"商品款号", "商品名称", "条码", "POP库存", "自营库存", "设计师编码", "设计师名称", "总件数", "总金额", "运营小组"};
    }

    @Override
    protected Response doHelp(ProductOrderSumSearcher searcher, PageModel page) {
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

    /**
     * 即时商品消费排行汇总查看
     *
     * @param request
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/rankingSummary", method = RequestMethod.POST)
    public Response summary(HttpServletRequest request, ProductOrderSumSearcher searcher, PageModel page) {
        if (!StringUtil.isEmpty(searcher.getCountsort())) {
            searcher.setOrderStr("totalCount " + searcher.getCountsort());
        }
        if (!StringUtil.isEmpty(searcher.getAmountsort())) {
            if (!StringUtil.isEmpty(searcher.getCountsort())) {
                searcher.setOrderStr(searcher.getOrderStr() + ",totalAmount " + searcher.getAmountsort());
            } else {
                searcher.setOrderStr("totalAmount " + searcher.getAmountsort());
            }
        }
        PageResult<Map<String, Object>> pager = orderItemService.findRankingSummaryByProduct(page, searcher);
        return new SuccessResponse(pager);
    }

}
