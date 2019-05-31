package com.d2c.member.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.member.dto.RoleDto;
import com.d2c.member.model.Role;
import com.d2c.member.query.RoleSearcher;

import java.util.List;

/**
 * 权限（sys_role、sys_admin_role）
 */
public interface RoleService {

    /**
     * 根据id查找权限信息
     *
     * @param id
     * @return
     */
    Role findOneById(Long id);

    /**
     * 根据资源id查找所有不重复的权限信息。
     *
     * @param id 资源id
     * @return
     */
    List<Role> findByResourceId(Long id);

    /**
     * 通过admin的id，得到符合条件的角色名字
     *
     * @param adminId
     * @return
     */
    List<String> findRoleNameByAdminId(Long adminId);

    /**
     * 获取所有权限信息
     *
     * @return
     */
    List<RoleDto> findAll(RoleSearcher searcher);

    /**
     * 获取所有权限信息，提供给doHelp使用
     *
     * @return
     */
    List<HelpDTO> findAllForHelp(RoleSearcher searcher);

    /**
     * 插入权限信息
     *
     * @param role
     * @return
     */
    Role insert(Role role);

    /**
     * 更改权限信息
     *
     * @param role
     * @return
     */
    int update(Role role);

    /**
     * 根据id删除权限信息
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据权限id解绑资源
     *
     * @param roleId 权限id
     * @return
     */
    int deleteAllResourceByRoleid(Long roleId);

    /**
     * 根据权限id解绑用户
     *
     * @param roleId 权限id
     * @return
     */
    int deleteAllAdminByRoleid(Long roleId);

    /**
     * 角色绑定资源
     *
     * @param id     角色id
     * @param roleId 资源id
     * @return
     */
    int doBindResource(Long id, Long roleId);

    /**
     * 角色解绑资源
     *
     * @param id     角色id
     * @param roleId 资源id
     * @return
     */
    int doUnbindResource(Long id, Long roleId);

    /**
     * 角色绑定用户
     *
     * @param adminId 用户id
     * @param roleid  权限id
     * @return
     */
    int doBindAdmin(Long adminId, Long roleid);

    /**
     * 角色解绑用户
     *
     * @param adminId 用户id
     * @param roleid  权限id
     * @return
     */
    int doUnbindAdmin(Long adminId, Long roleid);

}
