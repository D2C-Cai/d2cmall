package com.d2c.flame.controller;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.flame.controller.base.Config;
import com.d2c.frame.web.control.BaseControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class LoginController extends BaseControl {

    @Autowired
    private RedisHandler<String, Map<String, Object>> redisHandler;
    private DateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");

    /**
     * 密码登录
     *
     * @param password
     * @param response
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(String password, HttpServletResponse response) {
        ResponseResult result = new ResponseResult();
        String day = dayFormat.format(new Date());
        String dynamicPassword = MD5Util.encodeMD5Hex(Config.PASSWORD + day);
        if (password != null && password.equals(dynamicPassword)) {
            result.put("accesstoken", Config.getToken(day));
            return result;
        } else {
            result.setStatus(-1);
            result.setMsg("密码错误，请联系管理员！");
            return result;
        }
    }

    /**
     * 头部数字
     *
     * @param cookie
     * @return
     */
    @RequestMapping(value = "/head", method = RequestMethod.GET)
    public ResponseResult head() {
        ResponseResult result = new ResponseResult();
        result.setData(redisHandler.get("head_count"));
        return result;
    }

}
