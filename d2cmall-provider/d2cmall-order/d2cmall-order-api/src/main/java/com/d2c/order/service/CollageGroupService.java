package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.CollageGroupDto;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.query.CollageGroupSearcher;

import java.util.Date;

public interface CollageGroupService {

    /**
     * 创建团
     *
     * @param collageGroup
     * @return
     */
    CollageGroup insert(CollageGroup collageGroup);

    /**
     * 根据id查询团
     *
     * @param id
     * @return
     */
    CollageGroup findById(Long id);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    CollageGroupDto findDtoById(Long id);

    /**
     * 拼团详情页显示拼团团队
     *
     * @param promotionId
     * @param page
     * @return
     */
    PageResult<CollageGroup> findVaildByPromotionId(Long promotionId, PageModel page);

    /**
     * 通过条件查询，得到分页的换货数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<CollageGroup> findBySearch(CollageGroupSearcher searcher, PageModel page);

    /**
     * 符合查询条件的数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(CollageGroupSearcher searcher);

    /**
     * 修改当前参与团的人数<br>
     * 条件:
     * <li>进行中的，且当前人数+新增人数<=开团数
     *
     * @param groupId
     * @param num
     * @return
     */
    int updateCurrentMemberCount(Long groupId, int num);

    /**
     * 更新支付成功人数
     *
     * @param groupId
     * @param num
     * @return
     */
    int updatePayCount(Long groupId, int num);

    /**
     * 查找超时未成功的团
     *
     * @param deadline
     * @param page
     * @return
     */
    PageResult<CollageGroup> findExpireGroup(Date deadline, PageModel page);

    /**
     * 统计超时未成功的团
     *
     * @param deadline
     * @param page
     * @return
     */
    int countExpireGroup(Date deadline);

    /**
     * 拼团失败
     *
     * @param id
     * @return
     */
    int doFailGroup(Long id);

    /**
     * 拼团成功
     *
     * @param id
     * @return
     */
    int doSuccess(CollageGroup group);

    /**
     * 更新团长信息
     *
     * @param id
     * @param memberId
     * @param memberName
     * @param headPic
     * @return
     */
    int updateInitiator(Long id, Long memberId, String memberName, String headPic);

    /**
     * 查找该活动开团数
     *
     * @return
     */
    int countGroupByPromotionId(Long promotionId);

}
