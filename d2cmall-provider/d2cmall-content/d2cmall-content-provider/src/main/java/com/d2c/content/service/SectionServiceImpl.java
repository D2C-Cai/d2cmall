package com.d2c.content.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.SectionMapper;
import com.d2c.content.model.Section;
import com.d2c.content.query.SectionSearcher;
import com.d2c.content.search.service.SectionSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("sectionService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SectionServiceImpl extends ListServiceImpl<Section> implements SectionService {

    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private SectionValueService sectionValueService;
    @Reference
    private SectionSearcherService sectionSearcherService;

    @Override
    public PageResult<Section> findBySearcher(SectionSearcher searcher, PageModel page) {
        PageResult<Section> pager = new PageResult<Section>(page);
        int totalCount = sectionMapper.countBySearcher(searcher);
        List<Section> list = new ArrayList<Section>();
        if (totalCount > 0) {
            list = sectionMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySearcher(SectionSearcher searcher) {
        return sectionMapper.countBySearcher(searcher);
    }

    @Override
    public Section findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Section insert(Section entity) {
        return this.save(entity);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int delete(Long id) {
        int result = deleteById(id);
        if (result > 0) {
            sectionValueService.deleteBySectionId(id);
            sectionSearcherService.deleteBySectionDefId(id.toString());
        }
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int deleteByModuleId(Long moduleId) {
        return sectionMapper.deleteByModuleId(moduleId);
    }

    @Override
    public int update(Section section) {
        return this.updateNotNull(section);
    }

}
