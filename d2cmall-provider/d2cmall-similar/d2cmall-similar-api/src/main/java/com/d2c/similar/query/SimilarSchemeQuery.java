package com.d2c.similar.query;

import com.d2c.common.api.annotation.search.SearchField;
import com.d2c.common.api.query.enums.OperType;
import com.d2c.common.api.query.model.BaseQuery;

public class SimilarSchemeQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;
    @SearchField(oper = OperType.LIKE)
    private String ruleCode;
    @SearchField(oper = OperType.LIKE)
    private String ruleName;
    private String ruleType;

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

}
