package com.d2c.product.search.service;

import com.d2c.product.search.model.SearcherSeries;

public interface SeriesSearcherService {

    public static final String TYPE_SERIES = "typeseries";

    /**
     * 添加关键字搜索数据
     *
     * @param searchSum
     * @return
     */
    int insert(SearcherSeries series);

    /**
     * 重建商品搜索数据
     *
     * @param product
     * @return
     */
    int rebuild(SearcherSeries series);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    SearcherSeries findById(String id);

    /**
     * 清空索引
     */
    void removeAll();

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int remove(Long id);

}
