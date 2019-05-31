package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.MemberAttentionDto;
import com.d2c.member.model.MemberAttention;
import com.d2c.member.query.InterestSearcher;

public interface MemberAttentionService {

    /**
     * 查询我关注的设计师列表
     *
     * @param searcher：设计师对象查询条件
     * @param page：分页
     * @return
     */
    PageResult<MemberAttentionDto> findBySearch(InterestSearcher searcher, PageModel page);

    /**
     * 查询我关注的设计师
     *
     * @param id：主键ID
     * @param page：分页
     * @return
     */
    PageResult<MemberAttention> findByMemberId(Long id, PageModel page);

    int countByMemberId(Long memberId);

    /**
     * 查询设计师关注的人
     *
     * @param designerId：设计师ID
     * @param page：分页
     * @return
     */
    PageResult<MemberAttention> findByDesignerId(Long designerId, PageModel page);

    /**
     * 添加我关注的设计师
     *
     * @param memberAttention：设计师对象
     * @return
     */
    MemberAttention insert(MemberAttention memberAttention);

    /**
     * 取消关注的设计师
     *
     * @param memberInfoId：关注的设计师ID
     * @param designerId：设计师的ID
     * @return
     */
    int delete(Long memberInfoId, Long designerId);

    /**
     * 查询
     *
     * @param memberInfoId：会员ID
     * @param designerId：设计师ID
     * @return
     */
    MemberAttention findByMemberAndDesignerId(Long memberInfoId, Long designerId);

    int doMerge(Long memberSourceId, Long memberTargetId);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

}
