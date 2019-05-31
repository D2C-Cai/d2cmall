package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.content.dto.VoteDefDto;
import com.d2c.content.model.VoteDef;
import com.d2c.content.query.VoteDefSearcher;
import com.d2c.content.service.VoteDefService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/votedef")
public class VoteDefCtrl extends BaseCtrl<VoteDefSearcher> {

    @Autowired
    private VoteDefService VoteDefService;

    @Override
    protected Response doList(VoteDefSearcher searcher, PageModel page) {
        PageResult<VoteDef> pager = VoteDefService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(VoteDefSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(VoteDefSearcher searcher, PageModel page) {
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
    protected Response doHelp(VoteDefSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        VoteDefDto voteDef = null;
        voteDef = VoteDefService.findDtoById(id);
        result.put("voteDef", voteDef);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        VoteDef voteDef = (VoteDef) JsonUtil.instance().toObject(data, VoteDef.class);
        VoteDefService.update(voteDef);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        VoteDef voteDef = (VoteDef) JsonUtil.instance().toObject(data, VoteDef.class);
        voteDef.setStatus(1);
        voteDef = VoteDefService.insert(voteDef);
        result.put("voteDef", voteDef);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        VoteDefService.updateStatus(id, -1);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            VoteDefService.updateStatus(id, -1);
        }
        return result;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response updateMark(Long[] ids, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            VoteDefService.updateStatus(id, status);
        }
        return result;
    }

}
