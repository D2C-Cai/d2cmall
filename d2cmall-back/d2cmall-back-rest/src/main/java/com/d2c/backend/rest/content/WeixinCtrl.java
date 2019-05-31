package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WeixinCtrl {

    private static RestTemplate restTemplate = new RestTemplate();
    private static String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    private static String token = "9_Cekpmq7Pkhcp1GZeZIS2V8SDSZYxTy59mL6Uykqz6-gnHaUwdjcTC9itVU3Dp_GLt6YG2cA5r-t9AGsAMktmD5GbmSJQP1fTYRKbnIo4v-HYVMIHLH2vJlkXnOkURKfAGABYR";

    public static void main(String[] args) {
        createMenu();
    }

    public static void createMenu() {
        JSONObject menu = new JSONObject();
        JSONArray buttons = new JSONArray();
        JSONObject button1 = new JSONObject();// 菜单1
        button1.put("type", "miniprogram");
        button1.put("name", "买手服务中心");
        button1.put("appid", "wx6807f3a22c99318f");
        button1.put("url", "http://www.d2cmall.com");
        button1.put("pagepath", "/pages/index/index");
        // JSONObject button2 = new JSONObject();// 菜单2
        // button2.put("name", "买手教程");
        // JSONArray subButtons2 = new JSONArray();
        // JSONObject subItem21 = new JSONObject();
        // subItem21.put("name", "买手开店指南");
        // subItem21.put("type", "view");
        // subItem21.put("url", "http://www.d2cmall.com/page/maishouzhinan");
        // subButtons2.add(subItem21);
        // JSONObject subItem22 = new JSONObject();
        // subItem22.put("name", "买手问题帮助");
        // subItem22.put("type", "view");
        // subItem22.put("url", "http://www.d2cmall.com/page/buyerhelp");
        // subButtons2.add(subItem22);
        // button2.put("sub_button", subButtons2);
        JSONObject button3 = new JSONObject();// 菜单2
        button3.put("type", "view");
        button3.put("name", "素材中心");
        button3.put("url",
                "https://mp.weixin.qq.com/mp/homepage?__biz=MzI4Njk4NzUzMQ==&hid=2&sn=b1fa5d725eb5c31db4c525a748890485");
        buttons.add(button1);
        buttons.add(button3);
        menu.put("button", buttons);
        ResponseEntity<JSONObject> result = restTemplate.postForEntity(createMenuUrl + token, menu, JSONObject.class);
        System.out.println(result.toString());
    }

}
