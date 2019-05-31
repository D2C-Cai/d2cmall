package com.d2c.order.service.tx;

public interface CompensationTxService {

    /**
     * 赔偿支付到钱包
     *
     * @param id
     * @param username
     * @param remark
     * @return
     */
    int doCompensationPay(Long id, String username, String remark);

}
