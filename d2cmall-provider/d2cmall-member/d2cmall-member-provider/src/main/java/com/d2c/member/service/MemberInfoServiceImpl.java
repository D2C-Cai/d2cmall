package com.d2c.member.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.dao.MemberInfoMapper;
import com.d2c.member.enums.RestrictTypeEnum;
import com.d2c.member.model.Account;
import com.d2c.member.model.Designers;
import com.d2c.member.model.MemberDetail;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.MemberSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.util.string.RandomUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("memberInfoService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberInfoServiceImpl extends ListServiceImpl<MemberInfo> implements MemberInfoService {

    @Autowired
    private MemberInfoMapper memberInfoMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDetailService memberDetailService;
    @Autowired
    private MemberLoginService memberLoginService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private DesignersService designersService;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;

    /**
     * 用户数量Map TODO 缓存2分钟
     */
    @Override
    public Map<String, Integer> countMemberByBuz() {
        Map<String, Integer> map = new HashMap<>();
        Integer membersCounts = memberInfoMapper.countBySearch(null);
        MemberSearcher searcher = new MemberSearcher();
        searcher.setBought(true);
        Integer boughtMembersCount = memberInfoMapper.countBySearch(searcher);
        map.put("membersCounts", membersCounts);
        map.put("boughtMembersCount", boughtMembersCount);
        // 今天注册数量
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date todayDate = cal.getTime();
        Integer todayRegistCount = this.countRegistered(todayDate, null);
        map.put("todayRegistCount", todayRegistCount);
        // 2天内注册数量
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date secondDayDate = cal.getTime();
        Integer secondDayRegistCount = this.countRegistered(secondDayDate, null);
        map.put("secondDayRegistCount", secondDayRegistCount);
        // 3天内注册数量
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date threeDayDate = cal.getTime();
        Integer threeDayRegistCount = this.countRegistered(threeDayDate, null);
        map.put("threeDayRegistCount", threeDayRegistCount);
        // 一周内注册数量
        cal.add(Calendar.DAY_OF_YEAR, -3);
        Date weekDate = cal.getTime();
        Integer weekRegistCount = this.countRegistered(weekDate, null);
        map.put("weekRegistCount", weekRegistCount);
        // 一个月内注册数量
        cal.add(Calendar.DAY_OF_YEAR, -22);
        Date monthDate = cal.getTime();
        Integer monthRegistCount = this.countRegistered(monthDate, null);
        map.put("monthRegistCount", monthRegistCount);
        return map;
    }

    @Override
    public PageResult<MemberInfo> findBySearch(MemberSearcher searcher, PageModel page) {
        PageResult<MemberInfo> pager = new PageResult<>(page);
        Integer count = memberInfoMapper.countBySearch(searcher);
        if (count > 0) {
            List<MemberInfo> list = memberInfoMapper.findBySearch(searcher, page);
            pager.setList(list);
        }
        pager.setTotalCount(count);
        return pager;
    }

    @Override
    public List<MemberInfo> findPageByLastLogin(int page, int limit) {
        return memberInfoMapper.findPageByLastLogin(new PageModel(page, limit));
    }

    @Override
    public int countBySearch(MemberSearcher searcher) {
        return memberInfoMapper.countBySearch(searcher);
    }

    @Override
    public int countRegistered(Date startDate, Date endDate) {
        return memberInfoMapper.countRegistered(startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> findCountGroupByDevice(MemberSearcher searcher) {
        return memberInfoMapper.findCountGroupByDevice(searcher);
    }

    @Override
    public Integer getLoginError(String loginCode) {
        Integer loginError = redisHandler.get("login_error_times_" + loginCode);
        if (loginError == null) {
            return 0;
        }
        return loginError;
    }

    @Override
    public void updateLoginError(String loginCode, int errorTimes) {
        redisHandler.setInMinutes("login_error_times_" + loginCode, errorTimes, 20);
    }

    @Override
    public void cleanLoginError(String loginCode) {
        redisHandler.delete("login_error_times_" + loginCode);
    }

    @Override
    public MemberInfo findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    public MemberInfo findByLoginCode(String loginCode) {
        return memberInfoMapper.findByLoginCode(loginCode);
    }

    @Override
    public MemberInfo findByRecCode(String recCode) {
        return memberInfoMapper.findByRecCode(recCode);
    }

    @Override
    public MemberInfo findByDesignerId(Long designerId) {
        return memberInfoMapper.findByDesignerId(designerId);
    }

    @Override
    public MemberInfo findByStoreId(Long storeId) {
        return memberInfoMapper.findByStoreId(storeId);
    }

    @Override
    public JSONObject findMemberDetail(Long id, Long designerId, JSONObject json) {
        MemberDetail memberDetail = memberDetailService.findByMemberInfoId(id);
        if (memberDetail != null) {
            json.putAll(memberDetail.toJson());
        }
        if (designerId != null) {
            Designers designers = designersService.findById(designerId);
            if (designers != null) {
                json.putAll(designers.toJsonMap());
            }
        }
        return json;
    }

    @Override
    @Cacheable(value = "memberInfo", key = "'memberInfo_'+#token", unless = "#result == null")
    public MemberInfo findByToken(String token) {
        String loginCode = memberLoginService.findByToken(token);
        if (StringUtil.isBlank(loginCode)) {
            return null;
        }
        return memberInfoMapper.findByLoginCode(loginCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public MemberInfo insert(MemberInfo memberInfo) {
        for (int i = 0; i <= 3; i++) {
            String recCode = RandomUtil.shortUrl(memberInfo.getLoginCode(), i);
            memberInfo.setRecCode(recCode);
            try {
                memberInfo = this.save(memberInfo);
                if (memberInfo.getId() > 0) {
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }
        Account account = new Account(memberInfo.getId(), memberInfo.getLoginCode(), memberInfo.getNationCode());
        accountService.insert(account);
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.setMemberInfoId(memberInfo.getId());
        memberDetail.setLoginCode(memberInfo.getLoginCode());
        memberDetail.setUpgradeDate(new Date());
        memberDetailService.insert(memberDetail);
        return memberInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateLogin(String loginCode, String loginDevice) {
        this.refreshLoginMember(null, loginCode);
        int success = memberInfoMapper.updateLogin(loginCode, loginDevice);
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateToken(String loginCode, String token) {
        this.refreshLoginMember(null, loginCode);
        int success = memberInfoMapper.updateToken(loginCode, token);
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int update(MemberInfo memberInfo) {
        int success = this.updateNotNull(memberInfo);
        if (success > 0) {
            this.refreshLoginMember(memberInfo.getId(), null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updatePassword(String loginCode, String password) {
        int success = memberInfoMapper.updatePassword(loginCode, password);
        if (success > 0) {
            List<String> tokens = memberLoginService.findTokenByLoginCode(loginCode);
            for (String token : tokens) {
                memberLoginService.deleteByToken(token);
                redisHandler.delete("memberInfo_" + token);
            }
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateRecId(Long id, Long recId) {
        int success = memberInfoMapper.updateRecId(id, recId);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateType(Long id, Integer type) {
        int success = memberInfoMapper.updateType(id, type);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateRealName(Long id, String realName, String identityCard) {
        int success = memberInfoMapper.updateRealName(id, realName, identityCard);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doDelete(String loginCode) {
        int success = memberInfoMapper.doDelete(loginCode);
        if (success > 0) {
            memberService.doClean(loginCode);
            accountService.doDelete(loginCode);
            partnerService.doDelete(loginCode);
            List<String> tokens = memberLoginService.findTokenByLoginCode(loginCode);
            for (String token : tokens) {
                memberLoginService.deleteByToken(token);
                redisHandler.delete("memberInfo_" + token);
            }
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindDesigner(Long id, Long designerId, String username) {
        MemberInfo memberInfo = this.findById(id);
        int success = memberInfoMapper.bindDesigner(id, designerId, username, 2);
        if (success > 0) {
            if (memberInfo.getDesignerId() != null) {
                designersService.cancelMemberInfo(id);
            }
            designersService.bindMemberInfo(designerId, id, memberInfo.getLoginCode());
            this.refreshLoginMember(null, memberInfo.getLoginCode());
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelDesigner(Long id) {
        int success = memberInfoMapper.cancelDesigner(id);
        if (success > 0) {
            designersService.cancelMemberInfo(id);
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindDistributor(Long id, Long distributorId) {
        int result = memberInfoMapper.bindDistributor(id, distributorId);
        if (result > 0) {
            this.refreshLoginMember(id, null);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelDistributor(Long id) {
        int success = memberInfoMapper.cancelDistributor(id);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindCrmGroup(Long id, Long crmGroupId) {
        int success = memberInfoMapper.bindCrmGroup(id, crmGroupId);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelCrmGroup(Long id) {
        int success = memberInfoMapper.cancelCrmGroup(id);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindCustomer(Long id, Long storeGroupId) {
        int success = memberInfoMapper.bindCustomer(id, storeGroupId);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelCustomer(Long id) {
        int success = memberInfoMapper.cancelCustomer(id);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindStore(Long id, Long storeId) {
        int success = memberInfoMapper.bindStore(id, storeId);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelStore(Long id) {
        int success = memberInfoMapper.cancelStore(id);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doRestrictShareComment(Long id, Integer status) {
        MemberInfo memberInfo = this.findById(id);
        String restrict = memberInfo.getShield();
        JSONArray array = null;
        if (StringUtils.isNotBlank(restrict)) {
            array = JSONArray.parseArray(restrict);
            if (array.contains(RestrictTypeEnum.SHARECOMMENT.toString()) && status == 0) {
                array.remove(RestrictTypeEnum.SHARECOMMENT.toString());
            } else if (!array.contains(RestrictTypeEnum.SHARECOMMENT.toString()) && status == 1) {
                array.add(RestrictTypeEnum.SHARECOMMENT.toString());
            } else {
                return 1;
            }
        } else if (status == 1) {
            array = new JSONArray();
            array.add(RestrictTypeEnum.SHARECOMMENT.toString());
        }
        int success = memberInfoMapper.doShield(id, array != null && array.size() > 0 ? array.toString() : null);
        if (success > 0) {
            this.refreshLoginMember(null, memberInfo.getLoginCode());
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doAgreeMent(Long id, Date agreeDate) {
        int success = memberInfoMapper.doAgreeMent(id, agreeDate);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @TxTransaction
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindPartner(Long id, Long partnerId) {
        int success = memberInfoMapper.doBindPartner(id, partnerId);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelPartner(Long id) {
        int success = memberInfoMapper.doCancelPartner(id);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindParent(Long id, Long parentId) {
        int success = memberInfoMapper.doBindParent(id, parentId);
        if (success > 0) {
            this.refreshLoginMember(id, null);
        }
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCancelParent(Long parentId) {
        return memberInfoMapper.doCancelParent(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public String doChangeMobile(Long id, String loginCode, String newMobile, String nationCode, String device) {
        MemberInfo old = memberInfoMapper.findByLoginCode(newMobile);
        MemberInfo other = memberInfoMapper.findByLoginCode(loginCode);
        if (old != null) {
            throw new BusinessException("该手机号已存在！");
        }
        String tgt = UUID.randomUUID().toString();
        String newToken = "H-" + DigestUtils.md5Hex(tgt + System.currentTimeMillis());
        int success = memberInfoMapper.doChangeMobile(loginCode, newMobile, nationCode);
        if (success > 0) {
            memberService.doChangeMobile(id, newMobile);
            accountService.doChangeMobile(id, newMobile, nationCode);
            partnerService.doChangeMobile(id, newMobile);
            if (other.getDesignerId() != null) {
                designersService.bindMemberInfo(other.getDesignerId(), other.getId(), newMobile);
            }
            List<String> tokens = memberLoginService.findTokenByLoginCode(loginCode);
            for (String token : tokens) {
                memberLoginService.deleteByToken(token);
                redisHandler.delete("memberInfo_" + token);
            }
            memberLoginService.insert(loginCode, device, newToken);
        }
        return newToken;
    }

    private void refreshLoginMember(Long id, String loginCode) {
        if (id != null) {
            MemberInfo memberInfo = this.findById(id);
            loginCode = memberInfo.getLoginCode();
        }
        List<String> tokens = memberLoginService.findTokenByLoginCode(loginCode);
        for (String token : tokens) {
            redisHandler.delete("memberInfo_" + token);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int updateCertificate(Long memberId, boolean iscertification) {
        int success = memberInfoMapper.updateCertificate(memberId, iscertification);
        if (success > 0) {
            this.refreshLoginMember(memberId, null);
        }
        return success;
    }

}
