package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.query.BrandCategorySearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandCategoryMapper extends SuperMapper<BrandCategory> {

    List<BrandCategory> findByType(String type);

    BrandCategory findByNameAndType(@Param("name") String name, @Param("type") String type);

    BrandCategory findByCodeAndType(@Param("code") String code, @Param("type") String type);

    List<BrandCategory> findBySearcher(@Param("searcher") BrandCategorySearcher searcher,
                                       @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") BrandCategorySearcher searcher);

    int updateSort(@Param("id") Long id, @Param("orderList") Integer orderList);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

}
