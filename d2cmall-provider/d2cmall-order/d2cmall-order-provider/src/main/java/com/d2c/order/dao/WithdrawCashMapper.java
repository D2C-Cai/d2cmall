package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.WithdrawCash;
import com.d2c.order.query.WithdrawCashSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithdrawCashMapper extends SuperMapper<WithdrawCash> {

    List<WithdrawCash> findBySearch(@Param("searcher") WithdrawCashSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") WithdrawCashSearcher searcher);

    int confirm(Long id);

    int close(Long id);

    int success(WithdrawCash drawCash);

    int cancel(Long id);

}
