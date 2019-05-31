package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.enums.BusinessTypeEnum;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.model.Admin;
import com.d2c.order.model.AccountItem;
import com.d2c.order.query.AccountItemSearcher;
import com.d2c.order.service.AccountItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/account/bill")
public class AccountItemCtrl extends BaseCtrl<AccountItemSearcher> {

    @Autowired
    private AccountItemService accountItemService;

    @Override
    protected int count(AccountItemSearcher searcher) {
        return this.accountItemService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "钱包交易明细表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"会员ID", "钱包ID", "钱包账号", "业务类型", "财务类型", "流水号", "业务时间", "业务单号", "操作人", "变动本金", "变动赠金",
                "支付状态", "备注"};
    }

    @Override
    protected List<Map<String, Object>> getRow(AccountItemSearcher searcher, PageModel page) {
        List<AccountItem> bills = accountItemService.findList(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> cellsMap = null;
        for (AccountItem p : bills) {
            cellsMap = new HashMap<>();
            cellsMap.put("会员ID", p.getMemberId());
            cellsMap.put("钱包ID", p.getSelfAccountId());
            cellsMap.put("钱包账号", p.getSelfAccountSn());
            cellsMap.put("业务类型", BusinessTypeEnum.valueOf(p.getBusinessType()).getDisplay());
            cellsMap.put("财务类型", PayTypeEnum.valueOf(p.getPayType()).getDisplay());
            cellsMap.put("流水号", p.getSn());
            cellsMap.put("业务时间", p.getTransactionTime() == null ? "" : sdf.format(p.getTransactionTime()));
            cellsMap.put("业务单号", p.getTransactionInfo() == null ? "" : p.getTransactionInfo().substring(5));
            cellsMap.put("操作人", p.getCreator());
            cellsMap.put("变动本金", p.getFactAmount());
            cellsMap.put("变动赠金", p.getFactGiftAmount());
            cellsMap.put("支付状态", p.getStatusName());
            cellsMap.put("备注", p.getMemo());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected Response doHelp(AccountItemSearcher searcher, PageModel page) {
        return doList(searcher, page);
    }

    @Override
    protected Response doList(AccountItemSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<AccountItem> pager = accountItemService.findBySearch(searcher, page);
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
    protected String getExportFileType() {
        return "Bill";
    }

    /**
     * 关闭账单业务
     *
     * @param itemId
     * @param closeInfo
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/close/{itemId}", method = RequestMethod.POST)
    public Response doClose(@PathVariable Long itemId, String closeInfo) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        AccountItem payItem = accountItemService.findById(itemId);
        if (payItem == null) {
            result.setStatus(-1);
            result.setMessage("退款原始单不存在，退款不成功！");
            return result;
        }
        try {
            int success = accountItemService.doCancel(itemId, admin.getUsername(), closeInfo);
            if (success <= 0) {
                result.setStatus(-1);
                result.setMessage("关闭不成功！");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setMessage(e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

}
