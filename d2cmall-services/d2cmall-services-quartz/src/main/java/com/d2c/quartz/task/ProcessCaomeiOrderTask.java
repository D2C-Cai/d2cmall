package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.third.caomei.CaomeiClient;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProcessCaomeiOrderTask extends BaseTask {

    @Autowired
    private OrderItemService orderItemService;

    @Scheduled(fixedDelay = 3 * 60 * 1000 * 60)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        processUpdateDeliverySn();
        processDelivery();
    }

    /**
     * 草莓订单发货
     */
    private void processDelivery() {
        this.processPager(100, new EachPage<OrderItemDto>() {
            @Override
            public int count() {
                OrderSearcher searcher = new OrderSearcher();
                searcher.setItemStatus(new String[]{ItemStatus.NORMAL.name()});
                searcher.setProductSource(ProductSource.CAOMEI.name());
                return orderItemService.countBySearcher(searcher);
            }

            @Override
            public PageResult<OrderItemDto> search(PageModel page) {
                OrderSearcher searcher = new OrderSearcher();
                searcher.setItemStatus(new String[]{ItemStatus.NORMAL.name()});
                searcher.setProductSource(ProductSource.CAOMEI.name());
                return orderItemService.findSimpleBySearcher(searcher, page);
            }

            @Override
            public boolean each(OrderItemDto object) {
                try {
                    Map<String, String> map = CaomeiClient.getInstance().queryExpress(object.getOrderSn());
                    if (map == null) {
                        return false;
                    }
                    String expressCom = map.get("expressCom");
                    String expressNo = map.get("expressNo");
                    if (expressNo != null) {
                        if ("China EMS".equalsIgnoreCase(expressCom) || "EMS".equalsIgnoreCase(expressCom)) {
                            int result = orderItemService.doDeliveryItem(object.getId(), 0, object.getProductSkuSn(),
                                    "EMS", expressNo, "定时器", true, object.getProductQuantity());
                            return result > 0 ? true : false;
                        }
                        if ("Shentong".equalsIgnoreCase(expressCom)) {
                            int result = orderItemService.doDeliveryItem(object.getId(), 0, object.getProductSkuSn(),
                                    "shentong", expressNo, "定时器", true, object.getProductQuantity());
                            return result > 0 ? true : false;
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                return false;
            }
        });
    }

    /**
     * 草莓订单更新发货信息
     */
    private void processUpdateDeliverySn() {
        this.processPager(50, new EachPage<OrderItem>() {
            @Override
            public int count() {
                return orderItemService.countWaitUpdateDeliveryCaomeiOrder();
            }

            @Override
            public PageResult<OrderItem> search(PageModel page) {
                return orderItemService.findWaitUpdateDeliveryCaomeiOrder(page);
            }

            @Override
            public boolean each(OrderItem object) {
                try {
                    Map<String, String> map = CaomeiClient.getInstance().queryExpress(object.getOrderSn());
                    if (map == null) {
                        return false;
                    }
                    String expressCom = map.get("expressCom");
                    String expressNo = map.get("expressNo");
                    if (expressNo != null) {
                        if ("China EMS".equalsIgnoreCase(expressCom) || "EMS".equalsIgnoreCase(expressCom)) {
                            int success = orderItemService.updateDeliveryInfo(object.getId(), "EMS", expressNo,
                                    object.getProductSkuSn(), "定时器");
                            return success > 0 ? true : false;
                        }
                        if ("Shentong".equalsIgnoreCase(expressCom)) {
                            int success = orderItemService.updateDeliveryInfo(object.getId(), "shentong", expressNo,
                                    object.getProductSkuSn(), "定时器");
                            return success > 0 ? true : false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

}
