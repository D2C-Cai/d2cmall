package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.MemberTag;
import com.d2c.member.query.MemberTagSearcher;
import com.d2c.member.service.MemberTagService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/tag4member")
public class MemberTagCtrl extends BaseCtrl<MemberTagSearcher> {

    @Autowired
    private MemberTagService memberTagService;

    @Override
    protected Response doList(MemberTagSearcher searcher, PageModel page) {
        PageResult<MemberTag> pager = memberTagService.findBySearch(page, searcher);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(MemberTagSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(MemberTagSearcher searcher, PageModel page) {
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
    protected Response doHelp(MemberTagSearcher searcher, PageModel page) {
        searcher.setStatus(1);
        PageResult<MemberTag> pager = memberTagService.findBySearch(page, searcher);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse response = new SuccessResponse();
        MemberTag memberTag = memberTagService.findById(id);
        response.put("tag4Member", memberTag);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse response = new SuccessResponse();
        MemberTag tag = JsonUtil.instance().toObject(data, MemberTag.class);
        BeanUt.trimString(tag);
        memberTagService.update(tag);
        return response;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        MemberTag tag = JsonUtil.instance().toObject(data, MemberTag.class);
        BeanUt.trimString(tag);
        tag = memberTagService.insert(tag);
        SuccessResponse response = new SuccessResponse();
        response.put("tag4Member", tag);
        return response;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse response = new SuccessResponse();
        MemberTag tag = memberTagService.findById(id);
        if (tag.getFixed() == 1) {
            return new ErrorResponse("固定标签不能删除。");
        }
        memberTagService.deleteById(id);
        return response;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long id, Integer status) {
        SuccessResponse response = new SuccessResponse();
        memberTagService.updateStatus(id, status);
        return response;
    }

    @RequestMapping(value = "/sort/{id}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable Long id, Integer sort) {
        SuccessResponse response = new SuccessResponse();
        memberTagService.updateSort(id, sort);
        return response;
    }

}
