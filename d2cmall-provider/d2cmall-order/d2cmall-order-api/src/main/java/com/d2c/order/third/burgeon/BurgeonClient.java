package com.d2c.order.third.burgeon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.common.core.helper.SpringHelper;
import com.d2c.order.property.OrderProperties;
import com.d2c.util.http.HttpUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BurgeonClient {

    public final static DateFormat dayFormat = new SimpleDateFormat("yyyy_MM_dd");
    private static String BURGEON_API_KEY = "151367aadef44d2cedad90020ad611e8625";
    private static BurgeonClient instance = null;
    private static String newline = System.getProperty("line.separator");
    private OrderProperties orderProperties;

    private BurgeonClient() {
        orderProperties = SpringHelper.getBean(OrderProperties.class);
    }

    public static BurgeonClient getInstance() {
        if (instance == null) {
            instance = new BurgeonClient();
        }
        return instance;
    }

    public void sendBurgeon(JSONObject data, String method) throws Exception {
        Long timeStamp = System.currentTimeMillis();
        String md5Key = MD5Util.encodeMD5Hex(
                BURGEON_API_KEY + "data" + data.toJSONString() + "method" + method + "timeStamp" + timeStamp);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("method", method);
        params.put("timeStamp", timeStamp);
        params.put("data", data);
        params.put("sign", md5Key.toUpperCase());
        toLog(params.toString());
        String response = HttpUtil.sendPostHttps(orderProperties.getBurgeonApiUrl(), params, 15000);
        if (response == null) {
            throw new Exception("伯俊系统响应超时");
        }
        JSONObject result = JSON.parseObject(response);
        if (!result.getString("resultCode").equals("0")) {
            throw new Exception(result.getString("exceptionMessage"));
        }
    }

    // 伯俊提交日志
    private void toLog(final String params) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String fileName = "/mnt/burgeon/" + "burgeon_send_" + dayFormat.format(new Date()) + ".log";
                File file = new File(fileName);
                FileWriter writer = null;
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    writer = new FileWriter(file, true);
                    writer.write(params + newline);
                    writer.flush();
                } catch (IOException e) {
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
        });
        executor.shutdown();
    }

}
