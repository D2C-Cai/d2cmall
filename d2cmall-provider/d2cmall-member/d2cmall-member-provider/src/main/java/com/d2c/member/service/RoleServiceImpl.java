package com.d2c.member.service;

import com.d2c.common.api.dto.HelpDTO;
import com.d2c.member.dao.RoleMapper;
import com.d2c.member.dto.RoleDto;
import com.d2c.member.model.Role;
import com.d2c.member.query.RoleSearcher;
import com.d2c.mybatis.service.ListServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("roleService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class RoleServiceImpl extends ListServiceImpl<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public Role findOneById(Long id) {
        return this.findOneById(id);
    }

    @Cacheable(value = "role_resource", key = "'role_resource_'+#resourceId", unless = "#result == null")
    public List<Role> findByResourceId(Long resourceId) {
        return roleMapper.findByResourceId(resourceId);
    }

    @Cacheable(value = "role_admin", key = "'role_admin_'+#adminId", unless = "#result == null")
    @Override
    public List<String> findRoleNameByAdminId(Long adminId) {
        List<String> list = roleMapper.findRoleNameByAdminId(adminId);
        return list;
    }

    public List<RoleDto> findAll(RoleSearcher searcher) {
        List<Role> roles = roleMapper.findAll(searcher);
        List<RoleDto> rDto = this.doCopyProperties(roles);
        return rDto;
    }

    @Override
    public List<HelpDTO> findAllForHelp(RoleSearcher searcher) {
        List<Role> roles = roleMapper.findAll(searcher);
        List<HelpDTO> dtos = new ArrayList<HelpDTO>();
        if (roles.size() > 0) {
            for (Role role : roles) {
                HelpDTO dto = new HelpDTO(role);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private List<RoleDto> doCopyProperties(List<Role> roles) {
        List<RoleDto> dtos = new ArrayList<RoleDto>();
        for (int i = 0; i < roles.size(); i++) {
            RoleDto dto = new RoleDto();
            Role role = roles.get(i);
            BeanUtils.copyProperties(role, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Role insert(Role record) {
        return this.save(record);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Role record) {
        return this.updateNotNull(record);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteById(Long id) {
        return super.deleteById(id);
    }

    @CacheEvict(value = "role_admin", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteAllResourceByRoleid(Long roleid) {
        return roleMapper.deleteAllResourceByRoleid(roleid);
    }

    @CacheEvict(value = "role_admin", allEntries = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteAllAdminByRoleid(Long roleid) {
        return roleMapper.deleteAllAdminByRoleid(roleid);
    }

    @CacheEvict(value = "role_resource", key = "'role_resource_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBindResource(Long id, Long roleid) {
        return roleMapper.bindResource(id, roleid);
    }

    @CacheEvict(value = "role_resource", key = "'role_resource_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doUnbindResource(Long id, Long roleid) {
        return roleMapper.unbindResource(id, roleid);
    }

    @CacheEvict(value = "role_admin", key = "'role_admin_'+#adminId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doBindAdmin(Long adminId, Long roleid) {
        return roleMapper.bindAdmin(adminId, roleid);
    }

    @CacheEvict(value = "role_admin", key = "'role_admin_'+#adminId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doUnbindAdmin(Long adminId, Long roleid) {
        return roleMapper.unbindAdmin(adminId, roleid);
    }

}
