package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberShareComment;
import com.d2c.member.query.MemberShareCommentSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberShareCommentMapper extends SuperMapper<MemberShareComment> {

    List<MemberShareComment> findBySearcher(@Param("searcher") MemberShareCommentSearcher searcher,
                                            @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") MemberShareCommentSearcher searcher);

    void updateColumn();

    int updateStatus(@Param("status") int status, @Param("id") Long id);

    int deleteById(Long id);

    int deleteAllBySourceId(Long sourceId);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

    int doRefreshHeadPic4To(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                            @Param("nickName") String nickName);

    int doLike(Long id);

    int updateHot(@Param("id") Long id, @Param("hot") Integer hot);

}
