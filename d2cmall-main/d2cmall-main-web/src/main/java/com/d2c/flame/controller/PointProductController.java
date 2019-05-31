package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.product.model.PointProduct;
import com.d2c.product.query.PointProductSearcher;
import com.d2c.product.service.PointProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * 积分商城
 *
 * @author wwn
 */
@Controller
@RequestMapping(value = "/pointproduct")
public class PointProductController extends BaseController {

    @Autowired
    private PointProductService pointProductService;

    /**
     * 积分商品列表 <b> 在活动期间内上架的
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(PageModel page, ModelMap model) {
        PointProductSearcher searcher = new PointProductSearcher();
        Date now = new Date();
        searcher.setMaxStartTime(now);
        searcher.setMinEndTime(now);
        searcher.setMark(1);
        PageResult<PointProduct> pager = pointProductService.findBySearcher(searcher, page);
        model.put("pager", pager);
        return "";
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        PointProduct product = pointProductService.findById(id);
        result.put("pointProduct", product);
        return "";
    }

}
