package com.d2c.member.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.util.Date;

/**
 * 会员签到表
 *
 * @author wwn
 */
@Table(name = "m_member_daily_sign")
public class MemberDailySign extends PreUserDO {

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
     * 累计签到天数
     */
    private Integer totalDay;
    /**
     * 当天获得的D币
     */
    private Integer point;
    /**
     * 获得的总D币
     */
    private Long totalPoint;

    public MemberDailySign() {
    }

    public MemberDailySign(Long memberId, String loginCode, Integer totalDay, Integer point, Long totalPoint) {
        super();
        this.memberId = memberId;
        this.loginCode = loginCode;
        this.totalDay = totalDay;
        this.point = point;
        this.totalPoint = totalPoint;
        this.createDate = new Date();
        this.creator = loginCode;
    }

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

    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Long getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Long totalPoint) {
        this.totalPoint = totalPoint;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("memberId", this.getMemberId());
        obj.put("totalDay", this.getTotalDay());
        obj.put("reward", this.getPoint());
        obj.put("totalReward", this.getTotalPoint());
        obj.put("signDate", this.getCreateDate().getTime());
        return obj;
    }

}
