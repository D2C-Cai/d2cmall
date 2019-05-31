package com.d2c.logger.third.push;

import com.d2c.logger.third.push.common.GtPushUtil;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GtPushClient {

    private final static Logger logger = LoggerFactory.getLogger(GtPushClient.class);
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     * 单个推送
     *
     * @param clientId
     * @param msg
     * @param apn
     */
    public static void pushMessageToSingle(String clientId, String msg, APNPayload apn) {
        logger.debug("msg={},clientId={}", msg, clientId);
        TransmissionTemplate template = getTransmissionTemplate(msg, 2);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        if (apn != null) {
            template.setAPNInfo(apn);
        }
        // 离线有效时间，单位为毫秒，可选
        // message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(GtPushUtil.appId);
        // target.setClientId(clientId);
        target.setAlias(clientId);
        IPushResult ret = null;
        try {
            ret = GtPushUtil.getInstance().pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = GtPushUtil.getInstance().pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            logger.debug(ret.getResponse().toString());
        } else {
            logger.debug("服务器响应异常");
        }
    }

    /**
     * 群推
     *
     * @param msg
     * @param transmissionType
     * @param offlineExpireTime
     * @param apn
     */
    public static void pushTransmissionMessageToApp(String msg, int transmissionType, long offlineExpireTime,
                                                    APNPayload apn) {
        TransmissionTemplate template = getTransmissionTemplate(msg, transmissionType);
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setOffline(true);
        if (offlineExpireTime > 0) {
            // 离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(offlineExpireTime * 1000);
        }
        if (apn != null) {
            template.setAPNInfo(apn);
        }
        // 推送给App的目标用户需要满足的条件
        AppConditions cdt = new AppConditions();
        List<String> appIdList = new ArrayList<>();
        appIdList.add(GtPushUtil.appId);
        message.setAppIdList(appIdList);
        // 手机类型
        List<String> phoneTypeList = new ArrayList<>();
        // 省份
        List<String> provinceList = new ArrayList<>();
        // 自定义tag
        List<String> tagList = new ArrayList<>();
        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        cdt.addCondition(AppConditions.REGION, provinceList);
        cdt.addCondition(AppConditions.TAG, tagList);
        message.setConditions(cdt);
        IPushResult ret = GtPushUtil.getInstance().pushMessageToApp(message, "Tran_toApp");
        logger.info("result={}", ret.getResponse().toString());
    }

    /**
     * 组推
     *
     * @param msg
     * @param transmissionType
     * @param offlineExpireTime
     * @param apn
     */
    public static void pushTransmissionMessageToList(List<String> clientIds, String msg, int transmissionType,
                                                     long offlineExpireTime, APNPayload apn) {
        String appId = GtPushUtil.appId;
        // 配置返回每个用户返回用户状态，可选
        System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
        // 通知透传模板
        TransmissionTemplate template = getTransmissionTemplate(msg, transmissionType);
        // AppMessage message = new AppMessage();
        ListMessage message = new ListMessage();
        message.setData(template);
        message.setOffline(true);
        if (offlineExpireTime > 0) {
            // 离线有效时间，单位为毫秒，可选
            message.setOfflineExpireTime(offlineExpireTime * 1000);
        }
        if (apn != null) {
            template.setAPNInfo(apn);
        }
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
        List<Target> targets = new ArrayList<>();
        for (String clientId : clientIds) {
            Target target = new Target();
            target.setAppId(appId);
            target.setClientId(clientId);
            targets.add(target);
        }
        // taskId用于在推送时去查找对应的message
        String taskId = GtPushUtil.getInstance().getContentId(message);
        IPushResult ret = GtPushUtil.getInstance().pushMessageToList(taskId, targets);
        logger.debug("result={}", ret.getResponse().toString());
    }

    private static TransmissionTemplate getTransmissionTemplate(String msg, int transmissionType) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(GtPushUtil.appId);
        template.setAppkey(GtPushUtil.appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(transmissionType);
        template.setTransmissionContent(msg);
        // 展示时间
        // template.setDuration(begin, end);
        return template;
    }

}
