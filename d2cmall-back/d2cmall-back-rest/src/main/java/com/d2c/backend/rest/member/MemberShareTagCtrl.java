package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.MemberShareTag;
import com.d2c.member.query.MemberShareTagSearcher;
import com.d2c.member.service.MemberShareTagService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/tag4membershare")
public class MemberShareTagCtrl extends BaseCtrl<MemberShareTagSearcher> {

    @Autowired
    private MemberShareTagService memberShareTagService;

    @Override
    protected List<Map<String, Object>> getRow(MemberShareTagSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(MemberShareTagSearcher searcher) {
        return 0;
    }

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        return null;
    }

    @Override
    protected Response doHelp(MemberShareTagSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MemberShareTag> dtos = memberShareTagService.findBySearch(searcher, page);
        return new SuccessResponse(dtos);
    }

    @Override
    protected Response doList(MemberShareTagSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MemberShareTag> pager = memberShareTagService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        MemberShareTag tag = memberShareTagService.findById(id);
        SuccessResponse response = new SuccessResponse();
        response.put("tag4MemberShare", tag);
        return response;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        for (Long id : ids) {
            if (id == null) {
                continue;
            }
            memberShareTagService.delete(id);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        memberShareTagService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        MemberShareTag tag = JsonUtil.instance().toObject(data, MemberShareTag.class);
        BeanUt.trimString(tag);
        tag = memberShareTagService.insert(tag);
        SuccessResponse response = new SuccessResponse();
        response.put("tag4MemberShare", tag);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        MemberShareTag tag = JsonUtil.instance().toObject(data, MemberShareTag.class);
        BeanUt.trimString(tag);
        memberShareTagService.update(tag);
        return new SuccessResponse();
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    /**
     * 关联标签
     *
     * @param id
     * @param ids
     * @param model
     * @return
     */
    @RequestMapping(value = "/relationTags", method = RequestMethod.POST)
    public Response relationTags(Long id, Long[] ids) {
        boolean singleFlag = true;
        String shareId = null;
        List<MemberShareTag> usedTags = new ArrayList<MemberShareTag>();
        SuccessResponse succ = new SuccessResponse();
        if (id == null) {
            StringBuilder sb = new StringBuilder();
            for (Long i : ids) {
                sb.append(i + ",");
            }
            shareId = sb.toString();
            singleFlag = false;
        } else {
            shareId = String.valueOf(id);
            usedTags = memberShareTagService.findByMemberShareId(id);
        }
        List<MemberShareTag> tags = memberShareTagService.findAll();
        succ.put("singleFlag", singleFlag);
        succ.put("usedTags", usedTags);
        succ.put("tags", tags);
        succ.put("shareId", shareId);
        return succ;
    }

    @RequestMapping(value = "/updateStatus/{id}/{status}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        memberShareTagService.updateStatus(id, status);
        return result;
    }

    @RequestMapping(value = "/updateSort/{id}/{sort}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable(value = "id") Long id, @PathVariable(value = "sort") Integer sort) {
        memberShareTagService.updateSort(id, sort);
        return new SuccessResponse();
    }

}
