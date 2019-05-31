package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductAttributeMapper;
import com.d2c.product.model.ProductAttribute;
import com.d2c.product.query.ProductAttributeSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productAttributeService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductAttributeServiceImpl extends ListServiceImpl<ProductAttribute> implements ProductAttributeService {

    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    public ProductAttribute findById(Long id) {
        return super.findOneById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteById(Long id) {
        return this.deleteById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductAttribute insert(ProductAttribute productAttribute) {
        return save(productAttribute);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ProductAttribute productAttribute) {
        return this.updateNotNull(productAttribute);
    }

    public List<ProductAttribute> findByGroupId(Long attributeGroupId) {
        return productAttributeMapper.findByGroupId(attributeGroupId);
    }

    public PageResult<ProductAttribute> findBySearch(ProductAttributeSearcher searcher, PageModel page) {
        PageResult<ProductAttribute> pager = new PageResult<ProductAttribute>(page);
        int totalCount = productAttributeMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<ProductAttribute> list = productAttributeMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

}