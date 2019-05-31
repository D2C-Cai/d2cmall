package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.Remind;
import com.d2c.logger.model.Remind.RemindType;
import com.d2c.logger.service.RemindService;
import com.d2c.member.model.MemberInfo;
import com.d2c.product.dto.FlashPromotionDto;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
@RequestMapping("/flashpromotion")
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
     * @param model
     * @return
     */
    @RequestMapping(value = "/product/session", method = RequestMethod.GET)
    public String productSession(ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Date yesterDay = DateUtil.getLastDay(1);
        List<FlashPromotion> list = flashPromotionService.findSession(yesterDay,
                new Date(yesterDay.getTime() + 3 * 24 * 3600 * 1000), promotionTypeEnum.FLASH.getCode(),
                channelType.MEMBER.name());
        model.put("list", list);
        model.put("currentId", 0);
        Date now = new Date();
        if (list != null && list.size() > 0) {
            Date recentlyTime = list.get(0).getStartDate();
            model.put("currentId", list.get(0).getId());
            for (int i = 0; i < list.size(); i++) {
                FlashPromotion f = list.get(i);
                if (!f.isEnd() && f.getStartDate().after(recentlyTime) && f.getStartDate().before(now)) {
                    model.put("currentId", f.getId());
                }
            }
        }
        return "/product/flashproduct_list";
    }

    /**
     * 限时购品牌场次
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/brand/session", method = RequestMethod.GET)
    public String brandSession(ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Date yesterDay = DateUtil.getLastDay(1);
        List<FlashPromotion> list = flashPromotionService.findSession(yesterDay,
                new Date(yesterDay.getTime() + 3 * 24 * 3600 * 1000), promotionTypeEnum.LUXURIES.getCode(),
                channelType.MEMBER.name());
        model.put("list", list);
        model.put("currentId", 0);
        Date now = new Date();
        if (list != null && list.size() > 0) {
            Date recentlyTime = list.get(0).getStartDate();
            model.put("currentId", list.get(0).getId());
            list.forEach(f -> {
                if (!f.isEnd() && f.getStartDate().after(recentlyTime) && f.getStartDate().before(now)) {
                    model.put("currentId", f.getId());
                }
            });
        }
        return "/product/flashbrand_list";
    }

    /**
     * 某个场次下品牌列表
     *
     * @param model
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/brand/list", method = RequestMethod.GET)
    public String brandList(ModelMap model, Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        FlashPromotion flashP = flashPromotionService.findById(id);
        List<FlashPromotion> list = flashPromotionService.findBySessionAndDate(flashP.getSession(),
                flashP.getStartDate(), promotionTypeEnum.LUXURIES.getCode(), flashP.getChannel());
        List<FlashPromotionDto> brands = new ArrayList<>();
        ProductSearcher searcher = new ProductSearcher();
        for (FlashPromotion fp : list) {
            searcher.setFlashPromotionId(fp.getId());
            ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
            PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcherBean, new PageModel(1, 6));
            FlashPromotionDto dto = new FlashPromotionDto();
            BeanUtils.copyProperties(fp, dto);
            dto.setProducts(pager.getList());
            brands.add(dto);
        }
        model.put("brands", brands);
        return "/product/flashbrand_detail";
    }

    /**
     * 某个场次下商品列表
     *
     * @param model
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/products/list", method = RequestMethod.GET)
    public String productList(ModelMap model, Long id, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        FlashPromotion fp = flashPromotionService.findById(id);
        model.put("flashPromotion", fp);
        ProductSearcher searcher = new ProductSearcher();
        searcher.setFlashPromotionId(id);
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcherBean, page);
        model.put("pager", pager);
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
            model.put("brandFlashPromotions", brandArray);
        }
        return "/product/flashbrand_detail";
    }

    /**
     * 提醒我
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/remind/{id}", method = RequestMethod.POST)
    public String remind(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Product product = productService.findById(id);
        Remind remind = new Remind();
        remind.setContent("您关注的秒杀商品即将开抢，限时限量，速速抢购！");
        remind.setMemberId(memberInfo.getId());
        remind.setMobile(memberInfo.getLoginCode());
        remind.setType(RemindType.FLASHPROMOTION.name());
        remind.setSourceId(id);
        if ((remind = remindService.insert(remind)) == null) {
            return "";
        }
        FlashPromotion fp = flashPromotionService.findById(product.getFlashPromotionId());
        Map<String, Object> map = new HashMap<>();
        map.put("remindId", remind.getId());
        map.put("flashPromotionId", fp.getId());
        map.put("memberId", memberInfo.getId());
        map.put("productName", product.getName());
        map.put("wapUrl", "http://dwz.cn/70Tk1M");
        map.put("picUrl", product.getProductImageCover());
        Long seconds = (fp.getStartDate().getTime() - System.currentTimeMillis() - 3 * 60 * 1000) / 1000; // 提醒倒计时时间
        MqEnum.FLASH_REMIND.send(map, seconds);
        return "";
    }

}
