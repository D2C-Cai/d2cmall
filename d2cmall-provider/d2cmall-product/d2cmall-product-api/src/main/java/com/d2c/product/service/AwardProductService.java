package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.product.dto.AwardProductDto;
import com.d2c.product.model.AwardProduct;
import com.d2c.product.query.AwardProductSearcher;

import java.util.List;

public interface AwardProductService {

    /**
     * 根据id查询award
     *
     * @return
     */
    AwardProduct findById(Long id);

    /**
     * 查询最大权重的商品<br>
     * 用于代替无库存奖品
     *
     * @return
     */
    AwardProduct findByMaxWeight(Long sessionId);

    /**
     * 根据查询条件查询出分页类
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<AwardProductDto> findBySearcher(AwardProductSearcher searcher, PageModel page);

    /**
     * 根据条件查询出数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(AwardProductSearcher searcher);

    /**
     * 插入一条记录
     *
     * @param awardProduct
     * @return
     */
    AwardProduct insert(AwardProduct awardProduct);

    /**
     * 更新一条记录
     *
     * @param awardProduct
     * @return
     */
    int update(AwardProduct awardProduct);

    /**
     * 奖品减库存
     *
     * @param level
     * @return
     */
    int updateCouponQuantity(Long id);

    /**
     * 上下架
     *
     * @param enable
     * @param id
     */
    int doMark(Integer enable, Long id);

    /**
     * 查询该场次下奖品列表
     *
     * @param sessionId
     * @return
     */
    List<AwardProduct> findBySessionIdAndNow(Long sessionId);

}
