package com.d2c.content.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.model.ShareTaskDef;
import com.d2c.content.query.ShareTaskDefSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xuhua
 * @see 任务定义
 */
public interface ShareTaskDefMapper extends SuperMapper<ShareTaskDef> {

    List<ShareTaskDef> findBySearch(@Param("searcher") ShareTaskDefSearcher searcher, @Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") ShareTaskDefSearcher searcher);

    /**
     * 发布任务
     */
    int publish(Long taskDefId);

    /**
     * 关闭任务
     */
    int doClose(Long taskDefId);

    /**
     * 已发布状态，在有效时间内，总积分未完成，可以更新grantPoint+1
     */
    int doClickSharePoint(Long taskDefId);

    /**
     * 总积分完成或任务到期，更新状态为已完成
     */
    int updateCompletedStatus(Long taskDefId);

    /**
     * 获取定义
     */
    List<ShareTaskDef> findByStatus(int status);

    /**
     * 更新状态
     */
    int updateStatus(@Param("taskDefId") Long taskDefId, @Param("status") int status);

}
