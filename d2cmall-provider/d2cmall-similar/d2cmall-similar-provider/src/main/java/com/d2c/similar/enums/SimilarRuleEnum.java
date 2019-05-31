package com.d2c.similar.enums;

import com.d2c.similar.entity.SimilarRuleDO;
import com.d2c.similar.similar.resolver.enums.SimilarFieldType;
import com.d2c.similar.similar.resolver.enums.SimilarRuleType;

import java.util.ArrayList;
import java.util.List;

import static com.d2c.similar.similar.resolver.enums.SimilarRuleType.*;

public enum SimilarRuleEnum {
    ORIGINAL_PRICE("商品价格", NORMAL_RATE_TYPE, "minPrice", SimilarFieldType.DECIMAL, 0.2, 15),
    UP_MARKET_DATE("上架日期", DATE_NORMAL_TYPE, "upMarketDate", SimilarFieldType.DATE, 180, 10),
    PRODUCT_SERIES("同系列商品", STRING_EQUALS_TYPE, "seriesId", SimilarFieldType.STRING, 1, 6),
    PRODUCT_BRAND("同品牌商品", STRING_EQUALS_TYPE, "designerId", SimilarFieldType.STRING, 1, 5),
    BRAND_STYLE("品牌风格", STRING_EQUALS_TYPE, "brandStyle", SimilarFieldType.STRING, 1, 10),
    CATEGORY_LEVEL("品类分级", CATEGORY_DEFINE_TYPE, "productCategoryId", SimilarFieldType.NUMBER, 1, 10);
//	PRODUCT_CATEGORY("商品品类", EUCLIDEAM_DIMEN_TYPE, "productCategoryId", SimilarFieldType.NUMBER, 0.5, 8),
    String ruleName;
    SimilarRuleType ruleType;
    String fieldExpr;
    SimilarFieldType fieldType;
    double deviation; //偏差
    double weight;

    SimilarRuleEnum(String ruleName, SimilarRuleType ruleType, String fieldExpr, SimilarFieldType fieldType, double deviation, double weight) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
        this.fieldExpr = fieldExpr;
        this.fieldType = fieldType;
        this.deviation = deviation;
        this.weight = weight;
    }

    public static List<SimilarRuleDO> getAllRules() {
        List<SimilarRuleDO> list = new ArrayList<>();
        for (SimilarRuleEnum e : values()) {
            list.add(e.getSimilarRule());
        }
        return list;
    }

    public SimilarRuleDO getSimilarRule() {
        SimilarRuleDO bean = new SimilarRuleDO();
        bean.setRuleCode(name());
        bean.setRuleName(ruleName);
        bean.setRuleType(ruleType.name());
        bean.setIsDimen(isDimens());
        bean.setFieldExpr(fieldExpr);
        bean.setFieldType(fieldType.name());
        bean.setDeviation(deviation);
        bean.setWeight(weight);
        return bean;
    }

    /**
     * 是否多维离散
     */
    public boolean isDimens() {
        return ruleType.isDimens();
    }

    @Override
    public String toString() {
        return this.ruleName;
    }
}
