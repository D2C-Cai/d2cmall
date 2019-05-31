package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.AppVersion;
import com.d2c.content.query.AppVersionSearcher;
import com.d2c.content.service.AppVersionService;
import com.d2c.member.model.Admin;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/security/appversion")
public class AppVersionCtrl extends BaseCtrl<AppVersionSearcher> {

    @Autowired
    private AppVersionService appVersionService;

    @Override
    protected Response doList(AppVersionSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AppVersion> pager = appVersionService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(AppVersionSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(AppVersionSearcher searcher, PageModel page) {
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
    protected Response doHelp(AppVersionSearcher searcher, PageModel page) {
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
        Admin admin = this.getLoginedAdmin();
        AppVersion appVersion = JsonUtil.instance().toObject(data, AppVersion.class);
        appVersion.setLastModifyMan(admin.getUsername());
        int success = appVersionService.update(appVersion);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功！");
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        AppVersion appVersion = JsonUtil.instance().toObject(data, AppVersion.class);
        appVersion.setCreator(admin.getUsername());
        // 是否存在相同版本的
        AppVersion tempAppVersion = appVersionService.findSameVersion(appVersion);
        if (tempAppVersion == null) {
            // 安卓由4位改为3位
            Long digit = null;
            if (!StringUtils.isEmpty(appVersion.getVersion())) {
                digit = Long.parseLong(appVersion.getVersion().replace(".", ""));
            }
            // 默认先3位
            if (digit < 1000 && "APPANDROID".equalsIgnoreCase(appVersion.getDevice())) {
                digit = digit * 10;
            }
            appVersion.setDigit(digit);
            appVersion = appVersionService.insert(appVersion);
            result.put("appVersion", appVersion);
        } else {
            result.setStatus(-1);
            result.setMessage("存在相同的版本，请先删除！");
        }
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        AppVersion tempAppVersion = appVersionService.findById(id);
        int success = 0;
        if (tempAppVersion != null) {
            success = appVersionService.deleteById(id, admin.getUsername());
        }
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作失败！");
        }
        result.setMessage("操作成功！");
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/update/force/{id}", method = RequestMethod.POST)
    public Response updateForce(@PathVariable Long id, Integer force) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        int success = appVersionService.updateForce(id, force, this.getLoginedAdmin().getUsername());
        if (success <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

}
