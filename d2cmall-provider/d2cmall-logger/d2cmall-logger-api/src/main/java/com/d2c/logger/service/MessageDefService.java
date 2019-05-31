package com.d2c.logger.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.MessageDef;
import com.d2c.logger.query.MessageDefSearcher;

import java.util.List;

public interface MessageDefService {

    /**
     * 添加一个消息定义实体数据
     *
     * @param def
     * @return
     */
    MessageDef insert(MessageDef def);

    /**
     * 通过id，查找到消息定义实体类
     *
     * @param id
     * @return
     */
    MessageDef findById(Long id);

    /**
     * 通过查询条件，得到分页的消息定义
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<MessageDef> findBySearch(PageModel page, MessageDefSearcher searcher);

    /**
     * 更新消息定义实体类
     *
     * @param def
     * @return
     */
    int update(MessageDef def);

    /**
     * 查询简单的消息定义集合
     *
     * @param page
     * @param searcher
     * @return
     */
    List<HelpDTO> findHelpDtosBySearch(PageModel page, MessageDefSearcher searcher);

    /**
     * 根据ID 修改状态值
     *
     * @param id
     * @param i
     * @return
     */
    int updateStatusById(Long id, int status);

    /**
     * 记下发送延迟的时候的时间戳
     *
     * @param id
     * @param timestamp
     * @return
     */
    int updateDelayTime(Long id, Long timestamp);

}
