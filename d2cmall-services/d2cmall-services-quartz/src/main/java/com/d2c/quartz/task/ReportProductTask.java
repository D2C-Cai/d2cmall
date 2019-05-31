package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.service.OrderItemService;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.service.BrandCategoryService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.report.model.ProductOrderAnalysis;
import com.d2c.report.model.ProductOrderAnalysis.BuzType;
import com.d2c.report.service.ProductOrderAnalysisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReportProductTask extends BaseTask {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Reference
    private ProductOrderAnalysisService productOrderAnalysisService;

    @Scheduled(cron = "0 0 5 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doAnalysis();
    }

    private void doAnalysis() {
        ProductOrderAnalysis lastItem = productOrderAnalysisService.findLast();
        Calendar cal = Calendar.getInstance();
        cal.setTime(
                lastItem != null ? new Date(lastItem.getOrderDate().getTime() + 1 * 24 * 60 * 60 * 1000) : new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        PageModel page = null;
        PageResult<Map<String, Object>> pager = null;
        List<BrandCategory> basicDatas = brandCategoryService.findByType(BrandCategory.OPERATION);
        Map<Long, String> operationMap = new HashMap<>();
        for (BrandCategory basicData : basicDatas) {
            operationMap.put(basicData.getId(), basicData.getName());
        }
        while (cal.getTime().before(new Date())) {
            Date orderDate = new Date(cal.getTimeInMillis() - 1 * 24 * 60 * 60 * 1000);
            // 获取商品销售数据
            page = new PageModel();
            page.setPageSize(100);
            do {
                pager = orderItemService.findProductOrderAnalysis(cal.getTime(), BuzType.SALE.toString(), page);
                for (Map<String, Object> map : pager.getList()) {
                    ProductOrderAnalysis item = new ProductOrderAnalysis(map, orderDate, BuzType.SALE);
                    if (map.get("operation") != null && StringUtils.isNumeric((String) (map.get("operation")))) {
                        item.setOperation(operationMap.get(Long.parseLong((String) (map.get("operation")))));
                    }
                    productOrderAnalysisService.doReplaceInto(item);
                }
                page.setP(page.getP() + 1);
            } while (pager.isNext());
            // 获取商品发货数量
            page = new PageModel();
            page.setPageSize(100);
            do {
                pager = orderItemService.findProductOrderAnalysis(cal.getTime(), BuzType.DELIVERY.toString(), page);
                for (Map<String, Object> map : pager.getList()) {
                    ProductOrderAnalysis item = new ProductOrderAnalysis(map, orderDate, BuzType.DELIVERY);
                    if (map.get("operation") != null && StringUtils.isNumeric((String) (map.get("operation")))) {
                        item.setOperation(operationMap.get(Long.parseLong((String) (map.get("operation")))));
                    }
                    int result = productOrderAnalysisService.updatepDeliverAndClose(item.getDesignerId(),
                            item.getProductSku(), item.getDeliverQuantity(), null, orderDate);
                    if (result == 0) {
                        productOrderAnalysisService.doReplaceInto(item);
                    }
                }
                page.setP(page.getP() + 1);
            } while (pager.isNext());
            // 获取商品售后关闭数量
            page = new PageModel();
            page.setPageSize(100);
            do {
                pager = orderItemService.findProductOrderAnalysis(cal.getTime(), BuzType.CLOSE.toString(), page);
                for (Map<String, Object> map : pager.getList()) {
                    ProductOrderAnalysis item = new ProductOrderAnalysis(map, orderDate, BuzType.CLOSE);
                    if (map.get("operation") != null && StringUtils.isNumeric((String) (map.get("operation")))) {
                        item.setOperation(operationMap.get(Long.parseLong((String) (map.get("operation")))));
                    }
                    int result = productOrderAnalysisService.updatepDeliverAndClose(item.getDesignerId(),
                            item.getProductSku(), null, item.getCloseQuantity(), orderDate);
                    if (result == 0) {
                        productOrderAnalysisService.doReplaceInto(item);
                    }
                }
                page.setP(page.getP() + 1);
            } while (pager.isNext());
            cal.add(Calendar.HOUR_OF_DAY, 24);
        }
    }

}
