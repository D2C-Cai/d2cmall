package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductCategoryMapper;
import com.d2c.product.dto.ProductCategoryDto;
import com.d2c.product.model.ProductCategory;
import com.d2c.product.query.ProductCategorySearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("productCategoryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductCategoryServiceImpl extends ListServiceImpl<ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public PageResult<ProductCategoryDto> findBySearch(ProductCategorySearcher searcher, PageModel page) {
        PageResult<ProductCategoryDto> pager = new PageResult<ProductCategoryDto>(page);
        int totalCount = productCategoryMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<ProductCategory> list = productCategoryMapper.findBySearch(searcher, page);
            List<ProductCategoryDto> dtos = new ArrayList<ProductCategoryDto>();
            for (int i = 0; i < list.size(); i++) {
                ProductCategoryDto dto = new ProductCategoryDto();
                BeanUtils.copyProperties(list.get(i), dto);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    public List<ProductCategoryDto> processSequence(List<ProductCategoryDto> all, Long p,
                                                    List<ProductCategoryDto> temp) {
        if (temp == null) {
            temp = new ArrayList<ProductCategoryDto>();
        }
        for (ProductCategoryDto node : all) {
            if ((p == null && node.getParentId() == null)
                    || (node != null && node.getParentId() != null && node.getParentId().equals(p))) {
                temp.add(node);
                processSequence(all, node.getId(), temp);
            }
        }
        return temp;
    }

    @Cacheable(value = "product_category", key = "'product_category_'+#id", unless = "#result == null")
    public ProductCategory findById(Long id) {
        return super.findOneById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductCategory insert(ProductCategory productCategory) {
        ProductCategory parent = null;
        if (productCategory.getParentId() != null && productCategory.getParentId().intValue() > 0) {
            parent = this.findById(productCategory.getParentId());
            if (parent != null) {
                parent.setLeaf(0);
                this.update(parent);
                productCategory.setTopId(parent.getTopId());
                productCategory.setDepth(parent.getDepth() + 1);
            }
        }
        if (productCategory.getLeaf() == 1 && productCategory.getSp1GroupId() == null) {
            throw new BusinessException("最末级分类必须存在主规格组");
        }
        try {
            productCategory = this.save(productCategory);
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException("已经存在相同属性的商品类别");
        }
        if (productCategory.getId() > 0) {
            if (parent == null) {
                this.updatePathById(productCategory.getId().toString(), productCategory.getId());
            } else {
                this.updatePathById(
                        parent.getPath() + ProductCategory.PATH_SEPARATOR + productCategory.getId().toString(),
                        productCategory.getId());
            }
        }
        this.doBindAttributeGroup(productCategory);
        return this.findById(productCategory.getId());
    }

    @CacheEvict(value = "product_category", key = "'product_category_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updatePathById(String path, Long id) {
        return productCategoryMapper.updatePathById(path, id);
    }

    @CacheEvict(value = "product_category", key = "'product_category_'+#productCategory.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBindAttributeGroup(ProductCategory productCategory) {
        if (productCategory.getAttributeGroupId() != null) {
            return productCategoryMapper.bindAttributeGroup(productCategory);
        }
        return 0;
    }

    @CacheEvict(value = "product_category", key = "'product_category_'+#productCategory.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ProductCategory productCategory) {
        if (productCategory.getLeaf() == 1 && productCategory.getSp1GroupId() == null) {
            throw new BusinessException("最末级分类必须存在主规格组");
        }
        this.updateNotNull(productCategory);
        return this.doBindAttributeGroup(productCategory);
    }

    @CacheEvict(value = "product_category", key = "'product_category_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return productCategoryMapper.updateStatus(id, status);
    }

    public List<ProductCategory> findAll(Integer status) {
        return productCategoryMapper.findAll(status);
    }

    public List<ProductCategory> findInSet(String path) {
        return productCategoryMapper.findInSet(path);
    }

    @CacheEvict(value = "product_category", key = "'product_category_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByIdAndDepth(Long id, int depth) {
        return productCategoryMapper.deleteByIdAndDepth(id, depth);
    }

    @Override
    public ProductCategory findByCode(String code) {
        return productCategoryMapper.findByCode(code);
    }

    @Override
    public List<ProductCategory> findByBottomId(Long categoryId) {
        return productCategoryMapper.findByBottomId(categoryId);
    }

    @Override
    public List<ProductCategory> findByTopId(Long topId) {
        return productCategoryMapper.findByTopId(topId);
    }

}