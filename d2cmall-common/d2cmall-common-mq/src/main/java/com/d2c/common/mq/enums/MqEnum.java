package com.d2c.common.mq.enums;

import com.d2c.common.mq.QueueHandler;
import com.d2c.common.mq.TopicHandler;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import java.io.Serializable;
import java.util.Map;

import static com.d2c.common.mq.enums.MqTypeEnum.QUEUE;
import static com.d2c.common.mq.enums.MqTypeEnum.TOPIC;

public enum MqEnum {
    FLUSH_CACHE(MqTypeEnum.TOPIC, "清除缓存"), SMS_CHOICE(MqTypeEnum.TOPIC, "短信通道"),
    TIMING_PRODUCT("商品定时"), TIMING_PROMOTION("活动定时"),
    TIMING_SCREEN("闪屏定时"), TIMING_PUSH("推送定时"), TIMING_RULE("规则定时"),
    PRODUCT_SUMMARY("商品统计"), BRAND_SUMMARY("品牌统计"), REFRESH_MEMBER("刷新会员"), AWARD_QUALIFIED("抽奖资格"),
    UNLOCK_ACCOUNT("解锁钱包"), BREACH_AUCTION("拍卖违约"), PARTNER_CASH("分销提现"), PARTNER_ORDER("分销订单"),
    ORDER_REMIND("订单提醒"), FLASH_REMIND("限时购提醒"), BARGAIN_REMIND("砍价提醒"), COLLAGE_REMIND("拼团提醒");
    // **************************************************
    private ActiveMQDestination destination;
    private MqTypeEnum type;
    private MsgEnum msgType;
    private String remark;

    MqEnum(String remark) {
        this(QUEUE, remark);
    }

    MqEnum(MqTypeEnum type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    /**
     * 发送消息
     */
    public void send(final String text) {
        send(text, null);
    }

    public void send(final String text, final Long seconds) {
        switch (type) {
            case TOPIC:
                TopicHandler.sendText(getDestination(), text, seconds);
                break;
            default:
                QueueHandler.sendText(getDestination(), text, seconds);
                break;
        }
    }

    public void send(final Serializable obj) {
        send(obj, null);
    }

    public void send(final Serializable obj, final Long seconds) {
        switch (type) {
            case TOPIC:
                TopicHandler.sendObject(getDestination(), obj, seconds);
                break;
            default:
                QueueHandler.sendObject(getDestination(), obj, seconds);
                break;
        }
    }

    public void send(final Map<String, Object> map) {
        send(map, null);
    }

    public void send(final Map<String, Object> map, final Long seconds) {
        switch (type) {
            case TOPIC:
                TopicHandler.sendMap(getDestination(), map, seconds);
                break;
            default:
                QueueHandler.sendMap(getDestination(), map, seconds);
                break;
        }
    }
    // ********************************************************************************

    /**
     * 获得消息Destination
     */
    public ActiveMQDestination getDestination() {
        switch (type) {
            case TOPIC:
                destination = new ActiveMQTopic(this.name());
                break;
            case QUEUE:
                destination = new ActiveMQQueue(this.name());
                break;
            default:
                break;
        }
        return destination;
    }

    public boolean isTopic() {
        return TOPIC.equals(type);
    }
    // ***********************************************************

    public MqTypeEnum getType() {
        return type;
    }

    public String getRemark() {
        return remark;
    }

    public MsgEnum getMsgType() {
        return msgType;
    }
}
