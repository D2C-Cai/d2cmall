package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.query.MemberShareSearchBean;
import com.d2c.member.search.support.MemberShareHelp;

import java.util.List;

public interface MemberShareSearcherService {

    public static final String TYPE_MEMBERSHARE = "typemembershare";

    /**
     * 添加一条买家秀搜索数据
     *
     * @param memberShare
     * @return
     */
    int insert(SearcherMemberShare memberShare);

    /**
     * 通过id，查找买家秀搜索数据
     *
     * @param id
     * @return
     */
    SearcherMemberShare findById(String id);

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    List<SearcherMemberShare> findByIds(String[] ids);

    /**
     * 根据用户ids分页查询
     *
     * @param memberIds
     * @param page
     * @return
     */
    PageResult<SearcherMemberShare> findByMemberIds(List<Long> memberIds, PageModel page, Integer status);

    /**
     * 分页查询买家秀搜索数据
     *
     * @param search
     * @param page
     * @return
     */
    PageResult<SearcherMemberShare> search(MemberShareSearchBean search, PageModel page);

    /**
     * 买家秀搜索数量
     *
     * @param search
     * @param page
     * @return
     */
    int count(MemberShareSearchBean search);

    /**
     * 更新一条买家秀搜索数据
     *
     * @param memberShare
     * @return
     */
    int update(SearcherMemberShare memberShare);

    /**
     * 重建买家秀搜索数据
     *
     * @param memberShare
     * @return
     */
    int rebuild(SearcherMemberShare memberShare);

    /**
     * 根据会员id，买家秀id 删除我喜欢的买家秀的搜索数据
     *
     * @param memberId
     * @param productId
     * @return
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 移除一条买家秀搜索数据
     *
     * @param memberShareId
     * @return
     */
    int remove(Long memberShareId);

    /**
     * 清空索引
     */
    void removeAll();

    /**
     * 更新观看人次+1
     *
     * @param id
     * @return
     */
    int doWatched(Long id);

    /**
     * 查询热门买家秀
     *
     * @param type
     * @return
     */
    PageResult<SearcherMemberShare> findHotShare(String type, PageModel page);

    /**
     * 查找 热门会员
     *
     * @param type
     * @param page
     * @return
     */
    List<MemberShareHelp> findHotMember(String type, PageModel page);

    /**
     * 活跃用户<br>
     *
     * @param type
     * @return
     */
    List<MemberShareHelp> findMemberOrderByCount(String type, Integer pageSize);

    /**
     * 买家的最新一条买家秀
     *
     * @param memberId
     * @return
     */
    SearcherMemberShare findLastedByMemberId(Long memberId);

    /**
     * 查询某话题下买家秀数量
     *
     * @param topicId
     * @return
     */
    Long countByTopicId(Long topicId);

    /**
     * 更新买家秀标示
     *
     * @param id
     * @param role
     * @return
     */
    int updateRoleByMemberId(Long memberId, Integer role);

}
