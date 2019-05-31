package com.d2c.member.dao;

import com.d2c.member.model.PasswordHash;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

public interface PasswordHashMapper extends SuperMapper<PasswordHash> {

    int verify(@Param("loginCode") String loginCode, @Param("code") String code);

    int doInvalid(@Param("loginCode") String loginCode);

    int deleteExpireLog();

    int countDangerPasswd(@Param("password") String password);

}
