package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.SalesPropertyGroup;
import com.d2c.product.query.SalesPropertyGroupSearcher;

public interface SalesPropertyGroupService {

    /**
     * 根据SizeGroupSearcher内过滤条件，查找对应尺码组信息 采用分页方式，以PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<SalesPropertyGroup> findBySearch(SalesPropertyGroupSearcher searcher, PageModel page);

    /**
     * 根据id查找相应尺码组
     *
     * @param id 主键id
     * @return
     */
    SalesPropertyGroup findById(Long id);

    /**
     * 保存尺码组
     *
     * @param salesPropertyGroup 尺码组
     * @return
     */
    SalesPropertyGroup insert(SalesPropertyGroup salesPropertyGroup);

    /**
     * 更新尺码组
     *
     * @param salesPropertyGroup 尺码组
     * @return
     */
    int update(SalesPropertyGroup salesPropertyGroup);

    /**
     * 更新尺码组上下架
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatus(Long id, Integer status);

}
