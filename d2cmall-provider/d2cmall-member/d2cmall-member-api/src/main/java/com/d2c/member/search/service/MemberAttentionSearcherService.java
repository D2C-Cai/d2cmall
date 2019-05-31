package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherMemberAttention;

import java.util.List;
import java.util.Map;

public interface MemberAttentionSearcherService {

    public static final String TYPE_MEMBER_ATTENTION = "typememberattention";

    /**
     * 添加一条我关注的设计师搜索数据
     *
     * @param memberAttention
     * @return
     */
    int insert(SearcherMemberAttention memberAttention);

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    Map<Long, SearcherMemberAttention> findByIds(String[] ids);

    /**
     * 根据设计师ids分页查询
     *
     * @param designerIds
     * @param page
     * @return
     */
    PageResult<SearcherMemberAttention> findByDesignerIds(List<Long> designerIds, PageModel page);

    /**
     * 查询设计师关注的人
     *
     * @param designerId：设计师ID
     * @param page：分页
     * @return
     */
    PageResult<SearcherMemberAttention> findByDesignerId(Long designerId, PageModel page);

    /**
     * 查询设计师关注者数量
     *
     * @param designerId
     * @return
     */
    int coutByDesignerId(Long designerId);

    /**
     * 查询我关注的设计师
     *
     * @param id：主键ID
     * @param page：分页
     * @return
     */
    PageResult<SearcherMemberAttention> findByMemberId(Long id, PageModel page);

    /**
     * 根据memberId查询
     *
     * @param id
     * @return
     */
    int countByMemberId(Long memberId);

    /**
     * 根据会员id和设计师id查询数据
     *
     * @param memberInfoId
     * @param desigerId
     * @return
     */
    SearcherMemberAttention findByMemberAndDesignerId(Long memberInfoId, Long desigerId);

    /**
     * 重建索引
     *
     * @param memberAttention
     * @return
     */
    int rebuild(SearcherMemberAttention memberAttention);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 通过会员id和设计师id删除搜索数据
     *
     * @param memberInfoId
     * @param designerId
     * @return
     */
    int remove(Long memberInfoId, Long designerId);

    /**
     * 清空索引
     */
    void removeAll();

    /**
     * 热门品牌
     *
     * @param page
     * @return
     */
    List<SearcherMemberAttention> findHotBrand(PageModel page);

}
