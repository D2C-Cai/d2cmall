package com.d2c.product.search.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherTopCategory;
import com.d2c.product.search.query.CategorySearchBean;
import com.d2c.product.search.support.ProductSearchHelp;

import java.util.List;

public interface TopCategorySearcherService {

    public static final String TYPE_TOPCATEGORY = "typetopcategory";

    /**
     * 添加顶级类别搜索数据
     *
     * @param top
     * @return
     */
    int insert(SearcherTopCategory top);

    /**
     * 通过id，得到顶级搜索数据
     *
     * @param id
     * @return
     */
    SearcherTopCategory findById(String id);

    /**
     * 通过ids，得到商品筛选条件
     */
    List<ProductSearchHelp> findHelpByIds(String[] ids);

    /**
     * 分页查找顶级类别搜索数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherTopCategory> search(CategorySearchBean searcher, PageModel page);

    /**
     * 更新顶级类别搜索数据
     *
     * @param top
     * @return
     */
    int update(SearcherTopCategory top);

    /**
     * 重建顶级类别搜索数据
     *
     * @param top
     * @return
     */
    int rebuild(SearcherTopCategory top);

    /**
     * 通过Id，移除类别搜索数据
     *
     * @param topId
     * @return
     */
    int remove(Long topId);

    /**
     * 清空所有
     */
    int removeAll();

}
