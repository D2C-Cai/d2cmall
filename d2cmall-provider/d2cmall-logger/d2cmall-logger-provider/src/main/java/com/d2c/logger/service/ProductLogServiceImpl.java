package com.d2c.logger.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.ProductLogMapper;
import com.d2c.logger.model.ProductLog;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("productLogService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductLogServiceImpl extends ListServiceImpl<ProductLog> implements ProductLogService {

    @Autowired
    private ProductLogMapper productLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public ProductLog insert(ProductLog log) {
        return this.save(log);
    }

    @Override
    public PageResult<ProductLog> findByProductId(Long productId, PageModel page) {
        PageResult<ProductLog> pager = new PageResult<ProductLog>(page);
        int totalCount = productLogMapper.countByProductId(productId);
        List<ProductLog> list = new ArrayList<ProductLog>();
        if (totalCount > 0) {
            list = productLogMapper.findByProductId(productId, page);
            pager.setTotalCount(totalCount);
        }
        pager.setList(list);
        return pager;
    }

}
