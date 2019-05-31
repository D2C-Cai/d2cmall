package com.d2c.product.search.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherDesigner;
import com.d2c.product.search.query.DesignerSearchBean;
import com.d2c.product.search.support.ProductSearchHelp;

import java.util.List;
import java.util.Map;

public interface DesignerSearcherService {

    public static final String TYPE_DESIGNER = "typedesigner";

    /**
     * 添加一条设计师搜索
     *
     * @param designer
     * @return
     */
    int insert(SearcherDesigner designer);

    /**
     * 通过id，得到设计师搜索的记录
     *
     * @param id
     * @return
     */
    SearcherDesigner findById(String id);

    /**
     * 通过ids，得到设计师搜索的记录
     */
    Map<Long, SearcherDesigner> findMapByIds(String[] ids, DesignerSearchBean beanSearcher);

    /**
     * 通过ids，得到商品筛选条件
     */
    List<ProductSearchHelp> findHelpByIds(String[] ids);

    /**
     * 通过搜索条件和分页，得到设计师搜索的分页数据
     *
     * @param search
     * @param page
     * @return
     */
    PageResult<SearcherDesigner> search(DesignerSearchBean search, PageModel page);

    /**
     * 更新一条设计师搜索
     *
     * @param designer
     * @return
     */
    int update(SearcherDesigner designer);

    /**
     * 更新关注人数
     *
     * @param designerId
     * @param fans
     * @return
     */
    int updateFans(Long designerId, int fans);

    /**
     * 重新建立设计师搜索记录
     *
     * @param designer
     * @return
     */
    int rebuild(SearcherDesigner designer);

    /**
     * 通过id删除设计师搜索的记录
     *
     * @param designerId
     * @return
     */
    int remove(Long designerId);

    /**
     * 清空所有的设计师搜索的数据
     */
    void removeAll();

    /**
     * 设计师A-Z排序
     *
     * @return
     */
    Map<String, List<JSONObject>> findGroupLetter();

    /**
     * 更新品牌销量
     *
     * @param id
     * @param sales
     * @return
     */
    int updateSales(Long id, Integer sales);

    /**
     * 更新风格和价格段
     *
     * @param brandId
     * @param style
     * @param price
     * @return
     */
    int updateStyleAndPrice(Long brandId, String style, String price);

}
