package com.d2c.flame.controller.base;

import com.d2c.behavior.mongo.dto.PersonDeviceDTO;
import com.d2c.behavior.mongo.enums.AppTerminalEnum;
import com.d2c.behavior.mongo.model.PersonDeviceDO;
import com.d2c.behavior.mongo.model.PersonSessionDO;
import com.d2c.behavior.services.SessionService;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.CookieUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;

import java.util.concurrent.TimeUnit;

/**
 * 用户Session控制层
 */
public class BaseSessionController extends BaseController {

    private final static String SESSION_CACHE_NAME = "person_session";
    private final static String SESSION_CACHE_DEVICE = "device_";
    @Autowired
    private SessionService sessionService;
    @Autowired
    private RedisHandler<String, PersonSessionDO> sessionCache;

    /**
     * Session校验
     */
    protected PersonSessionDO validateSession() {
        return validateSession(null);
    }

    protected PersonSessionDO validateSession(PersonDeviceDTO dto) {
        PersonSessionDO session = getPersonSession();
        if (session == null) {
            try {
                PersonDeviceDO device = buildDevice(dto);
                MemberInfo memberInfo = getLoginUnCheck();
                session = sessionService.nextSession(device, memberInfo, getToken());
                savePersonSession(session);
            } catch (BaseException e) {
                throw e;
            } catch (Exception e) {
                logger.error("PersonSession校验失败...", e);
            }
        }
        return session;
    }

    /**
     * PersonSessionDO token 缓存数据
     */
    protected PersonSessionDO getPersonSession() {
        return sessionCache.getAndExpire(cacheKey(), 2, TimeUnit.HOURS);
    }

    protected PersonSessionDO savePersonSession(PersonSessionDO session) {
        sessionCache.setInHours(cacheKey(), session, 2);
        return session;
    }

    protected void cleanPersonSession() {
        sessionCache.delete(cacheKey());
    }

    private String cacheKey() {
        String key = getToken();
        if (StringUtils.isBlank(key)) {
            String deviceId = getRequest().getHeader("udid");
            if (StringUtils.isBlank(deviceId)) {
                deviceId = CookieUt.getCookie(getRequest(), PC_UDID);
            }
            AssertUt.notBlank(deviceId, "用户Token, 设备UDID不能为空");
            key = SESSION_CACHE_DEVICE + deviceId;
        }
        return SESSION_CACHE_NAME + ":" + key;
    }

    /**
     * 查询并创建设备Device
     * <p>
     * 根据参数 PersonDeviceDTO 添加设备属性
     */
    protected PersonDeviceDO buildDevice(PersonDeviceDTO dto) {
        String deviceId = getDeviceId();
        PersonDeviceDO device = sessionService.findDeviceById(deviceId);
        if (device == null) {
            device = new PersonDeviceDO(deviceId);
        }
        if (dto != null) {
            device = BeanUt.apply(device, dto);
        }
        initDevice(device);
        return device;
    }

    /**
     * 设备唯一标识 Unique Device Identifier
     * <p>
     * Android: IMEI <br>
     * IOS: UDID <br>
     * WEB: 存入Cookie中UDID, SessionId
     */
    protected String getDeviceId() {
        String deviceId = getRequest().getHeader("udid");
        if (StringUtils.isEmpty(deviceId)) {
            // 根据token获取会员查询DeviceId
            String token = getToken();
            if (token.startsWith("Y")) {
                // Member 设备登录
                Member member = memberService.findByToken(token);
                if (member != null) {
                    deviceId = member.getUnionId();
                }
            } else if (token.startsWith("H")) {
                // MemberInfo 会员登录
                MemberInfo memberInfo = memberInfoService.findByToken(token);
                if (memberInfo != null) {
                    // 会员登录，并未带有设备信息，默认最后一次登录设备登录
                    PersonSessionDO ps = sessionService.findLastSessionByMemberId(memberInfo.getId());
                    if (ps != null) {
                        deviceId = ps.getDeviceId();
                    }
                }
            }
            // WEB浏览器自动生成deviceId放入cookie
            switch (getAppTerminal()) {
                case APPANDROID:
                case APPIOS:
                case XCX:
                    // PersonDeviceDO dev = initDevice(new
                    // PersonDeviceDO(deviceId));
                    // logger.error("UDID不能为空... device: " + JsonUt.serialize(dev) +
                    // " url:" + getRequest().getRequestURL());
                    AssertUt.notBlank(deviceId, "UDID不能为空");
                    break;
                case WEB:
                    deviceId = CookieUt.getCookie(getRequest(), PC_UDID);
                    if (StringUtils.isEmpty(deviceId)) {
                        deviceId = StringUt.getUUID();
                        CookieUt.setCookie(getResponse(), PC_UDID, deviceId);
                    }
                    break;
                default:
                    AssertUt.notBlank(deviceId, "UDID不能为空");
                    break;
            }
        }
        return deviceId;
    }

    /**
     * APP平台类型 AppTerminal
     *
     * @return WEB(" 网页 "), APPIOS("苹果APP"), APPANDROID("安卓APP"), XCX("小程序")
     * @see AppTerminalEnum
     */
    protected AppTerminalEnum getAppTerminal() {
        Device device = getDevice();
        // 判断是否为小程序XCX
        String appTerminal = getParameter("appTerminal");
        if (StringUtils.isNotBlank(appTerminal)) {
            appTerminal = appTerminal.toLowerCase();
        }
        if (StringUtils.contains(appTerminal, "xcx")) {
            return AppTerminalEnum.XCX;
        } else if (StringUtils.contains(appTerminal, "android")) {
            return AppTerminalEnum.APPANDROID;
        } else if (StringUtils.contains(appTerminal, "ios")) {
            return AppTerminalEnum.APPIOS;
        }
        String userAgent = getUserAgent();
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();
            // Android.*MicroMessenger.*miniProgram 安卓端的小程序
            // iPhone.*MicroMessenger 苹果端微信或小程序
            if (userAgent.contains("micromessenger")) {
                return AppTerminalEnum.XCX;
            }
        }
        switch (device.getDevicePlatform()) {
            case ANDROID:
                return AppTerminalEnum.APPANDROID;
            case IOS:
                return AppTerminalEnum.APPIOS;
            default:
                return AppTerminalEnum.WEB;
        }
    }

    /**
     * 操作平台类型
     *
     * @return IOS, ANDROID, UNKNOWN
     */
    protected String getAppVersion() {
        String appVersion = getParameter("appVersion");
        if (StringUtils.isNotEmpty(appVersion)) {
            // AppVersion 兼容 Android/1.4.5 -> 1.4.5
            appVersion = StringUtils.substringAfter(appVersion, "/");
            if (StringUtils.isNotEmpty(appVersion)) {
                return appVersion;
            }
        }
        return null;
    }

    protected String getAppTerminalVersion() {
        return getParameter("appTerminal") + "-" + getParameter("appVersion");
    }

    /**
     * 添加设备相关属性
     */
    private PersonDeviceDO initDevice(PersonDeviceDO bean) {
        bean.setUserAgent(getUserAgent());
        bean.setPlatform(getPlatformName());
        bean.setDeviceType(getDeviceTypeName());
        bean.setIp(getLoginIp());
        bean.setAppTerminal(getAppTerminal().name());
        bean.setAppVersion(getAppVersion());
        bean.setAppTerminalVersion(getAppTerminalVersion());
        bean.setAppVersionId(null);
        return bean;
    }

}
