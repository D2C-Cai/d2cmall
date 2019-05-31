package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.ProductSkuDto;
import com.d2c.product.model.ProductSku;
import com.d2c.product.query.ProductSkuSearcher;
import com.d2c.product.query.ProductSkuStockSearcher;
import com.d2c.product.support.SkuCodeBean;
import com.d2c.product.support.UploadProductBean;
import com.d2c.product.third.kaola.KaolaProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 产品SKU码（productsku）
 */
public interface ProductSkuService {

    /**
     * 根据ids查询
     *
     * @param ids
     * @return
     */
    Map<Long, ProductSku> findByIds(Long[] ids);

    SkuCodeBean processRegular(Long sp1GroupId, Long sp2GroupId, String code);

    SkuCodeBean processthird(Long sp1GroupId, Long sp2GroupId, String code);

    SkuCodeBean processStore(Long sp1GroupId, Long sp2GroupId, String code);

    /**
     * 初始化库存
     *
     * @param productSkuId
     * @param store
     * @return
     */
    Integer initStore(Long productSkuId, Integer store);

    /**
     * 锁定库存
     *
     * @param productSkuId
     * @param store
     * @return
     */
    Integer decStore(Long productSkuId);

    void incStore(Long productSkuId);

    String dump(Long productSkuId);

    /**
     * 保存产品SKU码
     *
     * @param productSku
     * @return
     */
    ProductSku insert(ProductSku productSku, int productStatus, String operator);

    /**
     * 更新产品SKU码
     *
     * @param sku
     * @return
     */
    int update(ProductSku sku, boolean doPromotion, int productStatus, String operator);

    /**
     * 修改一口价
     *
     * @param sn
     * @param aPrice
     * @return
     */
    int updateAPrice(Long skuId, BigDecimal aPrice, String operator);

    /**
     * 根据sku码id查找对应SKU信息
     *
     * @param productSkuId SKU码id
     * @return
     */
    ProductSku findById(Long productSkuId);

    /**
     * 根据barCode查询
     *
     * @param barCode
     * @return
     */
    ProductSku findByBarCode(String barCode);

    /**
     * 根据产品id获取对应SKU信息
     *
     * @param productId 产品id
     * @return
     */
    List<ProductSku> findByProductId(Long productId);

    /**
     * 根据产品id获取对应SKU信息
     *
     * @param productId 产品id
     * @return
     */
    int countByProductId(Long id);

    /**
     * 根据条形码以及门店编码获取
     *
     * @param barCode 实际条码
     * @param primary 门店编码是否为0000，1为0000，0不为0000
     * @return
     */
    List<Map<String, Object>> findStockByStore(String barCode, int primary);

    /**
     * 根据设计师ID，初始化POP库存
     *
     * @param designerId
     * @return
     */
    int doInitPopStore(Long designerId);

    /**
     * 导入POP库存
     *
     * @param bean
     * @param operator
     * @return
     */
    int updatePopStore(UploadProductBean bean, String operator);

    /**
     * 更新活动库存
     *
     * @param productId
     * @param skuIds
     * @param flashStock
     * @param collagePrice
     * @param flashPrice
     * @return
     */
    int updateFlashStore(Long productId, Long[] skuIds, Integer[] flashStock, BigDecimal[] collagePrice,
                         BigDecimal[] flashPrice);

    /**
     * 同步SKU顺丰和门店库存
     *
     * @return
     */
    int doSyncSkuStore();

    /**
     * 求和SKU自营库存
     *
     * @return
     */
    int doSumSkuStore();

    /**
     * 修复冻结库存
     *
     * @return
     */
    int doRepairFreezeStock();

    /**
     * 根据ProductSkuSearcher内的过滤条件获取sku码信息， 采用分页，以PageResult对象返回
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<ProductSkuDto> findBySearch(ProductSkuSearcher searcher, PageModel page);

    /**
     * 根据id修改sku码状态
     *
     * @param skuId     sku主键id
     * @param status    状态
     * @param adminName 修改人
     * @return
     */
    int updateStatusByProduct(Long productId, Integer status, String adminName);

    /**
     * 根据id删除sku码信息
     *
     * @param skuId 主键id
     * @return
     */
    int delete(Long skuId, String operator);

    /**
     * 根据颜色尺码寻找SKU
     *
     * @param productId 产品id
     * @param colorId   颜色
     * @param sizeId    尺码
     * @return
     */
    Map<String, Object> findBySalesProperties(Long productId, Long colorId, Long sizeId);

    /**
     * 冻结库存 或 解冻库存
     *
     * @param productId
     * @param productSkuId
     * @param quantity
     * @return
     */
    int doFreezeStock(Long productId, Long productSkuId, int quantity);

    /**
     * 归还库存（已付款订单）
     *
     * @param productId
     * @param productSkuId
     * @param quantity
     * @param pop
     * @return
     */
    int doRevertStock(Long productId, Long productSkuId, int quantity, int pop);

    /**
     * 解冻库存 和 扣减库存
     *
     * @param productId
     * @param productSkuId
     * @param quantity
     * @param reopen
     * @return
     */
    int doCancelFreezeAndDeduceStock(Long productId, Long productSkuId, int quantity, int reopen);

    /**
     * 解冻库存 和 扣减活动库存
     *
     * @param productId
     * @param productSkuId
     * @param quantity
     * @param reopen
     * @return
     */
    int doCancelFreezeAndDeduceFlashStock(Long productId, Long productSkuId, int quantity, int reopen);

    /**
     * 保存sku修改记录
     *
     * @param oldproductSku 旧的sku（如果是新增则为null）
     * @param newproductSku 新的sku
     * @param operator      操作人
     */
    void saveProductSkuLog(ProductSku oldproductSku, ProductSku newproductSku, String operator);

    /**
     * 根据skuSn更新externalSn
     *
     * @param skuSn
     * @param externalSn
     * @return
     */
    int updateExternalSnBySkuSn(String skuSn, String externalSn);

    /**
     * 判断是否存在相同barCode的sku（除去状态为删除的）
     *
     * @param barCode
     * @return
     */
    List<ProductSku> existSameBarCode(String barCode);

    /**
     * 查询不同来源的商品sku
     *
     * @param source
     * @return
     */
    PageResult<KaolaProduct> findByProductSource(String source, PageModel page);

    /**
     * 查询不同来源的商品数量
     *
     * @param source
     * @return
     */
    int countByProductSource(String source);

    /**
     * 更新考拉商品pop库存
     *
     * @param barCode
     * @param popStore
     * @return
     */
    int updateKaolaProductPopStore(String barCode, Integer popStore, BigDecimal kaolaPrice);

    /**
     * 更新sp1（用户考拉修复颜色图片）
     *
     * @param id
     * @param sp1
     * @return
     */
    int updateSp1(Long id, String sp1, boolean updateSalesProperty, Long productId);

    int countKaolaByWaitingForRepair();

    /**
     * 寻找考拉待修复商品
     *
     * @return
     */
    PageResult<ProductSku> findKaolaByWaitingForRepair(PageModel page);

    /**
     * 查找外部编码查找
     *
     * @param brandId
     * @param externalSn
     * @return
     */
    List<ProductSku> findByBrandIdAndExternalSn(Long brandId, String externalSn);

    /**
     * 根据门店查询门店条码和库存
     *
     * @param storeCode
     * @param searcher
     * @param page
     * @return
     */
    PageResult<ProductSkuDto> findByStore(String storeCode, ProductSkuStockSearcher searcher, PageModel page);

    /**
     * 根据门店查询门店条码和库存
     *
     * @param storeCode
     * @param searcher
     * @return
     */
    int countByStore(String storeCode, ProductSkuStockSearcher searcher);

    /**
     * 草莓商品更新价格
     *
     * @param id
     * @param originalCost
     * @param price
     * @param costPrice
     * @param kaolaPrice
     * @return
     */
    int updateCaomeiSkuInfo(Long id, BigDecimal originalCost, BigDecimal price, BigDecimal costPrice,
                            BigDecimal kaolaPrice, Integer popStore);

    /**
     * 更新限时购价和库存
     *
     * @param productId
     * @param skuId
     * @param flashPrice
     * @param flashStore
     * @return
     */
    int updateFlashPrice(Long productId, Long skuId, BigDecimal flashPrice, Integer flashStore);

}
