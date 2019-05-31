package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.AddressMapper;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.model.Address;
import com.d2c.order.model.Area;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("addressService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AddressServiceImpl extends ListServiceImpl<Address> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AreaService areaService;

    @Override
    public AddressDto findById(Long id) {
        Address address = this.findOneById(id);
        if (address == null) {
            return null;
        }
        AddressDto dto = new AddressDto();
        BeanUtils.copyProperties(address, dto);
        setArea(dto);
        return dto;
    }

    @Override
    public PageResult<AddressDto> findByMemberInfoId(Long memberId, PageModel page, String keyWord) {
        PageResult<AddressDto> pager = new PageResult<>(page);
        int totalCount = addressMapper.countByMemberInfoId(memberId, keyWord);
        List<AddressDto> dtos = new ArrayList<>();
        if (totalCount > 0) {
            List<Address> addresses = addressMapper.findByMemberIdAndKeyword(memberId, page, keyWord);
            for (int i = 0; i < addresses.size(); i++) {
                Address address = addresses.get(i);
                AddressDto dto = new AddressDto();
                BeanUtils.copyProperties(address, dto);
                setArea(dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    public List<AddressDto> findByMemberInfoId(Long memberId) {
        List<Address> list = addressMapper.findByMemberInfoId(memberId);
        List<AddressDto> dtos = new ArrayList<>();
        list.forEach(bean -> {
            AddressDto dto = BeanUt.buildBean(bean, AddressDto.class);
            setArea(dto);
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public AddressDto insert(Address address) {
        address = this.save(address);
        if (address.getIsdefault()) {
            // 保证只有一个默认地址
            doSettingDefault(address.getId(), address.getMemberId());
        }
        AddressDto dto = new AddressDto();
        BeanUtils.copyProperties(address, dto);
        setArea(dto);
        return dto;
    }

    private void setArea(AddressDto addressDto) {
        if (addressDto.getRegionPrefix() != null) {
            Area province = areaService.findAreaByCode(Integer.parseInt(addressDto.getRegionPrefix()));
            addressDto.setProvince(province);
        }
        if (addressDto.getRegionMiddle() != null) {
            Area city = areaService.findAreaByCode(Integer.parseInt(addressDto.getRegionMiddle()));
            addressDto.setCity(city);
        }
        if (addressDto.getRegionSuffix() != null) {
            Area district = areaService.findAreaByCode(Integer.parseInt(addressDto.getRegionSuffix()));
            addressDto.setDistrict(district);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public AddressDto update(Address address) {
        if (address.getIsdefault()) {
            // 保证只有一个默认地址
            doSettingDefault(address.getId(), address.getMemberId());
        }
        this.updateNotNull(address);
        AddressDto dto = new AddressDto();
        BeanUtils.copyProperties(address, dto);
        setArea(dto);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doSettingDefault(Long addressId, Long memberId) {
        if (addressId == null || memberId == null) {
            return 0;
        }
        addressMapper.clearDefaultByMember(addressId, memberId);
        return addressMapper.settingDefault(addressId, memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long addressId, Long memberId) {
        return deleteById(addressId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doMerge(Long memberSourceId, Long memberTargetId) {
        int success = addressMapper.doMerge(memberSourceId, memberTargetId);
        return success;
    }

}
