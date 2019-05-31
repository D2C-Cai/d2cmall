package com.d2c.product.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.PromotionTagMapper;
import com.d2c.product.model.PromotionTag;
import com.d2c.product.query.PromotionTagSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("promotionTagService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
public class PromotionTagServiceImpl extends ListServiceImpl<PromotionTag> implements PromotionTagService {

    @Autowired
    private PromotionTagMapper promotionTagMapper;

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public PromotionTag insert(PromotionTag tag) {
        return this.save(tag);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(PromotionTag tag) {
        return this.updateNotNull(tag);
    }

    public PageResult<PromotionTag> findBySearch(PromotionTagSearcher searcher, PageModel page) {
        PageResult<PromotionTag> pager = new PageResult<PromotionTag>(page);
        int totalCount = promotionTagMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<PromotionTag> list = promotionTagMapper.findBySearch(searcher, page);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    public PromotionTag findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return super.deleteById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatus(Long id, Integer status) {
        return promotionTagMapper.updateStatus(id, status);
    }

    @Override
    public PageResult<HelpDTO> findBySearchForHelp(PromotionTagSearcher searcher, PageModel page) {
        PageResult<HelpDTO> pager = new PageResult<HelpDTO>(page);
        int totalCount = promotionTagMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<PromotionTag> list = promotionTagMapper.findBySearch(searcher, page);
            List<HelpDTO> dtos = new ArrayList<HelpDTO>();
            for (PromotionTag tag : list) {
                HelpDTO dto = new HelpDTO(tag);
                dtos.add(dto);
            }
            pager.setTotalCount(totalCount);
            pager.setList(dtos);
        }
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSort(Long id, Date upDateTime) {
        PromotionTag tag = this.findById(id);
        tag.setUpDateTime(upDateTime);
        return this.update(tag);
    }

}
