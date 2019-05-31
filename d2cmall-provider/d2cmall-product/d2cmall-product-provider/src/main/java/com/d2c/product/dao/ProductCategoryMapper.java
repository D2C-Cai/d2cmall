package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.ProductCategory;
import com.d2c.product.query.ProductCategorySearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper extends SuperMapper<ProductCategory> {

    List<ProductCategory> findBySearch(@Param("searcher") ProductCategorySearcher searcher,
                                       @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ProductCategorySearcher searcher);

    List<ProductCategory> findAll(@Param("status") Integer status);

    int updateStatus(@Param("id") Long id, @Param("status") int status);

    ProductCategory findByCode(@Param("code") String code);

    List<ProductCategory> findInSet(String path);

    int deleteByIdAndDepth(@Param("id") Long id, @Param("depth") int depth);

    int bindAttributeGroup(ProductCategory productCategory);

    int updatePathById(@Param("path") String path, @Param("id") Long id);

    List<ProductCategory> findByBottomId(Long categoryId);

    List<ProductCategory> findByTopId(Long topId);

}