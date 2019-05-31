package com.d2c.logger.third.cloud.methods;

import com.alibaba.fastjson.JSONObject;
import com.d2c.logger.third.cloud.core.Sign;
import com.d2c.logger.third.cloud.models.*;
import com.d2c.logger.third.cloud.util.HostType;
import com.d2c.logger.third.cloud.util.HttpUtil;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class User {

    private static final String UTF8 = "UTF-8";

    /**
     * 获取 Token 方法
     *
     * @param userId:用户        Id，最大长度 64 字节.是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id
     *                         将被当作是同一用户。（必传）
     * @param name:用户名称，最大长度   128 字节.用来在 Push 推送时显示用户的名称.用户名称，最大长度 128 字节.用来在 Push
     *                         推送时显示用户的名称。（必传）
     * @param portraitUri:用户头像 URI，最大长度 1024 字节.用来在 Push 推送时显示用户的头像。（必传）
     * @return TokenReslut
     **/
    public static TokenReslut getToken(String userId, String name, String portraitUri) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        if (name == null) {
            portraitUri = "";
        }
        if (portraitUri == null) {
            portraitUri = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        sb.append("&name=").append(URLEncoder.encode(name.toString(), UTF8));
        sb.append("&portraitUri=").append(URLEncoder.encode(portraitUri.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/getToken.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (TokenReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), TokenReslut.class);
    }

    /**
     * 刷新用户信息方法
     *
     * @param userId:用户        Id，最大长度 64 字节.是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id
     *                         将被当作是同一用户。（必传）
     * @param name:用户名称，最大长度   128 字节。用来在 Push 推送时，显示用户的名称，刷新用户名称后 5 分钟内生效。（可选，提供即刷新，不提供忽略）
     * @param portraitUri:用户头像 URI，最大长度 1024 字节。用来在 Push 推送时显示。（可选，提供即刷新，不提供忽略）
     * @return CodeSuccessReslut
     **/
    public CodeSuccessReslut refresh(String userId, String name, String portraitUri) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        if (name != null) {
            sb.append("&name=").append(URLEncoder.encode(name.toString(), UTF8));
        }
        if (portraitUri != null) {
            sb.append("&portraitUri=").append(URLEncoder.encode(portraitUri.toString(), UTF8));
        }
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/refresh.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (CodeSuccessReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }

    /**
     * 检查用户在线状态 方法
     *
     * @param userId:用户 Id，最大长度 64 字节。是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id
     *                  将被当作是同一用户。（必传）
     * @return CheckOnlineReslut
     **/
    public CheckOnlineReslut checkOnline(String userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/checkOnline.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (CheckOnlineReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), CheckOnlineReslut.class);
    }

    /**
     * 封禁用户方法（每秒钟限 100 次）
     *
     * @param userId:用户                          Id。（必传）
     * @param minute:封禁时长,单位为分钟，最大值为43200分钟。（必传）
     * @return CodeSuccessReslut
     **/
    public CodeSuccessReslut block(String userId, Integer minute) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        if (minute == null) {
            throw new IllegalArgumentException("Paramer 'minute' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        sb.append("&minute=").append(URLEncoder.encode(minute.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/block.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (CodeSuccessReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }

    /**
     * 解除用户封禁方法（每秒钟限 100 次）
     *
     * @param userId:用户 Id。（必传）
     * @return CodeSuccessReslut
     **/
    public CodeSuccessReslut unBlock(String userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/unblock.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (CodeSuccessReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }

    /**
     * 获取被封禁用户方法（每秒钟限 100 次）
     *
     * @return QueryBlockUserReslut
     **/
    public QueryBlockUserReslut queryBlock() throws Exception {
        StringBuilder sb = new StringBuilder();
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/block/query.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (QueryBlockUserReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), QueryBlockUserReslut.class);
    }

    /**
     * 添加用户到黑名单方法（每秒钟限 100 次）
     *
     * @param userId:用户                    Id。（必传）
     * @param blackUserId:被加到黑名单的用户Id。（必传）
     * @return CodeSuccessReslut
     **/
    public CodeSuccessReslut addBlacklist(String userId, String blackUserId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        if (blackUserId == null) {
            throw new IllegalArgumentException("Paramer 'blackUserId' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        sb.append("&blackUserId=").append(URLEncoder.encode(blackUserId.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/blacklist/add.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (CodeSuccessReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }

    /**
     * 获取某用户的黑名单列表方法（每秒钟限 100 次）
     *
     * @param userId:用户 Id。（必传）
     * @return QueryBlacklistUserReslut
     **/
    public QueryBlacklistUserReslut queryBlacklist(String userId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/blacklist/query.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (QueryBlacklistUserReslut) JSONObject.parseObject(HttpUtil.returnResult(conn),
                QueryBlacklistUserReslut.class);
    }

    /**
     * 从黑名单中移除用户方法（每秒钟限 100 次）
     *
     * @param userId:用户                 Id。（必传）
     * @param blackUserId:被移除的用户Id。（必传）
     * @return CodeSuccessReslut
     **/
    public CodeSuccessReslut removeBlacklist(String userId, String blackUserId) throws Exception {
        if (userId == null) {
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        if (blackUserId == null) {
            throw new IllegalArgumentException("Paramer 'blackUserId' is required");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(userId.toString(), UTF8));
        sb.append("&blackUserId=").append(URLEncoder.encode(blackUserId.toString(), UTF8));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(HostType.API, Sign.appKey, Sign.appSecret,
                "/user/blacklist/remove.json", "application/x-www-form-urlencoded");
        HttpUtil.setBodyParameter(body, conn);
        return (CodeSuccessReslut) JSONObject.parseObject(HttpUtil.returnResult(conn), CodeSuccessReslut.class);
    }

}