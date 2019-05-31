package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.model.Admin;
import com.d2c.order.model.CashCardDef;
import com.d2c.order.query.CashCardDefSearcher;
import com.d2c.order.service.CashCardDefService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/account/cashcarddef")
public class CashCardDefCtrl extends BaseCtrl<CashCardDefSearcher> {

    @Autowired
    private CashCardDefService cashCardDefService;

    @Override
    protected Response doHelp(CashCardDefSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected Response doList(CashCardDefSearcher searcher, PageModel page) {
        PageResult<CashCardDef> pager = cashCardDefService.findPageBy(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        CashCardDef def = this.cashCardDefService.findById(id);
        return new SuccessResponse(def);
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        try {
            CashCardDef cashCardDef = (CashCardDef) JsonUtil.instance().toObject(data, CashCardDef.class);
            CashCardDef exCashCardDef = cashCardDefService.findByCode(cashCardDef.getCode());
            if (exCashCardDef != null) {
                result.setStatus(-1);
                result.setMessage("保存不成功！已经存在相同编码的D2C卡！");
                return result;
            }
            cashCardDef = cashCardDefService.insert(cashCardDef);
            result.put("cashCardDef", cashCardDef);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Response result = new SuccessResponse();
        CashCardDef def = (CashCardDef) JsonUtil.instance().toObject(data, CashCardDef.class);
        CashCardDef exCashCardDef = cashCardDefService.findById(def.getId());
        if (exCashCardDef != null && !exCashCardDef.getCode().equals(def.getCode())) {
            result.setStatus(-1);
            result.setMessage("保存不成功！已经存在相同编码的D2C卡！");
            return result;
        }
        cashCardDefService.update(def);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        Response result = new SuccessResponse();
        for (int i = 0; i < id.length; i++) {
            cashCardDefService.delete(id[i]);
        }
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        SuccessResponse result = new SuccessResponse();
        cashCardDefService.delete(id);
        result.setMessage("删除成功！");
        return result;
    }

    @Override
    protected List<Map<String, Object>> getRow(CashCardDefSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(CashCardDefSearcher searcher) {
        return cashCardDefService.count(searcher);
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
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/checkCode", method = RequestMethod.POST)
    public Response checkCode(String name) {
        SuccessResponse result = new SuccessResponse();
        if (cashCardDefService.findByCode(name) != null) {
            result.setStatus(-1);
        }
        return result;
    }

    @RequestMapping(value = "/audit/{id}", method = RequestMethod.POST)
    public Response audit(@PathVariable("id") Long id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        int success = cashCardDefService.doAudit(id);
        if (success > 0) {
            result.setMessage("审核成功！");
        } else {
            result.setStatus(-1);
            result.setMessage("审核不成功！");
        }
        return result;
    }

    @RequestMapping(value = "/cancelAudit/{id}", method = RequestMethod.POST)
    public Response cancelAudit(@PathVariable("id") Long id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        int success = cashCardDefService.doCancelAudit(id);
        if (success > 0) {
            result.setMessage("取消审核成功！");
        } else {
            result.setStatus(-1);
            result.setMessage("取消审核不成功！");
        }
        return result;
    }

    @RequestMapping(value = "/send/{id}", method = RequestMethod.POST)
    public Response send(@PathVariable("id") Long id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            int success = cashCardDefService.doProduce(id, admin.getUsername());
            if (success <= 0) {
                result.setStatus(-1);
                result.setMessage("D2C卡生成不成功！");
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

}
