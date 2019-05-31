package com.d2c.flame.freemarker;

import com.d2c.member.dto.MemberDto;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.util.string.StringUtil;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LoginMember implements TemplateMethodModelEx {

    protected static final Log logger = LogFactory.getLog(LoginMember.class);
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;

    /**
     * 获取 token
     *
     * @return
     */
    protected static String getToken() {
        String tgc = null;
        Cookie[] cookies = getRequest().getCookies();
        if (cookies != null && cookies.length >= 1) {
            for (int j = 0; j < cookies.length; j++) {
                if (cookies[j].getName().equalsIgnoreCase(Member.CASTGC)) {
                    tgc = cookies[j].getValue();
                    break;
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

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arg0) throws TemplateModelException {
        String token = getToken();
        MemberDto dto = new MemberDto();
        if (StringUtil.isBlank(token)) {
            return dto;
        } else if (token.startsWith("Y")) {
            Member member = memberService.findByToken(token);
            if (member == null) {
                return dto;
            }
            BeanUtils.copyProperties(member, dto);
        } else if (token.startsWith("H")) {
            MemberInfo memberInfo = memberInfoService.findByToken(token);
            if (memberInfo == null) {
                return dto;
            }
            BeanUtils.copyProperties(memberInfo, dto);
        } else {
            return dto;
        }
        return dto;
    }

}
