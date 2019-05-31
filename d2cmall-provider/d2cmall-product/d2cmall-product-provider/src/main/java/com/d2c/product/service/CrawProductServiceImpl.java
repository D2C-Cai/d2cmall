package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.CrawProductMapper;
import com.d2c.product.dao.ProductMapper;
import com.d2c.product.dto.CrawProductDto;
import com.d2c.product.model.CrawProduct;
import com.d2c.product.model.Product;
import com.d2c.product.query.CrawProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("crawProductService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CrawProductServiceImpl extends ListServiceImpl<CrawProduct> implements CrawProductService {

    @Autowired
    private CrawProductMapper crawProductMapper;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public CrawProduct insert(CrawProduct crawProduct) {
        return this.save(crawProduct);
    }

    @Override
    public PageResult<CrawProductDto> findBySearch(CrawProductSearcher searcher, PageModel pager) {
        PageResult<CrawProductDto> pageResult = new PageResult<>();
        int totalCount = crawProductMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Long> productIds = crawProductMapper.countProIdsBySearcher(searcher, pager);
            List<CrawProductDto> list = new ArrayList<>();
            for (Long productId : productIds) {
                CrawProductDto crawProductDto = new CrawProductDto();
                List<CrawProduct> crawProducts = crawProductMapper.findByD2cProId(productId);
                if (crawProducts != null && crawProducts.size() > 0) {
                    crawProductDto.setCrawProducts(crawProducts);
                    SearcherProduct searcherProduct = productSearcherQueryService.findById(productId.toString());
                    if (searcherProduct == null) {
                        searcherProduct = new SearcherProduct();
                        Product product = productMapper.selectByPrimaryKey(productId);
                        BeanUtils.copyProperties(product, searcherProduct);
                    }
                    crawProductDto.setSearcherProduct(searcherProduct);
                    list.add(crawProductDto);
                }
            }
            pageResult.setList(list);
        }
        pageResult.setTotalCount(totalCount);
        return pageResult;
    }

    @Override
    public void doClearProductByDesignerId(Long designerId) {
        crawProductMapper.deleteByDesignerId(designerId);
    }

}
