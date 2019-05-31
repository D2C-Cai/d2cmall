package com.d2c.backend.rest.crm.demo;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientDemo {

    /**
     * 编号
     */
    public static final String PARTNER_CODE = "3333";

    public static void main(String[] args) {
        /**
         * 接口需要的参数 例如：pagerSize 每页记录数;pagerNumber 当前页码;lastSysDate 修改时间
         */
        Integer pagerSize = 10;
        Integer pagerNumber = 1;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date lastSysDate = new Date();
        try {
            lastSysDate = sf.parse("2016-10-1");
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        ApiOauth d2cApiOauth = ApiOauth.getInstance();
        // 设置接口路径和接口参数
        List<Map<String, Object>> params = d2cApiOauth.getParams("/api/crm/member/list");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pagerSize", pagerSize);
        map.put("pagerNumber", pagerNumber);
        map.put("lastSysDate", lastSysDate.getTime());
        params.add(map);
        try {
            // 发送请求并获取json对象的结果
            JSONObject json = d2cApiOauth.invoke();
            if (json != null && json.length() > 0) {
                System.out.println(json.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
