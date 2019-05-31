package com.d2c.content.dao;

import com.d2c.content.model.ArticleCategory;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleCategoryMapper extends SuperMapper<ArticleCategory> {

    List<ArticleCategory> findAll();

    int delete(Long id);

    int updateSort(@Param("id") Long id, @Param("orderList") Integer sort);

}
