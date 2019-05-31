package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.security.Base64Ut;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.common.core.cache.old.CacheTimerHandler;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.content.model.AdResource;
import com.d2c.content.model.Navigation;
import com.d2c.content.service.AdResourceService;
import com.d2c.content.service.NavigationService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.property.HttpProperties;
import com.d2c.logger.model.ShareLog;
import com.d2c.logger.model.Template;
import com.d2c.logger.search.model.SearcherMessage;
import com.d2c.logger.search.service.MessageSearcherService;
import com.d2c.logger.search.service.SearchKeySearcherService;
import com.d2c.logger.service.ShareLogService;
import com.d2c.logger.service.TemplateService;
import com.d2c.member.model.Member;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberLotto.LotteryOpportunityEnum;
import com.d2c.member.mongo.model.CollectCardTaskDO;
import com.d2c.member.mongo.model.CollectCardTaskDO.TaskType;
import com.d2c.member.mongo.services.CollectCardTaskService;
import com.d2c.order.model.Logistics;
import com.d2c.order.model.Logistics.BusinessType;
import com.d2c.order.model.LogisticsCompany;
import com.d2c.order.model.OrderItem;
import com.d2c.order.model.Setting;
import com.d2c.order.service.LogisticsService;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.SettingService;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.order.third.kaola.reponse.KaolaOrderStatus;
import com.d2c.order.third.kaola.reponse.Track;
import com.d2c.order.third.kaola.reponse.TrackLogistics;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.third.upyun.core.UpYun;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("")
public class HomeController extends BaseController {

    @Reference
    private SearchKeySearcherService searchKeySearcherService;
    @Autowired
    private NavigationService navigationService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private SettingService settingService;
    @Reference
    private MessageSearcherService messageSearcherService;
    @Autowired
    private LogisticsService logisticsService;
    @Autowired
    private AdResourceService adResourceService;
    @Autowired
    private ShareLogService shareLogService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CollectCardTaskService collectCardTaskService;

    /**
     * 可用性测试
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public SuccessResponse version() {
        SuccessResponse result = new SuccessResponse();
        String version = VERSION;
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
     * 404
     *
     * @return
     */
    @RequestMapping(value = "/404", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String show404() {
        return "error/404";
    }

    /**
     * 500
     *
     * @return
     */
    @RequestMapping(value = "/500", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public String show500() {
        return "error/500";
    }

    /**
     * 清除缓存
     *
     * @param key
     */
    @RequestMapping(value = "/clear/cache", method = RequestMethod.GET)
    public void clearCache(String key) {
        if (key == null) {
            return;
        }
        if (StringUtils.isNotBlank(key)) {
            for (String k : key.split(",")) {
                CacheTimerHandler.removeCache(k);
            }
        }
    }

    /**
     * 图片上传
     */
    @ResponseBody
    @RequestMapping(value = "/pic/upload/{policy}", method = RequestMethod.GET)
    public Response upload(@PathVariable String policy) {
        SuccessResponse result = new SuccessResponse();
        String sign = policy + "&" + UpYun.FORM_API_SECRET_D2C_PIC;
        result.put("sign", MD5Util.encodeMD5Hex(sign));
        return result;
    }

    /**
     * app到wap请求跳转
     *
     * @param url
     * @param token
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/appToWap", method = RequestMethod.GET)
    public String appToWap(ModelMap model, String url, String token, HttpServletResponse response) throws Exception {
        // 模拟登陆
        if (StringUtils.isNotBlank(token)) {
            Cookie tgc = new Cookie(Member.CASTGC, token);
            writeCookie(tgc, response);
        } else {
            removeCookie(Member.CASTGC, response);
        }
        // 标记请求
        Cookie uac = new Cookie(HttpProperties.USERAGENT, "1");
        writeCookie(uac, response);
        // 重定向地址
        return redirectPath(model, url);
    }

    private String redirectPath(ModelMap model, String url) {
        url = url.replaceAll("&amp;", "&");
        model.put("invoked", 1);
        int index = url.indexOf("?");
        if (index == -1) {
            return "redirect:" + url;
        } else {
            String path = url.substring(0, index);
            String param = url.substring(index + 1);
            this.splitParam(model, param);
            return "redirect:" + path;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void splitParam(Map map, String param) {
        if (StringUtil.isNotBlank(param)) {
            String[] params = param.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] p = params[i].split("=");
                if (p.length == 2) {
                    map.put(p[0], p[1]);
                }
            }
        }
    }

    /**
     * 导航搜索
     */
    @RequestMapping(value = "c/{code}", method = RequestMethod.GET)
    public String navigation(@PathVariable String code, ModelMap model) {
        Navigation navigation = navigationService.findNavigationByCode(code);
        if (navigation != null) {
            // 导航菜单
            model.put("nav", navigation);
            if (navigation.getParentId() == null) {
                Navigation navParent = navigationService.findById(navigation.getParentId());
                model.put("navParent", navParent == null ? navParent : navParent);
            }
            if (!StringUtils.isEmpty(navigation.getUrl())) {
                return "redirect:" + navigation.getUrl();
            }
        }
        return "redirect:/index";
    }

    /**
     * wap的导航菜单栏
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/navigation", method = RequestMethod.GET)
    public String renderNavigation(ModelMap model) {
        return "fragment/navigation";
    }

    /**
     * 站内信
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    public String message(ModelMap model, @PathVariable Long id) {
        SearcherMessage searcherMessage = messageSearcherService.findById(String.valueOf(id));
        model.put("message", searcherMessage);
        return "society/system_notification";
    }

    /**
     * 物流查询
     *
     * @param sn
     * @param com
     * @param orderItemId
     * @param model
     * @param productImg
     * @param ordersn
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logistics/info", method = RequestMethod.GET)
    public String logistics(String sn, String com, Long orderItemId, ModelMap model, String productImg, String ordersn)
            throws Exception {
        String deliveryCorpName = com;
        LogisticsCompany logisticsCompany = logisticsService.findCompanyByName(com);
        com = (logisticsCompany != null ? logisticsCompany.getCode() : "other");
        Logistics logistics = new Logistics();
        if (orderItemId != null) {
            // 传了orderItemId情形
            OrderItem item = orderItemService.findById(orderItemId);
            if (item == null) {
                throw new BusinessException("该笔订单明细不存在！");
            }
            if (item.getProductSource().equals(ProductSource.KAOLA.name())) {
                // 考拉订单的物流
                KaolaOrderStatus kaolaOrderStatus = KaolaClient.getInstance()
                        .queryOrderStatus(item.getOrderSn() + "-" + item.getWarehouseId());
                for (List<TrackLogistics> list : kaolaOrderStatus.getTrackLogisticss().values()) {
                    if (list.get(0) != null) {
                        TrackLogistics trackLogistics = list.get(0);
                        logistics.setDeliverySn(trackLogistics.getBillno());
                        logistics.setDeliveryCorpName(item.getDeliveryCorpName());
                        logistics.setStatus(trackLogistics.getState());
                        logistics.setType(BusinessType.ORDER.name());
                        JSONArray array = new JSONArray();
                        for (Track track : trackLogistics.getTracks()) {
                            JSONObject obj = new JSONObject();
                            obj.put("context", track.getContext());
                            obj.put("ftime", track.getTimeDetail());
                            obj.put("time", track.getTime());
                            array.add(obj);
                        }
                        logistics.setDeliveryInfo(array.toJSONString());
                        break;
                    }
                }
            } else {
                // 一般订单的物流
                logistics = logisticsService.findBySnAndCom(sn, com, null);
                if (logistics != null) {
                    logistics.setDeliveryCorpName(deliveryCorpName);
                }
            }
        } else {
            // 没传orderItemId情形
            logistics = logisticsService.findBySnAndCom(sn, com, null);
            if (logistics != null) {
                logistics.setDeliveryCorpName(deliveryCorpName);
            }
        }
        JSONObject json = new JSONObject();
        if (logistics != null) {
            json = (JSONObject) JSON.toJSON(logistics);
            if (!StringUtils.isEmpty(logistics.getDeliveryInfo())) {
                json.put("deliveryInfo", JSON.parse(logistics.getDeliveryInfo(), Feature.OrderedField).toString());
            }
        } else {
            logistics = new Logistics();
            logistics.setStatus(-1);
            logistics.setDeliverySn(sn);
            logistics.setDeliveryCode(com);
            json = (JSONObject) JSON.toJSON(logistics);
        }
        model.put("logistics", json);
        model.put("ordersn", ordersn);
        model.put("productImg", productImg);
        return "order/logistics_detail";
    }

    /**
     * 广告位展示
     *
     * @param appChannel
     * @param type
     * @param model
     * @return
     */
    @RequestMapping(value = "/ad/{appChannel}/{type}", method = RequestMethod.GET)
    public String getType(@PathVariable String appChannel, @PathVariable String type, ModelMap model) {
        AdResource adResource = adResourceService.findByAppChannelAndType(appChannel, type);
        model.put("adResource", adResource);
        return "";
    }

    /**
     * 通过APP分享链接进入
     *
     * @param param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/share/in", method = RequestMethod.GET)
    public String shareIn(ModelMap model, String param) throws Exception {
        param = Base64Ut.decode(param);
        if (StringUtils.isNotBlank(param)) {
            Map<String, String> map = new HashMap<>();
            this.splitParam(map, param);
            // 分享日志
            ShareLog shareLog = new ShareLog();
            String url = URLDecoder.decode(String.valueOf(map.get("url")), "UTF-8");
            shareLog.setUrl(url);
            shareLog.setDirection(-1);
            shareLog.setDevice(String.valueOf(map.get("device")));
            shareLog.setChannel(String.valueOf(map.get("channel")));
            if (StringUtils.isNumeric(map.get("memberId"))) {
                shareLog.setMemberId(Long.parseLong(map.get("memberId")));
            }
            shareLogService.insert(shareLog);
            // 分销商标识
            Object parentId = map.get("parent_id");
            if (parentId != null) {
                model.put("parent_id", parentId);
            }
            return "redirect:" + url;
        } else {
            throw new BusinessException("您访问的页面不存在！");
        }
    }

    /**
     * 用户分享日志
     *
     * @param request
     */
    @RequestMapping(value = "/share/out", method = RequestMethod.POST)
    public String shareOut(ModelMap model, String url) {
        ResponseResult result = new ResponseResult();
        model.put("result", result);
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            // 增加抽奖次数
            Map<String, Object> awardMap = new HashMap<>();
            awardMap.put("memberId", memberInfo.getId());
            awardMap.put("lotteryOpportunityEnum", LotteryOpportunityEnum.SHAREACTIVITY.name());
            MqEnum.AWARD_QUALIFIED.send(awardMap);
            // 收集卡片的任务
            if (url != null) {
                if (url.contains("/collection/card/home")) {
                    collectCardTaskService.insert(new CollectCardTaskDO(memberInfo.getId(), TaskType.SHAREACTIVITY));
                }
                if (url.contains("/page/520zhounianqing")) {
                    collectCardTaskService.insert(new CollectCardTaskDO(memberInfo.getId(), TaskType.SHAREACTIVITY));
                }
            }
        } catch (NotLoginException e) {
        }
        return "";
    }

}
