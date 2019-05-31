package com.d2c.order.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.AuctionOfferDto;
import com.d2c.order.model.AuctionOffer;
import com.d2c.order.query.AuctionOfferSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionOfferService {

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AuctionOffer> findBySearch(AuctionOfferSearcher searcher, PageModel page);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(AuctionOfferSearcher searcher);

    /**
     * 获取某个拍卖的最高出价列表
     *
     * @param auctionId 拍卖id
     * @return
     */
    AuctionOffer findTopByAuctionId(Long auctionId);

    /**
     * 通过查询条件和分页数据，得到符合条件的拍卖记录的分页数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AuctionOfferDto> findDtoBySearcher(AuctionOfferSearcher searcher, PageModel page);

    /**
     * 通过查询条件，得到简单的拍卖记录的集合
     *
     * @param searcher
     * @param page
     * @return
     */
    List<HelpDTO> findHelpDtos(AuctionOfferSearcher searcher, PageModel page);

    /**
     * 拍卖出价
     *
     * @param offer
     * @return
     */
    AuctionOffer insert(AuctionOffer offer);

    /**
     * 出价出局
     *
     * @param auctionId
     * @param offer
     * @return
     */
    int doOut(Long auctionId, BigDecimal offer);

    /**
     * 支付成功，更新成付款，并且更新对应的保证金数据
     *
     * @param id 拍卖记录的id
     * @return
     */
    int doSuccess(Long id);

    /**
     * 用户合并
     *
     * @param memberSourceId
     * @param memberTargetId
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

}
