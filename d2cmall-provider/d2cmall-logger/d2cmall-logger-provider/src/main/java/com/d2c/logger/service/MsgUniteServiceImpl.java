package com.d2c.logger.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.core.propery.AppProperties;
import com.d2c.logger.model.Message;
import com.d2c.logger.support.EmailBean;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.util.string.LoginUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;

@Service("msgUniteService")
public class MsgUniteServiceImpl implements MsgUniteService {

    private static JSONObject obj = JSON
            .parseObject("{\"time\":\"2200-2400,0000-0900\",\"without\":\"13,14,81,82,83,84,85,86,89\"}");
    @Autowired
    private SmsLogService smsLogService;
    @Autowired
    private EmailLogService emailLogService;
    @Autowired
    private MsgPushService msgPushService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AppProperties properties;

    @Async
    @Override
    public void sendMsg(SmsBean smsBean, PushBean pushBean, MsgBean msgBean, EmailBean emailBean) {
        if (msgBean != null && processRedLine(msgBean.getInMsgType())) {
            return;
        }
        if (smsBean != null) {
            this.sendSms(smsBean);
        }
        if (pushBean != null) {
            this.sendPush(pushBean);
        }
        if (msgBean != null) {
            this.sendMsg(msgBean);
        }
        // if (emailBean != null) {
        // this.sendEmail(emailBean);
        // }
    }

    @Async
    @Override
    public void sendPush(PushBean pushBean, MsgBean msgBean) {
        if (msgBean != null && processRedLine(msgBean.getInMsgType())) {
            return;
        }
        if (pushBean != null) {
            this.sendPush(pushBean);
        }
        if (msgBean != null) {
            this.sendMsg(msgBean);
        }
    }

    @Async
    @Override
    public void sendMsgBoss(SmsBean smsBean, PushBean pushBean, MsgBean msgBean, EmailBean emailBean) {
        if (msgBean != null && processRedLine(msgBean.getInMsgType())) {
            return;
        }
        if (smsBean != null) {
            this.sendSms(smsBean);
        }
        if (pushBean != null) {
            this.sendPushBoss(pushBean);
        }
        if (msgBean != null) {
            this.sendMsg(msgBean);
        }
        // if (emailBean != null) {
        // this.sendEmail(emailBean);
        // }
    }

    @Async
    @Override
    public void sendPushBoss(PushBean pushBean, MsgBean msgBean) {
        if (msgBean != null && processRedLine(msgBean.getInMsgType())) {
            return;
        }
        if (pushBean != null) {
            this.sendPushBoss(pushBean);
        }
        if (msgBean != null) {
            this.sendMsg(msgBean);
        }
    }
    // /**
    // * 发送邮件
    // *
    // * @param emailBean
    // */
    // private void sendEmail(EmailBean emailBean) {
    // if (StringUtils.isEmpty(emailBean.getEmail())) {
    // return;
    // }
    // if (LoginUtil.checkEmail(emailBean.getEmail())) {
    // emailLogService.sendEmail(emailBean.getEmail(), emailBean.getSubject(),
    // emailBean.getTemplateId(),
    // emailBean.getEmailC(), emailBean.getContent(), emailBean.getBuzId(),
    // emailBean.getEmailLogType());
    // }
    // }

    /**
     * 发送短信
     *
     * @param smsBean
     */
    private void sendSms(SmsBean smsBean) {
        if (StringUtils.isEmpty(smsBean.getMobile())) {
            return;
        }
        if (StringUtils.isEmpty(smsBean.getNationCode())) {
            smsBean.setNationCode("86");
        }
        if (LoginUtil.checkMobile(smsBean.getMobile())) {
            int success = smsLogService.insertLog(smsBean.getSmsLogType(), String.valueOf(smsBean.getBuzId()),
                    "00" + smsBean.getNationCode() + smsBean.getMobile(), smsBean.getIp(), "", smsBean.getContent());
            if (success > 0) {
                smsLogService.doSendSms(smsBean.getNationCode(), smsBean.getMobile(), smsBean.getContent());
            }
        }
    }

    /**
     * 发送站内信
     *
     * @param msgBean
     */
    private void sendMsg(MsgBean msgBean) {
        if (msgBean.getMemberId() == null) {
            return;
        }
        if (msgBean.getInMsgType() != null && msgBean.getInMsgType() > 0) {
            Message message = new Message();
            message.setGlobal(0);
            message.setStatus(0);
            message.setRecId(msgBean.getMemberId());
            message.setType(msgBean.getInMsgType());
            message.setTitle(msgBean.getSubject());
            message.setContent(msgBean.getContent());
            message.setUrl(msgBean.getAppUrl());
            message.setPic(msgBean.getPic());
            message.setOther(msgBean.getOther());
            messageService.insert(message);
        }
    }

    /**
     * 发送推送
     *
     * @param pushBean
     */
    private void sendPush(PushBean pushBean) {
        try {
            msgPushService.doMsgPushByGt(String.valueOf(pushBean.getMemberId()), pushBean.getContent(),
                    pushBean.getAppUrl(), pushBean.getTitle(), pushBean.getSubTitle(), "iphone",
                    pushBean.getInMessageType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送推送
     *
     * @param pushBean
     */
    private void sendPushBoss(PushBean pushBean) {
        try {
            msgPushService.doMsgPushBossByGt(String.valueOf(pushBean.getMemberId()), pushBean.getContent(),
                    pushBean.getAppUrl(), "", "iphone");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 红线时间不发普通消息
     *
     * @param inMsgType
     * @return
     */
    private boolean processRedLine(Integer inMsgType) {
        if (properties.getDebug()) {
            return false;
        }
        try {
            // JSON.parseObject(Setting.defaultValue(setting,
            // "{}").toString());
            if (Arrays.asList(obj.getString("without").split(",")).contains(inMsgType.toString())) {
                return false;
            }
            Integer now = Integer.parseInt(String.valueOf(LocalTime.now().getHour()) + LocalTime.now().getMinute());
            for (String timeItem : obj.getString("time").split(",")) {
                String[] times = timeItem.split("-");
                if (now >= Integer.valueOf(times[0]) && now < Integer.valueOf(times[1])) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

}
