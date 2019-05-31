package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.SubModuleMapper;
import com.d2c.content.model.SubModule;
import com.d2c.content.query.PageContentSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("subModuleQueryService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SubModuleQueryServiceImpl extends ListServiceImpl<SubModule> implements SubModuleQueryService {

    @Autowired
    private SubModuleMapper subModuleMapper;

    public SubModule findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Cacheable(value = "sub_module_list", key = "'sub_module_parent_' + #parent", unless = "#result == null")
    public List<SubModule> findByParent(String parent) {
        return subModuleMapper.findByParent(parent);
    }

    @Override
    @Cacheable(value = "sub_module_list", key = "'sub_module_category_'+#categoryId", unless = "#result == null")
    public List<SubModule> findByParentAndCategory(String parent, Long categoryId) {
        return subModuleMapper.findByParentAndCategory(categoryId);
    }

    public PageResult<SubModule> findBySearcher(PageContentSearcher searcher, PageModel page) {
        PageResult<SubModule> pager = new PageResult<SubModule>(page);
        int totalCount = subModuleMapper.countBySearcher(searcher);
        List<SubModule> list = new ArrayList<SubModule>();
        if (totalCount > 0) {
            list = subModuleMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    public int countBySearcher(PageContentSearcher searcher) {
        return subModuleMapper.countBySearcher(searcher);
    }

    @CacheEvict(value = "sub_module_list", key = "'sub_module_parent_' + #parent")
    public void clearCacheByParent(String parent) {
    }

    @CacheEvict(value = "sub_module_list", key = "'sub_module_category_' + #categoryId")
    public void clearCacheByCategory(Long categoryId) {
    }

    @Override
    public List<SubModule> findAllCategory(String parent) {
        return subModuleMapper.findCategory(parent);
    }

}
