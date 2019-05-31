package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherMemberFollow;

import java.util.List;
import java.util.Map;

public interface MemberFollowSearcherService {

    public static final String TYPE_MEMBER_FOLLOW = "typememberfollow";

    /**
     * 添加我喜欢的设计师
     *
     * @param memberFollow
     * @return
     */
    int insert(SearcherMemberFollow searcherMemberFollow);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    SearcherMemberFollow findById(String id);

    /**
     * 根据ids查询
     *
     * @param id
     * @return
     */
    Map<Long, SearcherMemberFollow> findByIds(String[] ids);

    /**
     * 我喜欢的设计师分页
     *
     * @param fromId：会员ID
     * @param page
     * @return
     */
    PageResult<SearcherMemberFollow> findByFromId(Long fromId, PageModel page);

    /**
     * 查询会员数量
     *
     * @param fromId:会员ID
     * @return
     */
    int countByFromId(Long fromId);

    /**
     * 查询单个
     *
     * @param toId:关注者ID
     * @param friends:是否好友
     * @param page
     * @return
     */
    PageResult<SearcherMemberFollow> findByToId(Long toId, Integer friends, PageModel page);

    /**
     * 查询设计师数量
     *
     * @param toId：关注者ID
     * @param friends:是否好友
     * @return
     */
    int countByToId(Long toId, Integer friends);

    /**
     * 获取关注的会员ID
     *
     * @param fromId
     * @return
     */
    List<Long> findToIdByFromId(Long fromId);

    /**
     * 更新是否好友关系
     *
     * @param toId
     * @param fromId
     * @param i
     * @return
     */
    int updateFriends(Long toId, Long fromId, int i);

    /**
     * 重建索引
     *
     * @param searcherMemberFollow
     * @return
     */
    int rebuild(SearcherMemberFollow searcherMemberFollow);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic4From(Long memberInfoId, String headPic, String nickName);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic4To(Long memberInfoId, String headPic, String nickName);

    /**
     * 修改我喜欢的设计师
     *
     * @param memberSourceId：
     * @param memberTargetId：会员ID
     */
    void doMerge(Long memberSourceId, Long memberTargetId);

    /**
     * 根据id删除索引
     *
     * @param id
     * @return
     */
    int remove(String id);

    /**
     * 清空索引
     */
    void removeAll();

}
