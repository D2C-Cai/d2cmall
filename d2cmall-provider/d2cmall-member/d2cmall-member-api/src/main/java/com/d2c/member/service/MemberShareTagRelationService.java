package com.d2c.member.service;

import com.d2c.member.model.MemberShareTagRelation;

public interface MemberShareTagRelationService {

    /**
     * 批量插入与买家秀关联的标签
     * <p>
     * 1.清除掉与买家秀关系的标签数据，并移除标签的搜索数据 2.批量插入买家秀标签关系数据，并且重建标签搜索数据 3.更新买家秀数据的关联标签数据
     *
     * @param shareId 买家秀id
     * @param tagIds  标签数组id
     * @return
     */
    int insert(Long shareId, Long[] tagIds);

    /**
     * 通过买家秀标签id，查找出对应的实体类
     *
     * @param relationId
     * @return
     */
    MemberShareTagRelation findById(Long relationId);

    /**
     * 通过标签Id，和买家秀id，删除对应的买家秀标签数据，并且移除搜索索引
     *
     * @param tagId   标签id
     * @param shareId 买家秀id
     * @return
     */
    int deleteByTagIdAndMemberShareId(Long tagId, Long shareId);

    /**
     * 更新买家秀标签与买家秀的排序号
     *
     * @param shareId 买家秀id
     * @param tagId   买家秀标签id
     * @param sort    排序号
     * @return
     */
    int updateSort(Long shareId, Long tagId, Integer sort);

}
