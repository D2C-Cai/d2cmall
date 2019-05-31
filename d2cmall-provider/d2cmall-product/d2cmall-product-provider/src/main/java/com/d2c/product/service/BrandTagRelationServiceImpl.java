package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.BrandLog;
import com.d2c.logger.model.BrandLog.DesignerLogType;
import com.d2c.logger.service.BrandLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.BrandTagRelationMapper;
import com.d2c.product.dto.BrandTagRelationDto;
import com.d2c.product.model.Brand;
import com.d2c.product.model.BrandTag;
import com.d2c.product.model.BrandTagRelation;
import com.d2c.product.query.BrandTagRelationSearcher;
import com.d2c.product.search.model.SearcherDesignerTagRelation;
import com.d2c.product.search.service.DesignerTagRelationSearcherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("brandTagRelationService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class BrandTagRelationServiceImpl extends ListServiceImpl<BrandTagRelation> implements BrandTagRelationService {

    @Autowired
    private BrandTagRelationMapper brandTagRelationMapper;
    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandTagService brandTagService;
    @Reference
    private DesignerTagRelationSearcherService designerTagRelationSearcherService;
    @Autowired
    private BrandLogService brandLogService;

    @Override
    public PageResult<SearcherDesignerTagRelation> findByTagId(Long tagId, PageModel page) {
        BrandTagRelationSearcher searcher = new BrandTagRelationSearcher();
        searcher.setTagId(tagId);
        PageResult<SearcherDesignerTagRelation> pager = new PageResult<>(page);
        int totalCount = brandTagRelationMapper.countByTagRelationSearch(searcher);
        if (totalCount > 0) {
            List<BrandTagRelation> list = brandTagRelationMapper.findByTagRelationSearch(searcher, page);
            List<SearcherDesignerTagRelation> dtos = new ArrayList<>();
            for (BrandTagRelation pr : list) {
                SearcherDesignerTagRelation searcheRelation = new SearcherDesignerTagRelation();
                BeanUtils.copyProperties(pr, searcheRelation);
                dtos.add(searcheRelation);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<BrandTagRelationDto> findDesignersByTagId(BrandTagRelationSearcher searcher, PageModel page) {
        PageResult<BrandTagRelationDto> pager = new PageResult<>(page);
        int totalCount = brandTagRelationMapper.countByTagRelationSearch(searcher);
        if (totalCount > 0) {
            List<BrandTagRelation> list = brandTagRelationMapper.findByTagRelationSearch(searcher, page);
            List<BrandTagRelationDto> items = new ArrayList<>();
            for (BrandTagRelation pr : list) {
                Brand brand = brandService.findById(pr.getDesignerId());
                BrandTagRelationDto dto = new BrandTagRelationDto();
                BeanUtils.copyProperties(pr, dto);
                dto.setDesigner(brand);
                items.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(items);
        }
        return pager;
    }

    @Override
    public PageResult<BrandTagRelationDto> findTagsByDesignerId(Long designerId, PageModel page) {
        PageResult<BrandTagRelationDto> pager = new PageResult<>(page);
        BrandTagRelationSearcher searcher = new BrandTagRelationSearcher();
        searcher.setDesignerId(designerId);
        int totalCount = brandTagRelationMapper.countByTagRelationSearch(searcher);
        if (totalCount > 0) {
            List<BrandTagRelation> list = brandTagRelationMapper.findByTagRelationSearch(searcher, page);
            List<BrandTagRelationDto> items = new ArrayList<>();
            for (BrandTagRelation pr : list) {
                BrandTag tag = brandTagService.findById(pr.getTagId());
                BrandTagRelationDto dto = new BrandTagRelationDto();
                BeanUtils.copyProperties(pr, dto);
                dto.setTag(tag);
                items.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(items);
        }
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateSort(Long id, Integer sort) {
        BrandTagRelation tdr = this.findOneById(id);
        int success = brandTagRelationMapper.updateSort(tdr.getDesignerId(), tdr.getTagId(), sort);
        if (success > 0) {
            SearcherDesignerTagRelation relation = new SearcherDesignerTagRelation();
            BeanUtils.copyProperties(tdr, relation);
            relation.setSort(sort);
            designerTagRelationSearcherService.updateSort(relation);
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(Long brandId, Long[] tagIds, String operator) {
        brandTagRelationMapper.deleteByDesignerId(brandId);
        designerTagRelationSearcherService.remove(brandId);
        if (tagIds != null && tagIds.length >= 1) {
            String tags = "";
            for (Long tagId : tagIds) {
                BrandTagRelation designerTagRelation = new BrandTagRelation();
                designerTagRelation.setDesignerId(brandId);
                designerTagRelation.setTagId(tagId);
                brandTagRelationMapper.insert(designerTagRelation);
                tags += tagId + ",";
                SearcherDesignerTagRelation relation = new SearcherDesignerTagRelation();
                BeanUtils.copyProperties(designerTagRelation, relation);
                designerTagRelationSearcherService.rebuild(relation);
            }
            tags = tags.substring(0, tags.length() - 1);
            brandService.updateTags(brandId, tags);
            JSONObject info = new JSONObject();
            info.put("操作", "关联标签，标签Id：" + tags);
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.TagR, brandId));
        }
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByTagIdAndDesignerId(Long tagId, Long designerId, String operator) {
        brandTagRelationMapper.deleteByTagIdAndDesignerId(tagId, designerId);
        int success = designerTagRelationSearcherService.remove(designerId, tagId);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "取消关联标签，标签Id：" + tagId);
            brandLogService.insert(new BrandLog(operator, info.toJSONString(), DesignerLogType.TagR, designerId));
        }
        return success;
    }

}
