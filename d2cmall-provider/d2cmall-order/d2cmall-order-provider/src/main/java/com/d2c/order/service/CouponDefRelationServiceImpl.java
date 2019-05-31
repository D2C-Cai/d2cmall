package com.d2c.order.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.ProductLog;
import com.d2c.logger.model.ProductLog.ProductLogType;
import com.d2c.logger.service.ProductLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CouponDefRelationMapper;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDefRelation;
import com.d2c.order.model.CouponDefRelation.CouponRelationType;
import com.d2c.product.model.Product;
import com.d2c.product.query.ProductSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("couponDefRelationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CouponDefRelationServiceImpl extends ListServiceImpl<CouponDefRelation>
        implements CouponDefRelationService {

    @Autowired
    private CouponDefRelationMapper couponDefRelationMapper;
    @Autowired
    private ProductLogService productLogService;
    @Autowired
    private RedisHandler<String, Object> redisHandler;

    @Override
    public CouponDefRelation findById(Long id) {
        return couponDefRelationMapper.selectByPrimaryKey(id);
    }

    @Override
    public CouponDefRelation findByCidAndTid(Long targetId, Long couponDefId, String type) {
        return couponDefRelationMapper.findByCidAndTid(targetId, couponDefId, type);
    }

    @Override
    public List<CouponDefRelation> findByCidAndTids(List<Long> targetIds, Long couponDefId, String type) {
        return couponDefRelationMapper.findByCidAndTids(targetIds, couponDefId, type);
    }

    @Override
    public PageResult<Product> findProductsBySearcher(ProductSearcher searcher, Long defineId, PageModel page) {
        PageResult<Product> pager = new PageResult<>(page);
        int totalCount = couponDefRelationMapper.countBySearcher(searcher, defineId);
        if (totalCount > 0) {
            List<Product> list = couponDefRelationMapper.findBySearcher(page, searcher, defineId);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    @Cacheable(value = "product_coupon_list", key = "'product_coupon_DESIGNER_'+#designerId", unless = "#result == null")
    public List<CouponDef> findCouponsByDesigner(Long designerId) {
        List<CouponDef> couponDefs = couponDefRelationMapper.findCouponsByDesigner(designerId);
        if (couponDefs == null) {
            couponDefs = new ArrayList<>();
        }
        return couponDefs;
    }

    @Override
    @Cacheable(value = "product_coupon_list", key = "'product_coupon_PRODUCT_'+#productId", unless = "#result == null")
    public List<CouponDef> findCouponsByProduct(Long productId) {
        List<CouponDef> couponDefs = couponDefRelationMapper.findCouponsByProduct(productId);
        if (couponDefs == null) {
            couponDefs = new ArrayList<>();
        }
        return couponDefs;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product_coupon_list", key = "'product_coupon_DESIGNER_'+#record.targetId", condition = "#record.type=='DESIGNER'"),
            @CacheEvict(value = "product_coupon_list", key = "'product_coupon_PRODUCT_'+#record.targetId", condition = "#record.type=='PRODUCT'")})
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CouponDefRelation insert(CouponDefRelation record) {
        this.deleteByCidAndTid(record.getTargetId(), record.getCouponDefId(), record.getType(), record.getCreator());
        CouponDefRelation couponDefRelation = save(record);
        if (record.getType().equals(CouponRelationType.PRODUCT.toString()) && record != null) {
            JSONObject info = new JSONObject();
            info.put("操作", "关联优惠券定义，优惠券定义Id:" + record.getCouponDefId());
            productLogService.insert(new ProductLog(record.getCreator(), info.toJSONString(), ProductLogType.CouponR,
                    record.getTargetId(), null));
        }
        return couponDefRelation;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product_coupon_list", key = "'product_coupon_DESIGNER_'+#record.targetId", condition = "#record.type=='DESIGNER'"),
            @CacheEvict(value = "product_coupon_list", key = "'product_coupon_PRODUCT_'+#record.targetId", condition = "#record.type=='PRODUCT'")})
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(CouponDefRelation record) {
        return this.updateNotNull(record);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return deleteById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "product_coupon_list", key = "'product_coupon_DESIGNER_'+#targetId", condition = "#type=='DESIGNER'"),
            @CacheEvict(value = "product_coupon_list", key = "'product_coupon_PRODUCT_'+#targetId", condition = "#type=='PRODUCT'")})
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByCidAndTid(Long targetId, Long couponDefId, String type, String operator) {
        int success = couponDefRelationMapper.deleteByCidAndTid(targetId, couponDefId, type);
        if (success > 0 && type.equals(CouponRelationType.PRODUCT.toString())) {
            JSONObject info = new JSONObject();
            info.put("操作", "取消关联优惠券定义，优惠券定义Id:" + couponDefId);
            productLogService
                    .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.CouponR, targetId, null));
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByTargetId(Long targetId, String type) {
        return couponDefRelationMapper.deleteByTargetId(targetId, type);
    }

    @Override
    public void clearProductCouponListCache(Long couponId) {
        List<CouponDefRelation> list = couponDefRelationMapper.selectByFieldName("coupon_def_id", couponId);
        List<String> keys = new ArrayList<>();
        for (CouponDefRelation cdr : list) {
            String key = "product_coupon_" + cdr.getType() + "_" + cdr.getTargetId();
            keys.add(key);
        }
        redisHandler.delete(keys);
    }

}
