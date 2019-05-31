package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.Consult;
import com.d2c.member.service.ConsultService;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品咨询
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/consult")
public class ConsultController extends BaseController {

    @Autowired
    private ConsultService consultService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 商品咨询列表
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult list(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        Consult consult = consultService.findById(id);
        SearcherProduct product = productSearcherQueryService.findById(consult.getProductId().toString());
        result.put("consult", consult.toJson());
        result.put("product", product.toJson());
        return result;
    }

}
