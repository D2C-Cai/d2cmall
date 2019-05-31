package com.d2c.flame.convert;

import com.d2c.member.enums.DeviceTypeEnum;

public class AppVersionConvert {

    /**
     * 版本号支持格式 (X.X.X或X.X.XX)
     *
     * @param appTerminal
     * @param appVersion
     * @return
     */
    public static int convert(String appTerminal, String appVersion) {
        if (appTerminal == null) {
            return 0;
        }
        if (DeviceTypeEnum.divisionDevice(appTerminal).equals(DeviceTypeEnum.XCX.name())) {
            return 9999;
        }
        if (DeviceTypeEnum.divisionDevice(appTerminal).equals(DeviceTypeEnum.APPIOS.name())
                || DeviceTypeEnum.divisionDevice(appTerminal).equals(DeviceTypeEnum.APPANDROID.name())) {
            return Integer.parseInt(appVersion.replace(".", "")) * 10;
        }
        return 0;
    }

}
