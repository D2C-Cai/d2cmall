package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.model.ProductSku;
import com.d2c.product.model.ProductSkuStock;
import com.d2c.product.model.StockCheck;
import com.d2c.product.model.StockCheckItem;
import com.d2c.product.query.StockCheckItemSearcher;
import com.d2c.product.service.ProductSkuService;
import com.d2c.product.service.ProductSkuStockService;
import com.d2c.product.service.StockCheckItemService;
import com.d2c.product.service.StockCheckService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/stockcheckitem")
public class StockCheckItemCtrl extends BaseCtrl<StockCheckItemSearcher> {

    @Autowired
    private StockCheckItemService stockCheckItemService;
    @Autowired
    private StockCheckService stockCheckService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private ProductSkuStockService productSkuStockService;

    @Override
    protected Response doList(StockCheckItemSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<StockCheckItem> pager = stockCheckItemService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        StockCheckItem stockCheckItem = (StockCheckItem) JsonUtil.instance().toObject(data, StockCheckItem.class);
        SuccessResponse result = new SuccessResponse();
        if (stockCheckItem.getActualQuantity() == null) {
            result.setStatus(-1);
            result.setMessage("实际库存不能为空！");
            return result;
        }
        StockCheckItem old = stockCheckItemService.findOne(stockCheckItem.getCheckId(), stockCheckItem.getBarCode());
        if (old != null) {
            old.setActualQuantity(stockCheckItem.getActualQuantity());
            stockCheckItemService.update(old);
            result.put("StockCheckItem", old);
        } else {
            ProductSku sku = productSkuService.findByBarCode(stockCheckItem.getBarCode());
            if (sku == null) {
                result.setStatus(-1);
                result.setMessage("该SKU不存在，请检查条码！");
                return result;
            }
            StockCheck stockCheck = stockCheckService.findById(stockCheckItem.getCheckId());
            ProductSkuStock productSkuStock = productSkuStockService.findOne(stockCheck.getStoreCode(),
                    stockCheckItem.getBarCode());
            if (productSkuStock != null) {
                stockCheckItem.setBookQuantity(productSkuStock.getStock());
            } else {
                stockCheckItem.setBookQuantity(0);
            }
            stockCheckItem = stockCheckItemService.insert(stockCheckItem);
            result.put("StockCheckItem", stockCheckItem);
        }
        return result;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        stockCheckItemService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        for (Long id : ids) {
            stockCheckItemService.delete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doHelp(StockCheckItemSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(StockCheckItemSearcher searcher) {
        return stockCheckItemService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "库存明细表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"商品条码", "实际库存"};
    }

    @Override
    protected List<Map<String, Object>> getRow(StockCheckItemSearcher searcher, PageModel page) {
        PageResult<StockCheckItem> pager = stockCheckItemService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (StockCheckItem item : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("商品条码", item.getBarCode());
            cellsMap.put("实际库存", item.getActualQuantity());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getExportFileType() {
        return "StockCheckItem";
    }

    @RequestMapping(value = "/update/quantity", method = RequestMethod.POST)
    public Response updateActualQuantity(Long id, Integer quantity) {
        SuccessResponse result = new SuccessResponse();
        StockCheckItem item = stockCheckItemService.findById(id);
        item.setActualQuantity(quantity);
        stockCheckItemService.update(item);
        return result;
    }

}
