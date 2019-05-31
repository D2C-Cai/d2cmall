package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.model.ProductAttribute;
import com.d2c.product.model.ProductCategory;
import com.d2c.product.query.ProductAttributeSearcher;
import com.d2c.product.service.ProductAttributeService;
import com.d2c.product.service.ProductCategoryService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/productattribute")
public class ProductAttributeCtrl extends BaseCtrl<ProductAttributeSearcher> {

    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @Override
    protected List<Map<String, Object>> getRow(ProductAttributeSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ProductAttributeSearcher searcher) {
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
    protected Response doHelp(ProductAttributeSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ProductAttributeSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductAttribute> pager = productAttributeService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ProductAttribute productAttribute = productAttributeService.findById(id);
        result.put("productAttribute", productAttribute);
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
        SuccessResponse result = new SuccessResponse();
        ProductAttribute productAttribute = (ProductAttribute) JsonUtil.instance().toObject(data,
                ProductAttribute.class);
        productAttribute = productAttributeService.insert(productAttribute);
        result.put("productAttribute", productAttribute);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        ProductAttribute productAttribute = (ProductAttribute) JsonUtil.instance().toObject(data,
                ProductAttribute.class);
        productAttributeService.update(productAttribute);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/productcategory/{categoryId}", method = RequestMethod.GET)
    public Response findByCategoryId(@PathVariable Long categoryId) {
        SuccessResponse result = new SuccessResponse();
        ProductCategory pc = productCategoryService.findById(categoryId);
        List<ProductAttribute> productAttributes = new ArrayList<ProductAttribute>();
        if (pc != null && pc.getAttributeGroupId() != null) {
            productAttributes = productAttributeService.findByGroupId(pc.getAttributeGroupId());
        }
        result.put("productAttributes", productAttributes);
        return result;
    }

}
