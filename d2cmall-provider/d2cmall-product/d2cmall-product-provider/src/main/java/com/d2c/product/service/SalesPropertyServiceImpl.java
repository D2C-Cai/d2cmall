package com.d2c.product.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.SalesPropertyGroupMapper;
import com.d2c.product.dao.SalesPropertyMapper;
import com.d2c.product.dto.SalesPropertyDto;
import com.d2c.product.model.SalesProperty;
import com.d2c.product.query.SalesPropertySearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("salesPropertyService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class SalesPropertyServiceImpl extends ListServiceImpl<SalesProperty> implements SalesPropertyService {

    @Autowired
    private SalesPropertyMapper salesPropertyMapper;
    @Autowired
    private SalesPropertyGroupMapper salesPropertyGroupMapper;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public SalesProperty insert(SalesProperty salesProperty) {
        return this.save(salesProperty);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, readOnly = false)
    public int update(SalesProperty salesProperty) {
        return this.updateNotNull(salesProperty);
    }

    public PageResult<SalesPropertyDto> findBySearch(SalesPropertySearcher searcher, PageModel page) {
        PageResult<SalesPropertyDto> pager = new PageResult<SalesPropertyDto>(page);
        int totalCount = salesPropertyMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<SalesProperty> list = salesPropertyMapper.findBySearch(searcher, page);
            List<SalesPropertyDto> dtos = new ArrayList<SalesPropertyDto>();
            for (int i = 0; i < list.size(); i++) {
                SalesPropertyDto dto = new SalesPropertyDto();
                BeanUtils.copyProperties(list.get(i), dto);
                dto.setGroupName(salesPropertyGroupMapper.findNameById(list.get(i).getGroupId()));
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    public SalesProperty findById(Long id) {
        return super.findOneById(id);
    }

    public List<SalesProperty> findColors() {
        return salesPropertyMapper.findColors();
    }

    public List<SalesProperty> findSizes() {
        return salesPropertyMapper.findSizes();
    }

}
