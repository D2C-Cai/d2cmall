package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.MemberShareTag;
import com.d2c.member.query.MemberShareTagSearcher;

import java.util.List;

public interface MemberShareTagService {

    /**
     * 通过标签id，找出标签实体类数据
     *
     * @param id 买家秀标签id
     * @return
     */
    MemberShareTag findById(Long id);

    /**
     * 根据code查询
     *
     * @param code
     * @return
     */
    MemberShareTag findByCode(String code);

    /**
     * 通过买家秀id，得到买家秀当前所有的标签集合
     *
     * @param shareId 买家秀id
     * @return
     */
    List<MemberShareTag> findByMemberShareId(Long shareId);

    /**
     * 找出所有的买家秀标签集合
     *
     * @return
     */
    List<MemberShareTag> findAll();

    /**
     * 通过标签名字，和分页条件，查找出符合条件的买家秀标签的分页数据
     *
     * @param tagName 标签名字
     * @param page
     * @return
     */
    PageResult<MemberShareTag> findBySearch(MemberShareTagSearcher searcher, PageModel page);

    /**
     * 通过标签名字，查找近似的数据的数量
     *
     * @param searcher 标签名字
     * @return
     */
    int countBySearch(MemberShareTagSearcher searcher);

    /**
     * 添加一条买家秀标签的实体类数据
     *
     * @param memberShareTag
     * @return
     */
    MemberShareTag insert(MemberShareTag memberShareTag);

    /**
     * 物理删除指定的买家秀标签数据
     *
     * @param id 标签id
     * @return
     */
    int delete(Long id);

    /**
     * 更新买家秀标签的实体类数据
     *
     * @param memberShareTag
     * @return
     */
    int update(MemberShareTag memberShareTag);

    /**
     * 通过标签id，更新排序
     *
     * @param id   标签id
     * @param sort 排序的权重
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 买家秀标签上下架
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

}
