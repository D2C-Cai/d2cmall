package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.model.ProductTag;
import com.d2c.product.query.ProductTagSearcher;
import com.d2c.product.service.ProductTagService;
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
@RequestMapping("/rest/product/tag4product")
public class ProductTagCtrl extends BaseCtrl<ProductTagSearcher> {

    @Autowired
    private ProductTagService productTagService;

    @Override
    protected List<Map<String, Object>> getRow(ProductTagSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ProductTagSearcher searcher) {
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
    protected Response doHelp(ProductTagSearcher searcher, PageModel page) {
        page.setPageSize(500);
        return doList(searcher, page);
    }

    @Override
    protected Response doList(ProductTagSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductTag> pager = productTagService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ProductTag productTag = productTagService.findById(id);
        result.put("tag4Product", productTag);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        productTagService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        ProductTag productTag = (ProductTag) JsonUtil.instance().toObject(data, ProductTag.class);
        productTag = productTagService.insert(productTag);
        result.put("tag4Product", productTag);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        ProductTag productTag = (ProductTag) JsonUtil.instance().toObject(data, ProductTag.class);
        productTagService.update(productTag);
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/sort/{id}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable Long id, Integer sort) {
        SuccessResponse result = new SuccessResponse();
        productTagService.updateSort(id, sort);
        return result;
    }

    @RequestMapping(value = "/status/{id}/{status}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        productTagService.updateStatus(id, status);
        return result;
    }

    @RequestMapping(value = "/relation", method = RequestMethod.POST)
    public Response relation(Long[] productIds, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        List<ProductTag> usedTags = new ArrayList<ProductTag>();
        if (1 == productIds.length) {
            usedTags = productTagService.findByProductId(productIds[0]);
        }
        PageResult<ProductTag> pager = productTagService.findBySearch(new ProductTagSearcher(), page);
        result.put("tagList", pager.getList());
        result.put("usedTags", usedTags);
        return result;
    }

}
