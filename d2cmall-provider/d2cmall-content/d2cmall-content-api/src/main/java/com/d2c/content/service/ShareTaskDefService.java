package com.d2c.content.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.ShareTaskDefDto;
import com.d2c.content.model.ShareTaskDef;
import com.d2c.content.query.ShareTaskDefSearcher;

import java.util.List;

public interface ShareTaskDefService {

    /**
     * 通过状态，查找出对应的分享任务定义的集合
     *
     * @param status
     * @return
     */
    List<ShareTaskDefDto> findByStatus(int status);

    /**
     * 更新指定的分享定义id，已经启动，完成积分任务或者已经结束活动的定义，设置成已经完成状态
     *
     * @param id
     * @return
     */
    int updateCompletedStatus(Long id);

    /**
     * 通过分享定义的id，查找出对应的分享任务的定义实体类
     *
     * @param id
     * @return
     */
    ShareTaskDef findById(Long id);

    /**
     * 通过查询条件和分页条件，查找出分享任务定义的分页数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ShareTaskDefDto> findBySearch(ShareTaskDefSearcher searcher, PageModel page);

    /**
     * 添加一个分享任务的定义实体类
     *
     * @param shareTaskDef
     * @return
     */
    ShareTaskDef insert(ShareTaskDef shareTaskDef);

    /**
     * 更新分享任务的实体类
     *
     * @param shareTaskDef
     * @return
     */
    int update(ShareTaskDef shareTaskDef);

    /**
     * 启动发布指定的分享任务定义
     *
     * @param id 定义id
     * @return
     */
    int doPublish(Long id);

    /**
     * 关闭任务
     *
     * @param id 定义id
     * @return
     */
    int doClose(Long id);

    /**
     * 逻辑删除分享定义的数据，将状态改成已删除状态
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 将任务定义的数据设置成已经结算状态
     *
     * @param id
     * @return
     */
    int updateCalculationStatus(Long id);

    /**
     * 添加一次点击分享后，可以添加积分
     *
     * @param taskDefId
     * @return
     */
    int doClickSharePoint(Long taskDefId);

    /**
     * 通过查询条件，查找出简单封装的任务定义数据的集合
     *
     * @param searcher
     * @param page
     * @return
     */
    List<HelpDTO> findHelpDtosBySearch(ShareTaskDefSearcher searcher, PageModel page);

}
