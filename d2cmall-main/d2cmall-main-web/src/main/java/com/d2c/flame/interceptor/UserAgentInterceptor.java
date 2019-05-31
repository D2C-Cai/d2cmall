package com.d2c.flame.interceptor;

import com.d2c.flame.property.HttpProperties;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.member.third.oauth.WeiboOauthClient;
import com.d2c.member.third.oauth.WeixinGzOauthClient;
import com.d2c.order.third.payment.wxpay.sign.WeixinSign;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 微信浏览器的GET请求，未登陆情况下优先授权，并消除容器切换的session丢失
 *
 * @author Administrator
 */
public class UserAgentInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SignatureService signatureService;
    @Autowired
    private WeixinGzOauthClient weixinGzAouth;
    @Autowired
    private WeiboOauthClient weiboOauthClient;
    @Autowired
    private HttpProperties httpProperties;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;

    // 获取uacookie
    protected static boolean getUserAgentCookie() {
        String uac = null;
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null && cookies.length >= 1) {
            for (int j = 0; j < cookies.length; j++) {
                if (cookies[j].getName().equalsIgnoreCase(HttpProperties.USERAGENT)) {
                    uac = cookies[j].getValue();
                    break;
                }
            }
        }
        return (uac != null && uac.equals("1")) ? true : false;
    }

    /**
     * 获取 token
     *
     * @return
     */
    protected static String getToken() {
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
     * 获取 request
     *
     * @return
     */
    protected static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    // Action之前执行:
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String userAgent = request.getHeader("USER-AGENT");
        String url = request.getRequestURL().toString();
        String urlParam = request.getQueryString();
        if (StringUtils.isNotBlank(userAgent) && !isAjax(request) && !getUserAgentCookie() && excludeUrl(url)
                && "GET".equalsIgnoreCase(request.getMethod())) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("micromessenger")) {
                url = this.getFullPath(request, url, urlParam);
                MemberDto member = this.getCurrentLoginMember(request, response);
                if (member == null) {
                    if (url.contains("/partner/grant")) {
                        response.sendRedirect(weixinGzAouth.getPartnerCode(true, url));
                    } else {
                        response.sendRedirect(weixinGzAouth.getCode(true, url));
                    }
                    return false;
                }
            } else if (userAgent.contains("weibo")) {
                url = this.getFullPath(request, url, urlParam);
                MemberDto member = this.getCurrentLoginMember(request, response);
                if (member == null) {
                    response.sendRedirect(weiboOauthClient.getCodeUrl(url));
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    // 生成视图之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        String userAgent = request.getHeader("USER-AGENT");
        String url = request.getRequestURL().toString();
        String urlParam = request.getQueryString();
        if (StringUtils.isNotBlank(userAgent) && !isAjax(request)) {
            userAgent = userAgent.toLowerCase();
            if (modelAndView != null) {
                if (userAgent.contains("micromessenger")) {
                    url = this.getFullPath(request, url, urlParam);
                    if (includeUrl(url)) {
                        // 微信js-api的token
                        Signature signature = signatureService.findByAppid(weixinGzAouth.getWEIXIN_GZ_APPKEY());
                        Map<String, String> wechat = WeixinSign.sha1Sign(signature.getTicket(), signature.getAppid(),
                                url);
                        modelAndView.getModelMap().put("wechat", wechat);
                    }
                    modelAndView.getModelMap().put("browser", "wechat");
                } else if (userAgent.contains("weibo")) {
                    modelAndView.getModelMap().put("browser", "weibo");
                }
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    // 是否ajax请求
    private boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").equals("XMLHttpRequest"));
    }

    // 去除不需要微信授权登入的url
    private boolean excludeUrl(String url) {
        return url.indexOf("/callback") < 0 && url.indexOf("/appToWap") < 0 && url.indexOf("/share/in") < 0;
    }

    // 需要微信授权js签名的url
    private boolean includeUrl(String url) {
        return url.contains("/product") || url.contains("/award") || url.contains("/promotion") || url.contains("/page")
                || url.contains("/share") || url.contains("/index") || url.contains("/bargain")
                || url.contains("/partner");
    }

    // 获取当前用户信息
    protected MemberDto getCurrentLoginMember(HttpServletRequest request, HttpServletResponse response) {
        String token = getToken();
        if (StringUtil.isBlank(token)) {
            return null;
        }
        MemberDto dto = new MemberDto();
        if (token.startsWith("Y")) {
            Member member = memberService.findByToken(token);
            if (member == null) {
                return null;
            }
            BeanUtils.copyProperties(member, dto);
        } else if (token.startsWith("H")) {
            MemberInfo memberInfo = memberInfoService.findByToken(token);
            if (memberInfo == null) {
                return null;
            }
            BeanUtils.copyProperties(memberInfo, dto);
        } else {
            return null;
        }
        return dto;
    }

    // 获取完整的url路径
    private String getFullPath(HttpServletRequest request, String url, String urlParam) {
        Device currentDevice = DeviceUtils.getCurrentDevice(request);
        if (currentDevice.isMobile()) {
            url = httpProperties.getMobileUrl() + request.getRequestURI();
        }
        if (StringUtils.isNotBlank(urlParam)) {
            url = url + "?" + urlParam;
        }
        return url;
    }

}
