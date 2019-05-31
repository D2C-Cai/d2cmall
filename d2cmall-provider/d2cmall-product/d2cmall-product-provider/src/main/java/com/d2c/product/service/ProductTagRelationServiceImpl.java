package com.d2c.product.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.ProductLog;
import com.d2c.logger.model.ProductLog.ProductLogType;
import com.d2c.logger.service.ProductLogService;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductTagRelationMapper;
import com.d2c.product.dto.ProductTagRelationDto;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductTag;
import com.d2c.product.model.ProductTagRelation;
import com.d2c.product.query.ProductSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("productTagRelationService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductTagRelationServiceImpl extends ListServiceImpl<ProductTagRelation>
        implements ProductTagRelationService {

    @Autowired
    private ProductTagRelationMapper productTagRelationMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductTagService productTagService;
    @Autowired
    private ProductLogService productLogService;

    @Override
    public ProductTagRelation findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    public ProductTagRelation findByTagIdAndProductId(Long productId, Long tagId) {
        return productTagRelationMapper.findByTagIdAndProductId(productId, tagId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(Long productId, Long tagId, String operator) {
        if (tagId != null) {
            ProductTagRelation relation = new ProductTagRelation();
            relation.setProductId(productId);
            relation.setTagId(tagId);
            relation = this.save(relation);
            JSONObject info = new JSONObject();
            info.put("操作", "关联标签，标签Id：" + tagId);
            productLogService
                    .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.ProductR, productId, null));
        }
        return 1;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long relationId, String operator) {
        ProductTagRelation relation = this.findById(relationId);
        int success = deleteById(relationId);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "删除关联标签，标签Id：" + relation.getTagId());
            productLogService.insert(new ProductLog(operator, info.toJSONString(), ProductLogType.ProductR,
                    relation.getProductId(), null));
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteByTagIdAndProductId(Long tagId, Long productId, String operator) {
        int success = productTagRelationMapper.deleteByTagIdAndProductId(tagId, productId);
        if (success > 0) {
            JSONObject info = new JSONObject();
            info.put("操作", "删除关联标签，标签Id：" + tagId);
            productLogService
                    .insert(new ProductLog(operator, info.toJSONString(), ProductLogType.ProductR, productId, null));
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, int sort) {
        return productTagRelationMapper.updateSort(id, sort);
    }

    @Override
    public PageResult<ProductTagRelationDto> findProductsByTagId(PageModel page, ProductSearcher productSearcher,
                                                                 Long tagId) {
        PageResult<ProductTagRelationDto> pager = new PageResult<>(page);
        int totalCount = productTagRelationMapper.countBySearcher(tagId, productSearcher);
        if (totalCount > 0) {
            List<ProductTagRelation> list = productTagRelationMapper.findBySearcher(tagId, productSearcher, page);
            List<ProductTagRelationDto> items = new ArrayList<>();
            for (ProductTagRelation pr : list) {
                Product product = productService.findById(pr.getProductId());
                ProductTagRelationDto dto = new ProductTagRelationDto();
                BeanUtils.copyProperties(pr, dto);
                dto.setProduct(product);
                items.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(items);
        }
        return pager;
    }

    @Override
    public PageResult<ProductTagRelationDto> findTagsByProductId(PageModel page, Long productId) {
        PageResult<ProductTagRelationDto> pager = new PageResult<>(page);
        int totalCount = productTagRelationMapper.countByProductId(productId);
        if (totalCount > 0) {
            List<ProductTagRelation> list = productTagRelationMapper.findByProductId(productId, page);
            List<ProductTagRelationDto> items = new ArrayList<>();
            for (ProductTagRelation pr : list) {
                ProductTag tag = productTagService.findById(pr.getTagId());
                ProductTagRelationDto dto = new ProductTagRelationDto();
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
    public Long[] findTagIdByProductId(Long productId) {
        Long[] tags = null;
        tags = productTagRelationMapper.findTagIdByProductId(productId);
        return tags;
    }

}
