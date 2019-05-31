package com.d2c.product.third.upyun;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.core.helper.SpringHelper;
import com.d2c.product.property.ProductProperty;
import com.d2c.product.third.upyun.core.PullingHandler;
import com.d2c.product.third.upyun.core.Result;
import com.d2c.product.third.upyun.core.UpException;
import com.d2c.product.third.upyun.core.UpYunUtils;
import com.d2c.util.date.DateUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpYunClient {

    private static final String BUCKET_NAME = "d2c-pic";
    private static final String OPERATOR_NAME = "d2c";
    private static final String OPERATOR_PWD = "d2c123456";
    private static UpYunClient instance = null;
    private static ProductProperty productProperty;

    private UpYunClient() {
        productProperty = SpringHelper.getBean(ProductProperty.class);
    }

    public static UpYunClient getInstance() {
        if (instance == null) {
            instance = new UpYunClient();
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {
        String[] aa = UpYunClient.getInstance()
                .pullNetworkImage(new String[]{"http://upyundocs.b0.upaiyun.com/img/spider.png",
                        "http://img1.gtimg.com/ninja/2/2018/06/ninja152877089883910.jpg"});
        System.out.println(Arrays.asList(aa));
    }

    public String[] pullNetworkImage(String[] imageUrls) {
        PullingHandler handler = new PullingHandler(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
        // 初始化参数组 Map
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        // 空间名
        paramsMap.put(PullingHandler.Params.BUCKET_NAME, BUCKET_NAME);
        // 回调地址
        paramsMap.put(PullingHandler.Params.NOTIFY_URL, productProperty.getAppUrl() + "/upyun/callback/kaola");
        // 选择任务
        paramsMap.put(PullingHandler.Params.APP_NAME, "spiderman");
        // 已json格式生成任务信息
        JSONArray array = new JSONArray();
        // 添加处理参数
        for (String imageUrl : imageUrls) {
            JSONObject json = new JSONObject();
            json.put(PullingHandler.Params.URL, imageUrl);
            json.put(PullingHandler.Params.RANDOM, false);
            json.put(PullingHandler.Params.OVERWRITE, true);
            json.put(PullingHandler.Params.SAVE_AS, DateUtil.day2str2(new Date()) + "/" + UpYunUtils.md5(imageUrl));
            array.add(json);
        }
        // 添加任务信息
        paramsMap.put(PullingHandler.Params.TASKS, array);
        try {
            Result result = handler.process(paramsMap);
            if (result.isSucceed()) {
                String[] ids = handler.getTaskId(result.getMsg());
                return ids;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UpException e) {
            e.printStackTrace();
        }
        return null;
    }

}
