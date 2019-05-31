package com.d2c.member.search.service;

import com.d2c.member.search.model.SearcherMemberArticle;

import java.util.Map;

public interface MemberArticleSearcherService {

    public static final String TYPE_MEMBER_ARTICLE = "typememberarticle";

    /**
     * 添加一条
     *
     * @param memberarticle
     * @return
     */
    int insert(SearcherMemberArticle memberarticle);

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    Map<Long, SearcherMemberArticle> findByIds(String[] ids);

    /**
     * 通过会员id和文章id删除
     *
     * @param memberInfoId
     * @param articleId
     * @return
     */
    int remove(Long memberInfoId, Long articleId);

    /**
     * 清空索引
     */
    void removeAll();

}
