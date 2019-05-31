package com.d2c.order.service.tx;

import com.d2c.order.model.WithdrawCash;

public interface WithdrawTxService {

    /**
     * 提现创建并自动提交完成
     *
     * @param success
     * @return
     */
    int doSuccessWithdraw(WithdrawCash success);

}
