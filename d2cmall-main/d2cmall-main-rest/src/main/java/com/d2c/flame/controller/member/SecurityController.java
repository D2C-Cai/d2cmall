package com.d2c.flame.controller.member;

import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.Account;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.LoginService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.util.string.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 修改、重置账户登录密码，支付密码
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/security")
public class SecurityController extends BaseController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private LoginService loginService;

    /**
     * 修改用户密码（旧）
     *
     * @param oldPassword     老密码
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @return
     */
    @RequestMapping(value = "/member/password", method = RequestMethod.POST)
    public ResponseResult updatePassword(@RequestParam(required = true) String oldPassword,
                                         @RequestParam(required = true) String newPassword, @RequestParam(required = true) String confirmPassword) {
        throw new BusinessException("账户安全升级，修改登录密码，请升级App到最新版本！");
    }

    /**
     * 修改钱包密码
     *
     * @param mobile          手机号
     * @param newPassword     新密码
     * @param confirmPassword 确认密码
     * @param code            验证码
     * @return
     */
    @RequestMapping(value = "/account/password", method = RequestMethod.POST)
    public ResponseResult changePassword(@RequestParam(required = true) String mobile,
                                         @RequestParam(required = true) String newPassword, @RequestParam(required = true) String confirmPassword,
                                         @RequestParam(required = true) String code, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        try {
            int success = 0;
            if (StringUtil.hasBlack(new Object[]{newPassword, confirmPassword})
                    || !newPassword.equals(confirmPassword)) {
                throw new BusinessException("两次输入密码不一致，请重新输入！");
            }
            AssertUt.isPassword(newPassword);
            Account account = accountService.findByMemberId(memberInfo.getId());
            if (account == null || account.getStatus() < 0) {
                throw new BusinessException("您的钱包账户异常，修改密码不成功！");
            }
            String message = loginService.doVerify(memberInfo.getLoginCode(), code);
            if (!message.equalsIgnoreCase("OK")) {
                throw new BusinessException(message);
            }
            String password = DigestUtils.md5Hex(newPassword);
            if (loginService.countDangerPasswd(password) == 1) {
                throw new BusinessException("密码过于简单，修改密码不成功！");
            }
            // 没有设置密码
            if (StringUtils.isBlank(account.getPassword())) {
                success = accountService.doSetPassword(account.getId(), account.getAccount(),
                        memberInfo.getNationCode(), password);
            } else {
                // 老用户更新密码
                success = accountService.doChangePassword(account.getId(), null, password);
            }
            if (success < 1) {
                throw new BusinessException("钱包密码设置不成功！");
            }
            loginService.createLoginLog("重置钱包密码", mobile, "重置钱包密码成功，passwd:" + newPassword, getLoginIp(),
                    DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
        } catch (BusinessException e) {
            loginService.createLoginLog("重置钱包密码", mobile, "重置钱包密码失败，passwd:" + newPassword + "， 原因" + e.getMessage(),
                    getLoginIp(), DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
            throw new BusinessException(e.getMessage());
        }
        return result;
    }

    /**
     * 重置登录密码
     *
     * @param loginCode 手机号
     * @param password1 新密码
     * @param password2 确认密码
     * @param code      验证码
     * @return
     */
    @RequestMapping(value = "/reset/password", method = RequestMethod.POST)
    public ResponseResult resetPassword(@RequestParam String loginCode, @RequestParam String password,
                                        @RequestParam String confirmPassword, @RequestParam(required = true) String code, String appTerminal,
                                        String appVersion) {
        ResponseResult result = new ResponseResult();
        String message = loginService.doVerify(loginCode, code);
        try {
            if (!message.equalsIgnoreCase("OK")) {
                throw new BusinessException(message);
            }
            if (!password.equals(confirmPassword)) {
                throw new BusinessException("两次输入的密码不一致！");
            }
            AssertUt.isPassword(password);
            if (loginService.countDangerPasswd(DigestUtils.md5Hex(password)) == 1) {
                throw new BusinessException("密码过于简单，修改密码不成功！");
            }
            try {
                MemberInfo member = memberInfoService.findByLoginCode(loginCode);
                if (member != null) {
                    member.setPassword(DigestUtils.md5Hex(password));
                    memberInfoService.updatePassword(member.getLoginCode(), member.getPassword());
                }
                loginService.createLoginLog("重置账号密码", loginCode, "重置账号密码成功，passwd:" + password, getLoginIp(),
                        DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
                result.setMsg("重置密码成功！");
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        } catch (BusinessException e) {
            loginService.createLoginLog("重置账号密码", loginCode, "重置账号密码失败，passwd:" + password + "， 原因" + e.getMessage(),
                    getLoginIp(), DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
            throw new BusinessException(e.getMessage());
        }
        return result;
    }

}
