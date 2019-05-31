package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Comment;
import com.d2c.member.query.CommentSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CommentMapper extends SuperMapper<Comment> {

    Comment findById(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId);

    List<Comment> findTop3ByProductId(Long productId);

    List<Comment> findBySearcher(@Param("searcher") CommentSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") CommentSearcher searcher);

    int countByProduct(@Param("productId") Long productId, @Param("verified") Boolean verified);

    List<Map<String, Object>> findCountGroupByScore(@Param("scoreKey") String scoreKey,
                                                    @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Map<String, Object>> countGroupByStatus();

    void updateColumn();

    int updateStatus(@Param("commentId") Long commentId, @Param("status") int status);

    int updateTop(@Param("ids") Long[] ids, @Param("top") Integer top);

    int updateShareId(@Param("id") Long id, @Param("shareId") Long shareId);

    int updateVideoById(@Param("id") Long id, @Param("video") String video);

    int doMerge(@Param("sourceId") Long memberSourceId, @Param("targetId") Long memberTargetId);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

}
