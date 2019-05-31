package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Designers;
import com.d2c.member.query.DesignersSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DesignersMapper extends SuperMapper<Designers> {

    Designers findByCode(@Param("code") String code);

    List<Designers> findBySearcher(@Param("page") PageModel page, @Param("searcher") DesignersSearcher searcher);

    int countBySearcher(@Param("searcher") DesignersSearcher searcher);

    int updateMemberInfo(@Param("id") Long id, @Param("memberId") Long memberId, @Param("loginCode") String loginCode);

    int cancelMemberInfo(@Param("memberId") Long memberId);

}
