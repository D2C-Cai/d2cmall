package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.product.model.PromotionTag;
import com.d2c.product.query.PromotionTagSearcher;
import com.d2c.product.service.PromotionTagService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/tag4Promotion")
public class PromotionTagCtrl extends BaseCtrl<PromotionTagSearcher> {

    @Autowired
    private PromotionTagService promotionTagService;

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        PromotionTag promotionTag = promotionTagService.findById(id);
        result.put("tag4promotion", promotionTag);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        promotionTagService.delete(id);
        result.setMessage("删除成功");
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        SuccessResponse result = new SuccessResponse();
        for (Long i : ids) {
            promotionTagService.delete(i);
        }
        result.setMessage("删除成功");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        PromotionTag promotionTag = JsonUtil.instance().toObject(data, PromotionTag.class);
        promotionTag = promotionTagService.insert(promotionTag);
        result.put("tag4promotion", promotionTag);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        PromotionTag promotionTag = JsonUtil.instance().toObject(data, PromotionTag.class);
        SuccessResponse result = new SuccessResponse();
        promotionTagService.update(promotionTag);
        return result;
    }

    @Override
    protected Response doList(PromotionTagSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<PromotionTag> pager = promotionTagService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(PromotionTagSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected List<Map<String, Object>> getRow(PromotionTagSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(PromotionTagSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/sort/{id}", method = RequestMethod.POST)
    public Response updateSort(@PathVariable Long id, Date upDateTime) {
        SuccessResponse result = new SuccessResponse();
        promotionTagService.updateSort(id, upDateTime);
        return result;
    }

    @RequestMapping(value = "/status/{id}/{status}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        promotionTagService.updateStatus(id, status);
        return result;
    }

}
