package com.d2c.product.service;

import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductSummaryMapper;
import com.d2c.product.model.ProductSummary;
import com.d2c.product.search.model.SearcherProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productSummaryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class ProductSummaryServiceImpl extends ListServiceImpl<ProductSummary> implements ProductSummaryService {

    @Autowired
    private ProductSummaryMapper productSummaryMapper;
    @Autowired
    private ProductModuleSearchService productModuleSearchService;

    @Override
    public ProductSummary insert(ProductSummary summary) {
        return this.save(summary);
    }

    @Override
    public ProductSummary findByProductId(Long productId) {
        return productSummaryMapper.findByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateViewsCount(Integer views, Long productId) {
        return productSummaryMapper.updateViewsCount(views, productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateCartsCount(Integer carts, Long productId) {
        return productSummaryMapper.updateCartsCount(carts, productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateOrdersCount(Integer orders, Long productId) {
        return productSummaryMapper.updateOrdersCount(orders, productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateSalesCount(Integer sales, Long productId) {
        int result = productSummaryMapper.updateSalesCount(sales, productId);
        if (result > 0) {
            ProductSummary summary = productSummaryMapper.findByProductId(productId);
            SearcherProduct product = new SearcherProduct();
            product.setId(productId);
            product.setSales(summary.getSales());
            productModuleSearchService.update(product);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateCommentsCount(Integer comments, Long productId) {
        int result = productSummaryMapper.updateCommentsCount(comments, productId);
        if (result > 0) {
            ProductSummary summary = productSummaryMapper.findByProductId(productId);
            SearcherProduct product = new SearcherProduct();
            product.setId(productId);
            product.setComments(summary.getComments());
            productModuleSearchService.update(product);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateConsultsCount(Integer consults, Long productId) {
        int result = productSummaryMapper.updateConsultsCount(consults, productId);
        if (result > 0) {
            ProductSummary summary = productSummaryMapper.findByProductId(productId);
            SearcherProduct product = new SearcherProduct();
            product.setId(productId);
            product.setConsults(summary.getConsults());
            productModuleSearchService.update(product);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateLikeCount(Integer count, Long productId) {
        return productSummaryMapper.updateLikeCount(count, productId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updateRecentlySales(Long productId, Integer recentlySales) {
        int result = productSummaryMapper.updateRecentlySales(productId, recentlySales);
        if (result > 0) {
            productModuleSearchService.updateRecentlySales(productId, recentlySales);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int updatePartnerSales(Long productId, Integer sales) {
        int success = productSummaryMapper.updatePartnerSales(productId, sales);
        if (success > 0) {
            productModuleSearchService.updatePartnerSales(productId, sales);
        }
        return success;
    }

    @Override
    public List<Long> findAllRecentlySales() {
        return productSummaryMapper.findAllRecentlySales();
    }

    @Override
    public List<Long> findAllPartnerSales() {
        return productSummaryMapper.findAllPartnerSales();
    }

}
