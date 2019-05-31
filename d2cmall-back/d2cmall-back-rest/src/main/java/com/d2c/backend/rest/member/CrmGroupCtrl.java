package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.CrmGroup;
import com.d2c.member.query.CrmGroupSearcher;
import com.d2c.member.query.MemberSearcher;
import com.d2c.member.service.CrmGroupService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/crmreceptiongroup")
public class CrmGroupCtrl extends BaseCtrl<CrmGroupSearcher> {

    @Autowired
    private CrmGroupService crmGroupService;
    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    protected Response doList(CrmGroupSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<CrmGroup> pager = crmGroupService.findBySearcher(page, searcher);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(CrmGroupSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(CrmGroupSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
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
    protected Response doHelp(CrmGroupSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        CrmGroup crmReceptionGroup = crmGroupService.findById(id);
        SuccessResponse result = new SuccessResponse();
        result.put("crmReceptionGroup", crmReceptionGroup);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        CrmGroup crmReceptionGroup = JsonUtil.instance().toObject(data, CrmGroup.class);
        SuccessResponse result = new SuccessResponse();
        CrmGroup old = crmGroupService.findByName(crmReceptionGroup.getName());
        if (old != null && old.getId() != crmReceptionGroup.getId()) {
            result.setStatus(-1);
            result.setMessage("接待小组名字不能重复！");
            return result;
        }
        crmGroupService.update(crmReceptionGroup);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        CrmGroup crmReceptionGroup = (CrmGroup) JsonUtil.instance().toObject(data, CrmGroup.class);
        CrmGroup old = crmGroupService.findByName(crmReceptionGroup.getName());
        if (old != null) {
            result.setStatus(-1);
            result.setMessage("接待小组名字不能重复！");
            return result;
        }
        crmReceptionGroup = crmGroupService.insert(crmReceptionGroup);
        result.put("crmReceptionGroup", crmReceptionGroup);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        MemberSearcher searcher = new MemberSearcher();
        searcher.setCrmGroupId(id);
        int totalCount = memberInfoService.countBySearch(searcher);
        if (totalCount > 0) {
            result.setStatus(-1);
            result.setMessage("该小组有会员绑定，无法删除！");
            return result;
        }
        crmGroupService.updateStatus(id, -1);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/updateStatus", method = {RequestMethod.POST})
    public Response updateStatus(Long id, Integer status) {
        SuccessResponse result = new SuccessResponse();
        crmGroupService.updateStatus(id, status);
        return result;
    }

    @RequestMapping(value = "/updateSort", method = {RequestMethod.POST})
    public Response updateSort(Long id, Integer sort) {
        SuccessResponse result = new SuccessResponse();
        crmGroupService.updateSort(id, sort);
        return result;
    }

}
