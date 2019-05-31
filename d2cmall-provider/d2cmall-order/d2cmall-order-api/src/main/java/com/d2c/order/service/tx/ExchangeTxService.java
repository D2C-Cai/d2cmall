package com.d2c.order.service.tx;

public interface ExchangeTxService {

    /**
     * 用户确认收货
     *
     * @param exchangeId
     * @param modifyMan
     * @param info
     * @return
     */
    int doReceive(Long exchangeId, String modifyMan, String info);

}
