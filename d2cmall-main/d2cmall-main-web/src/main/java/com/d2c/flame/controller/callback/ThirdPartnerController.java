package com.d2c.flame.controller.callback;

import com.alibaba.fastjson.JSONObject;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.property.HttpProperties;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.enums.MemberTypeEnum;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Partner;
import com.d2c.member.service.LoginService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.member.service.PartnerService;
import com.d2c.member.third.oauth.WeixinGzOauthClient;
import com.d2c.order.third.payment.wxpay.sign.SHA1;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class ThirdPartnerController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private WeixinGzOauthClient weixinGzAouth;
    @Autowired
    private HttpProperties httpProperties;

    /**
     * 第三方通知
     *
     * @param source 类型
     * @param code   作为换取access_token的票据
     * @return
     */
    @RequestMapping(value = "/callback/partner", method = RequestMethod.GET)
    public String index(String code, String path, ModelMap model, HttpServletResponse response) {
        if (path == null) {
            path = "";
        }
        path = path.replaceAll("__", "&");
        Member member = this.processWxGz(code, MemberTypeEnum.WeixinGz.name(), path, response);
        // 地址跳转
        if (StringUtils.isBlank(path)) {
            if (member == null || !member.isD2c()) {
                return "redirect:/member/bind";
            } else {
                return "redirect:/index";
            }
        }
        return "redirect:" + path;
    }

    private Member processWxGz(String code, String source, String path, HttpServletResponse response) {
        String openId = "";
        String accessToken = "";
        // 获取AccessToken和OpenId
        String tokenAndOpenId = weixinGzAouth.getPartnerAccessTokenAndOpenId(code);
        JSONObject tokenAndOpenIdJson = JSONObject.parseObject(tokenAndOpenId);
        accessToken = tokenAndOpenIdJson.getString("access_token");
        openId = tokenAndOpenIdJson.getString("openid");
        // 获取用户基本信息
        String wxuser = weixinGzAouth.getPartnerUser(accessToken, openId);
        JSONObject wxuserJson = JSONObject.parseObject(wxuser);
        source = MemberTypeEnum.WeixinGz.name();
        String nickname = getNickname(wxuserJson.getString("nickname"));
        int sexInt = wxuserJson.getIntValue("sex");
        String sex = (sexInt == 1 ? "男" : "女");
        String thirdHeadPic = wxuserJson.getString("headimgurl");
        String unionId = wxuserJson.getString("unionid");
        return this.processThirdMember(openId, unionId, source, nickname, thirdHeadPic, sex, accessToken, response);
    }

    /**
     * 处理昵称
     *
     * @param nickname
     * @return
     */
    private String getNickname(String nickname) {
        if (nickname.length() > 100) {
            nickname = nickname.substring(0, 100);
        }
        return nickname;
    }

    /**
     * 登陆或注册
     */
    private Member processThirdMember(String openId, String unionId, String source, String nickname, String headPic,
                                      String sex, String accessToken, HttpServletResponse response) {
        Member member = memberService.findByUnionIdAndSource(source, unionId);
        MemberDto dto = new MemberDto();
        if (member == null) {
            member = loginService.doThirdRegist(openId, unionId, source, isMobileDevice() ? "WAP" : "PC", getLoginIp(),
                    nickname, headPic, sex, accessToken, openId, null, getUserAgent());
            BeanUtils.copyProperties(member, dto);
        } else {
            String token = loginService.doThirdLogin(openId, unionId, source, isMobileDevice() ? "WAP" : "PC",
                    getLoginIp(), nickname, headPic, sex, accessToken, null, openId, null, getUserAgent());
            member.setNickname(nickname);
            member.setHeadPic(headPic);
            member.setSex(sex);
            member.setToken(token);
            if (member.isD2c()) {
                MemberInfo memberInfo = memberInfoService.findByLoginCode(member.getLoginCode());
                member.setToken(loginService.doLogin(member.getLoginCode(), isMobileDevice() ? "WAP" : "PC",
                        getLoginIp(), null, null, getUserAgent(), member.getToken()));
                dto.setOpenId(openId);
                dto.setUnionId(unionId);
                dto.setSource(source);
                dto.setPartnerOpenId(openId);
                BeanUtils.copyProperties(memberInfo, dto);
                if (memberInfo.getPartnerId() != null) {
                    Partner partner = partnerService.findById(memberInfo.getPartnerId());
                    if (partner != null /* && partner.getOpenId() == null */) {
                        partnerService.updateOpenId(partner.getId(), openId);
                    }
                }
            } else {
                BeanUtils.copyProperties(member, dto);
            }
        }
        Cookie tgc = new Cookie(Member.CASTGC, member.getToken());
        writeCookie(tgc, response);
        return member;
    }

    /**
     * 关注回调
     *
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @throws Exception
     */
    @RequestMapping(value = "/callback/event", method = {RequestMethod.GET, RequestMethod.POST})
    public void event(HttpServletResponse response, String signature, String timestamp, String nonce, String echostr)
            throws Exception {
        String token = "a66751fb542346e24fc001a4d0df41b8";// "d2cmall"的md5
        String[] arrTmp = {token, timestamp, nonce};
        Arrays.sort(arrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arrTmp.length; i++) {
            sb.append(arrTmp[i]);
        }
        String mySign = SHA1.sha1(sb.toString());
        if (mySign.equals(signature)) {
            if (echostr != null) {
                response.getWriter().print(echostr);
            } else {
                // 此处代码是业务代码
                HttpServletRequest request = getRequest();
                InputStream inStream = request.getInputStream();
                ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outSteam.write(buffer, 0, len);
                }
                outSteam.close();
                inStream.close();
                String responseXml = new String(outSteam.toByteArray(), "utf-8");
                HashMap<String, String> responseMap = StringUtil.xmlToMap(responseXml);
                if (String.valueOf(responseMap.get("Event")).equals("subscribe")) {
                    Date now = new Date();
                    response.getWriter().write(setXML(String.valueOf(responseMap.get("ToUserName")),
                            String.valueOf(responseMap.get("FromUserName")), now.getTime()));
                    response.getWriter().flush();
                }
            }
        } else {
            response.getWriter().print("error");
        }
    }

    private String setXML(String fromUser, String toUser, Long timeStamp) {
        String toUserName = "<ToUserName><![CDATA[" + toUser + "]]></ToUserName>";
        String fromUserName = "<FromUserName><![CDATA[" + fromUser + "]]></FromUserName>";
        String createTime = "<CreateTime>" + timeStamp / 1000 + "</CreateTime>";
        String msgType = "<MsgType><![CDATA[" + "news" + "]]></MsgType>";
        String articleCount = "<ArticleCount>" + 1 + "</ArticleCount>";
        String artile1 = "<item><Title><![CDATA[" + "点击授权接收实时通知" + "]]></Title><Description><![CDATA[" + ""
                + "]]></Description><PicUrl><![CDATA[" + "https://static.d2c.cn/other/wxcover.jpg"
                + "]]></PicUrl><Url><![CDATA[" + httpProperties.getMobileUrl() + "/partner/grant" + "]]></Url></item>";
        String articles = "<Articles>" + artile1 + "</Articles>";
        return "<xml>" + toUserName + fromUserName + createTime + msgType + articleCount + articles + "</xml>";
    }

}
