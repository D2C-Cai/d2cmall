package com.d2c.backend.rest.report;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.order.query.OrderSaleSearcher;
import com.d2c.order.service.OrderQueryService;
import com.d2c.util.file.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/report/ordersale")
public class OrderSaleCtrl extends BaseCtrl<OrderSaleSearcher> {

    @Autowired
    private OrderQueryService orderQueryService;

    @Override
    protected Response doList(OrderSaleSearcher searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = orderQueryService.findSaleBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(OrderSaleSearcher searcher) {
        return orderQueryService.countSaleBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "orderSale";
    }

    @Override
    protected List<Map<String, Object>> getRow(OrderSaleSearcher searcher, PageModel page) {
        PageResult<Map<String, Object>> pager = orderQueryService.findSaleBySearcher(searcher, page);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> map : pager.getList()) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("商品Id", map.get("productId"));
            if (searcher.getType() == 2) {
                rowMap.put("条码", map.get("barCode"));
            }
            rowMap.put("款号", map.get("productSn"));
            rowMap.put("标题", map.get("productName"));
            rowMap.put("数量", map.get("quantity"));
            rowMap.put("金额", map.get("amount"));
            rowMap.put("支付人数", map.get("memberCount"));
            rowMap.put("订单量", map.get("orderCount"));
            if (searcher.getHasPic()) {
                try {
                    rowMap.put("图片", ImageIO.read(new URL("http://img.d2c.cn/" + map.get("img") + "!80")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            list.add(rowMap);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "商品销量表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"商品Id", "款号", "标题", "数量", "金额", "支付人数", "订单量", "图片"};
    }

    protected String[] getExportTitles2() {
        return new String[]{"商品Id", "条码", "款号", "标题", "数量", "金额", "支付人数", "订单量", "图片"};
    }

    @Override
    protected Response doHelp(OrderSaleSearcher searcher, PageModel page) {
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

    @RequestMapping(value = "/sale/export", method = RequestMethod.POST)
    public Response doExport(HttpServletRequest request, OrderSaleSearcher searcher, PageModel pageModel) {
        try {
            // 默认导出
            String[] titleNames = getExportTitles();
            List<Map<String, Object>> execelData = this.getExcelData(request);
            if (searcher.getType() == 0) {
                List<Long> productIds = new ArrayList<>();
                for (Map<String, Object> map : execelData) {
                    productIds.add(Long.parseLong(map.get("productId").toString()));
                }
                searcher.setProductId(productIds);
            } else {
                List<String> strList = new ArrayList<>();
                for (Map<String, Object> map : execelData) {
                    strList.add(map.get("productId").toString());
                }
                if (searcher.getType() == 1) {
                    searcher.setProductSn(strList);
                } else {
                    searcher.setBarCode(strList);
                    titleNames = getExportTitles2();
                }
            }
            SuccessResponse result = new SuccessResponse();
            result.setMsg("导出成功，请至下载中心下载！");
            String fileName = this.getLoginedAdmin().getUsername() + "_" + getFileName();
            ExcelUtil excelUtil = new ExcelUtil(fileName, this.getLoginedAdmin().getUsername());
            // 一次导出500条，如果有的话
            pageModel.setPageSize(500);
            PageResult<Object> pageResult = new PageResult<>(pageModel);
            int pagerNumber = 1;
            int totalCount = count(searcher);
            pageResult.setTotalCount(totalCount);
            boolean exportSuccess = true;
            // 在服务器端生产excel文件
            do {
                pageModel.setPageNumber(pagerNumber);
                exportSuccess = createExcel(excelUtil, titleNames, getRow(searcher, pageModel));
                pagerNumber = pagerNumber + 1;
            } while (pagerNumber <= pageResult.getPageCount() && exportSuccess);
            // 返回文件地址
            createExcelResult(result, excelUtil.getExportFileBean());
            // 保存导出记录
            if (exportSuccess) {
                saveExportLog(excelUtil.getExportFileBean().getFileName(),
                        excelUtil.getExportFileBean().getDownloadPath(), excelUtil.getExportFileBean().getFileSize());
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ErrorResponse(e.getMessage());
        }
    }

}
