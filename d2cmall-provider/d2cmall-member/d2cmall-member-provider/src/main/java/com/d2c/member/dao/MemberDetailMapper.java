package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberDetail;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MemberDetailMapper extends SuperMapper<MemberDetail> {

    /**
     * 根据memberinfoId查询
     *
     * @param memberId
     * @return
     */
    MemberDetail findByMemberInfoId(@Param("memberInfoId") Long memberInfoId);

    /**
     * 根据memberinfoId更新
     *
     * @param memberId
     * @param memberDertail
     * @return
     */
    int updateByMemberId(@Param("memberId") Long memberId, @Param("memberDertail") MemberDetail memberDertail);

    /**
     * 更新等级
     *
     * @param additionalAmount
     * @param level
     * @param date
     * @param memberInfoId
     * @return
     */
    int updateLevel(@Param("additionalAmount") Integer additionalAmount, @Param("level") Integer level,
                    @Param("upgradeDate") Date upgradeDate, @Param("memberInfoId") Long memberInfoId);

    /**
     * 统计等级满365天用户
     *
     * @return
     */
    int countExpireMember();

    /**
     * 查找等级满365天用户
     *
     * @param page
     * @return
     */
    List<MemberDetail> findExpireMember(@Param("page") PageModel page);

    /**
     * 更新积分
     *
     * @param memberId
     * @param point
     * @return
     */
    int updateIntegration(@Param("memberInfoId") Long memberId, @Param("point") Integer point);

}
