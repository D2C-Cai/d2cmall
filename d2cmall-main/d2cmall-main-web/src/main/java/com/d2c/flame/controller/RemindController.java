package com.d2c.flame.controller;

import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.Remind;
import com.d2c.logger.model.Remind.RemindType;
import com.d2c.logger.service.RemindService;
import com.d2c.member.model.MemberInfo;
import com.d2c.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class RemindController extends BaseController {

    @Autowired
    private ProductService productService;
    @Autowired
    private RemindService remindService;

    /**
     * 到货提醒
     *
     * @param productId
     * @param type
     * @param mobilemail
     * @param name
     * @param model
     * @return
     */
    @RequestMapping(value = "/remind", method = RequestMethod.POST)
    public String remind(Long productId, RemindType type, String mobilemail, String name, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Remind remind = new Remind();
        MemberInfo memberInfo;
        try {
            memberInfo = this.getLoginMemberInfo();
            remind.setMemberId(memberInfo.getId());
        } catch (NotLoginException e) {
            remind.setMemberId(0L);
        }
        remind.setSourceId(productId);
        remind.setType(type.name());
        remind.setContent(name);
        if (mobilemail.contains("@")) {
            remind.setMail(mobilemail);
        } else {
            remind.setMobile(mobilemail);
        }
        if (!remind.check()) {
            throw new BusinessException("添加提醒不成功，请刷新页面重试！");
        }
        remind.setContent(analyseName(name, type, productId));
        remind = remindService.insert(remind);
        if (remind == null) {
            throw new BusinessException("您已经添加提醒！");
        } else if (remind.getId() < 0) {
            throw new BusinessException("添加提醒不成功，请刷新页面重试！");
        }
        result.setMsg("添加提醒成功！");
        return "";
    }

    private String analyseName(String name, RemindType remindType, Long productId) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(name)) {
            name = productService.findById(productId).getName();
        }
        sb.append(name);
        switch (remindType) {
            case CROWD_BEGIN:
                sb.append("，已经开始预售了，购买请到http://www.d2cmall.com/crowds/product/").append(productId);
                break;
            case ARRIVAL:
                sb.append("，已经到货了，购买请到http://www.d2cmall.com/product/").append(productId);
                break;
            case CUSTOM:
                break;
            default:
                break;
        }
        return sb.toString();
    }

}
