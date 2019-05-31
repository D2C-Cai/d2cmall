package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.GuanyiOrderItem;
import com.d2c.order.query.GuanyiOrderItemSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuanyiOrderItemMapper extends SuperMapper<GuanyiOrderItem> {

    List<GuanyiOrderItem> findByOrderId(Long orderId);

    List<GuanyiOrderItem> findBySearcher(@Param("searcher") GuanyiOrderItemSearcher searcher,
                                         @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") GuanyiOrderItemSearcher searcher);

    int doHandle(@Param("id") Long id, @Param("handleMan") String handleMan,
                 @Param("handleContent") String handleContent);

}
