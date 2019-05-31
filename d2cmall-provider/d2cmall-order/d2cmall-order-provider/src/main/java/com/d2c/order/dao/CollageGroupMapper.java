package com.d2c.order.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.mybatis.mapper.SuperMapper;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.query.CollageGroupSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CollageGroupMapper extends SuperMapper<CollageGroup> {

    /**
     * 根据promotionId查询拼团中的列表
     *
     * @param promotionId
     * @param page
     * @return
     */
    List<CollageGroup> findVaildByPromotionId(@Param("promotionId") Long promotionId, @Param("pager") PageModel page);

    /**
     * 根据promotionId查询拼团的数量
     *
     * @param promotionId
     * @return
     */
    int countVaildByPromotionId(@Param("promotionId") Long promotionId);

    /**
     * 根据promotionId查询开团数
     *
     * @param promotionId
     * @return
     */
    int countGroupByPromotionId(@Param("promotionId") Long promotionId);

    /**
     * 分页查询
     *
     * @param searcher
     * @param pager
     * @return
     */
    List<CollageGroup> findBySearch(@Param("searcher") CollageGroupSearcher searcher, @Param("pager") PageModel pager);

    /**
     * 统计数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(@Param("searcher") CollageGroupSearcher searcher);

    /**
     * 查找已经超时的团队
     *
     * @param deadline
     * @param page
     * @return
     */
    List<CollageGroup> findExpiredGroup(@Param("deadline") Date deadline, @Param("page") PageModel page);

    /**
     * 统计已经超时的团队
     *
     * @param deadline
     * @param page
     * @return
     */
    int countExpireGroup(@Param("deadline") Date deadline);

    /**
     * 更新当前参团人数
     *
     * @param id
     * @param num
     * @return
     */
    int updateCurrentMemberCount(@Param("id") Long id, @Param("num") int num);

    /**
     * 更新团队支付人数
     *
     * @param id
     * @param num
     * @return
     */
    int updatePayCount(@Param("id") Long id, @Param("num") int num);

    /**
     * 关闭团队
     *
     * @param id
     * @return
     */
    int doFailGroup(@Param("id") Long id);

    /**
     * 拼团成功
     *
     * @param id
     * @return
     */
    int doSuccess(@Param("id") Long id);

    /**
     * 更新团长信息
     *
     * @param memberId
     * @param memberName
     * @param headPic
     * @return
     */
    int updateInitiator(@Param("id") Long id, @Param("memberId") Long memberId, @Param("memberName") String memberName,
                        @Param("headPic") String headPic);

}
