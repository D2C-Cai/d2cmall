package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.SeriesDto;
import com.d2c.product.model.Series;
import com.d2c.product.query.SeriesSearcher;

import java.util.List;
import java.util.Map;

/**
 * 系列（Series）
 */
public interface SeriesService {

    /**
     * 根据id获取系列信息
     *
     * @param serieId
     * @return
     */
    Series findById(Long serieId);

    /**
     * 根据id获取系列信息
     *
     * @param serieIds
     * @return
     */
    List<Series> findByIds(List<Long> seriesIds);

    /**
     * 根据系列名称获取系列信息
     *
     * @param name 系列名称
     * @return
     */
    Series findByName(String name);

    /**
     * 从Series表中获取所有不重复的季节字段（season）
     *
     * @return
     */
    List<String> findSeason();

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SeriesDto> findBySearch(SeriesSearcher searcher, PageModel page);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @return
     */
    int countBySearch(SeriesSearcher searcher);

    /**
     * 保存设计师系列信息
     *
     * @param serie
     * @return
     */
    Series insert(Series serie);

    /**
     * 更新设计师系列信息
     *
     * @param serie
     * @return
     */
    int update(Series serie);

    /**
     * 根据id删除设计师系列信息
     *
     * @param id
     * @return
     */
    int delete(Long id, Long designerId, String operator);

    /**
     * 当系列排序时间更新，更新商品索引
     *
     * @param serie
     * @return
     */
    int updateSortTime(Series serie);

    /**
     * 根据品牌id更新品牌的设计风格
     *
     * @param brandId
     * @return
     */
    Map<String, String> findStyleAndPriceByBrand(Long brandId);

}
