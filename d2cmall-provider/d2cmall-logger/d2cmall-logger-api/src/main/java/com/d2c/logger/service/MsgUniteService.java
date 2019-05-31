package com.d2c.logger.service;

import com.d2c.logger.support.EmailBean;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;

public interface MsgUniteService {

    /**
     * 发送消息
     *
     * @param smsBean
     * @param pushBean
     * @param msgBean
     * @param emailBean
     */
    void sendMsg(SmsBean smsBean, PushBean pushBean, MsgBean msgBean, EmailBean emailBean);

    /**
     * 发送推送
     *
     * @param pushBean
     * @param msgBean
     */
    void sendPush(PushBean pushBean, MsgBean msgBean);

    /**
     * 发送消息
     *
     * @param smsBean
     * @param pushBean
     * @param msgBean
     * @param emailBean
     */
    void sendMsgBoss(SmsBean smsBean, PushBean pushBean, MsgBean msgBean, EmailBean emailBean);

    /**
     * 发送推送
     *
     * @param pushBean
     * @param msgBean
     */
    void sendPushBoss(PushBean pushBean, MsgBean msgBean);

}
