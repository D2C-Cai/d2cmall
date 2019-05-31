package com.d2c.member.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.service.ListService;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.MemberSearcher;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberInfoService extends ListService<MemberInfo> {

    /**
     * 注册会员数量
     *
     * @return
     */
    Map<String, Integer> countMemberByBuz();

    /**
     * 根据searcher查询
     *
     * @param searcher
     * @param page
     * @return
     */
    PageResult<MemberInfo> findBySearch(MemberSearcher searcher, PageModel page);

    /**
     * 查询最后登录
     *
     * @param page
     * @param limit
     * @return
     */
    List<MemberInfo> findPageByLastLogin(int page, int limit);

    /**
     * 查询数量
     *
     * @param searcher
     * @return
     */
    int countBySearch(MemberSearcher searcher);

    /**
     * 查询注册数量
     *
     * @param startDate
     * @param endDate
     * @return
     */
    int countRegistered(Date startDate, Date endDate);

    /**
     * 统计数量
     *
     * @param searcher
     * @return
     */
    List<Map<String, Object>> findCountGroupByDevice(MemberSearcher searcher);

    /**
     * 获取登录错误次数
     *
     * @param loginCode
     * @return
     */
    Integer getLoginError(String loginCode);

    /**
     * 更新登录错误次数
     *
     * @param loginCode
     * @param errorTimes
     */
    void updateLoginError(String loginCode, int errorTimes);

    /**
     * 清除错误登录次数
     *
     * @param loginCode
     */
    void cleanLoginError(String loginCode);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    MemberInfo findById(Long id);

    /**
     * 根据loginCode查询
     *
     * @param loginCode
     * @return
     */
    MemberInfo findByLoginCode(String loginCode);

    /**
     * 根据recCode查询
     *
     * @param recCode
     * @return
     */
    MemberInfo findByRecCode(String recCode);

    /**
     * 根据designerId查询
     *
     * @param designerId
     * @return
     */
    MemberInfo findByDesignerId(Long designerId);

    /**
     * 根据storeId查询
     *
     * @param storeId
     * @return
     */
    MemberInfo findByStoreId(Long storeId);

    /**
     * 查询detail信息
     *
     * @param id
     * @param designerId
     * @param json
     * @return
     */
    JSONObject findMemberDetail(Long id, Long designerId, JSONObject json);

    /**
     * 新增会员
     *
     * @param memberInfo
     * @return
     */
    MemberInfo insert(MemberInfo memberInfo);

    /**
     * 根据token查询
     *
     * @param token
     * @return
     */
    MemberInfo findByToken(String token);

    /**
     * 更新登录信息
     *
     * @param loginCode
     * @param loginDevice
     * @return
     */
    int updateLogin(String loginCode, String loginDevice);

    /**
     * 更新token
     *
     * @param loginCode
     * @param token
     * @return
     */
    int updateToken(String loginCode, String token);

    /**
     * 更新会员
     *
     * @param member
     * @return
     */
    int update(MemberInfo member);

    /**
     * 更新密码
     *
     * @param loginCode
     * @param password
     * @return
     */
    int updatePassword(String loginCode, String password);

    /**
     * 更新推荐
     *
     * @param id
     * @param recId
     * @return
     */
    int updateRecId(Long id, Long recId);

    /**
     * 更新类型
     *
     * @param id
     * @param type
     * @return
     */
    int updateType(Long id, Integer type);

    /**
     * 修改真实姓名
     *
     * @param id
     * @param realName
     * @param identityCard
     * @return
     */
    int updateRealName(Long id, String realName, String identityCard);

    /**
     * 注销账号
     *
     * @param loginCode
     * @return
     */
    int doDelete(String loginCode);

    /**
     * 绑定设计师
     *
     * @param memberInfoId
     * @param designerId
     * @param username
     * @return
     */
    int doBindDesigner(Long memberInfoId, Long designerId, String username);

    /**
     * 解绑设计师
     *
     * @param memberInfoId
     * @return
     */
    int doCancelDesigner(Long memberInfoId);

    /**
     * 绑定经销商
     *
     * @param memberInfoId
     * @param distributorId
     * @return
     */
    int doBindDistributor(Long memberInfoId, Long distributorId);

    /**
     * 解绑经销商
     *
     * @param memberInfoId
     * @return
     */
    int doCancelDistributor(Long memberInfoId);

    /**
     * 绑定crm小组
     *
     * @param memberInfoId
     * @param crmGroupId
     * @return
     */
    int doBindCrmGroup(Long memberInfoId, Long crmGroupId);

    /**
     * 解绑crm小组
     *
     * @param memberInfoId
     * @return
     */
    int doCancelCrmGroup(Long memberInfoId);

    /**
     * 绑定门店顾客
     *
     * @param memberInfoId
     * @param storeGroupId
     * @return
     */
    int doBindCustomer(Long memberInfoId, Long storeGroupId);

    /**
     * 解绑门店顾客
     *
     * @param memberInfoId
     * @return
     */
    int doCancelCustomer(Long memberInfoId);

    /**
     * 绑定门店
     *
     * @param memberInfoId
     * @param storeId
     * @return
     */
    int doBindStore(Long memberInfoId, Long storeId);

    /**
     * 解绑门店
     *
     * @param memberInfoId
     * @return
     */
    int doCancelStore(Long memberInfoId);

    /**
     * 买家秀禁言
     *
     * @param memberInfoId
     * @param status
     * @return
     */
    int doRestrictShareComment(Long memberInfoId, Integer status);

    /**
     * 网签协议
     *
     * @param id
     * @param agreeDate
     * @return
     */
    int doAgreeMent(Long id, Date agreeDate);

    /**
     * 绑定分销商
     *
     * @param id
     * @param partnerId
     * @return
     */
    int doBindPartner(Long id, Long partnerId);

    /**
     * 解绑分销商
     *
     * @param id
     * @return
     */
    int doCancelPartner(Long id);

    /**
     * 绑定上级分销
     *
     * @param id
     * @param parentId
     * @return
     */
    int doBindParent(Long id, Long parentId);

    /**
     * 解绑上级分销
     *
     * @param parentId
     * @return
     */
    int doCancelParent(Long parentId);

    /**
     * 更换绑定手机
     *
     * @param id
     * @param loginCode
     * @param newMobile
     * @param device
     * @return
     */
    String doChangeMobile(Long id, String loginCode, String newMobile, String nationCode, String device);

    /**
     * 设置会员是否实名认证了
     *
     * @param memberId
     * @param b
     * @return
     */
    int updateCertificate(Long memberId, boolean iscertification);

}
