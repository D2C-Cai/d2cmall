package com.d2c.flame.controller.callback;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.controller.behavior.EventController;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.enums.MemberTypeEnum;
import com.d2c.member.model.Designers;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberDetail;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.*;
import com.d2c.order.third.payment.wxpay.core.WxXcxConfig;
import com.d2c.util.string.StringUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * 第三方登录
 */
@RestController
@RequestMapping(value = "/v3/api/callback")
public class ThirdLoginController extends BaseController {

    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberDetailService memberDetailService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private DesignersService designersService;
    @Autowired
    private SignatureService signatureService;
    @Autowired
    private WxXcxConfig wxXcxConfig;
    @Autowired
    private EventController eventController;

    /**
     * APP第三方登录
     *
     * @param source
     * @param appTerminal
     * @param accessToken
     * @param openId
     * @param username
     * @param thirdHeadPic
     * @param request
     * @param device
     * @param sex
     * @param unionId
     * @param unionid
     * @return
     */
    @RequestMapping(value = "/{source}", method = RequestMethod.POST)
    public ResponseResult callback(@PathVariable String source, String appVersion,
                                   @RequestParam(required = true) String appTerminal, @RequestParam(required = true) String accessToken,
                                   @RequestParam(required = true) String openId, String username, String thirdHeadPic, String sex,
                                   String unionId) {
        ResponseResult result = new ResponseResult();
        switch (source) {
            case "QQ":
                source = MemberTypeEnum.QQ.name();
                unionId = openId;
                break;
            case "SINA":
                source = MemberTypeEnum.Weibo.name();
                unionId = openId;
                break;
            case "WECHAT":
                source = MemberTypeEnum.Weixin.name();
                if (StringUtil.isBlank(unionId) || unionId.equalsIgnoreCase("undefined")
                        || unionId.equalsIgnoreCase("null")) {
                    throw new BusinessException("unionId不能为空！");
                }
                break;
            case "XCX":
                source = MemberTypeEnum.WeixinXcx.name();
                if (StringUtil.isBlank(unionId) || unionId.equalsIgnoreCase("undefined")
                        || unionId.equalsIgnoreCase("null")) {
                    throw new BusinessException("unionId不能为空！");
                }
                break;
        }
        Member member = memberService.findByUnionIdAndSource(source, unionId);
        MemberDto dto = new MemberDto();
        String device = DeviceTypeEnum.divisionDevice(appTerminal);
        if (member == null) {
            member = loginService.doThirdRegist(openId, unionId, source, device, getLoginIp(), username, thirdHeadPic,
                    sex, accessToken, null, appVersion, null);
            BeanUtils.copyProperties(member, dto);
        } else {
            String token = loginService.doThirdLogin(openId, unionId, source, device, getLoginIp(), username,
                    thirdHeadPic, sex, accessToken, null, null, appVersion, null);
            member.setNickname(username);
            member.setHeadPic(thirdHeadPic);
            member.setSex(sex);
            member.setToken(token);
            if (member.isD2c()) {
                MemberInfo memberInfo = memberInfoService.findByLoginCode(member.getLoginCode());
                member.setToken(loginService.doLogin(member.getLoginCode(), device, getLoginIp(), null, appVersion,
                        null, member.getToken()));
                dto.setOpenId(openId);
                dto.setUnionId(unionId);
                dto.setSource(source);
                BeanUtils.copyProperties(memberInfo, dto);
            } else {
                BeanUtils.copyProperties(member, dto);
            }
        }
        eventController.onThirdLogin(member);
        JSONObject json = dto.toJson(member.getToken());
        json = this.getDetail(dto, json);
        result.put("member", json);
        return result;
    }

    /**
     * 获取用户详细信息
     *
     * @param dto
     * @param json
     * @return
     */
    private JSONObject getDetail(MemberDto dto, JSONObject json) {
        if (dto.isD2c()) {
            MemberDetail memberDetail = memberDetailService.findByMemberInfoId(dto.getId());
            if (memberDetail != null) {
                json.putAll(memberDetail.toJson());
            }
        }
        if (dto.getDesignerId() != null) {
            Designers designers = designersService.findById(dto.getDesignerId());
            if (designers != null) {
                json.putAll(designers.toJsonMap());
            }
        }
        return json;
    }

    /**
     * 更新游客信息
     *
     * @param unionId
     * @param nickname
     * @param headPic
     * @param sex
     * @return
     */
    @RequestMapping(value = "/update/member", method = RequestMethod.POST)
    public ResponseResult updateMember(@RequestParam(required = true) String unionId, String nickname, String headPic,
                                       String sex) {
        ResponseResult result = new ResponseResult();
        memberService.updateInfo(unionId, nickname, headPic, sex);
        return result;
    }

    /**
     * 小程序获取key
     *
     * @param code
     * @param appId
     * @return
     */
    @RequestMapping(value = "/wxsession/{code}", method = RequestMethod.POST)
    public ResponseResult wxsession(@PathVariable String code, String appId) {
        ResponseResult result = new ResponseResult();
        JSONObject respJson = new JSONObject();
        String sponseStr = "";
        String APP_ID = wxXcxConfig.getAPP_ID();
        String APP_SECRET = wxXcxConfig.getAPP_SECRET();
        if (StringUtil.isNotBlank(appId)) {
            Signature signature = signatureService.findByAppid(appId);
            if (signature == null) {
                throw new BusinessException("无效的appId");
            }
            APP_ID = appId;
            APP_SECRET = signature.getAppsecret();
        }
        try {
            sponseStr = restTemplate.getForObject(wxXcxConfig.getWX_URL() + "appid=" + APP_ID + "&secret=" + APP_SECRET
                    + "&js_code=" + code + "&grant_type=authorization_code", String.class);
            respJson = JSONObject.parseObject(sponseStr);
        } catch (Exception e) {
            throw new BusinessException("code值异常");
        }
        if (respJson.get("openid") == null) {
            throw new BusinessException("sessionKey获取失败");
        }
        String openId = respJson.getString("openid");
        String unionId = respJson.getString("unionid");
        String sessionKey = respJson.getString("session_key");
        result.put("openId", openId);
        result.put("unionId", unionId);
        result.put("sessionKey", sessionKey);
        return result;
    }

    /**
     * 小程序用户解密
     *
     * @param sessionKey
     * @param encryptedData
     * @param iv
     * @return
     */
    @RequestMapping(value = "/wxuserinfo", method = RequestMethod.POST)
    public ResponseResult wxuserinfo(String sessionKey, String encryptedData, String iv) {
        ResponseResult result = new ResponseResult();
        JSONObject userinfo = this.getUserInfo(encryptedData, sessionKey, iv);
        result.put("userinfo", userinfo);
        return result;
    }

    /**
     * 解密用户敏感数据获取用户信息
     *
     * @param sessionKey    数据进行加密签名的密钥
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv            加密算法的初始向量
     */
    private JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64Utils.decodeFromString(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64Utils.decodeFromString(sessionKey);
        // 偏移量
        byte[] ivByte = Base64Utils.decodeFromString(iv);
        try {
            // 如果密钥不足16位，那么就补足
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
