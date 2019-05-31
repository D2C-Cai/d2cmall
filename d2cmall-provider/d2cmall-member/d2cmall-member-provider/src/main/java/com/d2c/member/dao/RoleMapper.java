package com.d2c.member.dao;

import com.d2c.member.model.Role;
import com.d2c.member.query.RoleSearcher;
import com.d2c.mybatis.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends SuperMapper<Role> {

    List<Role> findByResourceId(@Param("resourceId") Long resourceId);

    List<String> findRoleNameByAdminId(@Param("adminId") Long adminId);

    /**
     * 寻找所有的角色
     */
    List<Role> findAll(@Param("searcher") RoleSearcher searcher);

    int deleteAllResourceByRoleid(@Param("roleid") Long roleid);

    int deleteAllAdminByRoleid(@Param("roleid") Long roleid);

    /**
     * 角色绑定用户
     */
    int bindAdmin(@Param("id") Long id, @Param("roleid") Long roleid);

    /**
     * 角色解绑用户
     */
    int unbindAdmin(@Param("id") Long id, @Param("roleid") Long roleid);

    /**
     * 角色绑定资源
     */
    int bindResource(@Param("id") Long id, @Param("roleid") Long roleid);

    /**
     * 角色解绑资源
     */
    int unbindResource(@Param("id") Long id, @Param("roleid") Long roleid);

}