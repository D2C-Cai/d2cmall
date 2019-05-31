package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.RedPacketsItemMapper;
import com.d2c.order.model.RedPacketsItem;
import com.d2c.order.query.RedPacketsItemSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("redPacketsItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RedPacketsItemServiceImpl extends ListServiceImpl<RedPacketsItem> implements RedPacketsItemService {

    @Autowired
    private RedPacketsItemMapper redPacketsItemMapper;

    @Override
    public RedPacketsItem findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public PageResult<RedPacketsItem> findBySearcher(RedPacketsItemSearcher searcher, PageModel page) {
        PageResult<RedPacketsItem> pager = new PageResult<>(page);
        Integer toltalCount = redPacketsItemMapper.countBySearcher(searcher);
        List<RedPacketsItem> list = new ArrayList<>();
        if (toltalCount > 0) {
            list = redPacketsItemMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(toltalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public Integer countBySearcher(RedPacketsItemSearcher searcher) {
        return redPacketsItemMapper.countBySearcher(searcher);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public RedPacketsItem insert(RedPacketsItem redPacketsItem) {
        return this.save(redPacketsItem);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return redPacketsItemMapper.updateStatus(id, status);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doRebate(Long id, BigDecimal redAmount) {
        int success = redPacketsItemMapper.doRebate(id, redAmount);
        return success;
    }

    @Override
    public List<RedPacketsItem> findByTypeAndMember(Long memberId, String type) {
        return redPacketsItemMapper.findByTypeAndMember(memberId, type);
    }

    @Override
    public RedPacketsItem findByTransactionAndType(Long transactionId, String type) {
        return redPacketsItemMapper.findByTransactionAndType(transactionId, type);
    }

}
