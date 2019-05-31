package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.VoteSelection;
import com.d2c.content.query.VoteSelectionSearcher;
import com.d2c.content.service.VoteSelectionService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/voteselection")
public class VoteSelectionCtrl extends BaseCtrl<VoteSelectionSearcher> {

    @Autowired
    private VoteSelectionService voteSelectionService;

    @Override
    protected Response doList(VoteSelectionSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<VoteSelection> pager = voteSelectionService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(VoteSelectionSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(VoteSelectionSearcher searcher, PageModel page) {
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
    protected Response doHelp(VoteSelectionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        VoteSelection voteSelection = voteSelectionService.findById(id);
        result.put("voteSelection", voteSelection);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        VoteSelection voteSelection = (VoteSelection) JsonUtil.instance().toObject(data, VoteSelection.class);
        voteSelectionService.update(voteSelection);
        result.put("voteSelection", voteSelection);
        result.setMessage("修改成功");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        VoteSelection voteSelection = (VoteSelection) JsonUtil.instance().toObject(data, VoteSelection.class);
        voteSelection.setStatus(1);
        voteSelection = voteSelectionService.insert(voteSelection);
        result.put("voteSelection", voteSelection);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        voteSelectionService.delete(id);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            voteSelectionService.delete(id);
        }
        return result;
    }

}
