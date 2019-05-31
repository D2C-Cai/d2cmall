package com.d2c.flame.controller.product;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.Remind;
import com.d2c.logger.model.Remind.RemindType;
import com.d2c.logger.service.RemindService;
import com.d2c.member.model.MemberInfo;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.model.FlashPromotion.channelType;
import com.d2c.product.model.FlashPromotion.promotionTypeEnum;
import com.d2c.product.model.Product;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.FlashPromotionService;
import com.d2c.product.service.ProductService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 限时购
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/flashpromotion")
public class FlashPromotionController extends BaseController {

    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private RemindService remindService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private ProductService productService;

    /**
     * 限时购商品场次
     *
     * @return
     */
    @RequestMapping(value = "/product/session", method = RequestMethod.GET)
    public ResponseResult productSession(String channel) {
        ResponseResult result = new ResponseResult();
        if (channel == null) {
            channel = channelType.MEMBER.name();
        }
        Date yesterDay = DateUtil.getLastDay(1);
        List<FlashPromotion> list = flashPromotionService.findSession(yesterDay,
                new Date(yesterDay.getTime() + 3 * 24 * 3600 * 1000), promotionTypeEnum.FLASH.getCode(), channel);
        JSONArray array = new JSONArray();
        list.forEach(fp -> array.add(fp.toJson()));
        result.put("list", array);
        result.put("currentId", 0);
        Date now = new Date();
        if (list != null && list.size() > 0) {
            Date recentlyTime = list.get(0).getStartDate();
            result.put("currentId", list.get(0).getId());
            for (int i = 0; i < list.size(); i++) {
                FlashPromotion f = list.get(i);
                if (!f.isEnd() && f.getStartDate().after(recentlyTime) && f.getStartDate().before(now)) {
                    result.put("currentId", f.getId());
                }
            }
        }
        return result;
    }

    /**
     * 限时购品牌场次
     *
     * @return
     */
    @RequestMapping(value = "/brand/session", method = RequestMethod.GET)
    public ResponseResult brandSession(String channel) {
        ResponseResult result = new ResponseResult();
        if (channel == null) {
            channel = channelType.MEMBER.name();
        }
        Date yesterDay = DateUtil.getLastDay(1);
        List<FlashPromotion> list = flashPromotionService.findSession(yesterDay,
                new Date(yesterDay.getTime() + 3 * 24 * 3600 * 1000), promotionTypeEnum.LUXURIES.getCode(), channel);
        JSONArray array = new JSONArray();
        list.forEach(fp -> array.add(fp.toJson()));
        result.put("list", array);
        result.put("currentId", 0);
        Date now = new Date();
        if (list != null && list.size() > 0) {
            Date recentlyTime = list.get(0).getStartDate();
            result.put("currentId", list.get(0).getId());
            list.forEach(f -> {
                if (!f.isEnd() && f.getStartDate().after(recentlyTime) && f.getStartDate().before(now)) {
                    result.put("currentId", f.getId());
                }
            });
        }
        return result;
    }

    /**
     * 场次下品牌列表
     *
     * @param promotionId
     * @return
     */
    @RequestMapping(value = "/brand/list", method = RequestMethod.GET)
    public ResponseResult brandList(Long promotionId) {
        ResponseResult result = new ResponseResult();
        FlashPromotion flashP = flashPromotionService.findById(promotionId);
        if (flashP == null) {
            result.put("brands", new JSONArray());
            return result;
        }
        List<FlashPromotion> list = flashPromotionService.findBySessionAndDate(flashP.getSession(),
                flashP.getStartDate(), promotionTypeEnum.LUXURIES.getCode(), flashP.getChannel());
        JSONArray array = new JSONArray();
        ProductSearcher searcher = new ProductSearcher();
        for (FlashPromotion fp : list) {
            searcher.setFlashPromotionId(fp.getId());
            ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
            PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcherBean, new PageModel(1, 6));
            JSONObject obj = fp.toJson();
            JSONArray productArray = new JSONArray();
            pager.getList().forEach(p -> productArray.add(p.toJson()));
            obj.put("products", productArray);
            array.add(obj);
        }
        result.put("brands", array);
        return result;
    }

    /**
     * 场次下商品列表
     *
     * @param promotionId
     * @param page
     * @return
     */
    @RequestMapping(value = "/products/list", method = RequestMethod.GET)
    public ResponseResult productList(Long promotionId, PageModel page) {
        ResponseResult result = new ResponseResult();
        FlashPromotion fp = flashPromotionService.findById(promotionId);
        if (fp == null) {
            throw new BusinessException("活动已经过期！");
        }
        result.put("flashPromotion", fp.toJson());
        ProductSearcher searcher = new ProductSearcher();
        searcher.setFlashPromotionId(promotionId);
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcherBean, page);
        JSONArray array = new JSONArray();
        for (SearcherProduct p : pager.getList()) {
            JSONObject pJson = p.toJson();
            pJson.putAll(productService.findFlashStore(p.getId()));
            array.add(pJson);
        }
        result.putPage("products", pager, array);
        if (promotionTypeEnum.FLASH.getCode() == fp.getPromotionScope()) {
            List<FlashPromotion> list = flashPromotionService.findBySessionAndDate(fp.getSession(), fp.getStartDate(),
                    promotionTypeEnum.LUXURIES.getCode(), fp.getChannel());
            JSONArray brandArray = new JSONArray();
            for (FlashPromotion brandFP : list) {
                searcherBean.setFlashPromotionId(brandFP.getId());
                PageResult<SearcherProduct> brandProductPager = productSearcherQueryService.search(searcherBean,
                        new PageModel(1, 6));
                JSONObject obj = brandFP.toJson();
                JSONArray productArray = new JSONArray();
                brandProductPager.getList().forEach(p -> productArray.add(p.toJson()));
                obj.put("products", productArray);
                brandArray.add(obj);
            }
            result.put("brandFlashPromotions", brandArray);
        }
        return result;
    }

    /**
     * 提醒我
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/remind/{productId}", method = RequestMethod.POST)
    public ResponseResult remind(@PathVariable Long productId) {
        ResponseResult result = new ResponseResult();
        result.put("remindTime", 3);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Product product = productService.findById(productId);
        Remind remind = new Remind();
        remind.setContent("您关注的限时购商品即将开抢，限时限量，速速抢购！");
        remind.setMemberId(memberInfo.getId());
        remind.setMobile(memberInfo.getLoginCode());
        remind.setType(RemindType.FLASHPROMOTION.name());
        remind.setSourceId(productId);
        if ((remind = remindService.insert(remind)) == null) {
            return result;
        }
        FlashPromotion fp = flashPromotionService.findById(product.getFlashPromotionId());
        Map<String, Object> map = new HashMap<>();
        map.put("remindId", remind.getId());
        map.put("flashPromotionId", fp.getId());
        map.put("memberId", memberInfo.getId());
        map.put("productName", product.getName());
        map.put("wapUrl", "http://dwz.cn/70Tk1M");
        map.put("picUrl", product.getProductImageCover());
        // 提醒倒计时时间
        Long seconds = (fp.getStartDate().getTime() - System.currentTimeMillis() - 3 * 60 * 1000) / 1000;
        MqEnum.FLASH_REMIND.send(map, seconds);
        return result;
    }

}