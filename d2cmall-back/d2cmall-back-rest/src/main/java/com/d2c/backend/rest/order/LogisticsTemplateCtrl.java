package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.LogisticsTemplateDto;
import com.d2c.order.model.LogisticsPostage;
import com.d2c.order.query.LogisticsTemplateSearcher;
import com.d2c.order.service.LogisticsPostageService;
import com.d2c.order.service.LogisticsTemplateService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/logisticstemplete")
public class LogisticsTemplateCtrl extends BaseCtrl<LogisticsTemplateSearcher> {

    @Autowired
    private LogisticsTemplateService logisticsTemplateService;
    @Autowired
    private LogisticsPostageService logisticsPostageService;

    @Override
    protected Response doList(LogisticsTemplateSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<LogisticsTemplateDto> pager = logisticsTemplateService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(LogisticsTemplateSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(LogisticsTemplateSearcher searcher, PageModel page) {
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
    protected Response doHelp(LogisticsTemplateSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        LogisticsTemplateDto dto = logisticsTemplateService.findById(id);
        result.put("logisticsTemplate", dto);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        LogisticsTemplateDto template = JsonUtil.instance().toObject(data, LogisticsTemplateDto.class);
        int success = logisticsTemplateService.update(template);
        if (success < 1) {
            result.setMessage("更新不成功");
            result.setStatus(-1);
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        LogisticsTemplateDto dto = JsonUtil.instance().toObject(data, LogisticsTemplateDto.class);
        try {
            logisticsTemplateService.insert(dto);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(-1);
        }
        return result;
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

    @RequestMapping(value = "/update/postagesetting", method = RequestMethod.POST)
    public Response updatePostageSetting(LogisticsPostage logisticsPostage) {
        SuccessResponse result = new SuccessResponse();
        logisticsPostageService.update(logisticsPostage);
        return result;
    }

    @RequestMapping(value = "/delete/postagesetting/{id}", method = RequestMethod.POST)
    public Response delete(@PathVariable("id") Long id) {
        SuccessResponse result = new SuccessResponse();
        int success = logisticsPostageService.delete(id);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("删除不成功");
        }
        return result;
    }

    @RequestMapping(value = "/insert/postagesetting", method = RequestMethod.POST)
    public Response insertPostageSetting(LogisticsPostage logisticsPostage, Long templateId) {
        SuccessResponse result = new SuccessResponse();
        logisticsPostageService.insert(logisticsPostage, templateId);
        return result;
    }

    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Response updateStatus(Long id, Integer status) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = logisticsTemplateService.updateStatus(id, status, admin.getUsername());
        if (success < 1) {
            result.setMessage("操作不成功");
            result.setStatus(-1);
        }
        return result;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response mark(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            logisticsTemplateService.updateStatus(id, status, admin.getUsername());
        }
        return new SuccessResponse();
    }

}