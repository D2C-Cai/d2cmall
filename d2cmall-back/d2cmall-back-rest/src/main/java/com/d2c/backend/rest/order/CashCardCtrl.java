package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.model.Admin;
import com.d2c.order.model.CashCard;
import com.d2c.order.query.CashCardSearcher;
import com.d2c.order.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/account/cashcard")
public class CashCardCtrl extends BaseCtrl<CashCardSearcher> {

    @Autowired
    private CashCardService cashCardService;

    @Override
    protected Response doHelp(CashCardSearcher searcher, PageModel page) {
        return doList(searcher, page);
    }

    @Override
    protected Response doList(CashCardSearcher searcher, PageModel page) {
        PageResult<CashCard> pager = cashCardService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(CashCardSearcher searcher, PageModel page) {
        List<CashCard> list = cashCardService.findBy(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (CashCard cc : list) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("卡号", cc.getCode());
            cellsMap.put("密码", cc.getPassword());
            cellsMap.put("金额", cc.getAmount());
            cellsMap.put("截止日期", cc.getExpireDate() == null ? "" : sdf.format(cc.getExpireDate()));
            cellsMap.put("优惠券使用说明", cc.getRemark());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(CashCardSearcher searcher) {
        return cashCardService.countBySearcher(searcher);
    }

    @Override
    protected String getFileName() {
        return "D2C卡列表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"卡号", "密码", "金额", "截止日期", "优惠券使用说明"};
    }

    @Override
    protected String getExportFileType() {
        return "D2CCard";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Response send(Long[] ids, String sendmark) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            cashCardService.doSend(id, admin.getUsername(), sendmark);
        }
        result.setMessage("发放成功！");
        return result;
    }

    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    public Response invalid(Long[] ids, String sendmark) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            cashCardService.doInvalid(id, admin.getUsername(), sendmark);
        }
        result.setMessage("作废成功！");
        return result;
    }

}
