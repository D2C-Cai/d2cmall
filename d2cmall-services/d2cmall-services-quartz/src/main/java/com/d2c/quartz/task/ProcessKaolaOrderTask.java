package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.order.third.kaola.reponse.KaolaOrderStatus;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProcessKaolaOrderTask extends BaseTask {

    @Autowired
    private OrderItemService orderItemService;

    @Scheduled(fixedDelay = 60 * 1000 * 15)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.autoDeliverOrderItem();
    }

    private void autoDeliverOrderItem() {
        try {
            this.processPager(100, new EachPage<OrderItemDto>() {
                @Override
                public int count() {
                    OrderSearcher searcher = new OrderSearcher();
                    searcher.setItemStatus(new String[]{ItemStatus.NORMAL.name()});
                    searcher.setProductSource(ProductSource.KAOLA.name());
                    return orderItemService.countBySearcher(searcher);
                }

                @Override
                public PageResult<OrderItemDto> search(PageModel page) {
                    OrderSearcher searcher = new OrderSearcher();
                    searcher.setItemStatus(new String[]{ItemStatus.NORMAL.name()});
                    searcher.setProductSource(ProductSource.KAOLA.name());
                    return orderItemService.findSimpleBySearcher(searcher, page);
                }

                @Override
                public boolean each(OrderItemDto object) {
                    if (object.getWarehouseId() != null && object.getWarehouseId() > 0) {
                        try {
                            KaolaOrderStatus kaolaOrder = KaolaClient.getInstance()
                                    .queryOrderStatus(object.getOrderSn() + "-" + object.getWarehouseId());
                            String status = kaolaOrder.getResult().get(0).getStatus();
                            if (status.equals("4") || status.equals("5")) {
                                int result = orderItemService.doDeliveryItem(object.getId(), 0,
                                        object.getProductSkuSn(), kaolaOrder.getResult().get(0).getDeliverName(),
                                        kaolaOrder.getResult().get(0).getDeliverNo(), "定时器", true,
                                        object.getProductQuantity());
                                return result > 0 ? true : false;
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}