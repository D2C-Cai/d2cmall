package com.d2c.product.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.ProductTag;
import com.d2c.product.query.ProductTagSearcher;

import java.util.Date;
import java.util.List;

/**
 * 产品标签(product_tag)
 */
public interface ProductTagService {

    /**
     * 根据id删除对应产品标签
     *
     * @param id 主键id
     * @return
     */
    int delete(Long id);

    /**
     * 根据id获取产品标签
     *
     * @param id 主键id
     * @return
     */
    ProductTag findById(Long id);

    /**
     * 更新产品标签
     *
     * @param tag
     * @return
     */
    int update(ProductTag tag);

    /**
     * 保存产品标签
     *
     * @param tag
     * @return
     */
    ProductTag insert(ProductTag tag);

    /**
     * 根据商品id获取该商品的所有标签信息
     *
     * @param productId 产品id
     * @return
     */
    List<ProductTag> findByProductId(Long productId);

    /**
     * 根据过滤条件，获取相应产品标签信息， 采用分页形式，以列表形式返回数据，提供给doHelp使用
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    List<HelpDTO> findBySearchForHelp(ProductTagSearcher searcher, PageModel page);

    /**
     * 根据过滤条件，获取相应产品标签信息， 采用分页形式，封装成PageResult对象返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<ProductTag> findBySearch(ProductTagSearcher searcher, PageModel page);

    int updateStatus(Long id, Integer status);

    int updateSort(Long id, Integer sort);

    PageResult<ProductTag> findSynProductTags(Date lastSysDate, PageModel page);

    /**
     * 按type查询
     *
     * @param type
     * @return
     */
    List<ProductTag> findByType(String type);

    /**
     * 根据id获取产品标签
     *
     * @param ids
     * @return
     */
    List<ProductTag> findByIds(Long[] ids);

    ProductTag findOneFixByType(String type);

}
