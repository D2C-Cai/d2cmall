package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.LoginService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.enums.CouponSourceEnum;
import com.d2c.order.model.*;
import com.d2c.order.query.CouponSearcher;
import com.d2c.order.service.*;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.DesignerSearchBean;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.DesignerSearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.util.string.LoginUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private CouponDefGroupService couponDefGroupService;
    @Autowired
    private CouponDefGroupQueryService couponDefGroupQueryService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private CouponDefRelationService couponDefRelationService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Reference
    private DesignerSearcherService designerSearcherService;

    /**
     * 我的优惠券
     *
     * @param searcher
     * @param model
     * @param page
     * @return
     */
    @RequestMapping(value = "/myCoupon", method = RequestMethod.GET)
    public String myCoupon(CouponSearcher searcher, ModelMap model, PageModel page) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        searcher.analysisStatus();
        PageResult<Coupon> pager = couponQueryService.findByMemberId(memberInfo.getId(), searcher.getStatus(), page);
        model.put("pager", pager);
        return "member/my_coupon";
    }

    /**
     * 兑换密码券/暗码券（需要登录）
     *
     * @param model
     * @param password
     * @return
     */
    @RequestMapping(value = "/convertCoupon", method = RequestMethod.POST)
    public String convertCoupon(ModelMap model, String password) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (memberInfo != null && StringUtils.isNotBlank(password)) {
            Coupon coupon = couponService.doConvertCoupon(password.trim().toUpperCase(), memberInfo.getId());
            if (coupon != null) {
                return this.convertPasswd(result, coupon);
            }
            CouponDef couponDef = couponDefQueryService.findByCipher(password);
            if (couponDef != null) {
                return this.convertCipher(result, memberInfo, couponDef);
            }
            CouponDefGroup group = couponDefGroupQueryService.findByCipher(password);
            if (group != null) {
                return this.convertCipherGroup(result, memberInfo, group);
            }
            throw new BusinessException("暗码不正确，兑换不成功！");
        }
        return "";
    }

    /**
     * 兑换密码券
     *
     * @param result
     * @param coupon
     * @return
     */
    private String convertPasswd(SuccessResponse result, Coupon coupon) {
        result.put("coupon", coupon.toJson());
        result.setMsg("兑换成功，订单商品总价满" + coupon.getNeedAmount() + "金额使用！");
        return "";
    }

    /**
     * 兑换暗码券
     *
     * @param result
     * @param member
     * @param couponDef
     * @return
     */
    private String convertCipher(SuccessResponse result, MemberInfo member, CouponDef couponDef) {
        Coupon coupon = couponDefService.doClaimedCoupon(couponDef.getId(), member.getId(), member.getLoginCode(),
                member.getLoginCode(), CouponSourceEnum.D2CMALL.name(), true);
        if (coupon != null) {
            result.setMsg("优惠券领取成功！");
        } else {
            throw new BusinessException("优惠券领取不成功！");
        }
        return "";
    }

    /**
     * 兑换暗码券包
     *
     * @param result
     * @param member
     * @param group
     * @return
     */
    private String convertCipherGroup(SuccessResponse result, MemberInfo member, CouponDefGroup group) {
        CouponGroup couponG = couponDefGroupService.doClaimedCoupon(group.getId(), member.getId(),
                member.getLoginCode(), member.getDisplayName(), CouponSourceEnum.D2CMALL.name());
        if (couponG != null) {
            result.setMsg("优惠券领取成功！");
        } else {
            throw new BusinessException("优惠券领取不成功！");
        }
        return "";
    }

    /**
     * 点击链接获取优惠券（需要登录）
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/receive/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String receiveCouponNeedLogin(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        result.setMsg("优惠券领取成功！");
        CouponDef couponDef = couponDefQueryService.findById(id);
        if (couponDef == null || couponDef.getId() == 1) {
            throw new BusinessException("优惠券不存在！");
        }
        couponDefService.doClaimedCoupon(couponDef.getId(), memberInfo.getId(), memberInfo.getLoginCode(),
                memberInfo.getLoginCode(), CouponSourceEnum.D2CMALL.name(), true);
        String redirectUrl = couponDef.getRedirectUrl();
        if (!StringUtils.isEmpty(redirectUrl)) {
            return "redirect:" + redirectUrl;
        }
        if (isMobileDevice()) {
            return "cms/coupon_success";
        }
        return "";
    }

    /**
     * 点击链接批量领取优惠券（需要登录）
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/batch/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String batchCouponNeedLogin(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        CouponDefGroup couponDefGroup = couponDefGroupQueryService.findById(id);
        if (couponDefGroup == null || couponDefGroup.getStatus() < 1) {
            throw new BusinessException("优惠券不存在！");
        }
        CouponGroup couponGroup = couponDefGroupService.doClaimedCoupon(couponDefGroup.getId(), memberInfo.getId(),
                memberInfo.getLoginCode(), null, CouponSourceEnum.D2CMALL.name());
        if (couponGroup != null) {
            result.put("coupon", couponGroup);
            result.setMsg("优惠券领取成功，已经成功放入您的账户，请到「我的D2C→我的优惠券」查看。");
        }
        if (isMobileDevice()) {
            return "cms/coupon_success";
        }
        return "";
    }

    /**
     * 根据手机号，批量领取优惠券（不需要注册，需要验证码）
     *
     * @param model
     * @param id
     * @param mobile
     * @param code
     * @return
     */
    @RequestMapping(value = "/batch/mobile/{id}", method = RequestMethod.POST)
    public String batchCouponNeedMobile(ModelMap model, @PathVariable Long id,
                                        @RequestParam(required = true) String mobile, @RequestParam(required = true) String code) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        String message = loginService.doVerify(mobile, code);
        if (!message.equalsIgnoreCase("OK")) {
            throw new BusinessException(message);
        }
        if (!LoginUtil.checkMobile(mobile)) {
            throw new BusinessException("手机号格式不正确！");
        }
        CouponDefGroup couponDefGroup = couponDefGroupQueryService.findById(id);
        if (couponDefGroup == null || couponDefGroup.getStatus() < 1) {
            throw new BusinessException("优惠券不存在！");
        }
        MemberInfo memberInfo = memberInfoService.findByLoginCode(mobile);
        Long memberId = 0L;
        if (memberInfo != null) {
            memberId = memberInfo.getId();
        }
        CouponGroup couponGroup = couponDefGroupService.doClaimedCoupon(couponDefGroup.getId(), memberId, mobile, null,
                CouponSourceEnum.D2CMALL.name());
        if (couponGroup != null) {
            result.put("coupon", couponGroup);
            result.setMsg("优惠券领取成功，已经成功放入您的账户，请到「我的D2C→我的优惠券」查看。");
        }
        return "";
    }

    /**
     * 门店核销优惠券（需要登录）
     *
     * @param model
     * @param code
     * @return
     */
    @RequestMapping(value = "/usedCoupon", method = RequestMethod.POST)
    public String usedCoupon(ModelMap model, String code) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (memberInfo.getStoreId() == null) {
            throw new BusinessException("没有兑换使用权限！");
        } else if (StringUtils.isNotBlank(code)) {
            Store store = storeService.findById(memberInfo.getStoreId());
            Coupon coupon = couponService.doUsedCoupon(code, memberInfo.getId(), store.getId());
            if (coupon != null) {
                result.setMsg("兑换使用成功！");
                result.put("coupon", coupon);
            } else {
                throw new BusinessException("兑换使用不成功！");
            }
        }
        return "";
    }

    /**
     * 激活领用现金券（需要登录）
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public String activateCoupon(ModelMap model, Long id) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        int success = couponService.doActivateCoupon(id, memberInfo.getId());
        if (success <= 0) {
            throw new BusinessException("激活不成功！");
        } else {
            result.setMsg("激活成功！");
        }
        return "";
    }

    /**
     * 转增优惠券（需要登录）
     *
     * @param model
     * @param code
     * @param fromId
     * @return
     */
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String transferCoupon(ModelMap model, String code, Long fromId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
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
        return "";
    }

    /**
     * 转增优惠券详细
     *
     * @param model
     * @param id
     * @param fromId
     * @return
     */
    @RequestMapping(value = "/transfer/{code}", method = RequestMethod.GET)
    public String transferInfo(ModelMap model, @PathVariable String code, Long fromId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Coupon coupon = couponQueryService.findByCode(code, null);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在！");
        }
        result.put("coupon", coupon);
        MemberInfo memberInfo = memberInfoService.findById(fromId);
        result.put("from", memberInfo.toJson());
        if (!coupon.getMemberId().equals(fromId)) {
            MemberInfo reciever = memberInfoService.findById(coupon.getMemberId());
            result.put("reciever", reciever.toJson());
        }
        return "member/transfer_coupon";
    }

    /**
     * 优惠券商品列表
     *
     * @param id
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/products/coupon/{id}", method = RequestMethod.GET)
    public String products(ModelMap model, @PathVariable Long id, ProductSearcher searcher, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Coupon coupon = couponQueryService.findById(id);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在！");
        }
        result.put("coupon", coupon);
        CouponDef couponDef = couponDefService.findById(coupon.getDefineId());
        List<CouponDefRelation> relations = couponDefRelationService.findByCidAndTids(null, couponDef.getId(),
                couponDef.getCheckAssociation());
        List<String> ids = new ArrayList<>();
        relations.forEach(r -> ids.add(String.valueOf(r.getTargetId())));
        ProductProSearchQuery searcherBean = searcher.convertSearchQuery();
        searcherBean.setProductIds(ids);
        PageResult<SearcherProduct> pager = productSearcherQueryService.search(searcherBean, page);
        result.put("products", pager);
        return "";
    }

    /**
     * 优惠券品牌列表
     *
     * @param id
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/brands/coupon/{id}", method = RequestMethod.GET)
    public String brands(ModelMap model, @PathVariable Long id, DesignerSearchBean searcher, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Coupon coupon = couponQueryService.findById(id);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在！");
        }
        result.put("coupon", coupon);
        CouponDef couponDef = couponDefService.findById(coupon.getDefineId());
        List<CouponDefRelation> relations = couponDefRelationService.findByCidAndTids(null, couponDef.getId(),
                couponDef.getCheckAssociation());
        List<String> ids = new ArrayList<>();
        relations.forEach(r -> ids.add(String.valueOf(r.getTargetId())));
        searcher.setIds((String[]) ids.toArray(new String[0]));
        PageResult<SearcherDesigner> pager = designerSearcherService.search(searcher, page);
        result.put("brands", pager);
        return "";
    }

}
