package com.d2c.flame.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberLotto.LotteryOpportunityEnum;
import com.d2c.order.mongo.model.BargainHelpDO;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.mongo.service.BargainHelpService;
import com.d2c.order.mongo.service.BargainPriceService;
import com.d2c.order.query.BargainPriceSearcher;
import com.d2c.order.service.tx.BargainPriceTxService;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.model.BargainRule;
import com.d2c.product.model.ProductSku;
import com.d2c.product.search.model.SearcherBargainPromotion;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.BargainPromotionSearchBean;
import com.d2c.product.search.service.BargainPromotionSearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BargainPromotionService;
import com.d2c.product.service.BargainRuleService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 砍价活动
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping("/v3/api/bargain")
public class BargainController extends BaseController {

    @Autowired
    private BargainPromotionService bargainPromotionService;
    @Reference
    private BargainPromotionSearcherService bargainPromotionSearcherService;
    @Autowired
    private BargainPriceService bargainPriceService;
    @Autowired
    private BargainHelpService bargainHelpService;
    @Autowired
    private BargainRuleService bargainRuleService;
    @Autowired
    private ProductSkuService productSkuService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private BargainPriceTxService bargainPriceTxService;

    /**
     * 砍价活动列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/promotion/list", method = RequestMethod.GET)
    public ResponseResult promotionList(BargainPromotionSearchBean searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        PageResult<SearcherBargainPromotion> pager = bargainPromotionSearcherService.search(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("list", pager, array);
        return result;
    }

    /**
     * 砍价活动明细
     *
     * @param promotionId
     * @return
     */
    @RequestMapping(value = "/detail/{promotionId}", method = RequestMethod.GET)
    public ResponseResult promotionDetail(@PathVariable("promotionId") Long promotionId) {
        ResponseResult result = new ResponseResult();
        SearcherBargainPromotion bargainPromotion = bargainPromotionSearcherService.findById(promotionId);
        result.put("bargainPromotion", bargainPromotion.toJson());
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            BargainPriceDO oldBargainPrice = bargainPriceService.findMineByPromotionId(promotionId, memberInfo.getId());
            if (oldBargainPrice != null) {
                result.put("bargainPriceId", oldBargainPrice.getId());
            }
        } catch (NotLoginException e) {
        }
        List<ProductSku> productSkus = productSkuService.findByProductId(bargainPromotion.getProductId());
        JSONArray array = new JSONArray();
        productSkus.forEach(sku -> array.add(sku.toJson()));
        result.put("skus", array);
        return result;
    }

    /**
     * 创建个人砍价
     *
     * @param promotionId
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseResult create(Long promotionId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        BargainPromotion bargainPromotion = bargainPromotionService.findById(promotionId);
        if (bargainPromotion == null || bargainPromotion.isOver()) {
            throw new BusinessException("该砍价活动已结束！");
        }
        SearcherProduct product = productSearcherQueryService.findById(bargainPromotion.getProductId().toString());
        if (product == null || product.getMark() < 1 || product.getStore() < 1) {
            throw new BusinessException("该商品已下架，砍价活动结束！");
        }
        BargainPriceDO oldBargainPrice = bargainPriceService.findMineByPromotionId(promotionId, memberInfo.getId());
        if (oldBargainPrice != null) {
            throw new BusinessException("该商品您已经发起过砍价啦！");
        }
        BargainPriceSearcher searcher = new BargainPriceSearcher();
        searcher.setMemberId(memberInfo.getId());
        searcher.setStartDate(DateUtil.str2second("2019-05-12 00:00:00"));
        searcher.setEndDate(DateUtil.str2second("2019-05-21 00:00:00"));
        int totalCount = bargainPriceService.countBySearcher(searcher);
        if (totalCount >= 3) {
            throw new BusinessException("本次活动时间段内您最多发起3次砍价！");
        }
        BargainPriceDO bargainPrice = new BargainPriceDO(memberInfo, bargainPromotion, product.getMinPrice(),
                product.getName());
        bargainPrice = bargainPriceService.create(bargainPrice);
        if (bargainPrice.getId() != null) {
            // 增加抽奖次数
            Map<String, Object> map = new HashMap<>();
            map.put("memberId", memberInfo.getId());
            map.put("lotteryOpportunityEnum", LotteryOpportunityEnum.BARGAIN.name());
            MqEnum.AWARD_QUALIFIED.send(map);
        }
        result.put("bargainPriceDO", bargainPrice);
        result.put("isBind", true);
        return result;
    }

    /**
     * 获得我的砍价清单
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/mine/list", method = RequestMethod.GET)
    public ResponseResult myBargainList(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PageResult<BargainPriceDO> pager = bargainPriceService.findMyBargainList(memberInfo.getId(), page);
        result.put("pager", pager);
        return result;
    }

    /**
     * 个人砍价明细
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/personal/{id}", method = RequestMethod.GET)
    public ResponseResult myBargainDetail(@PathVariable("id") String id) {
        ResponseResult result = new ResponseResult();
        BargainPriceDO bargainPriceDO = bargainPriceService.findById(id);
        if (bargainPriceDO == null) {
            throw new BusinessException("砍价活动不存在！");
        }
        PageResult<BargainHelpDO> pager = bargainHelpService.findByBargainId(bargainPriceDO.getId(), new PageModel());
        bargainPriceDO.setHelpers(pager.getList());
        result.put("bargainPriceDO", bargainPriceDO);
        BargainRule bargainRule = bargainRuleService.findFirst(bargainPriceDO.getBargainId());
        result.put("max", bargainRule.getMax());
        SearcherProduct product = productSearcherQueryService
                .findById(bargainPriceDO.getBargain().getProductId().toString());
        result.put("product", product.toJson());
        List<ProductSku> productSkus = productSkuService.findByProductId(product.getProductId());
        JSONArray array = new JSONArray();
        productSkus.forEach(sku -> array.add(sku.toJson()));
        result.put("skus", array);
        return result;
    }

    /**
     * 好友帮忙砍价
     *
     * @param bargainPriceId
     * @return
     */
    @RequestMapping(value = "/add/help", method = RequestMethod.POST)
    public ResponseResult addBargainHelp(String bargainPriceId) {
        ResponseResult result = new ResponseResult();
        MemberDto memberDto = this.getLoginDto();
        if (memberDto.isD2c()) {
            memberDto.setUnionId(memberDto.getLoginCode());
        }
        BargainPriceDO bargainPrice = bargainPriceService.findById(bargainPriceId);
        if (bargainPrice == null) {
            throw new BusinessException("该砍价活动不存在！");
        }
        BargainPromotion bargainPromotion = bargainPromotionService.findById(bargainPrice.getBargainId());
        if (bargainPromotion == null || bargainPromotion.isOver()) {
            throw new BusinessException("该砍价活动已结束！");
        }
        // 当前价格
        BigDecimal nowPrice = new BigDecimal(bargainPrice.getPrice()).setScale(2, RoundingMode.HALF_UP);
        if (nowPrice.compareTo(bargainPromotion.getMinPrice()) <= 0) {
            throw new BusinessException("该砍价商品已经是最低价，看看其他砍价商品吧！");
        }
        int count = bargainHelpService.countByUnionIdAndBarginId(memberDto.getUnionId(), bargainPriceId);
        if (count > 0) {
            throw new BusinessException("您已经砍过该商品啦！");
        }
        int totalCount = bargainHelpService.countByUnionIdAndDate(memberDto.getUnionId(),
                DateUtil.getStartOfDay(new Date()), DateUtil.getEndOfDay(new Date()));
        if (totalCount >= 3) {
            throw new BusinessException("每人一天只能帮助3次，您已超过上限了哦！");
        }
        BargainRule bargainRule = bargainRuleService.findUpperRule(bargainPrice.getBargainId(), nowPrice);
        if (bargainRule == null) {
            bargainRule = bargainRuleService.findLast(bargainPrice.getBargainId());
        }
        // 砍掉价格
        BigDecimal subPrice = new BigDecimal(
                Math.random() * (bargainRule.getMax() - bargainRule.getMin() + 1) + bargainRule.getMin()).setScale(0,
                RoundingMode.DOWN);
        if (nowPrice.subtract(subPrice).compareTo(bargainPrice.getBargain().getMinPrice()) < 0) {
            subPrice = nowPrice.subtract(bargainPrice.getBargain().getMinPrice()).setScale(2);
        }
        BargainHelpDO bargainHelper = new BargainHelpDO(bargainPriceId, subPrice.doubleValue(), memberDto.getId(),
                memberDto.getUnionId(), memberDto.getNickname(), memberDto.getHeadPic());
        // 修改价格
        bargainPrice.setPrice((nowPrice.subtract(subPrice)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        bargainPrice.setGmtModified(new Date());
        bargainPriceService.doBargain(bargainPrice, bargainHelper, bargainRule.getGradePrice(), subPrice);
        BigDecimal ratio = new BigDecimal("0.025");
        result.put("result", 1);
        result.put("price", subPrice);
        result.put("ratio", ratio);
        if (memberDto.isD2c() && !bargainPrice.getMemberId().equals(memberDto.getId())) {
            // 增加抽奖次数
            Map<String, Object> map = new HashMap<>();
            map.put("memberId", memberDto.getId());
            map.put("lotteryOpportunityEnum", LotteryOpportunityEnum.BARGAIN.name());
            MqEnum.AWARD_QUALIFIED.send(map);
        }
        return result;
    }

    /**
     * 开抢提醒
     *
     * @param promotionId
     * @return
     */
    @RequestMapping(value = "/remind/{promotionId}", method = RequestMethod.GET)
    public ResponseResult remindMe(@PathVariable("promotionId") Long promotionId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SearcherBargainPromotion bargainPromotion = bargainPromotionSearcherService.findById(promotionId);
        if (bargainPromotion == null) {
            throw new BusinessException("该砍价商品已下架，看看其他砍价商品吧！");
        }
        int success = bargainPromotionService.doRemind(memberInfo.getId(), memberInfo.getLoginCode(), bargainPromotion);
        result.put("result", success);
        return result;
    }

    /**
     * 砍价活动跑马灯
     *
     * @param promotionId
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/newest/{promotionId}", method = RequestMethod.GET)
    public ResponseResult latestPrice(@PathVariable("promotionId") Long promotionId, PageModel page) {
        ResponseResult result = new ResponseResult();
        List<BargainPriceDO> list = bargainPriceService.findLatest(promotionId, page, null);
        JSONArray array = new JSONArray();
        for (BargainPriceDO priceDo : list) {
            if (priceDo.getPrice() < priceDo.getOriginalPrice()) {
                JSONObject obj = new JSONObject();
                obj.put("nickname", priceDo.getNickname());
                obj.put("headPic", priceDo.getPic());
                obj.put("price", priceDo.getPrice());
                array.add(obj);
            }
        }
        result.put("list", array);
        return result;
    }

}
