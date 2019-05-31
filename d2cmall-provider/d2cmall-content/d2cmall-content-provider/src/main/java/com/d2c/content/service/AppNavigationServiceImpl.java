package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.AppNavigationMapper;
import com.d2c.content.model.AppNavigation;
import com.d2c.content.query.AppNavigationSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "appNavigationService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = Exception.class)
public class AppNavigationServiceImpl extends ListServiceImpl<AppNavigation> implements AppNavigationService {

    @Autowired
    private AppNavigationMapper appNavigationMapper;

    @Override
    @CacheEvict(value = "app_navigation", allEntries = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    public AppNavigation insert(AppNavigation appNavigation) {
        return super.save(appNavigation);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    @CacheEvict(value = "app_navigation", allEntries = true)
    public int update(AppNavigation appNavigation) {
        return super.updateNotNull(appNavigation);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    @CacheEvict(value = "app_navigation", allEntries = true)
    public int updateStatus(Long id, Integer status, String operator) {
        return appNavigationMapper.updateStatus(id, status, operator);
    }

    @Override
    public AppNavigation findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public PageResult<AppNavigation> findBySearcher(AppNavigationSearcher searcher, PageModel page) {
        PageResult<AppNavigation> pager = new PageResult<>(page);
        int totalCount = appNavigationMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<AppNavigation> list = appNavigationMapper.selectBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Cacheable(value = "app_navigation", key = "'app_navigation'", unless = "#result == null")
    public List<AppNavigation> findAllEnable() {
        return appNavigationMapper.findAllEnable();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, noRollbackFor = Exception.class)
    @CacheEvict(value = "app_navigation", allEntries = true)
    public int updateBackColor(Long id, String backColor, String lastModifyMan) {
        return appNavigationMapper.updateBackColor(id, backColor, lastModifyMan);
    }

}
