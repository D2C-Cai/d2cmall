package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductCombMapper;
import com.d2c.product.dto.ProductCombDto;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductComb;
import com.d2c.product.model.ProductRelation;
import com.d2c.product.model.ProductRelation.RelationType;
import com.d2c.product.query.ProductCombSearcher;
import com.d2c.product.query.RelationSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("productCombService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductCombServiceImpl extends ListServiceImpl<ProductComb> implements ProductCombService {

    @Autowired
    private ProductCombMapper productCombMapper;
    @Autowired
    private ProductRelationService productRelationService;
    @Autowired
    private ProductService productionService;

    @Override
    @Cacheable(value = "productcomb", key = "'productcomb_'+#id", unless = "#result == null")
    public ProductCombDto findDtoById(Long id) {
        ProductComb productComb = this.findById(id);
        if (productComb == null) {
            return null;
        }
        ProductCombDto dto = new ProductCombDto();
        BeanUtils.copyProperties(productComb, dto);
        dto.setProducts(findProducts(dto.getId()));
        return dto;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductComb insert(ProductComb productComb) {
        return this.save(productComb);
    }

    @Override
    @CacheEvict(value = "productcomb", key = "'productcomb_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return super.deleteById(id);
    }

    @Override
    public PageResult<ProductCombDto> findBySearcher(ProductCombSearcher searcher, PageModel page) {
        PageResult<ProductCombDto> pager = new PageResult<>(page);
        int totalCount = productCombMapper.countBySearcher(searcher);
        List<ProductCombDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            List<ProductComb> list = productCombMapper.findBySearcher(searcher, page);
            for (ProductComb productComb : list) {
                ProductCombDto dto = new ProductCombDto();
                BeanUtils.copyProperties(productComb, dto);
                dto.setProducts(findProducts(dto.getId()));
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    /**
     * 组合的商品
     *
     * @param productComb
     */
    private List<Product> findProducts(Long productCombId) {
        RelationSearcher search = new RelationSearcher();
        search.setType(RelationType.PRODUCTCOMB);
        search.setSourceId(productCombId);
        PageModel page = new PageModel();
        PageResult<ProductRelation> pager = productRelationService.findRelations(search, page);
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < pager.getList().size(); i++) {
            Product product = productionService.findById(pager.getList().get(i).getRelationId());
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }

    @Override
    @CacheEvict(value = "productcomb", key = "'productcomb_'+#productComb.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ProductComb productComb) {
        return this.updateNotNull(productComb);
    }

    @Override
    @CacheEvict(value = "productcomb", key = "'productcomb_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateMark(Long id, int mark) {
        return productCombMapper.updateMark(id, mark);
    }

    @Override
    public ProductComb findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    @CacheEvict(value = "productcomb", key = "'productcomb_'+#sourceId")
    public void doClearBySourceId(Long sourceId) {
    }

}
