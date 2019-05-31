package com.d2c.util.string;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类 - 公用
 */
public class LoginUtil {

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean checkMobile(String mobiles) {
        boolean flag = false;
        if (StringUtils.isBlank(mobiles)) {
            return flag;
        }
        try {
            Pattern p = Pattern.compile("^\\d{0,15}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

}