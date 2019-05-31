package com.d2c.flame.controller;

import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.model.BrandApply;
import com.d2c.content.service.BrandApplyService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.model.Setting;
import com.d2c.order.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class BrandApplyController extends BaseController {

    @Autowired
    private BrandApplyService brandApplyService;
    @Autowired
    private SettingService settingService;

    /**
     * 品牌入驻页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/brandapply", method = RequestMethod.GET)
    public String brandapply(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        BrandApply brandApply = brandApplyService.findByMemberId(memberInfo.getId());
        if (brandApply == null) {
            brandApply = new BrandApply();
        }
        model.put("brandApply", brandApply);
        return "cms/brandapply_input";
    }

    /**
     * 提交申请入驻
     *
     * @param model
     * @param brandApply
     * @return
     */
    @RequestMapping(value = "/brandapply", method = RequestMethod.POST)
    public String apply(ModelMap model, BrandApply brandApply) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        brandApply.setMemberId(memberInfo.getId());
        brandApply.setStatus(1);
        if (brandApply.getId() != null && brandApply.getId() > 0) {
            int success = brandApplyService.update(brandApply);
            if (success <= 0) {
                throw new BusinessException("申请不成功！");
            }
        } else {
            brandApply = brandApplyService.insert(brandApply);
            if (brandApply == null) {
                throw new BusinessException("申请不成功！");
            }
        }
        Setting setting = settingService.findByCode(Setting.BRADAPPLYWEIXIN);
        model.put("weixin", Setting.defaultValue(setting, ""));
        model.put("result", result);
        return "cms/brandapply_ok";
    }

}
