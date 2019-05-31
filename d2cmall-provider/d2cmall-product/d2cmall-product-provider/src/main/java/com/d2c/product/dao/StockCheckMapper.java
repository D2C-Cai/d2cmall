package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.StockCheck;
import com.d2c.product.query.StockCheckSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockCheckMapper extends SuperMapper<StockCheck> {

    List<StockCheck> findBySearch(@Param("searcher") StockCheckSearcher searcher, @Param("pager") PageModel page);

    int countBySearch(@Param("searcher") StockCheckSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int updateMemo(@Param("id") Long id, @Param("memo") String memo);

    int doSumQuantity(@Param("id") Long id);

}
