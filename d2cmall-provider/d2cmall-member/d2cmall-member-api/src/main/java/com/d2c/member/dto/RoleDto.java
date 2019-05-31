package com.d2c.member.dto;

import com.d2c.member.model.Resource;
import com.d2c.member.model.Role;

import java.util.Set;

public class RoleDto extends Role {

    private static final long serialVersionUID = 1L;
    /**
     * 可用菜单
     */
    private Set<Resource> resourceSet;

    public Set<Resource> getResourceSet() {
        return this.resourceSet;
    }

    public void setResourceSet(Set<Resource> resourceSet) {
        this.resourceSet = resourceSet;
    }

}
