package com.d2c.flame.controller.callback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 合力亿捷
 *
 * @author Lain
 */
@RestController
@RequestMapping("/hollycrm")
public class HollycrmController extends BaseController {

    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    @RequestMapping(value = "/product/info", method = {RequestMethod.POST, RequestMethod.GET})
    public void productInfo(String businessParam, String callback) {
        try {
            businessParam = URLDecoder.decode(businessParam, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        JSONObject param = JSON.parseObject(businessParam);
        SearcherProduct product = productSearcherQueryService.findById(param.getString("productId"));
        JSONObject obj = new JSONObject();
        obj.put("img", "https://img.d2c.cn/" + product.getProductImageCover());
        obj.put("name", product.getName());
        obj.put("url", "http://www.d2cmall.com/product/" + product.getId());
        obj.put("currentPrice", product.getCurrentPrice());
        JSONArray array = new JSONArray();
        array.add(obj);
        JSONObject result = new JSONObject();
        result.put("success", true);
        result.put("list", array);
        try {
            getResponse().getWriter().write(callback + "(" + result.toJSONString() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
