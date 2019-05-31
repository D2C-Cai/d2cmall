package com.d2c.logger.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.search.model.SearcherMessage;
import com.d2c.logger.search.support.MessageReadTimeHelp;
import com.d2c.logger.search.support.MessageSearchHelp;

import java.util.Date;
import java.util.List;

public interface MessageSearcherService {

    public static final String TYPE_MESSAGE = "typemessage";

    /**
     * 添加一条消息搜索
     *
     * @param designer
     * @return
     */
    int insert(SearcherMessage message);

    /**
     * 查询单个
     *
     * @param id
     * @return
     */
    SearcherMessage findById(String id);

    /**
     * 通过搜索条件和分页，得到消息搜索的分页数据
     *
     * @param 消息接收人ID
     * @param page
     * @return
     */
    PageResult<SearcherMessage> search(Long recId, Long majorType, PageModel page);

    /**
     * 查询不同状态的数量
     *
     * @param recId
     * @param status
     * @return
     */
    int count(Long recId, Integer status, MessageReadTimeHelp readTime);

    /**
     * 通过搜索条件和分页，得到消息搜索的分页数据
     *
     * @param 消息接收人ID
     * @param page
     * @return
     */
    PageResult<SearcherMessage> searchByType(Long recId, long[] types, PageModel page);

    /**
     * 查询小类的未读数量
     *
     * @param recId
     * @param types
     * @return
     */
    int countUnReadByType(Long recId, long[] types);

    /**
     * 查询消息总览
     *
     * @param recId
     * @return
     */
    List<MessageSearchHelp> searchGroupByMajorType(Long recId, MessageReadTimeHelp readTime);

    /**
     * 更新一条消息搜索
     *
     * @param designer
     * @return
     */
    int updateStatus(Long messageId, Integer status);

    /**
     * 重建索引
     *
     * @param message
     * @return
     */
    int rebuild(SearcherMessage message);

    /**
     * 通过id删除消息搜索的记录
     *
     * @param designerId
     * @return
     */
    int remove(Long messageId);

    /**
     * 清空索引
     */
    void removeAll();

    /**
     * 批量读取大类下的消息
     *
     * @param memberId
     * @param majorType
     */
    int doReadByMajor(Long memberId, Integer majorType);

    /**
     * 删除超过一定时间的消息
     *
     * @param date
     * @return
     */
    int doDeleteExpire(Date date);

}
