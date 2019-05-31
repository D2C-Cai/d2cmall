package com.d2c.member.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.member.query.AdminSearcher;

import java.util.List;

/**
 * 用户账号信息（sys_admin）
 */
public interface AdminService {

    /**
     * 根据id获取用户实体
     *
     * @param id
     * @return
     */
    Admin findById(Long id);

    /**
     * 根据权限id获取拥有此权限的用户信息
     *
     * @param roleId 权限id
     * @return
     */
    List<Admin> findByRoleId(Long roleId);

    /**
     * 根据权限id获取不拥有此权限的用户信息
     *
     * @param roleId 权限id
     * @return
     */
    List<Admin> findNotByRoleId(Long roleId);

    /**
     * 根据登陆账号获取用户信息
     *
     * @param username 登陆账号
     * @return
     */
    Admin findByUserName(String username);

    /**
     * 通过票据得到管理员
     *
     * @param tgt
     * @return
     */
    AdminDto findAdminByTicket(String tgt);

    /**
     * 以AdminSearcher对象中的过滤条件从sys_admin表中获取所有符合条件的用户信息， 放入PageResult对象内返回。
     *
     * @param searcher 过滤器
     * @param page     分页
     * @return
     */
    PageResult<AdminDto> findBySearcher(AdminSearcher searcher, PageModel page);

    /**
     * 以列表形式返回所有用户信息，以供doHelp使用
     *
     * @return
     */
    List<HelpDTO> findAllForHelp();

    /**
     * 保存用户信息
     *
     * @param admin
     * @return
     */
    Admin insert(Admin admin);

    /**
     * 更新用户信息
     *
     * @param admin
     * @return
     */
    int update(Admin admin);

    /**
     * 更新用户信息
     *
     * @param id
     * @param department
     * @param name
     * @param mobile
     * @return
     */
    int updateInfo(Long id, String department, String name, String mobile);

    /**
     * 根据id更新用户密码
     *
     * @param id
     * @param md5 使用MD5加密后的用户密码
     * @return
     */
    int updatePassword(Long id, String md5);

    /**
     * 根据登陆账号以及旧密码设置新密码
     *
     * @param username 登陆账号
     * @param md5      旧密码
     * @param md52     新密码
     * @return
     */
    int updateMyPassword(String username, String md5, String md52);

    /**
     * 更新管理员的票据数据
     *
     * @param id
     * @param tgt    票据
     * @param lastIp
     * @return
     */
    Admin updateAdminTicket(Long id, String tgt, String loginIp);

    /**
     * 根据id删除用户信息
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 清理票据
     *
     * @param tgt
     */
    void clearAdminTicket(String tgt);

}
