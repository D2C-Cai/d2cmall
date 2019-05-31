package com.d2c.common.api.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础提示类
 */
public abstract class Response implements Serializable {

    public final static int SUCCESS = 1;
    public final static int ERROR = -1;
    private static final long serialVersionUID = 1L;
    protected int status = 1;
    protected String message = "操作成功";
    protected boolean login = true;
    protected Object data;
    /**
     * 附加数据
     */
    protected Map<String, Object> datas = new HashMap<String, Object>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public void setMsg(String msg) {
        if (msg == null) {
            msg = "";
        }
        this.message = msg;
    }

    public Map<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }

    public void put(String key, Object value) {
        this.getDatas().put(key, value);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
