package com.d2c.backend.rest.product;

import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.dto.AdminDto;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.Product;
import com.d2c.product.model.Promotion;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.PromotionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/promotionrelation")
public class PromotionRelationCtrl extends SuperCtrl {

    @Autowired
    private PromotionService promotionService;
    @Autowired
    private ProductService productService;

    /**
     * 商品关联活动
     *
     * @param productIds
     * @param promotionId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Response insert(Long[] productIds, Long promotionId) throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (productIds == null || productIds.length == 0) {
            result.setStatus(-1);
            result.setMessage("请输入关联的商品！");
            return result;
        }
        Promotion promotion = promotionService.findSimpleById(promotionId);
        for (Long productId : productIds) {
            if (promotion.getPromotionScope() == 0) {
                productService.updateGoodPromotion(productId, promotionId, admin.getUsername());
            } else {
                productService.updateOrderPromotion(productId, promotionId, admin.getUsername());
            }
        }
        return result;
    }

    /**
     * 删除对应关系
     *
     * @param productIds
     * @param promotionId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(Long[] productIds, Long[] promotionId) throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (productIds == null || productIds.length == 0) {
            result.setStatus(-1);
            result.setMessage("请输入关联的商品！");
            return result;
        }
        for (Long promotionItemId : promotionId) {
            Promotion promotion = promotionService.findSimpleById(promotionItemId);
            for (Long productId : productIds) {
                if (promotion.getPromotionScope() == 0) {
                    productService.deleteGoodPromotion(productId, promotionItemId, admin.getUsername());
                } else {
                    productService.deleteOrderPromotion(productId, promotionItemId, admin.getUsername());
                }
            }
        }
        return result;
    }

    /**
     * 活动关联的商品列表
     *
     * @param promotionId
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/product/list/{promotionId}", method = RequestMethod.POST)
    public Response findProductsBySearcher(@PathVariable Long promotionId, ProductSearcher searcher, PageModel page) {
        Promotion promotion = promotionService.findById(promotionId);
        if (promotion.getPromotionScope() == 0) {
            searcher.setPromotionId(promotionId);
            searcher.setOrderPromotionId(null);
            searcher.setOrderByStr("gp_sort DESC");
        } else {
            searcher.setOrderPromotionId(promotionId);
            searcher.setPromotionId(null);
            searcher.setOrderByStr("op_sort DESC");
        }
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    /**
     * 商品关联的活动列表
     *
     * @param page
     * @param productId
     * @return
     */
    @RequestMapping(value = "/promotion/list/{productId}", method = RequestMethod.POST)
    public Response findPromotionsByProductId(PageModel page, @PathVariable Long productId) {
        Product product = productService.findById(productId);
        List<Promotion> list = new ArrayList<>();
        if (product.getGoodPromotionId() != null && product.getGoodPromotionId() > 0) {
            Promotion goodPromotion = promotionService.findSimpleById(product.getGoodPromotionId());
            list.add(goodPromotion);
        }
        if (product.getOrderPromotionId() != null && product.getOrderPromotionId() > 0) {
            Promotion orderPromotion = promotionService.findSimpleById(product.getOrderPromotionId());
            list.add(orderPromotion);
        }
        return new SuccessResponse(list);
    }

    /**
     * 导入商品活动表
     *
     * @param request
     * @param promotionId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/relation/import", method = RequestMethod.POST)
    public Response importPromotionProduct(HttpServletRequest request, Long promotionId) {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        Promotion promotion = promotionService.findSimpleById(promotionId);
        if (promotion == null) {
            result.setStatus(-1);
            result.setMessage("活动未找到，请检查！");
            return result;
        }
        List<Map<String, Object>> excelData = this.getExcelData(request);
        String message = "";
        try {
            String errorStr = "";
            int successCount = 0;
            int failCount = 0;
            List<Long> productIds = new ArrayList<>();
            for (Map<String, Object> map : excelData) {
                String inernalSn = String.valueOf(map.get("商品货号"));
                if (StringUtils.isEmpty(inernalSn)) {
                    continue;
                }
                List<Product> products = productService.findProductBySn(inernalSn);
                if (products != null && products.size() > 0) {
                    for (Product product : products) {
                        productIds.add(product.getId());
                    }
                    for (Long productId : productIds) {
                        if (promotion.getPromotionScope() == 0) {
                            productService.updateGoodPromotion(productId, promotionId, admin.getUsername());
                        } else {
                            productService.updateOrderPromotion(productId, promotionId, admin.getUsername());
                        }
                    }
                    productIds.clear();
                    successCount++;
                } else {
                    errorStr += "货号：" + inernalSn + "，错误原因：商品未找到<br/>";
                    failCount++;
                    continue;
                }
            }
            if (errorStr.length() > 0) {
                message = "导入成功" + successCount + "条！" + "导入不成功：" + failCount + "条！不成功原因：" + "<br/>" + errorStr;
            } else {
                message = "导入成功" + successCount + "条！";
            }
        } catch (Exception e) {
            result.setStatus(-1);
            message = "导入异常" + e.getMessage();
        }
        result.setMessage(message);
        return result;
    }

}
