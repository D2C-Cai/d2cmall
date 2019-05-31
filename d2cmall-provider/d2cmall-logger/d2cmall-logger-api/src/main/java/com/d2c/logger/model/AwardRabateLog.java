package com.d2c.logger.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "log_award_rabate")
public class AwardRabateLog extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 返利金额
     */
    private BigDecimal rabate;
    /**
     * 备注信息
     */
    private String uniqueMark;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getRabate() {
        return rabate;
    }

    public void setRabate(BigDecimal rabate) {
        this.rabate = rabate;
    }

    public String getUniqueMark() {
        return uniqueMark;
    }

    public void setUniqueMark(String uniqueMark) {
        this.uniqueMark = uniqueMark;
    }

}
