package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.Admin;
import com.d2c.member.model.WardrobeCollocation;
import com.d2c.member.query.WardrobeCollocationSearcher;
import com.d2c.member.service.WardrobeCollocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/wardrobecollocation")
public class WardrobeCollocationCtrl extends BaseCtrl<WardrobeCollocationSearcher> {

    @Autowired
    private WardrobeCollocationService wardrobeCollocationService;

    @Override
    protected Response doList(WardrobeCollocationSearcher query, PageModel page) {
        PageResult<WardrobeCollocation> pager = wardrobeCollocationService.findBySearcher(query, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        wardrobeCollocationService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            wardrobeCollocationService.delete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doHelp(WardrobeCollocationSearcher query, PageModel pager) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(WardrobeCollocationSearcher query) {
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
    protected List<Map<String, Object>> getRow(WardrobeCollocationSearcher query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public Response doVerify(Long id) {
        Admin admin = this.getLoginedAdmin();
        wardrobeCollocationService.doVerify(id, admin.getUsername());
        return new SuccessResponse();
    }

    @RequestMapping(value = "/cancelverify", method = RequestMethod.POST)
    public Response doCancelVerify(Long id) {
        Admin admin = this.getLoginedAdmin();
        wardrobeCollocationService.doCancelVerify(id, admin.getUsername());
        return new SuccessResponse();
    }

    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public Response doRefuse(Long id) {
        Admin admin = this.getLoginedAdmin();
        wardrobeCollocationService.doRefuse(id, admin.getUsername());
        return new SuccessResponse();
    }

}
