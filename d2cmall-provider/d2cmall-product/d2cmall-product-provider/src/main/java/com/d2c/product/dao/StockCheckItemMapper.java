package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.StockCheckItem;
import com.d2c.product.query.StockCheckItemSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockCheckItemMapper extends SuperMapper<StockCheckItem> {

    StockCheckItem findOne(@Param("checkId") Long checkId, @Param("barCode") String barCode);

    List<StockCheckItem> findBySearch(@Param("searcher") StockCheckItemSearcher searcher,
                                      @Param("pager") PageModel page);

    int countBySearch(@Param("searcher") StockCheckItemSearcher searcher);

    int doInit(@Param("storeCode") String storeCode, @Param("checkId") Long checkId);

    int deleteByCheckId(@Param("checkId") Long checkId);

}
