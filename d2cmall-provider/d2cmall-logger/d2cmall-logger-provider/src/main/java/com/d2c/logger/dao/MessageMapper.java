package com.d2c.logger.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.logger.model.Message;
import com.d2c.logger.query.MessageSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MessageMapper extends SuperMapper<Message> {

    List<Message> findBySearch(@Param("pager") PageModel page, @Param("searcher") MessageSearcher searcher);

    int countBySearch(@Param("searcher") MessageSearcher searcher);

    int doBatchDelete(@Param("ids") Long[] ids);

    List<Message> findByRecId(@Param("recId") Long recId, @Param("status") Integer status,
                              @Param("page") PageModel page);

    int countByIds(@Param("ids") Long[] ids);

    int countByRecId(@Param("recId") Long recId, @Param("status") Integer status);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    int delete(Long id);

    int deleteByIdAndMemberId(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId);

    List<Long> findByDate(@Param("date") Date modifyDate, @Param("page") PageModel page);

    int countByDate(@Param("date") Date modifyDate);

    int doReplaceInto(Message message);

    List<Long> findIdsByRecId(@Param("memberIds") Long[] memberIds, @Param("defId") Long defId);

    int doReadMajor(@Param("memberId") Long memberId, @Param("majorType") Integer majorType);

    int doDeleteExpire(Date date);

}
