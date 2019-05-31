package com.d2c.content.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.ThemeMapper;
import com.d2c.content.dto.ThemeDto;
import com.d2c.content.model.Theme;
import com.d2c.content.model.ThemeTag;
import com.d2c.content.query.ThemeSearcher;
import com.d2c.content.search.model.SearcherTheme;
import com.d2c.content.search.service.ThemeSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("themeService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ThemeServiceImpl extends ListServiceImpl<Theme> implements ThemeService {

    @Autowired
    private ThemeMapper themeMapper;
    @Reference
    private ThemeSearcherService themeSearcherService;
    @Autowired
    private ThemeTagService themeTagService;

    @Override
    public Theme findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public PageResult<ThemeDto> findDtoBySearcher(ThemeSearcher searcher, PageModel page) {
        PageResult<ThemeDto> pager = new PageResult<>(page);
        Integer totalCount = themeMapper.countBySearcher(searcher);
        List<ThemeTag> tags = themeTagService.findAll(searcher.getType());
        Map<Long, ThemeTag> tagMap = new HashMap<>();
        for (ThemeTag tag : tags) {
            tagMap.put(tag.getId(), tag);
        }
        List<ThemeDto> dtoList = new ArrayList<>();
        if (totalCount > 0) {
            List<Theme> list = themeMapper.findBySearcher(searcher, page);
            for (Theme theme : list) {
                ThemeDto dto = new ThemeDto();
                BeanUtils.copyProperties(theme, dto);
                dto.setTagName(tagMap.get(theme.getTagId()) == null ? null : tagMap.get(theme.getTagId()).getName());
                dto.setFix(tagMap.get(theme.getTagId()) == null ? null : tagMap.get(theme.getTagId()).getFix());
                dtoList.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtoList);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Theme insert(Theme theme) {
        theme = this.save(theme);
        if (theme.getId() > 0) {
            SearcherTheme searcherTheme = new SearcherTheme();
            BeanUtils.copyProperties(theme, searcherTheme, new String[]{"pcContent", "WapContent", "sharePic"});
            if (theme.getTagId() != null) {
                ThemeTag themeTag = themeTagService.findById(theme.getTagId());
                if (themeTag != null) {
                    searcherTheme.setTagName(themeTag.getName());
                    searcherTheme.setFix(themeTag.getFix());
                }
            }
            themeSearcherService.insert(searcherTheme);
        }
        return theme;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Theme theme) {
        int success = this.updateNotNull(theme);
        if (success > 0) {
            SearcherTheme searcherTheme = new SearcherTheme();
            BeanUtils.copyProperties(theme, searcherTheme, new String[]{"pcContent", "WapContent", "sharePic"});
            if (theme.getTagId() != null) {
                ThemeTag themeTag = themeTagService.findById(theme.getTagId());
                if (themeTag != null) {
                    searcherTheme.setTagName(themeTag.getName());
                    searcherTheme.setFix(themeTag.getFix());
                }
            }
            themeSearcherService.rebuild(searcherTheme);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        int success = themeMapper.updateSort(id, sort);
        if (success > 0) {
            themeSearcherService.updateSort(id, sort);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        int success = themeMapper.updateStatus(id, status);
        if (success > 0) {
            themeSearcherService.updateStatus(id, status);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateRecommend(Long id, Integer recommend) {
        int success = themeMapper.updateRecommend(id, recommend);
        if (success > 0) {
            themeSearcherService.updateRecommend(id, recommend);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        int success = themeMapper.delete(id);
        if (success > 0) {
            themeSearcherService.remove(id);
        }
        return success;
    }

    @Override
    public PageResult<Theme> findByTagId(Long tagId, PageModel page) {
        PageResult<Theme> pager = new PageResult<>(page);
        ThemeSearcher searcher = new ThemeSearcher();
        searcher.setTagId(tagId);
        searcher.setStatus(1);
        int totalCount = themeMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<Theme> list = themeMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

}
