package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.Remind;
import com.d2c.logger.query.RemindSearcher;

import java.util.List;

public interface RemindService {

    /**
     * 通过提示id，查找出提示数据
     *
     * @param remindId
     * @return
     */
    Remind findById(Long remindId);

    /**
     * 通过会员id，查找出提示数据
     *
     * @param remindId
     * @return
     */
    Remind findByMemberId(Long memberId);

    /**
     * 通过查询条件和分页条件，查找出对应的短信提示的分页数据
     *
     * @param searcher 查询条件
     * @param page     分页条件爱您
     * @return
     */
    PageResult<Remind> findBySearcher(RemindSearcher searcher, PageModel page);

    /**
     * 通过查询条件，查找出符合条件的数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(RemindSearcher searcher);

    /**
     * 添加一个短息提示的实体类
     *
     * @param remind
     * @return
     */
    Remind insert(Remind remind);

    /**
     * 更新短信提示实体类
     *
     * @param remind
     * @return
     */
    int update(Remind remind);

    /**
     * 删除一个短息提示的实体类
     *
     * @param remind
     * @return
     */
    int delete(Long id);

    /**
     * 活动发送消息
     *
     * @param remindIds
     * @return
     */
    int doSendMsg(Long[] remindIds);

    /**
     * 发送邮件
     *
     * @param remindIds
     * @return
     */
    int doSendEmail(Long[] remindIds);

    /**
     * 查找源ID
     *
     * @param type   类型
     * @param sended 是否发送
     * @return
     */
    List<Long> findSourceIdByType(String type, Integer smsSend);

}
