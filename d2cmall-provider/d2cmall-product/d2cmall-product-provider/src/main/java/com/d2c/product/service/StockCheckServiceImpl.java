package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.StockCheckMapper;
import com.d2c.product.model.StockCheck;
import com.d2c.product.query.StockCheckItemSearcher;
import com.d2c.product.query.StockCheckSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("stockCheckService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class StockCheckServiceImpl extends ListServiceImpl<StockCheck> implements StockCheckService {

    @Autowired
    private StockCheckMapper stockCheckMapper;
    @Autowired
    private StockCheckItemService stockCheckItemService;

    @Override
    public StockCheck findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public PageResult<StockCheck> findBySearch(StockCheckSearcher searcher, PageModel page) {
        PageResult<StockCheck> pager = new PageResult<StockCheck>(page);
        int totalCount = stockCheckMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<StockCheck> list = stockCheckMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public int countBySearch(StockCheckSearcher searcher) {
        return stockCheckMapper.countBySearch(searcher);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public StockCheck insert(StockCheck stockCheck) {
        stockCheck = this.save(stockCheck);
        if (stockCheck.getType() == 1) {
            stockCheckItemService.doInit(stockCheck.getStoreCode(), stockCheck.getId());
        }
        return stockCheck;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        StockCheck stockCheck = this.findById(id);
        int success = stockCheckMapper.updateStatus(id, status);
        if (success > 0) {
            if (stockCheck.getStatus() == 0 && status == 1) {
                StockCheckItemSearcher searcher = new StockCheckItemSearcher();
                searcher.setCheckId(id);
                searcher.setUnfilled(1);
                int count = stockCheckItemService.countBySearch(searcher);
                if (count > 0) {
                    throw new BusinessException("存在未填写的盘点单明细，提交不成功！");
                }
                stockCheckMapper.doSumQuantity(id);
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateMemo(Long id, String memo) {
        return stockCheckMapper.updateMemo(id, memo);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        int success = this.deleteById(id);
        if (success > 0) {
            stockCheckItemService.deleteByCheckId(id);
        }
        return success;
    }

}
