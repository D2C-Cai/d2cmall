package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.CrmGroup;
import com.d2c.member.query.CrmGroupSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CrmGroupMapper extends SuperMapper<CrmGroup> {

    CrmGroup findByName(@Param("name") String name);

    List<CrmGroup> findBySearcher(@Param("searcher") CrmGroupSearcher searcher, @Param("page") PageModel page);

    Integer countBySearcher(@Param("searcher") CrmGroupSearcher searcher);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

}
