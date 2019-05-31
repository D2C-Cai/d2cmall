package com.d2c.member.search.model;

import com.d2c.common.api.dto.PreUserDTO;

public class SearcherMemberArticle extends PreUserDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * 文章ID
     */
    private Long articleId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

}
