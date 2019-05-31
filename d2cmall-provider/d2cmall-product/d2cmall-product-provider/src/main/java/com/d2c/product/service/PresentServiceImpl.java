package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.PresentMapper;
import com.d2c.product.model.Present;
import com.d2c.product.query.PresentSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("presentService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PresentServiceImpl extends ListServiceImpl<Present> implements PresentService {

    @Autowired
    private PresentMapper presentMapper;

    @Override
    public PageResult<Present> findBySearcher(PresentSearcher searcher, PageModel page) {
        PageResult<Present> pager = new PageResult<Present>(page);
        int totalCount = presentMapper.countBySearcher(searcher);
        List<Present> list = new ArrayList<Present>();
        if (totalCount > 0) {
            list = presentMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public Present findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    @CacheEvict(value = "presents", key = "'presents_list'")
    public int deleteById(Long id, String username) {
        int success = presentMapper.deleteById(id, username);
        return success;
    }

    @Override
    @CacheEvict(value = "presents", key = "'presents_list'")
    public int updateStatus(Long id, Integer mark, String username) {
        return presentMapper.updateStatusById(id, mark, username);
    }

    @Override
    @CacheEvict(value = "presents", key = "'presents_list'")
    public Present insert(Present Present) {
        return save(Present);
    }

    @Override
    @CacheEvict(value = "presents", key = "'presents_list'")
    public int update(Present Present) {
        return this.updateNotNull(Present);
    }

    @Override
    @CacheEvict(value = "presents", key = "'presents_list'")
    public int updateSort(Long id, Integer sort, String username) {
        return presentMapper.updateSortById(id, sort, username);
    }

    @Override
    @Cacheable(value = "presents", key = "'presents_list'", unless = "#result == null")
    public List<Present> findListBySearcher(PresentSearcher searcher, PageModel page) {
        searcher.setStatus(1);
        List<Present> presents = presentMapper.findBySearcher(searcher, page);
        return presents;
    }

}
