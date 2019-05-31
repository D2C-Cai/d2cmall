package com.d2c.flame.controller.callback;

import com.alibaba.fastjson.JSONObject;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.enums.MemberTypeEnum;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.LoginService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberService;
import com.d2c.member.third.oauth.QQOauthClient;
import com.d2c.member.third.oauth.WeiboOauthClient;
import com.d2c.member.third.oauth.WeixinGzOauthClient;
import com.d2c.member.third.oauth.WeixinKfOauthClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class ThirdLoginController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private QQOauthClient qqAouth;
    @Autowired
    private WeiboOauthClient weiboAouth;
    @Autowired
    private WeixinKfOauthClient weixinKfAouth;
    @Autowired
    private WeixinGzOauthClient weixinGzAouth;

    @RequestMapping(value = "/member/doQuickLogin/{way}", method = RequestMethod.GET)
    public String doQuickLogin(@PathVariable String way) {
        if (MemberTypeEnum.QQ.name().equals(way)) {
            return this.qqAouth.getCode();
        } else if (MemberTypeEnum.Weibo.name().equals(way)) {
            return this.weiboAouth.getCode();
        } else if (MemberTypeEnum.Weixin.name().equals(way)) {
            return this.weixinKfAouth.getCode();
        } else {
            return RELOGIN;
        }
    }

    /**
     * 第三方通知
     *
     * @param source 类型
     * @param code   作为换取access_token的票据
     * @return
     */
    @RequestMapping(value = "/callback/{source}", method = RequestMethod.GET)
    public String index(@PathVariable String source, String code, ModelMap model, String path,
                        HttpServletResponse response) {
        // code作为换取access_token的票据
        // 若用户禁止授权，则重定向后不会带上code参数
        if (path == null) {
            path = "";
        }
        path = path.replaceAll("__", "&");
        Member member = null;
        if (code != null) {
            switch (source) {
                case "QQClient":
                    member = this.processQQ(code, source, response);
                    break;
                case "WeiboClient":
                    member = this.processWeiBo(code, source, response);
                    break;
                case "WeixinClient":
                    member = this.processWxKf(code, source, response);
                    break;
                case "WeixinGzClient":
                    // 微信浏览器
                    member = this.processWxGz(code, source, path, response);
                    break;
            }
            // 地址跳转
            if (StringUtils.isBlank(path) && (member == null || !member.isD2c())) {
                return "redirect:/member/bind";
            } else if (StringUtils.isBlank(path)) {
                return "redirect:/index";
            } else {
                return "redirect:" + path;
            }
        }
        return "redirect:/member/login";
    }

    private Member processQQ(String code, String source, HttpServletResponse response) {
        String openId = "";
        String accessToken = "";
        accessToken = this.qqAouth.getAccessToken(code);
        openId = qqAouth.getOpenId(accessToken);
        JSONObject user = qqAouth.getUser(accessToken, openId);
        source = MemberTypeEnum.QQ.name();
        String nickname = user.getString("nickname");
        nickname = getNickname(nickname);
        String sex = user.getString("gender").equals("女") ? "女" : "男";
        String thirdHeadPic = user.getString("figureurl_qq_2");
        return this.processThirdMember(openId, openId, source, nickname, thirdHeadPic, sex, accessToken, response);
    }

    private Member processWeiBo(String code, String source, HttpServletResponse response) {
        String openId = "";
        String accessToken = "";
        accessToken = this.weiboAouth.getAccessToken(code);
        openId = weiboAouth.getOpenId(accessToken);
        // uid = weiboAouth.getUid(accessToken);
        JSONObject user = weiboAouth.getUser(accessToken, openId);
        source = MemberTypeEnum.Weibo.name();
        String nickname = user.getString("name");
        nickname = getNickname(nickname);
        String sex = user.getString("gender").equals("m") ? "男" : "女";
        String thirdHeadPic = user.getString("avatar_hd");
        return this.processThirdMember(openId, openId, source, nickname, thirdHeadPic, sex, accessToken, response);
    }

    private Member processWxKf(String code, String source, HttpServletResponse response) {
        String openId = "";
        String accessToken = "";
        // 获取AccessToken和OpenId
        String tokenAndOpenId = weixinKfAouth.getAccessTokenAndOpenId(code);
        JSONObject tokenAndOpenIdJson = JSONObject.parseObject(tokenAndOpenId);
        accessToken = tokenAndOpenIdJson.getString("access_token");
        openId = tokenAndOpenIdJson.getString("openid");
        // 获取用户基本信息
        String wxuser = weixinKfAouth.getUser(accessToken, openId);
        JSONObject wxuserJson = JSONObject.parseObject(wxuser);
        source = MemberTypeEnum.Weixin.name();
        String nickname = getNickname(wxuserJson.getString("nickname"));
        int sexInt = wxuserJson.getIntValue("sex");
        String sex = (sexInt == 1 ? "男" : "女");
        String thirdHeadPic = wxuserJson.getString("headimgurl");
        String unionId = wxuserJson.getString("unionid");
        return this.processThirdMember(openId, unionId, source, nickname, thirdHeadPic, sex, accessToken, response);
    }

    private Member processWxGz(String code, String source, String path, HttpServletResponse response) {
        String openId = "";
        String accessToken = "";
        // 获取AccessToken和OpenId
        String tokenAndOpenId = weixinGzAouth.getAccessTokenAndOpenId(code);
        JSONObject tokenAndOpenIdJson = JSONObject.parseObject(tokenAndOpenId);
        accessToken = tokenAndOpenIdJson.getString("access_token");
        openId = tokenAndOpenIdJson.getString("openid");
        // 获取用户基本信息
        String wxuser = weixinGzAouth.getUser(accessToken, openId);
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
                    nickname, headPic, sex, accessToken, null, null, getUserAgent());
            BeanUtils.copyProperties(member, dto);
        } else {
            String token = loginService.doThirdLogin(openId, unionId, source, isMobileDevice() ? "WAP" : "PC",
                    getLoginIp(), nickname, headPic, sex, accessToken,
                    source.equals(MemberTypeEnum.WeixinGz.name()) ? openId : null, null, null, getUserAgent());
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
                dto.setGzOpenId(openId);
                dto.setSource(source);
                BeanUtils.copyProperties(memberInfo, dto);
            } else {
                BeanUtils.copyProperties(member, dto);
            }
        }
        Cookie tgc = new Cookie(Member.CASTGC, member.getToken());
        writeCookie(tgc, response);
        return member;
    }

}
