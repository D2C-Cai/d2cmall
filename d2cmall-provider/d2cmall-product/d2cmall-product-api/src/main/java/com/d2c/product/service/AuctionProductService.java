package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.AuctionProductDto;
import com.d2c.product.model.AuctionProduct;
import com.d2c.product.query.AuctionProductSearcher;

import java.math.BigDecimal;
import java.util.List;

public interface AuctionProductService {

    /**
     * 通过id查找拍卖商品
     *
     * @param id
     * @return
     */
    AuctionProduct findById(Long id);

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param pager
     * @return
     */
    List<AuctionProduct> findBySearcher(AuctionProductSearcher searcher, PageModel pager);

    /**
     * 根据searcher查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(AuctionProductSearcher searcher);

    /**
     * 分页查找拍卖商品数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AuctionProductDto> findDtoBySearcher(AuctionProductSearcher searcher, PageModel page);

    /**
     * 插入一条拍卖商品
     *
     * @param product
     * @return
     */
    AuctionProduct insert(AuctionProduct product);

    /**
     * 更新一条拍卖商品
     *
     * @param product
     * @return
     */
    int update(AuctionProduct product);

    /**
     * 删除一条拍卖商品，将状态设置成删除状态
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 将拍卖商品设置成上架状态
     *
     * @param id
     * @return
     */
    int doUp(Long id);

    /**
     * 将拍卖商品设置成下架状态
     *
     * @param id
     * @return
     */
    int doDown(Long id);

    /**
     * 更新当前拍卖的出价价格，和记录一条排名记录的id
     *
     * @param auctionProductId
     * @param offer
     * @param marginId
     * @param memberInfoId
     * @return
     */
    int updateCurrentPrice(Long auctionProductId, BigDecimal offer, Long marginId, Long memberInfoId);

    /**
     * 最后更新拍卖活动拍中的出价记录id
     *
     * @param id      活动id
     * @param offerId 出价记录id
     * @return
     */
    int updateOfferId(Long id, Long offerId);

    /**
     * 查询已经结束的拍卖商品
     *
     * @return
     */
    List<AuctionProduct> findExpiredAuctionProduct();

    /**
     * 排序
     *
     * @param id
     * @param sort
     * @return
     */
    int updateSort(Long id, Integer sort);

}
