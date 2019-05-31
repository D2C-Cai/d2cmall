package com.d2c.product.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.ProductLog;
import com.d2c.logger.model.ProductLog.ProductLogType;
import com.d2c.logger.service.ProductLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductRelationMapper;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductRelation;
import com.d2c.product.model.ProductRelation.RelationType;
import com.d2c.product.query.RelationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("productRelationService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductRelationServiceImpl extends ListServiceImpl<ProductRelation> implements ProductRelationService {

    @Autowired
    private ProductRelationMapper productRelationMapper;
    @Autowired
    private ProductLogService productLogService;

    @Override
    public PageResult<ProductRelation> findRelations(RelationSearcher searcher, PageModel page) {
        PageResult<ProductRelation> pager = new PageResult<>(page);
        int totalCount = productRelationMapper.countRelations(searcher);
        if (totalCount > 0) {
            List<ProductRelation> list = productRelationMapper.findRelations(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Caching(evict = {@CacheEvict(value = "product_relation", key = "'product_relation_'+#productRelation.sourceId"),
            @CacheEvict(value = "product_relation", key = "'product_relation_'+#productRelation.relationId")})
    public void insert(ProductRelation productRelation) {
        productRelationMapper.insertOne(productRelation);
        JSONObject info = new JSONObject();
        info.put("操作", "关联商品，关联对象id：" + productRelation.getRelationId());
        productLogService.insert(new ProductLog(productRelation.getCreator(), info.toJSONString(),
                ProductLogType.ProductR, productRelation.getSourceId(), null));
        // 反关联,sourId和relationId反一反
        Long sourId = productRelation.getSourceId();
        productRelation.setSourceId(productRelation.getRelationId());
        productRelation.setRelationId(sourId);
        productRelationMapper.insertOne(productRelation);
        JSONObject newInfo = new JSONObject();
        info.put("操作", "关联商品，关联对象id：" + productRelation.getRelationId());
        productLogService.insert(new ProductLog(productRelation.getCreator(), newInfo.toJSONString(),
                ProductLogType.ProductR, productRelation.getSourceId(), null));
    }

    @Override
    @CacheEvict(value = "product_relation", key = "'product_relation_'+#productRelation.sourceId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void update(ProductRelation productRelation) {
        this.updateNotNull(productRelation);
    }

    @Override
    @CacheEvict(value = "product_relation", key = "'product_relation_'+#sourceId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(List<ProductRelation> productRelations, Long sourceId) {
        for (ProductRelation productRelation : productRelations) {
            this.insert(productRelation);
        }
        return 1;
    }

    @Override
    public ProductRelation findOne(Long id) {
        return super.findOneById(id);
    }

    @Override
    @CacheEvict(value = "product_relation", key = "'product_relation_'+#sourceId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByTypeAndSourceId(String type, Long sourceId) {
        int result = productRelationMapper.deleteByTypeAndSourceId(type, sourceId);
        return result;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "product_relation", key = "'product_relation_'+#sourceId"),
            @CacheEvict(value = "product_relation", key = "'product_relation_'+#productRelationId")})
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteOne(String type, Long productRelationId, Long sourceId) {
        int result = productRelationMapper.deleteOne(type, productRelationId, sourceId);
        // 反解除搭配关系
        productRelationMapper.deleteOne(type, productRelationId, sourceId);
        return result;
    }

    @Override
    @CacheEvict(value = "product_relation", key = "'product_relation_'+#sourceId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(RelationType type, Long sourceId, Long productRelationId, Integer sort) {
        int result = productRelationMapper.updateSort(type, sourceId, productRelationId, sort);
        return result;
    }

    @Override
    @Cacheable(value = "product_relation", key = "'product_relation_'+#sourceId", unless = "#result == null")
    public List<Product> findRelationProducts(Long sourceId, Integer mark) {
        return productRelationMapper.findRelationProducts(sourceId, mark);
    }

    @Override
    @CacheEvict(value = "product_relation", key = "'product_relation_'+#sourceId")
    public void doClearRelationBySourceId(Long sourceId) {
    }

    @Override
    public List<Long> findSourceIdsByRelationId(Long id, String type) {
        return productRelationMapper.findSourceIdsByRelationId(id, type);
    }

    @Override
    public List<Long> findBySourceId(Long sourceId, String type) {
        return productRelationMapper.findBySourceId(sourceId, type);
    }

}
