package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.Account;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.LoginService;
import com.d2c.order.dto.RechargeRuleDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.AccountItem;
import com.d2c.order.model.CashCard;
import com.d2c.order.model.Recharge;
import com.d2c.order.model.RedPacketsItem;
import com.d2c.order.query.AccountItemSearcher;
import com.d2c.order.query.RedPacketsItemSearcher;
import com.d2c.order.service.AccountItemService;
import com.d2c.order.service.RechargeRuleService;
import com.d2c.order.service.RechargeService;
import com.d2c.order.service.RedPacketsItemService;
import com.d2c.order.service.tx.CashCardTxService;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的钱包
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/account")
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountItemService accountItemService;
    @Autowired
    private RechargeRuleService rechargeRuleService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private RedPacketsItemService redPacketsItemService;
    @Autowired
    private LoginService loginService;
    @Reference
    private CashCardTxService cashCardTxService;

    /**
     * 我的钱包信息
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseResult accountInfo() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Account account = accountService.findByMemberId(memberInfo.getId());
        if (account == null) {
            throw new BusinessException("我的钱包不存在！");
        }
        result.put("account", account.toJson());
        return result;
    }

    /**
     * 钱包消费明细
     *
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/payitem/list", method = RequestMethod.GET)
    public ResponseResult accountItemList(PageModel page, AccountItemSearcher searcher) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        searcher.setStatus(1);
        PageResult<AccountItem> pager = accountItemService.findBySearch(searcher, page);
        JSONArray array = new JSONArray();
        for (AccountItem accounItem : pager.getList()) {
            if (accounItem.getAmount().add(accounItem.getGiftAmount()).compareTo(new BigDecimal(0.00)) != 0) {
                array.add(accounItem.toJson());
            }
        }
        result.putPage("payItems", pager, array);
        return result;
    }

    /**
     * 充值规则
     *
     * @return
     */
    @RequestMapping(value = "/rule", method = RequestMethod.GET)
    public ResponseResult rechargeRule() {
        ResponseResult result = new ResponseResult();
        RechargeRuleDto rule = rechargeRuleService.findValidRule();
        result.put("rechargeRule", rule.toJson());
        return result;
    }

    /**
     * 充值提交
     *
     * @param sn             D2C卡号
     * @param password       D2C卡密码
     * @param payChannel     支付渠道
     * @param rechargeAmount 支付宝充值的金额
     * @return
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public ResponseResult doRecharge(String sn, String password, @RequestParam(required = true) String payChannel,
                                     BigDecimal rechargeAmount) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Account account = accountService.findByMemberId(memberInfo.getId());
        if (account == null || account.getStatus() <= 0) {
            throw new BusinessException("您的账户不存在或已冻结！");
        }
        if (PaymentTypeEnum.CARDPAY.name().equalsIgnoreCase(payChannel)) {
            // D2C卡充值
            if (StringUtil.hasBlack(new String[]{password, sn})) {
                throw new BusinessException("卡号及密码不能为空！");
            }
            CashCard card = cashCardTxService.doConvertCashCard(sn, password, memberInfo.getId(),
                    memberInfo.getLoginCode(), account.getId());
            if (card == null) {
                throw new BusinessException("充值不成功，请联系客服！");
            }
            result.put("sn", card.getCode());
            result.put("payChannel", PaymentTypeEnum.CARDPAY.name());
        } else {
            // 现金充值
            if (rechargeAmount == null) {
                throw new BusinessException("请输入充值金额！");
            } else if (rechargeAmount.compareTo(new BigDecimal(0.00)) <= 0) {
                throw new BusinessException("充值金额必须大于0！");
            } else if (rechargeAmount.compareTo(new BigDecimal(500000)) > 0) {
                throw new BusinessException("充值不成功，充值上限为500,000.00！");
            }
            Recharge recharge = new Recharge(account.getId(), memberInfo.getId(), memberInfo.getLoginCode());
            recharge.setStatus(-2);
            recharge.setPayChannel(payChannel);
            recharge.setPayAccount(memberInfo.getLoginCode());
            recharge.setRechargeAmount(rechargeAmount);
            // 支付宝支付的之后在回调中更新
            recharge.setPayDate(new Date());
            recharge.setPaySn(String.valueOf(new Date().getTime()));
            recharge = rechargeService.insert(recharge, true);
            result.put("sn", recharge.getSn());
            result.put("payChannel", recharge.getPayChannel());
        }
        return result;
    }

    /**
     * 校验钱包交易密码
     *
     * @param password
     * @return
     */
    @RequestMapping(value = "/check/password", method = RequestMethod.POST)
    public ResponseResult checkPassword(@RequestParam(required = true) String password) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        String error = accountService.checkPassword(member.getId(), password);
        if (!("1".equalsIgnoreCase(error))) {
            if ("输入密码错误已经5次，请点击忘记密码或2小时后重试".equalsIgnoreCase(error)) {
                result.put("errorTimes", 5);
            }
            throw new BusinessException(error);
        }
        // 密码过于简单
        result.put("danger", loginService.countDangerPasswd(password));
        return result;
    }

    /**
     * 我的红包信息
     *
     * @return
     */
    @RequestMapping(value = "/red", method = RequestMethod.GET)
    public ResponseResult redInfo() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Account account = accountService.findByMemberId(memberInfo.getId());
        if (account == null) {
            throw new BusinessException("我的钱包不存在！");
        }
        result.put("account", account.toJson());
        RedPacketsItemSearcher searcher = new RedPacketsItemSearcher();
        searcher.setAccountId(account.getId());
        PageResult<RedPacketsItem> pager = redPacketsItemService.findBySearcher(searcher, new PageModel(1, 3));
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("redPacketsItems", pager, array);
        return result;
    }

    /**
     * 我的红包记录
     *
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/red/list", method = RequestMethod.GET)
    public ResponseResult redItemList(PageModel page, RedPacketsItemSearcher searcher) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Account account = accountService.findByMemberId(memberInfo.getId());
        if (account == null) {
            throw new BusinessException("我的钱包不存在！");
        }
        searcher.setAccountId(account.getId());
        PageResult<RedPacketsItem> pager = redPacketsItemService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("redPacketsItems", pager, array);
        return result;
    }

}
