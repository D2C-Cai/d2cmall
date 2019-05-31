package com.d2c.member.service;

import com.d2c.cache.redis.RedisHandler;
import com.d2c.member.dao.MemberLoginMapper;
import com.d2c.member.model.MemberLogin;
import com.d2c.mybatis.service.ListServiceImpl;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("memberLoginService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class MemberLoginServiceImpl extends ListServiceImpl<MemberLogin> implements MemberLoginService {

    @Autowired
    private MemberLoginMapper memberLoginMapper;
    @Autowired
    private RedisHandler<String, String> redisHandler;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public MemberLogin insert(String loginCode, String device, String token) {
        String oldToken = memberLoginMapper.findOld(loginCode, device);
        if (!StringUtil.isBlank(oldToken)) {
            redisHandler.delete("member_login_" + oldToken);
        }
        memberLoginMapper.deleteOld(loginCode, device);
        MemberLogin memberLogin = new MemberLogin();
        memberLogin.setLoginCode(loginCode);
        memberLogin.setDevice(device);
        memberLogin.setToken(token);
        return this.save(memberLogin);
    }

    @Override
    @Cacheable(value = "member_login", key = "'member_login_'+#token", unless = "#result == null")
    public String findByToken(String token) {
        return memberLoginMapper.findByToken(token);
    }

    @Override
    @CacheEvict(value = "member_login", key = "'member_login_'+#token")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int deleteByToken(String token) {
        return memberLoginMapper.deleteByToken(token);
    }

    @Override
    public List<String> findTokenByLoginCode(String loginCode) {
        return memberLoginMapper.findTokenByLoginCode(loginCode);
    }

}
