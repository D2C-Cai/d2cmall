package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberLike;
import com.d2c.member.query.MemberLikeSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberLikeMapper extends SuperMapper<MemberLike> {

    int delete(@Param("memberId") Long memberId, @Param("shareId") Long shareId);

    int countByMemberId(Long memberId);

    List<MemberLike> findByMemberId(@Param("memberId") Long memberId, @Param("pager") PageModel pager);

    MemberLike findByMemberAndShareId(@Param("memberId") Long memberId, @Param("shareId") Long shareId);

    int countByMemberShareId(Long shareId);

    List<MemberLike> findByMemberShareId(@Param("shareId") Long shareId, @Param("pager") PageModel page);

    void updateColumn();

    int doMerge(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

    List<MemberLike> findBySearcher(@Param("searcher") MemberLikeSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") MemberLikeSearcher searcher);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

}
