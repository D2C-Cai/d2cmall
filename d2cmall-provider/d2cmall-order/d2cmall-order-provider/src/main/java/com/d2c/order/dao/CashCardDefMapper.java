package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CashCardDef;
import com.d2c.order.query.CashCardDefSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashCardDefMapper extends SuperMapper<CashCardDef> {

    /**
     * 审核
     */
    int doAudit(Long defId);

    /**
     * 取消审核
     */
    int doCancelAudit(Long defId);

    /**
     * 生成制作
     */
    int doProduce(Long defId);

    Integer countBySearcher(@Param("searcher") CashCardDefSearcher searcher);

    List<CashCardDef> findBySearcher(@Param("searcher") CashCardDefSearcher searcher, @Param("pager") PageModel pager);

    CashCardDef findByCode(String code);

}