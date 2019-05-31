package com.d2c.backend.rest.logger;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.PopMessage;
import com.d2c.logger.query.PopMessageSearcher;
import com.d2c.logger.service.PopMessageService;
import com.d2c.member.model.Admin;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/msg/popmessage")
public class PopMessageCtrl extends BaseCtrl<PopMessageSearcher> {

    @Autowired
    private PopMessageService popMessageService;

    @Override
    protected Response doList(PopMessageSearcher query, PageModel page) {
        BeanUt.trimString(query);
        PageResult<PopMessage> pager = popMessageService.findBySearcher(query, page);
        SuccessResponse response = new SuccessResponse(pager);
        return response;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        BeanUt.trimString(data);
        PopMessage popMessage = JsonUtil.instance().toObject(data, PopMessage.class);
        Admin admin = getLoginedAdmin();
        popMessage.setCreator(admin.getName());
        popMessage = popMessageService.insert(popMessage);
        SuccessResponse response = new SuccessResponse();
        response.put("popMessage", popMessage);
        return response;
    }

    @Override
    protected Response findById(Long id) {
        PopMessage popMessage = popMessageService.findById(id);
        SuccessResponse response = new SuccessResponse();
        response.put("popMessage", popMessage);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        BeanUt.trimString(data);
        PopMessage popMessage = JsonUtil.instance().toObject(data, PopMessage.class);
        popMessageService.update(popMessage);
        return new SuccessResponse();
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

    @Override
    protected Response doHelp(PopMessageSearcher query, PageModel pager) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(PopMessageSearcher query) {
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
    protected List<Map<String, Object>> getRow(PopMessageSearcher query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/push/{id}", method = RequestMethod.POST)
    public Response doPush(@PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        PopMessage popMessage = popMessageService.findById(id);
        if (popMessage.getStatus() == 1) {
            result.setStatus(-1);
            result.setMessage("该消息已经发送");
            return result;
        }
        popMessageService.doPush(id, admin.getUsername());
        return new SuccessResponse();
    }

}
