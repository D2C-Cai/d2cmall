package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.SectionValue;
import com.d2c.content.query.SectionValueSearcher;
import com.d2c.content.service.SectionValueService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/sectionvalue")
public class SectionValueCtrl extends BaseCtrl<SectionValueSearcher> {

    @Autowired
    private SectionValueService sectionValueService;

    @Override
    protected List<Map<String, Object>> getRow(SectionValueSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(SectionValueSearcher searcher) {
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
    protected Response doHelp(SectionValueSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(SectionValueSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<SectionValue> pager = sectionValueService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        SectionValue sectionValue = sectionValueService.findById(id);
        result.put("sectionValue", sectionValue);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        result.setMessage("操作成功");
        SectionValue sectionValue = sectionValueService.findById(id);
        int success = sectionValueService.delete(id, sectionValue.getSectionDefId());
        if (success <= 0) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        SectionValue sectionValue = (SectionValue) JsonUtil.instance().toObject(data, SectionValue.class);
        sectionValue.setDeleted(0);
        sectionValue = sectionValueService.insert(this.filterChar(sectionValue));
        result.put("sectionValue", sectionValue);
        result.setMessage("新增成功");
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        SectionValue sectionValue = (SectionValue) JsonUtil.instance().toObject(data, SectionValue.class);
        sectionValueService.update(this.filterChar(sectionValue));
        result.put("sectionValue", sectionValue);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Integer status, Long[] ids) {
        for (Long id : ids) {
            try {
                sectionValueService.updateStatus(id, status);
            } catch (Exception e) {
                logger.error(e.getMessage());
                return new ErrorResponse(e.getMessage());
            }
        }
        return new SuccessResponse();
    }

    private SectionValue filterChar(SectionValue sectionValue) {
        if (StringUtils.isNotBlank(sectionValue.getShortTitle())) {
            String title1 = sectionValue.getShortTitle().replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。+！？“”&\\s]+", "");
            title1 = title1.replaceAll("[\n\r\t\b\f]+", "");
            sectionValue.setShortTitle(title1);
        }
        if (StringUtils.isNotBlank(sectionValue.getLongTitle())) {
            String title2 = sectionValue.getLongTitle().replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。+！？“”&\\s]+", "");
            title2 = title2.replaceAll("[\n\r\t\b\f]+", "");
            sectionValue.setLongTitle(title2);
        }
        return sectionValue;
    }

    @RequestMapping(value = "/deleted/list", method = RequestMethod.GET)
    public Response deletedList(SectionValueSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<SectionValue> pager = sectionValueService.findDeletedBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @RequestMapping(value = "/recovery/{id}", method = RequestMethod.POST)
    public Response doRecovery(@PathVariable Long id) throws NotLoginException {
        this.getLoginedAdmin();
        sectionValueService.doRecovery(id);
        return new SuccessResponse();
    }

}
