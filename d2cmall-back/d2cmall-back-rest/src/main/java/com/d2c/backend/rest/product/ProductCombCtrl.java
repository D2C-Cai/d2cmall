package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.dto.ProductCombDto;
import com.d2c.product.model.ProductComb;
import com.d2c.product.query.ProductCombSearcher;
import com.d2c.product.service.ProductCombService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/productcomb")
public class ProductCombCtrl extends BaseCtrl<ProductCombSearcher> {

    @Autowired
    private ProductCombService productCombService;

    @Override
    protected List<Map<String, Object>> getRow(ProductCombSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ProductCombSearcher searcher) {
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
    protected Response doHelp(ProductCombSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ProductCombSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        page.setPageSize(10);
        PageResult<ProductCombDto> pager = productCombService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ProductCombDto productComb = productCombService.findDtoById(id);
        result.put("productComb", productComb);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        for (Long id : ids) {
            productCombService.delete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        int success = productCombService.delete(id);
        if (success > 0) {
            return new SuccessResponse();
        } else {
            return new ErrorResponse("删除不成功");
        }
    }

    @Override
    protected Response doInsert(JSONObject data) {
        this.getLoginedAdmin();
        ProductComb productComb = (ProductComb) JsonUtil.instance().toObject(data, ProductComb.class);
        productCombService.insert(productComb);
        return new SuccessResponse();
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        ProductCombDto productComb = (ProductCombDto) JsonUtil.instance().toObject(data, ProductCombDto.class);
        BeanUt.trimString(productComb);
        // 这两句话不能注掉，不然后台会报错。
        ProductComb db = new ProductComb();
        BeanUtils.copyProperties(productComb, db);
        productCombService.update(db);
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response mark(@PathVariable Integer status, Long[] ids) {
        for (Long id : ids) {
            productCombService.updateMark(id, status);
        }
        return new SuccessResponse();
    }

}
