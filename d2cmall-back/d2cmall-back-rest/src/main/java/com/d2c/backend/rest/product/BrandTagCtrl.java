package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.product.model.BrandTag;
import com.d2c.product.query.BrandTagSearcher;
import com.d2c.product.service.BrandTagService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/shop/tag4designer")
public class BrandTagCtrl extends BaseCtrl<BrandTagSearcher> {

    @Autowired
    private BrandTagService brandTagService;

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
    protected Response doList(BrandTagSearcher searcher, PageModel page) {
        PageResult<BrandTag> pager = brandTagService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        BrandTag tag = brandTagService.findById(id);
        result.put("tag4Designer", tag);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        for (Long i : id) {
            brandTagService.delete(i);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        brandTagService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        BrandTag tag = JsonUtil.instance().toObject(data, BrandTag.class);
        tag = brandTagService.insert(tag);
        result.put("tag4designer", tag);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        BrandTag tag = JsonUtil.instance().toObject(data, BrandTag.class);
        brandTagService.update(tag);
        return new SuccessResponse();
    }

    @Override
    protected List<Map<String, Object>> getRow(BrandTagSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(BrandTagSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected Response doHelp(BrandTagSearcher searcher, PageModel page) {
        PageResult<HelpDTO> pager = brandTagService.findBySearchForHelp(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/sort/{id}/{sort}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable Long id, @PathVariable Integer sort) {
        SuccessResponse result = new SuccessResponse();
        brandTagService.updateSort(id, sort);
        return result;
    }

    @RequestMapping(value = "/status/{id}/{status}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        brandTagService.updateStatus(id, status);
        return result;
    }

    @RequestMapping(value = "/relation", method = RequestMethod.POST)
    public Response relation(Long[] designerIds, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        List<BrandTag> usedTags = new ArrayList<BrandTag>();
        if (designerIds != null && 1 == designerIds.length) {
            usedTags = brandTagService.findByDesignerId(designerIds[0]);
        }
        PageResult<BrandTag> pager = brandTagService.findBySearch(new BrandTagSearcher(), page);
        result.put("tagList", pager.getList());
        result.put("usedTags", usedTags);
        return result;
    }

}
