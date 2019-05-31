package com.d2c.member.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.logger.model.EmailLog.EmailLogType;
import com.d2c.logger.model.LoginLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.EmailLogService;
import com.d2c.logger.service.LoginLogService;
import com.d2c.logger.service.SmsLogService;
import com.d2c.member.dao.PasswordHashMapper;
import com.d2c.member.dto.MemberInfoDto;
import com.d2c.member.enums.MemberTaskEnum;
import com.d2c.member.enums.MemberTypeEnum;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.PasswordHash;
import com.d2c.member.mongo.services.MemberTaskExecService;
import com.d2c.util.string.LoginUtil;
import com.d2c.util.string.RandomUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("loginService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class LoginServiceImpl implements LoginService {

    protected static final Log logger = LogFactory.getLog(LoginServiceImpl.class);
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberLoginService memberLoginService;
    @Autowired
    private SmsLogService smsLogService;
    @Autowired
    private EmailLogService emailLogService;
    @Autowired
    private PasswordHashMapper passwordMapper;
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;
    @Reference
    private MemberTaskExecService memberTaskExecService;

    /**
     * 记录登录日志
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void createLoginLog(String type, String mobile, String info, String ip, String device, String version,
                               String userAgent) {
        LoginLog log = new LoginLog();
        log.setType(type);
        log.setMobile(mobile);
        log.setInfo(info);
        log.setIp(ip);
        log.setDevice(device);
        log.setVersion(version);
        log.setUserAgent(userAgent);
        loginLogService.insert(log);
    }

    @Override
    public String validate(String rePassword, String password, String loginCode) {
        // 账号非空
        if (loginCode == null) {
            return "注册账号不能为空！";
        }
        // 账号格式
        Map<Integer, String> result = this.validateLoginCode(loginCode);
        if (result.get(-1) != null) {
            return result.get(-1);
        }
        // 密码一致
        if (rePassword == null || password.length() < 1 || !rePassword.equals(password)) {
            return "两次输入的密码不一致！";
        }
        return null;
    }

    @Override
    public Map<Integer, String> validateLoginCode(String loginCode) {
        Map<Integer, String> result = new HashMap<>();
        if (!LoginUtil.checkMobile(loginCode)) {
            result.put(-1, "账号格式不正确！");
            return result;
        }
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        if (memberInfo != null) {
            result.put(-1, "该手机号码已被注册！");
            result.put(1, memberInfo.getNationCode());
            return result;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public String doVerify(String loginCode, String code) {
        int errorTimes = 0;
        Integer errorCount = redisHandler.get("code_error_times_" + loginCode);
        if (errorCount != null) {
            errorTimes = errorCount;
        }
        if (errorTimes >= 3) {
            passwordMapper.doInvalid(loginCode);
            return "验证码输错次数过多，请重新发送验证码！";
        }
        code = DigestUtils.md5Hex(code);
        int success = passwordMapper.verify(loginCode, code);
        if (success <= 0) {
            redisHandler.set("code_error_times_" + loginCode, errorTimes + 1);
            return "验证码不存在或已过期！";
        }
        redisHandler.delete("code_error_times_" + loginCode);
        return "OK";
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public Member doThirdRegist(String openId, String unionId, String source, String device, String loginIp,
                                String nickname, String headPic, String sex, String accessToken, String partnerOpenId, String version,
                                String userAgent) {
        String token = "Y-" + DigestUtils.md5Hex(accessToken + System.currentTimeMillis());
        Member member = new Member();
        member.setOpenId(openId);
        member.setUnionId(unionId);
        member.setSource(source);
        member.setDevice(device);
        member.setRegisterIp(loginIp);
        member.setNickname(nickname);
        member.setHeadPic(headPic);
        member.setSex(sex);
        member.setToken(token);
        member.setPartnerOpenId(partnerOpenId);
        member = memberService.insert(member);
        // 登录日志
        this.createLoginLog("第三方登录", unionId, source + "新用户，openId:" + openId, loginIp, device, version, userAgent);
        return member;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public String doThirdLogin(String openId, String unionId, String source, String device, String loginIp,
                               String nickname, String headPic, String sex, String accessToken, String gzOpenId, String partnerOpenId,
                               String version, String userAgent) {
        String token = "Y-" + DigestUtils.md5Hex(accessToken + System.currentTimeMillis());
        memberService.updateLogin(openId, unionId, source, nickname, headPic, sex, token, gzOpenId, partnerOpenId);
        // 登录日志
        this.createLoginLog("第三方登录", unionId, source + "老用户，openId:" + openId, loginIp, device, version, userAgent);
        return token;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public MemberInfoDto doRegist(String nationCode, String loginCode, String password, String source, String device,
                                  String loginIp, Long parent_id, String version, String userAgent) {
        MemberInfoDto dto = this.processRegist(null, nationCode, loginCode, password, source, device, loginIp,
                parent_id);
        // 登录日志
        this.createLoginLog("用户注册", loginCode, source + "新用户，mobile:" + loginCode, loginIp, device, version, userAgent);
        return dto;
    }

    private MemberInfoDto processRegist(Member member, String nationCode, String loginCode, String password,
                                        String source, String device, String loginIp, Long parent_id) {
        MemberInfo memberInfo;
        String tgt = UUID.randomUUID().toString();
        String token = "H-" + DigestUtils.md5Hex(tgt + System.currentTimeMillis());
        memberInfo = new MemberInfo();
        memberInfo.setNationCode(nationCode);
        memberInfo.setLoginCode(loginCode);
        if (password == null) {
            password = MD5Util.encodeMD5Hex(loginCode).substring(0, 8);
        }
        memberInfo.setPassword(DigestUtils.md5Hex(password));
        memberInfo.setSource(source);
        memberInfo.setDevice(device);
        memberInfo.setRegisterIp(loginIp);
        memberInfo.setMobile(loginCode);
        if (member != null) {
            memberInfo.setSex(member.getSex() == null ? "女" : member.getSex());
            memberInfo.setNickname(member.getNickname());
            memberInfo.setHeadPic(member.getHeadPic());
        } else {
            memberInfo.setSex("女");
        }
        memberInfo.setParentId(parent_id);
        memberInfo = memberInfoService.insert(memberInfo);
        memberLoginService.insert(loginCode, device, token);
        try {
            // 新手任务 - 注册成功
            memberTaskExecService.taskDone(memberInfo.getId(), MemberTaskEnum.FRIST_REGISTER);
            // 成功短信提示
            String smsContent = "恭喜您成为D2C官网(www.d2cmall.com)的会员，您的账号：" + loginCode + "，密码：" + password + "，请妥善保管。";
            smsLogService.doSendSms(nationCode, loginCode, smsContent);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        MemberInfoDto dto = BeanUt.buildBean(memberInfo, MemberInfoDto.class);
        dto.setToken(token);
        return dto;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String doLogin(String loginCode, String device, String loginIp, String loginDevice, String version,
                          String userAgent, String thirdToken) {
        String tgt = UUID.randomUUID().toString();
        String token;
        if (!StringUtil.isBlank(thirdToken)) {
            token = "H" + thirdToken;
        } else {
            token = "H-" + DigestUtils.md5Hex(tgt + System.currentTimeMillis());
        }
        memberLoginService.insert(loginCode, device, token);
        memberInfoService.updateLogin(loginCode, loginDevice);
        memberInfoService.cleanLoginError(loginCode);
        // 登录日志
        this.createLoginLog("用户登录", loginCode, device + "老用户，mobile:" + loginCode, loginIp, device, version, userAgent);
        return token;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doLogOut(String unionId, String loginCode, String token, String device, String loginIp, String version,
                        String userAgent) {
        int success = 0;
        if (StringUtil.isBlank(token)) {
            return success;
        }
        if (token.startsWith("Y")) {
            success = memberService.updateToken(unionId, null);
        }
        if (token.startsWith("H")) {
            success = memberLoginService.deleteByToken(token);
            redisHandler.delete("memberInfo_" + token);
        }
        // 登录日志
        this.createLoginLog("用户退出", loginCode, device + "老用户，mobile:" + loginCode, loginIp, device, version, userAgent);
        return success;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public MemberInfoDto doBindMemberInfo(Member member, String nationCode, String loginCode, String unionId,
                                          String source, String device, String loginIp, Long parent_id, String version, String userAgent,
                                          String thirdToken) {
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        String nickname = null;
        String headPic = null;
        MemberInfoDto dto = null;
        if (memberInfo == null) {
            dto = this.processRegist(member, nationCode, loginCode, null, source, device, loginIp, parent_id);
        } else {
            Member binded = memberService.findByLoginCodeAndSource(source, loginCode);
            if (binded != null) {
                throw new BusinessException("该手机账户已绑定" + MemberTypeEnum.valueOf(source).getDisplay() + "，请勿多次绑定！");
            }
            String token = this.doLogin(loginCode, device, loginIp, null, version, userAgent, thirdToken);
            dto = BeanUt.buildBean(memberInfo, MemberInfoDto.class);
            dto.setToken(token);
            if (StringUtil.isBlank(memberInfo.getNickname())) {
                nickname = member.getNickname();
            }
            if (StringUtil.isBlank(memberInfo.getHeadPic())) {
                headPic = member.getHeadPic();
            }
            if (memberInfo.getPartnerId() == null && memberInfo.getParentId() == null && parent_id != null) {
                memberInfoService.doBindParent(memberInfo.getId(), parent_id);
            }
        }
        memberService.doBindMemberInfo(unionId, dto.getId(), loginCode, nickname, headPic);
        // 登录日志
        this.createLoginLog("绑定手机", loginCode, device + "新用户，mobile:" + loginCode, loginIp, device, version, userAgent);
        return dto;
    }

    @Override
    public String doSendValidateMsg(String nationCode, String loginCode, SmsLogType logType, String source,
                                    String loginIp, String method) {
        if (logType == null) {
            logType = SmsLogType.MEMBERMOBILE;
        }
        String code = null;
        if (LoginUtil.checkMobile(loginCode)) { // 手机账号
            try {
                code = this.sendSms(nationCode, loginCode, logType, source, loginIp, method);
            } catch (Exception e) {
                throw new BusinessException("发送短信超时，请稍后再试！");
            }
        } else {
            throw new BusinessException("账号格式不正确！");
        }
        this.insertPasswordHash(loginCode, code);
        redisHandler.delete("code_error_times_" + loginCode);
        return code;
    }

    private String sendSms(String nationCode, String loginCode, SmsLogType logType, String source, String loginIp,
                           String method) {
        String code = RandomUtil.getRandomNum(4);
        String content = "您好！您的验证码为" + code + "，验证码20分钟内有效。如果出现异常请联系客服。D2C提示您，注意保密验证码";
        smsLogService.sendSms(nationCode, loginCode, code, content, logType, source, loginIp, method);
        return code;
    }

    private String sendEmail(String loginCode, MemberInfo memberInfo) {
        String code = RandomUtil.getRandomNum(5);
        Map<String, String> replaces = new HashMap<>();
        replaces.put("name", loginCode);
        replaces.put("url", code);
        emailLogService.sendEmail(loginCode, "", 1L, replaces, "", memberInfo.getId(), EmailLogType.MEMBER);
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public void insertPasswordHash(String loginCode, String verify) {
        PasswordHash passwordHash = new PasswordHash();
        passwordHash.setLoginCode(loginCode);
        passwordHash.setHash(DigestUtils.md5Hex(verify));
        passwordMapper.insert(passwordHash);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int deleteExpireLog() {
        return passwordMapper.deleteExpireLog();
    }

    @Override
    public int countDangerPasswd(String password) {
        return passwordMapper.countDangerPasswd(password);
    }

}
