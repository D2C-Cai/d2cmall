package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.SplashScreen;
import com.d2c.content.query.SplashScreenSearcher;
import com.d2c.content.service.SplashScreenService;
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
@RequestMapping("/rest/cms/splashscreen")
public class SplashScreenCtrl extends BaseCtrl<SplashScreenSearcher> {

    @Autowired
    private SplashScreenService splashScreenService;

    @Override
    protected Response doList(SplashScreenSearcher searcher, PageModel page) {
        PageResult<SplashScreen> pager = splashScreenService.findBySearcher(page, searcher);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(SplashScreenSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(SplashScreenSearcher searcher, PageModel page) {
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
    protected Response doHelp(SplashScreenSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        SplashScreen splashScreen = splashScreenService.findById(id);
        result.put("item", splashScreen);
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        SplashScreen splashScreen = (SplashScreen) JsonUtil.instance().toObject(data, SplashScreen.class);
        if (StringUtils.isBlank(splashScreen.getPic320480()) || StringUtils.isBlank(splashScreen.getPic320568())
                || StringUtils.isBlank(splashScreen.getPic375667())
                || StringUtils.isBlank(splashScreen.getPic414736())) {
            result.setStatus(-1);
            result.setMessage("每个尺寸的图片需至少传1张。");
            return result;
        }
        splashScreen.setLastModifyMan(this.getLoginedAdmin().getName());
        splashScreenService.update(splashScreen);
        result.put("item", splashScreen);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        SplashScreen splashScreen = (SplashScreen) JsonUtil.instance().toObject(data, SplashScreen.class);
        if (StringUtils.isBlank(splashScreen.getPic320480()) || StringUtils.isBlank(splashScreen.getPic320568())
                || StringUtils.isBlank(splashScreen.getPic375667())
                || StringUtils.isBlank(splashScreen.getPic414736())) {
            result.setStatus(-1);
            result.setMessage("每个尺寸的图片需至少传1张。");
            return result;
        }
        splashScreen.setLastModifyMan(this.getLoginedAdmin().getName());
        splashScreen = splashScreenService.insert(splashScreen);
        result.put("item", splashScreen);
        return result;
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

    @RequestMapping(value = "/updatestatus/{id}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long id, Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        splashScreenService.updateStatus(id, status, this.getLoginedAdmin().getName());
        return result;
    }

}
