package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.TopicDto;
import com.d2c.member.model.Topic;
import com.d2c.member.query.TopicSearcher;

public interface TopicService {

    /**
     * 查询列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Topic> findBySearcher(TopicSearcher searcher, PageModel page);

    /**
     * 新增
     *
     * @param topic
     * @return
     */
    Topic insert(Topic topic);

    /**
     * 根据ID查找
     *
     * @param id
     * @return
     */
    Topic findById(Long id);

    /**
     * 更新
     *
     * @param topic
     * @return
     */
    int update(Topic topic);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 修改状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 更新置顶
     *
     * @param id
     * @param top
     * @return
     */
    int updateTop(Long id, Integer top);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 查询列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<TopicDto> findDtoBySearcher(TopicSearcher searcher, PageModel page);

}
