package com.d2c.member.service;

import com.d2c.member.model.MemberLogin;

import java.util.List;

public interface MemberLoginService {

    /**
     * 生成登录信息
     *
     * @param loginCode
     * @param device
     * @param token
     * @return
     */
    MemberLogin insert(String loginCode, String device, String token);

    /**
     * 根据token查询账号
     *
     * @param token
     * @return
     */
    String findByToken(String token);

    /**
     * 根据token删除
     *
     * @param token
     * @return
     */
    int deleteByToken(String token);

    /**
     * 根据账号查询token
     *
     * @param loginCode
     * @return
     */
    List<String> findTokenByLoginCode(String loginCode);

}