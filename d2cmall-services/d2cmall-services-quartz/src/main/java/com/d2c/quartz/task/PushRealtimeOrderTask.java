package com.d2c.quartz.task;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.service.MsgPushService;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.model.OrderItem;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.OrderQueryService;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.util.date.DateUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PushRealtimeOrderTask extends BaseTask {

    private static boolean setCount = true;
    private static Integer count = 0;
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private MsgPushService msgPushService;
    @Autowired
    private ProductService productService;

    @Scheduled(cron = "0 0/5 9-23 * * ?")
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.doPushOrder();
    }

    private void doPushOrder() {
        try {
            OrderDto orderDto = orderQueryService.findPushMinutesOrder("10");
            if (orderDto != null && count < 95) {
                OrderItem orderItem = orderItemService.findByOrderId(orderDto.getId()).get(0);
                Brand brand = brandService.findById(orderItem.getDesignerId());
                Product product = productService.findById(orderItem.getProductId());
                JSONArray array = JSONArray.fromObject(product.getProductCategory());
                String category = JSONObject.parseObject(array.get(array.size() - 1).toString()).getString("name");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("headpic", orderDto.getMemberInfo().getHeadPic());
                jsonObject.put("headPic", orderDto.getMemberInfo().getHeadPic());
                jsonObject.put("province", orderDto.getProvince());
                jsonObject.put("minutes", DateUtil.dateSubtrationToMin(new Date(), orderDto.getPaymentTime()));
                jsonObject.put("nickName", orderDto.getMemberInfo().getNickname());
                jsonObject.put("designerName", brand.getName());
                jsonObject.put("url", "/detail/product/" + orderItem.getProductId());
                jsonObject.put("category", category);
                msgPushService.doPushTransmissionMsgToApp(jsonObject, 2, 0, false, "ORDER", "");
                count++;
            }
        } catch (Exception e) {
            logger.error("doPushOrder error!" + e.getMessage(), e);
        }
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void execute2() {
        try {
            if (setCount) {
                setCount = false;
                count = 0;
                logger.info("setCount execute");
            }
        } finally {
            setCount = true;
        }
    }

}
