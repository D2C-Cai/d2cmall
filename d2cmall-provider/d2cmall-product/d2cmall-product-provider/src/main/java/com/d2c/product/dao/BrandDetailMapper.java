package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.BrandDetail;
import com.d2c.product.query.BrandDetailSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandDetailMapper extends SuperMapper<BrandDetail> {

    BrandDetail findByBrandId(Long brandId);

    List<BrandDetail> findBySearcher(@Param("searcher") BrandDetailSearcher searcher, @Param("pager") PageModel page);

    Integer countBySearcher(@Param("searcher") BrandDetailSearcher searcher);

}
