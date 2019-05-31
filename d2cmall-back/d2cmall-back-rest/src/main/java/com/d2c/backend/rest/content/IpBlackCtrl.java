package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.IpBlack;
import com.d2c.content.query.IpBlackSearcher;
import com.d2c.content.service.IpBlackService;
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
@RequestMapping("/rest/security/ipblack")
public class IpBlackCtrl extends BaseCtrl<IpBlackSearcher> {

    @Autowired
    private IpBlackService ipBlackService;

    @Override
    protected Response doList(IpBlackSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<IpBlack> pager = ipBlackService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(IpBlackSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(IpBlackSearcher searcher, PageModel page) {
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
    protected Response doHelp(IpBlackSearcher searcher, PageModel page) {
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
        IpBlack ipBlack = (IpBlack) JsonUtil.instance().toObject(data, IpBlack.class);
        ipBlack.setLastModifyMan(admin.getUsername());
        int success = ipBlackService.update(ipBlack);
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
        IpBlack ipBlack = (IpBlack) JsonUtil.instance().toObject(data, IpBlack.class);
        IpBlack tempIpBlack = ipBlackService.findById(ipBlack.getId());
        if (tempIpBlack == null) {
            ipBlack = ipBlackService.insert(ipBlack);
            result.put("ipBlack", ipBlack);
        } else {
            result.setStatus(-1);
            result.setMessage("该黑名单已存在！");
        }
        result.setMessage("操作成功！");
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        IpBlack ipBlack = ipBlackService.findById(id);
        if (ipBlack != null) {
            ipBlackService.deleteById(id);
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
        ipBlackService.updateStatus(id, status, this.getLoginedAdmin().getName());
        return result;
    }

}
