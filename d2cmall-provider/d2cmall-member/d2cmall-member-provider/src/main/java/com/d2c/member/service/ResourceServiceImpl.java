package com.d2c.member.service;

import com.d2c.member.dao.ResourceMapper;
import com.d2c.member.dto.ResourceDto;
import com.d2c.member.model.Resource;
import com.d2c.member.model.Role;
import com.d2c.member.query.ResourceSearcher;
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

@Service("resourceService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class ResourceServiceImpl extends ListServiceImpl<Resource> implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleService roleService;

    public Resource findById(Long id) {
        return this.findOneById(id);
    }

    @Cacheable(value = "resource", key = "'resource_'+#value", unless = "#result == null")
    public Resource findByValue(String value) {
        return resourceMapper.findByValue(value);
    }

    public List<Resource> findByRoles(List<String> roles) {
        return resourceMapper.findByRoles(roles);
    }

    public List<Long> findIdsByRoleId(Long id) {
        return resourceMapper.findIdsByRoleId(id);
    }

    public List<ResourceDto> findBySearch(ResourceSearcher searcher) {
        List<Resource> list = resourceMapper.findBySearch(searcher);
        List<ResourceDto> dtos = new ArrayList<ResourceDto>();
        for (Resource rs : list) {
            ResourceDto dto = new ResourceDto();
            BeanUtils.copyProperties(rs, dto);
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Resource insert(Resource resource) {
        return this.save(resource);
    }

    @CacheEvict(value = "resource", key = "'resource_'+#resource.value")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(Resource resource) {
        return this.updateNotNull(resource);
    }

    @CacheEvict(value = "resource", key = "'resource_'+#value")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int deleteById(Long id, String value) {
        int success = resourceMapper.deleteById(id);
        List<Role> roles = roleService.findByResourceId(id);
        if (success > 0) {
            for (int i = 0; i < roles.size(); i++) {
                roleService.doUnbindResource(id, roles.get(i).getId());
            }
        }
        this.deleteAllRoleByResouceId(id);
        return success;
    }

    @Override
    public int deleteAllRoleByResouceId(Long resourceId) {
        return resourceMapper.deleteRoleResouceByResourceId(resourceId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateSequence(Long id, Integer sequence) {
        return resourceMapper.updateSequence(id, sequence);
    }

}