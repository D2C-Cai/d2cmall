package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.PointProductDto;
import com.d2c.product.model.PointProduct;
import com.d2c.product.query.PointProductSearcher;

/**
 * 积分换商品
 *
 * @author wwn
 */
public interface PointProductService {

    /**
     * 新增
     *
     * @return
     */
    PointProduct insert(PointProduct pointProduct);

    /**
     * 分页查询列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<PointProduct> findBySearcher(PointProductSearcher searcher, PageModel page);

    /**
     * 分页查询列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<PointProductDto> findDto(PointProductSearcher searcher, PageModel page);

    /**
     * 状态
     *
     * @param id
     * @param mark
     * @param operator
     * @return
     */
    int updateMark(Long id, Integer mark, String operator);

    /**
     * 修改
     *
     * @param pointProduct
     * @return
     */
    int update(PointProduct pointProduct);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    PointProduct findById(Long id);

    /**
     * 更新商品的数量
     *
     * @param id
     * @param count
     * @return
     */
    int updateCount(Long id, int count);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @param username
     * @return
     */
    int updateSort(Long id, Integer sort, String username);

}
