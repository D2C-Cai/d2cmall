package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CashCard;
import com.d2c.order.query.CashCardSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CashCardMapper extends SuperMapper<CashCard> {

    List<CashCard> findByMemberId(@Param("memberId") Long memberId, @Param("status") Integer status,
                                  @Param("expireDate") Date expireDate, @Param("pager") PageModel pager);

    int countByMemberId(@Param("memberId") Long memberId, @Param("status") Integer status,
                        @Param("expireDate") Date expireDate);

    CashCard findBySnAndPassword(@Param("code") String sn, @Param("password") String password);

    CashCard findByMemberIdAndCashCardId(@Param("id") Long cashCardId, @Param("memberInfoId") Long memberInfoId);

    List<CashCard> findBySearcher(@Param("searcher") CashCardSearcher searcher, @Param("pager") PageModel pager);

    Integer countBySearcher(@Param("searcher") CashCardSearcher searcher);

    int doConvert(@Param("id") Long id, @Param("memberInfoId") Long memberInfoId, @Param("loginCode") String loginCode,
                  @Param("accountId") Long accountId);

    int doSend(@Param("id") Long id, @Param("userName") String userName, @Param("sendMark") String sendMark);

    int doInvalid(@Param("id") Long id, @Param("userName") String userName, @Param("sendMark") String sendMark);

    int doOver(@Param("id") Long id);

}