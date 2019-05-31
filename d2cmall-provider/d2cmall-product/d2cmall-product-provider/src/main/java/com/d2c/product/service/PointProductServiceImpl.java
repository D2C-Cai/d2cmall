package com.d2c.product.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.PointProductMapper;
import com.d2c.product.dto.PointProductDto;
import com.d2c.product.model.PointProduct;
import com.d2c.product.model.PointProduct.PointProductTypeEnum;
import com.d2c.product.query.PointProductSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "pointProductService")
@Transactional(readOnly = true, noRollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class PointProductServiceImpl extends ListServiceImpl<PointProduct> implements PointProductService {

    @Autowired
    private PointProductMapper pointProductMapper;
    @Autowired
    private ExternalCardService externalCardService;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PointProduct insert(PointProduct pointProduct) {
        return this.save(pointProduct);
    }

    @Override
    public PageResult<PointProductDto> findDto(PointProductSearcher searcher, PageModel page) {
        PageResult<PointProductDto> pager = new PageResult<>(page);
        int totalCount = pointProductMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<PointProduct> list = pointProductMapper.findBySearcher(searcher, page);
            List<PointProductDto> dtos = new ArrayList<>();
            for (PointProduct pointProduct : list) {
                PointProductDto dto = new PointProductDto();
                BeanUtils.copyProperties(pointProduct, dto);
                if (PointProductTypeEnum.CARD.equals(pointProduct.getType())) {
                    int totalUsed = externalCardService.countUsed(pointProduct.getId());
                    dto.setTotalUsed(totalUsed);
                }
                dtos.add(dto);
            }
            pager.setList(dtos);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    public PageResult<PointProduct> findBySearcher(PointProductSearcher searcher, PageModel page) {
        PageResult<PointProduct> pager = new PageResult<>(page);
        int totalCount = pointProductMapper.countBySearcher(searcher);
        if (totalCount > 0) {
            List<PointProduct> list = pointProductMapper.findBySearcher(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(totalCount);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateMark(Long id, Integer mark, String operator) {
        return pointProductMapper.updateMark(id, mark, operator);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(PointProduct pointProduct) {
        return this.updateNotNull(pointProduct);
    }

    @Override
    public PointProduct findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateCount(Long id, int count) {
        return pointProductMapper.updateCount(id, count);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Integer sort, String operator) {
        return pointProductMapper.updateSort(id, sort, operator);
    }

}
