package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.dto.ProductCategoryDto;
import com.d2c.product.model.ProductCategory;
import com.d2c.product.query.ProductCategorySearcher;
import com.d2c.product.service.ProductCategoryService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/productcategory")
public class ProductCategoryCtrl extends BaseCtrl<ProductCategorySearcher> {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Override
    protected List<Map<String, Object>> getRow(ProductCategorySearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ProductCategorySearcher searcher) {
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
    protected Response doHelp(ProductCategorySearcher searcher, PageModel page) {
        try {
            return this.doList(searcher, page);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected Response doList(ProductCategorySearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ProductCategoryDto> pager = productCategoryService.findBySearch(searcher, new PageModel(1, 500));
        if (searcher.getDepth() == null) {
            pager.setList(productCategoryService.processSequence(pager.getList(), null, null));
        }
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ProductCategory productCategory = productCategoryService.findById(id);
        result.put("productCategory", productCategory);
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
        ProductCategory productCategory = (ProductCategory) JsonUtil.instance().toObject(data, ProductCategory.class);
        try {
            if (productCategory.getCode() != null) {
                if (productCategoryService.findByCode(productCategory.getCode()) != null) {
                    result.setMessage("已经存在相同的代号，请选择其他的代号！");
                    result.setStatus(-1);
                    return result;
                }
            }
            productCategory = productCategoryService.insert(productCategory);
            result.put("productCategory", productCategory);
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        ProductCategory productCategory = (ProductCategory) JsonUtil.instance().toObject(data, ProductCategory.class);
        if (productCategory.getCode() != null) {
            ProductCategory db = productCategoryService.findByCode(productCategory.getCode());
            if (db != null && !db.getId().equals(productCategory.getId())) {
                result.setMessage("已经存在相同的代号，请选择其他的代号！");
                result.setStatus(-1);
                return result;
            }
        }
        productCategoryService.update(productCategory);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Response status(@PathVariable Integer status, Long[] ids) {
        for (Long id : ids) {
            productCategoryService.updateStatus(id, status);
        }
        return new SuccessResponse();
    }

}