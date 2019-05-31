package com.d2c.order.model;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.model.PreUserDO;
import com.d2c.common.base.utils.StringUt;

import javax.persistence.Table;
import java.util.Date;

/**
 * 抽奖记录
 */
@Table(name = "o_award_record")
public class AwardRecord extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 奖品id
     */
    private Long awardId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 奖品名称
     */
    private String awardName;
    /**
     * 中奖会员头像
     */
    private String headPic;
    /**
     * 中奖时间
     */
    private Date lotteryTime;
    /**
     * 奖品类型
     */
    private String awardProductType;
    /**
     * 奖品参数
     */
    private String awardProductParam;
    /**
     * 奖品图片
     */
    private String awardPic;
    /**
     * 奖品级别
     */
    private Integer awardLevel;
    /**
     * 級別名称
     */
    private String levelName;
    /**
     * D2C账号
     */
    private String loginNo;
    /**
     * 中奖的场次
     */
    private Long sessionId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getAwardId() {
        return awardId;
    }

    public void setAwardId(Long awardId) {
        this.awardId = awardId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Date getLotteryTime() {
        return lotteryTime;
    }

    public void setLotteryTime(Date lotteryTime) {
        this.lotteryTime = lotteryTime;
    }

    public Integer getAwardLevel() {
        return awardLevel;
    }

    public void setAwardLevel(Integer awardLevel) {
        this.awardLevel = awardLevel;
    }

    public String getLoginNo() {
        return loginNo;
    }

    public void setLoginNo(String loginNo) {
        this.loginNo = loginNo;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getAwardProductType() {
        return awardProductType;
    }

    public void setAwardProductType(String awardProductType) {
        this.awardProductType = awardProductType;
    }

    public String getAwardProductParam() {
        return awardProductParam;
    }

    public void setAwardProductParam(String awardProductParam) {
        this.awardProductParam = awardProductParam;
    }

    public String getAwardPic() {
        return awardPic;
    }

    public void setAwardPic(String awardPic) {
        this.awardPic = awardPic;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("memberId", this.getMemberId());
        obj.put("loginNo", StringUt.hideMobile(this.getLoginNo()));
        obj.put("memberName", StringUt.hideMobile(this.getMemberName()));
        obj.put("awardName", this.getAwardName());
        obj.put("headPic", this.getHeadPic());
        obj.put("awardPic", this.getAwardPic());
        return obj;
    }

}
