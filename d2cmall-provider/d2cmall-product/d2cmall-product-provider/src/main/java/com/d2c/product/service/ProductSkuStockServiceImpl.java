package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductSkuStockMapper;
import com.d2c.product.model.ProductSkuStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productSkuStockService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductSkuStockServiceImpl extends ListServiceImpl<ProductSkuStock> implements ProductSkuStockService {

    @Autowired
    private ProductSkuStockMapper productSkuStockMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductSkuStock insert(ProductSkuStock skuStock) {
        return save(skuStock);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(List<ProductSkuStock> skuStock) {
        for (ProductSkuStock ss : skuStock) {
            productSkuStockMapper.insert(ss);
        }
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteAll() {
        return productSkuStockMapper.deleteAll();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(String stockCode, String barCode) {
        return productSkuStockMapper.delete(stockCode, barCode);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(String storeCode, String barCode, int stock) {
        return productSkuStockMapper.update(storeCode, barCode, stock);
    }

    @Override
    public ProductSkuStock findOne(String storeCode, String barCode) {
        return productSkuStockMapper.findOne(storeCode, barCode);
    }

    @Override
    public List<ProductSkuStock> findBySkuAndStore(String sku, boolean isPrimary) {
        return productSkuStockMapper.findBySkuAndStore(sku, isPrimary);
    }

    @Override
    public Map<String, ProductSkuStock> findStoreBySku(String sku) {
        List<ProductSkuStock> storeCodeList = productSkuStockMapper.findStoreBySku(sku);
        Map<String, ProductSkuStock> storeStockMap = new HashMap<>();
        storeCodeList.forEach(item -> storeStockMap.put(item.getStoreCode(), item));
        return storeStockMap;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateOccupyStock(String storeCode, String barCode, int stock) {
        return productSkuStockMapper.updateOccupyStock(storeCode, barCode, stock);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCleanOccupy() {
        return productSkuStockMapper.doCleanOccupy();
    }

    @Override
    public PageResult<ProductSkuStock> findByStore(String storeCode, PageModel page) {
        PageResult<ProductSkuStock> pager = new PageResult<ProductSkuStock>(page);
        int totalCount = productSkuStockMapper.countByStore(storeCode);
        if (totalCount > 0) {
            List<ProductSkuStock> list = productSkuStockMapper.findByStore(storeCode, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public int countByStore(String storeCode) {
        return productSkuStockMapper.countByStore(storeCode);
    }

}
