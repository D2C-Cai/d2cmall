package com.d2c.product.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductSkuOptionMapper;
import com.d2c.product.model.ProductSkuOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productSkuOptionService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductSkuOptionServiceImpl extends ListServiceImpl<ProductSkuOption> implements ProductSkuOptionService {

    @Autowired
    private ProductSkuOptionMapper productSkuOptionMapper;
    @Autowired
    private ProductOptionService productOptionService;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ProductSkuOption insert(ProductSkuOption productSkuOption) {
        return this.save(productSkuOption);
    }

    @Override
    public List<ProductSkuOption> findByProductId(Long productId) {
        return productSkuOptionMapper.findByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateMarkByProductId(Long productId, Integer mark) {
        return productSkuOptionMapper.updateMarkByProductId(productId, mark);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(ProductSkuOption productSkuOption) {
        return this.updateNotNull(productSkuOption);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        ProductSkuOption old = productSkuOptionMapper.selectByPrimaryKey(id);
        productSkuOptionMapper.delete(id);
        List<ProductSkuOption> skus = productSkuOptionMapper.findByProductId(old.getProductId());
        productOptionService.updateSalesPropertyBySku(skus, old.getProductId());
        productOptionService.updatePriceBySku(old.getProductId());
        return 1;
    }

    @Override
    public ProductSkuOption findById(Long id) {
        return productSkuOptionMapper.selectByPrimaryKey(id);
    }

}
