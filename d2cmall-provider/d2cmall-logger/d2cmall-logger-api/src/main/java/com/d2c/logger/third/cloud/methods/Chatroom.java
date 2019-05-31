package com.d2c.logger.third.cloud.methods;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.third.cloud.core.Sign;
import com.d2c.logger.third.cloud.models.ChatroomUserQueryReslut;
import com.d2c.logger.third.cloud.util.HostType;
import com.d2c.logger.third.cloud.util.HttpUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class Chatroom {

    private static final String UTF8 = "UTF-8";

    /**
     * 查询聊天室内用户方法
     *
     * @param chatroomId:要查询的聊天室   ID。（必传）
     * @param count:要获取的聊天室成员数，上限为 500 ，超过 500 时最多返回 500 个成员。（必传）
     * @param order:加入聊天室的先后顺序，    1 为加入时间正序， 2 为加入时间倒序。（必传）
     * @return ChatroomUserQueryReslut
     **/
    public ChatroomUserQueryReslut queryUser(String chatroomId, String count, String order) throws Exception {
        if (chatroomId == null) {
            throw new IllegalArgumentException("Paramer 'chatroomId' is required");
        }
        if (count == null) {
            throw new IllegalArgumentException("Paramer 'count' is required");
        }
        if (order == null) {
            throw new IllegalArgumentException("Paramer 'order' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&chatroomId=").append(URLEncoder.encode(chatroomId.toString(), UTF8));
        sb.append("&count=").append(URLEncoder.encode(count.toString(), UTF8));
        sb.append("&order=").append(URLEncoder.encode(order.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/chatroom/user/query.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (ChatroomUserQueryReslut) JSONObject.parseObject(HttpUtil.returnResult(conn),
                ChatroomUserQueryReslut.class);
    }

}