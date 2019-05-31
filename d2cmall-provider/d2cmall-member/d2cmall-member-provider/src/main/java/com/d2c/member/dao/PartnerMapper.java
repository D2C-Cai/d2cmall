package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Partner;
import com.d2c.member.query.PartnerSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PartnerMapper extends SuperMapper<Partner> {

    Partner findByLoginCode(@Param("loginCode") String loginCode);

    Partner findByMemberId(@Param("memberId") Long memberId);

    Partner findContract(@Param("identityCard") String identityCard);

    List<Partner> findBySearcher(@Param("searcher") PartnerSearcher searcher, @Param("pager") PageModel pager);

    int countBySearcher(@Param("searcher") PartnerSearcher searcher);

    List<Partner> findExpired(@Param("pager") PageModel pager);

    int countExpired();

    List<Long> findVaildIds();

    List<Map<String, Object>> countChildrenGroup(@Param("id") Long id, @Param("rid") String rid);

    List<Map<String, Object>> countChildrenToday(@Param("id") Long id, @Param("rid") String rid,
                                                 @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Map<String, Object>> findCountGroupByLevel(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<Map<String, Object>> findCountGroupByStatus();

    int updateFirstDate(@Param("id") Long id, @Param("date") Date date);

    int updateConsumeDate(@Param("id") Long id, @Param("date") Date date);

    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("name") String name);

    int doChangeMobile(@Param("memberId") Long memberId, @Param("newMobile") String newMobile);

    int doRebate(@Param("id") Long id, @Param("amount") BigDecimal amount,
                 @Param("actualAmount") BigDecimal actualAmount);

    int doAward(@Param("id") Long id, @Param("amount") BigDecimal amount);

    int doGift(@Param("id") Long id, @Param("amount") BigDecimal amount);

    int doApplyCash(@Param("id") Long id, @Param("amount") BigDecimal amount, @Param("direction") Integer direction);

    int doWithdCash(@Param("id") Long id, @Param("amount") BigDecimal amount,
                    @Param("applyAmount") BigDecimal applyAmount);

    int doBindCounselor(@Param("id") Long id, @Param("counselorId") Long counselorId);

    int doContract(@Param("id") Long id, @Param("contract") Integer contract);

    int updateParentId(@Param("id") Long id, @Param("parentId") Long parentId);

    int updateStoreId(@Param("id") Long id, @Param("storeId") Long storeId);

    int updateLevel(@Param("id") Long id, @Param("level") Integer level);

    int updateOpenId(@Param("id") Long id, @Param("openId") String openId);

    int doSeparate(@Param("id") Long id);

    int cancelParentMaster(@Param("id") Long id);

    int doRefreshHeadPic(@Param("memberInfoId") Long memberInfoId, @Param("headPic") String headPic,
                         @Param("nickName") String nickName);

    int doDelete(@Param("loginCode") String loginCode);

    int doWithhold(@Param("id") Long id, @Param("amount") BigDecimal amount);

    List<Partner> findCancelRemind(Integer intervalDays);

    List<String> findOpenIdByMemberId(@Param("memberIds") List<Long> ids);

    List<String> findTags();

    int updateTags(@Param("id") Long id, @Param("tags") String tags);

    int updateGiftCount(@Param("id") Long id, @Param("count") Integer count);

    int updatePrestore(@Param("id") Long id, @Param("count") Integer count);

    int cancelPrestore(@Param("id") Long id);

    int updatePath(@Param("id") Long id, @Param("path") String path);

    List<Partner> findLevelsByIds(@Param("ids") List<Long> ids);

    int updatePoint(@Param("ids") List<Long> ids, @Param("count") Integer count);

    int updateUpgrade(@Param("id") Long id, @Param("upgrade") Integer upgrade);

    int updateWithdraw(@Param("id") Long id, @Param("withdraw") Integer withdraw);

    int doLogin(@Param("id") Long id);

}
