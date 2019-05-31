package com.d2c.flame.controller.product;

import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.order.model.Store;
import com.d2c.order.query.StoreSearcher;
import com.d2c.order.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 线下门店
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/store")
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;

    /**
     * 门店列表
     *
     * @param province
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(String province, PageModel page) {
        ResponseResult result = new ResponseResult();
        StoreSearcher searcher = new StoreSearcher();
        searcher.setStatus(1);
        searcher.setProvince(province);
        searcher.setBusType("DIRECT");
        PageResult<Store> pager = storeService.findBySearch(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("stores", pager, array);
        return result;
    }

    /**
     * 门店所在的省列表
     *
     * @return
     */
    @RequestMapping(value = "/province", method = RequestMethod.GET)
    public ResponseResult province() {
        ResponseResult result = new ResponseResult();
        List<String> list = storeService.findProvinceList();
        result.put("provinces", list);
        return result;
    }

}
