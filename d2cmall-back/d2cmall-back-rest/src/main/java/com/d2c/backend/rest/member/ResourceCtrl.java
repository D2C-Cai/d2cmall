package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.dto.ResourceDto;
import com.d2c.member.model.Resource;
import com.d2c.member.query.ResourceSearcher;
import com.d2c.member.service.ResourceService;
import com.d2c.member.service.RoleService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/security/resource")
public class ResourceCtrl extends BaseCtrl<ResourceSearcher> {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleService roleService;

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

    @Override
    protected List<Map<String, Object>> getRow(ResourceSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(ResourceSearcher searcher) {
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
    protected Response doHelp(ResourceSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(ResourceSearcher searcher, PageModel page) {
        SuccessResponse result;
        searcher.setInternal(0);
        List<ResourceDto> list = resourceService.findBySearch(searcher);
        if (StringUtils.isBlank(searcher.getName()) && StringUtils.isBlank(searcher.getType())
                && StringUtils.isBlank(searcher.getValue()) && searcher.getDepth() == null) {
            List<ResourceDto> resources = this.processSequence(list, null, null);
            result = new SuccessResponse(resources);
        } else {
            result = new SuccessResponse(list);
        }
        return result;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        SuccessResponse result = new SuccessResponse();
        Resource rs = this.resourceService.findById(id);
        result.put("data", rs);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        Resource rs = resourceService.findById(id);
        if (rs != null) {
            resourceService.deleteById(id, rs.getValue());
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        data.remove("children");
        Resource resource = (Resource) JsonUtil.instance().toObject(data, Resource.class);
        if (resource.getType().equals("DIR")) {
            resource.setValue(resource.getName());
        }
        if (resource.getParentId() != null) {
            // 子级
            Resource parent = resourceService.findById(resource.getParentId());
            resource.setDepth(parent.getDepth() + 1);
            resource = resourceService.insert(resource);
            resource.setPath(parent.getPath() + "," + resource.getId());
            resourceService.update(resource);
        } else {
            // 首级
            resource.setDepth(1);
            resource = resourceService.insert(resource);
            resource.setPath(resource.getId().toString());
            resourceService.update(resource);
        }
        roleService.doBindResource(resource.getId(), 1L);
        result.put("resource", resource);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        data.remove("children");
        Resource resource = (Resource) JsonUtil.instance().toObject(data, Resource.class);
        // Resource oldResource = resourceService.findById(id);
        // if (StringUtils.isNotBlank(name)) {
        // oldResource.setName(name);
        // }
        // if (StringUtils.isNotBlank(value)) {
        // oldResource.setValue(value);
        // }
        // if (sequence != null) {
        // oldResource.setSequence(sequence);
        // }
        resourceService.update(resource);
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/sort/{id}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable Long id, Integer sequence) {
        resourceService.updateSequence(id, sequence);
        return new SuccessResponse();
    }

}
