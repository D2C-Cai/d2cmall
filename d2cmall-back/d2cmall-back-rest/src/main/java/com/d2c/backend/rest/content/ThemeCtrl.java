package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.dto.ThemeDto;
import com.d2c.content.model.Theme;
import com.d2c.content.model.Theme.ThemeType;
import com.d2c.content.query.ThemeSearcher;
import com.d2c.content.service.ThemeService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/theme")
public class ThemeCtrl extends BaseCtrl<ThemeSearcher> {

    @Autowired
    private ThemeService themeService;

    @Override
    protected Response doList(ThemeSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<ThemeDto> pager = themeService.findDtoBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(ThemeSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(ThemeSearcher searcher, PageModel page) {
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
    protected Response doHelp(ThemeSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Theme theme = themeService.findById(id);
        result.put("theme", theme);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Theme theme = JsonUtil.instance().toObject(data, Theme.class);
        SuccessResponse result = new SuccessResponse();
        themeService.update(theme);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Theme theme = JsonUtil.instance().toObject(data, Theme.class);
        SuccessResponse result = new SuccessResponse();
        theme.setStatus(0);
        if (theme.getSort() == null) {
            theme.setSort(0);
        }
        if (StringUtils.isBlank(theme.getType())) {
            theme.setType(ThemeType.WECHAT.name());
        }
        theme = themeService.insert(theme);
        result.put("theme", theme);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        themeService.delete(id);
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
            themeService.updateStatus(id, status);
        }
        return result;
    }

    @RequestMapping(value = "/sort/{sort}", method = RequestMethod.POST)
    public Response updateSort(Long id, @PathVariable Integer sort) {
        SuccessResponse result = new SuccessResponse();
        themeService.updateSort(id, sort);
        return result;
    }

    @RequestMapping(value = "/recommend/{recommend}", method = RequestMethod.POST)
    public Response updateMark(Long id, @PathVariable Integer recommend) {
        SuccessResponse result = new SuccessResponse();
        themeService.updateRecommend(id, recommend);
        return result;
    }

}
