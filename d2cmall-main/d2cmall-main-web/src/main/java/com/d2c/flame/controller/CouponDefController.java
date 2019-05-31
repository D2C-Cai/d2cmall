package com.d2c.flame.controller;

import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.enums.CouponSourceEnum;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.model.CouponGroup;
import com.d2c.order.model.CouponOrder;
import com.d2c.order.query.CouponOrderSearcher;
import com.d2c.order.service.*;
import com.d2c.util.string.LoginUtil;
import com.d2c.util.string.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/coupondef")
public class CouponDefController extends BaseController {

    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private CouponDefGroupService couponDefGroupService;
    @Autowired
    private CouponDefGroupQueryService couponDefGroupQueryService;
    @Autowired
    private CouponOrderService couponOrderService;
    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 单张领取入口
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String receiveCouponEntry(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        CouponDef couponDef = couponDefQueryService.findById(id);
        if (couponDef == null || couponDef.getId() <= 1) {
            throw new BusinessException("优惠券不存在！");
        }
        result.put("coupon", couponDef);
        MemberInfo memberInfo = null;
        try {
            memberInfo = this.getLoginMemberInfo();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        String randomStr = RandomUtil.getRandomString(5);
        result.put("code", randomStr);
        if (memberInfo != null) {
            result.put("member", memberInfo);
            result.put("receiveUrl", "/coupon/receive/" + couponDef.getId() + ".json");
        } else {
            result.put("receiveUrl", "/coupondef/receive/mobile/" + couponDef.getId() + ".json");
        }
        return "cms/coupon_receive";
    }

    /**
     * 批量领取入口
     *
     * @param model
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/batch/{groupId}", method = RequestMethod.GET)
    public String batchCouponEntry(ModelMap model, @PathVariable Long groupId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        CouponDefGroup couponGroup = couponDefGroupQueryService.findById(groupId);
        if (couponGroup == null || couponGroup.getStatus() < 1) {
            throw new BusinessException("优惠券不存在！");
        }
        result.put("coupon", couponGroup);
        MemberInfo memberInfo = null;
        try {
            memberInfo = this.getLoginMemberInfo();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        String randomStr = RandomUtil.getRandomString(5);
        result.put("code", randomStr);
        if (memberInfo != null) {
            result.put("member", memberInfo);
            result.put("receiveUrl", "/coupon/batch/" + couponGroup.getId() + ".json");
        } else {
            result.put("receiveUrl", "/coupondef/batch/receive/mobile/" + couponGroup.getId() + ".json");
        }
        return "cms/coupon_receive_group";
    }

    /**
     * 根据手机号，领取优惠券（无需注册）
     *
     * @param model
     * @param id
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/receive/mobile/{id}", method = RequestMethod.POST)
    public String receiveCouponNoLogin(ModelMap model, @PathVariable Long id,
                                       @RequestParam(required = true) String mobile) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (!LoginUtil.checkMobile(mobile)) {
            throw new BusinessException("手机号格式不正确！");
        }
        result.setMsg("优惠券领取成功！");
        CouponDef couponDef = couponDefQueryService.findById(id);
        if (couponDef == null || couponDef.getId() == 1) {
            throw new BusinessException("优惠券不存在！");
        }
        MemberInfo memberInfo = memberInfoService.findByLoginCode(mobile);
        Long memberId = 0L;
        if (memberInfo != null) {
            memberId = memberInfo.getId();
        }
        couponDefService.doClaimedCoupon(couponDef.getId(), memberId, mobile, mobile, CouponSourceEnum.D2CMALL.name(),
                true);
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
     * 根据手机号，批量领取优惠券（无需注册）
     *
     * @param model
     * @param id
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/batch/receive/mobile/{id}", method = RequestMethod.POST)
    public String batchCouponNoLogin(ModelMap model, @PathVariable Long id,
                                     @RequestParam(required = true) String mobile) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
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
        if (memberInfo == null) {
            this.doGiveBatchCoupon(couponDefGroup.getId(), memberId, mobile, result);
        } else {
            this.doGiveBatchCoupon(couponDefGroup.getId(), memberInfo.getId(), memberInfo.getLoginCode(), result);
        }
        return "";
    }

    private SuccessResponse doGiveBatchCoupon(Long couponDefGroupId, Long memberInfoId, String loginCode,
                                              SuccessResponse result) {
        CouponGroup couponGroup = couponDefGroupService.doClaimedCoupon(couponDefGroupId, memberInfoId, loginCode,
                "sys", CouponSourceEnum.D2CMALL.name());
        if (couponGroup != null) {
            result.put("coupon", couponGroup);
            result.setMsg("优惠券领取成功，已经成功放入您的" + loginCode + "账户，下载D2C APP后到「我的D2C→我的优惠券」查看。");
        }
        return result;
    }

    /**
     * 购买优惠券
     *
     * @param model
     * @param defId
     * @return
     */
    @RequestMapping(value = "/buynow", method = RequestMethod.GET)
    public String createOrder(ModelMap model, Long defId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
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
        result.put("couponOrder", old);
        return "pay/coupon_payment";
    }

}
