package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.Setting;
import com.d2c.order.service.SettingService;
import com.d2c.product.model.Product;
import com.d2c.product.search.service.ProductSearcherService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.product.service.ProductSkuStockService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProcessProductStockTask extends BaseTask {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private SettingService settingService;
    @Reference
    private ProductSearcherService productSearcherService;
    @Autowired
    private ProductSkuStockService productSkuStockService;

    @Scheduled(cron = "0 0 4 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        Setting setting = settingService.findByCode(Setting.STOCKSYNC);
        if (setting != null && setting.getStatus() == 1) {
            Date currentTime = new Date();
            String syncStamp = String.valueOf(currentTime.getTime());
            this.doSyncStock(syncStamp);
            this.updateSearch(syncStamp);
        }
    }

    /**
     * 同步商品库存
     */
    private void doSyncStock(String syncStamp) {
        try {
            /**
             * 同步表中占单库存清零
             */
            productSkuStockService.doCleanOccupy();
            /**
             * 同步SKU的库存
             */
            productSkuService.doSyncSkuStore();
            /**
             * 标记上一步存在变动的商品的时间戳
             */
            productService.updateSyncTimestamp(syncStamp);
            /**
             * 汇总SKU的自营库存
             */
            productSkuService.doSumSkuStore();
            /**
             * 汇总商品的自营库存和POP库存
             */
            productService.doSumProductStore();
        } catch (Exception e) {
            logger.error("doSyncStock error!" + e.getMessage(), e);
        }
    }

    /**
     * 更新存在变动标记的商品索引
     */
    private void updateSearch(String syncStamp) {
        try {
            PageModel page = new PageModel();
            int numberPage = 1;
            page.setP(numberPage);
            page.setPageSize(500);
            PageResult<Product> pager = new PageResult<>();
            do {
                pager = productService.findSyncProductStore(syncStamp, page);
                for (Product product : pager.getList()) {
                    try {
                        int storeUpdate = 0, spotUpdate = 0;
                        if (product.getSyncStore() + product.getPopStore() > 0) {
                            storeUpdate = 1;
                        }
                        if (product.getSyncStore() > 0) {
                            spotUpdate = 1;
                        }
                        productSearcherService.updateStore(product.getId(), storeUpdate, spotUpdate);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                page.setPageNumber(page.getPageNumber() + 1);
            } while (pager.isNext());
        } catch (Exception e) {
            logger.error("updateSearch error!" + e.getMessage(), e);
        }
    }
    // /**
    // * 修复锁定库存
    // */
    // private void doRepairFreezeStock() {
    // try {
    // productSkuService.doRepairFreezeStock();
    // } catch (Exception e) {
    // logger.error("doRepairFreezeStock error!" + e.getMessage(), e);
    // }
    // }
}
