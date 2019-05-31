package com.d2c.product.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.product.dao.AwardProductMapper;
import com.d2c.product.dto.AwardProductDto;
import com.d2c.product.model.AwardProduct;
import com.d2c.product.model.AwardSession;
import com.d2c.product.query.AwardProductSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("awardProductService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AwardProductServiceImpl extends ListServiceImpl<AwardProduct> implements AwardProductService {

    @Autowired
    private AwardProductMapper awardProductMapper;
    @Autowired
    private AwardSessionService awardSessionService;

    @Override
    public AwardProduct findById(Long id) {
        return super.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(AwardProduct awardProduct) {
        return this.updateNotNull(awardProduct);
    }

    @Override
    public PageResult<AwardProductDto> findBySearcher(AwardProductSearcher searcher, PageModel page) {
        PageResult<AwardProductDto> pager = new PageResult<>(page);
        int count = awardProductMapper.countBySearcher(searcher);
        List<AwardProductDto> dtos = new ArrayList<AwardProductDto>();
        if (count > 0) {
            Map<Long, AwardSession> awardSessions = new HashMap<Long, AwardSession>();
            List<AwardProduct> list = awardProductMapper.findBySearcher(searcher, page);
            for (AwardProduct item : list) {
                AwardProductDto dto = new AwardProductDto();
                BeanUtils.copyProperties(item, dto);
                if (item.getSessionId() != null && awardSessions.get(item.getSessionId()) == null) {
                    AwardSession awardSession = awardSessionService.findById(item.getSessionId());
                    dto.setAwardSession(awardSession);
                } else {
                    dto.setAwardSession(awardSessions.get(item.getSessionId()));
                }
                dtos.add(dto);
            }
        }
        pager.setList(dtos);
        pager.setTotalCount(count);
        return pager;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public AwardProduct insert(AwardProduct awardProduct) {
        return this.save(awardProduct);
    }

    @Override
    public int countBySearcher(AwardProductSearcher searcher) {
        return awardProductMapper.countBySearcher(searcher);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateCouponQuantity(Long id) {
        return awardProductMapper.updateCouponToDecrease(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doMark(Integer enable, Long id) {
        return awardProductMapper.updateStatus(id, enable);
    }

    @Override
    public AwardProduct findByMaxWeight(Long sessionId) {
        return awardProductMapper.findByMaxWeight(sessionId);
    }

    @Override
    public List<AwardProduct> findBySessionIdAndNow(Long sessionId) {
        return awardProductMapper.findBySessionIdAndNow(sessionId);
    }

}
