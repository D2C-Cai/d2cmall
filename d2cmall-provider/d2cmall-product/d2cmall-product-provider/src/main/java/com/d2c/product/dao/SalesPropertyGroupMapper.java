package com.d2c.product.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.product.model.SalesPropertyGroup;
import com.d2c.product.query.SalesPropertyGroupSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalesPropertyGroupMapper extends SuperMapper<SalesPropertyGroup> {

    List<SalesPropertyGroup> findBySearch(@Param("searcher") SalesPropertyGroupSearcher searcher,
                                          @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") SalesPropertyGroupSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    String findNameById(@Param("id") Long id);

}
