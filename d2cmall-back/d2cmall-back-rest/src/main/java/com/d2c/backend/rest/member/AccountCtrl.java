package com.d2c.backend.rest.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Account;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.AccountSearcher;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.model.RedPacketsItem.BusinessTypeEnum;
import com.d2c.order.service.tx.AccountTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 钱包
 *
 * @author wwn
 */
@RestController
@RequestMapping("/rest/account/account")
public class AccountCtrl extends BaseCtrl<AccountSearcher> {

    @Autowired
    private AccountService accountService;
    @Reference
    private AccountTxService accountTxService;
    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    protected Response doHelp(AccountSearcher queryModel, PageModel pageModel) {
        BeanUt.trimString(queryModel);
        PageResult<Account> pager = accountService.findBySearch(queryModel, pageModel);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response doList(AccountSearcher searcher, PageModel pageModel) {
        BeanUt.trimString(searcher);
        PageResult<Account> pager = accountService.findBySearch(searcher, pageModel);
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
    protected List<Map<String, Object>> getRow(AccountSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(AccountSearcher searcher) {
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
     * 总金额查询
     *
     * @return
     */
    @RequestMapping(value = "/amount", method = RequestMethod.POST)
    public Response getAmount() {
        SuccessResponse result = new SuccessResponse();
        Map<String, BigDecimal> map = accountService.countAccountAmount();
        result.put("map", map);
        return result;
    }

    /**
     * 关闭/开启账户支付功能
     *
     * @param status
     * @param ids
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/lock", method = RequestMethod.POST)
    public Response doLock(int status, Long[] ids) throws NotLoginException {
        this.getLoginedAdmin();
        SuccessResponse resp = new SuccessResponse();
        for (Long id : ids) {
            if (status == 0) {
                accountService.doLockAccount(id, false);
            } else if (status == 1) {
                accountService.doLockAccount(id, true);
            }
        }
        return resp;
    }

    /**
     * 冻结账户（关联充值）
     *
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public Response updateStatus(Long[] ids, int status) {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        int count = 0;
        for (Long id : ids) {
            int success = accountService.updateStatus(id, status);
            if (success > 0) {
                count++;
            }
        }
        result.setMessage("操作成功" + count + "条，不成功" + (ids.length - count) + "条");
        return result;
    }

    /**
     * 红包赠送
     *
     * @param loginCode
     * @param amount
     * @return
     */
    @RequestMapping(value = "/update/red", method = RequestMethod.POST)
    public Response updateRedAmout(String loginCode, BigDecimal amount) {
        this.getLoginedAdmin();
        MemberInfo member = memberInfoService.findByLoginCode(loginCode);
        accountTxService.doSuccessRed(member.getId(), BusinessTypeEnum.SYSTEM.name(), amount);
        return new SuccessResponse();
    }

}
