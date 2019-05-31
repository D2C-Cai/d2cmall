package com.d2c.order.third.kaola.reponse;

/**
 * 物流明细
 *
 * @author Lain
 */
public class Track {

    private String context;
    private String timeDetail;
    private String time;
    private String type;
    private String remark;
    private String address;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTimeDetail() {
        return timeDetail;
    }

    public void setTimeDetail(String timeDetail) {
        this.timeDetail = timeDetail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
