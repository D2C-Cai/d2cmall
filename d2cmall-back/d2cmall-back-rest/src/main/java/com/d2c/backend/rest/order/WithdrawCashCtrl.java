package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.order.model.WithdrawCash;
import com.d2c.order.query.WithdrawCashSearcher;
import com.d2c.order.service.WithdrawCashService;
import com.d2c.order.service.tx.WithdrawTxService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/account/withdrawcash")
public class WithdrawCashCtrl extends BaseCtrl<WithdrawCashSearcher> {

    @Autowired
    private WithdrawCashService withdrawCashService;
    @Reference
    private WithdrawTxService withdrawTxService;

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
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
        this.getLoginedAdmin();
        WithdrawCash cash = (WithdrawCash) JsonUtil.instance().toObject(data, WithdrawCash.class);
        try {
            SuccessResponse result = new SuccessResponse();
            // TODO 登录处理
            // rule.setCreator(admin.getUsername());
            cash = withdrawCashService.insert(cash);
            result.put("data", cash);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ErrorResponse(e.getMessage());
        }
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(WithdrawCashSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected Response doList(WithdrawCashSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<WithdrawCash> pager = withdrawCashService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected List<Map<String, Object>> getRow(WithdrawCashSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(WithdrawCashSearcher searcher) {
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
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 审核确认提现申请
     */
    @RequestMapping(value = "/confirm/{id}", method = RequestMethod.POST)
    public Response confirm(@PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        int n = withdrawCashService.doConfirm(id);
        if (n != 1) {
            result.setStatus(-1);
            result.setMessage("审核不成功");
        }
        return result;
    }

    /**
     * 关闭未完成的提现申请
     */
    @RequestMapping(value = "/close/{id}", method = RequestMethod.POST)
    public Response close(@PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        try {
            int n = withdrawCashService.doClose(id);
            if (n != 1) {
                result.setStatus(-1);
                result.setMessage("关闭不成功");
            }
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 提交已完成的交易申请（表单）
     */
    @RequestMapping(value = "/success/{id}", method = RequestMethod.POST)
    public Response toSuccess(@PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        WithdrawCash withdrawCash = withdrawCashService.findById(id);
        if (withdrawCash != null) {
            result.put("withdrawCash", withdrawCash);
        } else {
            result.setStatus(-1);
            result.setMessage("提现申请不存在");
        }
        return result;
    }

    /**
     * 提交已完成的交易申请
     *
     * @throws NotLoginException
     */
    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public Response success(WithdrawCash withdrawCash) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        WithdrawCash success = this.withdrawCashService.findById(withdrawCash.getId());
        success.setAccountId(withdrawCash.getAccountId());
        success.setPayChannel(withdrawCash.getPayChannel());
        success.setPaySn(withdrawCash.getPaySn());
        success.setActualPay(withdrawCash.getActualPay());
        success.setPayDate(withdrawCash.getPayDate());
        success.setPayer(withdrawCash.getPayer());
        success.setStatus(8);
        success.setLastModifyMan(admin.getUsername());
        try {
            withdrawTxService.doSuccessWithdraw(success);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setStatus(-1);
            result.setMessage("提现不成功！" + e.getMessage());
        }
        return result;
    }

}
