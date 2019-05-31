package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.model.ProductAttributeGroup;
import com.d2c.product.query.ProductAttributeGroupSearcher;
import com.d2c.product.service.ProductAttributeGroupService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/productattributegroup")
public class ProductAttributeGroupCtrl extends BaseCtrl<ProductAttributeGroupSearcher> {

    @Autowired
    private ProductAttributeGroupService productAttributeGroupService;

    @Override
    protected List<Map<String, Object>> getRow(ProductAttributeGroupSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ProductAttributeGroupSearcher searcher) {
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
    protected Response doHelp(ProductAttributeGroupSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductAttributeGroup> pager = productAttributeGroupService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response doList(ProductAttributeGroupSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductAttributeGroup> pager = productAttributeGroupService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ProductAttributeGroup productAttributeGroup = productAttributeGroupService.findById(id);
        result.put("productAttributeGroup", productAttributeGroup);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
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
        ProductAttributeGroup productAttributeGroup = (ProductAttributeGroup) JsonUtil.instance().toObject(data,
                ProductAttributeGroup.class);
        productAttributeGroup = productAttributeGroupService.insert(productAttributeGroup);
        result.put("productAttributeGroup", productAttributeGroup);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        ProductAttributeGroup ProductAttributeGroup = (ProductAttributeGroup) JsonUtil.instance().toObject(data,
                ProductAttributeGroup.class);
        productAttributeGroupService.update(ProductAttributeGroup);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

}
