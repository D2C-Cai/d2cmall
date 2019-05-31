package com.d2c.main.openapi.controller.home;

import com.d2c.common.api.json.JsonBean;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.DateUt;
import com.d2c.common.base.utils.StringUt;
import com.d2c.common.base.utils.security.MD5Ut;
import com.d2c.common.base.utils.security.RSAUt;
import com.d2c.main.openapi.controller.base.BaseController;
import com.d2c.main.openapi.interceptor.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "home")
public class HomeController extends BaseController {

    @Autowired
    private TokenHandler tokenHandler;

    /**
     * 生成签名方法
     * API: /home/sign
     */
    @ResponseBody
    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    public JsonBean sign(HttpServletRequest request) {
        String token = getHeaderParam(request, "token");
        String privateKey = getHeaderParam(request, "privateKey");
        AssertUt.notBlank(token, "token不能为空");
        AssertUt.notBlank(privateKey, "privateKey不能为空");
        String nonce = getHeaderParam(request, "nonce");
        Long timestamp = DateUt.getTimeInMillis();
        String time = getHeaderParam(request, "timestamp");
        if (StringUt.isNotBlank(time)) {
            timestamp = DateUt.getTimeInMillis(time);
        }
        String signStr = tokenHandler.getSignString(request, token, timestamp, nonce);
        String signMd = MD5Ut.md5(signStr);
        String sign = RSAUt.encodePrivate(privateKey, signMd);
        JsonBean json = new JsonBean();
        json.add("token", token);
        json.add("timestamp", timestamp);
        json.add("time", DateUt.second2str(new Date(timestamp)));
        json.add("nonce", nonce);
        json.add("signStr", signStr);
        json.add("signMd", signMd);
        json.add("sign", sign);
        return json;
    }

    private String getHeaderParam(HttpServletRequest request, String param) {
        String res = request.getHeader(param);
        if (StringUt.isBlank(res)) {
            res = request.getParameter(param);
        }
        return res;
    }

}
