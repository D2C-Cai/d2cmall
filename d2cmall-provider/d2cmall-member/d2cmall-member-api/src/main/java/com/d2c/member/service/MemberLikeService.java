package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberLike;
import com.d2c.member.model.MemberShare;
import com.d2c.member.query.MemberLikeSearcher;

public interface MemberLikeService {

    /**
     * 查询我喜欢的买家秀
     *
     * @param id：主键
     * @param page：分页
     * @return
     */
    PageResult<MemberLike> findByMemberId(Long id, PageModel page);

    /**
     * 查询我关注的买家秀列表列表
     *
     * @param searcher：
     * @param page：分页
     * @return
     */
    PageResult<MemberLike> findBySearch(MemberLikeSearcher searcher, PageModel page);

    int countByMemberId(Long memberId);

    /**
     * 查询设计师列表
     *
     * @param memberLike：喜欢的设计师对象
     * @return
     */
    MemberLike insert(MemberLike memberLike);

    /**
     * 删除设计师
     *
     * @param memberInfoId：会员ID
     * @param shareId：设计师ID
     * @return
     */
    int delete(Long memberInfoId, Long shareId);

    /**
     * 查询会员列表
     *
     * @param memberInfoId：会员ID
     * @param shareId：设计师ID
     * @return
     */
    MemberLike findByMemberAndShareId(Long memberInfoId, Long shareId);

    /**
     * 查询设计师数量
     *
     * @param shareId
     * @return
     */
    int countByMeberShareId(Long shareId);

    /**
     * 查询我喜欢的设计师分页
     *
     * @param shareId
     * @param page
     * @return
     */
    PageResult<MemberLike> findByShareId(Long shareId, PageModel page);

    /**
     * 修改我喜欢的设计师
     *
     * @param memberSourceId
     * @param memberTargetId
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

    /**
     * 点赞提醒
     *
     * @param memberLike
     * @param memberShare
     * @param ip
     */
    void doSendLikeMsg(MemberLike memberLike, MemberShare memberShare, String ip);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

}
