package com.d2c.backend.rest;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.SuperCtrl;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.core.cache.old.CacheCallback;
import com.d2c.common.core.cache.old.CacheKey;
import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.content.model.AppVersion;
import com.d2c.content.model.AppVersion.VersionTypeEnum;
import com.d2c.content.service.AppVersionService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.dto.ResourceDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.ResourceSearcher;
import com.d2c.member.service.CommentService;
import com.d2c.member.service.MemberShareService;
import com.d2c.member.service.ResourceService;
import com.d2c.order.model.Setting;
import com.d2c.order.service.*;
import com.d2c.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/main")
public class MainCtrl extends SuperCtrl {

    protected static final Log logger = LogFactory.getLog(MainCtrl.class);
    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RefundService refundService;
    @Autowired
    private ReshipService reshipService;
    @Autowired
    private O2oSubscribeService o2oSubscribeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberShareService memberShareService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private SettingService settingService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response add(HttpServletRequest request) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        ResourceSearcher searcher = new ResourceSearcher();
        searcher.setInternal(0);
        searcher.setDepth(1);
        List<ResourceDto> first = resourceService.findBySearch(searcher);
        searcher.setDepth(2);
        List<ResourceDto> second = resourceService.findBySearch(searcher);
        String tgt = request.getHeader("accesstoken");
        AdminDto admin = adminService.findAdminByTicket(tgt);
        List<String> roleValues = new ArrayList<>();
        if (admin != null) {
            roleValues.addAll(admin.getRoleValues());
        } else {
            MemberInfo member = memberInfoService.findByToken(tgt);
            if (member == null) {
            } else if (member.getDesignerId() != null) {
                roleValues.add("ROLE_DESIGNER");
            } else if (member.getStoreId() != null) {
                roleValues.add("ROLE_STORE");
            }
        }
        List<com.d2c.member.model.Resource> menus = resourceService.findByRoles(roleValues);
        result.put("first", first);
        result.put("second", second);
        result.put("menus", menus);
        return result;
    }

    /**
     * 数据统计
     *
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public Response main() throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        String key = CacheKey.MAINCOUNTKEY;
        this.getLoginedAdmin();
        Map<String, Object> entity = null;
        try {
            entity = CacheTimerHandler.getAndSetCacheValue(key, 20, new CacheCallback<Map<String, Object>>() {
                @Override
                public Map<String, Object> doExecute() {
                    Map<String, Object> data = new HashMap<String, Object>();
                    // 统计各种状态销售单的数量
                    Map<String, Object> salesCountMap = orderQueryService.getCountsMap();
                    data.put("salesCountMap", salesCountMap);
                    // 客服退款审核数量 财务退款审核数量
                    Map<String, Object> refundCountMap = refundService.countRefundsMaps();
                    data.put("refundCountMap", refundCountMap);
                    // 客服退货审核数量 仓库退货审核数量
                    Map<String, Object> reshipsCountMap = reshipService.countReshipsMaps();
                    data.put("reshipsCountMap", reshipsCountMap);
                    // 统计各种商品数量
                    Map<String, Object> productMap = productService.countGroupByMark();
                    data.put("productMap", productMap);
                    // 统计未审核评论数量
                    Integer unVerifiedNum = commentService.findUnVerifiedCount();
                    data.put("unVerifiedNum", unVerifiedNum);
                    // 统计未审核买家秀数量
                    Integer memberShareCount = memberShareService.findUnSubmitCount();
                    data.put("memberShareCount", memberShareCount);
                    return data;
                }
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (entity != null) {
            result.put("data", entity);
        }
        return result;
    }

    /**
     * app是否强制更新
     *
     * @param appTerminal 终端
     * @param appVersion  版本号
     * @return
     */
    @RequestMapping(value = "/upgrade/plus", method = RequestMethod.GET)
    public Response getUpgrade(String appTerminal, String appVersion) {
        SuccessResponse result = new SuccessResponse();
        if (StringUtils.isEmpty(appTerminal) || StringUtils.isEmpty(appVersion)) {
            result.setStatus(-1);
            result.setMsg("获取版本号或终端不成功！");
            return result;
        }
        String type = VersionTypeEnum.boss.name();
        appTerminal = DeviceTypeEnum.divisionDevice(appTerminal);
        AppVersion lastAppVersion = appVersionService.findLastVersion(appTerminal, type, appVersion);
        // 是否启用升级这个功能
        Setting setting = new Setting();
        if (DeviceTypeEnum.APPIOS.toString().equalsIgnoreCase(appTerminal)) {
            setting = settingService.findByCode(Setting.IOSBOSS);
        } else if (DeviceTypeEnum.APPANDROID.toString().equalsIgnoreCase(appTerminal)) {
            setting = settingService.findByCode(Setting.ANDROID);
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

}
