package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.CommentDto;
import com.d2c.member.model.Comment;
import com.d2c.member.query.CommentSearcher;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CommentService {

    /**
     * 通过id获取评论
     *
     * @param key
     * @return
     */
    Comment findById(Long key);

    /**
     * 根据评论id和用户id 获取用户对该评论的所有评论
     *
     * @param commentId
     * @param memeberId
     * @return
     */
    CommentDto findByIdAndMemberId(Long commentId, Long memeberId);

    /**
     * 根据商品id获取最新的3条评论
     *
     * @param productId
     */
    List<Comment> findTop3ByProductId(Long productId);

    /**
     * 查询评论列表
     *
     * @param searcher：评论内容
     * @param page：分页
     * @return
     */
    PageResult<CommentDto> findBySearcher(CommentSearcher searcher, PageModel page);

    /**
     * 查询评论列表
     *
     * @param searcher：评论内容
     * @param page：分页
     * @return
     */
    PageResult<Comment> findSimpleBySearcher(CommentSearcher searcher, PageModel page);

    /**
     * 查询评论总数
     *
     * @param searcher：评论的对象
     * @return
     */
    int countBySearcher(CommentSearcher searcher);

    /**
     * 查询商品总数
     *
     * @param productId：商品编号
     * @param verify：已通过审核的数量
     * @return
     */
    int countByProduct(Long productId, Boolean verify);

    /**
     * 得到已通过审核和为审核状态的数量
     *
     * @return
     */
    Map<String, Object> countGroupByStatus();

    /**
     * 根据时间查询总数
     *
     * @param startDate：开始时间
     * @param endDate：结束时间
     * @return
     */
    List<Map<String, Object>> findCountGroupByScore(String scoreKey, Date startDate, Date endDate);

    /**
     * 获取未通过评论的总数
     *
     * @return
     */
    int findUnVerifiedCount();

    /**
     * 添加评论
     *
     * @param comment：评论内容
     * @return
     */
    Comment insert(Comment comment);

    /**
     * 后台新增
     *
     * @param comment
     * @return
     */
    Comment insert4Back(Comment comment);

    /**
     * 删除评论
     *
     * @param id：主键ID
     * @return
     */
    int delete(Long productId, Long commentId);

    /**
     * 修改评论
     *
     * @param comment：评论的内容
     * @return
     */
    int update(Comment comment);

    /**
     * 修改状态
     *
     * @param commentId：评论ID
     * @param status：评论的状态（已通过，未通过）
     * @return
     */
    int updateStatus(Long commentId, Long productId, int status);

    /**
     * 批量置顶或者取消置顶
     *
     * @param ids
     * @param status
     * @return
     */
    int updateTop(Long[] ids, Integer status);

    /**
     * 更新买家秀Id
     *
     * @param id
     * @param shareId
     * @return
     */
    int updateShareId(Long id, Long shareId);

    /**
     * 合并评论数据
     *
     * @param memberSourceId：会员ID
     * @param memberTargetId：会员ID
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 更新视频地址
     *
     * @param id
     * @param video
     * @return
     */
    int updateVideoById(Long id, String video);

    /**
     * 更新图片
     *
     * @param id
     * @param pic
     * @return
     */
    int updatePic(Long id, String pic);

}
