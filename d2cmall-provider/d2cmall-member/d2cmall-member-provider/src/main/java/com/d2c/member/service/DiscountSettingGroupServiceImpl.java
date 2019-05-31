package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.DiscountSettingGroupMapper;
import com.d2c.member.dao.DiscountSettingMapper;
import com.d2c.member.dto.DiscountSettingGroupDto;
import com.d2c.member.model.DiscountSetting;
import com.d2c.member.model.DiscountSetting.DiscountType;
import com.d2c.member.model.DiscountSettingGroup;
import com.d2c.member.query.DiscountSettingGroupSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "discountSettingGroupService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class DiscountSettingGroupServiceImpl extends ListServiceImpl<DiscountSettingGroup>
        implements DiscountSettingGroupService {

    @Autowired
    private DiscountSettingGroupMapper discountSettingGroupMapper;
    @Autowired
    private DiscountSettingService discountSettingService;
    @Autowired
    private DiscountSettingMapper discountSettingMapper;

    public DiscountSettingGroup findById(Long id) {
        return this.findOneById(id);
    }

    public PageResult<DiscountSettingGroupDto> findBySearch(DiscountSettingGroupSearcher searcher, PageModel page) {
        PageResult<DiscountSettingGroupDto> pager = new PageResult<DiscountSettingGroupDto>(page);
        int totalCount = discountSettingGroupMapper.countBySearch(searcher);
        if (totalCount > 0) {
            List<DiscountSettingGroup> value = discountSettingGroupMapper.findBySearch(searcher, page);
            List<DiscountSettingGroupDto> list = this.doCopyProperties(value);
            pager.setList(list);
            pager.setTotalCount(totalCount);
        }
        return pager;
    }

    private List<DiscountSettingGroupDto> doCopyProperties(List<DiscountSettingGroup> rules) {
        List<DiscountSettingGroupDto> dtos = new ArrayList<DiscountSettingGroupDto>();
        for (int i = 0; i < rules.size(); i++) {
            DiscountSettingGroupDto dto = new DiscountSettingGroupDto();
            DiscountSettingGroup rule = rules.get(i);
            BeanUtils.copyProperties(rule, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public DiscountSettingGroup insert(DiscountSettingGroup discountSettingGroup, String lastModifyMan) {
        discountSettingGroup = this.save(discountSettingGroup);
        DiscountSetting discountSetting = new DiscountSetting();
        discountSetting.setDisType(DiscountType.ALL.toString());
        discountSetting.setGroupId(discountSettingGroup.getId());
        discountSetting.setStatus(1);
        discountSettingService.insert(discountSetting, "SYS");
        return discountSettingGroup;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateDate(Long id, Date beginDate, Date endDate) {
        return discountSettingGroupMapper.updateDate(id, beginDate, endDate);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateStatusById(Long id, int status, String username) {
        int result = discountSettingGroupMapper.updateStatusById(id, status);
        if (result > 0 && status == 0) {
            List<DiscountSetting> list = discountSettingMapper.findByGroupId(id);
            for (DiscountSetting discountSetting : list) {
                discountSettingService.updateStatusById(discountSetting, status, username);
            }
        }
        return result;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateNameById(Long id, String name) {
        return discountSettingGroupMapper.updateNameById(id, name);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateDiscountSettingGroup(DiscountSettingGroup group) {
        return this.updateNotNull(group);
    }

}
