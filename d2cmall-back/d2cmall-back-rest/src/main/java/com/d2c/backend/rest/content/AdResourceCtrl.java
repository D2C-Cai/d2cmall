package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.AdResource;
import com.d2c.content.query.AdResourceSearcher;
import com.d2c.content.service.AdResourceService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/adresource")
public class AdResourceCtrl extends BaseCtrl<AdResourceSearcher> {

    @Autowired
    private AdResourceService adResourceService;

    @Override
    protected Response doList(AdResourceSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AdResource> pager = adResourceService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected int count(AdResourceSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(AdResourceSearcher searcher, PageModel page) {
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
    protected Response doHelp(AdResourceSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        AdResource adResource = adResourceService.findById(id);
        result.put("adResource", adResource);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AdResource adResource = (AdResource) JsonUtil.instance().toObject(data, AdResource.class);
        adResourceService.update(adResource);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        AdResource adResource = (AdResource) JsonUtil.instance().toObject(data, AdResource.class);
        adResource.setStatus(0);
        if (adResourceService.findByAppChannelAndTypeForBack(adResource.getAppChannel(),
                adResource.getType()) == null) {
            adResource = adResourceService.insert(adResource);
            result.put("adResource", adResource);
            result.setMessage("新增成功");
        } else {
            result.setStatus(-1);
            result.setMessage("该广告已经存在！");
        }
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        adResourceService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response updateMark(Long id, @PathVariable Integer status) {
        SuccessResponse result = new SuccessResponse();
        adResourceService.updateStatus(id, status);
        return result;
    }

}
