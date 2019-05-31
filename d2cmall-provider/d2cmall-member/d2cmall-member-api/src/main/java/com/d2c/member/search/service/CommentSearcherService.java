package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherComment;
import com.d2c.member.search.query.CommentSearchBean;

import java.util.List;

public interface CommentSearcherService {

    public static final String TYPE_COMMENT = "typecomment";

    /**
     * 添加一条评论搜索
     *
     * @param Comment
     * @return
     */
    int insert(SearcherComment comment);

    /**
     * 通过id，得到评论搜索的记录
     *
     * @param id
     * @return
     */
    SearcherComment findById(String id);

    /**
     * 查询前三条评论
     *
     * @param productId
     * @return
     */
    List<SearcherComment> findTop3Pic(Long productId);

    /**
     * 查询组合商品评论
     *
     * @param ids
     * @param page
     * @return
     */
    PageResult<SearcherComment> findCombComment(long[] ids, PageModel page);

    /**
     * 通过查询评论数量
     *
     * @param productId
     * @return
     */
    int count(CommentSearchBean search);

    /**
     * 通过搜索条件和分页，得到评论搜索的分页数据
     *
     * @param search
     * @param page
     * @return
     */
    PageResult<SearcherComment> search(CommentSearchBean search, PageModel page);

    /**
     * 更新一条评论搜索
     *
     * @param Comment
     * @return
     */
    int update(SearcherComment comment);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 重新建立评论搜索记录
     *
     * @param Comment
     * @return
     */
    int rebuild(SearcherComment comment);

    /**
     * 通过id删除评论搜索的记录
     *
     * @param CommentId
     * @return
     */
    int remove(Long commentId);

    /**
     * 清空所有的评论搜索的数据
     */
    void removeAll();

}
