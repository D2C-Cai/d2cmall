package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.MemberShareComment;
import com.d2c.member.query.MemberShareCommentSearcher;
import com.d2c.member.service.MemberShareCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/memberShareComment")
public class MemberShareCommentCtrl extends BaseCtrl<MemberShareCommentSearcher> {

    @Autowired
    private MemberShareCommentService memberShareCommentService;

    @Override
    protected Response doList(MemberShareCommentSearcher searcher, PageModel page) {
        searcher.setSortCreateDate("DESC");
        PageResult<MemberShareComment> pager = memberShareCommentService.findBySearcher(page, searcher);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(MemberShareCommentSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected List<Map<String, Object>> getRow(MemberShareCommentSearcher searcher, PageModel page) {
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
    protected Response doHelp(MemberShareCommentSearcher searcher, PageModel page) {
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
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
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
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public Response updateStatus(int verified, Long id, Long shareId, Long[] ids) {
        if (id != null) {
            memberShareCommentService.updateStatus(verified, id, shareId);
        }
        if (ids != null && ids.length > 0) {
            for (Long commentId : ids) {
                memberShareCommentService.updateStatus(verified, commentId, null);
            }
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/hot/{hot}", method = RequestMethod.POST)
    public Response updateHot(Long id, @PathVariable Integer hot) {
        SuccessResponse result = new SuccessResponse();
        memberShareCommentService.updateHot(id, hot);
        return result;
    }

}
