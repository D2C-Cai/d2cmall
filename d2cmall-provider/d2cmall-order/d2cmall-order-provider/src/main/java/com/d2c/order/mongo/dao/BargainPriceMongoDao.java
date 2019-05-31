package com.d2c.order.mongo.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.order.mongo.model.BargainPriceDO;
import com.d2c.order.mongo.model.BargainPriceDO.BargainStatus;
import com.d2c.order.query.BargainPriceSearcher;
import com.d2c.product.model.BargainPromotion;
import com.d2c.util.string.StringUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class BargainPriceMongoDao extends ListMongoDao<BargainPriceDO> {

    /**
     * 查询自己的砍价
     *
     * @param promotionId
     * @param memberId
     * @return
     */
    public BargainPriceDO findMineByPromotionId(Long promotionId, Long memberId) {
        return this.findOne(new Query(Criteria.where("bargainId").is(promotionId).and("memberId").is(memberId)));
    }

    /**
     * 查找我的砍价列表
     *
     * @param memberId
     * @param page
     * @param limit
     * @return
     */
    public List<BargainPriceDO> findMyBargain(Long memberId, Integer page, Integer limit) {
        return this.findPage(new Query(Criteria.where("memberId").is(memberId)), page, limit, "createDate", false);
    }

    /**
     * 统计我的砍价活动数量
     *
     * @param memberId
     * @return
     */
    public long countMyBargain(Long memberId) {
        return this.count(new Query(Criteria.where("memberId").is(memberId)));
    }

    /**
     * 后台分页查找
     *
     * @param searcher
     * @param page
     * @return
     */
    public List<BargainPriceDO> findBySearcher(BargainPriceSearcher searcher, PageModel page) {
        return this.findPage(buildQuery(searcher), page.getPageNumber(), page.getPageSize(), "createDate", false);
    }

    public long countBySearcher(BargainPriceSearcher searcher) {
        return this.count(buildQuery(searcher));
    }

    private Query buildQuery(BargainPriceSearcher searcher) {
        Query query = new Query();
        if (searcher != null) {
            Criteria criteria = new Criteria();
            // 加查询条件
            if (searcher.getMemberId() != null) {
                criteria.and("memberId").is(searcher.getMemberId());
            }
            if (searcher.getBargainId() != null) {
                criteria.and("bargainId").is(searcher.getBargainId());
            }
            if (StringUtil.isNotBlank(searcher.getStatus())) {
                criteria.and("status").is(searcher.getStatus());
            }
            if (searcher.getStatusArray() != null && searcher.getStatusArray().length > 0) {
                criteria.and("status").in(Arrays.asList(searcher.getStatusArray()));
            }
            if (StringUtil.isNotBlank(searcher.getProductName())) {
                criteria.and("productName").is(searcher.getProductName());
            }
            if (StringUtil.isNotBlank(searcher.getLoginCode())) {
                criteria.and("loginCode").is(searcher.getLoginCode());
            }
            if (searcher.getStartDate() != null && searcher.getEndDate() != null) {
                criteria.and("createDate").gte(searcher.getStartDate()).lte(searcher.getEndDate());
            }
            if (searcher.getStartDate() != null && searcher.getEndDate() == null) {
                criteria.and("createDate").gte(searcher.getStartDate());
            }
            if (searcher.getStartDate() == null && searcher.getEndDate() != null) {
                criteria.and("createDate").lte(searcher.getEndDate());
            }
            query.addCriteria(criteria);
        }
        return query;
    }

    /**
     * 更新砍价后的价格
     *
     * @param bargainPrice
     * @return
     */
    public long updatePrice(BargainPriceDO bargainPrice) {
        Update update = new Update();
        update.set("price", bargainPrice.getPrice());
        return this.update(new Query(Criteria.where("_id").is(bargainPrice.getId())), update).getModifiedCount();
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public BargainPriceDO updateStatus(String id, BargainStatus status) {
        return this.updateOne(new Query(Criteria.where("_id").is(id)), Update.update("status", status.name()));
    }

    /**
     * 设置为购买成功
     *
     * @param id
     * @return
     */
    public BargainPriceDO updateBargainSuccess(String id) {
        return this.updateOne(new Query(Criteria.where("_id").is(id).and("status").is(BargainStatus.ORDERED.name())),
                Update.update("status", BargainStatus.BUYED.name()));
    }

    /**
     * 更新活动
     *
     * @param bargainPromotion
     * @return
     */
    public int updateBargainPromotion(BargainPromotion bargainPromotion) {
        UpdateResult result = this.update(
                new Query(Criteria.where("bargainId").is(bargainPromotion.getId())
                        .andOperator(Criteria.where("status")
                                .nin(Arrays.asList(
                                        new String[]{BargainStatus.BUYED.name(), BargainStatus.ORDERED.name()})))),
                Update.update("bargain", bargainPromotion));
        if (result != null && result.getModifiedCount() > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 查询特定时间内砍价
     *
     * @param promotionId
     * @param date
     * @param page
     * @return
     */
    public List<BargainPriceDO> findLatest(Long promotionId, Date date, PageModel page) {
        Criteria criteria = Criteria.where("bargainId").is(promotionId);
        if (date != null) {
            criteria.and("gmtModified").gte(date);
        }
        return this.findPage(new Query(criteria), page.getPageNumber(), page.getPageSize(), "gmtModified", false);
    }

}
