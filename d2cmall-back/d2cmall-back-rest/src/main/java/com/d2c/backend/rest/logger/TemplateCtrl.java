package com.d2c.backend.rest.logger;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.Template;
import com.d2c.logger.query.TemplateSearcher;
import com.d2c.logger.service.TemplateService;
import com.d2c.member.model.Admin;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/sys/template")
public class TemplateCtrl extends BaseCtrl<TemplateSearcher> {

    @Autowired
    private TemplateService templateService;

    @Override
    protected List<Map<String, Object>> getRow(TemplateSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(TemplateSearcher searcher) {
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
    protected Response doHelp(TemplateSearcher searcher, PageModel page) {
        SuccessResponse reslut = new SuccessResponse();
        BeanUt.trimString(searcher);
        List<HelpDTO> helpList = templateService.findBySearchForHelp(searcher, page);
        reslut.put("articleTemplateList", helpList);
        return reslut;
    }

    @Override
    protected Response doList(TemplateSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Template> pager = templateService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Template template = templateService.findById(id);
        result.put("template", template);
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
        try {
            templateService.delete(id);
            result.setMessage("删除成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Template template = (Template) JsonUtil.instance().toObject(data, Template.class);
        template.setCreateMan(admin.getName());
        try {
            template = templateService.insert(template);
            result.put("template", template);
            result.setMessage("保存成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Template template = (Template) JsonUtil.instance().toObject(data, Template.class);
        template.setCreateMan(admin.getUsername());
        try {
            templateService.update(template);
            result.setMessage("修改成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
        return result;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

}
