package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.model.ProductSku;
import com.d2c.product.query.CollagePromotionSearcher;
import com.d2c.product.service.CollagePromotionService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/order/collagepromotion")
public class CollagePromotionCtrl extends BaseCtrl<CollagePromotionSearcher> {

    @Autowired
    private CollagePromotionService collagePromotionService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;

    @Override
    protected Response doList(CollagePromotionSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<CollagePromotion> pager = collagePromotionService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("searcher", searcher);
        return result;
    }

    @Override
    protected int count(CollagePromotionSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(CollagePromotionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
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
    protected Response doHelp(CollagePromotionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        CollagePromotion collagePromotion = collagePromotionService.findById(id);
        result.put("collagePromotion", collagePromotion);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        AdminDto admin = this.getLoginedAdmin();
        CollagePromotion collagePromotion = JsonUtil.instance().toObject(data, CollagePromotion.class);
        collagePromotion.setLastModifyMan(admin.getUsername());
        collagePromotionService.update(collagePromotion);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        CollagePromotion collagePromotion = JsonUtil.instance().toObject(data, CollagePromotion.class);
        collagePromotion = collagePromotionService.insert(collagePromotion);
        result.put("collagePromotion", collagePromotion);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = collagePromotionService.delete(id, admin.getUsername());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    public Response doMark(Long id, Integer status) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = collagePromotionService.doMark(id, status, admin.getUsername());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    @RequestMapping(value = "/update/sort", method = RequestMethod.POST)
    public Response updateSort(Long id, Integer sort) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = collagePromotionService.updateSort(id, sort);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    /**
     * 更新活动库存
     *
     * @param skuIds
     * @param flashStock
     * @return
     */
    @RequestMapping(value = "/update/stock", method = RequestMethod.POST)
    public Response updateStock(Long[] skuIds, Integer[] flashStock, BigDecimal[] collagePrice) {
        SuccessResponse result = new SuccessResponse();
        if (skuIds == null || skuIds.length <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
            return result;
        }
        ProductSku sku = productSkuService.findById(skuIds[0]);
        productSkuService.updateFlashStore(sku.getProductId(), skuIds, flashStock, collagePrice, null);
        productService.updatePriceBySku(sku.getProductId());
        return result;
    }

}
