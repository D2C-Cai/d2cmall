package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.logger.model.DiscountSettingLog;
import com.d2c.logger.service.DiscountSettingLogService;
import com.d2c.member.dao.DiscountSettingMapper;
import com.d2c.member.dto.DiscountSettingDto;
import com.d2c.member.model.DiscountSetting;
import com.d2c.member.model.Distributor;
import com.d2c.member.query.DiscountSettingSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("discountSettingService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class DiscountSettingServiceImpl extends ListServiceImpl<DiscountSetting> implements DiscountSettingService {

    @Autowired
    private DiscountSettingMapper discountSettingMapper;
    @Autowired
    private DiscountSettingLogService discountSettingLogService;
    @Autowired
    private DistributorService distributorService;

    public DiscountSetting findById(Long id) {
        return this.findOneById(id);
    }

    public PageResult<DiscountSettingDto> findBySearch(DiscountSettingSearcher searcher, PageModel page) {
        PageResult<DiscountSettingDto> pager = new PageResult<DiscountSettingDto>(page);
        int totalCount = discountSettingMapper.countBySearch(searcher);
        List<DiscountSettingDto> dtoList = new ArrayList<DiscountSettingDto>();
        if (totalCount > 0) {
            List<DiscountSetting> list = discountSettingMapper.findBySearch(searcher, page);
            for (DiscountSetting item : list) {
                DiscountSettingDto dto = new DiscountSettingDto();
                BeanUtils.copyProperties(item, dto);
                dtoList.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtoList);
        return pager;
    }

    @Override
    public int countBySearch(DiscountSettingSearcher searcher) {
        int totalCount = discountSettingMapper.countBySearch(searcher);
        return totalCount;
    }

    @Override
    public List<DiscountSetting> findDiscountSettings(Long distributorId, String disType, List<Long> targetIds) {
        Distributor distributor = distributorService.findById(distributorId);
        Long groupId = distributor.getGroupId();
        return discountSettingMapper.findByGroupOrDisId(groupId, distributorId, disType, targetIds);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public DiscountSetting insert(DiscountSetting discountSetting, String lastModifyMan) {
        discountSetting = this.save(discountSetting);
        this.insertLog(discountSetting, lastModifyMan);
        return discountSetting;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertBatch(List<DiscountSetting> list, String lastModifyMan) {
        for (DiscountSetting discountSetting : list) {
            this.insert(discountSetting, lastModifyMan);
        }
        return 1;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateDiscountById(DiscountSetting discountSetting, BigDecimal discount, String lastModifyMan) {
        int result = discountSettingMapper.updateDiscountById(discountSetting.getId(), discount);
        discountSetting.setDiscount(discount);
        this.insertLog(discountSetting, lastModifyMan);
        return result;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatusById(DiscountSetting discountSetting, int status, String lastModifyMan) {
        int result = discountSettingMapper.updateStatusById(discountSetting.getId(), status);
        discountSetting.setStatus(status);
        this.insertLog(discountSetting, lastModifyMan);
        return result;
    }

    private void insertLog(DiscountSetting discountSetting, String lastModifyMan) {
        DiscountSettingLog log = new DiscountSettingLog();
        log.setLastModifyMan(lastModifyMan);
        log.setDiscountSettingId(discountSetting.getId());
        log.setDisType(discountSetting.getDisType());
        log.setDistributorId(discountSetting.getDistributorId());
        log.setTargetId(discountSetting.getTargetId());
        log.setDiscount(discountSetting.getDiscount());
        log.setStatus(discountSetting.getStatus());
        if (log != null) {
            discountSettingLogService.insert(log);
        }
    }

    @Override
    public DiscountSetting findByGroupIdAndProductId(Long groupId, Long productId) {
        return discountSettingMapper.findByGroupIdAndProductId(groupId, productId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatusByTargetId(DiscountSetting discountSetting, int status, String username) {
        return discountSettingMapper.updateStatusByTargetId(discountSetting.getTargetId(), discountSetting.getGroupId(),
                discountSetting.getDistributorId(), discountSetting.getDisType(), status, username);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(DiscountSetting discountSetting, String username) {
        int success = this.updateNotNull(discountSetting);
        this.insertLog(discountSetting, username);
        return success;
    }

    @Override
    public DiscountSetting findByTargetId(String disType, Long targetId, Long groupId, Long distributorId) {
        return discountSettingMapper.findByTargetId(disType, targetId, groupId, distributorId);
    }

}
