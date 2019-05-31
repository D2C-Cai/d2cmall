package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.RedPacketsItem;
import com.d2c.order.query.RedPacketsItemSearcher;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RedPacketsItemMapper extends SuperMapper<RedPacketsItem> {

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    List<RedPacketsItem> findByTypeAndMember(@Param("memberId") Long memberId, @Param("type") String type);

    List<RedPacketsItem> findBySearcher(@Param("searcher") RedPacketsItemSearcher searcher,
                                        @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") RedPacketsItemSearcher searcher);

    RedPacketsItem findByTransactionAndType(@Param("transactionId") Long transactionId, @Param("type") String type);

    int doRebate(@Param("id") Long id, @Param("redAmount") BigDecimal redAmount);

}
