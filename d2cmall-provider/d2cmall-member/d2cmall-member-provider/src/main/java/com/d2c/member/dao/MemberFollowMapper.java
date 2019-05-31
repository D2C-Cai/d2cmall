package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberFollow;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberFollowMapper extends SuperMapper<MemberFollow> {

    int countByFromId(Long fromId);

    List<MemberFollow> findByFromId(@Param("fromId") Long fromId, @Param("pager") PageModel pager);

    int countByToId(@Param("toId") Long toId, @Param("friends") Integer friends);

    List<MemberFollow> findByToId(@Param("toId") Long toId, @Param("friends") Integer friends,
                                  @Param("pager") PageModel pager);

    int updateFriends(@Param("fromId") Long fromId, @Param("toId") Long toId, @Param("friends") int friends);

    MemberFollow findByFromIdAndToId(@Param("fromId") Long fromId, @Param("toId") Long toId);

    int delete(@Param("fromId") Long fromId, @Param("toId") Long toId);

    void updateColumn();

    int doMergeFrom(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

    int doMergeTo(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

    List<Long> findToIdByFromId(Long fromId);

    int countTotal();

    List<MemberFollow> findByPage(@Param("pager") PageModel page);

    int doRefreshHeadPic4From(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                              @Param("nickName") String nickName);

    int doRefreshHeadPic4To(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                            @Param("nickName") String nickName);

}
