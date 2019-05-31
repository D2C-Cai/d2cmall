package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.dto.ShareTaskDto;
import com.d2c.content.query.ShareTaskSearcher;
import com.d2c.content.service.ShareTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/sharetask")
public class ShareTaskCtrl extends BaseCtrl<ShareTaskSearcher> {

    @Autowired
    private ShareTaskService shareTaskService;

    @Override
    protected List<Map<String, Object>> getRow(ShareTaskSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(ShareTaskSearcher searcher) {
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
    protected Response doHelp(ShareTaskSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ShareTaskDto> pager = shareTaskService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doList(ShareTaskSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ShareTaskDto> pager = shareTaskService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        return null;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

}
