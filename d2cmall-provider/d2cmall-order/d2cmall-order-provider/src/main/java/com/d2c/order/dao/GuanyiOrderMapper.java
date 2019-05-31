package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.GuanyiOrder;
import com.d2c.order.query.GuanyiOrderSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuanyiOrderMapper extends SuperMapper<GuanyiOrder> {

    Integer countBySearcher(@Param("searcher") GuanyiOrderSearcher search);

    List<GuanyiOrder> findBySearcher(@Param("searcher") GuanyiOrderSearcher search, @Param("pager") PageModel page);

    GuanyiOrder findLastDeliverOrder();

    int doHandle(@Param("id") Long id, @Param("type") Integer type, @Param("handleMan") String handleMan,
                 @Param("handleContent") String handleContent);

}
