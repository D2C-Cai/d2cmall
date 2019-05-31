package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CashCardDefMapper;
import com.d2c.order.model.CashCard;
import com.d2c.order.model.CashCardDef;
import com.d2c.order.query.CashCardDefSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("cashCardDefService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CashCardDefServiceImpl extends ListServiceImpl<CashCardDef> implements CashCardDefService {

    @Autowired
    private CashCardDefMapper cashCardDefMapper;
    @Autowired
    private CashCardService cashCardService;

    public PageResult<CashCardDef> findPageBy(CashCardDefSearcher searcher, PageModel page) {
        PageResult<CashCardDef> pager = new PageResult<CashCardDef>(page);
        Integer totalCount = cashCardDefMapper.countBySearcher(searcher);
        List<CashCardDef> list = new ArrayList<CashCardDef>();
        if (totalCount > 0) {
            list = cashCardDefMapper.findBySearcher(searcher, page);
        }
        pager.setList(list);
        pager.setTotalCount(totalCount);
        return pager;
    }

    public int countBySearcher(CashCardDefSearcher searcher) {
        return cashCardDefMapper.countBySearcher(searcher);
    }

    public CashCardDef findById(Long defId) {
        return this.cashCardDefMapper.selectByPrimaryKey(defId);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CashCardDef insert(CashCardDef def) {
        this.cashCardDefMapper.insert(def);
        return def;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int update(CashCardDef def) {
        return this.cashCardDefMapper.updateByPrimaryKey(def);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int delete(Long defId) {
        return deleteById(defId);
    }

    /**
     * 审核
     *
     * @param defId
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doAudit(Long defId) {
        int success = this.cashCardDefMapper.doAudit(defId);
        return success;
    }

    /**
     * 取消审核
     *
     * @param defId
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doCancelAudit(Long defId) {
        int success = this.cashCardDefMapper.doCancelAudit(defId);
        return success;
    }

    /**
     * 制作生成d2c卡券
     *
     * @param defId
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int doProduce(Long defId, String creator) {
        CashCardDef def = this.cashCardDefMapper.selectByPrimaryKey(defId);
        int success = 0;
        if (def != null) {
            success = this.cashCardDefMapper.doProduce(defId);
            if (success > 0 && def.getStatus() == 1) {
                for (int i = 0; i < def.getQuantity(); i++) {
                    CashCard card = def.createCashCard(i + 1);
                    card.setCreator(creator);
                    cashCardService.insert(card);
                }
            } else {
                throw new BusinessException("d2c卡券已经生成或删除");
            }
        } else {
            throw new BusinessException("d2c卡券不存在");
        }
        return success;
    }

    public CashCardDef findByCode(String code) {
        return cashCardDefMapper.findByCode(code);
    }

    @Override
    public List<CashCardDef> findBy(CashCardDefSearcher searcher, PageModel page) {
        return cashCardDefMapper.findBySearcher(searcher, page);
    }

    @Override
    public int count(CashCardDefSearcher searcher) {
        return cashCardDefMapper.countBySearcher(searcher);
    }

}
