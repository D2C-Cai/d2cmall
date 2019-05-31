package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.MemberLevel;
import com.d2c.member.query.MemberLevelSearcher;
import com.d2c.member.service.MemberLevelService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/memberLevel")
public class MemberLevelCtrl extends BaseCtrl<MemberLevelSearcher> {

    @Autowired
    public MemberLevelService memberLevelService;

    @Override
    protected Response doList(MemberLevelSearcher searcher, PageModel page) {
        PageResult<MemberLevel> pager = memberLevelService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(MemberLevelSearcher searcher) {
        int count = memberLevelService.countBySearch(searcher);
        return count;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(MemberLevelSearcher searcher, PageModel page) {
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
    protected Response doHelp(MemberLevelSearcher searcher, PageModel page) {
        PageResult<MemberLevel> pager = memberLevelService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse response = new SuccessResponse();
        MemberLevel memberLevel = memberLevelService.findById(id);
        response.put("memberLevel", memberLevel);
        return response;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse response = new SuccessResponse();
        MemberLevel memberLevel = JsonUtil.instance().toObject(data, MemberLevel.class);
        BeanUt.trimString(memberLevel);
        MemberLevel old = memberLevelService.findByLevel(memberLevel.getLevel());
        if (old.getId() != memberLevel.getId()) {
            response.setStatus(-1);
            response.setMessage("保存不成功！等级级别不能重复！");
            return response;
        }
        memberLevelService.update(memberLevel);
        return response;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse response = new SuccessResponse();
        MemberLevel memberLevel = JsonUtil.instance().toObject(data, MemberLevel.class);
        BeanUt.trimString(memberLevel);
        MemberLevel old = memberLevelService.findByLevel(memberLevel.getLevel());
        if (old != null) {
            response.setStatus(-1);
            response.setMessage("保存不成功！等级级别不能重复！");
            return response;
        }
        memberLevel = memberLevelService.insert(memberLevel);
        response.put("memberLevel", memberLevel);
        return response;
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

}
