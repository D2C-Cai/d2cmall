package com.d2c.backend.rest.product;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.product.model.SalesPropertyGroup;
import com.d2c.product.query.SalesPropertyGroupSearcher;
import com.d2c.product.service.SalesPropertyGroupService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/product/salespropertygroup")
public class SalesPropertyGroupCtrl extends BaseCtrl<SalesPropertyGroupSearcher> {

    @Autowired
    private SalesPropertyGroupService salesPropertyGroupService;

    @Override
    protected List<Map<String, Object>> getRow(SalesPropertyGroupSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(SalesPropertyGroupSearcher searcher) {
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
    protected Response doHelp(SalesPropertyGroupSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(SalesPropertyGroupSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<SalesPropertyGroup> pager = salesPropertyGroupService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        SalesPropertyGroup salesPropertyGroup = salesPropertyGroupService.findById(id);
        result.put("salesPropertyGroup", salesPropertyGroup);
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
        SalesPropertyGroup salesPropertyGroup = (SalesPropertyGroup) JsonUtil.instance().toObject(data,
                SalesPropertyGroup.class);
        BeanUt.trimString(salesPropertyGroup);
        Admin admin = this.getLoginedAdmin();
        salesPropertyGroup.setCreator(admin.getUsername());
        salesPropertyGroup = salesPropertyGroupService.insert(salesPropertyGroup);
        result.put("salesPropertyGroup", salesPropertyGroup);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        SalesPropertyGroup salesPropertyGroup = (SalesPropertyGroup) JsonUtil.instance().toObject(data,
                SalesPropertyGroup.class);
        salesPropertyGroupService.update(salesPropertyGroup);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public Response updateStatus(Integer status, Long id) {
        SuccessResponse result = new SuccessResponse();
        int success = salesPropertyGroupService.updateStatus(id, status);
        if (success != 1) {
            result.setStatus(-1);
            result.setMsg("操作不成功！");
        }
        return result;
    }

}
