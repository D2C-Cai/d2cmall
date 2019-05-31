package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.content.dto.PageDefineDto;
import com.d2c.content.model.FieldDefine;
import com.d2c.content.model.PageDefine;
import com.d2c.content.query.PageDefineSearcher;
import com.d2c.content.service.FieldDefineService;
import com.d2c.content.service.PageDefineService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/pagedefine")
public class PageDefineCtrl extends BaseCtrl<PageDefineSearcher> {

    @Autowired
    private FieldDefineService fieldDefService;
    @Autowired
    private PageDefineService pageDefService;

    @Override
    protected List<Map<String, Object>> getRow(PageDefineSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(PageDefineSearcher searcher) {
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
    protected Response doHelp(PageDefineSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(PageDefineSearcher searcher, PageModel page) {
        PageDefine pdef = new PageDefine();
        BeanUtils.copyProperties(searcher, pdef);
        // define为1 为开发专用，2客服专用
        if (searcher.getTerminal() != null) {
            pdef.setTerminal(searcher.getTerminal());
        }
        PageResult<PageDefineDto> pager_result = pageDefService.findBySearch(pdef, page);
        SuccessResponse result = new SuccessResponse(pager_result);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
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
        PageDefine pageDefine = JsonUtil.instance().toObject(data, PageDefine.class);
        // 先去找一下，有没有出现相同的数据
        PageDefineDto pageDefineDto = pageDefService.findPageDefine(
                com.d2c.content.model.PageDefine.MODULE.valueOf(pageDefine.getModule()),
                com.d2c.content.model.PageDefine.TERMINAL.valueOf(pageDefine.getTerminal()), pageDefine.getVersion());
        if (pageDefineDto != null) {
            result.setStatus(-1);
            result.setMessage("保存不成功！已经存在：（编码+终端号+版本号）相同的数据，请重新编辑！");
            return result;
        }
        pageDefine = pageDefService.insert(pageDefine);
        result.put("pageDefine", pageDefine);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Response result = new SuccessResponse();
        PageDefine pageDefine = JsonUtil.instance().toObject(data, PageDefine.class);
        PageDefineDto pageDefineDto = pageDefService.findPageDefine(
                com.d2c.content.model.PageDefine.MODULE.valueOf(pageDefine.getModule()),
                com.d2c.content.model.PageDefine.TERMINAL.valueOf(pageDefine.getTerminal()), pageDefine.getVersion());
        if (pageDefineDto != null && !pageDefineDto.getId().equals(pageDefine.getId())) {
            result.setStatus(-1);
            result.setMessage("保存不成功！已经存在：（编码+终端号+版本号）相同的数据，请重新编辑！");
            return result;
        }
        pageDefService.update(pageDefine);
        return result;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public Response edit(@PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        PageDefine pageDefine = null;
        if (id == null || id == 0) {
            result.put("action", "save");
            pageDefine = new PageDefine();
        } else {
            result.put("action", "update");
            pageDefine = pageDefService.findById(id);
        }
        result.put("pageDefine", pageDefine);
        return result;
    }

    @RequestMapping(value = "/copy/{id}", method = RequestMethod.POST)
    public Response copy(@PathVariable Long id) {
        PageDefine searcher = new PageDefine();
        PageModel page = new PageModel();
        try {
            pageDefService.doCopy(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        PageResult<PageDefineDto> pager_result = pageDefService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager_result);
        result.put("define", 1);
        return result;
    }

    @RequestMapping(value = "/{id}/fielddef/list", method = RequestMethod.POST)
    public Response fieldDefineList(@PathVariable Long id, String desc) {
        SuccessResponse result = null;
        PageDefineDto pageDefine = pageDefService.findAllById(id);
        if (pageDefine != null)
            result = new SuccessResponse(pageDefine.getFieldList());
        else
            result = new SuccessResponse();
        return result;
    }

    @RequestMapping(value = "/field/update", method = RequestMethod.POST)
    public Response updateField(FieldDefine fieldDef) {
        Response result = new SuccessResponse();
        fieldDefService.update(fieldDef);
        result.setMessage("更新成功");
        return result;
    }

}
