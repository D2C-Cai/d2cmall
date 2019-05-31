package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.GuanyiStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuanyiStockMapper extends SuperMapper<GuanyiStock> {

    GuanyiStock findLast();

    List<GuanyiStock> findBySearcher(@Param("pager") PageModel page);

    Integer countBySearcher();

    int deleteIgnoreId(Long id);

    GuanyiStock findFirst();

}
