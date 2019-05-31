package com.d2c.member.service;

import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.member.dto.MemberInfoDto;
import com.d2c.member.model.Member;

import java.util.Map;

public interface LoginService {

    /**
     * 登录日志
     *
     * @param type
     * @param mobile
     * @param info
     * @param ip
     * @param device
     * @param version
     * @param userAgent
     */
    void createLoginLog(String type, String mobile, String info, String ip, String device, String version,
                        String userAgent);

    /**
     * 注册前校验
     *
     * @param rePassword
     * @param password
     * @param loginCode
     * @return
     */
    String validate(String rePassword, String password, String loginCode);

    /**
     * 验证账号是否可以
     *
     * @param loginCode
     * @return
     */
    Map<Integer, String> validateLoginCode(String loginCode);

    /**
     * 手机验证码验证
     *
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    String doVerify(String mobile, String code);

    /**
     * 第三方注册
     *
     * @param openId
     * @param unionId
     * @param source
     * @param device
     * @param loginIp
     * @param nickname
     * @param headPic
     * @param sex
     * @param accessToken
     * @param partnerOpenId
     * @param version
     * @param userAgent
     * @return
     */
    Member doThirdRegist(String openId, String unionId, String source, String device, String loginIp, String nickname,
                         String headPic, String sex, String accessToken, String partnerOpenId, String version, String userAgent);

    /**
     * 第三方登录
     *
     * @param openId
     * @param unionId
     * @param source
     * @param device
     * @param loginIp
     * @param nickname
     * @param headPic
     * @param sex
     * @param accessToken
     * @param gzOpenId
     * @param partnerOpenId
     * @param version
     * @param userAgent
     * @return
     */
    String doThirdLogin(String openId, String unionId, String source, String device, String loginIp, String nickname,
                        String headPic, String sex, String accessToken, String gzOpenId, String partnerOpenId, String version,
                        String userAgent);

    /**
     * 会员注册
     *
     * @param nationCode
     * @param loginCode
     * @param password
     * @param source
     * @param device
     * @param loginIp
     * @param parent_id
     * @param version
     * @param userAgent
     * @return
     */
    MemberInfoDto doRegist(String nationCode, String loginCode, String password, String source, String device,
                           String loginIp, Long parent_id, String version, String userAgent);

    /**
     * 会员登录
     *
     * @param loginCode
     * @param device
     * @param loginIp
     * @param loginDevice
     * @param version
     * @param userAgent
     * @param thirdToken
     * @return
     */
    String doLogin(String loginCode, String device, String loginIp, String loginDevice, String version,
                   String userAgent, String thirdToken);

    /**
     * 会员注销
     *
     * @param unionId
     * @param loginCode
     * @param token
     * @param device
     * @param loginIp
     * @param version
     * @param userAgent
     * @return
     */
    int doLogOut(String unionId, String loginCode, String token, String device, String loginIp, String version,
                 String userAgent);

    /**
     * 绑定D2C会员
     *
     * @param member
     * @param nationCode
     * @param loginCode
     * @param unionId
     * @param source
     * @param device
     * @param loginIp
     * @param parent_id
     * @param version
     * @param userAgent
     * @param thirdToken
     * @return
     */
    MemberInfoDto doBindMemberInfo(Member member, String nationCode, String loginCode, String unionId, String source,
                                   String device, String loginIp, Long parent_id, String version, String userAgent, String thirdToken);

    /**
     * 发送验证信息
     *
     * @param nationCode
     * @param loginCode
     * @param logType
     * @param source
     * @param loginIp
     * @param method
     * @return
     */
    String doSendValidateMsg(String nationCode, String loginCode, SmsLogType logType, String source, String loginIp,
                             String method);

    /**
     * 插入hash值
     *
     * @param loginCode
     * @param verify
     */
    void insertPasswordHash(String loginCode, String verify);

    /**
     * 过期日志删除
     *
     * @return
     */
    int deleteExpireLog();

    /**
     * 查询是否是危险的密码
     *
     * @param password
     * @return
     */
    int countDangerPasswd(String password);

}
