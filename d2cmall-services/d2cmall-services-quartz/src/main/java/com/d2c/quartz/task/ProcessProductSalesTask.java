package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.service.OrderItemService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductSummaryService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessProductSalesTask extends BaseTask {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ProductSummaryService productSummaryService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doAnalysisBrandSales();
        this.doAnalysisRecentlySales();
        this.doAnalysisPartnerSales();
        this.doAnalysisProductSales();
    }

    private void doAnalysisBrandSales() {
        PageModel page = new PageModel();
        page.setPageSize(100);
        List<Long> brandIds = brandService.findAllSales();
        PageResult<Map<String, Object>> pager = new PageResult<>();
        do {
            pager = orderItemService.findBrandSales(page, 30);
            for (Map<String, Object> map : pager.getList()) {
                try {
                    Long brandId = (Long) map.get("brandId");
                    Integer sales = ((BigDecimal) map.get("sales")).intValue();
                    brandService.updateSales(brandId, sales);
                    brandIds.remove(brandId);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            page.setPageNumber(page.getPageNumber() + 1);
        } while (pager.isNext());
        for (Long id : brandIds) {
            brandService.updateSales(id, 0);
        }
    }

    private void doAnalysisRecentlySales() {
        List<Long> productIds = productSummaryService.findAllRecentlySales();
        List<Map<String, Object>> list = orderItemService.findRecentlyMonthSales(new PageModel(1, 150), 1);
        for (Map<String, Object> map : list) {
            Long productId = (Long) map.get("productId");
            Integer recentlySales = ((BigDecimal) map.get("sales")).intValue();
            productSummaryService.updateRecentlySales(productId, recentlySales);
            productIds.remove(productId);
        }
        for (Long productId : productIds) {
            productSummaryService.updateRecentlySales(productId, 0);
        }
    }

    private void doAnalysisPartnerSales() {
        List<Long> productIds = productSummaryService.findAllPartnerSales();
        List<Map<String, Object>> list = orderItemService.findPartnerSales(new PageModel(1, 100), 7);
        for (Map<String, Object> map : list) {
            Long productId = (Long) map.get("productId");
            Integer sales = ((BigDecimal) map.get("sales")).intValue();
            productSummaryService.updatePartnerSales(productId, sales);
            productIds.remove(productId);
        }
        for (Long productId : productIds) {
            productSummaryService.updatePartnerSales(productId, 0);
        }
    }

    private void doAnalysisProductSales() {
        try {
            List<HashMap<String, Object>> orders = orderItemService.findProductSales();
            for (int i = 0; i < orders.size(); i++) {
                try {
                    HashMap<String, Object> value = orders.get(i);
                    Long productId = (Long) value.get("id");
                    Integer sales = Integer.parseInt(value.get("count").toString());
                    productSummaryService.updateSalesCount(sales, productId);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error("doSyncProductSales error!" + e.getMessage(), e);
        }
    }

}
