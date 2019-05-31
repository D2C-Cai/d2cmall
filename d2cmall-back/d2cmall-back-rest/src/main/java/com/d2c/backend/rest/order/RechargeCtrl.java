package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.enums.PayTypeEnum;
import com.d2c.member.model.Account;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.order.dto.RechargeDto;
import com.d2c.order.model.Recharge;
import com.d2c.order.query.RechargeSearcher;
import com.d2c.order.service.RechargeService;
import com.d2c.order.service.tx.RechargeTxService;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/account/recharge")
public class RechargeCtrl extends BaseCtrl<RechargeSearcher> {

    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Reference
    private RechargeTxService rechargeTxService;

    @Override
    protected Response doHelp(RechargeSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return doList(searcher, page);
    }

    @Override
    protected Response doList(RechargeSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<RechargeDto> pager = rechargeService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("audit", true);
        return result;
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
        data.remove("bank");
        data.remove("cardId");
        data.remove("channel");
        data.remove("levelId");
        data.remove("cardHolder");
        data.remove("cardAccount");
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Recharge recharge = (Recharge) JsonUtil.instance().toObject(data, Recharge.class);
        MemberInfo memberInfo = memberInfoService.findById(recharge.getMemberId());
        if (!memberInfo.getLoginCode().equals(recharge.getPayAccount())) {
            result.setStatus(-1);
            result.setMessage("会员账户和付款账号不一致！");
            return result;
        }
        // 赠送的交易号取当前时间
        if (PayTypeEnum.GIVE.name().equalsIgnoreCase(recharge.getPayType())
                && StringUtils.isBlank(recharge.getPaySn())) {
            recharge.setPaySn(String.valueOf(new Date().getTime()));
        }
        try {
            // recharge.validate();
            Recharge newRecharge = new Recharge(recharge.getAccountId(), memberInfo.getId(), admin.getUsername());
            BeanUtils.copyProperties(recharge, newRecharge, "sn");
            recharge = rechargeService.insert(newRecharge, false);
            if (recharge != null) {
                result.put("data", recharge);
                result.setMessage("充值成功");
            } else {
                result.setStatus(-1);
                result.setMessage("充值不成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
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
    protected List<Map<String, Object>> getRow(RechargeSearcher searcher, PageModel page) {
        PageResult<RechargeDto> pager = rechargeService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> cellsMap;
        for (Recharge recharge : pager.getList()) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("交易流水号", recharge.getPaySn());
            cellsMap.put("充值金额", recharge.getRechargeAmount());
            cellsMap.put("红包金额", recharge.getGiftAmount());
            cellsMap.put("总金额", recharge.getRechargeAmount().add(recharge.getGiftAmount()));
            cellsMap.put("充值渠道", recharge.getPayChannelName());
            cellsMap.put("会员ID", recharge.getMemberId());
            cellsMap.put("充值账号", recharge.getPayAccount());
            cellsMap.put("活动名称", recharge.getRuleName());
            cellsMap.put("状态", recharge.getStatusName());
            cellsMap.put("创建时间", recharge.getCreateDate() == null ? "" : sdf.format(recharge.getCreateDate()));
            cellsMap.put("发生时间", recharge.getPayDate() == null ? "" : sdf.format(recharge.getPayDate()));
            cellsMap.put("审核时间", recharge.getSubmitDate() == null ? "" : sdf.format(recharge.getSubmitDate()));
            cellsMap.put("审核人", recharge.getSubmitor());
            cellsMap.put("关闭时间", recharge.getCloseDate());
            cellsMap.put("关闭人", recharge.getCloser());
            cellsMap.put("备注", recharge.getMemo());
            cellsMap.put("门店充值", recharge.getStoreId() == null ? "非门店充值" : recharge.getStoreId());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(RechargeSearcher searcher) {
        return rechargeService.countBySearch(searcher);
    }

    @Override
    protected String getFileName() {
        return "充值表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"交易流水号", "充值金额", "红包金额", "总金额", "充值渠道", "会员ID", "充值账号", "活动名称", "状态", "创建时间", "发生时间",
                "审核时间", "审核人", "关闭时间", "关闭人", "备注", "门店充值"};
    }

    @Override
    protected String getExportFileType() {
        return "Recharge";
    }

    /**
     * 充值页面
     *
     * @param model
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/{accountId}/{memberId}", method = RequestMethod.POST)
    public Response recharge(@PathVariable Long accountId, @PathVariable Long memberId) {
        SuccessResponse result = new SuccessResponse();
        RechargeDto recharge = new RechargeDto();
        Account account = null;
        if (accountId != null && accountId != 0) {
            account = this.accountService.findById(accountId);
        }
        if (account == null) {
            result.setStatus(-1);
            result.setMessage("账户不存在");
            return result;
        }
        if (!account.getMemberId().equals(memberId)) {
            result.setStatus(-1);
            result.setMessage("账户与绑定会员不匹配");
            return result;
        }
        if (account != null && account.getStatus() == 1) {
            recharge.setAccount(account);
            recharge.setAccountId(account.getId());
            recharge.setMemberId(account.getMemberId());
            recharge.setStatus(0);
            result.put("recharge", recharge);
            return result;
        } else {
            result.setStatus(-1);
            result.setMessage("账户未开通");
            return result;
        }
    }

    /**
     * 充值审核
     *
     * @param model
     * @param recharge
     * @return
     */
    @RequestMapping(value = "/recharge/submit/{rechargeId}", method = RequestMethod.POST)
    public Response rechargeSubmit(@PathVariable Long rechargeId) {
        SuccessResponse result = new SuccessResponse();
        try {
            Admin admin = this.getLoginedAdmin();
            String submitor = admin.getUsername();
            int success = rechargeTxService.doSuccessRecharge(rechargeId, submitor, null);
            if (success > 0) {
                result.setMessage("提交成功");
            } else {
                result.setMessage("提交不成功");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setMessage("提交不成功，错误原因：" + e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 关闭充值
     *
     * @throws NotLoginException
     */
    @RequestMapping(value = "/recharge/close/{rechargeId}", method = RequestMethod.POST)
    public Response rechargeClose(@PathVariable Long rechargeId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        try {
            String closer = admin.getUsername();
            result.setMessage("关闭成功");
            rechargeService.doClose(rechargeId, closer, "");
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setMessage("关闭不成功" + "，" + e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

    @RequestMapping(value = "/recharge/view", method = RequestMethod.POST)
    public Response rechargeView(RechargeSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<RechargeDto> pager = rechargeService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

}
