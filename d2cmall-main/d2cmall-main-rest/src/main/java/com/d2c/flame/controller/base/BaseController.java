package com.d2c.flame.controller.base;

import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.service.SensitiveWordsService;
import com.d2c.logger.model.ErrorLog;
import com.d2c.logger.service.ErrorLogService;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础控制层类
 */
public class BaseController extends BaseDeviceController {

    @Autowired
    protected MemberService memberService;
    @Autowired
    protected MemberInfoService memberInfoService;
    @Autowired
    private ErrorLogService errorLogService;
    @Autowired
    private SensitiveWordsService sensitiveWordsService;

    /**
     * 获取登录会员
     *
     * @return
     */
    protected MemberInfo getLoginUnCheck() {
        String token = getToken();
        if (StringUtil.isBlank(token)) {
            return null;
        }
        return memberInfoService.findByToken(token);
    }

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
        if (getToken().startsWith("Y")) {
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
        if (getToken().startsWith("Y")) {
            Member member = memberService.findByToken(token);
            if (member == null) {
                throw new NotLoginException("账号没有登录！");
            }
            BeanUtils.copyProperties(member, dto);
        } else if (getToken().startsWith("H")) {
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

    protected String getToken() {
        String token = getRequest().getHeader("accesstoken");
        return token == null ? "" : token;
    }

    /**
     * 用户未登录处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({NotLoginException.class})
    @ResponseBody
    public ResponseResult handleNotLoginException(NotLoginException ex) {
        ResponseResult result = new ResponseResult();
        result.setStatus(-1);
        result.setMsg(ex.getMessage());
        if (1 == ex.getCode()) {
            result.setLogin(true);
        } else {
            result.setLogin(false);
        }
        return result;
    }

    /**
     * 参数检查异常，业务异常
     * <p>
     * ServletRequestBindingException： 参数绑定异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({BusinessException.class, AssertException.class})
    @ResponseBody
    public ResponseResult handleBusinessException(BaseException ex) {
        ResponseResult result = new ResponseResult();
        result.setStatus(-1);
        result.put("code", ex.getErrCode());
        result.setMsg(ex.getMessage());
        return result;
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public ResponseResult handleRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        ResponseResult result = new ResponseResult();
        result.setMsg("系统繁忙，请稍后再试");
        result.setStatus(-9);
        result.put("error", ex.getMessage());
        return result;
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseResult handleException(Exception ex) {
        ex.printStackTrace();
        logger.error(ex.getMessage(), ex);
        String ip = "";
        String reqIp = "";
        String url = "";
        String method = "";
        @SuppressWarnings("rawtypes")
        Map map = new HashMap();
        StringBuilder body = new StringBuilder();
        String version = "";
        String terminal = "";
        String agent = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();
            reqIp = getLoginIp();
            url = getRequest().getRequestURL().toString();
            method = getRequest().getMethod();
            agent = getRequest().getHeader("User-Agent");
            version = (getRequest().getParameter("appVersion") == null ? ""
                    : getRequest().getParameter("appVersion").toString());
            terminal = (getRequest().getParameter("appTerminal") == null ? ""
                    : getRequest().getParameter("appTerminal").toString());
            body.append("用户IP：" + reqIp + "<br/>" + "请求地址：" + url + "<br/>" + "请求方式：" + method + "<br/>");
            if (method.equalsIgnoreCase("POST")) {
                map = getRequest().getParameterMap();
                body.append("上传参数：").append(StringUtil.mapToString(map)).append("，版本号:").append(version).append("，终端:")
                        .append(terminal).append("，User-Agent:").append(agent);
            } else {
                body.append("请求参数：").append(getRequest().getQueryString()).append("，版本号:").append(version)
                        .append("，终端:").append(terminal).append("，User-Agent:").append(agent);
            }
            body.append("<br/>" + ex.getMessage());
            ErrorLog errorLog = new ErrorLog();
            errorLog.createErrorLog(ip, DeviceTypeEnum.divisionDevice(terminal), body.toString());
            logger.error(errorLog.toString());
            errorLogService.insert(errorLog);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        ResponseResult result = new ResponseResult();
        result.setMsg("系统繁忙，请稍后再试");
        result.setStatus(-9);
        result.put("error", ex.getMessage());
        return result;
    }

    protected void checkSensitiveWords(String content) {
        if (sensitiveWordsService.findBySensitiveWords(content)) {
            throw new BusinessException("你输入的内容包含敏感词汇，请重新输入。");
        }
    }

}
