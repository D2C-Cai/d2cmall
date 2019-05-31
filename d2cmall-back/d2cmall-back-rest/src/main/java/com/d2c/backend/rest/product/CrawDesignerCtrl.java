package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.Admin;
import com.d2c.product.model.CrawDesigner;
import com.d2c.product.query.CrawDesignerSearcher;
import com.d2c.product.service.CrawDesignerService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 维护抓取店铺
 *
 * @author Zephyr
 */
@RestController
@RequestMapping("/rest/product/crawdesigner")
public class CrawDesignerCtrl extends BaseCtrl<CrawDesignerSearcher> {

    @Autowired
    private CrawDesignerService crawDesignerService;

    @Override
    protected Response doInsert(JSONObject data) {
        CrawDesigner crawDesigner = JsonUtil.instance().toObject(data, CrawDesigner.class);
        Admin admin = this.getLoginedAdmin();
        crawDesigner.setCreator(admin.getName());
        crawDesigner = crawDesignerService.insert(crawDesigner);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.put("crawDesigner", crawDesigner);
        return successResponse;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse successResponse = new SuccessResponse();
        CrawDesigner crawDesigner = crawDesignerService.findById(id);
        successResponse.put("crawDesigner", crawDesigner);
        return successResponse;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(CrawDesignerSearcher searcher, PageModel page) {
        PageResult<CrawDesigner> pageResult = crawDesignerService.findBySearch(searcher, page);
        SuccessResponse successResponse = new SuccessResponse(pageResult);
        return successResponse;
    }

    @Override
    protected int count(CrawDesignerSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(CrawDesignerSearcher searcher, PageModel page) {
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
    protected Response doHelp(CrawDesignerSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
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

    @RequestMapping(value = "/do/craw", method = RequestMethod.GET)
    public SuccessResponse doCraw(Long[] ids) {
        SuccessResponse successResponse = new SuccessResponse();
        crawDesignerService.doCrawByIds(ids);
        return successResponse;
    }

}