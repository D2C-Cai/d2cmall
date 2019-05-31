package com.d2c.flame.controller.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.product.model.Promotion;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.PromotionService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 营销活动
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/promotion")
public class PromotionController extends BaseController {

    @Autowired
    private PromotionService promotionService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * 活动详情
     *
     * @param id
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult promotion(@PathVariable final Long id, PageModel page, ProductSearcher searcher) {
        ResponseResult result = new ResponseResult();
        Promotion promotion = promotionService.findById(id);
        if (promotion == null) {
            throw new BusinessException("活动不存在！");
        }
        String wapBanner = null;
        if (promotion.getWapBanner() != null) {
            Document document = Jsoup.parse(promotion.getWapBanner());
            Elements elements = document.getElementsByTag("img");
            wapBanner = (elements.size() <= 0 ? "" : elements.get(0).attr("src").replace("http://img.d2c.cn", ""));
        }
        result.put("wapBanner", wapBanner);
        result.put("name", promotion.getName());
        result.put("picture", promotion.getSmallPic());
        result.put("content", promotion.getDescription());
        promotion.setWapBanner(wapBanner);
        result.put("promotion", promotion.toJson());
        PageResult<SearcherProduct> pager = null;
        if (StringUtils.isNotBlank(promotion.getMobileCode())) {
            result.put("code", promotion.getMobileCode());
            return result;
        } else {
            if (promotion.getPromotionScope() == 0) {
                searcher.setPromotionId(id);
            } else if (promotion.getPromotionScope() == 1) {
                searcher.setOrderPromotionId(id);
            }
            ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
            pager = productSearcherQueryService.search(searcherBean, page);
            JSONArray array = new JSONArray();
            pager.getList().forEach(item -> array.add(item.toJson()));
            result.putPage("products", pager, array);
            return result;
        }
    }

    /**
     * 标签活动列表
     *
     * @param tagId
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(Long tagId, PageModel page) {
        ResponseResult result = new ResponseResult();
        PageResult<Promotion> pager = promotionService.findByTagId(tagId, true, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("promotions", pager, array);
        return result;
    }

}
