package com.d2c.content.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.content.dao.PageContentMapper;
import com.d2c.content.dto.PageContentDto;
import com.d2c.content.model.FieldContent;
import com.d2c.content.model.FieldDefine;
import com.d2c.content.model.PageContent;
import com.d2c.mybatis.service.ListServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("pageContentService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PageContentServiceImpl extends ListServiceImpl<PageContent> implements PageContentService {

    @Autowired
    private PageContentMapper pageContentMapper;
    @Autowired
    private FieldContentService fieldContentService;
    @Autowired
    private FieldDefineService fieldDefService;

    @CacheEvict(value = "page_content", key = "'page_content_'+#pageContent.pageDefId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PageContent insert(PageContent pageContent) {
        return this.save(pageContent);
    }

    @CacheEvict(value = "page_content", key = "'page_content_'+#pageContent.pageDefId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(PageContent pageContent) {
        return this.updateNotNull(pageContent);
    }

    public PageContentDto findOneById(Long id) {
        PageContent homePage = pageContentMapper.findById(id);
        if (homePage == null) {
            return null;
        }
        PageContentDto dto = new PageContentDto();
        BeanUtils.copyProperties(homePage, dto);
        initHomePageBlocks(dto, null);
        return dto;
    }

    public PageContentDto findOneById(Long id, PageModel pager) {
        PageContent homePage = pageContentMapper.selectByPrimaryKey(id);
        if (homePage == null) {
            return null;
        }
        PageContentDto dto = new PageContentDto();
        BeanUtils.copyProperties(homePage, dto);
        initHomePageBlocks(dto, pager);
        return dto;
    }

    private void initHomePageBlocks(PageContentDto homePage, PageModel page) {
        if (homePage == null)
            return;
        List<FieldDefine> fieldDefs = fieldDefService.findByPageDefId(homePage.getPageDefId());
        for (int i = 0; i < fieldDefs.size(); i++) {
            FieldDefine field = fieldDefs.get(i);
            List<FieldContent> blocks = null;
            if (field.getType() == 0) {
                continue;
            }
            if (page == null) {
                blocks = fieldContentService.findByGroupAndPageId(field.getCode(), homePage.getId(), null).getList();
            } else {
                blocks = fieldContentService.findByGroupAndPageId(field.getCode(), homePage.getId(), page).getList();
            }
            if (blocks != null) {
                homePage.getBlocks().put(field.getCode(), blocks);
                if (StringUtils.isNotBlank(field.getAliasCode())) {
                    homePage.getBlocks().put(field.getAliasCode(), blocks);
                }
            }
        }
    }

    @Cacheable(value = "page_content", key = "'page_content_'+#defId", condition = "1==#status")
    public PageContentDto findOneByModule(Long defId, int status) {
        PageContent homePage = pageContentMapper.findOneByModule(defId, status);
        if (homePage == null) {
            return null;
        }
        PageContentDto dto = new PageContentDto();
        BeanUtils.copyProperties(homePage, dto);
        initHomePageBlocks(dto, null);
        return dto;
    }

    public PageContentDto findOneByModule(Long pageDefId, int status, PageModel pager) {
        PageContent homePage = pageContentMapper.findOneByModule(pageDefId, status);
        if (homePage == null) {
            return null;
        }
        PageContentDto dto = new PageContentDto();
        BeanUtils.copyProperties(homePage, dto);
        initHomePageBlocks(dto, pager);
        return dto;
    }

    @CacheEvict(value = "page_content", key = "'page_content_'+#pageContent.pageDefId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doPublish(PageContent pageContent) {
        if (pageContent.getStatus() == 0) {
            Long historyId = pageContentMapper.findIdByModule(pageContent.getPageDefId(), -1);
            /**
             * 删除最后一条历史记录
             */
            List<FieldDefine> fieldDefs = fieldDefService.findByPageDefId(pageContent.getPageDefId());
            for (int i = 0; i < fieldDefs.size(); i++) {
                FieldDefine field = fieldDefs.get(i);
                List<FieldContent> blocks = fieldContentService.findByGroupAndPageId(field.getCode(), historyId, null)
                        .getList();
                if (blocks != null) {
                    for (FieldContent block : blocks) {
                        this.fieldContentService.delete(block);
                    }
                }
            }
            pageContentMapper.delete(historyId);
            /**
             * 将现在的记录，变为历史记录 生成-1版本
             */
            Long publishedId = pageContentMapper.findIdByModule(pageContent.getPageDefId(), 1);
            pageContentMapper.updateStatusById(publishedId, -1);
            /**
             * 发布为在线状态记录 将原来的0版本变成了1版本
             */
            pageContentMapper.updateStatusById(pageContent.getId(), 1);
            /**
             * 生成0版本（原来的0版本变成了1版本，没有了0版本）
             */
            this.doCopyInsert(pageContent);
            return 1;
        }
        return 0;
    }

    @CacheEvict(value = "page_content", key = "'page_content_'+#pageContent.pageDefId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PageContent doCopyInsert(PageContent page) {
        Long oldPageId = page.getId();
        page.setId(null);
        page.setStatus(0);
        PageContent pageContent = this.insert(page);
        List<FieldDefine> fieldDefs = fieldDefService.findByPageDefId(page.getPageDefId());
        for (int i = 0; i < fieldDefs.size(); i++) {
            FieldDefine field = fieldDefs.get(i);
            int totalCount = fieldContentService.countByGroupAndPage(field.getCode(), oldPageId);
            int p = 1;
            int pageSize = 50;
            PageModel pageBlock = new PageModel(p, pageSize);
            if (totalCount > 0) {
                int pageCount = (totalCount + pageSize - 1) / pageSize;
                do {
                    List<FieldContent> blocks = fieldContentService
                            .findByGroupAndPageId(field.getCode(), oldPageId, pageBlock).getList();
                    if (blocks != null) {
                        for (FieldContent block : blocks) {
                            copyInsertBlock(page, block);
                        }
                    }
                    pageBlock.setP(++p);
                } while (pageBlock.getP() <= pageCount);
            }
        }
        return pageContent;
    }

    private void copyInsertBlock(PageContent page, FieldContent block) {
        block.setModulePageId(page.getId());
        block.setId(null);
        this.fieldContentService.insert(block);
    }

}
