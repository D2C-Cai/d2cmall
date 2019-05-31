package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.content.dao.FieldContentMapper;
import com.d2c.content.model.FieldContent;
import com.d2c.content.query.FieldContentSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("fieldContentService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class FieldContentServiceImpl extends ListServiceImpl<FieldContent> implements FieldContentService {

    @Autowired
    private FieldContentMapper fieldContentMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public FieldContent insert(FieldContent field) {
        return this.save(field);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(FieldContent field) {
        return deleteById(field.getId());
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(FieldContent field) {
        return this.updateNotNull(field);
    }

    public FieldContent findOneById(Long id) {
        FieldContent field = fieldContentMapper.selectByPrimaryKey(id);
        return field;
    }

    public PageResult<FieldContent> findByGroupAndPageId(String group, Long pageid, PageModel page) {
        PageResult<FieldContent> pager = new PageResult<FieldContent>(page);
        if (page == null) {
            page = new PageModel(1, PageModel.MAX_PAGE_SIZE);
        }
        int totalCount = fieldContentMapper.countByGroupAndPage(group, pageid);
        if (totalCount > 0) {
            pager.setTotalCount(totalCount);
            List<FieldContent> fields = fieldContentMapper.findByGroupAndPage(group, pageid, page);
            pager.setList(fields);
        }
        return pager;
    }

    @Override
    public int countByGroupAndPage(String group, Long pageid) {
        int totalCount = fieldContentMapper.countByGroupAndPage(group, pageid);
        return totalCount;
    }

    public PageResult<FieldContent> findBySearch(FieldContentSearcher searcher, PageModel page) {
        PageResult<FieldContent> pager = new PageResult<FieldContent>(page);
        int totalCount = fieldContentMapper.countBySearch(searcher);
        if (totalCount > 0) {
            pager.setTotalCount(totalCount);
            pager.setList(fieldContentMapper.findBySearch(page, searcher));
        }
        return pager;
    }

}
