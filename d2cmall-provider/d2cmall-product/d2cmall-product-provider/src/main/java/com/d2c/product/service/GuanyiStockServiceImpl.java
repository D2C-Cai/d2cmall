package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.GuanyiSkuStockLog;
import com.d2c.logger.service.GuanyiSkuStockLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.GuanyiStockMapper;
import com.d2c.product.model.GuanyiStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("guanyiStockService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class GuanyiStockServiceImpl extends ListServiceImpl<GuanyiStock> implements GuanyiStockService {

    @Autowired
    private GuanyiStockMapper guanyiStockMapper;
    @Autowired
    private GuanyiSkuStockLogService guanyiSkuStockLogService;

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public GuanyiStock insert(GuanyiStock guanyiStock) {
        GuanyiStock old = null;
        if ((old = this.findOneByFieldName("sku", guanyiStock.getSku())) == null) {
            guanyiStock = this.save(guanyiStock);
        } else {
            guanyiStock.setId(old.getId());
            this.updateNotNull(guanyiStock);
        }
        GuanyiSkuStockLog guanyiSkuStockLog = new GuanyiSkuStockLog();
        guanyiSkuStockLog.setSku(guanyiStock.getSku());
        guanyiSkuStockLog.setStock(guanyiStock.getSalableQty());
        guanyiSkuStockLogService.insert(guanyiSkuStockLog);
        return guanyiStock;
    }

    @Override
    public GuanyiStock findLast() {
        return guanyiStockMapper.findLast();
    }

    @Override
    public PageResult<GuanyiStock> findBySearcher(PageModel page) {
        PageResult<GuanyiStock> pager = new PageResult<GuanyiStock>(page);
        Integer totalCount = guanyiStockMapper.countBySearcher();
        List<GuanyiStock> list = new ArrayList<>();
        if (totalCount > 0) {
            list = guanyiStockMapper.findBySearcher(page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher() {
        return guanyiStockMapper.countBySearcher();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doClean() {
        GuanyiStock guanyiStock = this.findLast();
        return guanyiStockMapper.deleteIgnoreId(guanyiStock.getId());
    }

    @Override
    public GuanyiStock findFirst() {
        return guanyiStockMapper.findFirst();
    }

}
