package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.Admin;
import com.d2c.member.model.Wardrobe;
import com.d2c.member.query.WardrobeSearcher;
import com.d2c.member.service.WardrobeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/wardrobe")
public class WardrobeCtrl extends BaseCtrl<WardrobeSearcher> {

    @Autowired
    private WardrobeService wardrobeItemService;

    @Override
    protected Response doList(WardrobeSearcher query, PageModel page) {
        PageResult<Wardrobe> pager = wardrobeItemService.findBySearcher(query, page);
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
        wardrobeItemService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(WardrobeSearcher query, PageModel pager) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(WardrobeSearcher query) {
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
    protected List<Map<String, Object>> getRow(WardrobeSearcher query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

}
