package com.d2c.order.service.tx;

import com.d2c.order.model.CashCard;

public interface CashCardTxService {

    /**
     * d2c卡充值
     *
     * @param sn
     * @param password
     * @param memberInfoId
     * @param loginCode
     * @param accountId
     * @return
     */
    CashCard doConvertCashCard(String sn, String password, Long memberInfoId, String loginCode, Long accountId);

}
