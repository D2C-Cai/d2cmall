package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductAttributeGroupMapper;
import com.d2c.product.model.ProductAttributeGroup;
import com.d2c.product.query.ProductAttributeGroupSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productAttributeGroupService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductAttributeGroupServiceImpl extends ListServiceImpl<ProductAttributeGroup>
        implements ProductAttributeGroupService {

    @Autowired
    private ProductAttributeGroupMapper productAttributeGroupMapper;

    @Override
    public PageResult<ProductAttributeGroup> findBySearch(ProductAttributeGroupSearcher searcher, PageModel page) {
        PageResult<ProductAttributeGroup> pager = new PageResult<ProductAttributeGroup>(page);
        int totalCount = productAttributeGroupMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<ProductAttributeGroup> list = productAttributeGroupMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    public ProductAttributeGroup findById(Long id) {
        return super.findOneById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductAttributeGroup insert(ProductAttributeGroup productAttributeGroup) {
        return this.save(productAttributeGroup);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ProductAttributeGroup productAttributeGroup) {
        return this.updateNotNull(productAttributeGroup);
    }

}