package com.d2c.order.service.tx;

import com.d2c.order.dto.CollageOrderDto;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.CollageOrder;

public interface CollageTxService {

    /**
     * 拼团参团
     *
     * @param collageOrder
     * @return
     */
    CollageOrderDto createCollageOrder(CollageOrder collageOrder);

    /**
     * 拼团开团
     *
     * @param collageGroup
     * @param addressDto
     * @param memberInfo
     * @return
     */
    CollageOrderDto createCollageGroup(CollageGroup collageGroup, CollageOrder collageOrder);

    /**
     * 超时关闭
     *
     * @param id
     * @param closeTime
     * @param failGroup
     * @return
     */
    int doClose(Long id, String closeTime, boolean failGroup);

    /**
     * 拼团失败退款
     *
     * @param id
     * @return
     */
    int doRefund(Long id);

    /**
     * 拼团失败关闭
     *
     * @param id
     * @return
     */
    int doFailGroup(Long id);

    /**
     * 退款至钱包
     *
     * @param id
     * @param operator
     * @return
     */
    int doBackToWallet(Long id, String operator);

}
