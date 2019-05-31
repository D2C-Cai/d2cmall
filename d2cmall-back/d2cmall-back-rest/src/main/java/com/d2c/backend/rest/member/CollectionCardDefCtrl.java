package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.Admin;
import com.d2c.member.model.CollectionCardDef;
import com.d2c.member.query.CollectionCardDefSearcher;
import com.d2c.member.service.CollectionCardDefService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/collectioncarddef")
public class CollectionCardDefCtrl extends BaseCtrl<CollectionCardDefSearcher> {

    private final Long promotionId = 2L;
    private final String promotionName = "19年520";
    @Autowired
    private CollectionCardDefService collectionCardDefService;

    @Override
    protected Response doList(CollectionCardDefSearcher query, PageModel page) {
        PageResult<CollectionCardDef> pager = collectionCardDefService.findBySearcher(query, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        CollectionCardDef collectionCardDef = JsonUtil.instance().toObject(data, CollectionCardDef.class);
        collectionCardDef.setPromotionId(promotionId);
        collectionCardDef.setPromotionName(promotionName);
        collectionCardDef.setCreator(admin.getUsername());
        collectionCardDef = collectionCardDefService.insert(collectionCardDef);
        result.put("collectionCardDef", collectionCardDef);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        CollectionCardDef collectionCardDef = collectionCardDefService.findById(id);
        result.put("collectionCardDef", collectionCardDef);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        CollectionCardDef collectionCardDef = JsonUtil.instance().toObject(data, CollectionCardDef.class);
        collectionCardDefService.update(collectionCardDef);
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

    @Override
    protected Response doHelp(CollectionCardDefSearcher query, PageModel page) {
        PageResult<CollectionCardDef> pager = collectionCardDefService.findBySearcher(query, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(CollectionCardDefSearcher query) {
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
    protected List<Map<String, Object>> getRow(CollectionCardDefSearcher query, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public Response updateStatus(Integer status, Long id) {
        collectionCardDefService.updateStatus(id, status);
        return new SuccessResponse();
    }

    @RequestMapping(value = "/update/sort", method = RequestMethod.POST)
    public Response updateSort(Integer sort, Long id) {
        collectionCardDefService.updateSort(id, sort);
        return new SuccessResponse();
    }

}
