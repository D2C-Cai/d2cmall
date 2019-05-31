package com.d2c.product.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.BrandTag;
import com.d2c.product.query.BrandTagSearcher;

import java.util.Date;
import java.util.List;

public interface BrandTagService {

    /**
     * 通过id获取设计师标签信息
     *
     * @param id
     * @return
     */
    BrandTag findById(Long id);

    /**
     * 根据设计师id获取该设计师对应的标签信息
     *
     * @param designerId
     * @return
     */
    List<BrandTag> findByDesignerId(Long designerId);

    /**
     * 根据标签名称模糊查询相应的标签信息， 采用分页方式，封装成PageResult对象返回。
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<BrandTag> findBySearch(BrandTagSearcher searcher, PageModel page);

    /**
     * 根据标签名称模糊查询相应的标签信息， 采用分页方式，封装成PageResult对象返回。提供给doHelp使用
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<HelpDTO> findBySearchForHelp(BrandTagSearcher searcher, PageModel page);

    /**
     * 查询同步的数据
     *
     * @param lastSysDate
     * @param page
     * @return
     */
    PageResult<BrandTag> findSynDesignerTags(Date lastSysDate, PageModel page);

    /**
     * 保存设计师标签信息
     *
     * @param tag
     * @return
     */
    BrandTag insert(BrandTag tag);

    /**
     * 更新设计师标签信息
     *
     * @param tag
     * @return
     */
    int update(BrandTag tag);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

    /**
     * 根据id删除设计标签信息
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 查找一条固定标签（用于热门）
     *
     * @return
     */
    BrandTag findFixedOne();

}
