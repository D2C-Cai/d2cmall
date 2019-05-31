package com.d2c.logger.third.cloud.methods;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.third.cloud.core.Sign;
import com.d2c.logger.third.cloud.messages.BaseMessage;
import com.d2c.logger.third.cloud.models.CodeSuccessReslut;
import com.d2c.logger.third.cloud.util.HostType;
import com.d2c.logger.third.cloud.util.HttpUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class Message {

    private static final String UTF8 = "UTF-8";

    /**
     * 发送聊天室消息方法（一个用户向聊天室发送消息，单条消息最大 128k。每秒钟限 100 次。）
     *
     * @param fromUserId:发送人用户                                Id。（必传）
     * @param toChatroomId:接收聊天室Id，提供多个本参数可以实现向多个聊天室发送消息。（必传）
     * @param txtMessage:发送消息内容（必传）
     * @return CodeSuccessReslut
     **/
    public static CodeSuccessReslut publishChatroom(String fromUserId, String[] toChatroomId, BaseMessage message)
            throws Exception {
        if (fromUserId == null) {
            throw new IllegalArgumentException("Paramer 'fromUserId' is required");
        }
        if (toChatroomId == null) {
            throw new IllegalArgumentException("Paramer 'toChatroomId' is required");
        }
        if (message == null) {
            throw new IllegalArgumentException("Paramer 'message' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&fromUserId=").append(URLEncoder.encode(fromUserId.toString(), UTF8));
        for (int i = 0; i < toChatroomId.length; i++) {
            String child = toChatroomId[i];
            sb.append("&toChatroomId=").append(URLEncoder.encode(child, UTF8));
        }
        sb.append("&objectName=").append(URLEncoder.encode(message.getType(), UTF8));
        sb.append("&content=").append(URLEncoder.encode(message.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/message/chatroom/publish.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (CodeSuccessReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }

}