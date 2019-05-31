package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductSkuStockSummaryMapper;
import com.d2c.product.dto.ProductSkuStockDto;
import com.d2c.product.model.ProductSkuStockSummary;
import com.d2c.product.query.ProductSkuStockSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productSkuStockSummaryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductSkuStockSummaryServiceImpl extends ListServiceImpl<ProductSkuStockSummary>
        implements ProductSkuStockSummaryService {

    @Autowired
    private ProductSkuStockSummaryMapper productSkuStockSummaryMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int sumSfStock() {
        productSkuStockSummaryMapper.initSfStock();
        productSkuStockSummaryMapper.sumSfStock();
        return 1;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int sumStStock() {
        productSkuStockSummaryMapper.initStStock();
        productSkuStockSummaryMapper.sumStStock();
        return 1;
    }

    @Override
    public ProductSkuStockSummary insert(ProductSkuStockSummary productSkuStockSummary) {
        return this.save(productSkuStockSummary);
    }

    @Override
    public ProductSkuStockSummary findBySkuId(Long skuId) {
        return productSkuStockSummaryMapper.findBySkuId(skuId);
    }

    @Override
    public List<ProductSkuStockSummary> findByProductId(Long productId) {
        return productSkuStockSummaryMapper.findByProductId(productId);
    }

    @Override
    public PageResult<ProductSkuStockDto> findBySearch(ProductSkuStockSearcher productSearcher, PageModel page) {
        PageResult<ProductSkuStockDto> pager = new PageResult<ProductSkuStockDto>(page);
        int totalCount = productSkuStockSummaryMapper.countBySearch(productSearcher);
        if (totalCount > 0) {
            List<ProductSkuStockDto> list = productSkuStockSummaryMapper.findBySearch(productSearcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public int countBySearch(ProductSkuStockSearcher productSearcher) {
        return productSkuStockSummaryMapper.countBySearch(productSearcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSkuBySkuId(String barCode, Long skuId, String sp1, String sp2) {
        return productSkuStockSummaryMapper.updateSkuBySkuId(barCode, skuId, sp1, sp2);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return productSkuStockSummaryMapper.delete(id);
    }

}
