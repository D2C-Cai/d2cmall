package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.TopCategory;
import com.d2c.product.query.TopCategorySearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopCategoryMapper extends SuperMapper<TopCategory> {

    List<TopCategory> findBySearch(@Param("searcher") TopCategorySearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") TopCategorySearcher searcher);

    List<TopCategory> findAll(@Param("status") Integer status);

    int updateStatus(@Param("id") Long id, @Param("status") int status);

    TopCategory findByCode(@Param("code") String code);

}