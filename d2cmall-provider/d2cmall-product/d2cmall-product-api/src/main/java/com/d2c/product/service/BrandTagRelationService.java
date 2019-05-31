package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.BrandTagRelationDto;
import com.d2c.product.query.BrandTagRelationSearcher;
import com.d2c.product.search.model.SearcherDesignerTagRelation;

public interface BrandTagRelationService {

    /**
     * 根据标签id查询
     *
     * @param tagId
     * @param page
     * @return
     */
    PageResult<SearcherDesignerTagRelation> findByTagId(Long tagId, PageModel page);

    /**
     * 根据过滤条件，获取相应产品标签信息， 采用分页形式，封装成PageResult对象返回。
     *
     * @param designerCode
     * @param searcher
     * @param page
     * @return
     */
    PageResult<BrandTagRelationDto> findDesignersByTagId(BrandTagRelationSearcher searcher, PageModel page);

    /**
     * 根据设计师id查询标签
     *
     * @param designerId
     * @param page
     * @return
     */
    PageResult<BrandTagRelationDto> findTagsByDesignerId(Long designerId, PageModel page);

    /**
     * 根据标签id以及设计师id更新排序。
     *
     * @param id
     * @param sort
     */
    void updateSort(Long id, Integer sort);

    /**
     * 将设计师对应的标签插入designer_tag_relation表中，插入前先按照设计师id将原有标签全部删除。
     *
     * @param designerId
     * @param tagIds
     * @return
     */
    int insert(Long designerId, Long[] tagIds, String operator);

    /**
     * 根据标签id以及设计师id将对应的数据删除。
     *
     * @param tagId
     * @param designerId
     * @return
     */
    int deleteByTagIdAndDesignerId(Long tagId, Long designerId, String operator);

}
