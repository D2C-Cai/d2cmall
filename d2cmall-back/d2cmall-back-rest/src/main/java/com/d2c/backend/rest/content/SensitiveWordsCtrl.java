package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.SensitiveWords;
import com.d2c.content.query.SensitiveWordsSearcher;
import com.d2c.content.service.SensitiveWordsService;
import com.d2c.member.model.Admin;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/security/sensitivewords")
public class SensitiveWordsCtrl extends BaseCtrl<SensitiveWordsSearcher> {

    @Autowired
    private SensitiveWordsService sensitiveWordsService;

    @Override
    protected Response doList(SensitiveWordsSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<SensitiveWords> pager = sensitiveWordsService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(SensitiveWordsSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(SensitiveWordsSearcher searcher, PageModel page) {
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
    protected Response doHelp(SensitiveWordsSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        SensitiveWords sensitiveWords = (SensitiveWords) JsonUtil.instance().toObject(data, SensitiveWords.class);
        sensitiveWords.setLastModifyMan(admin.getUsername());
        int success = sensitiveWordsService.update(sensitiveWords);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作失败！");
        }
        result.setMessage("操作成功！");
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        SensitiveWords sensitiveWords = (SensitiveWords) JsonUtil.instance().toObject(data, SensitiveWords.class);
        SensitiveWords tempSensitiveWords = sensitiveWordsService.findByKeyword(sensitiveWords.getKeyword());
        if (tempSensitiveWords == null) {
            sensitiveWords = sensitiveWordsService.insert(sensitiveWords);
            result.put("sensitiveWords", sensitiveWords);
        } else {
            result.setStatus(-1);
            result.setMessage("该敏感词已存在！");
        }
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        SensitiveWords sensitiveWords = sensitiveWordsService.findById(id);
        if (sensitiveWords != null) {
            sensitiveWordsService.deleteById(id);
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/updatestatus/{id}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Long id, Integer status) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        sensitiveWordsService.updateStatus(id, status, this.getLoginedAdmin().getName());
        return result;
    }

}
