package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.product.model.CrawUrl;
import com.d2c.product.query.CrawUrlSearcher;
import com.d2c.product.service.CrawUrlService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 添加抓取店铺的路径
 *
 * @author Zephyr
 */
@RestController
@RequestMapping("/rest/product/crawurl")
public class CrawUrlCtrl extends BaseCtrl<CrawUrlSearcher> {

    @Autowired
    private CrawUrlService crawUrlService;

    @Override
    protected Response doInsert(JSONObject data) {
        CrawUrl crawUrl = JsonUtil.instance().toObject(data, CrawUrl.class);
        crawUrl = crawUrlService.insert(crawUrl);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.put("crawUrl", crawUrl);
        return successResponse;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse successResponse = new SuccessResponse();
        CrawUrl crawUrl = crawUrlService.findById(id);
        successResponse.put("crawUrl", crawUrl);
        return successResponse;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(CrawUrlSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(CrawUrlSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(CrawUrlSearcher searcher, PageModel page) {
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
    protected Response doHelp(CrawUrlSearcher searcher, PageModel page) {
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

}