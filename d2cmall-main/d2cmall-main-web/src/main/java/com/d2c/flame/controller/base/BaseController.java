package com.d2c.flame.controller.base;

import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.security.D2CSign;
import com.d2c.content.service.SensitiveWordsService;
import com.d2c.frame.web.control.BaseControl;
import com.d2c.logger.model.ErrorLog;
import com.d2c.logger.service.ErrorLogService;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.order.model.base.IPaymentInterface;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayBase;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayCore;
import com.d2c.order.third.payment.alipay.sgin.BASE64;
import com.d2c.product.third.upyun.PictureModel;
import com.d2c.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础控制层类
 */
public class BaseController extends BaseControl {

    protected static final String VERSION = "20180109";
    protected static final String RELOGIN = "redirect:/member/login";
    protected static final String REBIND = "redirect:/member/bind";
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ErrorLogService errorLogService;
    @Autowired
    private SensitiveWordsService sensitiveWordsService;

    /**
     * 获取登录会员
     *
     * @return
     */
    protected MemberInfo getLoginMemberInfo() {
        String token = getToken();
        if (StringUtil.isBlank(token)) {
            throw new NotLoginException("账号没有登录！");
        }
        if (token.startsWith("Y")) {
            Member member = memberService.findByToken(token);
            if (member != null) {
                throw new NotLoginException(1, "账号没有绑定！");
            } else {
                throw new NotLoginException("账号没有登录！");
            }
        }
        MemberInfo memberInfo = memberInfoService.findByToken(token);
        if (memberInfo == null) {
            throw new NotLoginException("账号没有登录！");
        }
        return memberInfo;
    }

    /**
     * 获取登录游客
     *
     * @return
     */
    protected Member getLoginMember() {
        String token = getToken();
        if (StringUtil.isBlank(token)) {
            throw new NotLoginException("账号没有登录！");
        }
        Member member = memberService.findByToken(token);
        if (member == null) {
            throw new NotLoginException("账号没有登录！");
        }
        return member;
    }

    /**
     * 获取任意登录信息
     *
     * @return
     */
    protected MemberDto getLoginDto() {
        String token = getToken();
        if (StringUtil.isBlank(token)) {
            throw new NotLoginException("账号没有登录！");
        }
        MemberDto dto = new MemberDto();
        if (token.startsWith("Y")) {
            Member member = memberService.findByToken(token);
            if (member == null) {
                throw new NotLoginException("账号没有登录！");
            }
            BeanUtils.copyProperties(member, dto);
        } else if (token.startsWith("H")) {
            MemberInfo memberInfo = memberInfoService.findByToken(token);
            if (memberInfo == null) {
                throw new NotLoginException("账号没有登录！");
            }
            BeanUtils.copyProperties(memberInfo, dto);
            if (token.startsWith("HY")) {
                Member member = memberService.findByToken(token.substring(1));
                if (member != null) {
                    dto.setSource(member.getSource());
                    dto.setOpenId(member.getOpenId());
                    dto.setUnionId(member.getUnionId());
                    dto.setGzOpenId(member.getGzOpenId());
                    dto.setPartnerOpenId(member.getPartnerOpenId());
                }
            }
        } else {
            throw new NotLoginException("登录信息无效或已过期！");
        }
        return dto;
    }

    /**
     * 获取 token
     *
     * @return
     */
    protected String getToken() {
        String tgc = null;
        if (tgc == null) {
            Cookie[] cookies = getRequest().getCookies();
            if (cookies != null && cookies.length >= 1) {
                for (int j = 0; j < cookies.length; j++) {
                    if (cookies[j].getName().equalsIgnoreCase(Member.CASTGC)) {
                        tgc = cookies[j].getValue();
                        break;
                    }
                }
            }
        }
        return tgc == null ? "" : tgc;
    }

    /**
     * 获取浏览器类型
     *
     * @return
     */
    protected String getUserAgent() {
        HttpServletRequest request = getRequest();
        return request.getHeader("USER-AGENT").toLowerCase();
    }

    /**
     * 生成cookie
     *
     * @param cookie
     * @param response
     */
    protected void writeCookie(Cookie cookie, HttpServletResponse response) {
        writeCookie(cookie, response, -1);
    }

    /**
     * 生成cookie
     *
     * @param cookie
     * @param response
     * @param expiry
     */
    protected void writeCookie(Cookie cookie, HttpServletResponse response, int expiry) {
        // cookie.setDomain(".d2cmall.com");
        cookie.setPath("/");
        if (expiry > 0) {
            cookie.setMaxAge(expiry);
        } else {
            // 默认cookies失效时间是直到关闭浏览器，cookies失效
        }
        response.addCookie(cookie);
    }

    /**
     * 清除cookie
     *
     * @param key
     * @param response
     */
    protected void removeCookie(String key, HttpServletResponse response) {
        if (key == null || response == null) {
            return;
        }
        Cookie cookie = new Cookie(key, null);
        writeCookie(cookie, response, 0);
    }

    /**
     * 是否Ajax请求
     *
     * @return
     */
    protected boolean isAjax() {
        HttpServletRequest request = getRequest();
        boolean ajaxRequest = (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").equalsIgnoreCase("XMLHttpRequest"));
        return ajaxRequest;
    }

    protected Device getDevice() {
        return DeviceUtils.getCurrentDevice(getRequest());
    }

    protected boolean isNormalDevice() {
        return DeviceUtils.getCurrentDevice(getRequest()).isNormal();
    }

    protected boolean isMobileDevice() {
        return DeviceUtils.getCurrentDevice(getRequest()).isMobile();
    }

    protected boolean isTabletDevice() {
        return DeviceUtils.getCurrentDevice(getRequest()).isTablet();
    }

    protected DeviceTypeEnum getDeviceType() {
        DeviceTypeEnum dc = DeviceTypeEnum.PC;
        if (isNormalDevice()) {
            dc = DeviceTypeEnum.PC;
        } else if (isMobileDevice()) {
            dc = DeviceTypeEnum.MOBILE;
        } else if (isTabletDevice()) {
            dc = DeviceTypeEnum.TABLET;
        }
        return dc;
    }

    /**
     * 唤起app支付参数
     *
     * @param order
     * @return
     * @throws UnsupportedEncodingException
     */
    protected String getPayParams(IPaymentInterface order) throws UnsupportedEncodingException {
        Map<String, String> sPara = AlipayCore.parasFilter(order.getPayParams());
        String mySign = AlipayBase.BuildMysign(sPara, D2CSign.SECRET_KEY);
        sPara.put("sign", mySign);
        String params = "param=" + BASE64.encode(AlipayCore.createLinkString(sPara).getBytes("UTF-8"));
        return params;
    }

    /**
     * 用户未登录处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({NotLoginException.class})
    public ModelAndView handleNoLoginException(NotLoginException ex) {
        if (isAjax()) {
            if (1 == ex.getCode()) {
                ErrorResponse result = new ErrorResponse("账号没有绑定！");
                result.put("isD2C", false);
                result.put("isBind", false);
                ModelAndView mv = new ModelAndView();
                mv.getModelMap().put("result", result);
                return mv;
            } else {
                ErrorResponse result = new ErrorResponse("账号没有登录！");
                result.setLogin(false);
                ModelAndView mv = new ModelAndView();
                mv.getModelMap().put("result", result);
                return mv;
            }
        } else {
            if (1 == ex.getCode()) {
                return new ModelAndView(REBIND);
            } else {
                return new ModelAndView(RELOGIN);
            }
        }
    }

    /**
     * 业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({BusinessException.class, AssertException.class})
    public ModelAndView handleBusinessException(BaseException ex) {
        // logger.error("业务异常", ex);
        ErrorResponse result = new ErrorResponse(ex.getMessage());
        if (isAjax()) {
            ModelAndView mv = new ModelAndView();
            mv.getModelMap().put("result", result);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("error/503");
            mv.getModelMap().put("result", result);
            return mv;
        }
    }

    /**
     * dubbo异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({RuntimeException.class})
    public ModelAndView handleRuntimeException(RuntimeException ex) {
        logger.error("系统运行异常", ex);
        ErrorResponse result = new ErrorResponse("系统繁忙，请稍后再试");
        if (isAjax()) {
            ModelAndView mv = new ModelAndView();
            mv.getModelMap().put("result", result);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("error/503");
            mv.getModelMap().put("result", result);
            return mv;
        }
    }

    /**
     * 其他异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception ex) {
        logger.error("其他异常", ex);
        String ip = "";
        String reqIp = "";
        String url = "";
        String method = "";
        StringBuilder body = new StringBuilder();
        String refer = "";
        @SuppressWarnings("rawtypes")
        Map map = new HashMap();
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
            reqIp = getLoginIp();
            url = getRequest().getRequestURL().toString();
            method = getRequest().getMethod();
            refer = getRequest().getHeader("Referer") == null ? "" : getRequest().getHeader("Referer");
            body.append("用户IP：" + reqIp + "<br/>" + "请求地址：" + url + "<br/>" + "请求方式：" + method + "<br/>" + "来源页面："
                    + refer + "<br/>");
            if (method.equalsIgnoreCase("POST")) {
                map = getRequest().getParameterMap();
                body.append("上传参数：").append(StringUtil.mapToString(map));
            } else {
                body.append("请求参数：").append(getRequest().getQueryString());
            }
            body.append("<br/>" + ex.getMessage());
            ErrorLog errorLog = new ErrorLog();
            errorLog.createErrorLog(ip, "PC", body.toString());
            errorLogService.insert(errorLog);
        } catch (Exception e) {
            logger.error("获取请求产生" + e.getMessage());
        }
        ErrorResponse result = new ErrorResponse("系统繁忙，请稍后再试");
        if (isAjax()) {
            ModelAndView mv = new ModelAndView();
            mv.getModelMap().put("result", result);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("error/503");
            mv.getModelMap().put("result", result);
            return mv;
        }
    }

    /**
     * 将本地图片上传至UPY云
     *
     * @param pictureUrl
     */
    protected void uploadPic2CloudStore(String pictureUrl) {
        if (pictureUrl == null || pictureUrl.length() < 1) {
            logger.error("图片地址不正确");
        }
        PictureModel picture;
        try {
            picture = PictureModel.class.newInstance().newInstanceFromUrl(pictureUrl);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BusinessException("本地不存在此图片：" + pictureUrl);
        }
        picture.setPath(pictureUrl);
        picture.upload2CloudStore();
    }

    /**
     * 敏感词过滤
     *
     * @param content
     */
    protected void checkSensitiveWords(String content) {
        if (sensitiveWordsService.findBySensitiveWords(content)) {
            throw new BusinessException("你输入的内容包含敏感词汇，请重新输入。");
        }
    }

}
