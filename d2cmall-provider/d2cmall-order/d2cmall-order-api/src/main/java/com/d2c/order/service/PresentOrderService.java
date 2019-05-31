package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.model.PresentOrder;
import com.d2c.order.query.PresentOrderSearcher;

import java.math.BigDecimal;

public interface PresentOrderService {

    PresentOrder createPresentOrder(PresentOrder presentOrder);

    PresentOrder findBySn(String sn);

    PageResult<PresentOrder> findBySearcher(PresentOrderSearcher searcher, PageModel page);

    int countBySeasrcher(PresentOrderSearcher searcher);

    BigDecimal findVirtualPresentAmount(Long memberId);

    BigDecimal findActualPresentAmount(Long memberId);

    BigDecimal findVirtualGiveAmount(Long memberId);

    BigDecimal findActualGiveAmount(Long memberId);

}
