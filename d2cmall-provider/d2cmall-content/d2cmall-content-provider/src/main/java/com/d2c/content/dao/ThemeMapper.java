package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.Theme;
import com.d2c.content.query.ThemeSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThemeMapper extends SuperMapper<Theme> {

    List<Theme> findBySearcher(@Param("searcher") ThemeSearcher searcher, @Param("page") PageModel page);

    Integer countBySearcher(@Param("searcher") ThemeSearcher searcher);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int updateRecommend(@Param("id") Long id, @Param("recommend") Integer recommend);

    int updateTagId(@Param("id") Long id, @Param("tagId") Long tagId);

    int delete(Long id);

}
