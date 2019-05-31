package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dto.FeedBackDto;
import com.d2c.content.model.FeedBack;
import com.d2c.content.query.FeedBackSearcher;

import java.util.List;
import java.util.Map;

public interface FeedBackService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    FeedBack findById(Long id);

    /**
     * 以FeedBackSearcher对象中包含的过滤条件从feedback表中获取所有符合条件的客户反馈信息， 放入PageResult内返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return PageResult<E>
     */
    PageResult<FeedBackDto> findBySearcher(FeedBackSearcher searcher, PageModel page);

    /**
     * 查询数量
     *
     * @return
     */
    Integer countBySearcher(FeedBackSearcher searcher);

    /**
     * 统计数量
     *
     * @param searcher
     * @return
     */
    List<Map<String, Object>> findCountGroupByType(FeedBackSearcher searcher);

    /**
     * 根据客户反馈id从feedback表中删除对应记录。
     *
     * @param id
     * @return int
     */
    int delete(Long id);

    /**
     * 以数组形式根据id，批量删除对应客户反馈信息
     *
     * @param ids
     * @return int
     */
    int deleteByIds(Long[] ids);

    /**
     * 插入客户反馈
     *
     * @param feedBack
     * @return
     */
    FeedBack insert(FeedBack feedBack);

    /**
     * 合并数据
     *
     * @param memberSourceId
     * @param memberTargetId
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

    /**
     * 回复
     *
     * @param id
     * @param status
     * @param reply
     * @return
     */
    int doReply(Long id, String reply, String replyMan);

    /**
     * 关闭
     *
     * @param id
     * @return
     */
    int doClose(Long id);

    /**
     * 查找版本号
     *
     * @return
     */
    List<String> findVersions();

    /**
     * 处理
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status, String meno);

}
