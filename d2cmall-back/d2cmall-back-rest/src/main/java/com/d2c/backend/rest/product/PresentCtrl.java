package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.product.model.Present;
import com.d2c.product.query.PresentSearcher;
import com.d2c.product.service.PresentService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/present")
public class PresentCtrl extends BaseCtrl<PresentSearcher> {

    @Autowired
    private PresentService presentService;

    @Override
    protected Response doList(PresentSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Present> pager = presentService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(PresentSearcher searcher) {
        return 0;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(PresentSearcher searcher, PageModel page) {
        return null;
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
    protected Response doHelp(PresentSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Present present = presentService.findById(id);
        result.put("present", present);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Present Present = JsonUtil.instance().toObject(data, Present.class);
        int success = presentService.update(Present);
        SuccessResponse successResponse = new SuccessResponse();
        if (success < 1) {
            successResponse.setStatus(-1);
            successResponse.setMsg("操作失败");
        }
        return successResponse;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Present present = JsonUtil.instance().toObject(data, Present.class);
        present = presentService.insert(present);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.put("present", present);
        return successResponse;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = presentService.deleteById(id, admin.getName());
        if (success <= 0) {
            result.setStatus(-1);
            result.setMsg("操作失败");
        }
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return null;
    }

    @RequestMapping(value = "/marketable/{mark}", method = RequestMethod.POST)
    public Response updateMark(Long[] ids, @PathVariable Integer mark) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        for (Long id : ids) {
            presentService.updateStatus(id, mark, admin.getUsername());
        }
        return result;
    }

    @RequestMapping(value = "/update/sort", method = RequestMethod.POST)
    public Response updateSort(Long id, Integer sort) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = presentService.updateSort(id, sort, admin.getUsername());
        if (success <= 0) {
            result.setStatus(-1);
            result.setMsg("操作失败");
        }
        return result;
    }

}
