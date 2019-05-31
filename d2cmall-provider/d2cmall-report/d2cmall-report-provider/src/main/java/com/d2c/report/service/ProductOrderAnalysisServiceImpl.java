package com.d2c.report.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.report.dao.ProductOrderAnalysisMapper;
import com.d2c.report.model.ProductOrderAnalysis;
import com.d2c.report.query.ProductOrderAnalysisSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(protocol = "dubbo")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductOrderAnalysisServiceImpl extends ListServiceImpl<ProductOrderAnalysis>
        implements ProductOrderAnalysisService {

    @Autowired
    private ProductOrderAnalysisMapper productOrderAnalysisMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PageResult<ProductOrderAnalysis> findBySearcher(ProductOrderAnalysisSearcher searcher, PageModel page) {
        PageResult<ProductOrderAnalysis> pager = new PageResult<ProductOrderAnalysis>(page);
        Integer totalCount = productOrderAnalysisMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            pager.setList(productOrderAnalysisMapper.findBySearcher(searcher, page));
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductOrderAnalysis insert(ProductOrderAnalysis productOrderAnalysis) {
        return this.save(productOrderAnalysis);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void doReplaceInto(ProductOrderAnalysis productOrderAnalysis) {
        productOrderAnalysisMapper.doReplaceInto(productOrderAnalysis);
    }

    @Override
    public List<Map<String, Object>> findExport(ProductOrderAnalysisSearcher searcher, PageModel page) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Integer total = productOrderAnalysisMapper.countExport(searcher);
        if (total > 0) {
            list = productOrderAnalysisMapper.findExport(searcher, page);
        }
        return list;
    }

    @Override
    public Integer countExport(ProductOrderAnalysisSearcher searcher) {
        return productOrderAnalysisMapper.countExport(searcher);
    }

    @Override
    public Integer countBySearcher(ProductOrderAnalysisSearcher searcher) {
        return productOrderAnalysisMapper.countBySearcher(searcher);
    }

    @Override
    public ProductOrderAnalysis findLast() {
        return productOrderAnalysisMapper.findLast();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updatepDeliverAndClose(Long designerId, String productSku, Integer deliverQuantity,
                                      Integer closeQuantity, Date orderDate) {
        return productOrderAnalysisMapper.updatepDeliverAndClose(designerId, productSku, deliverQuantity, closeQuantity,
                orderDate);
    }

}
