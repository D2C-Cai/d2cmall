package com.d2c.member.support;

import java.math.BigDecimal;

public class RecommendInfo extends BillInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 推荐人ID
     */
    private Long recomMemberInfoId;
    /**
     * 赠送金额
     */
    private BigDecimal recommendRebates;

    public RecommendInfo() {
        super();
    }

    public RecommendInfo(String businessType, String payType) {
        super(businessType, payType);
    }

    public Long getRecomMemberInfoId() {
        return recomMemberInfoId;
    }

    public void setRecomMemberInfoId(Long recomMemberInfoId) {
        this.recomMemberInfoId = recomMemberInfoId;
    }

    public BigDecimal getRecommendRebates() {
        return recommendRebates;
    }

    public void setRecommendRebates(BigDecimal recommendRebates) {
        this.recommendRebates = recommendRebates;
    }

}
