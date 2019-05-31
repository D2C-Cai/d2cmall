package com.d2c.jms.listener;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.common.mq.listener.AbsMqListener;
import com.d2c.logger.model.MessageDef;
import com.d2c.logger.model.MessageDef.MessageChannel;
import com.d2c.logger.service.MessageDefService;
import com.d2c.logger.service.MessageService;
import com.d2c.logger.service.MsgPushService;
import com.d2c.logger.service.WeixinPushService;
import com.d2c.logger.support.MsgResource;
import com.d2c.logger.third.wechat.WeixinPushEntity;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.Date;

@Component
public class MessagePushQueueListener extends AbsMqListener {

    @Autowired
    private MsgPushService msgPushService;
    @Autowired
    private MessageDefService messageDefService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private WeixinPushService weixinPushService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            Object object1 = mapMessage.getObject("idStr");
            String idStr = String.valueOf(object1);
            Object object2 = mapMessage.getObject("creator");
            String creator = String.valueOf(object2);
            Object object3 = mapMessage.getObject("defId");
            Long defId = Long.parseLong(String.valueOf(object3));
            Object object4 = mapMessage.getObject("timestamp");
            Long timestamp = Long.parseLong(String.valueOf(object4));
            Object object5 = mapMessage.getObject("openIds");
            String[] openIds = null;
            if (object5 != null) {
                openIds = StringUtil.strToArray(String.valueOf(object5));
            }
            this.pushMessage(idStr, defId, creator, timestamp, openIds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void pushMessage(String idStr, Long defId, String creator, Long timestamp, String[] openIds) {
        MessageDef messageDef = messageDefService.findById(defId);
        if (!MessageChannel.WEIXINTOPARTNER.name().equals(messageDef.getChannel())) {
            if (messageDef == null || messageDef.getStatus() == -1 || messageDef.getStatus() == 1
                    || timestamp.longValue() != messageDef.getTimestamp()) {
                return;
            }
            com.d2c.logger.model.Message message = new com.d2c.logger.model.Message();
            BeanUtils.copyProperties(messageDef, message, "id");
            message.setRecId(0L);
            message.setCreator(creator);
            if (messageDef.getGlobal() == 1) {
                // 全站消息
                message.setGlobal(1);
                messageService.insert(message);
                this.pushTransmissionMsgToApp(messageDef.getUrl(), messageDef.getTitle(),
                        MsgResource.getTypeName(messageDef.getType()), messageDef.getContent(), "iphone/android",
                        messageDef.getType());
            } else {
                // 用户消息
                message.setGlobal(0);
                if (StringUtil.isNotBlank(idStr)) {
                    messageService.doBatchInsert(message, StringUtil.strToLongArray(idStr));
                }
            }
        }
        // 小程序推送
        if (!MessageChannel.APP.name().equals(messageDef.getChannel()) && openIds != null) {
            processMessageToPartnerWexin(messageDef, openIds);
        }
        messageDefService.updateStatusById(messageDef.getId(), 1);
    }

    private void pushTransmissionMsgToApp(String url, String title, String subTitle, String content, String deviceType,
                                          Integer inMessageType) {
        JSONObject msgContent = new JSONObject();
        msgContent.put("url", url);
        msgContent.put("title", title);
        msgContent.put("subTitle", subTitle);
        msgContent.put("msgContent", content);
        msgContent.put("type", inMessageType);
        boolean isApnPayLoad = false;
        if (deviceType.contains("iphone")) {
            isApnPayLoad = true;
        }
        try {
            msgPushService.doPushTransmissionMsgToApp(msgContent, 2, 0, isApnPayLoad, "REMIND", deviceType);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
    }

    private void processMessageToPartnerWexin(MessageDef def, String[] openIds) {
        String pagepath = null;
        String url = def.getUrl();
        String remark = def.getUrl() == null ? null : "点击查看详细";
        if (def.getToMiniProgram() != null && def.getToMiniProgram() == 1) {
            pagepath = def.getUrl();
            url = null;
        }
        for (String openId : openIds) {
            weixinPushService.send(new WeixinPushEntity(openId, def.getTitle(), def.getContent(), "全体成员", new Date(),
                    remark, pagepath, url));
        }
    }

    @Override
    public MqEnum getMqEnum() {
        return MqEnum.TIMING_PUSH;
    }

}
