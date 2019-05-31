package com.d2c.content.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.SectionMapper;
import com.d2c.content.dao.SectionValueMapper;
import com.d2c.content.dto.SectionValueDto;
import com.d2c.content.model.Section;
import com.d2c.content.model.SectionValue;
import com.d2c.content.query.SectionValueSearcher;
import com.d2c.content.search.service.SectionSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("sectionValueService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SectionValueServiceImpl extends ListServiceImpl<SectionValue> implements SectionValueService {

    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private SectionValueMapper sectionValueMapper;
    @Reference
    private SectionSearcherService sectionSearcherService;

    @Override
    public PageResult<SectionValue> findBySearcher(SectionValueSearcher searcher, PageModel page) {
        PageResult<SectionValue> pager = new PageResult<SectionValue>(page);
        int totalCount = sectionValueMapper.countBySearcher(searcher);
        List<SectionValue> list = new ArrayList<SectionValue>();
        if (totalCount > 0) {
            list = sectionValueMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public int countBySearcher(SectionValueSearcher searcher) {
        return sectionValueMapper.countBySearcher(searcher);
    }

    @Override
    public PageResult<SectionValueDto> findDtoBySearcher(SectionValueSearcher searcher, PageModel page) {
        PageResult<SectionValueDto> pager = new PageResult<SectionValueDto>(page);
        int totalCount = sectionValueMapper.countBySearcher(searcher);
        List<SectionValueDto> list = new ArrayList<SectionValueDto>();
        if (totalCount > 0) {
            list = sectionValueMapper.findDtoBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public PageResult<SectionValue> findDeletedBySearcher(SectionValueSearcher searcher, PageModel page) {
        PageResult<SectionValue> pager = new PageResult<SectionValue>(page);
        int totalCount = sectionValueMapper.countDeletedBySearcher(searcher);
        List<SectionValue> list = new ArrayList<SectionValue>();
        if (totalCount > 0) {
            list = sectionValueMapper.findDeletedBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public List<SectionValueDto> findBySection(Section section, PageModel page) {
        SectionValueSearcher searcher = new SectionValueSearcher();
        searcher.setModuleId(section.getModuleId());
        searcher.setSectionDefId(section.getId());
        PageResult<SectionValueDto> pager = this.findDtoBySearcher(searcher, page);
        return pager.getList();
    }

    @Override
    public SectionValue findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public SectionValue insert(SectionValue entity) {
        return super.save(entity);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int delete(Long id, Long sectionId) {
        Section section = sectionMapper.selectByPrimaryKey(sectionId);
        if (section.getFixed() == 0) {
            sectionSearcherService.remove(sectionId + "_" + id);
        }
        return sectionValueMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int deleteBySectionId(Long sectionId) {
        return sectionValueMapper.deleteBySectionId(sectionId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int deleteByModuleId(Long moduleId) {
        return sectionValueMapper.deleteByModuleId(moduleId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int update(SectionValue sectionValue) {
        return this.updateNotNull(sectionValue);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateStatus(Long id, Integer status) {
        return sectionValueMapper.updateStatus(id, status);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doRecovery(Long id) {
        return sectionValueMapper.doRecovery(id);
    }

}
