package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.AppNavigation;
import com.d2c.content.query.AppNavigationSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppNavigationMapper extends SuperMapper<AppNavigation> {

    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("operator") String operator);

    List<AppNavigation> findAllEnable();

    int countBySearcher(@Param("searcher") AppNavigationSearcher searcher);

    List<AppNavigation> selectBySearcher(@Param("searcher") AppNavigationSearcher searcher,
                                         @Param("page") PageModel page);

    int updateBackColor(@Param("id") Long id, @Param("backColor") String backColor,
                        @Param("lastModifyMan") String lastModifyMan);

}
