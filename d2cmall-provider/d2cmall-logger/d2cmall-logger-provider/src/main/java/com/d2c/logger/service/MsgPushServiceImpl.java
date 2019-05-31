package com.d2c.logger.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.third.push.GtPushBossClient;
import com.d2c.logger.third.push.GtPushClient;
import com.d2c.util.date.DateUtil;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.APNPayload.SimpleAlertMsg;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("msgPushService")
public class MsgPushServiceImpl implements MsgPushService {

    public static void main(String[] args) {
        JSONObject msgContent = new JSONObject();
        msgContent.put("url", "/rest/order/requisitionitem/list?status=1&type=124");
        msgContent.put("title", "调拨单提醒");
        msgContent.put("msgContent", "亲爱的" + "xxx" + "品牌主理人，截止至"
                + DateUtil.convertDate2Str(new Date(), "yyyy年MM月dd日 HH:mm") + "您还有" + 2 + "条未处理的调拨单，为了防止超期赔偿，请及时处理。");
        APNPayload apn = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", SmsLogType.REMIND.name());
        jsonObject.put("messageContent", msgContent);
        apn = new APNPayload();
        apn.setSound("default");
        apn.addCustomMsg("url", msgContent.get("url"));
        apn.setAutoBadge("+1");
        SimpleAlertMsg simpleAlertMsg = new SimpleAlertMsg(msgContent.getString("title"));
        apn.setAlertMsg(simpleAlertMsg);
        // GtPushClient.pushTransmissionMessageToApp(jsonObject.toJSONString(),
        // 2, 0, apn);
        GtPushBossClient.pushMessageToSingle("1b741ae09b209ddbc403e80cf7f8d7dd", msgContent.toJSONString(), apn);
    }

    @Override
    public void doMsgPushByGt(String clientId, String msg, String url, String title, String subTitle, String deviceType,
                              Integer inMessageType) {
        APNPayload apn = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", "REMIND");
        JSONObject msgContent = new JSONObject();
        msgContent.put("msgContent", msg);
        msgContent.put("url", url);
        msgContent.put("title", title);
        msgContent.put("type", inMessageType);
        msgContent.put("subTitle", subTitle);
        jsonObject.put("messageContent", msgContent);
        if (deviceType.contains("iphone")) {
            apn = new APNPayload();
            apn.setSound("default");
            apn.addCustomMsg("url", url);
            apn.setAutoBadge("+1");
            SimpleAlertMsg simpleAlertMsg = new SimpleAlertMsg(msg);
            apn.setAlertMsg(simpleAlertMsg);
            apn.setBadge(1);
        }
        GtPushClient.pushMessageToSingle(clientId, jsonObject.toJSONString(), apn);
    }

    @Override
    public void doMsgPushBossByGt(String clientId, String msg, String url, String title, String deviceType) {
        APNPayload apn = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", "REMIND");
        JSONObject msgContent = new JSONObject();
        msgContent.put("msgContent", msg);
        msgContent.put("url", url);
        jsonObject.put("messageContent", msgContent);
        if (deviceType.contains("iphone")) {
            apn = new APNPayload();
            apn.setSound("default");
            apn.addCustomMsg("url", url);
            apn.setAutoBadge("+1");
            SimpleAlertMsg simpleAlertMsg = new SimpleAlertMsg(msg);
            apn.setAlertMsg(simpleAlertMsg);
        }
        GtPushBossClient.pushMessageToSingle(clientId, jsonObject.toJSONString(), apn);
    }

    @Override
    public void doPushTransmissionMsgToApp(JSONObject messageContent, int transmissionType, long offlineExpireTime,
                                           boolean isApnPayLoad, String msgType, String deviceType) {
        APNPayload apn = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", msgType);
        jsonObject.put("messageContent", messageContent);
        if (isApnPayLoad && deviceType.contains("iphone")) {
            apn = new APNPayload();
            apn.setSound("default");
            apn.addCustomMsg("url", messageContent.get("url"));
            SimpleAlertMsg simpleAlertMsg = new SimpleAlertMsg(messageContent.getString("title"));
            apn.setAlertMsg(simpleAlertMsg);
        }
        GtPushClient.pushTransmissionMessageToApp(jsonObject.toJSONString(), transmissionType, offlineExpireTime, apn);
    }

    @Override
    public void doPushTransmissionMsgToList(List<String> clientIds, JSONObject messageContent, int transmissionType,
                                            long offlineExpireTime, boolean isApnPayLoad, String msgType, String deviceType) {
        APNPayload apn = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageType", msgType);
        jsonObject.put("messageContent", messageContent);
        if (isApnPayLoad && deviceType.contains("iphone")) {
            apn = new APNPayload();
            apn.setSound("default");
            apn.addCustomMsg("url", messageContent.get("url"));
            SimpleAlertMsg simpleAlertMsg = new SimpleAlertMsg(messageContent.getString("title"));
            apn.setAlertMsg(simpleAlertMsg);
        }
        GtPushClient.pushTransmissionMessageToList(clientIds, jsonObject.toJSONString(), transmissionType,
                offlineExpireTime, apn);
    }

}
