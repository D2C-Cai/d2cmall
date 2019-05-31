package com.d2c.similar.mongo.dao;

import com.d2c.common.api.query.model.MongoQuery;
import com.d2c.common.mongodb.base.ListMongoDao;
import com.d2c.similar.mongo.model.SimilarDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class SimilarMongoDao extends ListMongoDao<SimilarDO> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Collection<SimilarDO> saveAll(Collection<SimilarDO> list) {
        for (SimilarDO bean : list) {
            this.save(bean);
        }
        return list;
    }

    @Override
    public SimilarDO save(SimilarDO bean) {
        try {
            bean.initId();
            return super.save(bean);
        } catch (Exception e) {
            logger.error(bean + "保存失败...", e);
            return bean;
        }
    }

    /**
     * 删除该对象的相似度
     */
    public long removeByBeanId(Object beanId) {
        return this.deleteByQuery(new Query(Criteria.where("beanId").is(beanId)));
    }

    public long removeByTargetId(Object targetId) {
        return this.deleteByQuery(new Query(Criteria.where("targetId").is(targetId)));
    }

    public SimilarDO findById(Object beanId, Object targetId) {
        return findById(beanId + "-" + targetId);
    }

    public List<SimilarDO> findByBeanIds(List<?> beanIds, Integer limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("beanId").in(beanIds));
        return findQueryPage(query, null, null, limit, "prob", false);
    }

    /**
     * 查询该品类的商品
     */
    public List<SimilarDO> findTopCategoryByIds(List<?> beanIds, Long categoryId, Integer limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("beanId").in(beanIds).and("target.productCategoryId").is(categoryId));
        return findQueryPage(query, null, null, limit, "prob", false);
    }

    /**
     * 查询有活动的商品
     */
    public List<SimilarDO> findHasPromotionByBeanIds(List<?> beanIds, Integer limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("beanId").in(beanIds).and("target.promotionId").gt(0));
        return findQueryPage(query, null, null, limit, "prob", false);
    }

    public List<SimilarDO> findByTargetId(Object targetId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("targetId").is(targetId));
        return find(query);
    }

    public long countByBeanId(Object beanId) {
        return count(new Query(Criteria.where("beanId").is(beanId)));
    }

    public long countByBeanId(Object beanId, MongoQuery search) {
        Query query = new Query();
        query.addCriteria(Criteria.where("beanId").is(beanId));
        return count(query, search);
    }

    public List<SimilarDO> findTopPage(Integer limit) {
        return findPage(null, 1, limit, "prob", false);
    }

    public List<SimilarDO> findByBeanId(Object beanId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("beanId").is(beanId));
        query.with(new Sort(Direction.DESC, "prob"));
        return find(query);
    }

    public List<SimilarDO> findByBeanId(Object beanId, Integer limit) {
        return findByBeanId(beanId, null, null, limit);
    }

    public List<SimilarDO> findByBeanId(Object beanId, MongoQuery search, Integer page, Integer limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("beanId").is(beanId));
        return findQueryPage(query, search, page, limit, "prob", false);
    }

    public List<SimilarDO> findByTargetIds(List<?> beanIds, Integer limit) {
        Query query = new Query();
        query.addCriteria(Criteria.where("targetId").in(beanIds));
        return findQueryPage(query, null, null, limit, "prob", false);
    }

}
