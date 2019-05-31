package com.d2c.flame.controller;

import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.dto.MemberInfoDto;
import com.d2c.member.enums.MemberTypeEnum;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Partner;
import com.d2c.member.service.LoginService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.PartnerService;
import com.d2c.util.string.LoginUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/member")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private PartnerService partnerService;

    /**
     * 跳转到登录页面
     *
     * @param path
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(String path, ModelMap model) {
        if (isAjax()) {
            return "fragment/login_ajax";
        } else {
            model.put("path", path);
            return "member/login";
        }
    }
    // /**
    // * 用户登陆
    // *
    // * @param loginCode
    // * @param password
    // * @param auto
    // * @param path
    // * @param model
    // * @param randomCode
    // * @return
    // */
    // @RequestMapping(value = "/login", method = RequestMethod.POST)
    // public String doLogin(ModelMap model, String loginCode, String password,
    // String auto, String path,
    // String randomCode, HttpServletResponse response) {
    // SuccessResponse result = new SuccessResponse();
    // model.put("result", result);
    // loginCode = loginCode.toLowerCase();
    // MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
    // if (memberInfo == null) {
    // result.setStatus(-1);
    // result.setMsg("用户名或密码不正确！");
    // result.put("loginCode", loginCode);
    // return "member/login";
    // }
    // int loginError = memberInfoService.getLoginError(loginCode);
    // // 用户错误登陆3次
    // if (loginError >= 3) {
    // result.setStatus(-1);
    // result.setMsg("密码连续错误3次，20分钟内无法登录！");
    // result.put("loginCode", loginCode);
    // return "member/login";
    // }
    // // 登录操作
    // if (memberInfo.getLoginCode().equals(loginCode)
    // && DigestUtils.md5Hex(password).equals(memberInfo.getPassword())) {
    // String token = loginService.doLogin(loginCode, isMobileDevice() ? "WAP" :
    // "PC", null);
    // if (StringUtil.isNotBlank(token)) {
    // Cookie tgc = new Cookie(Member.CASTGC, token);
    // if ("1".equals(auto)) {
    // writeCookie(tgc, response, 14 * 24 * 60 * 60);
    // } else {
    // writeCookie(tgc, response, 1 * 60 * 60);
    // }
    // // 地址跳转
    // if ("XMLHttpRequest".equals(getRequest().getHeader("X-Requested-With")))
    // {
    // return "";
    // } else if (StringUtils.isEmpty(path) || "/member/login".equals(path)
    // || "/member/register".equals(path)) {
    // return "redirect:/";
    // } else {
    // return "redirect:" + path;
    // }
    // } else {
    // return "member/login";
    // }
    // } else {
    // // 登陆不成功
    // memberInfoService.updateLoginError(loginCode, loginError + 1);
    // result.setStatus(-1);
    // result.setMsg("用户名或密码不正确！");
    // result.put("loginCode", loginCode);
    // return "member/login";
    // }
    // }

    /**
     * 短信验证码登录
     *
     * @param model
     * @param loginCode
     * @param code
     * @param auto
     * @param path
     * @return
     */
    @RequestMapping(value = "/quick/login", method = RequestMethod.POST)
    public String doQuickLogin(ModelMap model, String loginCode, String code, String auto, String path,
                               HttpServletResponse response) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        loginCode = loginCode.toLowerCase();
        String message = loginService.doVerify(loginCode, code);
        loginService.createLoginLog("用户登录失败", loginCode, "用户登录失败，code:" + code + "， 原因验证码不正确！", getLoginIp(),
                isMobileDevice() ? "WAP" : "PC", null, getUserAgent());
        if (!message.equalsIgnoreCase("OK")) {
            result.setStatus(-1);
            result.setMsg(message);
            return "member/login";
        }
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        String token = null;
        if (memberInfo == null) {
            String password = MD5Util.encodeMD5Hex(loginCode).substring(0, 8);
            MemberInfoDto dto = loginService.doRegist("86", loginCode, password, MemberTypeEnum.D2CMall.name(),
                    isMobileDevice() ? "WAP" : "PC", getLoginIp(), null, null, getUserAgent());
            token = dto.getToken();
        } else {
            token = loginService.doLogin(loginCode, isMobileDevice() ? "WAP" : "PC", getLoginIp(), null, null,
                    getUserAgent(), null);
        }
        Cookie tgc = new Cookie(Member.CASTGC, token);
        if ("1".equals(auto)) {
            writeCookie(tgc, response, 14 * 24 * 60 * 60);
        } else {
            writeCookie(tgc, response, 1 * 60 * 60);
        }
        // 地址跳转
        if ("XMLHttpRequest".equals(getRequest().getHeader("X-Requested-With"))) {
            return "";
        } else if (StringUtils.isEmpty(path) || "/member/login".equals(path) || "/member/register".equals(path)) {
            return "redirect:/";
        } else {
            return "redirect:" + path;
        }
    }

    /**
     * 跳转到注册页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegister() {
        return "member/register";
    }

    /**
     * 用户注册
     *
     * @param nationCode
     * @param loginCode
     * @param password
     * @param rePassword
     * @param model
     * @param path
     * @param code
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegister(ModelMap model, String nationCode, String loginCode, String password, String rePassword,
                             String path, String code, HttpServletResponse response) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        result.put("loginCode", loginCode);
        // 注册前校验
        String errorStr = loginService.validate(rePassword, password, loginCode);
        if (errorStr != null) {
            throw new BusinessException(errorStr);
        }
        AssertUt.isPassword(password);
        // 手机注册
        if (LoginUtil.checkMobile(loginCode)) {
            String message = loginService.doVerify(loginCode, code);
            if (!message.equalsIgnoreCase("OK")) {
                throw new BusinessException(message);
            }
        }
        // 推荐人校验
        Long parent_id = this.processCookie();
        if (parent_id != null) {
            Partner partner = partnerService.findById(parent_id);
            if (partner == null || partner.getStatus() < 0) {
                parent_id = null;
            }
        }
        // 注册操作
        MemberInfoDto memberInfo = loginService.doRegist(nationCode, loginCode, rePassword,
                MemberTypeEnum.D2CMall.name(), isMobileDevice() ? "WAP" : "PC", getLoginIp(), parent_id, null,
                getUserAgent());
        if (memberInfo != null) {
            Cookie tgc = new Cookie(Member.CASTGC, memberInfo.getToken());
            writeCookie(tgc, response);
        } else {
            throw new BusinessException(loginCode + "注册不成功");
        }
        // 表单提交注册
        if (StringUtils.isEmpty(path)) {
            path = "/member/updateProfile";
        }
        result.put("redirect", path);
        return "";
    }

    /**
     * 绑定会员，跳转页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/bind", method = RequestMethod.GET)
    public String showBind(ModelMap model) {
        MemberDto dto = this.getLoginDto();
        if (!dto.isD2c()) {
            if (isAjax()) {
                return "fragment/bind_ajax";
            } else {
                model.put("member", dto);
                return "/member/bind_d2c_member";
            }
        }
        return "redirect:/index";
    }

    /**
     * 绑定D2C会员
     *
     * @param model
     * @param code
     * @param loginCode
     * @param nationCode
     * @return
     */
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public String doBind(ModelMap model, String code, String loginCode, String nationCode, String password1,
                         String password2, HttpServletResponse response) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        Member member = this.getLoginMember();
        if (StringUtil.hasBlack(new Object[]{loginCode, code})) {
            throw new BusinessException("参数不能为空！");
        }
        String message = loginService.doVerify(loginCode, code);
        if (!message.equalsIgnoreCase("OK")) {
            throw new BusinessException(message);
        }
        // 兼容以往版本，密码可不设置，设置必须确认相同
        if (StringUtil.isNotBlank(password1) && StringUtil.isNotBlank(password2) && !password1.equals(password2)) {
            throw new BusinessException("两次输入的密码不一致");
        }
        Member third = new Member();
        BeanUtils.copyProperties(member, third);
        // 推荐人校验
        Long parent_id = this.processCookie();
        if (parent_id != null) {
            Partner partner = partnerService.findById(parent_id);
            if (partner == null || partner.getStatus() < 0) {
                parent_id = null;
            }
        }
        MemberInfoDto memberInfo = loginService.doBindMemberInfo(third, nationCode, loginCode, member.getUnionId(),
                member.getSource(), isMobileDevice() ? "WAP" : "PC", getLoginIp(), parent_id, null, getUserAgent(),
                member.getToken());
        Cookie tgc = new Cookie(Member.CASTGC, memberInfo.getToken());
        writeCookie(tgc, response);
        result.setMsg("绑定成功！");
        return "";
    }

    /**
     * 用户注销
     *
     * @param tgc
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue(defaultValue = "", value = Member.CASTGC) String tgc,
                         HttpServletResponse response) {
        try {
            MemberDto dto = this.getLoginDto();
            if (dto != null) {
                loginService.doLogOut(dto.getUnionId(), dto.getLoginCode(), tgc, isMobileDevice() ? "WAP" : "PC",
                        getLoginIp(), null, getUserAgent());
                Cookie[] cookies = getRequest().getCookies();
                for (int i = 0, len = cookies.length; i < len; i++) {
                    String cookName = cookies[i].getName();
                    if (cookName.equals(Member.CASTGC)) {
                        Cookie cookie = new Cookie(Member.CASTGC, null);
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        // cookie.setDomain(".d2cmall.com");
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
        } catch (NotLoginException e) {
            // 不处理
            logger.error(e.getMessage());
        }
        return "redirect:/member/login";
    }

    /**
     * 验证账号是否可用
     *
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "/checkLoginCode", method = RequestMethod.POST)
    public String checkLoginCode(String code, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        Map<Integer, String> map = loginService.validateLoginCode(code);
        if (map.get(-1) != null) {
            result.setStatus(-1);
            result.setMsg(map.get(-1));
            result.put("nationCode", map.get(1) == null ? "86" : map.get(1));
        }
        model.put("result", result);
        return "";
    }

    /**
     * 跳转到忘记密界面
     *
     * @return
     */
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String toForgetPassword() {
        return "member/forget_password";
    }

    /**
     * 确认账号是否已经注册
     *
     * @param loginCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/forgetLoginCode", method = RequestMethod.POST)
    public String forgetLoginCode(String loginCode, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        if (memberInfo == null) {
            throw new BusinessException("该用户不存在！");
        }
        return "";
    }

    /**
     * 忘记密码下一步
     *
     * @param loginCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/sendResetPassword", method = RequestMethod.POST)
    public String sendResetPassword(String loginCode, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("loginCode", loginCode);
        model.put("result", result);
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        if (memberInfo == null) {
            throw new BusinessException("该用户不存在！");
        }
        return "/member/reset_password";
    }

    /**
     * 重置登录密码 （找回密码）
     *
     * @param model
     * @param loginCode
     * @param password
     * @param code
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String resetPassword(ModelMap model, String loginCode, String password, String code,
                                HttpServletResponse response) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        String message = loginService.doVerify(loginCode, code);
        try {
            if (!message.equalsIgnoreCase("OK")) {
                throw new BusinessException(message);
            }
            MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
            if (memberInfo != null) {
                AssertUt.isPassword(password);
                memberInfo.setPassword(DigestUtils.md5Hex(password));
                int success = memberInfoService.updatePassword(memberInfo.getLoginCode(), memberInfo.getPassword());
                if (success == 1) {
                    Cookie clearTgc = new Cookie(Member.CASTGC, null);
                    response.addCookie(clearTgc);
                }
                loginService.createLoginLog("重置账号密码", loginCode, "重置账号密码成功，passwd:" + password, getLoginIp(),
                        isMobileDevice() ? "WAP" : "PC", null, getUserAgent());
            }
            result.setMsg("密码修改成功！");
        } catch (BusinessException e) {
            loginService.createLoginLog("重置账号密码", loginCode, "重置账号密码失败，passwd:" + password + "， 原因" + e.getMessage(),
                    getLoginIp(), isMobileDevice() ? "WAP" : "PC", null, getUserAgent());
            throw new BusinessException(e.getMessage());
        }
        return "";
    }

    /**
     * 重置密码成功
     *
     * @return
     */
    @RequestMapping(value = "/resetSuccess", method = RequestMethod.GET)
    public String toResetSuccess() {
        return "/member/reset_password_success";
    }

    private Long processCookie() {
        try {
            Cookie[] cookies = getRequest().getCookies();
            if (cookies != null && cookies.length >= 1) {
                for (int j = 0; j < cookies.length; j++) {
                    if (cookies[j].getName().equalsIgnoreCase("d2cCookie")) {
                        String channelStr = cookies[j].getValue().split(",")[0];
                        String channel = channelStr.split(":")[1];
                        String infoStr = cookies[j].getValue().split(",")[1];
                        String info = infoStr.split(":")[1];
                        if (channel.equalsIgnoreCase("Partner")) {
                            return Long.valueOf(info);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
