package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductDetail;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.ProductDetailService;
import com.d2c.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/productdetail")
public class ProductDetailCtrl extends BaseCtrl<ProductSearcher> {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailService productDetailService;

    @Override
    protected List<Map<String, Object>> getRow(ProductSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ProductSearcher searcher) {
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
    protected Response doHelp(ProductSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ProductSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 进入编辑界面
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public Response edit(@PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        Product product = productService.findById(id);
        ProductDetail productDetail = productDetailService.findByProductId(id);
        ProductDto dto = new ProductDto(product, productDetail);
        result.put("product", dto);
        return result;
    }

    /**
     * 进入编辑界面
     */
    @RequestMapping(value = "/updateAd/{id}", method = RequestMethod.POST)
    public Response updateAdPic(@PathVariable Long id, String adPics) {
        SuccessResponse result = new SuccessResponse();
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductId(id);
        String[] adPicList = adPics.split(",");
        String adPic = "";
        for (int i = 0; i < adPicList.length; i++) {
            adPic += adPicList[i] + ",";
        }
        if (adPic.length() > 0) {
            adPic = adPic.substring(0, adPic.length() - 1);
        }
        productDetail.setAdPic(adPic);
        productDetailService.updateByProductId(productDetail);
        return result;
    }

    /**
     * 删除尺码表
     *
     * @param sizeJson
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/sizeJson/{productId}", method = RequestMethod.POST)
    public Response deleteSizeJson(@PathVariable Long productId) {
        SuccessResponse result = new SuccessResponse();
        int success = productDetailService.deleteSizeJson(productId);
        if (success < 1) {
            result.setMessage("操作不成功");
            result.setStatus(-1);
        }
        return result;
    }

}
