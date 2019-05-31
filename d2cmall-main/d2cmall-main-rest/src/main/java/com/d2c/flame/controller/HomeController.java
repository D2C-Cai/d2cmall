package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.utils.security.Base64Ut;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.ShareLog;
import com.d2c.logger.model.Template;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.logger.service.ShareLogService;
import com.d2c.logger.service.TemplateService;
import com.d2c.member.model.MemberLotto.LotteryOpportunityEnum;
import com.d2c.member.mongo.model.CollectCardTaskDO;
import com.d2c.member.mongo.model.CollectCardTaskDO.TaskType;
import com.d2c.member.mongo.services.CollectCardTaskService;
import com.d2c.order.model.Setting;
import com.d2c.order.service.SettingService;
import com.d2c.product.third.upyun.core.UpYun;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "/v3/api/home")
public class HomeController extends BaseController {

    @Autowired
    private ShareLogService shareLogService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private CollectCardTaskService collectCardTaskService;
    @Reference
    private SearchKeySearcherService searchKeySearcherService;

    /**
     * 可用性测试
     *
     * @return
     */
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public ResponseResult version() {
        ResponseResult result = new ResponseResult();
        String version = "20170504";
        Setting setting = settingService.findByCode(Setting.STOCKSYNC);
        Template template = templateService.findById(1L);
        Set<String> keys = searchKeySearcherService.search("张");
        result.put("shop_db", setting == null ? false : true);
        result.put("log_db", template == null ? false : true);
        result.put("search_connect", keys == null ? false : true);
        result.put("version", version);
        return result;
    }

    /**
     * 用户分享日志
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/share/out", method = RequestMethod.GET)
    public ResponseResult shareOut(HttpServletRequest request) throws Exception {
        ResponseResult result = new ResponseResult();
        String param = Base64Ut.decode(request.getParameter("param"));
        if (StringUtils.isNotBlank(param)) {
            Map<String, String> map = spitParam(param);
            // 分享日志
            ShareLog shareLog = new ShareLog();
            String url = String.valueOf(map.get("url"));
            shareLog.setUrl(url);
            shareLog.setDirection(1);
            shareLog.setDevice(String.valueOf(map.get("device")));
            shareLog.setChannel(String.valueOf(map.get("channel")));
            if (StringUtils.isNumeric(map.get("memberId"))) {
                shareLog.setMemberId(Long.parseLong(map.get("memberId")));
            }
            if ("ios".equals(shareLog.getDevice()) && url.contains("param")) {
                String decodeUrl = url.substring(url.indexOf("param=") + 6, url.length());
                decodeUrl = Base64Ut.decode(decodeUrl);
                Map<String, String> decodeMap = spitParam(decodeUrl);
                url = URLDecoder.decode(String.valueOf(decodeMap.get("url")), "UTF-8");
                shareLog.setUrl(url);
            }
            shareLogService.insert(shareLog);
            if (shareLog.getMemberId() != null && shareLog.getMemberId() > 0) {
                // 增加抽奖次数
                Map<String, Object> awardMap = new HashMap<>();
                awardMap.put("memberId", shareLog.getMemberId());
                awardMap.put("lotteryOpportunityEnum", LotteryOpportunityEnum.SHAREACTIVITY.name());
                MqEnum.AWARD_QUALIFIED.send(awardMap);
                // 收集卡片的任务
                if (url != null) {
                    if (url.contains("/collection/card/home") || url.contains("/collection/card/share/detail")) {
                        collectCardTaskService
                                .insert(new CollectCardTaskDO(shareLog.getMemberId(), TaskType.SHAREACTIVITY));
                    }
                    if (url.contains("/page/520zhounianqing")) {
                        collectCardTaskService.insert(new CollectCardTaskDO(shareLog.getMemberId(), TaskType.SHARE520));
                    }
                }
            }
        }
        return result;
    }

    private Map<String, String> spitParam(String param) {
        Map<String, String> map = new HashMap<String, String>();
        String[] params = param.split("&");
        for (String keyvalue : params) {
            String[] pair = keyvalue.split("=");
            if (pair.length == 2) {
                map.put(pair[0], pair[1]);
            }
            if (pair.length > 2) {
                String value = "";
                for (int i = 1; i < pair.length; i++) {
                    value += "=" + pair[i];
                }
                map.put(pair[0], value.substring(1, value.length()));
            }
        }
        return map;
    }

    /**
     * 图片上传
     *
     * @param policy
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/picture/upload/{policy}", method = RequestMethod.GET)
    public ResponseResult pictureUpload(@PathVariable String policy) throws Exception {
        ResponseResult result = new ResponseResult();
        String sign = policy + "&" + UpYun.FORM_API_SECRET_D2C_PIC;
        result.put("sign", MD5Util.encodeMD5Hex(sign));
        return result;
    }

    /**
     * 视频上传
     *
     * @param policy
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/vedio/upload/{policy}", method = RequestMethod.GET)
    public ResponseResult vedioUpload(@PathVariable String policy) throws Exception {
        ResponseResult result = new ResponseResult();
        String sign = policy + "&" + UpYun.FORM_API_SECRET_D2C_VEDIO;
        result.put("sign", MD5Util.encodeMD5Hex(sign));
        return result;
    }

}
