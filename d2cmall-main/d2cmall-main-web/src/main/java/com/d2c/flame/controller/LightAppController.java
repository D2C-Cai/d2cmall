package com.d2c.flame.controller;

import com.d2c.content.model.ShareTask;
import com.d2c.content.service.ShareTaskService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.member.third.oauth.WeixinGzOauthClient;
import com.d2c.order.third.payment.alipay.sgin.BASE64;
import com.d2c.order.third.payment.wxpay.sign.WeixinSign;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Controller
@RequestMapping("")
public class LightAppController extends BaseController {

    @Autowired
    private SignatureService signatureService;
    @Autowired
    private ShareTaskService shareTaskService;
    @Autowired
    private WeixinGzOauthClient weixinGzOauthClient;

    /**
     * 轻APP相关
     *
     * @param request
     * @param response
     * @param path
     */
    @RequestMapping(value = "/light", method = RequestMethod.GET)
    public @ResponseBody
    void light(HttpServletResponse response, String path) {
        HttpServletRequest request = getRequest();
        String url = request.getRequestURL().toString();
        String urlParam = request.getQueryString();
        if (StringUtils.isNotBlank(urlParam)) {
            url = url + "?" + urlParam;
        }
        Map<String, String[]> params = request.getParameterMap();
        writeLightApp(response, path, url, params, "", "", "");
    }

    /**
     * 轻APP相关
     *
     * @param request
     * @param response
     * @param base64
     */
    @RequestMapping(value = {"/light2/{base64}", "/light/{base64}"}, method = RequestMethod.GET)
    public @ResponseBody
    void lightApp(HttpServletResponse response, @PathVariable String base64) {
        HttpServletRequest request = getRequest();
        String url = request.getRequestURL().toString();
        String urlParam = request.getQueryString();
        if (StringUtils.isNotBlank(urlParam)) {
            url = url + "?" + urlParam;
        }
        Map<String, String[]> params = request.getParameterMap();
        @SuppressWarnings("static-access")
        String path = new String(new BASE64().decode(base64));
        writeLightApp(response, path, url, params, "", "", "");
    }

    /**
     * 轻APP相关
     *
     * @param request
     * @param response
     * @param base64
     * @param defId
     * @param shareTaskId
     */
    @RequestMapping(value = "/light2/{base64}/{defId}/{shareTaskId}", method = RequestMethod.GET)
    public @ResponseBody
    void light2App(HttpServletResponse response, @PathVariable("base64") String base64,
                   @PathVariable("defId") String defId, @PathVariable("shareTaskId") Long shareTaskId) {
        HttpServletRequest request = getRequest();
        String url = request.getRequestURL().toString();
        String urlParam = request.getQueryString();
        if (StringUtils.isNotBlank(urlParam)) {
            url = url + "?" + urlParam;
        }
        Map<String, String[]> params = request.getParameterMap();
        @SuppressWarnings("static-access")
        String path = new String(new BASE64().decode(base64));
        ShareTask task = shareTaskService.findByMemberIdAndTaskDef(this.getLoginMemberInfo().getId(), shareTaskId);
        if (task != null) {
            writeLightApp(response, path, url, params, task.getLuckyNum(), defId, String.valueOf(shareTaskId));
        } else {
            writeLightApp(response, path, url, params, null, defId, String.valueOf(shareTaskId));
        }
    }

    private void writeLightApp(HttpServletResponse response, String path, String url, Map<String, String[]> params,
                               String luckNum, String defId, String shareTaskId) {
        Signature signature = signatureService.findByAppid(weixinGzOauthClient.getWEIXIN_GZ_APPKEY());// 正式
        Map<String, String> wechat = WeixinSign.sha1Sign(signature.getTicket(), signature.getAppid(), url);
        PrintWriter writer = null;
        try {
            String str = this.getUrl4Weixin(path);
            if (wechat != null && str != null) {
                if (params != null && params.keySet() != null) {
                    for (String key : params.keySet()) {
                        String[] values = params.get(key);
                        String value = "";
                        for (int i = 0; i < values.length; i++) {
                            String va = values[i];
                            value += value + va;
                        }
                        str = str.replaceAll("~" + key + "~", value);
                    }
                }
                str = str.replaceAll("~signature~", wechat.get("signature"));
                str = str.replaceAll("~nonceStr~", wechat.get("nonceStr"));
                str = str.replaceAll("~timestamp~", wechat.get("timestamp"));
                str = str.replaceAll("~appId~", wechat.get("appId"));
                str = str.replaceAll("~defId~", defId);
                str = str.replaceAll("~taskId~", shareTaskId);
                if (StringUtils.isNotBlank(luckNum)) {
                    for (int i = 0; luckNum != null && i < luckNum.length(); i++) {
                        str = str.replaceAll("~luckNum" + (i + 1) + "~", luckNum.substring(i, i + 1));
                    }
                } else {
                    luckNum = "??????";
                    for (int i = 0; luckNum != null && i < luckNum.length(); i++) {
                        str = str.replaceAll("~luckNum" + (i + 1) + "~", luckNum.substring(i, i + 1));
                    }
                }
            }
            response.setHeader("content-type", "text/html;charset=UTF-8");
            writer = response.getWriter();
            writer.write(str == null ? "" : str);
            writer.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String getUrl4Weixin(String readPath) throws IOException {
        String domain = "http://lightapp.d2cmall.com";
        URL targetUrl;
        String str = null;
        try {
            targetUrl = new URL(domain + "/" + readPath);
            BufferedReader in = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
            StringBuffer strBuffer = new StringBuffer();
            while ((str = in.readLine()) != null) {
                strBuffer.append(str);
            }
            str = strBuffer.toString();
            in.close();
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
        return str;
    }

}
