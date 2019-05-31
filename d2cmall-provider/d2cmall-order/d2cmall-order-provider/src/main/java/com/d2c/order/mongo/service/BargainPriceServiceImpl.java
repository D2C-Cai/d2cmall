package com.d2c.order.mongo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.logger.support.SmsBean;
import com.d2c.order.mongo.dao.BargainHelpMongoDao;
import com.d2c.order.mongo.dao.BargainPriceMongoDao;
import com.d2c.order.mongo.model.BargainHelpDO;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.mongo.model.BargainPriceDO.BargainStatus;
import com.d2c.order.query.BargainPriceSearcher;
import com.d2c.product.model.BargainPromotion;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BargainPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("bargainPriceService")
public class BargainPriceServiceImpl implements BargainPriceService {

    @Autowired
    private BargainPriceMongoDao bargainPriceMongoDao;
    @Autowired
    private BargainHelpMongoDao bargainHelpMongoDao;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private BargainPromotionService bargainPromotionService;
    @Autowired
    private MsgUniteService msgUniteService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BargainPriceDO findById(String id) {
        return bargainPriceMongoDao.findById(id);
    }

    @Override
    public BargainPriceDO findMineByPromotionId(Long promotionId, Long memberId) {
        return bargainPriceMongoDao.findMineByPromotionId(promotionId, memberId);
    }

    @Override
    public List<BargainPriceDO> findLatest(Long promotionId, PageModel page, Date date) {
        return bargainPriceMongoDao.findLatest(promotionId, date, page);
    }

    @Override
    public List<BargainPriceDO> findPage(PageModel page) {
        return bargainPriceMongoDao.findBySearcher(null, page);
    }

    @Override
    public PageResult<BargainPriceDO> findBySearcher(PageModel page, BargainPriceSearcher searcher) {
        PageResult<BargainPriceDO> pager = new PageResult<>(page);
        long totalCount = bargainPriceMongoDao.countBySearcher(searcher);
        if (totalCount > 0) {
            List<BargainPriceDO> list = bargainPriceMongoDao.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount((int) totalCount);
        return pager;
    }

    @Override
    public int countBySearcher(BargainPriceSearcher searcher) {
        return (int) bargainPriceMongoDao.countBySearcher(searcher);
    }

    @Override
    public PageResult<BargainPriceDO> findMyBargainList(Long memberId, PageModel page) {
        PageResult<BargainPriceDO> dtos = new PageResult<>(page);
        long totalCount = bargainPriceMongoDao.countMyBargain(memberId);
        if (totalCount > 0) {
            List<BargainPriceDO> list = bargainPriceMongoDao.findMyBargain(memberId, page.getPageNumber(),
                    page.getPageSize());
            dtos.setList(list);
        }
        dtos.setTotalCount((int) totalCount);
        return dtos;
    }

    @Override
    public BargainPriceDO create(BargainPriceDO bargainPrice) {
        bargainPrice = bargainPriceMongoDao.insert(bargainPrice);
        if (bargainPrice.getId() != null) {
            bargainPromotionService.updateCount(bargainPrice.getBargainId());
        }
        return bargainPrice;
    }

    @Override
    public BigDecimal doBargain(BargainPriceDO bargainPrice, BargainHelpDO bargainHelper, BigDecimal gradePrice,
                                BigDecimal subPrice) {
        // 防止并发提交的锁
        final String bargainKey = "bargain_price_" + bargainPrice.getId();
        try {
            Boolean exist = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.exists(bargainKey.getBytes());
                }
            });
            if (exist != null && exist.booleanValue()) {
                throw new BusinessException("您操作过快，不要心急哦！");
            } else {
                redisTemplate.execute(new RedisCallback<Long>() {
                    @Override
                    public Long doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.set(bargainKey.getBytes(), String.valueOf(bargainPrice.getId()).getBytes());
                        return 1L;
                    }
                });
            }
            bargainPriceMongoDao.updatePrice(bargainPrice);
            bargainHelpMongoDao.insert(bargainHelper);
            if (bargainPrice.getPrice() < gradePrice.doubleValue()
                    && bargainPrice.getPrice() + subPrice.doubleValue() >= gradePrice.doubleValue()) {
                String subject = "砍价活动降价通知";
                String content = "您发起的砍价商品" + bargainPrice.getProductName() + "已经砍至" + bargainPrice.getPrice()
                        + "元了，邀请好友砍至最低价";
                PushBean pushBean = new PushBean(bargainPrice.getMemberId(), content, 61);
                pushBean.setAppUrl("/bargain/promotion/list");
                MsgBean msgBean = new MsgBean(bargainPrice.getMemberId(), 61, subject, content);
                msgBean.setAppUrl("/bargain/promotion/list");
                msgBean.setPic(bargainPrice.getBargain().getCoverPic());
                SmsBean smsBean = new SmsBean(null, bargainPrice.getLoginCode(), SmsLogType.REMIND, content);
                msgUniteService.sendMsg(smsBean, pushBean, msgBean, null);
            }
        } finally {
            redisTemplate.execute(new RedisCallback<Long>() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.del(bargainKey.getBytes());
                    return 1L;
                }
            });
        }
        return subPrice;
    }

    @Override
    public BargainPriceDO updateBargainForPay(String bargainId) {
        return bargainPriceMongoDao.updateStatus(bargainId, BargainStatus.ORDERED);
    }

    @Override
    public BargainPriceDO updateBargainForCancel(String bargainId) {
        return bargainPriceMongoDao.updateStatus(bargainId, BargainStatus.BEGIN);
    }

    @Override
    public BargainPriceDO updateBargainSuccess(String bargainId) {
        return bargainPriceMongoDao.updateBargainSuccess(bargainId);
    }

    @Override
    public int updateBargainPromotion(BargainPromotion bargainPromotion) {
        return bargainPriceMongoDao.updateBargainPromotion(bargainPromotion);
    }

}
