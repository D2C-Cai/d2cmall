package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.product.model.Promotion;
import com.d2c.product.model.PromotionTag;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.PromotionService;
import com.d2c.product.service.PromotionTagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/promotion")
public class PromotionController extends BaseController {

    @Autowired
    private PromotionService promotionService;
    @Autowired
    private PromotionTagService promotionTagService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 活动专场页
     *
     * @param id
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String promotion(@PathVariable Long id, PageModel page, ModelMap model, ProductSearcher searcher) {
        Promotion promotion = promotionService.findById(id);
        if (promotion == null) {
            throw new BusinessException("活动已经下架了，下次早点来哦！");
        }
        PageResult<SearcherProduct> pager = null;
        model.put("promotion", promotion);
        if ((isMobileDevice() && !StringUtils.isEmpty(promotion.getMobileCode()))
                || (!isMobileDevice() && !StringUtils.isEmpty(promotion.getCustomCode()))) {
        } else {
            if (promotion.getPromotionScope() == 0) {
                searcher.setPromotionId(id);
            } else if (promotion.getPromotionScope() == 1) {
                searcher.setOrderPromotionId(id);
            }
            ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
            pager = productSearcherQueryService.search(searcherBean, page);
            model.put("products", pager);
        }
        return "product/promotion_product_list";
    }

    /**
     * 活动页预览
     *
     * @param id
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/preview/{id}", method = RequestMethod.GET)
    public String preview(@PathVariable Long id, PageModel page, ModelMap model, ProductSearcher searcher) {
        Promotion promotion = promotionService.findById(id);
        if (promotion == null) {
            throw new BusinessException("活动已经下架了，下次早点来哦！");
        }
        PageResult<SearcherProduct> pager = null;
        model.put("promotion", promotion);
        if ((isMobileDevice() && !StringUtils.isEmpty(promotion.getMobileCode()))
                || (!isMobileDevice() && !StringUtils.isEmpty(promotion.getCustomCode()))) {
        } else {
            if (promotion.getPromotionScope() == 0) {
                searcher.setPromotionId(id);
            } else if (promotion.getPromotionScope() == 1) {
                searcher.setOrderPromotionId(id);
            }
            ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
            pager = productSearcherQueryService.search(searcherBean, page);
            model.put("products", pager);
        }
        return "product/promotion_product_list";
    }

    /**
     * 特殊活动专场商品列表
     *
     * @param id
     * @param model
     * @param hover
     * @param page
     * @return
     */
    @RequestMapping(value = "/productlist/{id}", method = RequestMethod.GET)
    public String promotionProductList(@PathVariable Long id, ModelMap model, Integer hover, PageModel page,
                                       ProductSearcher searcher) {
        Promotion promotion = promotionService.findById(id);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        if (promotion.getPromotionScope() == 0) {
            searcher.setPromotionId(id);
        } else if (promotion.getPromotionScope() == 1) {
            searcher.setOrderPromotionId(id);
        }
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcherBean, page);
        model.put("list", pager.getList());
        model.put("hover", hover);
        return "fragment/promotion_product_list";
    }

    /**
     * 标签活动列表
     *
     * @param tagId
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = {"/list", "/items"}, method = RequestMethod.GET)
    public String promotionList(Long tagId, ModelMap model, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        PromotionTag tag = promotionTagService.findById(tagId);
        if (tag == null) {
            throw new BusinessException("该标签不存在！");
        }
        model.put("tag4Promotion", tag);
        PageResult<Promotion> pager = promotionService.findByTagId(tagId, true, page);
        model.put("pager", pager);
        return "product/promotion_list";
    }

}
