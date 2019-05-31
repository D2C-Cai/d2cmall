package com.d2c.logger.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.model.Signature;
import com.d2c.logger.third.wechat.WeixinPushEntity;
import com.d2c.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("weixinPushService")
public class WeixinPushServiceImpl implements WeixinPushService {

    private static RestTemplate restTemplate = new RestTemplate();
    private static String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    @Autowired
    private SignatureService signatureService;
    private String appId = "wxd53cb8e070c61c72";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void send(WeixinPushEntity msgEntity) {
        Signature signature = signatureService.findByAppid(appId);
        JSONObject obj = new JSONObject();
        obj.put("touser", msgEntity.getOpenId());
        obj.put("template_id", msgEntity.getTemplateId());
        obj.put("url", msgEntity.getUrl());
        obj.put("miniprogram", msgEntity.getMiniprogram());
        obj.put("data", msgEntity.getObj());
        try {
            if (!StringUtil.isBlank(msgEntity.getOpenId())) {
                restTemplate.postForEntity(sendUrl + signature.getToken(), obj, JSONObject.class);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    // public static void main(String[] args) {
    // WeixinPushEntity msgEntity = new
    // WeixinPushEntity("oC3Prw3hFlYW02JV2YbFomG5D7AU", "提现单号:" + "123" + "
    // 已提现成功",
    // new Date(), new BigDecimal(10), "提现成功", "支付流水号：" + "456",
    // "/pages/member/withdrawal/list");
    // JSONObject obj = new JSONObject();
    // obj.put("touser", msgEntity.getOpenId());
    // obj.put("template_id", msgEntity.getTemplateId());
    // obj.put("url", msgEntity.getUrl());
    // obj.put("miniprogram", msgEntity.getMiniprogram());
    // obj.put("data", msgEntity.getObj());
    // try {
    // if (!StringUtil.isBlank(msgEntity.getOpenId())) {
    // ResponseEntity<JSONObject> json = restTemplate.postForEntity(
    // sendUrl +
    // "7_Xke-3lHJtjOzLr4BkhZ-D2m4TrymB4LtDj3ZilaERkanzVhohXc9WJtuXiHWIu50PHvNwiYZ-33NMLwHWCoD6aJ7fk3A1eg7PKZrCPhZSHYjYiNRN2OuPWK-6KwqIxm8fCVv7NiCV2RRidRTLTSeAEAAIH",
    // obj, JSONObject.class);
    // System.out.println(json);
    // }
    // } catch (Exception e) {
    //
    // }
    // }
}
