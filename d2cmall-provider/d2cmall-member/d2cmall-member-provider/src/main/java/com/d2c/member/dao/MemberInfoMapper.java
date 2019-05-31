package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.MemberSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberInfoMapper extends SuperMapper<MemberInfo> {

    String findTokenById(@Param("id") Long id);

    String findTokenByLoginCode(@Param("loginCode") String loginCode);

    List<MemberInfo> findBySearch(@Param("searcher") MemberSearcher searcher, @Param("pager") PageModel pager);

    List<MemberInfo> findPageByLastLogin(@Param("pager") PageModel pager);

    int countBySearch(@Param("searcher") MemberSearcher searcher);

    int countRegistered(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<Map<String, Object>> findCountGroupByDevice(@Param("searcher") MemberSearcher searcher);

    MemberInfo findByLoginCode(@Param("loginCode") String loginCode);

    MemberInfo findByRecCode(@Param("loginCode") String recCode);

    MemberInfo findByDesignerId(@Param("designerId") Long designerId);

    MemberInfo findByStoreId(@Param("storeId") Long storeId);

    MemberInfo findByToken(@Param("token") String token);

    int updateLogin(@Param("loginCode") String loginCode, @Param("loginDevice") String loginDevice);

    int updateToken(@Param("loginCode") String loginCode, @Param("token") String token);

    int updateRecId(@Param("id") Long id, @Param("recId") Long recId);

    int updateType(@Param("id") long id, @Param("type") Integer type);

    int updatePassword(@Param("loginCode") String loginCode, @Param("password") String password);

    int bindDesigner(@Param("id") Long id, @Param("designerId") Long designerId, @Param("operator") String operator,
                     @Param("type") Integer type);

    int cancelDesigner(@Param("id") Long id);

    int bindCrmGroup(@Param("id") Long id, @Param("crmGroupId") Long crmGroupId);

    int cancelCrmGroup(@Param("id") Long id);

    int bindCustomer(@Param("id") Long id, @Param("storeGroupId") Long storeGroupId);

    int cancelCustomer(@Param("id") Long id);

    int bindDistributor(@Param("id") Long id, @Param("distributorId") Long distributorId);

    int cancelDistributor(@Param("id") Long id);

    int bindStore(@Param("id") Long id, @Param("storeId") Long storeId);

    int cancelStore(@Param("id") Long id);

    int doDelete(@Param("loginCode") String loginCode);

    int doShield(@Param("id") Long id, @Param("shield") String shield);

    int doAgreeMent(@Param("id") Long id, @Param("agreeDate") Date agreeDate);

    int doBindPartner(@Param("id") Long id, @Param("partnerId") Long partnerId);

    int doCancelPartner(@Param("id") Long id);

    int doBindParent(@Param("id") Long id, @Param("parentId") Long parentId);

    int doCancelParent(@Param("parentId") Long parentId);

    int doChangeMobile(@Param("loginCode") String loginCode, @Param("newMobile") String newMobile,
                       @Param("nationCode") String nationCode);

    int updateRealName(@Param("id") Long id, @Param("realName") String realName,
                       @Param("identityCard") String identityCard);

    int updateCertificate(@Param("id") Long id, @Param("certification") boolean certification);

}
