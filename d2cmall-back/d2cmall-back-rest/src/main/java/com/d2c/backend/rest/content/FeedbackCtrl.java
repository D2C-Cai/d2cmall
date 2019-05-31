package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.dto.FeedBackDto;
import com.d2c.content.query.FeedBackSearcher;
import com.d2c.content.service.FeedBackService;
import com.d2c.member.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/crm/feedback")
public class FeedbackCtrl extends BaseCtrl<FeedBackSearcher> {

    @Autowired
    private FeedBackService feedBackService;

    @Override
    protected List<Map<String, Object>> getRow(FeedBackSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(FeedBackSearcher searcher) {
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
    protected Response doHelp(FeedBackSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(FeedBackSearcher searcher, PageModel page) {
        PageResult<FeedBackDto> pager = feedBackService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        feedBackService.deleteByIds(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        feedBackService.deleteByIds(new Long[]{id});
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/reply/{id}", method = RequestMethod.POST)
    public Response reply(@PathVariable Long id, String reply) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        feedBackService.doReply(id, reply, admin.getName());
        return result;
    }

    @RequestMapping(value = "/deal/{id}", method = RequestMethod.POST)
    public Response deal(@PathVariable Long id, Integer status, String meno) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        feedBackService.updateStatus(id, status, meno);
        return result;
    }

    @RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
    public Response close(@PathVariable Long id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        feedBackService.doClose(id);
        return result;
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public Response version() throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        List<String> versions = feedBackService.findVersions();
        result.put("versions", versions);
        return result;
    }

    @RequestMapping(value = "/reopen/{id}", method = RequestMethod.POST)
    public Response reOpen(@PathVariable Long id, String meno) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        feedBackService.updateStatus(id, 2, meno);
        return result;
    }

}
