package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherMemberLike;

import java.util.Map;

public interface MemberLikeSearcherService {

    public static final String TYPE_MEMBER_LIKE = "typememberlike";

    /**
     * 添加喜欢的买家秀的搜索数据
     *
     * @param memberCollection
     * @return
     */
    int insert(SearcherMemberLike memberLike);

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    Map<Long, SearcherMemberLike> findByIds(String[] ids);

    /**
     * 根据memberId分页查询
     *
     * @param id
     * @param page
     * @return
     */
    PageResult<SearcherMemberLike> findByMemberId(Long id, PageModel page);

    /**
     * 根据shareId分页查询
     *
     * @param shareId
     * @param page
     * @return
     */
    PageResult<SearcherMemberLike> findByShareId(Long shareId, PageModel page);

    /**
     * 根据memberId和shareId查询
     *
     * @param memberInfoId
     * @param shareId
     * @return
     */
    SearcherMemberLike findByMemberAndShareId(Long memberInfoId, Long shareId);

    /**
     * 重建索引
     *
     * @param memberLike
     * @return
     */
    int rebuild(SearcherMemberLike memberLike);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 根据会员id，买家秀id 删除我喜欢的买家秀的搜索数据
     *
     * @param memberId
     * @param productId
     * @return
     */
    int remove(Long memberId, Long shareId);

    /**
     * 清空索引
     */
    void removeAll();

}
