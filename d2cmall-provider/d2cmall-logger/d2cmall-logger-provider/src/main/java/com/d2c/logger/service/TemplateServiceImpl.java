package com.d2c.logger.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.dao.TemplateMapper;
import com.d2c.logger.model.Template;
import com.d2c.logger.query.TemplateSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("templateService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class TemplateServiceImpl extends ListServiceImpl<Template> implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    public PageResult<Template> findBySearch(TemplateSearcher templateSearcher, PageModel page) {
        PageResult<Template> pager = new PageResult<Template>(page);
        Integer totalCount = templateMapper.countBySearch(templateSearcher);
        List<Template> list = new ArrayList<Template>();
        if (totalCount > 0) {
            list = templateMapper.findBySearch(page, templateSearcher);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    public List<HelpDTO> findBySearchForHelp(TemplateSearcher templateSearcher, PageModel page) {
        List<HelpDTO> helpDtos = new ArrayList<HelpDTO>();
        Integer totalCount = templateMapper.countBySearch(templateSearcher);
        List<Template> list = new ArrayList<Template>();
        if (totalCount > 0) {
            list = templateMapper.findBySearch(page, templateSearcher);
            for (Template tp : list) {
                HelpDTO dto = new HelpDTO();
                dto.setId(tp.getId());
                dto.setName(tp.getSubject());
                helpDtos.add(dto);
            }
        }
        return helpDtos;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Template insert(Template template) throws Exception {
        return save(template);
    }

    @Cacheable(value = "template", key = "'template_'+#id", unless = "#result == null")
    public Template findById(Long id) {
        return this.findOneById(id);
    }

    @CacheEvict(value = "template", key = "'template_'+#template.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Template template) throws Exception {
        return this.updateNotNull(template);
    }

    public List<Template> findByIds(Long[] ids) {
        List<Template> list = new ArrayList<Template>();
        for (Long id : ids) {
            if (id == null) {
                continue;
            }
            Template temp = this.findById(id);
            if (temp != null) {
                list.add(temp);
            }
        }
        return list;
    }

    @CacheEvict(value = "template", key = "'template_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) throws Exception {
        return this.deleteById(id);
    }

}
