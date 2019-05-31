package com.d2c.product.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherProductTag;

public interface ProductTagSearcherService {

    public static final String TYPE_PRODUCT_TAG = "typeproducttag";

    /**
     * 添加商品标签搜索数据
     *
     * @param tag
     * @return
     */
    int insert(SearcherProductTag tag);

    /**
     * 通过id，得到商品标签搜索数据
     *
     * @param id
     * @return
     */
    SearcherProductTag findById(String id);

    /**
     * 分页查询商品标签数据
     *
     * @param page
     * @return
     */
    PageResult<SearcherProductTag> search(PageModel page);

    /**
     * 更新商品标签搜索数据
     *
     * @param tag
     * @return
     */
    int update(SearcherProductTag tag);

    /**
     * 重建商品标签搜索数据
     *
     * @param tag
     * @return
     */
    int rebuild(SearcherProductTag tag);

    /**
     * 移除商品标签搜索数据
     *
     * @param tagId
     * @return
     */
    int remove(Long tagId);

    /**
     * 清空索引
     */
    void removeAll();

}
