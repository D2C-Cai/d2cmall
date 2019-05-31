package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.MemberTagRelationDto;
import com.d2c.member.model.MemberTagRelation;
import com.d2c.member.query.MemberTagRelationSearcher;

import java.util.List;

public interface MemberTagRelationService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    MemberTagRelation findById(Long id);

    /**
     * 根据标签id查询
     *
     * @param tagId
     * @return
     */
    List<Long> findByTagId(Long tagId);

    /**
     * 根据用户id查询
     *
     * @param memberId
     * @return
     */
    List<MemberTagRelation> findByMemberId(Long memberId);

    /**
     * 根据searcher查询
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<MemberTagRelationDto> findBySearch(PageModel page, MemberTagRelationSearcher searcher);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    Integer countBySearch(MemberTagRelationSearcher searcher);

    /**
     * 根据searcher查询
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<MemberTagRelation> findSimpleBySearch(PageModel page, MemberTagRelationSearcher searcher);

    /**
     * 新增
     *
     * @param memberTagRelation
     * @return
     */
    MemberTagRelation insert(MemberTagRelation memberTagRelation);

    /**
     * 批量新增
     *
     * @param memberIds
     * @param tagIds
     * @return
     */
    int batchInsert(Long[] memberIds, Long[] tagIds);

    /**
     * 根据标签id删除
     *
     * @param tagId
     * @return
     */
    int deleteByTagId(Long tagId);

    /**
     * 根据ids删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids);

    /**
     * 根据用户id删除
     *
     * @param memberId
     * @return
     */
    int deleteByMemberId(Long memberId);

    /**
     * 根据标签id修改状态
     *
     * @param tagId
     * @param status
     * @return
     */
    int updateStatusByTagId(Long tagId, Integer status);

    /**
     * 覆盖保存
     *
     * @param memberTagRelation
     * @return
     */
    int doReplaceInto(MemberTagRelation memberTagRelation);

}
