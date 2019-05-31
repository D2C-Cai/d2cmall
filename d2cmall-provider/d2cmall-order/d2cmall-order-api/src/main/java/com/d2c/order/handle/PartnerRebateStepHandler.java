package com.d2c.order.handle;

import java.math.BigDecimal;
import java.util.Arrays;

public class PartnerRebateStepHandler {

    int start = 0;
    private BigDecimal[] stepRatio = new BigDecimal[]{new BigDecimal(0.25), new BigDecimal(0.20),
            new BigDecimal(1.00), new BigDecimal(0.30)};
    final int MAX_STEP = stepRatio.length;
    public PartnerRebateStepHandler(int level) {
        for (LevelEnum item : LevelEnum.values()) {
            if (item.ordinal() == level) {
                start = LevelEnum.BUYER.ordinal() - level;
            }
        }
    }

    public BigDecimal[] formula(BigDecimal rebate, Long partnerId, Long parentId, Long superId, Long masterId,
                                Long topId) {
        BigDecimal[] result = new BigDecimal[MAX_STEP + 1];
        Arrays.fill(result, new BigDecimal(0));
        if (partnerId != null) {
            result[0] = rebate;
        }
        if (parentId != null) {
            rebate = rebate.multiply(stepRatio[start]);
            result[1] = rebate;
            start++;
        }
        if (superId != null) {
            rebate = rebate.multiply(stepRatio[start]);
            result[2] = rebate;
            start++;
        }
        if (masterId != null) {
            rebate = rebate.multiply(stepRatio[start]);
            result[3] = rebate;
            start++;
        }
        if (topId != null) {
            rebate = rebate.multiply(stepRatio[MAX_STEP - 1]);
            result[4] = rebate;
        }
        return result;
    }

    public enum LevelEnum {
        AM, DM, BUYER;
    }

}
