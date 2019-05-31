package com.d2c.backend.rest.openapi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.openapi.api.entity.OpenUserDO;
import com.d2c.openapi.api.query.OpenUserQuery;
import com.d2c.openapi.api.services.OpenUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * OpenApi 开放平台
 *
 * @author wull
 */
@RestController
@RequestMapping("/rest/openapi/user")
public class OpenUserCtrl extends BaseCtrl<OpenUserQuery> {

    @Reference(timeout = 12000)
    private OpenUserService openUserService;

    /**
     * 用户注册
     */
    @Override
    protected Response doInsert(JSONObject data) {
        OpenUserDO bean = getBean(data, OpenUserDO.class);
        AssertUt.notNull(bean.getBrandId(), "品牌ID不能为空");
        bean = openUserService.register(bean);
        return ResultHandler.success(bean);
    }

    @Override
    protected Response doList(OpenUserQuery query, PageModel pager) {
        PageResult<OpenUserDO> res = openUserService.findPageResult(query, pager);
        return ResultHandler.success(res);
    }

    @Override
    protected Response findById(Long id) {
        OpenUserDO bean = openUserService.findOneById(id.intValue());
        return ResultHandler.success(bean);
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        OpenUserDO bean = getBean(data, OpenUserDO.class);
        openUserService.updateNotNull(bean);
        return ResultHandler.success(bean);
    }

    @Override
    protected Response doHelp(OpenUserQuery query, PageModel pager) {
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        openUserService.deleteById(id.intValue());
        return ResultHandler.success();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return null;
    }

    @Override
    protected int count(OpenUserQuery query) {
        return openUserService.count(query);
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
    protected List<Map<String, Object>> getRow(OpenUserQuery searcher, PageModel page) {
        return null;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

}
