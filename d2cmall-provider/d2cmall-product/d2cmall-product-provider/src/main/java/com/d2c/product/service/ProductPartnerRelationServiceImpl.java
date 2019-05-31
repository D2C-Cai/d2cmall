package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductPartnerRelationMapper;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductPartnerRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productPartnerRelationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductPartnerRelationServiceImpl extends ListServiceImpl<ProductPartnerRelation>
        implements ProductPartnerRelationService {

    @Autowired
    private ProductPartnerRelationMapper productPartnerRelationMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(Long storeId, Long productId) {
        return productPartnerRelationMapper.doReplaceInto(storeId, productId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteOne(Long storeId, Long productId) {
        return productPartnerRelationMapper.deleteOne(storeId, productId);
    }

    @Override
    public ProductPartnerRelation findOne(Long storeId, Long productId) {
        return productPartnerRelationMapper.findOne(storeId, productId);
    }

    @Override
    public PageResult<Product> findProductByStoreId(Long storeId, PageModel page) {
        PageResult<Product> pager = new PageResult<>(page);
        int totalCount = productPartnerRelationMapper.countProductByStoreId(storeId);
        if (totalCount > 0) {
            List<Product> list = productPartnerRelationMapper.findProductByStoreId(storeId, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public ProductPartnerRelation findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long storeId, Long productId, Integer sort) {
        return productPartnerRelationMapper.updateSort(storeId, productId, sort);
    }

}
