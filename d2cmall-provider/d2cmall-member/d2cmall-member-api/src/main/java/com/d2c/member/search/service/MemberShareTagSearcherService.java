package com.d2c.member.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.search.model.SearcherMemberShareTag;

public interface MemberShareTagSearcherService {

    public static final String TYPE_MEMBERSHARE_TAG = "typemembersharetag";

    /**
     * 添加买家秀标签搜索数据
     *
     * @param tag
     * @return
     */
    int insert(SearcherMemberShareTag tag);

    /**
     * 通过id，查询买家秀标签搜索数据
     *
     * @param id
     * @return
     */
    SearcherMemberShareTag findById(String id);

    /**
     * 通过code查询
     *
     * @param code
     * @return
     */
    SearcherMemberShareTag findByCode(String code);

    /**
     * 查询 买家秀标签分页数据
     *
     * @param page
     * @return
     */
    PageResult<SearcherMemberShareTag> search(PageModel page);

    /**
     * 更新买家秀标签搜索数据
     *
     * @param tag
     * @return
     */
    int update(SearcherMemberShareTag tag);

    /**
     * 重建买家秀标签搜索数据
     *
     * @param tag
     * @return
     */
    int rebuild(SearcherMemberShareTag tag);

    /**
     * 通过标签id，移除买家秀标签搜索数据
     *
     * @param tagId
     * @return
     */
    int remove(Long tagId);

    /**
     * 清空索引
     */
    void removeAll();

}
