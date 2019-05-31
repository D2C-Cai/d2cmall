package com.d2c.product.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.BrandCategoryMapper;
import com.d2c.product.model.BrandCategory;
import com.d2c.product.query.BrandCategorySearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("brandCategoryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class BrandCategoryServiceImpl extends ListServiceImpl<BrandCategory> implements BrandCategoryService {

    @Autowired
    private BrandCategoryMapper brandCategoryMapper;
    @Autowired
    private RedisHandler<String, JSONObject> redisHandler;

    @Cacheable(value = "brand_category", key = "'brand_category_'+#id", unless = "#result == null")
    public BrandCategory findById(Long id) {
        return super.findOneById(id);
    }

    @Cacheable(value = "brand_category_list", key = "'brand_category_list_'+#type", unless = "#result == null")
    public List<BrandCategory> findByType(String type) {
        return brandCategoryMapper.findByType(type);
    }

    @Cacheable(value = "brand_category", key = "'brand_category_'+#name+'_'+#type", unless = "#result == null")
    public BrandCategory findByNameAndType(String name, String type) {
        return brandCategoryMapper.findByNameAndType(name, type);
    }

    public BrandCategory findByCodeAndType(String code, String type) {
        return brandCategoryMapper.findByCodeAndType(code, type);
    }

    public PageResult<BrandCategory> findBySearch(BrandCategorySearcher searcher, PageModel page) {
        PageResult<BrandCategory> pager = new PageResult<BrandCategory>(page);
        int count = brandCategoryMapper.countBySearcher(searcher);
        if (count > 0) {
            pager.setTotalCount(count);
            pager.setList(brandCategoryMapper.findBySearcher(searcher, page));
        }
        return pager;
    }

    @Caching(evict = {@CacheEvict(value = "brand_category", key = "'brand_category_'+#data.name+'_'+#data.type"),
            @CacheEvict(value = "brand_category_list", key = "'brand_category_list_'+#data.type")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public BrandCategory insert(BrandCategory data) throws Exception {
        return this.save(data);
    }

    @Caching(evict = {@CacheEvict(value = "brand_category", key = "'brand_category_'+#data.id"),
            @CacheEvict(value = "brand_category", key = "'brand_category_'+#data.name+'_'+#data.type"),
            @CacheEvict(value = "brand_category_list", key = "'brand_category_list_'+#data.type")})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(BrandCategory data) throws Exception {
        BrandCategory old = this.findById(data.getId());
        if (old != null) {
            this.clearBrandCategory(old);
            return this.updateNotNull(data);
        } else {
            throw new BusinessException("该 基本数据不存在，修改失败");
        }
    }

    @Caching(evict = {@CacheEvict(value = "brand_category", allEntries = true),
            @CacheEvict(value = "brand_category_list", allEntries = true)})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer orderList) {
        return brandCategoryMapper.updateSort(id, orderList);
    }

    @Caching(evict = {@CacheEvict(value = "brand_category", allEntries = true),
            @CacheEvict(value = "brand_category_list", allEntries = true)})
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return brandCategoryMapper.updateStatus(id, status);
    }

    private void clearBrandCategory(BrandCategory old) {
        redisHandler.delete("brand_category_" + old.getId());
        redisHandler.delete("brand_category_" + old.getName() + "_" + old.getType());
        redisHandler.delete("brand_category_" + old.getType());
    }

}
