package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.service.ListService;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.model.Address;

import java.util.List;

public interface AddressService extends ListService<Address> {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    AddressDto findById(Long id);

    /**
     * 根据memberInfoId查询
     *
     * @param memberId memberInfo表id
     * @param page     分页
     * @param keyWord  关键信息
     * @return
     */
    PageResult<AddressDto> findByMemberInfoId(Long memberId, PageModel page, String keyWord);

    /**
     * 根据memberId查询
     *
     * @param memberId
     * @return
     */
    List<AddressDto> findByMemberInfoId(Long memberId);

    /**
     * 插入
     *
     * @param address
     * @return
     */
    AddressDto insert(Address address);

    /**
     * 更新地址
     *
     * @param address
     * @return
     */
    AddressDto update(Address address);

    /**
     * 设置地址为默认地址
     *
     * @param id
     * @param memberInfoId
     * @return
     */
    int doSettingDefault(Long id, Long memberInfoId);

    /**
     * 删除
     *
     * @param id
     * @param memberInfoId
     * @return
     */
    int delete(Long id, Long memberInfoId);

    /**
     * 合并数据
     *
     * @param memberSourceId
     * @param memberTargetId
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

}
