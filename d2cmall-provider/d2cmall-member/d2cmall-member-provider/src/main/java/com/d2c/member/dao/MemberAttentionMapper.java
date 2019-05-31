package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberAttention;
import com.d2c.member.query.InterestSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberAttentionMapper extends SuperMapper<MemberAttention> {

    int countBySearch(@Param("searcher") InterestSearcher searcher);

    List<MemberAttention> findBySearch(@Param("searcher") InterestSearcher searcher, @Param("pager") PageModel page);

    int delete(@Param("memberId") Long memberId, @Param("designerId") Long designerId);

    int countByMemberId(Long memberId);

    List<MemberAttention> findByMemberId(@Param("memberId") Long memberId, @Param("pager") PageModel pager);

    int countByDesignerId(Long designerId);

    List<MemberAttention> findByDesignerId(@Param("designerId") Long designerId, @Param("pager") PageModel page);

    MemberAttention findByMemberAndDesignerId(@Param("memberId") Long memberId, @Param("designerId") Long designerId);

    void updateColumn();

    int doMerge(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

}
