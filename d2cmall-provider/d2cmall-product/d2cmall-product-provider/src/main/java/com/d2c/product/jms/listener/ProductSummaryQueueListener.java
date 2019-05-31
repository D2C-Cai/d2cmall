package com.d2c.product.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.product.service.ProductSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class ProductSummaryQueueListener extends AbsMqListener {

    @Autowired
    private ProductSummaryService productSummaryService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long id = mapMsg.getLong("id");
            Integer count = mapMsg.getInt("count");
            String type = mapMsg.getString("type");
            if (type.equals("consult")) {
                productSummaryService.updateConsultsCount(count, id);
            } else if (type.equals("collection")) {
                productSummaryService.updateLikeCount(count, id);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.PRODUCT_SUMMARY;
    }

}
