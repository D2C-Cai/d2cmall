package com.d2c.content.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.content.dao.SubModuleMapper;
import com.d2c.content.dto.SectionValueDto;
import com.d2c.content.model.Section;
import com.d2c.content.model.SectionValue.RelationType;
import com.d2c.content.model.SubModule;
import com.d2c.content.query.SectionSearcher;
import com.d2c.content.query.SectionValueSearcher;
import com.d2c.content.search.model.SearcherSection;
import com.d2c.content.search.service.SectionSearcherService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.ProductProSearchQuery;
import com.d2c.product.search.service.ProductSearcherQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("subModuleService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SubModuleServiceImpl extends ListServiceImpl<SubModule> implements SubModuleService {

    @Autowired
    private SubModuleMapper subModuleMapper;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private SectionValueService sectionValueService;
    @Reference
    private SectionSearcherService sectionSearcherService;
    @Autowired
    private SubModuleQueryService subModuleQueryService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateDefault(Long id, int isDefault) {
        if (1 == isDefault) {
            SubModule old = this.findOneById(id);
            subModuleMapper.updateCancelDefault(old.getParent());
            subModuleQueryService.clearCacheByParent(old.getParent());
            if (old.getCategoryId() != null) {
                subModuleQueryService.clearCacheByCategory(old.getCategoryId());
            }
        }
        return subModuleMapper.updateDefault(id, isDefault);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public SubModule save(SubModule subModule) {
        if (subModule.getIsDefault() == 1) {
            subModuleMapper.updateCancelDefault(subModule.getParent());
            subModuleQueryService.clearCacheByParent(subModule.getParent());
            if (subModule.getCategoryId() != null) {
                subModuleQueryService.clearCacheByCategory(subModule.getCategoryId());
            }
        }
        return super.save(subModule);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int delete(Long id) {
        SubModule subModule = subModuleMapper.selectByPrimaryKey(id);
        if (subModule.getCategoryId() == null) {
            List<SubModule> childModules = subModuleMapper.findByParentAndCategory(subModule.getId());
            if (childModules.size() > 0) {
                throw new BusinessException("删除不成功，一级类目存在子级目录");
            }
        }
        int success = 0;
        if (subModule != null) {
            success = subModuleMapper.delete(id);
            if (success > 0) {
                sectionService.deleteByModuleId(id);
                sectionValueService.deleteByModuleId(id);
                subModuleQueryService.clearCacheByParent(subModule.getParent());
                if (subModule.getCategoryId() != null) {
                    subModuleQueryService.clearCacheByCategory(subModule.getCategoryId());
                }
            }
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int update(SubModule subModule) {
        if (subModule.getIsDefault() == 1) {
            updateDefault(subModule.getId(), 1);
        }
        SubModule old = this.findById(subModule.getId());
        if (old.getTbPic() != subModule.gettPic() || old.getTbPic() != subModule.getTbPic()) {
            subModuleMapper.updatetPic(subModule.getId(), subModule.gettPic(), subModule.getTbPic());
        }
        return this.updateNotNull(subModule);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public SubModule insert(SubModule subModule) {
        return this.save(subModule);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateStatus(Long id, Integer status, String lastModifyMan) {
        int success = subModuleMapper.updateStatus(id, status, lastModifyMan);
        if (success > 0) {
            SubModule subModule = this.subModuleMapper.selectByPrimaryKey(id);
            subModuleQueryService.clearCacheByParent(subModule.getParent());
            if (subModule.getCategoryId() != null) {
                subModuleQueryService.clearCacheByCategory(subModule.getCategoryId());
            }
        }
        return success;
    }

    @Override
    public SubModule findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateVersion(Long id, Integer version) {
        int result = subModuleMapper.updateVersion(id, version);
        if (result > 0) {
            SubModule subModule = subModuleMapper.selectByPrimaryKey(id);
            subModuleQueryService.clearCacheByParent(subModule.getParent());
            sectionSearcherService.deleteVersion(id.toString(), version - 10);
            if (subModule.getCategoryId() != null) {
                subModuleQueryService.clearCacheByCategory(subModule.getCategoryId());
            }
        }
        return result;
    }

    @Override
    public List<SubModule> findCategory(String parent) {
        return subModuleMapper.findCategory(parent);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int updateCategoryId(Long id, Long catgegoryId) {
        return subModuleMapper.updateCategoryId(id, catgegoryId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doPublishSection(Long sectionId) {
        Section section = sectionService.findById(sectionId);
        SubModule subModule = this.findById(section.getModuleId());
        if (section.getFixed() == 1) {
            this.initFixSectionToSearch(section, subModule.getVersion(), true);
        } else {
            this.initUnFixSectionToSearch(section, subModule.getVersion(), true);
        }
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int doPublish(Long moduleId) {
        SubModule subModule = subModuleQueryService.findById(moduleId);
        Integer newVersion = subModule.getVersion() + 1;
        int pagerNumber = 1;
        PageModel sPage = new PageModel(1, 100);
        SectionSearcher sectionSearcher = new SectionSearcher();
        sectionSearcher.setModuleId(moduleId);
        int totalCount = sectionService.countBySearcher(sectionSearcher);
        PageResult<Section> sPager = new PageResult<>(sPage);
        sPager.setTotalCount(totalCount);
        do {
            sPage.setPageNumber(pagerNumber);
            sPager = sectionService.findBySearcher(sectionSearcher, sPage);
            // 将数据分模板推向搜索，固定模板和非固定模板不同处理
            this.initSectionToSearch(sPager, newVersion);
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= sPager.getPageCount());
        return this.updateVersion(moduleId, newVersion);
    }

    private void initSectionToSearch(PageResult<Section> sPager, Integer version) {
        for (Section section : sPager.getList()) {
            if (section.getFixed() == 1) {
                this.initFixSectionToSearch(section, version, false);
            } else {
                this.initUnFixSectionToSearch(section, version, false);
            }
        }
    }

    private void initFixSectionToSearch(Section section, Integer version, boolean rebuild) {
        SearcherSection searcherSection = findFixSection(section, version);
        if (rebuild) {
            sectionSearcherService.rebuild(searcherSection);
        } else {
            sectionSearcherService.insert(searcherSection);
        }
    }

    private void initUnFixSectionToSearch(Section section, Integer version, boolean rebuild) {
        int pagerNumber = 1;
        PageModel page = new PageModel(1, 100);
        SectionValueSearcher searcher = new SectionValueSearcher();
        searcher.setModuleId(section.getModuleId());
        searcher.setSectionDefId(section.getId());
        if (section.getType() == 6) {
            // 如果发生了新增和删除的情况下，原来的数据不会被移除
            sectionSearcherService.deleteBySectionDefId(String.valueOf(section.getId()));
            // type6显示12个
            searcher.setStatus(1);
            page.setPageSize(12);
        }
        int totalCount = sectionValueService.countBySearcher(searcher);
        PageResult<SectionValueDto> pager = new PageResult<>(page);
        pager.setTotalCount(totalCount);
        do {
            page.setPageNumber(pagerNumber);
            pager = sectionValueService.findDtoBySearcher(searcher, page);
            List<SearcherSection> list = findUnFixSection(section, pager, version);
            for (SearcherSection searcherSection : list) {
                if (rebuild) {
                    sectionSearcherService.rebuild(searcherSection);
                } else {
                    sectionSearcherService.insert(searcherSection);
                }
            }
            if (section.getType() == 6) {
                break;
            }
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount());
    }

    @Override
    public SearcherSection findFixSection(Section section, Integer version) {
        SearcherSection searcherSection = new SearcherSection();
        searcherSection.initFix(section, version);
        List<SectionValueDto> list = sectionValueService.findBySection(section, new PageModel(1, 60));
        if (list == null || list.size() <= 0) {
            searcherSection.setStatus(0);
        }
        if (section.getType() == 11 && list.size() > 1) {
            searcherSection.setUrl(list.get(0).getUrl());
            searcherSection.setFrontPic(list.get(0).getFrontPic());
            searcherSection.setShortTitle(list.get(0).getShortTitle());
            list.remove(0);
        }
        JSONArray array = new JSONArray();
        for (SectionValueDto dto : list) {
            if (dto.getStatus() == 0) {
                continue;
            }
            array.add(dto.toInitJson());
        }
        searcherSection.setSectionValues(array.toJSONString());
        return searcherSection;
    }

    @Override
    public List<SearcherSection> findUnFixSection(Section section, PageResult<SectionValueDto> pager, Integer version) {
        List<SearcherSection> list = new ArrayList<>();
        for (SectionValueDto dto : pager.getList()) {
            SearcherSection searcherSection = new SearcherSection();
            searcherSection.initUnFix(section, dto, version, pager.getPageNumber());
            searcherSection.setSectionValues(processValuesByType(dto));
            list.add(searcherSection);
            section.setVisible(0);
        }
        return list;
    }

    private String processValuesByType(SectionValueDto dto) {
        PageResult<SearcherProduct> products = new PageResult<>();
        if (dto.getType() == 8) {
            Object obj = JSONObject.parseObject(dto.getProperties()).get("designerId");
            if (obj != null) {
                Long designerId = Long.parseLong(obj.toString());
                products = productSearcherQueryService.findSaleProductByDesigner(designerId, new PageModel(1, 8),
                        new ProductProSearchQuery());
            }
        }
        if (dto.getType() == 12) {
            if (RelationType.BRAND.name().equals(dto.getRelationType())) {
                products = productSearcherQueryService.findSaleProductByDesigner(dto.getRelationId(),
                        new PageModel(1, 8), new ProductProSearchQuery());
            } else if (RelationType.PROMOTION.name().equals(dto.getRelationType())) {
                ProductProSearchQuery searcher = new ProductProSearchQuery();
                searcher.setPromotionId(dto.getRelationId());
                products = productSearcherQueryService.search(searcher, new PageModel(1, 8));
            }
        }
        if (products.getList() == null || products.getList().size() <= 0) {
            return null;
        }
        JSONArray array = new JSONArray();
        for (SearcherProduct searcherProduct : products.getList()) {
            array.add(searcherProduct.toSectionJson());
        }
        return array.toJSONString();
    }

}
