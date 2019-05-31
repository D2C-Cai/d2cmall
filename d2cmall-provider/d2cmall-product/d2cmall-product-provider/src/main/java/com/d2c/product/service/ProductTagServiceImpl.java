package com.d2c.product.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductTagMapper;
import com.d2c.product.dao.ProductTagRelationMapper;
import com.d2c.product.model.ProductTag;
import com.d2c.product.query.ProductTagSearcher;
import com.d2c.product.search.model.SearcherProductTag;
import com.d2c.product.search.service.ProductTagSearcherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("productTagService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductTagServiceImpl extends ListServiceImpl<ProductTag> implements ProductTagService {

    @Autowired
    private ProductTagMapper productTagMapper;
    @Autowired
    private ProductTagRelationMapper roductTagRelationMapper;
    @Reference
    private ProductTagSearcherService productTagSearcherService;

    @CacheEvict(value = "product_tag_type", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ProductTag insert(ProductTag tag) {
        tag = this.save(tag);
        SearcherProductTag smt = new SearcherProductTag();
        BeanUtils.copyProperties(tag, smt);
        productTagSearcherService.insert(smt);
        return tag;
    }

    @CacheEvict(value = "product_tag_type", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(ProductTag tag) {
        this.updateNotNull(tag);
        tag = productTagMapper.selectByPrimaryKey(tag.getId());
        SearcherProductTag smt = new SearcherProductTag();
        BeanUtils.copyProperties(tag, smt);
        productTagSearcherService.update(smt);
        return 1;
    }

    @CacheEvict(value = "product_tag_type", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        roductTagRelationMapper.deleteByTagId(id);
        int result = productTagMapper.delete(id);
        if (result > 0) {
            productTagSearcherService.remove(id);
        }
        return result;
    }

    public List<ProductTag> findByProductId(Long productId) {
        return productTagMapper.findByProductId(productId);
    }

    public PageResult<ProductTag> findBySearch(ProductTagSearcher searcher, PageModel page) {
        PageResult<ProductTag> pager = new PageResult<ProductTag>(page);
        int totalCount = productTagMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<ProductTag> list = productTagMapper.findBySearch(searcher, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    public ProductTag findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public List<HelpDTO> findBySearchForHelp(ProductTagSearcher searcher, PageModel page) {
        List<ProductTag> list = new ArrayList<ProductTag>();
        list = productTagMapper.findBySearch(searcher, page);
        List<HelpDTO> dtos = new ArrayList<HelpDTO>();
        if (list != null) {
            for (ProductTag tag : list) {
                HelpDTO dto = new HelpDTO();
                BeanUtils.copyProperties(tag, dto);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @CacheEvict(value = "product_tag_type", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        int result = productTagMapper.updateStatus(id, status);
        if (result > 0) {
            SearcherProductTag smt = new SearcherProductTag();
            smt.setId(id);
            smt.setStatus(status);
            productTagSearcherService.update(smt);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort) {
        return productTagMapper.updateSort(id, sort);
    }

    @Override
    public PageResult<ProductTag> findSynProductTags(Date lastSysDate, PageModel page) {
        PageResult<ProductTag> pager = new PageResult<ProductTag>(page);
        int totalCount = productTagMapper.countSynProductTags(lastSysDate);
        if (totalCount > 0) {
            List<ProductTag> list = productTagMapper.findSynProductTags(lastSysDate, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    @Override
    @Cacheable(value = "product_tag_type", key = "'product_tag_type_'+#type", unless = "#result == null")
    public List<ProductTag> findByType(String type) {
        return productTagMapper.findByType(type);
    }

    @Override
    public List<ProductTag> findByIds(Long[] ids) {
        return productTagMapper.findByIds(ids);
    }

    @Override
    public ProductTag findOneFixByType(String type) {
        return productTagMapper.findOneFixByType(type);
    }

}
