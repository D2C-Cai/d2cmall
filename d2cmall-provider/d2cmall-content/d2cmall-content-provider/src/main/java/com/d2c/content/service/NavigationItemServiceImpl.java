package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.NavigationItemMapper;
import com.d2c.content.dto.NavigationItemDto;
import com.d2c.content.model.NavigationItem;
import com.d2c.content.query.NavigationItemSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("navigationItemService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class NavigationItemServiceImpl extends ListServiceImpl<NavigationItem> implements NavigationItemService {

    @Autowired
    private NavigationItemMapper navigationItemMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public NavigationItem insert(NavigationItem item) {
        return this.save(item);
    }

    public NavigationItem findById(Long id) {
        return this.findOneById(id);
    }

    public PageResult<NavigationItemDto> findBySearch(NavigationItemSearcher searcher, PageModel page) {
        PageResult<NavigationItemDto> pager = new PageResult<NavigationItemDto>(page);
        List<NavigationItemDto> dtos = new ArrayList<NavigationItemDto>();
        int count = navigationItemMapper.countBySearch(searcher);
        if (count > 0) {
            List<NavigationItem> list = navigationItemMapper.findBySearch(searcher, page);
            for (NavigationItem nt : list) {
                NavigationItemDto dto = new NavigationItemDto();
                BeanUtils.copyProperties(nt, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(count);
        pager.setList(dtos);
        return pager;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(NavigationItem item) {
        return this.updateNotNull(item);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return navigationItemMapper.delete(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doUp(Long id) {
        return navigationItemMapper.doUp(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDown(Long id) {
        return navigationItemMapper.doDown(id);
    }

}
