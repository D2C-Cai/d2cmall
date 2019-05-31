package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CashCardMapper;
import com.d2c.order.model.CashCard;
import com.d2c.order.query.CashCardSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("cashCardService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CashCardServiceImpl extends ListServiceImpl<CashCard> implements CashCardService {

    @Autowired
    private CashCardMapper cashCardMapper;

    @Override
    public CashCard findById(Long id) {
        return this.cashCardMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult<CashCard> findByMemberId(Long memberInfoId, Integer status, PageModel page) {
        PageResult<CashCard> pager = new PageResult<>(page);
        Integer totalCount = cashCardMapper.countByMemberId(memberInfoId, status, new Date());
        List<CashCard> list = new ArrayList<>();
        if (totalCount > 0) {
            list = cashCardMapper.findByMemberId(memberInfoId, status, new Date(), page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public CashCard findByMemberIdAndCashCardId(Long cashCardId, Long memberInfoId) {
        return cashCardMapper.findByMemberIdAndCashCardId(cashCardId, memberInfoId);
    }

    @Override
    public List<CashCard> findBy(CashCardSearcher searcher, PageModel page) {
        return cashCardMapper.findBySearcher(searcher, page);
    }

    @Override
    public CashCard findBySnAndPassword(String sn, String password) {
        return cashCardMapper.findBySnAndPassword(sn, password);
    }

    @Override
    public PageResult<CashCard> findBySearcher(CashCardSearcher searcher, PageModel page) {
        PageResult<CashCard> pager = new PageResult<>(page);
        Integer totalCount = cashCardMapper.countBySearcher(searcher);
        List<CashCard> list = new ArrayList<>();
        if (totalCount > 0) {
            list = cashCardMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(CashCardSearcher searcher) {
        return cashCardMapper.countBySearcher(searcher);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CashCard insert(CashCard cashCard) {
        save(cashCard);
        return cashCard;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doConvert(Long id, Long memberInfoId, String loginCode, Long accountId) {
        return cashCardMapper.doConvert(id, memberInfoId, loginCode, accountId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doSend(Long cashCardId, String username, String sendmark) {
        return cashCardMapper.doSend(cashCardId, username, sendmark);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doInvalid(Long cashCardId, String username, String sendmark) {
        return cashCardMapper.doInvalid(cashCardId, username, sendmark);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doOver(Long cashCardId) {
        return cashCardMapper.doOver(cashCardId);
    }

}
