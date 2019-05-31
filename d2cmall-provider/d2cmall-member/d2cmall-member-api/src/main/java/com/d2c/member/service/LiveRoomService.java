package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.LiveRoom;
import com.d2c.member.query.LiveRoomSearcher;

public interface LiveRoomService {

    /**
     * 根据会员id查找
     *
     * @param memberId
     * @return
     */
    LiveRoom findByMemberId(Long memberId);

    /**
     * 分页列表
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<LiveRoom> findBySearcher(LiveRoomSearcher searcher, PageModel page);

    /**
     * 绑定优惠券
     *
     * @param id
     * @param couponId
     * @param operator
     * @param couponGroupId
     * @return
     */
    int doBindCoupons(Long id, Long couponId, String operator, Long couponGroupId);

    /**
     * 创建直播间
     *
     * @param memberId
     * @return
     */
    int doCreateLiveRoom(Long memberId);

    /**
     * 删除直播间
     *
     * @param memberId
     * @return
     */
    int doDeleteLiveRoom(Long memberId);

}
