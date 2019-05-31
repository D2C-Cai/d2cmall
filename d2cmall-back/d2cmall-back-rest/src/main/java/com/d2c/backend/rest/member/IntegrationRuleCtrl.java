package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.member.model.IntegrationRule;
import com.d2c.member.query.IntegrationRuleSearcher;
import com.d2c.member.service.IntegrationRuleService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/integrationrule")
public class IntegrationRuleCtrl extends BaseCtrl<IntegrationRuleSearcher> {

    @Autowired
    public IntegrationRuleService integrationRuleService;

    @Override
    protected Response doList(IntegrationRuleSearcher searcher, PageModel page) {
        PageResult<IntegrationRule> pager = integrationRuleService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(IntegrationRuleSearcher searcher) {
        int count = integrationRuleService.countBySearch(searcher);
        return count;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(IntegrationRuleSearcher searcher, PageModel page) {
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
    protected Response doHelp(IntegrationRuleSearcher searcher, PageModel page) {
        PageResult<IntegrationRule> pager = integrationRuleService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse response = new SuccessResponse();
        IntegrationRule integrationRule = integrationRuleService.findById(id);
        response.put("integrationRule", integrationRule);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse response = new SuccessResponse();
        IntegrationRule integrationRule = JsonUtil.instance().toObject(data, IntegrationRule.class);
        BeanUt.trimString(integrationRule);
        integrationRuleService.update(integrationRule);
        return response;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse response = new SuccessResponse();
        IntegrationRule integrationRule = JsonUtil.instance().toObject(data, IntegrationRule.class);
        BeanUt.trimString(integrationRule);
        integrationRule = integrationRuleService.insert(integrationRule);
        response.put("integrationRule", integrationRule);
        return response;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            integrationRuleService.updateStatus(id, status, admin.getUsername());
        }
        return new SuccessResponse();
    }

}
