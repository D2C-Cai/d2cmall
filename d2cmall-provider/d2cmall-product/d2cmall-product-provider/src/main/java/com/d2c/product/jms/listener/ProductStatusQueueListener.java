package com.d2c.product.jms.listener;

import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.product.model.Product;
import com.d2c.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;

@Component
public class ProductStatusQueueListener extends AbsMqListener {

    @Autowired
    private ProductService productService;

    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long id = mapMsg.getLong("id");
            Integer mark = mapMsg.getInt("mark");
            Long date = mapMsg.getLong("date");
            if (id != null && id > 0) {
                this.processProduct(id, mark, date);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void processProduct(Long productId, Integer mark, long date) {
        Product product = productService.findById(productId);
        if (product != null && product.getMark() == 0) {
            if (product.getUpMarketDate() != null && product.getUpMarketDate().compareTo(new Date(date)) <= 0) {
                productService.doTiming(productId, 0);
                productService.updateMark(productId, mark, product.getLastModifyMan() + " : SYS定时上架");
            }
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.TIMING_PRODUCT;
    }

}
