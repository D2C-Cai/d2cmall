package com.d2c.product.search.service;

import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherProductCategory;
import com.d2c.product.search.query.CategorySearchBean;
import com.d2c.product.search.support.ProductSearchHelp;

import java.util.List;

public interface ProductCategorySearcherService {

    public static final String TYPE_PRODUCTCATEGORY = "typeproductcategory";

    /**
     * 添加商品分类搜索数据
     *
     * @param category
     * @return
     */
    int insert(SearcherProductCategory category);

    /**
     * 通过id，得到商品分类搜索数据
     *
     * @param id
     * @return
     */
    SearcherProductCategory findById(String id);

    /**
     * 通过ids，得到商品筛选条件
     */
    List<ProductSearchHelp> findHelpByIds(String[] ids);

    /**
     * 通过搜索条件和分页，得到分类搜索的分页数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherProductCategory> search(CategorySearchBean searcher, PageModel page);

    /**
     * 更新商品分类搜索数据
     *
     * @param category
     * @return
     */
    int update(SearcherProductCategory category);

    /**
     * 重建商品分类搜索数据
     *
     * @param category
     * @return
     */
    int rebuild(SearcherProductCategory category);

    /**
     * 通过id，移除搜索数据
     *
     * @param pcId
     * @return
     */
    int remove(Long pcId);

    /**
     * 清空索引
     */
    int removeAll();

    /**
     * 获取商品分类
     *
     * @param topId
     * @param parentId
     * @return
     */
    JSONArray findByTopId(Long topId, Long parentId);

}
