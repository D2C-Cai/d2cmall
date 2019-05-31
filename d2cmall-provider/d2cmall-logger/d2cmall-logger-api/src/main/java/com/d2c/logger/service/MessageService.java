package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.Message;
import com.d2c.logger.model.MessageDef;
import com.d2c.logger.query.MessageSearcher;

import java.util.Date;
import java.util.List;

public interface MessageService {

    /**
     * 添加一条消息
     *
     * @param message
     * @return
     */
    Message insert(Message message);

    /**
     * 批量添加消息
     *
     * @param message
     * @param memberIds
     * @return
     */
    int doBatchInsert(Message message, Long[] memberIds);

    /**
     * 通过id，查找消息
     *
     * @param id
     * @return
     */
    Message findById(Long id);

    /**
     * 通过接收人ID，查询消息
     *
     * @param recId
     * @param status
     * @param page
     * @return
     */
    PageResult<Message> findByRecId(Long recId, Integer status, PageModel page);

    /**
     * 通过modifyDate，查找消息
     *
     * @param modifyDate
     * @param page
     * @return
     */
    PageResult<Long> findByDate(Date modifyDate, PageModel page);

    /**
     * 分页查找消息数据
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<Message> findBySearch(PageModel page, MessageSearcher searcher);

    /**
     * 通过接收人ID，查询消息
     *
     * @param recId
     * @param status
     * @return
     */
    int countByRecId(Long recId, Integer status);

    /**
     * 删除消息数据
     *
     * @param ids
     * @return
     */
    int delete(Long id);

    /**
     * 删除消息数据
     *
     * @param ids
     * @return
     */
    int deleteByIdAndMemberId(Long id, Long memberInfoId);

    /**
     * 改变消息状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Long recId, Integer status);

    /**
     * 根据用户标签发送消息
     *
     * @param def
     * @param memberIds
     * @param creator
     * @return
     */
    int sendMemberMsg(MessageDef def, Long[] memberIds, String creator, List<String> openIds);

    /**
     * 发送全站消息
     *
     * @param def
     * @param creator
     * @return
     */
    int sendGlobalMsg(MessageDef def, String creator, List<String> openIds);

    /**
     * 查询消息概要
     *
     * @param majorType
     * @return
     */
    int doReadMajor(Long memberId, Integer majorType);

    /**
     * 删除消息，(目前是删除超过一个月的消息)
     *
     * @return
     */
    int doDeleteExpire(Date date);

}
