package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.enums.CouponSourceEnum;
import com.d2c.order.model.*;
import com.d2c.order.model.Coupon.CouponStatus;
import com.d2c.order.model.CouponDef.Association;
import com.d2c.order.query.CouponDefSearcher;
import com.d2c.order.query.CouponOrderSearcher;
import com.d2c.order.query.CouponSearcher;
import com.d2c.order.service.*;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.DesignerSearchBean;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.DesignerSearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/coupon")
public class CouponController extends BaseController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponDefGroupService couponDefGroupService;
    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private CouponDefGroupQueryService couponDefGroupQueryService;
    @Autowired
    private CouponOrderService couponOrderService;
    @Autowired
    private CouponDefRelationService couponDefRelationService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private DesignerSearcherService designerSearcherService;

    /**
     * 我的优惠券
     *
     * @param page
     * @param status
     * @return
     */
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public ResponseResult mine(PageModel page, String[] status) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        CouponSearcher searcher = new CouponSearcher();
        searcher.setMemberId(member.getId());
        searcher.setStatus(status);
        PageResult<Coupon> pager = couponQueryService.findByMemberId(member.getId(), searcher.getStatus(), page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("myCoupons", pager, array);
        return result;
    }

    /**
     * 优惠券数量
     *
     * @return
     */
    @RequestMapping(value = "/counts", method = RequestMethod.GET)
    public ResponseResult counts() {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        CouponSearcher searcher = new CouponSearcher();
        searcher.setMemberId(member.getId());
        searcher.setStatus(new String[]{CouponStatus.CLAIMED.name()});
        int count = couponQueryService.countByMemberId(member.getId(), searcher.getStatus());
        result.put("CLAIMED", count);
        return result;
    }

    /**
     * 固定组的券列表
     *
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/fix/list", method = RequestMethod.GET)
    public ResponseResult fixList(Long groupId) {
        ResponseResult result = new ResponseResult();
        CouponDefGroup group = couponDefGroupQueryService.findById(groupId);
        if (group == null) {
            throw new BusinessException("优惠券组不存在！");
        }
        if (StringUtils.isEmpty(group.getFixDefIds())) {
            throw new BusinessException("该优惠券组未绑定固定券！");
        }
        Long[] fixDefIds = StringUtil.strToLongArray(group.getFixDefIds());
        CouponDefSearcher searcher = new CouponDefSearcher();
        searcher.setCouponDefIds(fixDefIds);
        PageResult<CouponDef> pager = couponDefQueryService.findBySearcher(new PageModel(1, 50), searcher);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("fixCoupons", pager, array);
        return result;
    }

    /**
     * 可领用的优惠券（折扣券和现金券）
     *
     * @return
     */
    @RequestMapping(value = "/available/coupon", method = RequestMethod.GET)
    public ResponseResult availableList() {
        ResponseResult result = new ResponseResult();
        this.getLoginMemberInfo();
        List<CouponDef> couponDefs = couponDefQueryService.findAvailableCoupons();
        JSONArray array = new JSONArray();
        couponDefs.forEach(item -> array.add(item.toJson()));
        result.put("couponDefs", array);
        return result;
    }

    /**
     * 兑换密码券或暗码券
     *
     * @param password
     * @param code
     * @return
     */
    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public ResponseResult convert(@RequestParam(required = true) String password, String code) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        if (StringUtils.isNotBlank(password)) {
            Coupon coupon = couponService.doConvertCoupon(password.trim().toUpperCase(), member.getId());
            if (coupon != null) {
                return this.convertPasswd(result, coupon);
            } else {
                CouponDef couponDef = couponDefQueryService.findByCipher(password);
                if (couponDef != null) {
                    return this.convertCipher(result, member, couponDef, code);
                } else {
                    CouponDefGroup group = couponDefGroupQueryService.findByCipher(password);
                    if (group != null) {
                        return this.convertCipherGroup(result, member, group);
                    }
                }
            }
            throw new BusinessException("兑换码不正确，兑换不成功！");
        }
        return result;
    }

    /**
     * 兑换密码券
     *
     * @param result
     * @param coupon
     * @return
     */
    private ResponseResult convertPasswd(ResponseResult result, Coupon coupon) {
        result.put("coupon", coupon.toJson());
        result.setMsg("兑换成功，订单商品总价满" + coupon.getNeedAmount() + "金额使用！");
        return result;
    }

    /**
     * 兑换暗码券
     *
     * @param result
     * @param member
     * @param couponDef
     * @param code
     * @return
     */
    private ResponseResult convertCipher(ResponseResult result, MemberInfo member, CouponDef couponDef, String code) {
        Coupon coupon = couponDefService.doClaimedCoupon(couponDef.getId(), member.getId(), member.getLoginCode(),
                member.getLoginCode(), CouponSourceEnum.APP.name(), true);
        if (coupon != null) {
            result.setMsg("优惠券领取成功！");
        } else {
            throw new BusinessException("优惠券领取不成功！");
        }
        return result;
    }

    /**
     * 兑换暗码券包
     *
     * @param result
     * @param member
     * @param group
     * @return
     */
    private ResponseResult convertCipherGroup(ResponseResult result, MemberInfo member, CouponDefGroup group) {
        CouponGroup couponG = couponDefGroupService.doClaimedCoupon(group.getId(), member.getId(),
                member.getLoginCode(), member.getDisplayName(), CouponSourceEnum.APP.name());
        if (couponG != null) {
            result.setMsg("优惠券领取成功！");
        } else {
            throw new BusinessException("优惠券领取不成功！");
        }
        return result;
    }

    /**
     * 激活优惠券
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/activate/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult activate(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        int success = couponService.doActivateCoupon(id, memberInfo.getId());
        if (success == 0) {
            throw new BusinessException("优惠券激活不成功！");
        } else {
            result.setMsg("优惠券激活成功！");
        }
        return result;
    }

    /**
     * 领取优惠券
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/receive/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult receive(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        CouponDef couponDef = couponDefQueryService.findById(id);
        if (couponDef == null || couponDef.getId() == 1) {
            throw new BusinessException("优惠券不存在！");
        }
        Coupon coupon = couponDefService.doClaimedCoupon(couponDef.getId(), memberInfo.getId(),
                memberInfo.getLoginCode(), memberInfo.getLoginCode(), CouponSourceEnum.APP.name(), true);
        if (coupon != null) {
            result.put("coupon", coupon.toJson());
            result.setMsg("优惠券领取成功！");
        } else {
            throw new BusinessException("优惠券领取不成功！");
        }
        return result;
    }

    /**
     * 领取优惠券组
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/batch/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult batch(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        CouponDefGroup couponDefGroup = couponDefGroupQueryService.findById(id);
        if (couponDefGroup == null || couponDefGroup.getStatus() < 1) {
            throw new BusinessException("优惠券不存在！");
        }
        CouponGroup couponGroup = couponDefGroupService.doClaimedCoupon(couponDefGroup.getId(), memberInfo.getId(),
                memberInfo.getLoginCode(), memberInfo.getLoginCode(), CouponSourceEnum.APP.name());
        if (couponGroup != null) {
            result.put("couponGroup", couponGroup);
            result.setMsg("优惠券领取成功，已经成功放入您的账户，请到「我的D2C→我的优惠券」查看。");
        } else {
            throw new BusinessException("优惠券领取不成功！");
        }
        return result;
    }

    /**
     * 转增优惠券
     *
     * @param code
     * @param fromId
     * @return
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public ResponseResult transfer(String code, Long fromId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Coupon coupon = couponQueryService.findByCode(code, fromId);
        if (coupon == null) {
            throw new BusinessException("很抱歉分享的优惠券已被其它人抢走了...");
        }
        if (!coupon.getTransfer()) {
            throw new BusinessException("该优惠券不允许转让！");
        }
        if (1 == coupon.getExpired()) {
            throw new BusinessException("很抱歉分享的优惠券已过期...");
        }
        int success = couponService.doTransfer(coupon.getId(), memberInfo.getId(), memberInfo.getLoginCode());
        if (success == 0) {
            throw new BusinessException("优惠券转增不成功！");
        } else {
            result.setMsg("优惠券转增成功！");
        }
        return result;
    }

    /**
     * 转增优惠券详细
     *
     * @param id
     * @param fromId
     * @return
     */
    @RequestMapping(value = "/transfer/{code}", method = RequestMethod.GET)
    public ResponseResult info(@PathVariable String code, Long fromId) {
        ResponseResult result = new ResponseResult();
        Coupon coupon = couponQueryService.findByCode(code, null);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在！");
        }
        result.put("coupon", coupon.toJson());
        MemberInfo memberInfo = memberInfoService.findById(fromId);
        result.put("from", memberInfo.toJson());
        return result;
    }

    /**
     * 优惠券商品和品牌
     *
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/relations/{id}", method = RequestMethod.GET)
    public ResponseResult relations(@PathVariable Long id, PageModel page) {
        ResponseResult result = new ResponseResult();
        Coupon coupon = couponQueryService.findById(id);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在！");
        }
        result.put("coupon", coupon.toJson());
        CouponDef couponDef = couponDefService.findById(coupon.getDefineId());
        List<CouponDefRelation> relations = couponDefRelationService.findByCidAndTids(null, couponDef.getId(),
                couponDef.getCheckAssociation());
        if (relations == null || relations.size() == 0) {
            result.put("brands", new JSONArray());
            result.put("products", new JSONArray());
            return result;
        }
        List<String> ids = new ArrayList<>();
        relations.forEach(r -> ids.add(String.valueOf(r.getTargetId())));
        JSONArray array = new JSONArray();
        switch (Association.valueOf(couponDef.getCheckAssociation())) {
            case PRODUCT:
                ProductSearcher pSearcher = new ProductSearcher();
                ProductProSearchQuery searcherBean = pSearcher.convertSearchQuery();
                searcherBean.setProductIds(ids);
                PageResult<SearcherProduct> products = productSearcherQueryService.search(searcherBean, page);
                products.getList().forEach(item -> array.add(item.toJson()));
                result.putPage("products", products, array);
                return result;
            case DESIGNER:
                DesignerSearchBean dSearcher = new DesignerSearchBean();
                dSearcher.setIds((String[]) ids.toArray(new String[0]));
                PageResult<SearcherDesigner> brands = designerSearcherService.search(dSearcher, page);
                for (SearcherDesigner item : brands.getList()) {
                    JSONObject obj = item.toJson();
                    ProductSearcher search = new ProductSearcher();
                    ProductProSearchQuery searchBean = search.convertSearchQuery();
                    PageResult<SearcherProduct> pager = productSearcherQueryService.findSaleProductByDesigner(item.getId(),
                            new PageModel(1, 6), searchBean);
                    JSONArray productArray = new JSONArray();
                    pager.getList().forEach(p -> productArray.add(p.toJson()));
                    obj.put("products", productArray);
                    array.add(obj);
                }
                result.putPage("brands", brands, array);
                return result;
            default:
                return result;
        }
    }

    /**
     * 购买优惠券
     *
     * @param defId
     * @return
     */
    @RequestMapping(value = "/create/order", method = RequestMethod.POST)
    public ResponseResult createOrder(Long defId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        CouponDef couponDef = couponDefQueryService.findById(defId);
        if (couponDef == null || couponDef.getId() == 1) {
            throw new BusinessException("优惠券不存在！");
        }
        if (couponDef.getFree() == 1) {
            throw new BusinessException("免费优惠券不能购买！");
        }
        if (!(couponDef.getEnable() == 1 && couponDef.checkClaimDate())) {
            throw new BusinessException("优惠券不在购买时间范围内！");
        }
        if (couponDef.getClaimed() >= couponDef.getQuantity()) {
            throw new BusinessException("优惠券已经售罄了，下次早点来哦！");
        }
        CouponOrderSearcher searcher = new CouponOrderSearcher();
        searcher.setMemberId(memberInfo.getId());
        searcher.setCouponDefId(couponDef.getId());
        int count = couponOrderService.countBySearcher(searcher);
        if (count >= couponDef.getClaimLimit()) {
            throw new BusinessException("此优惠券每人只能购买" + couponDef.getClaimLimit() + "张，不要贪心哦！");
        }
        CouponOrder old = couponOrderService.findByMemberIdAndCouponDefId(memberInfo.getId(), defId);
        if (old == null) {
            old = new CouponOrder(couponDef);
            old.setMemberId(memberInfo.getId());
            old.setLoginCode(memberInfo.getLoginCode());
            old.setQuantity(1);
            old.setTotalAmount(old.getCouponPrice().multiply(new BigDecimal(old.getQuantity())));
            old = couponOrderService.insert(old);
        }
        result.put("couponOrder", old.toJson());
        return result;
    }

}
