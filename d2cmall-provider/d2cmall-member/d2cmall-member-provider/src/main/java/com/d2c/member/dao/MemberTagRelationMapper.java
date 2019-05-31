package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberTagRelation;
import com.d2c.member.query.MemberTagRelationSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberTagRelationMapper extends SuperMapper<MemberTagRelation> {

    List<MemberTagRelation> findByMemberId(Long memberId);

    List<Long> findByTagId(Long tagId);

    List<MemberTagRelation> findBySearch(@Param("pager") PageModel page,
                                         @Param("searcher") MemberTagRelationSearcher searcher);

    int countBySearch(@Param("searcher") MemberTagRelationSearcher searcher);

    int deleteByTagId(Long tagId);

    int deleteByIds(@Param("ids") Long[] ids);

    int deleteByMemberId(Long memberId);

    int updateStatusByTagId(@Param("tagId") Long tagId, @Param("status") Integer status);

    int doReplaceInto(MemberTagRelation memberTagRelation);

}
