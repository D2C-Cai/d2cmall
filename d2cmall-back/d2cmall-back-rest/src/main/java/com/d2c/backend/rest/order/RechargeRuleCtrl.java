package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.RechargeRuleDto;
import com.d2c.order.model.RechargeRule;
import com.d2c.order.query.RechargeRuleSearcher;
import com.d2c.order.service.RechargeRuleService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/account/rechargerule")
public class RechargeRuleCtrl extends BaseCtrl<RechargeRuleSearcher> {

    @Autowired
    private RechargeRuleService rechargeRuleService;

    @Override
    protected Response doHelp(RechargeRuleSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected Response doList(RechargeRuleSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<RechargeRuleDto> pager = rechargeRuleService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        RechargeRule rule = (RechargeRule) JsonUtil.instance().toObject(data, RechargeRule.class);
        try {
            SuccessResponse result = new SuccessResponse();
            // rule.validate();
            // TODO 登录处理
            // rule.setCreator(admin.getUsername());
            rule = rechargeRuleService.insert(rule, admin.getUsername());
            result.put("data", rule);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        RechargeRule rule = (RechargeRule) JsonUtil.instance().toObject(data, RechargeRule.class);
        try {
            SuccessResponse result = new SuccessResponse();
            // rule.validate();
            rechargeRuleService.update(rule, admin.getUsername());
            result.put("data", rule);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
    }

    @Override
    protected List<Map<String, Object>> getRow(RechargeRuleSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(RechargeRuleSearcher searcher) {
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
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response doMark(Long id, @PathVariable Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = 0;
        success = rechargeRuleService.doMark(id, admin.getUsername(), status);
        if (success < 0) {
            result.setMessage("操作不成功");
            result.setStatus(-1);
        }
        return result;
    }

}
