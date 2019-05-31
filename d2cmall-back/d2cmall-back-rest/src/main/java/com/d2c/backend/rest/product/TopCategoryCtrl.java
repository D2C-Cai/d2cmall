package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.model.TopCategory;
import com.d2c.product.query.TopCategorySearcher;
import com.d2c.product.service.TopCategoryService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/topcategory")
public class TopCategoryCtrl extends BaseCtrl<TopCategorySearcher> {

    @Autowired
    private TopCategoryService topCategoryService;

    @Override
    protected List<Map<String, Object>> getRow(TopCategorySearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(TopCategorySearcher searcher) {
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
    protected Response doHelp(TopCategorySearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<TopCategory> pager = topCategoryService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response doList(TopCategorySearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<TopCategory> pager = topCategoryService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        TopCategory topCategory = topCategoryService.findById(id);
        result.put("topCategory", topCategory);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        TopCategory topCategory = (TopCategory) JsonUtil.instance().toObject(data, TopCategory.class);
        TopCategory tc = topCategoryService.findByCode(topCategory.getCode());
        if (tc != null) {
            result.setStatus(-1);
            result.setMessage("已经存在相同的编码了，请重新输入！");
            return result;
        }
        topCategory = topCategoryService.insert(topCategory);
        result.put("topCategory", topCategory);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        TopCategory topCategory = (TopCategory) JsonUtil.instance().toObject(data, TopCategory.class);
        TopCategory tc = topCategoryService.findByCode(topCategory.getCode());
        if (tc != null && !tc.getId().equals(topCategory.getId())) {
            result.setStatus(-1);
            result.setMessage("已经存在相同的编码了，请重新输入！");
            return result;
        }
        topCategoryService.update(topCategory);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Response status(@PathVariable Integer status, Long[] ids) {
        for (Long id : ids) {
            topCategoryService.updateStatus(id, status);
        }
        return new SuccessResponse();
    }

}
