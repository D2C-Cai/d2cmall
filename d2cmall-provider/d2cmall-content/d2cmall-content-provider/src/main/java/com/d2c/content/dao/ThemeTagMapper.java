package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.ThemeTag;
import com.d2c.content.query.ThemeTagSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThemeTagMapper extends SuperMapper<ThemeTag> {

    List<ThemeTag> findBySearcher(@Param("searcher") ThemeTagSearcher searcher, @Param("page") PageModel page);

    Integer countBySearcher(@Param("searcher") ThemeTagSearcher searcher);

    ThemeTag findFixedOne();

    List<ThemeTag> findAll(@Param("type") String type);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int delete(Long id);

}
