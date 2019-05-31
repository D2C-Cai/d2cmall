package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherMemberCollection;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.Setting;
import com.d2c.order.service.CollageGroupService;
import com.d2c.order.service.SettingService;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.product.search.model.SearcherCollagePromotion;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.CollagePromotionSearcherBean;
import com.d2c.product.search.service.CollagePromotionSearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.CollagePromotionService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 拼团
 *
 * @author wwn
 * @version 3.0
 */
@Controller
@RequestMapping(value = "/collage")
public class CollageController extends BaseController {

    @Autowired
    private CollagePromotionService collagePromotionService;
    @Reference
    private CollagePromotionSearcherService collagePromotionSearcherService;
    @Autowired
    private CollageGroupService collageGroupService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private SettingService settingService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;

    /**
     * 拼团活动列表
     *
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = {"/list", "/products/list"}, method = RequestMethod.GET)
    public String list(PageModel page, ModelMap model) {
        CollagePromotionSearcherBean searcher = new CollagePromotionSearcherBean();
        ResponseResult result = new ResponseResult();
        PageResult<SearcherCollagePromotion> pager = collagePromotionSearcherService.search(searcher, page);
        List<String> productIds = new ArrayList<>();
        pager.getList().forEach(item -> productIds.add(item.getProductId().toString()));
        Map<Long, SearcherProduct> map = productSearcherQueryService
                .findMapByIds(productIds.toArray(new String[productIds.size()]));
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> {
            JSONObject obj = item.toJson();
            if (obj.get("begainDate") != null) {
                obj.put("begainDate", DateUtil.second2str(new Date(Long.parseLong(obj.get("begainDate").toString()))));
            }
            if (obj.get("endDate") != null) {
                obj.put("endDate", DateUtil.second2str(new Date(Long.parseLong(obj.get("endDate").toString()))));
            }
            SearcherProduct product = map.get(item.getProductId());
            obj.put("product", product.toJson());
            array.add(obj);
        });
        result.putPage("collageList", pager, array);
        model.put("result", result);
        return "product/collage_list";
    }

    /**
     * 拼团详情页
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Long id, ModelMap model) {
        promotionDetail(id, model);
        return "product/product_collage";
    }

    @RequestMapping(value = "/{id}/{productId}", method = RequestMethod.GET)
    public String detail2(@PathVariable("id") Long id, ModelMap model, @PathVariable("productId") Long productId) {
        promotionDetail(id, model);
        return "product/product_collage";
    }

    /**
     * 活动详情
     *
     * @param id
     * @param model
     */
    private void promotionDetail(Long id, ModelMap model) {
        CollagePromotion promotion = collagePromotionService.findById(id);
        model.put("collagePromotion", promotion.toJson());
        // 商品信息
        ProductDto dto = new ProductDto();
        Product product = productService.findById(promotion.getProductId());
        if (product == null || product.getMark() < 0) {
            throw new BusinessException("商品已下架！");
        }
        BeanUtils.copyProperties(product, dto);
        SearcherProduct searcherProduct = productSearcherQueryService.findById(promotion.getProductId().toString());
        // 是否收藏
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            SearcherMemberCollection smc = memberCollectionSearcherService.findByMemberAndProductId(memberInfo.getId(),
                    product.getId());
            if (smc != null && smc.getProductId() != null) {
                dto.setCollectioned(1);
            } else {
                dto.setCollectioned(0);
            }
        } catch (NotLoginException e) {
            dto.setCollectioned(0);
        }
        model.put("product", dto.toJson(searcherProduct));
        // 参团信息
        JSONArray groups = new JSONArray();
        PageResult<CollageGroup> pager = collageGroupService.findVaildByPromotionId(id, new PageModel(1, 2));
        pager.getList().forEach(item -> groups.add(item.toJson()));
        model.put("groupList", groups);
        // 库存信息
        List<ProductSku> productSKUSet = productSkuService.findByProductId(promotion.getProductId());
        JSONArray array = new JSONArray();
        for (ProductSku sku : productSKUSet) {
            array.add(sku.toJson());
        }
        model.put("skuList", array);
        // 全局返利系数
        Setting ratio = settingService.findByCode(Setting.REBATERATIO);
        model.put("ratio", Setting.defaultValue(ratio, new Integer(1)));
    }

}
