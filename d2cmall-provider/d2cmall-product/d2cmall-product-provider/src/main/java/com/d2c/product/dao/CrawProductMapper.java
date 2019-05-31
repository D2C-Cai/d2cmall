package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.CrawProduct;
import com.d2c.product.query.CrawProductSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CrawProductMapper extends SuperMapper<CrawProduct> {

    int countBySearcher(@Param("searcher") CrawProductSearcher searcher);

    List<CrawProduct> findBySearcher(@Param("searcher") CrawProductSearcher searcher, @Param("pager") PageModel pager);

    List<CrawProduct> findByD2cProId(@Param("productId") Long productId);

    List<Long> countProIdsBySearcher(@Param("searcher") CrawProductSearcher searcher, @Param("pager") PageModel pager);

    void deleteByDesignerId(@Param("designerId") Long designerId);

}
