package com.d2c.flame.controller;

import com.d2c.flame.controller.base.BaseController;
import com.d2c.order.model.Store;
import com.d2c.order.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController extends BaseController {

    @Autowired
    private StoreService storeService;

    /**
     * 门店列表
     *
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        List<Store> list = storeService.findStoreList();
        model.put("list", list);
        return "/o2o/store_list";
    }

    /**
     * 门店详情
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/code/{id}", method = RequestMethod.GET)
    public String store(ModelMap model, @PathVariable Long id) {
        Store store = storeService.findById(id);
        if (store != null) {
            model.put("description", store.getDescription());
        }
        return "/o2o/store_code";
    }

    /**
     * 门店地址
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
    public String address(ModelMap model, @PathVariable Long id) {
        Store store = storeService.findById(id);
        if (store != null) {
            model.put("bdxy", store.getBdxy());
            model.put("xy", store.getXy());
        }
        return "";
    }

}
