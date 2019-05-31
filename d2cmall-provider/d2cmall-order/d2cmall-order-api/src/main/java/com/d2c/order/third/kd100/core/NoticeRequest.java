package com.d2c.order.third.kd100.core;

public class NoticeRequest {

    /**
     * 监控状态 polling:监控中，shutdown:结束(签收)，abort:中止，updateall：重新推送
     */
    private String status = "";
    @Deprecated
    private String billstatus = "";
    /**
     * 监控状态相关消息
     */
    private String message = "";
    /**
     * 最后一次结果
     */
    private Result lastResult = new Result();
    /**
     * 快递公司编码是否出错
     */
    private String autoCheck;
    /**
     * 原始的快递公司编码
     */
    private String comOld;
    /**
     * 纠正后的新的快递公司编码
     */
    private String comNew;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillstatus() {
        return billstatus;
    }

    public void setBillstatus(String billstatus) {
        this.billstatus = billstatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getLastResult() {
        return lastResult;
    }

    public void setLastResult(Result lastResult) {
        this.lastResult = lastResult;
    }

    public String getAutoCheck() {
        return autoCheck;
    }

    public void setAutoCheck(String autoCheck) {
        this.autoCheck = autoCheck;
    }

    public String getComOld() {
        return comOld;
    }

    public void setComOld(String comOld) {
        this.comOld = comOld;
    }

    public String getComNew() {
        return comNew;
    }

    public void setComNew(String comNew) {
        this.comNew = comNew;
    }

}
