package com.d2c.quartz.task;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.order.model.Order;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.model.Store;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.OrderService;
import com.d2c.order.service.RequisitionItemService;
import com.d2c.order.service.StoreService;
import com.d2c.product.model.ProductSkuStock;
import com.d2c.product.service.ProductSkuStockService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ProcessRequisitionTask extends BaseTask {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private OrderService orderService;

    @Scheduled(fixedDelay = 60 * 1000 * 10)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        try {
            this.sysImportType1();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            this.sysImportType3();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 自动生成订单采购
     *
     * @return
     * @throws BusinessException
     * @throws ParseException
     */
    private void sysImportType1() throws BusinessException, ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = sf.parse("2018-01-01");
        RequisitionItem item = requisitionItemService.findLastOne(1);
        if (item != null) {
            deadline = item.getCreateDate();
            // 往前推一个定时周期的时间，避免漏单
            deadline = new Date(deadline.getTime() - 60 * 10 * 1000);
        }
        final Date lastDate = deadline;
        try {
            this.processPager(100, new EachPage<OrderItem>() {
                @Override
                public int count() {
                    return orderItemService.countPopOrderitems(lastDate);
                }

                @Override
                public PageResult<OrderItem> search(PageModel page) {
                    return orderItemService.findPopOrderitems(lastDate, page);
                }

                @Override
                public boolean each(OrderItem object) {
                    if (object.getRefundId() != null || object.getReshipId() != null
                            || object.getExchangeId() != null) {
                        return false;
                    }
                    if (object.getType().equals(OrderType.collage.name()) && object.getCollageStatus() != 8) {
                        return false;
                    }
                    RequisitionItem requisitionItem = requisitionItemService.doSysDesignerDelivery(object);
                    return requisitionItem != null ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 自动生成门店调拨
     *
     * @return
     * @throws BusinessException
     * @throws ParseException
     */
    private void sysImportType3() throws BusinessException, ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date deadline = sf.parse("2018-01-01");
        RequisitionItem item = requisitionItemService.findLastOne(3);
        if (item != null) {
            deadline = item.getCreateDate();
            // 往前推一个定时周期的时间，避免漏单
            deadline = new Date(deadline.getTime() - 60 * 10 * 1000);
        }
        final Date lastDate = deadline;
        try {
            this.processPager(100, new EachPage<OrderItem>() {
                @Override
                public int count() {
                    return orderItemService.countNormalOrderitems(lastDate);
                }

                @Override
                public PageResult<OrderItem> search(PageModel page) {
                    return orderItemService.findNormalOrderitems(lastDate, page);
                }

                @Override
                public boolean each(OrderItem object) {
                    if (object.getRefundId() != null || object.getReshipId() != null
                            || object.getExchangeId() != null) {
                        return false;
                    }
                    if (object.getType().equals(OrderType.collage.name()) && object.getCollageStatus() != 8) {
                        return false;
                    }
                    Map<String, ProductSkuStock> storeStockMap = productSkuStockService
                            .findStoreBySku(object.getProductSkuSn());
                    List<String> storeCodeList = new ArrayList<>();
                    storeCodeList.addAll(storeStockMap.keySet());
                    Order order = orderService.findByOrderSn(object.getOrderSn());
                    if (storeStockMap.size() < 1 || storeStockMap.containsKey("0000") || order.getLongitude() == null
                            || order.getLatitude() == null) {
                        return false;
                    }
                    Long storeId = storeService.chooseStore(storeCodeList, order.getLongitude(), order.getLatitude());
                    if (storeId == null) {
                        return false;
                    }
                    Store store = storeService.findById(storeId);
                    try {
                        int success = orderItemService.doBindStore(object.getId(), null, object.getDeliveryBarCode(),
                                storeId, store.getCode(), store.getName(), "sys");
                        return success == 1 ? true : false;
                    } catch (Exception e) {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
