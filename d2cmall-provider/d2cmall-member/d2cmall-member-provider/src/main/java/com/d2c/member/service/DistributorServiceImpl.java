package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.DistributorMapper;
import com.d2c.member.dto.DistributorDto;
import com.d2c.member.model.DiscountSettingGroup;
import com.d2c.member.model.Distributor;
import com.d2c.member.query.DistributorSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("distributorService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class DistributorServiceImpl extends ListServiceImpl<Distributor> implements DistributorService {

    @Autowired
    private DistributorMapper distributorMapper;
    @Autowired
    private DiscountSettingGroupService discountSettingGroupService;
    @Autowired
    private MemberInfoService memberInfoService;

    public Distributor findById(Long id) {
        return this.findOneById(id);
    }

    public Distributor findByMemberInfoId(Long memberId) {
        return distributorMapper.findByMemberInfoId(memberId);
    }

    @Cacheable(value = "distributor", key = "'distributor_'+#memberInfoId", unless = "#result == null")
    public Distributor findEnableByMemberInfoId(Long memberInfoId) {
        Distributor distributor = this.distributorMapper.findByMemberInfoId(memberInfoId);
        if (distributor != null && distributor.getStatus() <= 0) {
            return null;
        }
        return distributor;
    }

    public PageResult<DistributorDto> findBySearch(DistributorSearcher searcher, PageModel page) {
        PageResult<DistributorDto> pager = new PageResult<DistributorDto>(page);
        int totalCount = distributorMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<Distributor> value = distributorMapper.findBySearch(searcher, page);
            List<DistributorDto> list = this.doCopyProperties(value);
            pager.setTotalCount(totalCount);
            pager.setList(list);
        }
        return pager;
    }

    private List<DistributorDto> doCopyProperties(List<Distributor> rules) {
        List<DistributorDto> dtos = new ArrayList<DistributorDto>();
        for (int i = 0; i < rules.size(); i++) {
            DistributorDto dto = new DistributorDto();
            Distributor rule = rules.get(i);
            DiscountSettingGroup group = discountSettingGroupService.findById(rule.getGroupId());
            BeanUtils.copyProperties(rule, dto);
            dto.setDiscountSettingGroup(group);
            dtos.add(dto);
        }
        return dtos;
    }

    @CacheEvict(value = "distributor", key = "'distributor_'+#memberInfoId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBindGroup(Long id, Long groupId, Long memberInfoId) {
        return distributorMapper.updateGroupIdById(id, groupId);
    }

    @CacheEvict(value = "distributor", key = "'distributor_'+#memberInfoId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCancleGroup(Long id, Long memberInfoId) {
        return distributorMapper.updateGroupIdById(id, null);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Distributor insert(Distributor distributor) {
        return this.save(distributor);
    }

    @CacheEvict(value = "distributor", key = "'distributor_'+#distributor.memberId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Distributor distributor) {
        return this.updateNotNull(distributor);
    }

    @CacheEvict(value = "distributor", key = "'distributor_'+#memberInfoId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatusById(Long id, int status, Long memberInfoId) {
        int success = distributorMapper.updateStatusById(id, status);
        if (success > 0) {
            if (status == 0) {
                memberInfoService.doCancelDistributor(memberInfoId);
            } else if (status == 1) {
                memberInfoService.doBindDistributor(memberInfoId, id);
            }
        }
        return success;
    }

    @CacheEvict(value = "distributor", key = "'distributor_'+#memberInfoId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateFreeShipFeeById(Long id, int status, Long memberInfoId) {
        return distributorMapper.updateFreeShipFeeById(id, status);
    }

    @CacheEvict(value = "distributor", key = "'distributor_'+#memberInfoId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateReship(Long id, int status, Long memberInfoId) {
        return distributorMapper.updateReship(id, status);
    }

}
