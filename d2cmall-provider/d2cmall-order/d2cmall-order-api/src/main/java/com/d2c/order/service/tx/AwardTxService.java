package com.d2c.order.service.tx;

import com.d2c.order.model.AwardRecord;

import java.math.BigDecimal;

public interface AwardTxService {

    /**
     * 抽奖成功
     *
     * @param awardRecord
     */
    int doLotteryFinished(AwardRecord awardRecord);

    /**
     * 抽奖返利
     *
     * @param memberId
     * @param amount
     * @param uniqueMark
     * @param operator
     * @return
     * @throws Exception
     */
    int doRecome(Long memberId, BigDecimal amount, String uniqueMark, String operator) throws Exception;

}
