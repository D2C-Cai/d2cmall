package com.d2c.product.search.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.dto.CountDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.model.SearcherRecProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.support.ProductSearchHelp;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductSearcherQueryService {

    /**
     * 商品搜索数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherProduct> search(ProductProSearchQuery searcher, PageModel page);

    /**
     * 商品搜索数量
     *
     * @param searcher
     * @return
     */
    int count(ProductProSearchQuery searcher);

    /**
     * 直播推荐商品查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<SearcherRecProduct> searchRec(ProductProSearchQuery searcher, PageModel page);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    SearcherProduct findById(String id);

    /**
     * 根据ids查询
     *
     * @param ids
     * @param mark
     * @return
     */
    List<SearcherProduct> findByIds(List<String> ids, Integer mark);

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    Map<Long, SearcherProduct> findMapByIds(String[] ids);

    /**
     * 查询直播推荐商品
     *
     * @param liveId
     * @param productId
     * @return
     */
    SearcherRecProduct findRecById(Long liveId, Long productId);

    /**
     * 分页不排序查询
     *
     * @param page
     * @param limit
     * @return
     */
    List<SearcherProduct> findByPage(int page, int limit);

    /**
     * 获取推荐值最高的商品
     *
     * @param limit
     * @return
     */
    List<SearcherProduct> findTopRecom(int limit);

    /**
     * 相似商品对比数据
     *
     * @param topId
     * @param page
     * @param limit
     * @return
     */
    List<SearcherProduct> findSimilarTargets(Long topId, int page, int limit);

    /**
     * 根据品牌id聚合出上架商品的系列
     *
     * @param designerId
     * @return
     */
    List<Long> findFactSeries(Long designerId);

    /**
     * PC商品列表左边栏
     *
     * @param searcher
     * @param parentId
     * @param topCategoryId
     * @return
     */
    Map<String, List<CountDTO<?>>> findFactDesignerAndCategory(ProductProSearchQuery searcher, Long parentId,
                                                               Long topCategoryId);

    /**
     * 店铺商品列表
     *
     * @param designerId
     * @param page
     * @param searcherBean
     * @return
     */
    PageResult<SearcherProduct> findSaleProductByDesigner(Long designerId, PageModel page,
                                                          ProductProSearchQuery searcherBean);

    /**
     * 店铺商品数量
     *
     * @param designerId
     * @return
     */
    int countSaleProductByDesigner(Long designerId);

    /**
     * 顶级类目下，推荐商品列表，包括不可查询，已下架商品
     *
     * @param topId
     * @param afterModifyDate
     * @param page
     * @param limit
     * @return
     */
    List<SearcherProduct> findProductByTopCategory(Long topId, Date afterModifyDate, int page, int limit);

    /**
     * 根据关键字聚合 搜索帮助
     *
     * @param searcher
     * @param params
     * @return
     */
    Map<String, List<ProductSearchHelp>> findProductSearchHelp(ProductProSearchQuery searcher, String[] params);

    /**
     * 商品筛选服务
     *
     * @param searcher
     * @return
     */
    JSONObject filterService(ProductProSearchQuery searcher);

    /**
     * 商品筛选属性
     *
     * @param searcher
     * @return
     */
    JSONObject filterProperties(ProductProSearchQuery searcher);

    /**
     * 筛选列表的品牌
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<JSONObject> brandList(ProductProSearchQuery searcher, PageModel page);

    /**
     * 筛选列表的系列
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<JSONObject> seriesList(ProductProSearchQuery searcher, PageModel page);

    /**
     * 查询最近上新的品牌和商品
     *
     * @param pageSize
     * @param productSize
     * @param designerIds
     * @return
     */
    Map<Long, List<SearcherProduct>> findNewUpProductByBrand(Integer pageSize, Integer productSize,
                                                             List<Long> designerIds);

    /**
     * 根据分类查询N天内有上新的品牌
     *
     * @param day
     * @param size
     * @param topIds
     * @return
     */
    List<Long> findNewUpBrandByTopCate(Integer day, Integer size, Long[] topIds);

    /**
     * 根据分类查询N天内最新创建的品牌
     *
     * @param day
     * @param size
     * @param topIds
     * @return
     */
    List<Long> findNewCreateBrandByTopCate(Integer day, Integer size, Long[] topIds);

    /**
     * 查询品牌新上架的商品数量
     *
     * @param day
     * @param size
     * @return
     */
    JSONArray findNewUpGoodsCountGroupBrand(Integer day, Integer size);

    /**
     * 查询品类新上架的商品数量
     *
     * @param day
     * @param size
     * @return
     */
    Map<Long, Long> findNewUpGoodsCountGroupTopCate(Integer day, Integer size);

}
