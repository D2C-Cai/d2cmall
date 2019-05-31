package com.d2c.quartz.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.GuanyiOrderDto;
import com.d2c.order.dto.GuanyiOrderDto.ShopCodeEnum;
import com.d2c.order.model.GuanyiOrder;
import com.d2c.order.model.GuanyiOrderItem;
import com.d2c.order.query.GuanyiOrderItemSearcher;
import com.d2c.order.query.GuanyiOrderSearcher;
import com.d2c.order.service.GuanyiOrderItemService;
import com.d2c.order.service.GuanyiOrderService;
import com.d2c.order.third.guanyi.GuanyiErpClient;
import com.d2c.product.model.GuanyiStock;
import com.d2c.product.model.ProductSkuStock;
import com.d2c.product.service.GuanyiStockService;
import com.d2c.product.service.ProductSkuStockService;
import com.d2c.product.service.ProductSkuStockSummaryService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.base.EachPage;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GuanyiToBurgeonTask extends BaseTask {

    private static final String SF_WAREHOUSE = "顺丰正品仓";
    @Autowired
    private GuanyiOrderService guanyiOrderService;
    @Autowired
    private GuanyiStockService guanyiStockService;
    @Autowired
    private ProductSkuStockService productSkuStockService;
    @Autowired
    private ProductSkuStockSummaryService productSkuStockSummaryService;
    @Autowired
    private GuanyiOrderItemService guanyiOrderItemService;

    @Scheduled(fixedDelay = 60 * 1000 * 10)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        this.processSaveOrder();
        this.processToBurgeon();
        this.processExpress();
        this.processSaveStock();
        this.processUpdateStock();
    }

    // 保存从管易中查询到的发货单
    private void processSaveOrder() {
        int pageSize = 100; // 每页大小
        int pageNo = 1; // 从第一页开始
        JSONObject params = new JSONObject();
        params.put("page_no", pageNo);
        params.put("page_size", pageSize);
        params.put("delivery", 1);
        // params.put("code", "SDO73932901927");
        GuanyiOrder order = guanyiOrderService.findLastDeliverOrder();
        if (order != null) {
            params.put("start_delivery_date",
                    DateUtil.second2str(new Date(order.getDeliveryDate().getTime() - 1000 * 60 * 15)));
        }
        params.put("end_delivery_date", DateUtil.second2str(new Date()));
        try {
            GuanyiErpClient client = GuanyiErpClient.getInstance();
            // 获取总数量
            JSONObject countResponse = client.send(params, GuanyiErpClient.DELIVERY_CODE);
            Integer total = countResponse.getInteger("total");
            int pageCount = (total + pageSize - 1) / pageSize;
            // 查询每页的数据并存储
            while (pageCount >= pageNo) {
                params.put("page_no", pageNo);
                JSONObject response = client.send(params, GuanyiErpClient.DELIVERY_CODE);
                JSONArray array = response.getJSONArray("deliverys");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    GuanyiOrderDto guanyiOrderDto = new GuanyiOrderDto(obj);
                    // 排除未知的管易门店
                    if (!ShopCodeEnum.getShopCodeMap().containsKey(guanyiOrderDto.getShopCode())) {
                        continue;
                    }
                    JSONArray details = obj.getJSONArray("details");
                    List<GuanyiOrderItem> items = new ArrayList<>();
                    for (int j = 0; j < details.size(); j++) {
                        items.add(new GuanyiOrderItem(details.getJSONObject(j), guanyiOrderDto));
                    }
                    guanyiOrderDto.setItems(items);
                    try {
                        guanyiOrderService.insert(guanyiOrderDto);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                pageNo++;
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 管易数据伯俊推单
    private void processToBurgeon() {
        GuanyiOrderSearcher search = new GuanyiOrderSearcher();
        search.setSuccess(0);// 还未处理过的
        try {
            this.processPager(20, new EachPage<GuanyiOrderDto>() {
                @Override
                public int count() {
                    return guanyiOrderService.countBySearch(search);
                }

                @Override
                public PageResult<GuanyiOrderDto> search(PageModel page) {
                    return guanyiOrderService.findBySearch(search, page);
                }

                @Override
                public boolean each(GuanyiOrderDto object) {
                    int result = guanyiOrderService.processBurgeon(object, false, "定时器");
                    return result > 0 ? true : false;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 管易发货，将订单 明细发货
    private void processExpress() {
        GuanyiOrderItemSearcher searcher = new GuanyiOrderItemSearcher();
        searcher.setExpressFall(0);
        searcher.setExpress(1);
        try {
            this.processPager(100, new EachPage<GuanyiOrderItem>() {
                @Override
                public int count() {
                    return guanyiOrderItemService.countBySearcher(searcher);
                }

                @Override
                public PageResult<GuanyiOrderItem> search(PageModel page) {
                    return guanyiOrderItemService.findBySearcher(searcher, page);
                }

                @Override
                public boolean each(GuanyiOrderItem guanyiOrderItem) {
                    boolean result = true;
                    try {
                        guanyiOrderItemService.processExpress(guanyiOrderItem, false, "定时器");
                    } catch (Exception e) {
                        result = false;
                        guanyiOrderItem.setExpressFall(1);
                        guanyiOrderItem.setExpressError(e.getMessage() == null ? "空指针异常" : e.getMessage());
                        guanyiOrderItemService.update(guanyiOrderItem);
                        logger.error(e.getMessage(), e);
                    }
                    return result;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // sf库存同步
    private void processSaveStock() {
        int pageSize = 100; // 每页大小
        int pageNo = 1; // 从第一页开始
        JSONObject params = new JSONObject();
        params.put("page_no", pageNo);
        params.put("page_size", pageSize);
        GuanyiStock skuStock = guanyiStockService.findFirst();
        if (skuStock != null) {
            params.put("start_date",
                    DateUtil.second2str(new Date(skuStock.getCreateDate().getTime() - 1000 * 60 * 15)));
        }
        params.put("end_date", DateUtil.second2str(new Date()));
        try {
            GuanyiErpClient client = GuanyiErpClient.getInstance();
            // 获取总数量
            JSONObject countResponse = client.send(params, GuanyiErpClient.STOCK_CODE);
            Integer total = countResponse.getInteger("total");
            int pageCount = (total + pageSize - 1) / pageSize;
            // 查询每页的数据并存储
            while (pageCount >= pageNo) {
                params.put("page_no", pageNo);
                JSONObject response = client.send(params, GuanyiErpClient.STOCK_CODE);
                JSONArray array = response.getJSONArray("stocks");
                for (int i = 0; i < array.size(); i++) {
                    GuanyiStock guanyiStock = new GuanyiStock(array.getJSONObject(i));
                    if (!SF_WAREHOUSE.equals(guanyiStock.getStore())) {
                        continue;
                    }
                    try {
                        guanyiStockService.insert(guanyiStock);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                Thread.sleep(2000);
                pageNo++;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 更新库存
    private void processUpdateStock() {
        // 删除的SKU，暂时当它不删
        PageModel page = new PageModel();
        PageResult<GuanyiStock> pager = null;
        try {
            do {
                pager = guanyiStockService.findBySearcher(page);
                for (GuanyiStock guanyiStock : pager.getList()) {
                    int result = productSkuStockService.update("0000", guanyiStock.getSku(),
                            guanyiStock.getSalableQty() < 0 ? 0 : guanyiStock.getSalableQty());
                    if (result == 0) {
                        ProductSkuStock skuStock = new ProductSkuStock();
                        skuStock.setStoreCode("0000");
                        skuStock.setBarCode(guanyiStock.getSku());
                        skuStock.setStock(guanyiStock.getSalableQty() < 0 ? 0 : guanyiStock.getSalableQty());
                        skuStock.setOccupyStock(0);
                        productSkuStockService.insert(skuStock);
                    }
                }
                page.setPage(page.getPage() + 1);
            } while (pager.getTotalCount() > pager.getPageSize() && pager.isNext());
            productSkuStockSummaryService.sumSfStock();
            guanyiStockService.doClean();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
