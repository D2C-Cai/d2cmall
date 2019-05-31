package com.d2c.flame.controller.base;

import com.d2c.frame.web.control.BaseControl;
import com.d2c.member.enums.DeviceTypeEnum;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceType;
import org.springframework.mobile.device.DeviceUtils;

/**
 * 基础控制层类
 */
public abstract class BaseDeviceController extends BaseControl {

    public final static String PC_UDID = "pc_udid";

    /**
     * 操作系统平台类型
     *
     * @return IOS, ANDROID, UNKNOWN
     */
    protected String getPlatformName() {
        return getDevice().getDevicePlatform().name();
    }

    /**
     * Device设备类型
     *
     * @return MOBILE 手机, TABLET 平板电脑, NORMAL 其他普通设备PC电脑
     */
    protected String getDeviceTypeName() {
        Device device = getDevice();
        if (device.isMobile()) {
            return DeviceType.MOBILE.name();
        } else if (device.isTablet()) {
            return DeviceType.TABLET.name();
        } else {
            return DeviceType.NORMAL.name();
        }
    }

    protected Device getDevice() {
        return DeviceUtils.getCurrentDevice(getRequest());
    }

    protected boolean isNormalDevice() {
        return getDevice().isNormal();
    }

    protected boolean isMobileDevice() {
        return getDevice().isMobile();
    }

    protected boolean isTabletDevice() {
        return getDevice().isTablet();
    }

    protected DeviceTypeEnum getDeviceType() {
        DeviceTypeEnum dc = DeviceTypeEnum.PC;
        if (isNormalDevice()) {
            dc = DeviceTypeEnum.PC;
        } else if (isMobileDevice()) {
            dc = DeviceTypeEnum.MOBILE;
        } else if (isTabletDevice()) {
            dc = DeviceTypeEnum.TABLET;
        }
        return dc;
    }

}
