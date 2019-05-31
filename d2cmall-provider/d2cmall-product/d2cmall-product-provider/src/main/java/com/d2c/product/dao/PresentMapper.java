package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.Present;
import com.d2c.product.query.PresentSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PresentMapper extends SuperMapper<Present> {

    int countBySearcher(@Param("searcher") PresentSearcher searcher);

    List<Present> findBySearcher(@Param("searcher") PresentSearcher searcher,
                                 @Param("pager") PageModel page);

    int updateSortById(@Param("id") Long id, @Param("sort") Integer sort, @Param("username") String username);

    int updateStatusById(@Param("id") Long id, @Param("status") Integer status, @Param("username") String username);

    int deleteById(@Param("id") Long id, @Param("username") String username);

}
