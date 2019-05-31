package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.NavigationItem;
import com.d2c.content.query.NavigationItemSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NavigationItemMapper extends SuperMapper<NavigationItem> {

    List<NavigationItem> findByNavId(Long navId);

    List<NavigationItem> findBySearch(@Param("searcher") NavigationItemSearcher searcher,
                                      @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") NavigationItemSearcher searcher);

    int doUp(Long id);

    int doDown(Long id);

    int delete(Long id);

}
