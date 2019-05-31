package com.d2c.product.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.PromotionTag;
import com.d2c.product.query.PromotionTagSearcher;

import java.util.Date;

/**
 * 产品标签(product_tag)
 */
public interface PromotionTagService {

    /**
     * 根据id获取产品标签
     *
     * @param id 主键id
     * @return
     */
    PromotionTag findById(Long id);

    /**
     * 更新产品标签
     *
     * @param tag
     * @return
     */
    int update(PromotionTag tag);

    /**
     * 保存产品标签
     *
     * @param tag
     * @return
     */
    PromotionTag insert(PromotionTag tag);

    /**
     * 根据过滤条件，获取相应产品标签信息， 采用分页形式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<PromotionTag> findBySearch(PromotionTagSearcher searcher, PageModel page);

    /**
     * 删除活动标签
     *
     * @param id
     * @return
     */
    int delete(Long id);

    PageResult<HelpDTO> findBySearchForHelp(PromotionTagSearcher searcher, PageModel page);

    /**
     * 更新标签状态
     *
     * @param id     id
     * @param status 上下架状态
     * @return
     */
    int updateStatus(Long id, Integer status);

    int updateSort(Long id, Date sort);

}
