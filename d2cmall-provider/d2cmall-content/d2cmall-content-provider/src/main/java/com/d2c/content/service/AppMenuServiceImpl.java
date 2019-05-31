package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.AppMenuMapper;
import com.d2c.content.model.AppMenu;
import com.d2c.content.query.AppMenuSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("appMenuService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AppMenuServiceImpl extends ListServiceImpl<AppMenu> implements AppMenuService {

    @Autowired
    private AppMenuMapper appMenuMapper;

    @Override
    public PageResult<AppMenu> findBySearcher(AppMenuSearcher searcher, PageModel page) {
        PageResult<AppMenu> pager = new PageResult<>(page);
        int totalCount = appMenuMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<AppMenu> list = appMenuMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Cacheable(value = "app_menu", key = "'app_menu_list_'+#version", unless = "#result == null")
    public List<AppMenu> findByStatus(Integer status, String version) {
        return appMenuMapper.findByStatus(status, version);
    }

    @Override
    @CacheEvict(value = "app_menu", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public AppMenu insert(AppMenu appMenu) {
        appMenu = save(appMenu);
        return appMenu;
    }

    @Override
    @CacheEvict(value = "app_menu", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(AppMenu appMenu) {
        int result = updateNotNull(appMenu);
        return result;
    }

    @Override
    @CacheEvict(value = "app_menu", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatusById(Long id, Integer status, String name) {
        int result = appMenuMapper.updateStatusById(id, status, name);
        return result;
    }

    @Override
    @CacheEvict(value = "app_menu", allEntries = true)
    public int updateSortById(Long id, Integer sort) {
        int result = appMenuMapper.updateSortById(id, sort);
        return result;
    }

    @Override
    @CacheEvict(value = "app_menu", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        int result = this.deleteById(id);
        return result;
    }

}
