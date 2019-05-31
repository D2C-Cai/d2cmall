package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.product.model.AwardSession;
import com.d2c.product.query.AwardSessionSearcher;
import com.d2c.product.service.AwardSessionService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/awardsession")
public class AwardSessionCtrl extends BaseCtrl<AwardSessionSearcher> {

    @Autowired
    private AwardSessionService awardSessionService;

    @Override
    protected Response doList(AwardSessionSearcher searcher, PageModel page) {
        PageResult<AwardSession> pager = awardSessionService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(AwardSessionSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(AwardSessionSearcher searcher, PageModel page) {
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
    protected Response doHelp(AwardSessionSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        AwardSession awardSession = awardSessionService.findById(id);
        result.put("awardSession", awardSession);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AwardSession awardSession = (AwardSession) JsonUtil.instance().toObject(data, AwardSession.class);
        awardSessionService.update(awardSession);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AwardSession awardSession = (AwardSession) JsonUtil.instance().toObject(data, AwardSession.class);
        awardSession = awardSessionService.insert(awardSession);
        result.put("awardSession", awardSession);
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

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response doMark(Long id, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        awardSessionService.updateStatus(id, status);
        return result;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Response noover(Integer over) {
        SuccessResponse result = new SuccessResponse();
        List<AwardSession> list = awardSessionService.findByLotterySource(over, null);
        result.put("list", list);
        return result;
    }

}
