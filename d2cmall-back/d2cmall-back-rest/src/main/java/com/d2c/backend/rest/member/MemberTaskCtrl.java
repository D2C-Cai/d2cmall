package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.MemberTask;
import com.d2c.member.query.MemberTaskSearcher;
import com.d2c.member.service.MemberTaskService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/task")
public class MemberTaskCtrl extends BaseCtrl<MemberTaskSearcher> {

    @Autowired
    private MemberTaskService memberTaskService;

    @Override
    protected Response doList(MemberTaskSearcher searcher, PageModel page) {
        PageResult<MemberTask> pager = memberTaskService.findBySearch(searcher, page);
        return ResultHandler.success(pager);
    }

    @Override
    protected int count(MemberTaskSearcher searcher) {
        return 0;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(MemberTaskSearcher searcher, PageModel page) {
        return null;
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
    protected Response doHelp(MemberTaskSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected Response findById(Long id) {
        MemberTask memberTask = memberTaskService.findById(id);
        return ResultHandler.successData(memberTask);
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        MemberTask tag = JsonUtil.instance().toObject(data, MemberTask.class);
        BeanUt.trimString(tag);
        tag.makeCode();
        AssertUt.notBlank(tag.getCode(), "任务编码不能为空");
        memberTaskService.update(tag);
        return ResultHandler.success();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        MemberTask tag = JsonUtil.instance().toObject(data, MemberTask.class);
        BeanUt.trimString(tag);
        AssertUt.notBlank(tag.getCode(), "任务编码不能为空");
        MemberTask old = memberTaskService.findByCode(tag.getCode());
        if (old != null) {
            throw new BusinessException("任务编码已存在，请检查...");
        }
        tag = memberTaskService.insert(tag);
        return ResultHandler.success(tag);
    }

    @Override
    protected Response doDelete(Long id) {
        MemberTask tag = memberTaskService.findById(id);
        if (tag.getFixed()) {
            return ResultHandler.error("固定标签不能删除。");
        }
        memberTaskService.delete(id);
        return ResultHandler.success();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return null;
    }

}
