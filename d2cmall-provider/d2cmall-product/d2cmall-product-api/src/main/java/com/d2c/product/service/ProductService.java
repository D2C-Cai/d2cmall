package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.service.ListService;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductCategory;
import com.d2c.product.model.ProductRelation.RelationType;
import com.d2c.product.model.ProductSku;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.query.ProductSkuStockSearcher;
import com.d2c.product.support.SkuCodeBean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductService extends ListService<Product> {

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    Map<Long, Product> findByIds(Long[] ids);

    /**
     * 商品列表
     *
     * @param productSearcher
     * @param page
     * @return
     */
    PageResult<ProductDto> findBySearch(ProductSearcher productSearcher, PageModel page);

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    Product findById(Long id);

    /**
     * 根据产品id从Cache中获取产品信息
     *
     * @param id 产品id
     * @return
     */
    ProductDto findDetailById(Long id);

    /**
     * 索引
     *
     * @param productId
     * @param status
     * @return
     */
    int updateIndex(Long productId, Integer status, String operator);

    /**
     * 上下架
     *
     * @param id
     * @param status
     * @param adminName
     * @return
     */
    int updateMark(Long id, Integer status, String adminName);

    /**
     * 主推
     *
     * @param id
     * @param top
     * @return
     */
    int updateTopical(Long id, int top, String operator);

    /**
     * 广告
     *
     * @param id
     * @param ad
     * @return
     */
    int updateAd(Long id, int ad, String operator);

    /**
     * 商品标签
     *
     * @param id
     * @param tags
     * @return
     */
    int updateTags(Long id, String tags);

    /**
     * 货到付款
     *
     * @param id
     * @param cod
     * @return
     */
    int updateCod(Long id, int cod, String operator);

    /**
     * 购物车
     *
     * @param id
     * @param cart
     * @return
     */
    int updateCart(Long id, int cart, String operator);

    /**
     * 门店试衣
     *
     * @param id
     * @param type
     * @return
     */
    int updateSubscribe(Long id, int subscribe, String operator);

    /**
     * 设置允许售后
     *
     * @param id
     * @param subscribe
     * @return
     */
    int updateAfter(Long id, int after, String operator);

    /**
     * 是否支持优惠券
     *
     * @param id
     * @param coupon
     * @return
     */
    int updateCoupon(Long id, Integer coupon, String operator);

    /**
     * 更新备注
     *
     * @param id
     * @param remark
     * @return
     */
    int updateRemark(Long id, String remark, String operator);

    /**
     * 归档和复原
     *
     * @param id
     * @param mark
     * @return
     */
    int delete(Long id, int mark, String operator);

    /**
     * 23 24(23+1) 自营商品 18 19(18+1) 代销商品 16 17 老编码
     *
     * @param sp1GroupId
     * @param sp2GroupId
     * @param code
     * @return
     */
    SkuCodeBean getSKUCodeParser(Long sp1GroupId, Long sp2GroupId, String sn);

    /**
     * 添加商品
     *
     * @param productDto
     * @return
     */
    Product insert(ProductDto productDto, String operator);

    /**
     * 更新商品
     *
     * @param productDto
     * @return
     */
    int update(ProductDto productDto, String operator);

    /**
     * 更新商品库存时间戳
     *
     * @param syncStamp
     * @return
     */
    int updateSyncTimestamp(String syncStamp);

    /**
     * 更新商品库存
     *
     * @param currentTime
     * @return
     */
    int doSumProductStore();

    /**
     * 查询商品库存
     *
     * @param productId
     * @return
     */
    Map<String, Object> findProductStoreBySku(Long productId);

    /**
     * 查询商品活动库存
     *
     * @param productId
     * @return
     */
    Map<String, Integer> findFlashStore(Long productId);

    /**
     * 更新商品销售属性
     *
     * @param skuSet
     * @param productId
     * @return
     */
    int updateSalesPropertyBySku(List<ProductSku> skuSet, Long productId);

    /**
     * 更新商品价格
     *
     * @param product
     * @param skuSet
     * @return
     */
    int updatePriceBySku(Long productId);

    /**
     * 获取店铺中最大站内排序
     *
     * @param designerId
     * @return
     */
    int getMaxSort(Long designerId);

    /**
     * 更新排序
     *
     * @param productId
     * @param sort
     * @return
     */
    int updateSort(Long productId, Integer sort, String operator);

    /**
     * 更新活动排序
     *
     * @param id
     * @param gpSort
     * @param opSort
     * @param fpSort
     * @return
     */
    int updatePromotionSort(Long id, Integer gpSort, Integer opSort, Integer fpSort);

    /**
     * 商品不同状态的数量
     *
     * @return
     */
    Map<String, Object> countGroupByMark();

    /**
     * 商品数量
     *
     * @param productSearcher
     * @return
     */
    Integer countBySearch(ProductSearcher productSearcher);

    /**
     * 获取货号重复的商品
     *
     * @param sn
     * @return
     */
    List<Product> findProductBySn(String inernalSn);

    /**
     * 货号查询商品
     *
     * @param sn
     * @return
     */
    Product findOneBySn(String sn);

    /**
     * 更新上下架时间
     *
     * @param id
     * @param mark
     * @param marketDate
     * @return
     */
    int updateMarketDate(Long id, int mark, Date marketDate, String operator);

    /**
     * 更新商品分类
     *
     * @param productId
     * @param productCategory
     * @param pcJson
     * @param tcJson
     * @return
     */
    int updateCategory(Long productId, ProductCategory productCategory, String pcJson, String tcJson, String operator);

    /**
     * 设置定时任务
     *
     * @param productId
     * @param i
     * @return
     */
    int doTiming(Long productId, int i);

    /**
     * 商品门店库存数量
     *
     * @param searcher
     * @return
     */
    int countStockBySearch(ProductSkuStockSearcher searcher);

    /**
     * 商品门店库存列表
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<ProductDto> findStockBySearch(PageModel page, ProductSkuStockSearcher searcher);

    /**
     * 根据优惠券查询商品
     *
     * @param defineId
     * @param page
     * @return
     */
    PageResult<Product> findByCouponDefId(Long defineId, PageModel page);

    /**
     * 根据标签查询商品
     *
     * @param tagId
     * @param page
     * @return
     */
    PageResult<Product> findByProductTagId(Long tagId, PageModel page);

    /**
     * 根据活动查询商品
     *
     * @param promotionId
     * @param page
     * @return
     */
    PageResult<Product> findByPromotionId(Long promotionId, PageModel page);

    /**
     * 根据设计师查询商品
     *
     * @param designerId
     * @param page
     * @return
     */
    PageResult<Product> findByDesignerId(Long designerId, PageModel page);

    /**
     * 查询推荐搭配商品
     *
     * @param sourceId
     * @param type
     * @param page
     * @param mark
     * @return
     */
    PageResult<Product> findBySourceId(Long sourceId, RelationType type, PageModel page, Integer mark);

    /**
     * 根据买家秀查询商品
     *
     * @param shareId
     * @return
     */
    Product findByMemberShareId(Long shareId);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ProductDto findDtoById(Long id);

    /**
     * 查询广告商品
     *
     * @param productSearcher
     * @param page
     * @return
     */
    PageResult<ProductDto> findAdBySearch(ProductSearcher productSearcher, PageModel page);

    /**
     * 分页查询商品库存
     *
     * @param page
     * @return
     */
    PageResult<Product> findSyncProductStore(String syncStamp, PageModel page);

    /**
     * 根据品牌和条码查询
     *
     * @param designerId
     * @param barCode
     * @param designerIds
     * @return
     */
    List<Long> findByDesignerIdAndBarCode(Long designerId, String barCode, List<Long> designerIds);

    /**
     * 没有库存的商品
     *
     * @param page
     * @return
     */
    PageResult<ProductDto> findNoStore(PageModel page);

    /**
     * 根据售后状态和设计师id查找
     *
     * @param designerId
     * @param after
     * @return
     */
    int updateAfterByDesigner(Long designerId, Integer after, Long productId);

    /**
     * 根据设计师id和货到付款状态修改
     *
     * @param designerId
     * @param cod
     * @return
     */
    int updateCodByDesigner(Long designerId, Integer cod, Long productId);

    /**
     * 根据设计师id查询商品id
     *
     * @param designerId
     * @return
     */
    List<Long> findProductId(Long designerId);

    /**
     * 根据第三方平台货号查D2C商品ID
     *
     * @param productSn
     * @return
     */
    Product findByProductSn(String productSn);

    /**
     * 查询导出商品
     *
     * @param productSearcher
     * @param page
     * @return
     */
    PageResult<ProductDto> findExport(ProductSearcher productSearcher, PageModel page);

    /**
     * 根据分类获取商品
     *
     * @param productCategoryId
     * @param lastDate
     * @param page
     * @param limit
     * @return
     */
    List<Product> findDeepByCategoryId(Long productCategoryId, Date lastDate, int page, int limit);

    /**
     * 更新预计发货时间
     *
     * @param id
     * @param estimateDate
     * @param operator
     * @return
     */
    int updateEstimateDate(Long id, String estimateDate, String operator);

    /**
     * 更新视频信息
     *
     * @param id
     * @param video
     * @return
     */
    int updateVideoById(Long id, String video);

    /**
     * 更新商品推荐值
     *
     * @param id
     * @param recom
     * @return
     */
    int updateRecomById(Long id, Double recom, Boolean operRecom);

    /**
     * 设置返利
     *
     * @param id
     * @param firstRadio
     * @param secondRatio
     * @param grossRatio
     * @param opertor
     * @return
     */
    int updateRebate(Long id, BigDecimal firstRatio, BigDecimal secondRatio, BigDecimal grossRatio, String opertor);

    /**
     * 更新商品活动
     *
     * @param productId
     * @param goodPromotionId
     * @return
     */
    int updateGoodPromotion(Long productId, Long goodPromotionId, String operator);

    /**
     * 更新订单活动
     *
     * @param productId
     * @param orderPromotionId
     * @return
     */
    int updateOrderPromotion(Long productId, Long orderPromotionId, String operator);

    /**
     * 删除商品活动
     *
     * @param productId
     * @param goodPromotionId
     * @return
     */
    int deleteGoodPromotion(Long productId, Long goodPromotionId, String operator);

    /**
     * 删除订单活动
     *
     * @param productId
     * @param orderPromotionId
     * @return
     */
    int deleteOrderPromotion(Long productId, Long orderPromotionId, String operator);

    /**
     * 修改一口价
     *
     * @param id
     * @param aPrice
     * @return
     */
    int updateAPrice(Long id, BigDecimal aPrice);

    /**
     * 绑定限时购活动
     *
     * @param productId
     * @param id
     * @param username
     * @return
     */
    int updateFlashPromotion(Long id, Long flashPromotionId, String username);

    /**
     * 删除限时购活动
     *
     * @param product
     * @return
     */
    int deleteFlashPromotion(Long productId);

    /**
     * 绑定拼团活动
     *
     * @param id
     * @param collagePromotionId
     * @param username
     * @return
     */
    int updateCollagePromotion(Long id, Long collagePromotionId, String username);

    /**
     * 删除拼团活动
     *
     * @param productId
     * @return
     */
    int deleteCollagePromotion(Long productId);

    /**
     * 修改售后说明
     *
     * @param id
     * @param afterMemo
     * @return
     */
    int updateAfterMemo(Long id, String afterMemo, String operator);

    /**
     * 更新商品类型
     *
     * @param id
     * @param sellType
     * @param estimateDate
     * @param estimateDay
     * @param remark
     * @return
     */
    int updateSellType(Long id, String sellType, Date estimateDate, Integer estimateDay, String remark);

    /**
     * 根据品牌id（和系列id）查找上架的商品
     *
     * @param brandId
     * @param seriesId
     * @param pageModel
     * @return
     */
    PageResult<Long> findByBrandAndSeries(Long brandId, Long seriesId, PageModel pageModel);

    /**
     * 上传图片
     *
     * @param id
     * @return
     */
    int uploadPic(Long id);

    /**
     * 修改跨境商品属性
     *
     * @param id
     * @param kaolaPrice
     * @param mark
     * @param taxation
     * @param shipping
     * @return
     */
    int updateKaolaProduct(Long id, BigDecimal kaolaPrice, Integer mark, Integer taxation, Integer shipping);

    /**
     * 修复考拉商品图片
     *
     * @param dto
     * @return
     */
    int doRepairKaolaPic(ProductDto dto);

    int countKaolaByWaitingForRepair();

    /**
     * 寻找考拉待修复商品
     *
     * @return
     */
    PageResult<ProductDto> findKaolaByWaitingForRepair(PageModel page);

    /**
     * 答题选商品
     *
     * @param constellation
     * @param pattern
     * @param interest
     * @param style
     * @param category
     * @return
     */
    List<Map<String, Object>> findProductBySelect(Integer constellation, Integer pattern, Integer interest,
                                                  Integer style, Integer category);

}
