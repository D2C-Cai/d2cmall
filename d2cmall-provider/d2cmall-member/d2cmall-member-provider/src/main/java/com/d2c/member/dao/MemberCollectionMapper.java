package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberCollection;
import com.d2c.member.query.InterestSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberCollectionMapper extends SuperMapper<MemberCollection> {

    int countBySearch(@Param("searcher") InterestSearcher searcher);

    List<MemberCollection> findBySearch(@Param("searcher") InterestSearcher searcher, @Param("pager") PageModel page);

    int delete(@Param("memberId") Long memberId, @Param("productId") Long productId);

    int countByMemberId(Long memberId);

    List<MemberCollection> findByMemberId(@Param("memberId") Long memberId, @Param("pager") PageModel pager);

    int countByProductId(Long productId);

    MemberCollection findByMemberAndProductId(@Param("memberId") Long memberId, @Param("productId") Long productId);

    void updateColumn();

    int doMerge(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

}
