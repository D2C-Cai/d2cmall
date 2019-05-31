package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.model.WardrobeCollocation;
import com.d2c.member.query.WardrobeCollocationSearcher;

public interface WardrobeCollocationService {

    /**
     * 新增
     *
     * @param wardrobeCollocation
     * @return
     */
    WardrobeCollocation insert(WardrobeCollocation wardrobeCollocation);

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 根据searcher查询分页数据
     *
     * @param query
     * @param page
     * @return
     */
    PageResult<WardrobeCollocation> findBySearcher(WardrobeCollocationSearcher query, PageModel page);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    WardrobeCollocation findById(Long id);

    /**
     * 审核通过
     *
     * @param id
     * @param verifyMan
     * @return
     */
    int doVerify(Long id, String verifyMan);

    /**
     * 取消审核
     *
     * @param id
     * @param lastModifyMan
     * @return
     */
    int doCancelVerify(Long id, String lastModifyMan);

    /**
     * 拒绝
     *
     * @param id
     * @param lastModifyMan
     * @return
     */
    int doRefuse(Long id, String lastModifyMan);

    /**
     * 更新
     *
     * @param wardrobeCollocation
     * @return
     */
    int update(WardrobeCollocation wardrobeCollocation);

    /**
     * 更新视频
     *
     * @param id
     * @param video
     * @return
     */
    int updateVideoById(Long id, String video);

    /**
     * 会员自查
     *
     * @param memberId
     * @param page
     * @return
     */
    PageResult<WardrobeCollocation> findMine(Long memberId, WardrobeCollocationSearcher query, PageModel page);

    /**
     * 用户删除
     *
     * @param id
     * @param memberId
     * @return
     */
    int deleteByMemberId(Long[] ids, Long memberId);

}
