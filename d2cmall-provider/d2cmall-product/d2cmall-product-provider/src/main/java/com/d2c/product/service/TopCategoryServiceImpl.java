package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.TopCategoryMapper;
import com.d2c.product.model.TopCategory;
import com.d2c.product.query.TopCategorySearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("topCategoryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class TopCategoryServiceImpl extends ListServiceImpl<TopCategory> implements TopCategoryService {

    @Autowired
    private TopCategoryMapper topCategoryMapper;

    public PageResult<TopCategory> findBySearch(TopCategorySearcher searcher, PageModel page) {
        PageResult<TopCategory> pager = new PageResult<TopCategory>(page);
        int totalCount = topCategoryMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<TopCategory> list = topCategoryMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    public List<TopCategory> findAll(Integer status) {
        return topCategoryMapper.findAll(status);
    }

    @Cacheable(value = "top_category", key = "'top_category_'+#id", unless = "#result == null")
    public TopCategory findById(Long id) {
        return super.findOneById(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public TopCategory insert(TopCategory topCategory) {
        return this.save(topCategory);
    }

    @CacheEvict(value = "top_category", key = "'top_category_'+#topCategory.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(TopCategory topCategory) {
        return this.updateNotNull(topCategory);
    }

    @CacheEvict(value = "top_category", key = "'top_category_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return topCategoryMapper.updateStatus(id, status);
    }

    @Override
    public TopCategory findByCode(String code) {
        return topCategoryMapper.findByCode(code);
    }

}
