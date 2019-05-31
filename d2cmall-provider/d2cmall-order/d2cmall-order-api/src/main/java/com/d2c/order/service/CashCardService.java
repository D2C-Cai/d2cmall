package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.CashCard;
import com.d2c.order.query.CashCardSearcher;

import java.util.List;

/**
 * D2C卡,暂时不能用
 */
public interface CashCardService {

    CashCard findById(Long cashCardId);

    PageResult<CashCard> findByMemberId(Long memberInfoId, Integer status, PageModel page);

    CashCard findByMemberIdAndCashCardId(Long long1, Long memberId);

    List<CashCard> findBy(CashCardSearcher searcher, PageModel page);

    CashCard findBySnAndPassword(String sn, String password);

    PageResult<CashCard> findBySearcher(CashCardSearcher searcher, PageModel page);

    int countBySearcher(CashCardSearcher searcher);

    CashCard insert(CashCard card);

    /**
     * 领用d2c卡
     *
     * @param id
     * @param memberInfoId
     * @param loginCode
     * @param accountId
     * @return
     */
    int doConvert(Long id, Long memberInfoId, String loginCode, Long accountId);

    /**
     * 发行d2c卡
     *
     * @param id
     * @param username
     * @param sendmark
     * @return
     */
    int doSend(Long id, String username, String sendmark);

    /**
     * 作废d2c卡
     *
     * @param id
     * @param username
     * @param sendmark
     * @return
     */
    int doInvalid(Long id, String username, String sendmark);

    /**
     * 核销d2c卡
     *
     * @param cashCardId
     * @return
     */
    int doOver(Long cashCardId);

}
