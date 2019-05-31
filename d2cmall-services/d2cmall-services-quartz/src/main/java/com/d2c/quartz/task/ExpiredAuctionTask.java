package com.d2c.quartz.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.order.service.tx.AuctionTxService;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.service.AuctionProductService;
import com.d2c.quartz.task.base.BaseTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpiredAuctionTask extends BaseTask {

    @Autowired
    private AuctionProductService auctionProductService;
    @Reference
    private AuctionTxService auctionTxService;

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.autoCloseAuction();
    }

    /**
     * 过期拍卖订单结束
     */
    private void autoCloseAuction() {
        try {
            List<AuctionProduct> list = auctionProductService.findExpiredAuctionProduct();
            for (AuctionProduct auctionProduct : list) {
                try {
                    auctionTxService.doEndAuction(auctionProduct);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
