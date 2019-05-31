package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.dao.PageDefineMapper;
import com.d2c.content.dto.PageDefineDto;
import com.d2c.content.model.FieldDefine;
import com.d2c.content.model.PageDefine;
import com.d2c.content.model.PageDefine.MODULE;
import com.d2c.content.model.PageDefine.TERMINAL;
import com.d2c.mybatis.service.ListServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("pageDefineService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PageDefineServiceImpl extends ListServiceImpl<PageDefine> implements PageDefineService {

    @Autowired
    private PageDefineMapper pageDefineMapper;
    @Autowired
    private FieldDefineService fieldDefService;

    public PageResult<PageDefineDto> findBySearch(PageDefine searcher, PageModel page) {
        PageResult<PageDefineDto> pager = new PageResult<PageDefineDto>(page);
        int totalCount = pageDefineMapper.countBySearch(searcher);
        List<PageDefine> list = new ArrayList<PageDefine>();
        List<PageDefineDto> dtos = new ArrayList<PageDefineDto>();
        if (totalCount > 0) {
            list = pageDefineMapper.findBySearch(searcher, page);
            for (PageDefine pd : list) {
                PageDefineDto dto = new PageDefineDto();
                BeanUtils.copyProperties(pd, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    public PageDefineDto findAllById(Long id) {
        PageDefine pageDefine = pageDefineMapper.selectByPrimaryKey(id);
        if (pageDefine == null) {
            return null;
        }
        List<FieldDefine> fieldDefs = fieldDefService.findByPageDefId(pageDefine.getId());
        PageDefineDto dto = new PageDefineDto();
        BeanUtils.copyProperties(pageDefine, dto);
        for (int i = 0; i < fieldDefs.size(); i++) {
            FieldDefine def = fieldDefs.get(i);
            if (StringUtils.isNotBlank(def.getAliasCode())) {
                dto.getFieldDefs().put(def.getAliasCode(), def);
            }
            dto.getFieldDefs().put(def.getCode(), def);
            dto.getFieldList().add(def);
        }
        return dto;
    }

    public PageDefine findById(Long id) {
        PageDefine pageDefine = pageDefineMapper.selectByPrimaryKey(id);
        return pageDefine;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PageDefine insert(PageDefine define) {
        define = save(define);
        if (define.getId() > 0) {
            for (int i = 0; i < 25; i++) {
                FieldDefine df = new FieldDefine();
                df.setStatus(0);
                String code = String.valueOf(i + 1);
                df.setCode("block" + StringUtils.leftPad(code, 2, "0"));
                df.setPageDefId(define.getId());
                fieldDefService.insert(df);
            }
        }
        return define;
    }

    @CacheEvict(value = "page_define", key = "'page_define_'+#define.module+'_'+#define.terminal+'_'+#define.version")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(PageDefine define) {
        return this.updateNotNull(define);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCopy(Long id) {
        PageDefine pageDefine = this.findAllById(id);
        if (pageDefine == null) {
            throw new BusinessException("定义不存在！");
        }
        PageDefine newDefine = new PageDefine();
        BeanUtils.copyProperties(pageDefine, newDefine, new String[]{"id"});
        newDefine.setVersion(pageDefine.getVersion() + 1);
        newDefine = save(newDefine);
        if (newDefine.getId() > 0) {
            List<FieldDefine> fieldDefs = fieldDefService.findByPageDefId(id);
            for (int i = 0; i < fieldDefs.size(); i++) {
                FieldDefine def = fieldDefs.get(i);
                FieldDefine newDf = new FieldDefine();
                BeanUtils.copyProperties(def, newDf, new String[]{"id"});
                newDf.setPageDefId(newDefine.getId());
                fieldDefService.insert(newDf);
            }
        }
        return 1;
    }

    @Cacheable(value = "page_define", key = "'page_define_'+#module+'_'+#terminal+'_'+#version", unless = "#result == null")
    public PageDefineDto findPageDefine(MODULE module, TERMINAL terminal, Integer version) {
        PageDefine pageDefine = pageDefineMapper.findPageDefine(module, terminal, version);
        if (pageDefine == null) {
            return null;
        }
        List<FieldDefine> fieldDefs = fieldDefService.findByPageDefId(pageDefine.getId());
        PageDefineDto dto = new PageDefineDto();
        BeanUtils.copyProperties(pageDefine, dto);
        for (int i = 0; i < fieldDefs.size(); i++) {
            FieldDefine def = fieldDefs.get(i);
            if (StringUtils.isNotBlank(def.getAliasCode())) {
                dto.getFieldDefs().put(def.getAliasCode(), def);
            }
            dto.getFieldDefs().put(def.getCode(), def);
            dto.getFieldList().add(def);
        }
        return dto;
    }

}
