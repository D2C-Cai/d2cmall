package com.d2c.backend.rest.crm;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.backend.rest.crm.oauth.D2cApiOauth;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.CouponDef;
import com.d2c.order.service.CouponDefQueryService;
import com.d2c.order.service.CouponDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/crm/coupon")
public class CrmCouponController extends SuperCtrl {

    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 调用接口给用户发送单张优惠券
     *
     * @throws BusinessException
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String sendCoupon(HttpServletRequest request, HttpServletResponse response) {
        D2cApiOauth d2cApiOauth = D2cApiOauth.getInstance();
        JSONArray jsonArray = d2cApiOauth.getJsonArray(request, "order");
        if (jsonArray == null) {
            return null;
        }
        JSONObject obj = jsonArray.getJSONObject(0);
        Long couponId = obj.getLong("id");
        String loginCode = obj.getString("loginCode");
        JSONObject result = new JSONObject();
        if (couponId == null || StringUtils.isEmpty(loginCode)) {
            result.put("status", 0);
            result.put("message", "获取参数失败!");
        } else {
            MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
            CouponDef couponDef = couponDefQueryService.findById(couponId);
            Coupon coupon = null;
            if (memberInfo != null && couponDef != null) {
                try {
                    coupon = couponDefService.doClaimedCoupon(couponId, memberInfo.getId(), loginCode, "CRM", "CRM",
                            true);
                } catch (BusinessException e) {
                    result.put("status", 0);
                    result.put("message", e.getMessage());
                }
                if (coupon != null) {
                    result.put("status", 1);
                    result.put("message", "优惠券发送成功！");
                }
            } else if (memberInfo == null) {
                result.put("status", 0);
                result.put("message", "不存在登录名为:" + loginCode + "的用户");
            } else if (couponDef == null) {
                result.put("status", 0);
                result.put("message", "不存在该优惠券！");
            }
        }
        d2cApiOauth.sendJson(response, result);
        return null;
    }

}
