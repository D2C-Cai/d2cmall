package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.AdminDto;
import com.d2c.order.model.Store;
import com.d2c.order.service.StoreService;
import com.d2c.product.model.StockCheck;
import com.d2c.product.query.StockCheckSearcher;
import com.d2c.product.service.StockCheckService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/stockcheck")
public class StockCheckCtrl extends BaseCtrl<StockCheckSearcher> {

    @Autowired
    private StockCheckService stockCheckService;
    @Autowired
    private StoreService storeService;

    @Override
    protected Response doList(StockCheckSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        PageResult<StockCheck> pager = stockCheckService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        StockCheck stockCheck = (StockCheck) JsonUtil.instance().toObject(data, StockCheck.class);
        SuccessResponse result = new SuccessResponse();
        Store store = storeService.findById(stockCheck.getStoreId());
        if (store == null) {
            result.setStatus(-1);
            result.setMessage("门店不存在，无法提交盘点单！");
            return result;
        }
        StockCheckSearcher searcher = new StockCheckSearcher();
        searcher.setStatus(new Integer[]{0, 1});
        searcher.setStoreId(stockCheck.getStoreId());
        int count = stockCheckService.countBySearch(searcher);
        if (count > 0) {
            result.setStatus(-1);
            result.setMessage("请先处理掉未提交的盘点单！");
            return result;
        }
        stockCheck.setStoreCode(store.getCode());
        stockCheck.setStoreName(store.getName());
        stockCheck.setSn("C" + System.currentTimeMillis());
        stockCheck = stockCheckService.insert(stockCheck);
        result.put("stockCheck", stockCheck);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        StockCheck stockCheck = stockCheckService.findById(id);
        result.put("stockCheck", stockCheck);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        stockCheckService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(StockCheckSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected int count(StockCheckSearcher searcher) {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        return stockCheckService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "库存盘点表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"创建时间", "门店编号", "门店名称", "单据类型", "备注", "账面库存", "实际库存"};
    }

    @Override
    protected List<Map<String, Object>> getRow(StockCheckSearcher searcher, PageModel page) {
        AdminDto dto = this.getLoginedAdmin();
        this.initSearcherByRole(dto, searcher);
        PageResult<StockCheck> pager = stockCheckService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (StockCheck item : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("创建时间", DateUtil.second2str(item.getCreateDate()));
            cellsMap.put("门店编号", item.getStoreCode());
            cellsMap.put("门店名称", item.getStoreName());
            if (item.getType() == 1) {
                cellsMap.put("单据类型", "全盘单");
            } else if (item.getType() == 2) {
                cellsMap.put("单据类型", "抽盘单");
            }
            cellsMap.put("备注", item.getMemo());
            cellsMap.put("账面库存", item.getBookQuantity());
            cellsMap.put("实际库存", item.getActualQuantity());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getExportFileType() {
        return "StockCheck";
    }

    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Response updateStatus(Long[] ids, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            stockCheckService.updateStatus(id, status);
        }
        return result;
    }

    @RequestMapping(value = "/update/memo", method = RequestMethod.POST)
    public Response updateMemo(Long id, String memo) {
        SuccessResponse result = new SuccessResponse();
        stockCheckService.updateMemo(id, memo);
        return result;
    }

}
