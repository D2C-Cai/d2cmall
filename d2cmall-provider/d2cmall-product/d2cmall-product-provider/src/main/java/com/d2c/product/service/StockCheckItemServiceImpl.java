package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.StockCheckItemMapper;
import com.d2c.product.model.StockCheckItem;
import com.d2c.product.query.StockCheckItemSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("stockCheckItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class StockCheckItemServiceImpl extends ListServiceImpl<StockCheckItem> implements StockCheckItemService {

    @Autowired
    private StockCheckItemMapper stockCheckItemMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doInit(String storeCode, Long checkId) {
        return stockCheckItemMapper.doInit(storeCode, checkId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public StockCheckItem insert(StockCheckItem stockCheckItem) {
        return this.save(stockCheckItem);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(StockCheckItem stockCheckItem) {
        return this.updateNotNull(stockCheckItem);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return this.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByCheckId(Long checkId) {
        return stockCheckItemMapper.deleteByCheckId(checkId);
    }

    @Override
    public StockCheckItem findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public StockCheckItem findOne(Long checkId, String barCode) {
        return stockCheckItemMapper.findOne(checkId, barCode);
    }

    @Override
    public PageResult<StockCheckItem> findBySearch(StockCheckItemSearcher searcher, PageModel page) {
        PageResult<StockCheckItem> pager = new PageResult<StockCheckItem>(page);
        int totalCount = stockCheckItemMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<StockCheckItem> list = stockCheckItemMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public int countBySearch(StockCheckItemSearcher searcher) {
        return stockCheckItemMapper.countBySearch(searcher);
    }

}
