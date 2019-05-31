package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.CustomerCompensation;
import com.d2c.order.model.OrderItem;
import com.d2c.order.query.CustomerCompensationSearcher;

import java.math.BigDecimal;

public interface CustomerCompensationService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CustomerCompensation findById(Long id);

    /**
     * 计算订单超时的赔偿金
     *
     * @param orderItem
     * @return
     */
    int doOrderItemCompensation(OrderItem orderItem, String operator, Integer type);

    /**
     * 修改赔偿金额
     *
     * @param id
     * @param compensationAmount
     * @return
     */
    int updateCompensationAmount(Long id, BigDecimal compensationAmount, String operator, String remark);

    /**
     * 设置赔偿单为已赔偿
     *
     * @param id
     * @return
     */
    int doPay(Long id);

    /**
     * 关闭采购单
     *
     * @param id
     * @param operator
     * @return
     */
    int doClose(Long id, String operator, String remark);

    /**
     * 列表查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<CustomerCompensation> findBySearcher(CustomerCompensationSearcher searcher, PageModel page);

    /**
     * 根据条件统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(CustomerCompensationSearcher searcher);

    /**
     * 订单明细完结后
     *
     * @param orderItemId
     * @return
     */
    int updateStatusByOrderItem(Long orderItemId);

    /**
     * 按查询条件计算
     *
     * @param searcher
     */
    BigDecimal sumBySearcher(CustomerCompensationSearcher searcher);

}
