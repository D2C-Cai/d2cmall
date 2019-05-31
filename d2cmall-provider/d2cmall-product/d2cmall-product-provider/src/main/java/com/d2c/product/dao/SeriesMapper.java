package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.Series;
import com.d2c.product.query.SeriesSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SeriesMapper extends SuperMapper<Series> {

    List<Series> findByIds(@Param("ids") List<Long> ids);

    Series findByName(@Param("name") String name);

    List<String> findSeason();

    List<Series> findBySearch(@Param("searcher") SeriesSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") SeriesSearcher searcher);

    int delete(Long serieId);

    Map<String, String> findStyleAndPriceByBrand(@Param("brandId") Long brandId);

}
