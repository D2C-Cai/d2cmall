package com.d2c.similar.enums;

import com.d2c.similar.entity.RecomRuleDO;
import com.d2c.similar.recom.enums.RecomRuleType;

import java.util.ArrayList;
import java.util.List;

import static com.d2c.similar.recom.enums.RecomRuleType.DECLINE_TYPE;
import static com.d2c.similar.recom.enums.RecomRuleType.NORMAL_TYPE;

public enum RecomRuleEnum {
    RECENTLY_SALES_NUM("当月销量", NORMAL_TYPE, "recentlySales", 100),
    SALES_NUM("总销量", NORMAL_TYPE, "sales", 5),
    COMMENTS_NUM("评价数量", NORMAL_TYPE, "comments", 50),
    NEW_DAYS_NUM("新品推荐", DECLINE_TYPE, "newDays", 30, 100),
    IS_RECOM("运营推荐", NORMAL_TYPE, "operRecom", 3000),
    IS_PROMOTION("活动推荐", NORMAL_TYPE, "promotion", 1500);
//	HITS_NUM("明细点击量", NORMAL_TYPE, "hits", 0.1),
//	SHOPPING_NUMBER("商品在所有购物车中的数量", NORMAL_TYPE, "shoppingNum", 100),
//	COLLECT_NUMBER("商品在所有收藏夹中的数量", NORMAL_TYPE, "collectNum", 200),
    String ruleName;
    RecomRuleType ruleType;
    String fieldExpr;
    double weight;
    double max;

    RecomRuleEnum(String ruleName, RecomRuleType ruleType, String fieldExpr, double weight, double max) {
        this(ruleName, ruleType, fieldExpr, weight);
        this.max = max;
    }

    RecomRuleEnum(String ruleName, RecomRuleType ruleType, String fieldExpr, double weight) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
        this.fieldExpr = fieldExpr;
        this.weight = weight;
    }

    public static List<RecomRuleDO> getAllRules() {
        List<RecomRuleDO> list = new ArrayList<>();
        for (RecomRuleEnum e : values()) {
            list.add(e.getRecomRule());
        }
        return list;
    }

    public RecomRuleDO getRecomRule() {
        RecomRuleDO bean = new RecomRuleDO();
        bean.setId(ordinal() + 1);
        bean.setRuleCode(name());
        bean.setRuleName(ruleName);
        bean.setRuleType(ruleType.name());
        bean.setFieldExpr(fieldExpr);
        bean.setWeight(weight);
        bean.setMax(max);
        return bean;
    }

    @Override
    public String toString() {
        return this.ruleName;
    }
}
