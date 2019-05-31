package com.d2c.product.model;

import com.d2c.common.api.model.PreUserDO;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 砍价规则
 */
@Table(name = "o_bargain_rule")
public class BargainRule extends PreUserDO {

    private static final long serialVersionUID = 1L;
    /**
     * 金额级别
     */
    private BigDecimal gradePrice;
    /**
     * 砍价金额范围始
     */
    private Integer min;
    /**
     * 砍价金额范围终
     */
    private Integer max;
    /**
     * 砍价活动ID
     */
    private Long bargainPromotionId;

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public BigDecimal getGradePrice() {
        return gradePrice;
    }

    public void setGradePrice(BigDecimal gradePrice) {
        this.gradePrice = gradePrice;
    }

    public Long getBargainPromotionId() {
        return bargainPromotionId;
    }

    public void setBargainPromotionId(Long bargainPromotionId) {
        this.bargainPromotionId = bargainPromotionId;
    }

}
