package com.d2c.product.jms.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.product.search.service.DesignerSearcherService;
import com.d2c.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;

@Component
public class BrandSummaryQueueListener extends AbsMqListener {

    @Autowired
    private BrandService brandService;
    @Reference
    private DesignerSearcherService designerSearcherService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMsg = (MapMessage) message;
        try {
            Long brandId = mapMsg.getLong("id");
            Integer count = mapMsg.getInt("count");
            String type = mapMsg.getString("type");
            if (type.equals("fans")) {
                this.updateDesignerFans(brandId, count);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void updateDesignerFans(Long id, Integer count) {
        int success = brandService.updateFansCount(id, count);
        if (success > 0) {
            designerSearcherService.updateFans(id, count);
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.BRAND_SUMMARY;
    }

}
