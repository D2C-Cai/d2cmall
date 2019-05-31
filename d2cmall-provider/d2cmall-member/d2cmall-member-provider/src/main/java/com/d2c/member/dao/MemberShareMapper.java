package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberShare;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberShareMapper extends SuperMapper<MemberShare> {

    List<MemberShare> findByShareTagId(@Param("tagId") Long tagId, @Param("pager") PageModel pager);

    List<MemberShare> findMyFollows(@Param("memberId") Long memberInfoId, @Param("status") Integer status,
                                    @Param("pager") PageModel page);

    List<MemberShare> findBySearch(@Param("searcher") MemberShareSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") MemberShareSearcher searcher);

    int countByMemberId(@Param("memberId") Long memberId, @Param("status") Integer status);

    int countByProductId(@Param("productId") Long productId, @Param("status") Integer status);

    int countByShareTagId(Long tagId);

    int countByMyFollows(@Param("memberId") Long memberInfoId, @Param("status") Integer status);

    int getTotalLikesCount(@Param("memberId") Long memberId, @Param("status") Integer status);

    int deleteById(Long id);

    void updateColumn();

    int updateUrl(@Param("id") Long id, @Param("url") String url);

    int updateTop(@Param("id") Long id, @Param("status") Integer status);

    int updateTags(@Param("id") Long id, @Param("tags") String tags);

    int updatePic(@Param("id") Long id, @Param("pic") String pic);

    int updatePicTag(@Param("id") Long id, @Param("picTag") String picTag);

    int updateDesignerId(@Param("id") Long id, @Param("designerId") Long designerId, @Param("role") Integer role);

    int updateVideoById(@Param("id") Long id, @Param("video") String video);

    int updateBindStatus(@Param("id") Long id, @Param("productId") Long productId,
                         @Param("designerId") Long designerId);

    int updateLikesCount(@Param("id") Long id, @Param("likes") int likes);

    int updateCommentsCount(@Param("id") Long id, @Param("comments") int comments);

    int unBindDesigner(@Param("id") Long id);

    int unBindProduct(@Param("id") Long id);

    int doAccept(@Param("id") Long id, @Param("status") Integer status, @Param("lastModifyMan") String lastModifyMan);

    int doRefuse(@Param("id") Long shareId, @Param("refuseReason") String refuseReason,
                 @Param("lastModifyMan") String lastModifyMan);

    int doWatch(@Param("id") Long id);

    int doVerify(@Param("id") Long memberShareId, @Param("verifyMan") String verifyMan);

    int doCancelVerify(@Param("id") Long memberShareId, @Param("cancelMan") String cancelMan);

    int doCancelBind(@Param("id") Long id, @Param("type") String type);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

    int doMerge(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

    int doBindTopic(@Param("id") Long id, @Param("topicId") Long topicId, @Param("topicName") String topicName);

    int countByTopic(Long topicId);

    int updateRole(@Param("id") Long id, @Param("role") Integer role);

    int updateRoleByMemberId(@Param("memberId") Long memberId, @Param("role") Integer role);

    int doAddDownCount(Long id);

    int doAddShareCount(Long id);

}
