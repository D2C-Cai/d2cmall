package com.d2c.member.view;

import com.d2c.common.api.annotation.HideColumn;
import com.d2c.common.api.annotation.HideEnum;
import com.d2c.common.api.view.BaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MemberInfoVO extends BaseVO {

    private static final long serialVersionUID = -4310135075540752478L;
    /**
     * MemberInfoId
     */
    private Long id;
    /**
     * 会员账号
     */
    @HideColumn(type = HideEnum.MOBILE)
    private String loginCode;
    /**
     * 最后登录时间
     */
    private String loginDate;
    /**
     * 最后登录设备
     */
    private String loginDevice;
    /**
     * 会员昵称
     */
    private String nickname;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 会员性别
     */
    private String sex;
    /**
     * 最后购买时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date consumeDate;
    /**
     * 实付金额
     */
    private Double payAmount = 0.0;
    /**
     * 购买商品数量
     */
    private Long payCount = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginDevice() {
        return loginDevice;
    }

    public void setLoginDevice(String loginDevice) {
        this.loginDevice = loginDevice;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public Long getPayCount() {
        return payCount;
    }

    public void setPayCount(Long payCount) {
        this.payCount = payCount;
    }

}
