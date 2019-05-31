package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.ShareTaskDto;
import com.d2c.content.model.ShareTask;
import com.d2c.content.query.ShareTaskSearcher;

public interface ShareTaskService {

    /**
     * 通过查询条件，和分页条件查找出符合条件的分享任务的分页数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ShareTaskDto> findBySearch(ShareTaskSearcher searcher, PageModel page);

    /**
     * 通过查询条件，和分页条件查找出符合条件的分享任务的分页数据
     *
     * @param searcher
     * @return
     */
    int countBySearch(ShareTaskSearcher searcher);

    /**
     * 任务定义id，查找出一条分享任务实体类
     *
     * @param id
     * @return
     */
    ShareTask findById(Long id);

    /**
     * 通过会员id，任务定义id，查找出一条分享任务实体类
     *
     * @param memberId
     * @param shareTaskId
     * @return
     */
    ShareTask findByMemberIdAndTaskDef(Long memberId, Long defId);

    /**
     * 更新分享任务被点击后，点击次数
     *
     * @param id
     * @param defId
     * @return
     */
    int updateClick(Long id, Long defId);

    /**
     * 更新分享次数
     *
     * @param memberInfoId
     * @param taskDefId
     * @return
     */
    int updateShare(Long memberInfoId, Long taskDefId);

    /**
     * 分享一次任务，添加一次分享次数,设置幸运码
     *
     * @param defId       任务定义id
     * @param shareTaskId 任务id
     * @return
     */
    int doShare(Long defId, Long shareTaskId);

    /**
     * 点击分享任务得到积分，并且更新任务定义的点击积分
     *
     * @param taskDefId 任务定义id
     * @param taskId    任务id
     * @return
     */
    int doClickSharePoint(Long taskDefId, Long taskId);

    /**
     * 插入一条分享任务实体类
     *
     * @param shareTask
     * @return
     */
    ShareTask insert(ShareTask shareTask);

}
