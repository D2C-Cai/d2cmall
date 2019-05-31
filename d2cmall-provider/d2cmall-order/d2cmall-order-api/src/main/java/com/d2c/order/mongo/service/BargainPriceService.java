package com.d2c.order.mongo.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.mongo.model.BargainHelpDO;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.query.BargainPriceSearcher;
import com.d2c.product.model.BargainPromotion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BargainPriceService {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    BargainPriceDO findById(String id);

    /**
     * 查询自己的砍价
     *
     * @param promotionId
     * @param memberId
     * @return
     */
    BargainPriceDO findMineByPromotionId(Long promotionId, Long memberId);

    /**
     * 根据活动查询规定时间内
     *
     * @param promotionId
     * @param page
     * @return
     */
    List<BargainPriceDO> findLatest(Long promotionId, PageModel page, Date date);

    /**
     * 砍价明细列表
     *
     * @param page
     * @return
     */
    List<BargainPriceDO> findPage(PageModel page);

    /**
     * 发起砍价的列表
     *
     * @param page
     * @param searcher
     * @return
     */
    PageResult<BargainPriceDO> findBySearcher(PageModel page, BargainPriceSearcher searcher);

    /**
     * 发起砍价的数量
     *
     * @param searcher
     * @return
     */
    int countBySearcher(BargainPriceSearcher searcher);

    /**
     * 获得我的砍价清单
     *
     * @param memberId
     * @param page
     * @return
     */
    PageResult<BargainPriceDO> findMyBargainList(Long memberId, PageModel page);

    /**
     * 创建个人砍价
     *
     * @param bargainPrice
     * @return
     */
    BargainPriceDO create(BargainPriceDO bargainPrice);

    /**
     * 帮助好友砍一刀
     *
     * @param bargainPrice
     * @param bargainHelper
     * @param gradePrice
     * @param subPrice
     * @return
     */
    BigDecimal doBargain(BargainPriceDO bargainPrice, BargainHelpDO bargainHelper, BigDecimal gradePrice,
                         BigDecimal subPrice);

    /**
     * 设置等待支付
     *
     * @param bargainId
     * @return
     */
    BargainPriceDO updateBargainForPay(String bargainId);

    /**
     * 设置取消支付
     *
     * @param bargainId
     * @return
     */
    BargainPriceDO updateBargainForCancel(String bargainId);

    /**
     * 设置购买成功
     *
     * @param bargainId
     * @return
     */
    BargainPriceDO updateBargainSuccess(String bargainId);

    /**
     * 更新砍价活动
     *
     * @param bargainPromotion
     * @return
     */
    int updateBargainPromotion(BargainPromotion bargainPromotion);

}
