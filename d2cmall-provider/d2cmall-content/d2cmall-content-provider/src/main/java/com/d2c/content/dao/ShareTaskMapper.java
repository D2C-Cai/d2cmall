package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.ShareTask;
import com.d2c.content.query.ShareTaskSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuhua
 * @see 任务分享实例
 */
public interface ShareTaskMapper extends SuperMapper<ShareTask> {

    List<ShareTask> findBySearch(@Param("searcher") ShareTaskSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ShareTaskSearcher searcher);

    /**
     * 根据会员与分享定义，获取已分享任务
     */
    ShareTask findByMemberIdAndTaskDef(@Param("memberId") Long memberId, @Param("taskDefId") Long taskDefId);

    /**
     * 调用一次，执行一次,总积分未完成，可以更新grantPoint+1
     */
    int doClickSharePoint(Long id);

    /**
     * 更新为积分已赠送1
     */
    int doPointed(Long id);

    /**
     * 根据任务定义与状态获取下发已任务列表
     */
    List<ShareTask> findByDefIdAndStatus(@Param("taskDefId") Long taskDefId, @Param("status") int status,
                                         @Param("pager") PageModel pager);

    /**
     * 根据任务定义与状态获取下发已任务列表数量
     */
    int countByDefIdAndStatus(@Param("taskDefId") Long taskDefId, @Param("status") int status);

    ShareTask findByMemberIdAndTaskId(@Param("memberInfoId") Long memberInfoId, @Param("shareTaskId") Long shareTaskId);

    int updateShare(@Param("memberInfoId") Long memberInfoId, @Param("taskDefId") Long taskDefId);

    int updateClick(@Param("memberInfoId") Long memberInfoId, @Param("taskDefId") Long taskDefId);

    int doShare(@Param("shareTaskId") Long shareTaskId, @Param("luckyNum") String luckyNum);

}
