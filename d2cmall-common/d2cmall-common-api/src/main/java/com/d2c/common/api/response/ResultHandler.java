package com.d2c.common.api.response;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;

import java.util.List;
import java.util.Map;

public class ResultHandler {

    public static SuccessResponse success() {
        return new SuccessResponse();
    }

    public static SuccessResponse successMsg(String message) {
        return new SuccessResponse(null, message);
    }

    public static SuccessResponse success(Object data) {
        return new SuccessResponse(data);
    }

    @SuppressWarnings("rawtypes")
    public static SuccessResponse success(PageResult pager) {
        return new SuccessResponse(pager);
    }

    public static SuccessResponse success(Object o, String message) {
        return new SuccessResponse(o, message);
    }

    @SuppressWarnings("rawtypes")
    public static ResponseResult successAppPage(String key, PageResult pager, Object list) {
        ResponseResult res = new ResponseResult();
        res.putPage(key, pager, list);
        return res;
    }

    @SuppressWarnings("rawtypes")
    public static ResponseResult successAppPage(String key, PageResult pager) {
        ResponseResult res = new ResponseResult();
        res.putPage(key, pager);
        return res;
    }

    public static ResponseResult successApp(String key, Object value) {
        ResponseResult res = new ResponseResult();
        res.put(key, value);
        return res;
    }

    public static <T> SuccessResponse successPage(PageModel page, List<T> list) {
        return new SuccessResponse(new PageResult<T>(page, list));
    }

    public static <T> SuccessResponse successPage(PageModel page, List<T> list, Long count) {
        return new SuccessResponse(new PageResult<T>(page, list, count));
    }

    public static <T> SuccessResponse successPage(PageModel page, List<T> list, Integer count) {
        return new SuccessResponse(new PageResult<T>(page, list, count));
    }

    public static SuccessResponse successData(String key, Object value) {
        SuccessResponse res = new SuccessResponse();
        res.put(key, value);
        return res;
    }

    public static SuccessResponse successData(Map<String, Object> map) {
        SuccessResponse res = new SuccessResponse();
        res.getDatas().putAll(map);
        return res;
    }

    public static SuccessResponse successData(Object data) {
        return successData("data", data);
    }

    public static SuccessResponse successData(Object data, String message) {
        SuccessResponse res = successData("data", data);
        res.setMessage(message);
        return res;
    }

    public static ErrorResponse error(Exception e) {
        return error(e.getMessage());
    }

    public static ErrorResponse unlogin() {
        ErrorResponse res = error("没有登陆");
        res.setLogin(false);
        return res;
    }

    public static ErrorResponse error(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse error(String errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }

}
