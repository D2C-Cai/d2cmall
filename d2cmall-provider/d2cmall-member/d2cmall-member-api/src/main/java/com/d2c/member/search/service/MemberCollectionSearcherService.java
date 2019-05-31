package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherMemberCollection;

import java.util.List;
import java.util.Map;

public interface MemberCollectionSearcherService {

    public static final String TYPE_MEMBER_COLLECTION = "typemembercollection";

    /**
     * 添加收藏商品的搜索数据
     *
     * @param memberCollection
     * @return
     */
    int insert(SearcherMemberCollection memberCollection);

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    Map<Long, SearcherMemberCollection> findByIds(String[] ids);

    /**
     * 查找收藏某商品的用户集合
     *
     * @param productId
     * @return
     */
    List<Long> findByProductIds(Long productId);

    /**
     * 根据memberId查询
     *
     * @param id
     * @param page
     * @return
     */
    PageResult<SearcherMemberCollection> findByMemberId(Long id, PageModel page);

    /**
     * 根据memberId查询商品ProductId
     */
    List<Long> findProductIdsInCollection(Long memberId, PageModel page);

    /**
     * 根据memberId查询
     *
     * @param id
     * @return
     */
    int countByMemberId(Long memberId);

    /**
     * 根据memberId和productId查询
     *
     * @param memberInfoId
     * @param productId
     * @return
     */
    SearcherMemberCollection findByMemberAndProductId(Long memberInfoId, Long productId);

    /**
     * 重建索引
     *
     * @param memberCollection
     * @return
     */
    int rebuild(SearcherMemberCollection memberCollection);

    /**
     * 通过会员id和设计师id删除搜索数据
     *
     * @param memberInfoId
     * @param designerId
     * @return
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 根据会员id，商品id 删除收藏商品的搜索数据
     *
     * @param memberId
     * @param productId
     * @return
     */
    int remove(Long memberId, Long productId);

    /**
     * 清空索引
     */
    void removeAll();

}
