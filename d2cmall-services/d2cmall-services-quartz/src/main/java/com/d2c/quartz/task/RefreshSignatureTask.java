package com.d2c.quartz.task;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.quartz.task.base.BaseTask;
import com.d2c.quartz.task.config.WxAppConfig;
import com.d2c.quartz.task.config.WxXcxConfig;
import com.d2c.quartz.task.properties.QuartzProperties;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RefreshSignatureTask extends BaseTask {

    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private SignatureService signatureService;
    @Autowired
    private QuartzProperties props;

    public static void main(String[] args) {
        // 微信公众号JS-SDK使用权限签名
        Signature signature = new Signature();// 正式
        signature.setAppid("wxd53cb8e070c61c72");
        signature.setAppsecret("ef9e5243659177e943ef86d3a9a31643");
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + signature.getAppid() + "&secret=" + signature.getAppsecret();
        signature.setToken(restTemplate.getForObject(tokenUrl, JSONObject.class).getString("access_token"));
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + signature.getToken()
                + "&type=jsapi";
        signature.setTicket(restTemplate.getForObject(ticketUrl, JSONObject.class).getString("ticket"));
        System.out.println(JSONObject.toJSON(signature));
    }

    @Scheduled(fixedDelay = 60 * 1000 * 10)
    public void execute() {
        if (properties.getDebug()) {
            return;
        }
        super.exec();
    }

    @Override
    public void execImpl() {
        if (props.getMsg() == 1) {
            this.process();
            this.processXcx1();
            this.processXcx2();
            this.processXcx3();
        }
    }

    private void process() {
        // 微信公众号JS-SDK使用权限签名
        Signature signature = signatureService.findByAppid(WxAppConfig.appId);// 正式
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + signature.getAppid() + "&secret=" + signature.getAppsecret();
        JSONObject result = restTemplate.getForObject(tokenUrl, JSONObject.class);
        String token = result.getString("access_token");
        if (StringUtil.isBlank(token)) {
            logger.error("token获取失败，方法名称:process()，" + result.toJSONString());
        }
        signature.setToken(token);
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + signature.getToken()
                + "&type=jsapi";
        signature.setTicket(restTemplate.getForObject(ticketUrl, JSONObject.class).getString("ticket"));
        signatureService.update(signature);
    }

    private void processXcx1() {
        // 微信公众号JS-SDK使用权限签名
        Signature signature = signatureService.findByAppid(WxXcxConfig.appId1);// 正式
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + signature.getAppid() + "&secret=" + signature.getAppsecret();
        JSONObject result = restTemplate.getForObject(tokenUrl, JSONObject.class);
        String token = result.getString("access_token");
        if (StringUtil.isBlank(token)) {
            logger.error("token获取失败，方法名称:processXcx1()，" + result.toJSONString());
        }
        signature.setToken(token);
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + signature.getToken()
                + "&type=jsapi";
        signature.setTicket(restTemplate.getForObject(ticketUrl, JSONObject.class).getString("ticket"));
        signatureService.update(signature);
    }

    private void processXcx2() {
        // 微信公众号JS-SDK使用权限签名
        Signature signature = signatureService.findByAppid(WxXcxConfig.appId2);// 正式
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + signature.getAppid() + "&secret=" + signature.getAppsecret();
        JSONObject result = restTemplate.getForObject(tokenUrl, JSONObject.class);
        String token = result.getString("access_token");
        if (StringUtil.isBlank(token)) {
            logger.error("token获取失败，方法名称:processXcx2()，" + result.toJSONString());
        }
        signature.setToken(token);
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + signature.getToken()
                + "&type=jsapi";
        signature.setTicket(restTemplate.getForObject(ticketUrl, JSONObject.class).getString("ticket"));
        signatureService.update(signature);
    }

    private void processXcx3() {
        // 微信公众号JS-SDK使用权限签名
        Signature signature = signatureService.findByAppid(WxXcxConfig.appId3);// 正式
        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + signature.getAppid() + "&secret=" + signature.getAppsecret();
        JSONObject result = restTemplate.getForObject(tokenUrl, JSONObject.class);
        String token = result.getString("access_token");
        if (StringUtil.isBlank(token)) {
            logger.error("token获取失败，方法名称:processXcx3()，" + result.toJSONString());
        }
        signature.setToken(token);
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + signature.getToken()
                + "&type=jsapi";
        signature.setTicket(restTemplate.getForObject(ticketUrl, JSONObject.class).getString("ticket"));
        signatureService.update(signature);
    }

}
