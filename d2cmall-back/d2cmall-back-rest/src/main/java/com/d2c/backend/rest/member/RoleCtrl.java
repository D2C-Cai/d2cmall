package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.dto.ResourceDto;
import com.d2c.member.dto.RoleDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.Role;
import com.d2c.member.query.ResourceSearcher;
import com.d2c.member.query.RoleSearcher;
import com.d2c.member.service.AdminService;
import com.d2c.member.service.ResourceService;
import com.d2c.member.service.RoleService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/security/role")
public class RoleCtrl extends BaseCtrl<RoleSearcher> {

    @Autowired
    private RoleService roleService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ResourceService resourceService;

    @Override
    protected List<Map<String, Object>> getRow(RoleSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(RoleSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(RoleSearcher searcher, PageModel page) {
        List<HelpDTO> roles = roleService.findAllForHelp(searcher);
        return new SuccessResponse(roles);
    }

    @Override
    protected Response doList(RoleSearcher searcher, PageModel page) {
        List<RoleDto> roles = roleService.findAll(searcher);
        return new SuccessResponse(roles);
    }

    @Override
    protected Response findById(Long id) {
        Role role = roleService.findOneById(id);
        // TODO Auto-generated method stub
        return new SuccessResponse(role);
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        for (Long id : ids) {
            this.doDelete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        roleService.deleteById(id);
        roleService.deleteAllResourceByRoleid(id);
        roleService.deleteAllAdminByRoleid(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Role role = (Role) JsonUtil.instance().toObject(data, Role.class);
        String description = role.getDescription();
        Integer isSystem = role.getIsSystem();
        role.setDescription(description);
        role.setName(description);
        role.setValue(description);
        if (isSystem == null) {
            role.setIsSystem(0);
        } else {
            role.setIsSystem(isSystem);
        }
        Role newrole = roleService.insert(role);
        Long roleId = newrole.getId();
        newrole.setValue("ROLE_" + roleId);
        newrole.setName("ROLE_" + roleId);
        roleService.update(newrole);
        this.bindSystemResource(roleId);
        result.put("role", newrole);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    private int bindSystemResource(Long roleId) {
        ResourceSearcher searcher = new ResourceSearcher();
        searcher.setInternal(1);
        List<ResourceDto> resouces = resourceService.findBySearch(searcher);
        for (ResourceDto resouce : resouces) {
            roleService.doBindResource(resouce.getId(), roleId);
        }
        return 0;
    }

    /**
     * 跳转至权限绑定用户界面used
     *
     * @param model
     * @param role
     * @return
     */
    @RequestMapping(value = "/bind/admin", method = RequestMethod.GET)
    public Response bindAdmin(Long id) {
        SuccessResponse result = new SuccessResponse();
        List<Admin> admins = adminService.findByRoleId(id);
        List<Admin> notadmins = adminService.findNotByRoleId(id);
        result.put("admins", admins);
        result.put("notadmins", notadmins);
        result.put("roleid", id);
        return result;
    }

    /**
     * 批量进行权限绑定用户used
     *
     * @param model
     * @param id
     * @param roleid
     * @return
     */
    @RequestMapping(value = "/bindAdmins", method = RequestMethod.POST)
    public Response bindAdmins(String id, Long roleid) {
        String adminIds[] = id.split(",");
        for (int i = 0; i < adminIds.length; i++) {
            Long adminId = Long.valueOf(adminIds[i]);
            roleService.doBindAdmin(adminId, roleid);
        }
        return new SuccessResponse();
    }

    /**
     * 批量进行权限解绑用户
     *
     * @param model
     * @param id
     * @param roleid
     * @return
     */
    @RequestMapping(value = "/unbindAdmins", method = RequestMethod.POST)
    public Response unbindAdmins(String id, Long roleid) {
        String adminIds[] = id.split(",");
        for (int i = 0; i < adminIds.length; i++) {
            Long adminId = Long.valueOf(adminIds[i]);
            roleService.doUnbindAdmin(adminId, roleid);
        }
        return new SuccessResponse();
    }

    /**
     * 跳转至权限绑定资源界面used
     *
     * @param model
     * @param role
     * @return
     */
    @RequestMapping(value = "/bind/resource", method = RequestMethod.POST)
    public Response bindResource(Role role) {
        SuccessResponse result = new SuccessResponse();
        ResourceSearcher searcher = new ResourceSearcher();
        List<ResourceDto> list = resourceService.findBySearch(searcher);
        List<ResourceDto> resources = this.processSequence(list, null, null);
        result.put("resources", resources);
        List<Long> ids = resourceService.findIdsByRoleId(role.getId());
        result.put("ids", ids);
        result.put("roleId", role.getId());
        return result;
    }

    private List<ResourceDto> processSequence(List<ResourceDto> all, Long p, List<ResourceDto> temp) {
        if (temp == null) {
            temp = new ArrayList<ResourceDto>();
        }
        for (ResourceDto node : all) {
            if ((p == null && node.getParentId() == null)
                    || (node != null && node.getParentId() != null && node.getParentId().equals(p))) {
                temp.add(node);
                processSequence(all, node.getId(), temp);
            }
        }
        return temp;
    }

    /**
     * 绑定资源used
     *
     * @param model
     * @param id
     * @param roleid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bindResources", method = RequestMethod.POST)
    public Response bindResources(Long id, Long roleid) throws Exception {
        roleService.doBindResource(id, roleid);
        return new SuccessResponse();
    }

    /**
     * 解绑资源
     *
     * @param model
     * @param id
     * @param roleid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/unBindResources", method = RequestMethod.POST)
    public Response unBindResources(Long id, Long roleid) throws Exception {
        roleService.doUnbindResource(id, roleid);
        return new SuccessResponse();
    }

}
