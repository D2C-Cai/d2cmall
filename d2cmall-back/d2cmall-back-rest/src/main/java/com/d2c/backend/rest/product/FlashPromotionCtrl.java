package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.model.Admin;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.product.query.FlashPromotionSearcher;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.FlashPromotionService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/flashpromotion")
public class FlashPromotionCtrl extends BaseCtrl<FlashPromotionSearcher> {

    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;

    @Override
    protected Response doList(FlashPromotionSearcher searcher, PageModel page) {
        PageResult<FlashPromotion> pager = flashPromotionService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(FlashPromotionSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(FlashPromotionSearcher searcher, PageModel page) {
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
    protected Response doHelp(FlashPromotionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        FlashPromotion flashPromotion = JsonUtil.instance().toObject(data, FlashPromotion.class);
        // 商品级别的
        if (flashPromotion.getPromotionScope() == 0) {
            FlashPromotion same = flashPromotionService.findByStartDateAndScope(flashPromotion.getStartDate(), 0,
                    flashPromotion.getChannel());
            if (same != null && !same.getId().equals(flashPromotion.getId())) {
                result.setStatus(-1);
                result.setMessage("已存在同一天相同场次的商品限时购，请先删除之前的，限时购：" + same.getName());
                return result;
            }
        }
        flashPromotion.setLastModifyMan(admin.getUsername());
        int success = flashPromotionService.update(flashPromotion);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        FlashPromotion flashPromotion = JsonUtil.instance().toObject(data, FlashPromotion.class);
        // 商品级别的
        if (flashPromotion.getPromotionScope() == 0) {
            FlashPromotion same = flashPromotionService.findByStartDateAndScope(flashPromotion.getStartDate(), 0,
                    flashPromotion.getChannel());
            if (same != null) {
                result.setMessage("已存在同一天相同场次的商品限时购，请先删除之前的，限时购：" + same.getName());
                result.setStatus(-1);
                return result;
            }
        }
        flashPromotion = flashPromotionService.insert(flashPromotion);
        result.put("flashPromotion", flashPromotion);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = flashPromotionService.deleteById(id, admin.getUsername());
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

    /**
     * 商品列表
     *
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Response findProduct(@PathVariable("id") Long id, PageModel page) {
        ProductSearcher productSearcher = new ProductSearcher();
        productSearcher.setFlashPromotionId(id);
        PageResult<ProductDto> pager = productService.findBySearch(productSearcher, page);
        return new SuccessResponse(pager);
    }

    /**
     * 状态
     *
     * @param id
     * @param status
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public Response updateMark(Long id, Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = flashPromotionService.updateStatus(id, status, admin.getUsername());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    /**
     * 排序
     *
     * @param id
     * @param sort
     * @return
     */
    @RequestMapping(value = "/update/sort", method = RequestMethod.POST)
    public Response updateSort(Long id, Integer sort) {
        SuccessResponse result = new SuccessResponse();
        flashPromotionService.updateSort(id, sort);
        return result;
    }

    /**
     * 绑定商品
     *
     * @param id
     * @param productIds
     * @return
     * @throws NotLoginException
     * @throws BusinessException
     */
    @RequestMapping(value = "/bind/product", method = RequestMethod.POST)
    public Response doBind(Long id, Long[] productIds) throws NotLoginException, BusinessException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int totalCount = 0;
        for (Long productId : productIds) {
            Product product = productService.findById(productId);
            if (product != null && product.getFlashPromotionId() != null && product.getFlashPromotionId() != id) {
                FlashPromotion promotion = flashPromotionService.findById(product.getFlashPromotionId());
                if (promotion != null && !promotion.isEnd()) {
                    continue;
                }
            }
            int success = productService.updateFlashPromotion(productId, id, admin.getUsername());
            if (success > 0) {
                totalCount++;
            }
        }
        result.setMessage("成功绑定" + totalCount + "条，不成功" + (productIds.length - totalCount) + "条");
        return result;
    }

    /**
     * 商品解绑活动
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/cancal/product", method = RequestMethod.POST)
    public Response doCancel(Long productId) {
        SuccessResponse result = new SuccessResponse();
        productService.deleteFlashPromotion(productId);
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
    public Response updateStock(Long[] skuIds, Integer[] flashStock, BigDecimal[] flashPrice) {
        SuccessResponse result = new SuccessResponse();
        if (skuIds == null || skuIds.length <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
            return result;
        }
        ProductSku sku = productSkuService.findById(skuIds[0]);
        productSkuService.updateFlashStore(sku.getProductId(), skuIds, flashStock, null, flashPrice);
        productService.updatePriceBySku(sku.getProductId());
        return result;
    }

    /**
     * 批量导入商品
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/product", method = RequestMethod.POST)
    public Response importProduct(HttpServletRequest request, Long id) {
        Admin admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String productSn = map.get("商品款号").toString();
                Product product = productService.findOneBySn(productSn);
                if (product != null && product.getFlashPromotionId() != null && product.getFlashPromotionId() != id) {
                    FlashPromotion promotion = flashPromotionService.findById(product.getFlashPromotionId());
                    if (promotion != null && !promotion.isEnd()) {
                        errorMsg.append("第" + row + "行，商品款号为：" + productSn + "商品已绑定正在进行中的活动：" + promotion.getName()
                                + "，请先手动解绑");
                        return false;
                    }
                }
                productService.updateFlashPromotion(product.getId(), id, admin.getUsername());
                return true;
            }
        });
    }

}
