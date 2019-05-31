package com.d2c.flame.controller.product;

import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.product.model.PointProduct;
import com.d2c.product.query.PointProductSearcher;
import com.d2c.product.service.PointProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 积分商城
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/pointproduct")
public class PointProductController extends BaseController {

    @Autowired
    private PointProductService pointProductService;

    /**
     * 积分商品列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(PageModel page) {
        ResponseResult result = new ResponseResult();
        PointProductSearcher searcher = new PointProductSearcher();
        Date now = new Date();
        searcher.setMaxStartTime(now);
        searcher.setMinEndTime(now);
        searcher.setMark(1);
        PageResult<PointProduct> pager = pointProductService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("list", pager, array);
        return result;
    }

    /**
     * 积分商品详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        PointProduct product = pointProductService.findById(id);
        result.put("pointProduct", product.toJson());
        return result;
    }

}
