package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.GuanyiOrderDto;
import com.d2c.order.model.GuanyiOrder;
import com.d2c.order.query.GuanyiOrderSearcher;

public interface GuanyiOrderService {

    GuanyiOrder insert(GuanyiOrderDto guanyiOrder);

    PageResult<GuanyiOrderDto> findBySearch(GuanyiOrderSearcher search, PageModel page);

    /**
     * 人工处理
     *
     * @param id
     * @param type          1为物流 2为伯俊推单
     * @param handleMan     处理人
     * @param handleContent 处理内容
     * @return
     */
    int doHandle(Long id, Integer type, String handleMan, String handleContent);

    GuanyiOrder findById(Long id);

    GuanyiOrder findLastDeliverOrder();

    Integer countBySearch(GuanyiOrderSearcher search);

    /**
     * 管易推送做单
     *
     * @param guanyiOrderDto
     * @param reProcess      是否重新做单
     * @param type           1为物流重做 2为伯俊推单重做
     * @param handleMan      处理人
     */
    Integer processBurgeon(final GuanyiOrderDto guanyiOrderDto, boolean reProcess, String handleMan);

    Integer update(GuanyiOrder guanyiOrder);

    GuanyiOrder findByCode(String code);

}
