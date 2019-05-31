package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.AppMenu;
import com.d2c.content.query.AppMenuSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppMenuMapper extends SuperMapper<AppMenu> {

    int countBySearcher(@Param("searcher") AppMenuSearcher searcher);

    List<AppMenu> findBySearcher(@Param("searcher") AppMenuSearcher searcher, @Param("pager") PageModel pager);

    int updateStatusById(@Param("id") Long id, @Param("status") Integer status, @Param("name") String name);

    int updateSortById(@Param("id") Long id, @Param("sort") Integer sort);

    List<AppMenu> findByStatus(@Param("status") Integer status, @Param("version") String version);

}
