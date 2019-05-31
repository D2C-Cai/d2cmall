package com.d2c.common.api.response;

import com.d2c.common.api.page.PageResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * APP返回数据对象
 */
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 服务端异常，如SQL错误等
     */
    private boolean success = true;
    /**
     * 是否登录状态
     */
    private boolean login = true;
    /**
     * status:返回状态码（成功为1，不成功为必负数，0为请求成功但影响行数为0）<br>
     */
    private int status = 1;
    /**
     * 成功或者错误信息
     */
    private String msg = "";
    /**
     * 返回的一般格式数据
     */
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 返回的分页列表数据
     *
     * @param key
     * @param pager
     * @param array
     */
    public void putPage(String key, PageResult<?> pager, Object array) {
        Map<String, Object> json = new HashMap<>();
        json.put("index", pager.getPageNumber());
        json.put("pageSize", pager.getPageSize());
        json.put("total", pager.getTotalCount());
        json.put("previous", pager.isForward());
        json.put("next", pager.isNext());
        json.put("list", array);
        this.put(key, json);
    }

    public void putPage(String key, PageResult<?> pager) {
        Map<String, Object> json = new HashMap<>();
        json.put("index", pager.getPageNumber());
        json.put("pageSize", pager.getPageSize());
        json.put("total", pager.getTotalCount());
        json.put("previous", pager.isForward());
        json.put("next", pager.isNext());
        json.put("list", pager.getList());
        this.put(key, json);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void put(String key, Object value) {
        this.getData().put(key, value);
    }

    public void remove(String key) {
        this.getData().remove(key);
    }

}
