package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductMagazineRelationMapper;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductMagazineRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productMagazineRelationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductMagazineRelationServiceImpl extends ListServiceImpl<ProductMagazineRelation>
        implements ProductMagazineRelationService {

    @Autowired
    private ProductMagazineRelationMapper productMagazineRelationMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(Long pageId, Long productId) {
        return productMagazineRelationMapper.doReplaceInto(pageId, productId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteOne(Long pageId, Long productId) {
        return productMagazineRelationMapper.deleteOne(pageId, productId);
    }

    @Override
    public PageResult<Product> findProductByPageId(Long pageId, PageModel page) {
        PageResult<Product> pager = new PageResult<>(page);
        int totalCount = productMagazineRelationMapper.countProductByPageId(pageId);
        if (totalCount > 0) {
            List<Product> list = productMagazineRelationMapper.findProductByPageId(pageId, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public ProductMagazineRelation findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long pageId, Long productId, Integer sort) {
        return productMagazineRelationMapper.updateSort(pageId, productId, sort);
    }

}
