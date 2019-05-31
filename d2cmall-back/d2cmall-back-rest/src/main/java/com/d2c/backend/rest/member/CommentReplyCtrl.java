package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.Admin;
import com.d2c.member.model.CommentReply;
import com.d2c.member.query.CommentSearcher;
import com.d2c.member.service.CommentReplyService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/commentreply")
public class CommentReplyCtrl extends BaseCtrl<CommentSearcher> {

    @Autowired
    private CommentReplyService commentReplyService;

    @Override
    protected List<Map<String, Object>> getRow(CommentSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(CommentSearcher searcher) {
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
    protected Response doHelp(CommentSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(CommentSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        for (Long id : ids) {
            this.doDelete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        commentReplyService.deleteById(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        CommentReply commentReply = (CommentReply) JsonUtil.instance().toObject(data, CommentReply.class);
        Admin admin = this.getLoginedAdmin();
        commentReply.setLastModifyMan(admin.getUsername());
        commentReply.setType(CommentReply.ReplyType.SYSTEM.name());
        try {
            commentReply = commentReplyService.insert(commentReply);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(-1);
        }
        result.put("commentReply", commentReply);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        CommentReply commentReply = (CommentReply) JsonUtil.instance().toObject(data, CommentReply.class);
        Admin admin = this.getLoginedAdmin();
        commentReply.setLastModifyMan(admin.getUsername());
        commentReply.setId(id);
        try {
            commentReplyService.update(commentReply);
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
        result.setMessage("修改保存成功！");
        result.put("commentReply", commentReply);
        return result;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/batchsave", method = RequestMethod.POST)
    public Response save(String commentId, String content) throws Exception {
        Response response = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        String[] ids = commentId.split(",");
        for (int i = 0; i < ids.length; i++) {
            Long id = Long.parseLong(ids[i]);
            CommentReply commentReply = new CommentReply();
            commentReply.setContent(content);
            commentReply.setCommentId(id);
            commentReply.setReplyId(new Long(0));
            commentReply.setLastModifyMan(admin.getUsername());
            commentReply.setType(CommentReply.ReplyType.SYSTEM.name());
            commentReplyService.insert(commentReply);
        }
        return response;
    }

    @RequestMapping(value = "/audit/{commentReplyId}", method = RequestMethod.GET)
    public Response audit(@PathVariable Long commentReplyId) {
        commentReplyService.doAudit(commentReplyId);
        return new SuccessResponse();
    }

}
