package com.d2c.member.dao;

import com.d2c.common.api.page.PageModel;
import com.d2c.member.model.Admin;
import com.d2c.member.query.AdminSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper extends SuperMapper<Admin> {

    List<Admin> findByRoleId(@Param("id") Long id);

    List<Admin> findNotByRoleId(@Param("id") Long id);

    Admin findByUserName(@Param("userName") String userName);

    Admin findAdminByTicket(@Param("tgt") String tgt);

    List<Admin> findBySearcher(@Param("searcher") AdminSearcher searcher, @Param("pager") PageModel pager);

    Integer countBySearcher(@Param("searcher") AdminSearcher searcher);

    List<Admin> findAll();

    int updateMyPassword(@Param("userName") String userName, @Param("oldPassword") String oldPassword,
                         @Param("newPassword") String newPassword);

    int updatePassword(@Param("id") Long id, @Param("newPassword") String newPassword);

    int updateAdminTicket(@Param("id") Long id, @Param("tgt") String tgt, @Param("loginIp") String loginIp);

    int updateInfo(@Param("id") Long id, @Param("department") String department, @Param("name") String name,
                   @Param("mobile") String mobile);

    int deleteById(@Param("id") Long id);

}
