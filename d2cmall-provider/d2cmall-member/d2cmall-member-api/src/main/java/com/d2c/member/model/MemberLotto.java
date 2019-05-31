package com.d2c.member.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;

/**
 * 抽奖的权利
 */
@Table(name = "m_member_lotto")
public class MemberLotto extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 会员头像
     */
    private String headPic;
    /**
     * 账号
     */
    private String loginNo;
    /**
     * 剩余次数
     */
    private Integer ownCount;
    /**
     * 用户抽奖来源
     */
    private String lotterySource;
    /**
     * 抽奖场次ID
     */
    private Long sessionId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getLoginNo() {
        return loginNo;
    }

    public void setLoginNo(String loginNo) {
        this.loginNo = loginNo;
    }

    public Integer getOwnCount() {
        return ownCount;
    }

    public void setOwnCount(Integer ownCount) {
        this.ownCount = ownCount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getLotterySource() {
        return lotterySource;
    }

    public void setLotterySource(String lotterySource) {
        this.lotterySource = lotterySource;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public enum LotteryOpportunityEnum {
        SHAREACTIVITY(1, "分享活动", 1), BARGAIN(2, "参加砍价活动", 1), PAYED(3, "付款", 1), LOGIN(4, "登录", 1);

        private String sourceType;
        private Integer code;
        private Integer count;
        LotteryOpportunityEnum(Integer code, String sourceType, Integer count) {
            this.sourceType = sourceType;
            this.code = code;
            this.count = count;
        }

        public String getSourceType() {
            return sourceType;
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

}
