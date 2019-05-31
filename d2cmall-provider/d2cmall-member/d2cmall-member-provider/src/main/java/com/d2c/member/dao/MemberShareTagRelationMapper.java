package com.d2c.member.dao;

import com.d2c.member.model.MemberShareTagRelation;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

public interface MemberShareTagRelationMapper extends SuperMapper<MemberShareTagRelation> {

    int insertNotNull(MemberShareTagRelation memberShareTagRelation);

    int deleteByTagIdAndMemberShareId(@Param("tagId") Long tagId, @Param("shareId") Long shareId);

    int deleteByTagId(Long tagId);

    int deleteByMemberShareId(Long shareId);

    int updateTop(@Param("shareId") Long shareId, @Param("status") Integer status);

    int updateSort(@Param("shareId") Long shareId, @Param("tagId") Long tagId, @Param("sort") Integer sort);

}
