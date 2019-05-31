package com.d2c.order.jms.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.order.service.tx.AuctionTxService;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.service.AuctionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class AuctionMarginQueueListener extends AbsMqListener {

    @Autowired
    private AuctionProductService auctionProductService;
    @Reference
    private AuctionTxService auctionTxService;

    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long auctionId = mapMsg.getLong("auctionId");
            String type = mapMsg.getString("type");
            AuctionProduct auctionProduct = auctionProductService.findById(auctionId);
            if (1 == auctionProduct.getStatus()) {
                if ("SUCCESS".equalsIgnoreCase(type)) {
                    auctionTxService.doEndAuction(auctionProduct);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.BREACH_AUCTION;
    }

}
