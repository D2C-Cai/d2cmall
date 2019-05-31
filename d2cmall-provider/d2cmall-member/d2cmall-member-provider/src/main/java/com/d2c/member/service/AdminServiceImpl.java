package com.d2c.member.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.member.dao.AdminMapper;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.member.query.AdminSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("adminService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class AdminServiceImpl extends ListServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleService roleService;

    public Admin findById(Long id) {
        return this.findOneById(id);
    }

    public List<Admin> findByRoleId(Long roleId) {
        return adminMapper.findByRoleId(roleId);
    }

    public List<Admin> findNotByRoleId(Long roleId) {
        return adminMapper.findNotByRoleId(roleId);
    }

    public Admin findByUserName(String userName) {
        return adminMapper.findByUserName(userName);
    }

    @Override
    @Cacheable(value = "admin", key = "'admin_'+#tgt", unless = "#result == null")
    public AdminDto findAdminByTicket(String tgt) {
        if (StringUtils.isEmpty(tgt)) {
            return null;
        }
        Admin admin = adminMapper.findAdminByTicket(tgt);
        if (admin == null) {
            return null;
        }
        AdminDto adminDto = new AdminDto();
        BeanUtils.copyProperties(admin, adminDto);
        List<String> roles = roleService.findRoleNameByAdminId(admin.getId());
        adminDto.setRoleValues(roles);
        return adminDto;
    }

    @Override
    public PageResult<AdminDto> findBySearcher(AdminSearcher searcher, PageModel page) {
        PageResult<AdminDto> pager = new PageResult<AdminDto>(page);
        int totalCount = adminMapper.countBySearcher(searcher);
        List<Admin> list = new ArrayList<Admin>();
        List<AdminDto> dtos = new ArrayList<AdminDto>();
        if (totalCount > 0) {
            list = adminMapper.findBySearcher(searcher, page);
            for (Admin admin : list) {
                AdminDto dto = new AdminDto();
                BeanUtils.copyProperties(admin, dto);
                dtos.add(dto);
            }
        }
        pager.setTotalCount(totalCount);
        pager.setList(dtos);
        return pager;
    }

    @Override
    public List<HelpDTO> findAllForHelp() {
        List<Admin> roles = adminMapper.findAll();
        List<HelpDTO> dtos = new ArrayList<HelpDTO>();
        if (roles.size() > 0) {
            for (Admin role : roles) {
                HelpDTO dto = new HelpDTO(role);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Admin insert(Admin admin) {
        return this.save(admin);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Admin admin) {
        return this.updateNotNull(admin);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateMyPassword(String userName, String oldPassword, String newPassword) {
        return adminMapper.updateMyPassword(userName, oldPassword, newPassword);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updatePassword(Long id, String newPassword) {
        return adminMapper.updatePassword(id, newPassword);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateInfo(Long id, String department, String name, String mobile) {
        return adminMapper.updateInfo(id, department, name, mobile);
    }

    @Override
    @CacheEvict(value = "admin", key = "'admin_'+#tgt")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Admin updateAdminTicket(Long id, String tgt, String loginIp) {
        // 这里用缓存的策略是 将 tgt为key 查找，因为在rest，获取admin，是通过tgt为唯一值，查找
        adminMapper.updateAdminTicket(id, tgt, loginIp);
        return this.findAdminByTicket(tgt);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteById(Long id) {
        return adminMapper.deleteById(id);
    }

    @Override
    @CacheEvict(value = "admin", key = "'admin_'+#tgt")
    public void clearAdminTicket(String tgt) {
        // 清除缓存
    }

}
