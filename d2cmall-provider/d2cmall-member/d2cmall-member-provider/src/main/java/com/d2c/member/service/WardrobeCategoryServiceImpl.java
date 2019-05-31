package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.WardrobeCategoryMapper;
import com.d2c.member.model.WardrobeCategory;
import com.d2c.member.query.WardrobeCategorySearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("wardrobeCategoryService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class WardrobeCategoryServiceImpl extends ListServiceImpl<WardrobeCategory> implements WardrobeCategoryService {

    @Autowired
    private WardrobeCategoryMapper wardrobeCategoryMapper;

    @Override
    @CacheEvict(value = "wardrobe_category", allEntries = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public WardrobeCategory insert(WardrobeCategory wardrobeCategory) {
        return this.save(wardrobeCategory);
    }

    @Override
    @CacheEvict(value = "wardrobe_category", allEntries = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int update(WardrobeCategory wardrobeCategory) {
        return this.updateNotNull(wardrobeCategory);
    }

    @Override
    public PageResult<WardrobeCategory> findBySearcher(WardrobeCategorySearcher query, PageModel page) {
        PageResult<WardrobeCategory> pager = new PageResult<WardrobeCategory>(page);
        Integer totalCount = wardrobeCategoryMapper.countBySearcher(query);
        List<WardrobeCategory> list = new ArrayList<>();
        if (totalCount > 0) {
            list = wardrobeCategoryMapper.findBySearcher(query, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public WardrobeCategory findById(Long id) {
        return wardrobeCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    @Cacheable(value = "wardrobe_category", key = "'wardrobe_category_top_'+#topName+'_'+#status", unless = "#result == null")
    public List<WardrobeCategory> findByTop(String topName, Integer status) {
        return wardrobeCategoryMapper.findByTop(topName, status);
    }

    @Override
    @CacheEvict(value = "wardrobe_category", allEntries = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateStatus(Long id, Integer status) {
        return wardrobeCategoryMapper.updateFieldById(id.intValue(), "status", status);
    }

    @Override
    @CacheEvict(value = "wardrobe_category", allEntries = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateSort(Long id, Integer sort) {
        return wardrobeCategoryMapper.updateFieldById(id.intValue(), "sort", sort);
    }

    @Override
    public WardrobeCategory findByName(String name) {
        return wardrobeCategoryMapper.findByName(name);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int delete(Long id) {
        return wardrobeCategoryMapper.delete(id);
    }

}
