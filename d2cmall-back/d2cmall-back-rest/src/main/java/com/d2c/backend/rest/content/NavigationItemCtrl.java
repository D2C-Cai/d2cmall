package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.content.dto.NavigationItemDto;
import com.d2c.content.model.NavigationItem;
import com.d2c.content.query.NavigationItemSearcher;
import com.d2c.content.service.NavigationItemService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/navigationitem")
public class NavigationItemCtrl extends BaseCtrl<NavigationItemSearcher> {

    @Autowired
    private NavigationItemService navigationItemService;

    @Override
    protected List<Map<String, Object>> getRow(NavigationItemSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(NavigationItemSearcher searcher) {
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
    protected Response doHelp(NavigationItemSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(NavigationItemSearcher searcher, PageModel page) {
        PageResult<NavigationItemDto> pager = navigationItemService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            this.doDelete(id);
        }
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        navigationItemService.delete(id);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        NavigationItem navigationItem = JsonUtil.instance().toObject(data, NavigationItem.class);
        navigationItem = navigationItemService.insert(navigationItem);
        result.put("navigationItem", navigationItem);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        NavigationItem navigationItem = JsonUtil.instance().toObject(data, NavigationItem.class);
        navigationItemService.update(navigationItem);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

}
