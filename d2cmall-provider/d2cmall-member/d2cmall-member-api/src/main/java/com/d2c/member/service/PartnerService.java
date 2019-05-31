package com.d2c.member.service;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.service.ListService;
import com.d2c.member.model.Partner;
import com.d2c.member.query.PartnerSearcher;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PartnerService extends ListService<Partner> {

    /**
     * 插入一条记录
     *
     * @param partner
     * @return
     */
    Partner insert(Partner partner);

    /**
     * 更新一条记录
     *
     * @param partner
     * @return
     */
    int update(Partner partner);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Partner findById(Long id);

    /**
     * 通过账号查询
     *
     * @param loginCode
     * @return
     */
    Partner findByLoginCode(String loginCode);

    /**
     * 通过会员ID查询
     *
     * @param memberId
     * @return
     */
    Partner findByMemberId(Long memberId);

    /**
     * 根据身份证号查询
     *
     * @param identityCard
     * @return
     */
    Partner findContract(String identityCard);

    /**
     * 分页查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<Partner> findBySearcher(PartnerSearcher searcher, PageModel page);

    /**
     * 按条件统计总数
     *
     * @param searcher
     * @return
     */
    int countBySearcher(PartnerSearcher searcher);

    /**
     * 查询试用期已过的买手
     *
     * @param page
     * @return
     */
    PageResult<Partner> findExpired(PageModel page);

    /**
     * 查询试用期已过的买手数量
     *
     * @return
     */
    int countExpired();

    /**
     * 直接下属，有30个累计成交额大于等于1000元的分销商
     *
     * @return
     */
    List<Long> findVaildIds();

    /**
     * 查询下级数量
     *
     * @param id
     * @param rid
     * @return
     */
    List<Map<String, Object>> countChildrenGroup(Long id, String rid);

    /**
     * 查询今日下级数量
     *
     * @param id
     * @param rid
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> countChildrenToday(Long id, String rid, Date startDate, Date endDate);

    /**
     * 根据等级统计
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> findCountGroupByLevel(Date beginDate, Date endDate);

    /**
     * 根据状态统计
     *
     * @return
     */
    List<Map<String, Object>> findCountGroupByStatus();

    /**
     * 停用启用
     *
     * @param id
     * @param status
     * @param operator
     * @return
     */
    int updateStatus(Long id, Integer status, String operator);

    /**
     * 修改绑定手机
     *
     * @param memberId
     * @param newMobile
     * @return
     */
    int doChangeMobile(Long memberId, String newMobile);

    /**
     * 试用期通过
     *
     * @param id
     * @param operator
     * @return
     */
    int doActive(Long id, String operator);

    /**
     * 试用期不通过
     *
     * @param id
     * @param memberId
     * @param operator
     * @return
     */
    int doCancel(Long id, Long memberId, String operator);

    /**
     * 购买礼包生成分销
     *
     * @param parentId
     * @param memberId
     * @param level
     * @return
     */
    int doCreate(Long parentId, Long memberId, int level);

    /**
     * 试用期复活
     *
     * @param id
     * @param memberId
     * @param operator
     * @return
     */
    int doPass(Long id, Long memberId, String operator);

    /**
     * 获得返利
     *
     * @param id
     * @param amount       返利金额
     * @param actualAmount 订单支付金额
     * @param operator
     * @return
     */
    int doRebate(Long id, BigDecimal amount, BigDecimal actualAmount, String operator);

    /**
     * 获得奖励
     *
     * @param partnerId
     * @param amount
     * @param operator
     * @return
     */
    int doAward(Long partnerId, BigDecimal amount, String operator);

    /**
     * 礼包奖励
     *
     * @param partnerId
     * @param amount
     * @param operator
     * @return
     */
    int doGift(Long partnerId, BigDecimal amount, String operator);

    /**
     * 申请提现金额
     *
     * @param id
     * @param amount
     * @param direction
     * @return
     */
    int doApplyCash(Long id, BigDecimal amount, Integer direction);

    /**
     * 提现成功
     *
     * @param id
     * @param amount
     * @param applyAmount
     * @param operator
     * @return
     */
    int doWithdCash(Long id, BigDecimal amount, BigDecimal applyAmount, String operator);

    /**
     * 绑定运营顾问
     *
     * @param id
     * @param counselorId
     * @param operator
     * @return
     */
    int doBindCounselor(Long id, Long counselorId, String operator);

    /**
     * 工猫电签
     *
     * @param id
     * @param contract
     * @return
     */
    int doContract(Long id, Integer contract);

    /**
     * 更新最后开单时间
     *
     * @param ids
     * @param status
     * @return
     */
    int updateConsumeDate(Long id, int status);

    /**
     * 更新店铺ID
     *
     * @param id
     * @param storeId
     * @param operator
     * @return
     */
    int updateStoreId(Long id, Long storeId, String operator);

    /**
     * 更改等级
     *
     * @param id
     * @param level
     * @param operator
     * @return
     */
    int updateLevel(Long id, Integer level, String operator);

    /**
     * 更新openId
     *
     * @param id
     * @param openId
     * @return
     */
    int updateOpenId(Long id, String openId);

    /**
     * 删除下级
     *
     * @param id
     * @param childId
     * @param operator
     * @return
     */
    int doSeparate(Long id, Long childId, String operator);

    /**
     * 刷新用户头像
     *
     * @param memberInfoId
     * @param headPic
     * @param nickName
     */
    void doRefreshHeadPic(Long memberInfoId, String headPic, String nickName);

    /**
     * 注销账户
     *
     * @param loginCode
     * @return
     */
    int doDelete(String loginCode);

    /**
     * 扣款
     *
     * @param partnerId
     * @param amount
     * @param operator
     * @param type
     * @return
     */
    int doWithhold(Long partnerId, BigDecimal amount, String operator, String type);

    /**
     * 绑定门店标识
     *
     * @param id
     * @param mark
     * @return
     */
    int doBindStoreMark(Long id, Integer mark);

    /**
     * 关闭提醒
     *
     * @param intervalDays 取消前多少天提醒
     * @return
     */
    List<Partner> findCancelRemind(Integer intervalDays);

    /**
     * 通过会员ID查询分销OpenId
     *
     * @param ids
     * @return
     */
    List<String> findOpenIdByMemberId(List<Long> ids);

    /**
     * 查询标签
     *
     * @return
     */
    List<String> findTags();

    /**
     * 更新标签
     *
     * @param id
     * @param tags
     * @return
     */
    int updateTags(Long id, String tags);

    /**
     * 更改预存礼包数
     *
     * @param id
     * @param count
     * @param operator
     * @return
     */
    int updatePrestore(Long id, Integer count, String operator);

    /**
     * 清空预存礼包数
     *
     * @param id
     * @param operator
     * @return
     */
    int cancelPrestore(Long id, String operator);

    /**
     * 更新邀请人路径
     *
     * @param id
     * @param path
     * @return
     */
    int updatePath(Long id, String path);

    /**
     * 获取积分奖励人ID
     *
     * @param path
     * @return
     */
    List<Long> findPointRelation(String path);

    /**
     * 批量赠送积分
     *
     * @param ids
     * @param count
     * @return
     */
    int updatePoint(List<Long> ids, Integer count);

    /**
     * 是否允许升级
     *
     * @param id
     * @param upgrade
     * @return
     */
    int updateUpgrade(Long id, Integer upgrade);

    /**
     * 是否允许提现
     *
     * @param id
     * @param withdraw
     * @return
     */
    int updateWithdraw(Long id, Integer withdraw);

    /**
     * 最后登录时间
     *
     * @param id
     * @return
     */
    int doLogin(Long id);

}
