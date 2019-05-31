package com.d2c.order.dto;

import com.alibaba.fastjson.JSONObject;
import com.d2c.order.model.RechargeRule;

import java.util.HashMap;
import java.util.Map;

public class RechargeRuleDto extends RechargeRule {

    private static final long serialVersionUID = 1L;
    /**
     * 促销类型名称
     */
    private String ruleTypeName;

    public String getRuleTypeName() {
        RuleType ruleType = RuleType.getStatus(this.getRuleType());
        if (ruleType != null) {
            ruleTypeName = ruleType.getName();
        }
        return ruleTypeName;
    }

    public void setRuleTypeName() {
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("ruleTypeName", this.getRuleTypeName());
        obj.put("id", this.getId());
        obj.put("name", this.getName());
        obj.put("solution", this.getSolution());
        obj.put("startTime", this.getStartTime());
        obj.put("endTime", this.getEndTime());
        obj.put("status", this.getStatus());
        obj.put("description", this.getDescription());
        obj.put("limited", this.getLimited());
        return obj;
    }

    public enum RuleType {
        X_ON_Y_STEP(1), X_ON_Y_UNLIMITED(2);
        private static Map<Integer, RuleType> holder = new HashMap<>();

        static {
            for (RuleType ruleType : values()) {
                holder.put(ruleType.getCode(), ruleType);
            }
        }

        private Integer code;

        RuleType(Integer code) {
            this.code = code;
        }

        public static RuleType getStatus(Integer i) {
            return holder.get(i);
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            if (this.code == null) {
                return "未知";
            }
            switch (code) {
                case 1:
                    return "满送(不支持提现)";
                case 2:
                    return "满送上不封顶(不支持提现)";
                default:
                    return "未知";
            }
        }
    }

}
