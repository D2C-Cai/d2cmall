package com.d2c.product.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductDetailMapper;
import com.d2c.product.model.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("productDetailService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class ProductDetailServiceImpl extends ListServiceImpl<ProductDetail> implements ProductDetailService {

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Override
    @Cacheable(value = "product_detail", key = "'product_detail_'+#productId", unless = "#result == null")
    public ProductDetail findByProductId(Long productId) {
        return productDetailMapper.findByProductId(productId);
    }

    @Override
    @CacheEvict(value = "product_detail", key = "'product_detail_'+#productDetail.productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int update(ProductDetail productDetail) {
        return this.updateNotNull(productDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public ProductDetail insert(ProductDetail productDetail) {
        return this.save(productDetail);
    }

    @Override
    @CacheEvict(value = "product_detail", key = "'product_detail_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateAttribute(Long productId, Long attributeGroupId) {
        return productDetailMapper.updateAttribute(productId, attributeGroupId);
    }

    @Override
    @CacheEvict(value = "product_detail", key = "'product_detail_'+#productDetail.productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateByProductId(ProductDetail productDetail) {
        ProductDetail oldProductDetail = this.findByProductId(productDetail.getProductId());
        productDetail.setId(oldProductDetail.getId());
        return this.updateNotNull(productDetail);
    }

    @Override
    @CacheEvict(value = "product_detail", key = "'product_detail_'+#productId")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int deleteSizeJson(Long productId) {
        return productDetailMapper.updateSizeJson(productId, null);
    }

}
