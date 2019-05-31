package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.MemberCollectionDto;
import com.d2c.member.model.MemberCollection;
import com.d2c.member.query.InterestSearcher;

public interface MemberCollectionService {

    /**
     * 通过查询条件，得到我收藏商品的分页数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<MemberCollectionDto> findBySearch(InterestSearcher searcher, PageModel page);

    /**
     * 通过会员id，得到
     *
     * @param id:
     * @param page
     * @return
     */
    PageResult<MemberCollection> findByMemberId(Long id, PageModel page);

    /**
     * 查询会员数量
     *
     * @param memberId：会员ID
     * @return
     */
    int countByMemberId(Long memberId);

    /**
     * 查询商品数量
     *
     * @param productId:商品编号
     * @return
     */
    int countByProductId(Long productId);

    /**
     * 收藏我的喜欢的商品
     *
     * @param memberCollection
     * @return
     */
    MemberCollection insert(MemberCollection memberCollection);

    /**
     * 取消我收藏的商品
     *
     * @param memberInfoId：会员ID
     * @param productId：商品ID
     * @return
     */
    int delete(Long memberInfoId, Long productId);

    /**
     * 查询会员喜欢的商品
     *
     * @param memberId：会员ID
     * @param productId：商品编号
     * @return
     */
    MemberCollection findByMemberAndProductId(Long memberId, Long productId);

    /**
     * 修改我喜欢的商品
     *
     * @param memberSourceId：
     * @param memberTargetId：会员ID
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

}
