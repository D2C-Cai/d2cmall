package com.d2c.flame.controller;

import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.security.D2CSign;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.member.service.LoginService;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayBase;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayCore;
import com.d2c.order.third.payment.alipay.sgin.BASE64;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController {

    @Autowired
    private LoginService loginService;

    /**
     * 发送验证码(短信加密)
     *
     * @param model
     * @param mobile
     * @param type
     * @param appParams
     * @param nationCode
     * @param source
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/send/encrypt", method = RequestMethod.POST)
    public String sendByEncrypt(ModelMap model, @RequestParam(required = true) String mobile,
                                @RequestParam(required = true) SmsLogType type, String nationCode, String source,
                                @RequestParam(required = true) String appParams) throws UnsupportedEncodingException {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (!checkEncrypt(mobile, appParams)) {
            throw new BusinessException("签名不正确！");
        }
        try {
            // 发送短信验证码
            loginService.doSendValidateMsg(nationCode, mobile, type, source, getLoginIp(), "sms");
            result.setMsg("验证码发送成功！");
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }

    /**
     * 检查验证码
     *
     * @param model
     * @param code
     * @param source
     * @param type
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String check(ModelMap model, String code, String source, SmsLogType type) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (StringUtil.hasBlack(new Object[]{source, code, type})) {
            throw new BusinessException("参数异常");
        }
        String message = loginService.doVerify(source, code);
        result.setMsg("验证码校验成功！");
        if (!message.equalsIgnoreCase("OK")) {
            throw new BusinessException(message);
        }
        return "";
    }

    private boolean checkEncrypt(String mobile, String appParams) throws UnsupportedEncodingException {
        Map<String, String> mobileString = new HashMap<>();
        mobileString.put("mobile", mobile);
        Map<String, String> sPara = AlipayCore.parasFilter(mobileString);
        String mySign = AlipayBase.BuildMysign(sPara, D2CSign.SECRET_KEY);
        sPara.put("sign", mySign);
        String params = BASE64.encode(AlipayCore.createLinkString(sPara).getBytes("UTF-8"));
        if (params.equalsIgnoreCase(appParams)) {
            return true;
        }
        return false;
    }

}
