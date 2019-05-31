package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CustomerCompensation;
import com.d2c.order.query.CustomerCompensationSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerCompensationMapper extends SuperMapper<CustomerCompensation> {

    /**
     * 更新赔偿金额
     *
     * @param id
     * @param compensationAmount
     * @return
     */
    int updateCompensationAmount(@Param("id") Long id, @Param("compensationAmount") BigDecimal compensationAmount);

    /**
     * 根据orderItem查找需要赔偿的
     *
     * @param orderItem
     * @return
     */
    List<CustomerCompensation> findCompensation(Long orderItem);

    /**
     * 设置赔偿单为已赔偿
     *
     * @param id
     * @return
     */
    int doPay(Long id);

    /**
     * 根据业务id查询
     *
     * @param id
     * @return
     */
    CustomerCompensation findByTransactionId(@Param("transactionId") Long transactionId);

    /**
     * 关闭赔偿单
     *
     * @param id
     * @return
     */
    int doClose(@Param("id") Long id, @Param("remark") String remark);

    /**
     * 根据条件统计
     *
     * @param searcher
     * @return
     */
    int countBySearcher(@Param("searcher") CustomerCompensationSearcher searcher);

    /**
     * 根据条件查询
     *
     * @param searcher
     * @param page
     * @return
     */
    List<CustomerCompensation> findBySearcher(@Param("searcher") CustomerCompensationSearcher searcher,
                                              @Param("page") PageModel page);

    /**
     * 订单完结，赔偿单状态从初始状态到待审核状态
     *
     * @param orderItemId
     * @return
     */
    int updateStatusByOrderItem(@Param("orderItemId") Long orderItemId);

    /**
     * 按条件统计赔偿金额
     *
     * @param searcher
     * @return
     */
    BigDecimal sumBySearcher(@Param("searcher") CustomerCompensationSearcher searcher);

}
