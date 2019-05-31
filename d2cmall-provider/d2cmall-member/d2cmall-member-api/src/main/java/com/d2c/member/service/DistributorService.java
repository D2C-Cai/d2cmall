package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.DistributorDto;
import com.d2c.member.model.Distributor;
import com.d2c.member.query.DistributorSearcher;

public interface DistributorService {

    /**
     * 根据id查询
     *
     * @param distributorId
     * @return
     */
    Distributor findById(Long distributorId);

    /**
     * 根据memberInfo的id查询
     *
     * @param id
     * @return
     */
    Distributor findByMemberInfoId(Long id);

    /**
     * 根据memberInfo的id查询
     *
     * @param memberInfoId
     * @return
     */
    Distributor findEnableByMemberInfoId(Long memberInfoId);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<DistributorDto> findBySearch(DistributorSearcher searcher, PageModel page);

    /**
     * 绑定会员组
     *
     * @param id
     * @param groupId
     * @return
     */
    int doBindGroup(Long id, Long groupId, Long memberInfoId);

    /**
     * 取消绑定会员组
     *
     * @param id
     * @return
     */
    int doCancleGroup(Long id, Long memberInfoId);

    /**
     * 插入分销商
     *
     * @param distributor
     * @return
     */
    Distributor insert(Distributor distributor);

    /**
     * 更新分销商
     *
     * @param old
     * @return
     */
    int update(Distributor old);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatusById(Long id, int status, Long memberId);

    /**
     * 是否免运费
     *
     * @param id
     * @param status
     * @return
     */
    int updateFreeShipFeeById(Long id, int status, Long memberId);

    /**
     * 是否退款退货
     *
     * @param id
     * @param status
     * @return
     */
    int updateReship(Long id, int status, Long memberId);

}
