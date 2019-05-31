package com.d2c.flame.controller.logger;

import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.security.D2CSign;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.LoginService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayBase;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayCore;
import com.d2c.order.third.payment.alipay.sgin.BASE64;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信验证码
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/sms")
public class SmsController extends BaseController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 发送验证码(短信加密)
     *
     * @param mobile
     * @param type
     * @param nationCode
     * @param source
     * @param appParams
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/send/encrypt", method = RequestMethod.POST)
    public ResponseResult sendByEncrypt(@RequestParam(required = true) String mobile,
                                        @RequestParam(required = true) String type, String nationCode, String source,
                                        @RequestParam(required = true) String appParams) throws UnsupportedEncodingException {
        String sendMobile = mobile;
        if (mobile.contains("*")) {
            sendMobile = this.getLoginMemberInfo().getLoginCode();
        }
        if (!checkEncrypt(mobile, appParams)) {
            throw new BusinessException("签名不正确！");
        }
        return this.send(nationCode, sendMobile, type, source, "sms");
    }

    /**
     * 发送验证码(注册账号)
     *
     * @param mobile
     * @param type
     * @param nationCode
     * @param source
     * @return
     */
    @RequestMapping(value = "/d2c/send", method = RequestMethod.POST)
    public ResponseResult sendByD2C(@RequestParam(required = true) String mobile,
                                    @RequestParam(required = true) String type, String nationCode, String source) {
        MemberInfo memberInfo = memberInfoService.findByLoginCode(mobile);
        if (memberInfo == null) {
            throw new BusinessException("您的手机号未注册！");
        }
        return this.send(nationCode, mobile, type, source, "sms");
    }

    /**
     * 语音验证码
     *
     * @param mobile
     * @param type
     * @param nationCode
     * @param source
     * @param appParams
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/send/audio", method = RequestMethod.POST)
    public ResponseResult sendAudioCode(@RequestParam(required = true) String mobile,
                                        @RequestParam(required = true) String type, String nationCode, String source,
                                        @RequestParam(required = true) String appParams) throws UnsupportedEncodingException {
        String sendMobile = mobile;
        if (mobile.contains("*")) {
            sendMobile = this.getLoginMemberInfo().getLoginCode();
        }
        if (!checkEncrypt(mobile, appParams)) {
            throw new BusinessException("签名不正确！");
        }
        return this.send(nationCode, sendMobile, type, source, "audio");
    }

    /**
     * 检查验证码
     *
     * @param code   验证码
     * @param mobile 手机号
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseResult check(@RequestParam(required = true) String code,
                                @RequestParam(required = true) String mobile) {
        if (mobile.contains("*")) {
            mobile = this.getLoginMemberInfo().getLoginCode();
        }
        ResponseResult result = new ResponseResult();
        if (StringUtil.hasBlack(new Object[]{mobile, code})) {
            throw new BusinessException("参数异常");
        }
        String message = loginService.doVerify(mobile, code);
        if (!message.equalsIgnoreCase("OK")) {
            throw new BusinessException(message);
        }
        return result;
    }

    private ResponseResult send(String nationCode, String mobile, String type, String source, String method) {
        ResponseResult result = new ResponseResult();
        SmsLogType logType = SmsLogType.valueOf(type.toUpperCase());
        if (logType.equals("REGISTER")) {
            logType = SmsLogType.MEMBERMOBILE;
        }
        loginService.doSendValidateMsg(nationCode, mobile, logType, source, getLoginIp(), method);
        result.setMsg("发送成功！");
        return result;
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
