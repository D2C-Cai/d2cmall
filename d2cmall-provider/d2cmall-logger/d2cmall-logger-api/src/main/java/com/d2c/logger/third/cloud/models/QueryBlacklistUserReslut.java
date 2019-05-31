package com.d2c.logger.third.cloud.models;

/**
 * queryBlacklistUser返回结果
 */
public class QueryBlacklistUserReslut {

    // 返回码，200 为正常。
    Integer code;
    // 黑名单用户列表。
    String[] users;
    // 错误信息。
    String errorMessage;

    public QueryBlacklistUserReslut() {
    }

    public QueryBlacklistUserReslut(Integer code, String[] users, String errorMessage) {
        this.code = code;
        this.users = users;
        this.errorMessage = errorMessage;
    }

    /**
     * 获取code
     *
     * @return Integer
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取users
     *
     * @return String[]
     */
    public String[] getUsers() {
        return users;
    }

    /**
     * 设置users
     */
    public void setUsers(String[] users) {
        this.users = users;
    }

    /**
     * 获取errorMessage
     *
     * @return String
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 设置errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
