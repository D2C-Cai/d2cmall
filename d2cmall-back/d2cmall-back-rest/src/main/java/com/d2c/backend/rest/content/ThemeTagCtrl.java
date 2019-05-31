package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.ThemeTag;
import com.d2c.content.query.ThemeTagSearcher;
import com.d2c.content.service.ThemeTagService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/themetag")
public class ThemeTagCtrl extends BaseCtrl<ThemeTagSearcher> {

    @Autowired
    private ThemeTagService themeTagService;

    @Override
    protected Response doList(ThemeTagSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ThemeTag> pager = themeTagService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(ThemeTagSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(ThemeTagSearcher searcher, PageModel page) {
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
    protected Response doHelp(ThemeTagSearcher searcher, PageModel page) {
        PageResult<ThemeTag> pager = themeTagService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        ThemeTag themeTag = themeTagService.findById(id);
        result.put("themeTag", themeTag);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        ThemeTag themeTag = JsonUtil.instance().toObject(data, ThemeTag.class);
        SuccessResponse result = new SuccessResponse();
        themeTagService.update(themeTag);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        ThemeTag themeTag = JsonUtil.instance().toObject(data, ThemeTag.class);
        SuccessResponse result = new SuccessResponse();
        themeTag.setStatus(0);
        themeTag.setSort(0);
        themeTag = themeTagService.insert(themeTag);
        result.put("themeTag", themeTag);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        themeTagService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response updateMark(Long[] ids, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            themeTagService.updateStatus(id, status);
        }
        return result;
    }

    @RequestMapping(value = "/sort/{sort}", method = RequestMethod.POST)
    public Response updateSort(Long id, @PathVariable Integer sort) {
        SuccessResponse result = new SuccessResponse();
        themeTagService.updateSort(id, sort);
        return result;
    }

}
