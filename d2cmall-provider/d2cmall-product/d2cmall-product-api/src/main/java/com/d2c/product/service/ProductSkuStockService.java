package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.model.ProductSkuStock;

import java.util.List;
import java.util.Map;

/**
 * 条码即时库存（product_sku_stock）
 */
public interface ProductSkuStockService {

    /**
     * 根据门店编号，条码删除条码即时库存
     *
     * @param storeCode 门店编号
     * @param barCode   条码
     * @return
     */
    int delete(String storeCode, String barCode);

    /**
     * 根据门店编号，条码更新即时库存
     *
     * @param storeCode 门店编号
     * @param barCode   条码
     * @param stock     即时库存
     * @return
     */
    int update(String storeCode, String barCode, int stock);

    /**
     * 保存即时库存信息
     *
     * @param skuStock
     * @return
     */
    ProductSkuStock insert(ProductSkuStock skuStock);

    /**
     * 查询即时库存信息
     *
     * @param storeCode
     * @param barCode
     * @return
     */
    ProductSkuStock findOne(String storeCode, String barCode);

    /**
     * 根据条码及门店编码是否为0000查找即时库存
     *
     * @param sku       条码
     * @param isPrimary storeCode是否为0000，
     * @return
     */
    List<ProductSkuStock> findBySkuAndStore(String sku, boolean isPrimary);

    /**
     * 加减占单库存
     *
     * @param storeCode
     * @param barCode
     * @param stock
     * @return
     */
    int updateOccupyStock(String storeCode, String barCode, int stock);

    /**
     * 清除占单库存
     *
     * @return
     */
    int doCleanOccupy();

    /**
     * 根据sku查找店铺
     *
     * @param sku
     * @return
     */
    Map<String, ProductSkuStock> findStoreBySku(String sku);

    /**
     * 根据门店查询明细
     *
     * @param storeCode
     * @param page
     * @return
     */
    PageResult<ProductSkuStock> findByStore(String storeCode, PageModel page);

    /**
     * 根据门店查询数量
     *
     * @param storeCode
     * @return
     */
    int countByStore(String storeCode);

}
