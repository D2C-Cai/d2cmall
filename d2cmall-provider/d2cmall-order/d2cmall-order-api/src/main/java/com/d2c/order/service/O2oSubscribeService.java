package com.d2c.order.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.order.dto.O2oSubscribeDto;
import com.d2c.order.model.O2oSubscribe;
import com.d2c.order.model.O2oSubscribeItem;
import com.d2c.order.query.O2oSubscribeSearcher;

import java.math.BigDecimal;
import java.util.Map;

public interface O2oSubscribeService {

    /**
     * 通过id，查询门店预约实体类
     *
     * @param id
     * @return
     */
    O2oSubscribeDto findById(Long id);

    /**
     * 通过门店预约id和门店id，得到门店预约实体数据
     *
     * @param subscribeId
     * @param storeId
     * @return
     */
    O2oSubscribe findByIdAndStoreId(Long subscribeId, Long storeId);

    /**
     * 得到没有提交的门店预约单
     *
     * @param subscribeId
     * @return
     */
    O2oSubscribeDto findUnSubmit(Long subscribeId);

    /**
     * 查询顾客上一次的试衣
     *
     * @param memberId
     * @return
     */
    O2oSubscribe findLastO2oSubscribe(Long memberId);

    /**
     * 分页查询门店预约数据
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<O2oSubscribeDto> findBySearch(O2oSubscribeSearcher searcher, PageModel page);

    /**
     * 查询符合条件的门店预约的数量
     *
     * @param searcher
     * @return
     */
    Integer countBySearch(O2oSubscribeSearcher searcher);

    /**
     * 分析门店预约
     *
     * @param searcher
     * @return
     */
    Map<String, Object> findAnalysisBySearch(O2oSubscribeSearcher searcher);

    /**
     * 各状态的预约单数量
     *
     * @param storeId
     * @return
     */
    Map<String, Integer> countGroupByStatus(Long storeId);

    /**
     * 查询预约次数
     *
     * @param subscribe
     * @return
     */
    int countSubscribeTimes(O2oSubscribe subscribe);

    /**
     * 查询已提交审核的数量
     *
     * @return
     */
    int findPendingCount();

    /**
     * 添加门店预约和门店预约详细的数据
     *
     * @param noSubmit
     * @param items
     * @return
     */
    O2oSubscribe insert(O2oSubscribe noSubmit, O2oSubscribeItem item);

    /**
     * 更新门店预约实体类
     *
     * @param o2oSubscribe
     * @return
     */
    int update(O2oSubscribe o2oSubscribe);

    /**
     * 更新客户回访内容
     *
     * @param id
     * @param visit
     * @return
     */
    int updateVisitById(Long id, String visit);

    /**
     * 更新备注
     *
     * @param id
     * @param remark
     * @return
     */
    int updateRemarkById(Long id, String remark);

    /**
     * 更新客户反馈资金
     *
     * @param o2oSubscribeId
     * @param i
     * @param id
     * @param cusCost
     * @return
     */
    int updateCusCostById(Long o2oSubscribeId, int i, Long id, BigDecimal cusCost);

    /**
     * 通过门店id和会员id，删除门店预约数据
     *
     * @param subscribeId
     * @param memberInfoId
     * @return
     */
    int deleteBy(Long subscribeId, Long memberInfoId);

    /**
     * 客服取消预约
     *
     * @param id
     * @param cancelMan
     * @param cancelReason
     * @return
     */
    int doCancel(Long id, String cancelMan, String cancelReason);

    /**
     * 客服通知门店
     *
     * @param id
     * @param noticeMan
     * @return
     */
    int doNotice(Long id, String noticeMan);

    /**
     * 门店接收预约
     *
     * @param id
     * @param memberId
     * @param loginCode
     * @param displayName
     * @return
     */
    int doReceive(Long id, String loginCode);

    /**
     * 门店准备预约
     *
     * @param id
     * @param memberId
     * @param displayName
     * @return
     */
    int doReady(Long id);

    /**
     * 门店完成预约
     *
     * @param subscribeId
     * @param loginCode
     * @return
     */
    int doComplete(Long subscribeId, String loginCode, String feedback, BigDecimal payAmount, Integer payStatus,
                   Integer actualNumbers, String retailSn);

    /**
     * 用户取消预约
     *
     * @param id
     * @param memberId
     * @return
     */
    int doCancelByMemberId(Long id, Long memberId);

    /**
     * 合并数据
     *
     * @param memberSourceId
     * @param memberTargetId
     * @return
     */
    int doMerge(Long memberSourceId, Long memberTargetId);

}
