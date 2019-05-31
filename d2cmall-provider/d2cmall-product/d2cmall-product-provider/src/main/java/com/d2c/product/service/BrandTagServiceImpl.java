package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.BrandTagMapper;
import com.d2c.product.dao.BrandTagRelationMapper;
import com.d2c.product.model.BrandTag;
import com.d2c.product.query.BrandTagSearcher;
import com.d2c.product.search.model.SearcherDesignerTag;
import com.d2c.product.search.service.DesignerTagSearcherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("brandTagService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class BrandTagServiceImpl extends ListServiceImpl<BrandTag> implements BrandTagService {

    @Autowired
    private BrandTagMapper brandTagMapper;
    @Autowired
    private BrandTagRelationMapper designerTagRelationMapper;
    @Reference
    private DesignerTagSearcherService designerTagSearcherService;

    @Override
    public BrandTag findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public List<BrandTag> findByDesignerId(Long designerId) {
        List<BrandTag> list = brandTagMapper.findByDesignerId(designerId);
        return list;
    }

    @Override
    public PageResult<BrandTag> findBySearch(BrandTagSearcher searcher, PageModel page) {
        PageResult<BrandTag> pager = new PageResult<>(page);
        int totalCount = brandTagMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<BrandTag> list = brandTagMapper.findBySearch(searcher, page);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    @Override
    public PageResult<HelpDTO> findBySearchForHelp(BrandTagSearcher searcher, PageModel page) {
        PageResult<HelpDTO> pager = new PageResult<>(page);
        int totalCount = brandTagMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<BrandTag> list = brandTagMapper.findBySearch(searcher, page);
            List<HelpDTO> dtos = new ArrayList<>();
            for (BrandTag tag : list) {
                HelpDTO dto = new HelpDTO(tag);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    public PageResult<BrandTag> findSynDesignerTags(Date lastSysDate, PageModel page) {
        PageResult<BrandTag> pager = new PageResult<>(page);
        int totalCount = brandTagMapper.countSynDesignerTags(lastSysDate);
        if (totalCount > 0) {
            List<BrandTag> list = brandTagMapper.findSynDesignerTags(lastSysDate, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public BrandTag insert(BrandTag tag) {
        tag = this.save(tag);
        SearcherDesignerTag sdt = new SearcherDesignerTag();
        BeanUtils.copyProperties(tag, sdt);
        designerTagSearcherService.insert(sdt);
        return tag;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(BrandTag tag) {
        this.updateNotNull(tag);
        tag = brandTagMapper.selectByPrimaryKey(tag.getId());
        SearcherDesignerTag sdt = new SearcherDesignerTag();
        BeanUtils.copyProperties(tag, sdt);
        designerTagSearcherService.update(sdt);
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        int result = brandTagMapper.updateStatus(id, status);
        if (result > 0) {
            if (status == 1) {
                BrandTag tag = brandTagMapper.selectByPrimaryKey(id);
                SearcherDesignerTag sdt = new SearcherDesignerTag();
                BeanUtils.copyProperties(tag, sdt);
                designerTagSearcherService.insert(sdt);
            } else {
                designerTagSearcherService.remove(id);
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        BrandTag tag = this.findById(id);
        tag.setSort(sort);
        return this.update(tag);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        designerTagRelationMapper.deleteByTagId(id);
        designerTagSearcherService.remove(id);
        return delete(id);
    }

    @Override
    public BrandTag findFixedOne() {
        return brandTagMapper.findFixedOne();
    }

}
