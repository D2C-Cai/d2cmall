package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.AppMenu;
import com.d2c.content.query.AppMenuSearcher;
import com.d2c.content.service.AppMenuService;
import com.d2c.member.model.Admin;
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
@RequestMapping("/rest/cms/appmenudef")
public class AppMenuCtrl extends BaseCtrl<AppMenuSearcher> {

    @Autowired
    private AppMenuService appMenuService;

    @Override
    protected Response doList(AppMenuSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AppMenu> pager = appMenuService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("search", searcher);
        return result;
    }

    @Override
    protected int count(AppMenuSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(AppMenuSearcher searcher, PageModel page) {
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
    protected Response doHelp(AppMenuSearcher searcher, PageModel page) {
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
        SuccessResponse result = new SuccessResponse();
        AppMenu appMenu = (AppMenu) JsonUtil.instance().toObject(data, AppMenu.class);
        int success = appMenuService.update(appMenu);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作失败");
        }
        result.put("appMenuDef", appMenu);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AppMenu appMenu = (AppMenu) JsonUtil.instance().toObject(data, AppMenu.class);
        if (appMenu != null) {
            if (StringUtils.isBlank(appMenu.getName()) || StringUtils.isBlank(appMenu.getIconUrl())
                    || StringUtils.isBlank(appMenu.getVersion())) {
                result.setStatus(-1);
                result.setMessage("操作名或图标url或APP版本号不能为空");
                return result;
            }
        }
        appMenu = appMenuService.insert(appMenu);
        result.put("appMenuDef", appMenu);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        int success = appMenuService.delete(id);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作失败");
        }
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response mark(@PathVariable Integer status, Long id) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = appMenuService.updateStatusById(id, status, admin.getUsername());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作失败");
        }
        return result;
    }

    @RequestMapping(value = "/sort/{sort}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable Integer sort, Long id) {
        SuccessResponse result = new SuccessResponse();
        int success = appMenuService.updateSortById(id, sort);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作失败");
        }
        return result;
    }

}
