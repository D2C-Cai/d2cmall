package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.SalesPropertyDto;
import com.d2c.product.model.SalesProperty;
import com.d2c.product.query.SalesPropertySearcher;

import java.util.List;

/**
 * 商品销售属性维护（salesproperty）
 */
public interface SalesPropertyService {

    /**
     * 根据SalesPropertySearcher内的过滤条件，获取对应商品销售属性信息 采用分页方式，以PageResult对象返回
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<SalesPropertyDto> findBySearch(SalesPropertySearcher searcher, PageModel page);

    /**
     * 根据id获取对应商品销售属性信息
     *
     * @param id 主键id
     * @return
     */
    SalesProperty findById(Long id);

    /**
     * 保存商品销售属性信息
     *
     * @param salesProperty
     * @return
     */
    SalesProperty insert(SalesProperty salesProperty);

    /**
     * 更新商品销售属性信息
     *
     * @param salesProperty
     * @return
     */
    int update(SalesProperty salesProperty);

    /**
     * 获取所有颜色信息
     *
     * @return
     */
    List<SalesProperty> findColors();

    /**
     * 获取所有尺码信息
     *
     * @return
     */
    List<SalesProperty> findSizes();

}
