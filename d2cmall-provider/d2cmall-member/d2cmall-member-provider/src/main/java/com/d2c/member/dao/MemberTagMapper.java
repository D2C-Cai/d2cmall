package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberTag;
import com.d2c.member.query.MemberTagSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberTagMapper extends SuperMapper<MemberTag> {

    List<MemberTag> findBySearch(@Param("pager") PageModel page, @Param("searcher") MemberTagSearcher searcher);

    Integer countBySearch(@Param("searcher") MemberTagSearcher searcher);

    int deleteById(Long id);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int updateSort(@Param("id") Long id, @Param("sort") Integer sort);

}