package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberFollow;

import java.util.List;

/**
 * 喜欢的设计师
 *
 * @author Administrator
 */
public interface MemberFollowService {

    /**
     * 添加我喜欢的设计师
     *
     * @param memberFollow
     * @return
     */
    int insert(MemberFollow memberFollow);

    /**
     * 查询会员数量
     *
     * @param fromId:会员ID
     * @return
     */
    int countByFromId(Long fromId);

    /**
     * 我喜欢的设计师分页
     *
     * @param fromId：会员ID
     * @param page
     * @return
     */
    PageResult<MemberFollow> findByFromId(Long fromId, PageModel page);

    /**
     * 查询设计师数量
     *
     * @param toId：关注者ID
     * @param friends:是否好友
     * @return
     */
    int countByToId(Long toId, Integer friends);

    /**
     * 查询单个
     *
     * @param toId:关注者ID
     * @param friends:是否好友
     * @param page
     * @return
     */
    PageResult<MemberFollow> findByToId(Long toId, Integer friends, PageModel page);

    /**
     * 取消喜欢设计师
     *
     * @param fromId:会员ID
     * @param toId:关注者ID
     * @return
     */
    int delete(Long fromId, Long toId);

    /**
     * 修改我喜欢的设计师
     *
     * @param memberSourceId：
     * @param memberTargetId：会员ID
     */
    void doMerge(Long memberSourceId, Long memberTargetId);

    /**
     * 获取关注的会员ID
     *
     * @param fromId
     * @return
     */
    List<Long> findToIdByFromId(Long fromId);

    /**
     * 关注提醒
     *
     * @param memberId
     * @param memberFollow
     * @param ip
     */
    void doSendFollowMsg(Long memberId, MemberFollow memberFollow, String ip);

    PageResult<MemberFollow> findByPage(PageModel page);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

}
