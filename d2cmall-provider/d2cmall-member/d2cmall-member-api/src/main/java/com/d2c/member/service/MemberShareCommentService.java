package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberShareComment;
import com.d2c.member.query.MemberShareCommentSearcher;

public interface MemberShareCommentService {

    /**
     * 根据ID查询买家秀评论
     *
     * @param id
     * @return
     */
    MemberShareComment findById(Long id);

    /**
     * 根据买家秀id，获取到对应的评论分页数据
     *
     * @param page          分页
     * @param memberShareId 买家秀
     * @return
     */
    PageResult<MemberShareComment> findBySearcher(PageModel page, MemberShareCommentSearcher searcher);

    /**
     * 添加一条买家秀评论实体类，并且更新买家秀的评论数量，重建搜索数据
     *
     * @param memberShareComment
     * @return
     */
    MemberShareComment insert(MemberShareComment memberShareComment);

    /**
     * 物理删除买家秀评论，并且更新买家秀的评论数量，重建搜索数据
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 更新状态
     *
     * @param status
     * @param id
     * @param shareId
     * @return
     */
    int updateStatus(int status, Long id, Long shareId);

    /**
     * 买家秀评论提醒
     *
     * @param memberShareComment
     * @param ip
     */
    void doSendShareCommentMsg(MemberShareComment memberShareComment, String ip);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 评论点赞
     *
     * @param commentId
     */
    int doLike(Long commentId);

    /**
     * 设置为热门评论
     *
     * @param hot
     * @return
     */
    int updateHot(Long id, Integer hot);

    /**
     * 查询数量
     *
     * @param searcher
     * @return
     */
    Integer countBySearcher(MemberShareCommentSearcher searcher);

}
