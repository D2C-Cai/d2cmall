package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherCommentReply;
import com.d2c.member.search.query.CommentSearchBean;

public interface CommentReplySearcherService {

    public static final String TYPE_COMMENTREPLY = "typecommentreply";

    /**
     * 添加一条评论回复搜索
     *
     * @param Comment
     * @return
     */
    int insert(SearcherCommentReply comment);

    /**
     * 通过id，得到评论回复搜索的记录
     *
     * @param id
     * @return
     */
    SearcherCommentReply findById(String id);

    /**
     * 通过搜索条件和分页，得到评论回复搜索的分页数据
     *
     * @param search
     * @param page
     * @return
     */
    PageResult<SearcherCommentReply> search(CommentSearchBean search, PageModel page);

    /**
     * 更新一条评论回复搜索
     *
     * @param Comment
     * @return
     */
    int update(SearcherCommentReply comment);

    /**
     * 重新建立评论回复搜索记录
     *
     * @param Comment
     * @return
     */
    int rebuild(SearcherCommentReply comment);

    /**
     * 通过id删除评论回复搜索的记录
     *
     * @param CommentId
     * @return
     */
    int remove(Long commentId);

    /**
     * 清空所有的评论回复搜索的数据
     */
    void removeAll();

}
