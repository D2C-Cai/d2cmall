package com.d2c.flame.controller;

import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.Subscribe;
import com.d2c.content.model.Subscribe.SubType;
import com.d2c.content.service.SubscribeService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.util.string.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/subscribe")
public class SubscribeController extends BaseController {

    @Autowired
    private SubscribeService subscribeService;

    /**
     * 邮箱订阅
     *
     * @param model
     * @param email
     * @return
     */
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public String email(ModelMap model, String email) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (!LoginUtil.checkEmail(email)) {
            throw new BusinessException("邮箱格式错误");
        }
        Subscribe subscribe = subscribeService.findByEmail(email);
        if (subscribe != null) {
            throw new BusinessException("该邮箱已订阅");
        }
        subscribe = this.create(email, SubType.EMAIL);
        model.put("result", result);
        return "";
    }

    /**
     * 手机订阅
     *
     * @param model
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/mobile", method = RequestMethod.POST)
    public String mobile(ModelMap model, String mobile) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (!LoginUtil.checkMobile(mobile)) {
            throw new BusinessException("手机格式错误");
        }
        Subscribe subscribe = subscribeService.findByMobile(mobile);
        if (subscribe != null) {
            throw new BusinessException("该手机已订阅");
        }
        subscribe = this.create(mobile, SubType.MOBILE);
        model.put("result", result);
        return "";
    }

    private Subscribe create(String sub, SubType type) {
        Subscribe subscribe = new Subscribe();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            subscribe.setMemberId(memberInfo.getId());
            subscribe.setMemberName(memberInfo.getLoginCode());
            subscribe.setMemberCode(memberInfo.getLoginCode());
        } catch (NotLoginException e) {
            // 无需处理
        }
        subscribe.setSubscribe(sub);
        subscribe.setSubType(type.toString());
        subscribeService.insert(subscribe);
        return subscribe;
    }

}
