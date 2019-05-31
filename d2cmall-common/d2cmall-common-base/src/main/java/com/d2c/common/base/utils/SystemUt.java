package com.d2c.common.base.utils;

/**
 * JVM系统参数工具类
 *
 * @author wull
 */
public class SystemUt {

    private static String os = System.getProperty("os.name").toLowerCase();
    private static String userHome = System.getProperty("user.home");

    public static String os() {
        return os;
    }

    public static boolean isWindow() {
        return os.contains("windows");
    }

    public static boolean isLinux() {
        return os.contains("linux");
    }

    public static boolean isMac() {
        return os.contains("mac");
    }

    public static String getUserHome() {
        return userHome;
    }

}
