package com.d2c.member.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.util.date.DateUtil;

import javax.persistence.Table;
import java.util.Date;

/**
 * 衣橱穿搭
 *
 * @author Lain
 */
@Table(name = "m_wardrobe_collocation")
public class WardrobeCollocation extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员账号
     */
    private String loginCode;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员发布时头像
     */
    private String headPic;
    /**
     * 买家秀图片
     */
    private String pic;
    /**
     * 描述
     */
    private String description;
    /**
     * 短视频地址
     */
    private String video;
    /**
     * 视频时间长度
     */
    private Long timeLength;
    /**
     * 状态，-1已拒绝 0未审核，1已审核
     */
    private Integer status = 0;
    /**
     * 审核人
     */
    private String verifyMan;
    /**
     * 是否公开
     */
    private Integer open = 0;
    /**
     * 城市
     */
    private String city;
    /**
     * 温度
     */
    private String temp;
    /**
     * 相机
     */
    private String camera;
    /**
     * 天气
     */
    private String weather;
    /**
     * 业务时间
     */
    private Date transactionTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Long getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Long timeLength) {
        this.timeLength = timeLength;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getVerifyMan() {
        return verifyMan;
    }

    public void setVerifyMan(String verifyMan) {
        this.verifyMan = verifyMan;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public JSONArray getPicsToArray() {
        JSONArray arry = new JSONArray();
        if (pic != null && pic.length() > 0) {
            for (String item : pic.split(",")) {
                if (item == null) {
                    continue;
                }
                arry.add(item);
            }
        }
        return arry;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.getId());
        obj.put("createDate", DateUtil.second2str(this.getCreateDate()));
        obj.put("loginCode", this.getLoginCode());
        obj.put("nickname", this.getNickname());
        obj.put("memberId", this.getMemberId());
        obj.put("headPic", this.getHeadPic());
        obj.put("pics", this.getPicsToArray());
        obj.put("description", this.getDescription());
        obj.put("video", this.getVideo());
        obj.put("timeLength", this.getTimeLength());
        obj.put("open", this.getOpen());
        obj.put("city", this.getCity());
        obj.put("temp", this.getTemp());
        obj.put("weather", this.getWeather());
        obj.put("camera", this.getCamera());
        obj.put("transactionTime", DateUtil.second2str(this.getTransactionTime()));
        return obj;
    }

}
