package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.CashCardDef;
import com.d2c.order.query.CashCardDefSearcher;

import java.util.List;

public interface CashCardDefService {

    PageResult<CashCardDef> findPageBy(CashCardDefSearcher searcher, PageModel page);

    List<CashCardDef> findBy(CashCardDefSearcher searcher, PageModel page);

    CashCardDef findByCode(String code);

    CashCardDef findById(Long id);

    int count(CashCardDefSearcher searcher);

    int update(CashCardDef def);

    CashCardDef insert(CashCardDef def);

    int delete(Long defId);

    /**
     * d2c卡定义，审核通过
     *
     * @param defId
     * @return
     */
    int doAudit(Long defId);

    /**
     * d2c卡定义，取消审核
     *
     * @param defId
     * @return
     */
    int doCancelAudit(Long defId);

    /**
     * 生成d2c卡
     *
     * @param defId
     * @param username
     * @return
     */
    int doProduce(Long defId, String username);

}
