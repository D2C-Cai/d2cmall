package com.d2c.flame.controller.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.AppMenu;
import com.d2c.content.model.AppNavigation;
import com.d2c.content.model.AppVersion;
import com.d2c.content.model.SplashScreen;
import com.d2c.content.service.AppMenuService;
import com.d2c.content.service.AppNavigationService;
import com.d2c.content.service.AppVersionService;
import com.d2c.content.service.SplashScreenService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.convert.AppVersionConvert;
import com.d2c.logger.model.MemberDevice;
import com.d2c.logger.service.MemberDeviceService;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import com.d2c.order.model.Setting;
import com.d2c.order.service.SettingService;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/home")
public class AppInitController extends BaseController {

    @Autowired
    private AppMenuService appMenuService;
    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private AppNavigationService appNavigationService;
    @Autowired
    private SplashScreenService splashScreenService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private MemberDeviceService memberDeviceService;
    @Autowired
    private PartnerService partnerService;

    /**
     * 获取App闪屏
     *
     * @param screenSize
     * @return
     */
    @RequestMapping(value = "/splashscreen", method = RequestMethod.GET)
    public ResponseResult getSplashScreen(String screenSize) {
        ResponseResult result = new ResponseResult();
        SplashScreen splashScreen = splashScreenService.findCurrentVersion();
        if (splashScreen == null) {
            result.setStatus(0);
            return result;
        }
        JSONObject json = splashScreen.toJson();
        if (StringUtils.isNotBlank(screenSize)) {
            switch (screenSize) {
                case "320480":
                    json.put("pics", JSONArray.parseArray(splashScreen.getPic320480()));
                    break;
                case "320568":
                    json.put("pics", JSONArray.parseArray(splashScreen.getPic320568()));
                    break;
                case "375667":
                    json.put("pics", JSONArray.parseArray(splashScreen.getPic375667()));
                    break;
                case "414736":
                    json.put("pics", JSONArray.parseArray(splashScreen.getPic414736()));
                    break;
                case "375812":
                    json.put("pics", JSONArray.parseArray(splashScreen.getPic375812()));
                    break;
                default:
                    json.put("pics", JSONArray.parseArray(splashScreen.getPic414736()));
            }
        } else {
            json.put("pics", JSONArray.parseArray(splashScreen.getPic414736()));
        }
        JSONArray array = json.getJSONArray("pics");
        if (array == null || array.size() == 0) {
            result.setStatus(0);
            return result;
        }
        int random = (int) (Math.random() * array.size());
        JSONArray pics = new JSONArray();
        pics.add(array.get(random));
        json.put("pics", pics);
        if (StringUtils.isBlank(splashScreen.getUrls())) {
            json.put("url", "");
        } else {
            JSONObject urls = JSONObject.parseObject(splashScreen.getUrls());
            json.put("url", urls.get(random + ""));
        }
        result.put("splashscreen", json);
        return result;
    }

    /**
     * 获取App底部导航
     *
     * @return
     */
    @RequestMapping(value = "/appnavigation", method = RequestMethod.GET)
    public ResponseResult getAppNavigation() {
        ResponseResult result = new ResponseResult();
        List<AppNavigation> list = appNavigationService.findAllEnable();
        JSONArray array = new JSONArray();
        list.forEach(item -> array.add(item.toJson()));
        result.put("appNavigation", array);
        return result;
    }

    /**
     * 获取App我的菜单
     *
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/appmenu/list", method = RequestMethod.GET)
    public ResponseResult getMenuList(String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        Long memberId = 0L;
        // 处理会员菜单权限
        String[] memberType = new String[]{"type0"};
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            memberId = memberInfo.getId();
            memberType = getMemberTypes(memberInfo);
        } catch (NotLoginException e) {
        }
        List<AppMenu> list = appMenuService.findByStatus(1, "v3");
        // 分销用户 3.1.9 之前显示旧菜单id=12，之后显示新菜单=13
        if (memberType.length == 3) {
            boolean partnerMenuToWap = true;
            try {
                partnerMenuToWap = (AppVersionConvert.convert(appTerminal, appVersion) > 0
                        && AppVersionConvert.convert(appTerminal, appVersion) < 3190);
            } catch (Exception e) {
            }
            for (AppMenu appMenu : list) {
                if (partnerMenuToWap && appMenu.getId() == 13) {
                    list.remove(appMenu);
                    break;
                } else if (!partnerMenuToWap && appMenu.getId() == 12) {
                    list.remove(appMenu);
                    break;
                }
            }
        }
        // 会员菜单过滤
        JSONArray menuList = new JSONArray();
        final String memberIdStr = String.valueOf(memberId);
        final String[] memberTyps = memberType;
        list.forEach(item -> {
            if (item.getMemberType() != null) {
                for (String type : memberTyps) {
                    if (item.getMemberType().contains(type)) {
                        item.setUrl(this.replaceUrl(item.getUrl(), memberIdStr));
                        menuList.add(item.toJson());
                        break;
                    }
                }
            } else {
                item.setUrl(this.replaceUrl(item.getUrl(), memberIdStr));
                menuList.add(item.toJson());
            }
        });
        result.put("menuList", menuList);
        return result;
    }

    private String[] getMemberTypes(MemberInfo memberInfo) {
        String[] types = null;
        Partner partner = null;
        if (memberInfo.getPartnerId() != null && (partner = partnerService.findById(memberInfo.getPartnerId())) != null
                && partner.getStatus() >= 0) {
            types = new String[3];
            types[0] = "type0";
            types[1] = "type" + memberInfo.getType();
            types[2] = "typep" + partner.getLevel();
        } else {
            types = new String[2];
            types[0] = "type0";
            types[1] = "type" + memberInfo.getType();
        }
        return types;
    }

    private String replaceUrl(String url, String memberId) {
        return StringUtils.isNotBlank(url) ? url.replace("{memberId}", memberId) : "";
    }

    /**
     * 获取App更新信息
     *
     * @param appTerminal
     * @param appVersion
     * @param type
     * @return
     */
    @RequestMapping(value = "/upgrade/plus", method = RequestMethod.GET)
    public ResponseResult getUpgrade(String appTerminal, String appVersion, String type) {
        ResponseResult result = new ResponseResult();
        if (StringUtils.isEmpty(appTerminal) || StringUtils.isEmpty(appVersion)) {
            throw new BusinessException("获取版本号或终端不成功！");
        }
        if (StringUtils.isBlank(type)) {
            type = "free";
        }
        appTerminal = DeviceTypeEnum.divisionDevice(appTerminal);
        AppVersion lastAppVersion = appVersionService.findLastVersion(appTerminal, type, appVersion);
        // 是否启用升级这个功能
        Setting setting = new Setting();
        if (DeviceTypeEnum.APPANDROID.toString().equalsIgnoreCase(appTerminal)) {
            setting = settingService.findByCode(Setting.ANDROID);
        } else if (DeviceTypeEnum.APPIOS.toString().equalsIgnoreCase(appTerminal)) {
            if ("pay".equalsIgnoreCase(type)) {
                setting = settingService.findByCode(Setting.IOSPAY);
            } else if ("personal".equalsIgnoreCase(type)) {
                setting = settingService.findByCode(Setting.IOSPERSONAL);
            } else {
                setting = settingService.findByCode(Setting.IOSFREE);
            }
        }
        if (setting != null) {
            result.put("isUpgrade", setting.getStatus());
        } else {
            result.put("isUpgrade", 0);
        }
        if (lastAppVersion != null) {
            JSONObject value = new JSONObject();
            value.put("lasted", lastAppVersion.getVersion());
            value.put("url", lastAppVersion.getUrl());
            value.put("info", lastAppVersion.getInfo());
            value.put("size", lastAppVersion.getSize());
            value.put("icon", lastAppVersion.getIconUrl());
            value.put("must", lastAppVersion.getUpgrade());
            result.put("value", value.toJSONString());
        }
        return result;
    }

    /**
     * 获取App默认支付方式
     *
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/channel", method = RequestMethod.GET)
    public ResponseResult getPayChannel(String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        // 默认的支付方式
        List<String> channels = new ArrayList<>();
        // 花呗利息承担者，默认D2C承担
        result.put("interestHolder", 1);
        result.put("channels", channels);
        return result;
    }

    /**
     * 刷新App最后登录信息
     *
     * @param memberId
     * @param appTerminal
     * @param appVersion
     * @param deviceLabel
     * @param platform
     * @param clientId
     * @return
     */
    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public ResponseResult updateDevice(@RequestParam(required = true) Long memberId, String appTerminal,
                                       String appVersion, String deviceLabel, String platform, String clientId) {
        ResponseResult result = new ResponseResult();
        MemberDevice md = new MemberDevice();
        md.setMemberId(memberId);
        md.setApp(appTerminal);
        md.setVersion(appVersion);
        md.setDevice(deviceLabel);
        md.setOs(platform);
        md.setIp(this.getLoginIp());
        md.setClientId(clientId);
        if (!StringUtil.isBlank(clientId)) {
            memberDeviceService.doInsert(md);
        }
        return result;
    }

    /**
     * App开启时刷新用户信息
     *
     * @return
     */
    @RequestMapping(value = "/arrival", method = RequestMethod.GET)
    public ResponseResult doArrival() {
        ResponseResult result = new ResponseResult();
        MemberDto dto = this.getLoginDto();
        MemberInfo memberInfo = memberInfoService.findById(dto.getId());
        if (memberInfo == null) {
            throw new BusinessException("非D2C会员！");
        }
        BeanUtils.copyProperties(memberInfo, dto);
        JSONObject json = dto.toJson(getRequest().getHeader("accesstoken"));
        if (dto.isD2c()) {
            json = memberInfoService.findMemberDetail(memberInfo.getId(), memberInfo.getDesignerId(), json);
        }
        result.put("member", json);
        if (memberInfo.getPartnerId() != null) {
            partnerService.doLogin(memberInfo.getPartnerId());
        }
        return result;
    }

}
