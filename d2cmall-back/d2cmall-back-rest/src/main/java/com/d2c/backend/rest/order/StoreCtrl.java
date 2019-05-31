package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.model.Store;
import com.d2c.order.query.StoreSearcher;
import com.d2c.order.service.StoreService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/o2o/store")
public class StoreCtrl extends BaseCtrl<StoreSearcher> {

    @Autowired
    private StoreService storeService;

    @Override
    protected List<Map<String, Object>> getRow(StoreSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(StoreSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(StoreSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<HelpDTO> pager = storeService.findBySearchForHelp(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doList(StoreSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Store> pager = storeService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Store store = storeService.findById(id);
        result.put("store", store);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Store store = (Store) JsonUtil.instance().toObject(data, Store.class);
        BeanUt.trimString(store);
        SuccessResponse result = new SuccessResponse();
        Store oldStore = storeService.findByCode(store.getCode());
        if (oldStore != null) {
            result.setStatus(-1);
            result.setMessage("门店编号已存在！");
            return result;
        }
        try {
            store = storeService.insert(store);
            result.put("store", store);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Store store = (Store) JsonUtil.instance().toObject(data, Store.class);
        BeanUt.trimString(store);
        SuccessResponse result = new SuccessResponse();
        Store oldStore = storeService.findByCode(store.getCode());
        if (oldStore != null && oldStore.getId().intValue() != store.getId().intValue()) {
            result.setStatus(-1);
            result.setMessage("门店编号已存在！");
            return result;
        }
        try {
            storeService.update(store);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/sort/{id}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable Long id, Integer sort) {
        SuccessResponse result = new SuccessResponse();
        try {
            storeService.updateSort(id, sort);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Response updateStatus(Long[] ids, Integer status) {
        SuccessResponse result = new SuccessResponse();
        for (int i = 0; i < ids.length; i++) {
            try {
                storeService.updateStatus(ids, status);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        return result;
    }

}
