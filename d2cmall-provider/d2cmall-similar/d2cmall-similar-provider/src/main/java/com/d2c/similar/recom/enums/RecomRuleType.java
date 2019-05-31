package com.d2c.similar.recom.enums;

import com.d2c.common.base.utils.ConvertUt;
import com.d2c.similar.entity.RecomRuleDO;

public enum RecomRuleType {
    NORMAL_TYPE("线性上升"),
    DECLINE_TYPE("线性下降") {
        Double getValueImpl(RecomRuleDO rule, Double value) {
            if (rule.getMax() == null || rule.getMax() <= 0) {
                return 0.0;
            }
            double v = rule.getMax() - value;
            if (v < 0) v = 0;
            if (v > rule.getMax()) v = rule.getMax();
            return v;
        }
    };
    String remark;

    RecomRuleType(String remark) {
        this.remark = remark;
    }

    public static Double getRecomValue(RecomRuleDO rule, Object fieldValue) {
        if (fieldValue == null) return 0.0;
        RecomRuleType ruleType = NORMAL_TYPE;
        try {
            ruleType = valueOf(rule.getRuleType());
        } catch (Exception e) {
        }
        return ruleType.getValue(rule, fieldValue);
    }

    Double getValueImpl(RecomRuleDO rule, Double value) {
        return value;
    }
    //***********************************

    Double getDouble(Object value) {
        try {
            return ConvertUt.convertType(value, Double.class);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Double getValue(RecomRuleDO rule, Object value) {
        return getValueImpl(rule, getDouble(value));
    }
}
