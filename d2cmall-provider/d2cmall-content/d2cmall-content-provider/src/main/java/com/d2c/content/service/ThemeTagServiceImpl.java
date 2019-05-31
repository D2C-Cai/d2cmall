package com.d2c.content.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.ThemeTagMapper;
import com.d2c.content.dto.ThemeTagDto;
import com.d2c.content.model.ThemeTag;
import com.d2c.content.query.ThemeTagSearcher;
import com.d2c.content.search.service.ThemeSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("themeTagService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ThemeTagServiceImpl extends ListServiceImpl<ThemeTag> implements ThemeTagService {

    @Autowired
    private ThemeTagMapper themeTagMapper;
    @Reference
    private ThemeSearcherService themeSearcherService;

    @Override
    public ThemeTag findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public ThemeTag findFixedOne() {
        return themeTagMapper.findFixedOne();
    }

    @Override
    @Cacheable(value = "theme_tag_list", key = "'theme_tag_list_'+#type", unless = "#result == null")
    public List<ThemeTag> findAll(String type) {
        return themeTagMapper.findAll(type);
    }

    @Override
    public PageResult<ThemeTag> findBySearcher(ThemeTagSearcher searcher, PageModel page) {
        PageResult<ThemeTag> pager = new PageResult<ThemeTag>(page);
        Integer totalCount = themeTagMapper.countBySearcher(searcher);
        List<ThemeTag> list = new ArrayList<ThemeTag>();
        if (totalCount > 0) {
            list = themeTagMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public List<ThemeTagDto> countGroupByTag(String type, ThemeTagSearcher searcher) {
        List<ThemeTag> themeTags = new ArrayList<ThemeTag>();
        if (type != null) {
            themeTags = themeTagMapper.findAll(type);
        } else {
            themeTags = themeTagMapper.findBySearcher(searcher, new PageModel());
        }
        Map<Long, Long> map = themeSearcherService.countGroupByTag();
        List<ThemeTagDto> list = new ArrayList<ThemeTagDto>();
        for (ThemeTag tag : themeTags) {
            ThemeTagDto dto = new ThemeTagDto();
            BeanUtils.copyProperties(tag, dto);
            dto.setCount(map.get(tag.getId()) == null ? 0 : map.get(tag.getId()));
            list.add(dto);
        }
        return list;
    }

    @Override
    @CacheEvict(value = "theme_tag_list", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ThemeTag insert(ThemeTag themeTag) {
        return this.save(themeTag);
    }

    @Override
    @CacheEvict(value = "theme_tag_list", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ThemeTag themeTag) {
        ThemeTag old = themeTagMapper.selectByPrimaryKey(themeTag.getId());
        if (!old.getFix().equals(themeTag.getFix())) {
            themeSearcherService.updateFixByTagId(themeTag.getId(), themeTag.getFix());
        }
        return this.updateNotNull(themeTag);
    }

    @Override
    @CacheEvict(value = "theme_tag_list", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return themeTagMapper.updateSort(id, sort);
    }

    @Override
    @CacheEvict(value = "theme_tag_list", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return themeTagMapper.updateStatus(id, status);
    }

    @Override
    @CacheEvict(value = "theme_tag_list", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return themeTagMapper.delete(id);
    }

}
