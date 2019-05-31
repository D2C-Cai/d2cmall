package com.d2c.similar.similar.resolver.dist;

import com.d2c.common.core.helper.SpringHelper;
import com.d2c.similar.service.DimenValueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品属性多维度继承父类
 */
public abstract class AbstractDimenDistance extends AbstractDistance {

    protected DimenValueService dimenValueService;
    protected Map<Integer, List<Double>> dimenMap = new HashMap<>();
    protected List<Double> valueList;
    protected List<Double> targetList;

    @Override
    public void doInit() {
        dimenValueService = SpringHelper.getBean(DimenValueService.class);
    }

    public abstract double doDimenProb();

    public double doExec() {
        valueList = getDimenProbs(value.toString());
        targetList = getDimenProbs(target.toString());
        return doDimenProb();
    }

    private List<Double> getDimenProbs(String v) {
        List<Double> dpList = dimenMap.get(v);
        if (dpList == null) {
            dpList = dimenValueService.getDistByValue(rule.getId(), v);
        }
        return dpList;
    }

}
