package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.ProductShareRelationMapper;
import com.d2c.product.model.ProductShareRelation;
import com.d2c.product.query.ProductShareRelationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("productShareRelationService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ProductShareRelationServiceImpl extends ListServiceImpl<ProductShareRelation>
        implements ProductShareRelationService {

    @Autowired
    private ProductShareRelationMapper memberShareProductRelationMapper;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doReplace(ProductShareRelation productShareRelation) {
        return memberShareProductRelationMapper.doReplace(productShareRelation);
    }

    @Override
    public PageResult<ProductShareRelation> findBySearcher(ProductShareRelationSearcher searcher, PageModel page) {
        PageResult<ProductShareRelation> pager = new PageResult<>();
        Integer totalCount = memberShareProductRelationMapper.countBySearcher(searcher);
        List<ProductShareRelation> list = new ArrayList<>();
        if (totalCount > 0) {
            list = memberShareProductRelationMapper.findBySearcher(searcher, page);
        }
        pager.setTotalCount(totalCount);
        pager.setList(list);
        return pager;
    }

    @Override
    public List<ProductShareRelation> findByShareId(Long shareId, Integer pageSize) {
        return memberShareProductRelationMapper.findByShareId(shareId, pageSize);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteRelation(Long shareId, Long productId) {
        return memberShareProductRelationMapper.deleteRelation(shareId, productId);
    }

    @Override
    public List<Long> findProductIdByShareId(Long shareId) {
        return memberShareProductRelationMapper.findProductIdByShareId(shareId);
    }

}
