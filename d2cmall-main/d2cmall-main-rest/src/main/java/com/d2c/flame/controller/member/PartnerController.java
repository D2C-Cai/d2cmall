package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.property.ASE;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.*;
import com.d2c.member.query.MemberSearcher;
import com.d2c.member.query.PartnerCounselorSearcher;
import com.d2c.member.query.PartnerInviteSearcher;
import com.d2c.member.query.PartnerSearcher;
import com.d2c.member.service.*;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.enums.TaxRule2StepEnum;
import com.d2c.order.model.PartnerCash;
import com.d2c.order.model.PartnerCash.PayType;
import com.d2c.order.model.PartnerGift;
import com.d2c.order.model.PartnerItem;
import com.d2c.order.model.Setting;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.query.PartnerCashSearcher;
import com.d2c.order.query.PartnerGiftSearcher;
import com.d2c.order.query.PartnerItemSearcher;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.PartnerTxService;
import com.d2c.order.third.payment.gongmall.client.GongmallClient;
import com.d2c.order.third.payment.gongmall.core.GongmallConfig;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductPartnerRelation;
import com.d2c.product.query.ProductSearcher;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.ProductPartnerRelationService;
import com.d2c.product.service.ProductService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * 无人买手店
 *
 * @author Cai
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/partner")
public class PartnerController extends BaseController {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerItemService partnerItemService;
    @Autowired
    private PartnerInviteService partnerInviteService;
    @Autowired
    private PartnerBillService partnerBillService;
    @Autowired
    private PartnerCashService partnerCashService;
    @Autowired
    private PartnerGiftService partnerGiftService;
    @Autowired
    private PartnerStoreService partnerStoreService;
    @Autowired
    private PartnerCounselorService partnerCounselorService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private ProductService productService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private ProductPartnerRelationService productPartnerRelationService;
    @Autowired
    private GongmallConfig gongmallConfig;
    @Reference
    private PartnerTxService partnerTxService;

    /**
     * 我的分销信息 TODO
     *
     * @return
     */
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public ResponseResult myInfo(String appTerminal) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner partner = partnerService.findByMemberId(memberInfo.getId());
        if (partner != null) {
            if (DeviceTypeEnum.APPIOS.toString().equalsIgnoreCase(appTerminal)) {
                if (partner.getStatus() < 0) {
                    result.setStatus(-1);
                    result.setData(null);
                    return result;
                }
            }
            result.put("member", memberInfo.toJson());
            result.put("partner", partner.toJson());
            result.put("applyCashAmount", partner.getApplyAmount());
        } else {
            result.setStatus(-1);
            result.setData(null);
            return result;
        }
        return result;
    }

    /**
     * 分销商信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        Partner partner = partnerService.findById(id);
        if (partner == null) {
            throw new BusinessException("分销商不存在！");
        }
        result.put("partner", partner.toSimpleJson());
        return result;
    }

    /**
     * 我的下级分销
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/children", method = RequestMethod.GET)
    public ResponseResult children(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getPartnerId() == null) {
            throw new BusinessException("您没有权限！");
        }
        PartnerSearcher searcher = new PartnerSearcher();
        searcher.setParentId(memberInfo.getPartnerId());
        PageResult<Partner> pager = partnerService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(partner -> array.add(partner.toJson()));
        result.putPage("children", pager, array);
        return result;
    }

    /**
     * 我的下级客户
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseResult customer(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getPartnerId() == null) {
            throw new BusinessException("您没有权限！");
        }
        MemberSearcher searcher = new MemberSearcher();
        searcher.setParentId(memberInfo.getPartnerId());
        PageResult<MemberInfo> pager = memberInfoService.findBySearch(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("customers", pager, array);
        return result;
    }

    /**
     * 删除团队成员
     *
     * @param childId
     * @return
     */
    @RequestMapping(value = "/separate", method = RequestMethod.POST)
    public ResponseResult separate(Long childId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner partner = partnerService.findById(memberInfo.getPartnerId());
        if (partner == null || partner.getLevel() == 2) {
            throw new BusinessException("您没有权限！");
        }
        partnerService.doSeparate(partner.getId(), childId, memberInfo.getLoginCode());
        return result;
    }

    /**
     * 我邀请的成员列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/invite/list", method = RequestMethod.GET)
    public ResponseResult inviteList(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getPartnerId() == null) {
            throw new BusinessException("您没有权限！");
        }
        PartnerInviteSearcher searcher = new PartnerInviteSearcher();
        searcher.setFromMemberId(memberInfo.getId());
        PageResult<PartnerInvite> pager = partnerInviteService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("invites", pager, array);
        return result;
    }

    /**
     * 更新分销商
     *
     * @param partner
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult updatePartner(Partner partner) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner old = partnerService.findById(partner.getId());
        if (old == null || !old.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("分销商数据不正确！");
        }
        partner.setMemberId(old.getMemberId());
        partner.setLoginCode(old.getLoginCode());
        partner.setTotalAmount(null);
        partner.setTotalOrderAmount(null);
        partner.setCashAmount(null);
        partner.setApplyAmount(null);
        partner.setBalanceAmount(null);
        partner.setLastModifyMan(memberInfo.getLoginCode());
        if (partner.getAlipay() != null && partner.getAlipay().contains("*")) {
            partner.setAlipay(null);
        }
        if (partner.getBankSn() != null && partner.getBankSn().contains("*")) {
            partner.setBankSn(null);
        }
        if (partner.getIdentityCard() != null && partner.getIdentityCard().contains("*")) {
            partner.setIdentityCard(null);
        }
        if (partner.getRealName() != null && partner.getRealName().contains("*")) {
            partner.setRealName(null);
        }
        if (partner.getLicenseNum() != null && partner.getLicenseNum().contains("*")) {
            partner.setLicenseNum(null);
        }
        partnerService.update(partner);
        partner = partnerService.findById(partner.getId());
        result.put("partner", partner.toJson());
        return result;
    }

    /**
     * 返利单列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/bill", method = RequestMethod.GET)
    public ResponseResult partnerBill(OrderSearcher searcher, Integer index, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getPartnerId() == null) {
            throw new BusinessException("您没有权限！");
        }
        if (index != null) {
            searcher.handleIndexForPartner(index);
        }
        page.setPageSize(10);
        if (searcher.getPartnerId() == null && searcher.getParentId() == null && searcher.getSuperId() == null
                && searcher.getMasterId() == null) {
            searcher.setPartnerId(memberInfo.getPartnerId());
        }
        PageResult<OrderItemDto> pager = orderItemService.findPartnerOrder(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(pb -> array.add(pb.toJson()));
        result.putPage("partnerBill", pager, array);
        Setting setting = settingService.findByCode(Setting.ORDERAFTERCLOSE);
        result.put("expireDay", Setting.defaultValue(setting, new Integer(7)));
        return result;
    }

    /**
     * 提现单列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/cash", method = RequestMethod.GET)
    public ResponseResult partnerCash(PartnerCashSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getPartnerId() == null) {
            throw new BusinessException("您没有权限！");
        }
        searcher.setPartnerId(memberInfo.getPartnerId());
        searcher.setWaitConfirm(1);
        PageResult<PartnerCash> pager = partnerCashService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(pc -> array.add(pc.toJson()));
        result.putPage("partnerCash", pager, array);
        return result;
    }

    /**
     * 流水明细列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/log", method = RequestMethod.GET)
    public ResponseResult partnerItem(PartnerItemSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getPartnerId() == null) {
            throw new BusinessException("您没有权限！");
        }
        searcher.setPartnerId(memberInfo.getPartnerId());
        PageResult<PartnerItem> pager = partnerItemService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(log -> array.add(log.toJson()));
        result.putPage("partnerLog", pager, array);
        return result;
    }

    /**
     * 最小提现金额
     *
     * @return
     */
    @RequestMapping(value = "/min/withdraw", method = RequestMethod.GET)
    public ResponseResult minWithdrawCash() {
        ResponseResult result = new ResponseResult();
        BigDecimal minWithdraw = new BigDecimal(0);
        result.put("minWithdraw", minWithdraw);
        return result;
    }

    /**
     * 本月已提现金额
     *
     * @param taxType
     * @return
     */
    @RequestMapping(value = "/month/withdraw", method = RequestMethod.GET)
    public ResponseResult monthWithdrawCash(Integer taxType) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner partner = partnerService.findById(memberInfo.getPartnerId());
        if (partner == null) {
            throw new BusinessException("您没有权限！");
        }
        Date today = new Date();
        Date sMonthDate = DateUtil.getStartOfMonth(today);
        Date eMonthDate = DateUtil.getEndOfMonth(today);
        BigDecimal monthWithdraw = partnerCashService.findWithCashByDate(partner.getId(), sMonthDate, eMonthDate,
                taxType);
        result.put("monthWithdraw", monthWithdraw);
        PartnerCash lastOne = partnerCashService.findLastSuccessOne(partner.getId(), sMonthDate, eMonthDate, taxType);
        result.put("taxAmount", lastOne == null ? new BigDecimal(0) : lastOne.getTotalTaxAmount());
        return result;
    }

    /**
     * 个人信息确认，工猫电签
     *
     * @param realName
     * @param identityCard
     * @param region
     * @param bank
     * @param bankType
     * @param bankSn
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gongmall/contract", method = RequestMethod.POST)
    public ResponseResult confirmContract(String realName, String identityCard, String region, String bank,
                                          String bankType, String bankSn) throws Exception {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner partner = partnerService.findById(memberInfo.getPartnerId());
        if (partner == null) {
            throw new BusinessException("您没有权限！");
        }
        if (identityCard != null && identityCard.contains("*")) {
            identityCard = partner.getIdentityCard();
        }
        if (realName != null && realName.contains("*")) {
            realName = partner.getRealName();
        }
        if (bankSn != null && bankSn.contains("*")) {
            bankSn = partner.getBankSn();
        }
        partner.setRegion(region);
        partner.setBank(bank);
        partner.setBankType(bankType);
        partner.setIdentityCard(identityCard);
        partner.setRealName(realName);
        partner.setBankSn(bankSn);
        partnerService.update(partner);
        if (partner.getContract() == 0) {
            Partner contracted = partnerService.findContract(identityCard);
            if (contracted != null) {
                throw new BusinessException("该身份证号已在工猫电签，请勿重复申请！");
            }
            String url = this.getContractUrl(gongmallConfig, partner.getRealName(), partner.getLoginCode(), "1",
                    partner.getIdentityCard(), partner.getBankSn(), partner.getBankType(), partner.getId().toString(),
                    "");
            result.put("url", URLEncoder.encode(url, "UTF-8"));
        }
        return result;
    }

    /**
     * 电签功能对接
     *
     * @param config
     * @param name
     * @param mobile
     * @param certificateType
     * @param idNumber
     * @param bankNum
     * @param bankName
     * @param workNumber
     * @param extraParam
     */
    private String getContractUrl(GongmallConfig config, String name, String mobile, String certificateType,
                                  String idNumber, String bankNum, String bankName, String workNumber, String extraParam) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("certificateType", certificateType);
        map.put("idNumber", idNumber);
        map.put("bankNum", bankNum);
        map.put("bankName", bankName);
        map.put("workNumber", workNumber);
        map.put("extraParam", extraParam);
        StringBuilder sb = new StringBuilder();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (StringUtil.isNotBlank(map.get(key))) {
                sb.append(key).append("=").append(map.get(key)).append("&");
            }
        }
        String dataTemp = sb.toString();
        String data = ASE.aesEncrypt(dataTemp.substring(0, dataTemp.length() - 1),
                MD5Util.encodeMD5Hex(config.getAppkey() + config.getAppSecret()).toUpperCase());
        return config.getContractUrl() + "&data=" + data;
    }

    /**
     * 更新个人银行卡信息
     *
     * @param realName
     * @param identityCard
     * @param region
     * @param bank
     * @param bankType
     * @param bankSn
     * @return
     */
    @RequestMapping(value = "/gongmall/update", method = RequestMethod.POST)
    public ResponseResult confirmUpdate(String realName, String identityCard, String region, String bank,
                                        String bankType, String bankSn) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner partner = partnerService.findById(memberInfo.getPartnerId());
        if (partner == null) {
            throw new BusinessException("您没有权限！");
        }
        if (identityCard != null && identityCard.contains("*")) {
            identityCard = partner.getIdentityCard();
        }
        if (realName != null && realName.contains("*")) {
            realName = partner.getRealName();
        }
        if (bankSn != null && bankSn.contains("*")) {
            bankSn = partner.getBankSn();
        }
        JSONObject json = GongmallClient.getInstance().doBankAccount(gongmallConfig, partner.getRealName(),
                partner.getLoginCode(), partner.getIdentityCard(), partner.getBankType(), partner.getBankSn(), bankType,
                bankSn);
        if (!json.getString("success").equals("true")) {
            throw new BusinessException(json.getString("errorMsg"));
        }
        partner.setRegion(region);
        partner.setBank(bank);
        partner.setBankType(bankType);
        partner.setIdentityCard(identityCard);
        partner.setRealName(realName);
        partner.setBankSn(bankSn);
        partnerService.update(partner);
        return result;
    }

    /**
     * 提现申请
     *
     * @param applyAmount  提现金额
     * @param applyAccount 提现账号
     * @param payType      提现方式
     * @return
     */
    @RequestMapping(value = "/withdraw/cash", method = RequestMethod.POST)
    public ResponseResult withdrawCash(BigDecimal applyAmount, String applyAccount, String payType) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner partner = partnerService.findById(memberInfo.getPartnerId());
        if (partner == null) {
            throw new BusinessException("您没有权限！");
        }
        if (partner.getStatus() < 0) {
            throw new BusinessException("用户状态异常！");
        }
        if (partner.getWithdraw() == 0) {
            throw new BusinessException("该账户提现功能被关闭！");
        }
        if (payType != null && !PayType.contain(payType)) {
            throw new BusinessException("提现方式异常！");
        }
        if (applyAmount.compareTo(new BigDecimal(0)) < 0) {
            throw new BusinessException("提现金额异常！");
        }
        if (applyAmount.compareTo(
                partner.getTotalAmount().subtract(partner.getCashAmount()).subtract(partner.getApplyAmount())) > 0) {
            throw new BusinessException("超出可提现金额！");
        }
        PartnerCash oldActive = partnerCashService.findActiveByMobile(partner.getLoginCode());
        if (oldActive != null && !payType.equals(PayType.wallet.name())
                && !oldActive.getPayType().equals(PayType.wallet.name())) {
            throw new BusinessException("您有一笔正在处理的提现单，请处理完后再申请！");
        }
        PartnerCash partnerCash = new PartnerCash(partner, payType);
        partnerCash.setApplyAccount(applyAccount);
        partnerCash.setApplyAmount(applyAmount);
        Integer taxType = payType.equals(PayType.wallet.name()) ? 1 : 0;
        partnerCash.setTaxType(taxType);
        Date today = new Date();
        Date sMonthDate = DateUtil.getStartOfMonth(today);
        Date eMonthDate = DateUtil.getEndOfMonth(today);
        BigDecimal monthWithdraw = partnerCashService.findWithCashByDate(partner.getId(), sMonthDate, eMonthDate,
                taxType);
        PartnerCash lastOne = partnerCashService.findLastSuccessOne(partner.getId(), sMonthDate, eMonthDate, taxType);
        BigDecimal taxAmount = new BigDecimal(0);
        if (taxType == 0) {
            int limit = TaxRule2StepEnum.STEP1.getOpenEnd();
            if (monthWithdraw.compareTo(new BigDecimal(0)) > 0 && monthWithdraw.compareTo(new BigDecimal(limit)) < 0) {
                if ((monthWithdraw.add(applyAmount)).compareTo(new BigDecimal(limit)) >= 0) {
                    throw new BusinessException("您本月最多可提现金额为" + limit + "元！");
                }
            }
            taxAmount = TaxRule2StepEnum.calculateTax(monthWithdraw.add(applyAmount));
        }
        partnerCash.setTotalTaxAmount(taxAmount);
        taxAmount = taxAmount.subtract(lastOne == null ? new BigDecimal(0) : lastOne.getTotalTaxAmount());
        partnerCash.setApplyTaxAmount(applyAmount.subtract(taxAmount));
        partnerCash = partnerTxService.doApplyCash(partnerCash);
        if (partnerCash.getPayType().equals(PayType.wallet.name())) {
            Map<String, Object> mq = new HashMap<>();
            mq.put("id", partnerCash.getId());
            mq.put("sn", partnerCash.getSn());
            MqEnum.PARTNER_CASH.send(mq, 3L);
        }
        return result;
    }

    /**
     * 根据类型分组<br/>
     * <p>
     * 数据结构（类型，总金额）<br/>
     *
     * @param partnerId
     * @return
     */
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public ResponseResult summaryItem(Long partnerId) {
        ResponseResult result = new ResponseResult();
        if (partnerId == null) {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            if (memberInfo.getPartnerId() == null) {
                throw new BusinessException("您没有权限！");
            }
            partnerId = memberInfo.getPartnerId();
        }
        Partner partner = partnerService.findById(partnerId);
        if (partner == null) {
            throw new BusinessException("买手数据不存在！");
        }
        result.put("partner", partner.toJson());
        List<Map<String, Object>> incomeData = partnerItemService.findSummaryByType(partner.getId());
        result.put("incomeData", incomeData);
        return result;
    }

    /**
     * 根据状态分组<br/>
     * <p>
     * 直接订单（数量，返利金额，实付金额）<br/>
     * 团队订单（数量，返利金额，实付金额）<br/>
     * 间接团队订单（数量，返利金额，实付金额）<br/>
     * AM订单（数量，返利金额，实付金额）<br/>
     *
     * @return
     */
    @RequestMapping(value = "/bill/summary", method = RequestMethod.GET)
    public ResponseResult summaryBill() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Partner partner = partnerService.findById(memberInfo.getPartnerId());
        if (partner == null) {
            throw new BusinessException("您没有权限！");
        }
        if (partner.getLevel() <= 0) {
            List<Map<String, Object>> masterData = partnerBillService.findBillSummary(partner.getId(), "master_id",
                    "master_ratio");
            result.put("masterData", masterData);
        }
        if (partner.getLevel() <= 1) {
            List<Map<String, Object>> superData = partnerBillService.findBillSummary(partner.getId(), "super_id",
                    "super_ratio");
            result.put("superData", superData);
            List<Map<String, Object>> parentData = partnerBillService.findBillSummary(partner.getId(), "parent_id",
                    "parent_ratio");
            result.put("parentData", parentData);
        }
        if (partner.getLevel() <= 2) {
            List<Map<String, Object>> partnerData = partnerBillService.findBillSummary(partner.getId(), "partner_id",
                    "partner_ratio");
            result.put("partnerData", partnerData);
        }
        return result;
    }

    /**
     * 根据等级分组<br/>
     * <p>
     * 当买手/DM进入数据中心时，是parent_id=DM 的买手/DM 人数<br/>
     * 当AM进入数据中心时，是master_id=AM 的买手/DM 人数<br/>
     *
     * @param partnerId
     * @return
     */
    @RequestMapping(value = "/children/summary", method = RequestMethod.GET)
    public ResponseResult summaryChildren(Long partnerId) {
        ResponseResult result = new ResponseResult();
        if (partnerId == null) {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            if (memberInfo.getPartnerId() == null) {
                throw new BusinessException("您没有权限！");
            }
            partnerId = memberInfo.getPartnerId();
        }
        Partner partner = partnerService.findById(partnerId);
        if (partner == null) {
            throw new BusinessException("您没有权限！");
        }
        Date now = new Date();
        if (partner.getLevel() == 0) {
            List<Map<String, Object>> amData = partnerService.countChildrenGroup(partner.getId(), "master_id");
            result.put("amData", amData);
            List<Map<String, Object>> todayData = partnerService.countChildrenToday(partner.getId(), "master_id",
                    DateUtil.getStartOfDay(now), DateUtil.getEndOfDay(now));
            result.put("todayData", todayData);
        } else if (partner.getLevel() == 1) {
            List<Map<String, Object>> dmData = partnerService.countChildrenGroup(partner.getId(), "parent_id");
            result.put("dmData", dmData);
            List<Map<String, Object>> todayData = partnerService.countChildrenToday(partner.getId(), "parent_id",
                    DateUtil.getStartOfDay(now), DateUtil.getEndOfDay(now));
            result.put("todayData", todayData);
        } else if (partner.getLevel() == 2) {
            List<Map<String, Object>> buyerData = partnerService.countChildrenGroup(partner.getId(), "parent_id");
            result.put("buyerData", buyerData);
            List<Map<String, Object>> todayData = partnerService.countChildrenToday(partner.getId(), "parent_id",
                    DateUtil.getStartOfDay(now), DateUtil.getEndOfDay(now));
            result.put("todayData", todayData);
        }
        return result;
    }

    /**
     * 我的店铺
     *
     * @return
     */
    @RequestMapping(value = "/store", method = RequestMethod.GET)
    public ResponseResult store(PageModel page, Long memberId, Long partnerId) {
        ResponseResult result = new ResponseResult();
        PartnerStore partnerStore = null;
        if (memberId == null && partnerId != null) {
            partnerStore = partnerStoreService.findByPartnerId(partnerId);
        } else if (memberId != null && partnerId == null) {
            partnerStore = partnerStoreService.findByMemberId(memberId);
        } else if (memberId == null && partnerId == null) {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            partnerStore = partnerStoreService.findByMemberId(memberInfo.getId());
        }
        if (partnerStore == null) {
            throw new BusinessException("您还未开通小店！");
        }
        result.put("partnerStore", partnerStore.toJson());
        return result;
    }

    /**
     * 更新店铺
     *
     * @param partnerStore
     * @return
     */
    @RequestMapping(value = "/update/store", method = RequestMethod.POST)
    public ResponseResult updateStore(PartnerStore partnerStore) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PartnerStore old = partnerStoreService.findByMemberId(memberInfo.getId());
        if (old == null) {
            throw new BusinessException("您还未开通小店！");
        }
        if (partnerStore.getId() == null || !old.getId().equals(partnerStore.getId())) {
            throw new BusinessException("不是您本人的小店！");
        }
        partnerStore.setMemberId(memberInfo.getId());
        partnerStore.setPartnerId(memberInfo.getPartnerId());
        partnerStoreService.update(partnerStore);
        return result;
    }

    /**
     * 是否选品
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/select/product", method = RequestMethod.GET)
    public ResponseResult selected(Long productId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PartnerStore partnerStore = partnerStoreService.findByMemberId(memberInfo.getId());
        if (partnerStore == null) {
            result.put("selected", 0);
            return result;
        }
        ProductPartnerRelation productPartnerRelation = productPartnerRelationService.findOne(partnerStore.getId(),
                productId);
        if (productPartnerRelation == null) {
            result.put("selected", 0);
            return result;
        }
        result.put("selected", 1);
        return result;
    }

    /**
     * 店铺选品
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/select/product", method = RequestMethod.POST)
    public ResponseResult selectProduct(Long productId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PartnerStore partnerStore = partnerStoreService.findByMemberId(memberInfo.getId());
        if (partnerStore == null) {
            throw new BusinessException("您还未开通小店！");
        }
        productPartnerRelationService.insert(partnerStore.getId(), productId);
        return result;
    }

    /**
     * 店铺取消选品
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/cancel/product", method = RequestMethod.POST)
    public ResponseResult cancelProduct(Long productId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        PartnerStore partnerStore = partnerStoreService.findByMemberId(memberInfo.getId());
        if (partnerStore == null) {
            throw new BusinessException("您还未开通小店！");
        }
        productPartnerRelationService.deleteOne(partnerStore.getId(), productId);
        return result;
    }

    /**
     * 店铺商品列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public ResponseResult productList(PageModel page, Long memberId, Long partnerId) {
        ResponseResult result = new ResponseResult();
        PartnerStore partnerStore = null;
        if (memberId == null && partnerId != null) {
            partnerStore = partnerStoreService.findByPartnerId(partnerId);
        } else if (memberId != null && partnerId == null) {
            partnerStore = partnerStoreService.findByMemberId(memberId);
        } else if (memberId == null && partnerId == null) {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            partnerStore = partnerStoreService.findByMemberId(memberInfo.getId());
        }
        if (partnerStore == null) {
            throw new BusinessException("您还未开通小店！");
        }
        PageResult<Product> productPager = productPartnerRelationService.findProductByStoreId(partnerStore.getId(),
                page);
        List<String> productIds = new ArrayList<>();
        productPager.getList().forEach(product -> productIds.add(product.getId().toString()));
        List<SearcherProduct> productList = productSearcherQueryService.findByIds(productIds, 1);
        JSONArray productArray = new JSONArray();
        productList.forEach(product -> productArray.add(product.toJson()));
        result.putPage("products", productPager, productArray);
        // 全局返利系数
        Setting ratio = settingService.findByCode(Setting.REBATERATIO);
        result.put("ratio", Setting.defaultValue(ratio, new Integer(1)));
        return result;
    }

    /**
     * 运营顾问
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/counselor/{id}", method = RequestMethod.GET)
    public ResponseResult counselor(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        PartnerCounselor partnerCounselor = partnerCounselorService.findById(id);
        if (partnerCounselor == null) {
            throw new BusinessException("运营顾问不存在！");
        }
        result.put("partnerCounselor", partnerCounselor.toJson());
        return result;
    }

    /**
     * 运营顾问列表
     *
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/counselor/list", method = RequestMethod.GET)
    public ResponseResult counselorList(PartnerCounselorSearcher searcher) {
        ResponseResult result = new ResponseResult();
        searcher.setStatus(1);
        PageResult<PartnerCounselor> pager = partnerCounselorService.findBySearcher(new PageModel(1, 10), searcher);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.put("counselors", array);
        return result;
    }

    /**
     * 选择运营顾问
     *
     * @param counselorId
     * @return
     */
    @RequestMapping(value = "/select/counselor", method = RequestMethod.POST)
    public ResponseResult selectCounselor(Long counselorId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getPartnerId() == null) {
            throw new BusinessException("您没有权限！");
        }
        PartnerCounselor partnerCounselor = partnerCounselorService.findById(counselorId);
        if (partnerCounselor == null) {
            throw new BusinessException("运营顾问不存在！");
        }
        partnerService.doBindCounselor(memberInfo.getPartnerId(), counselorId, memberInfo.getLoginCode());
        // couponDefGroupService.doClaimedCoupon(157L, memberInfo.getId(),
        // memberInfo.getLoginCode(),
        // memberInfo.getLoginCode(), CouponSourceEnum.APP.name());
        result.put("partnerCounselor", partnerCounselor);
        return result;
    }

    /**
     * 礼包商品列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/gift/list", method = RequestMethod.GET)
    public ResponseResult giftList(PageModel page) {
        ResponseResult result = new ResponseResult();
        ProductSearcher searcher = new ProductSearcher();
        searcher.setStatus(-1);
        searcher.setMark(1);
        PageResult<ProductDto> pager = productService.findBySearch(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson(null)));
        result.putPage("products", pager, array);
        return result;
    }

    /**
     * 我的礼包列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/package/list", method = RequestMethod.GET)
    public ResponseResult packageList(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getPartnerId() == null) {
            throw new BusinessException("您没有权限！");
        }
        Partner partner = partnerService.findById(memberInfo.getPartnerId());
        if (partner == null) {
            throw new BusinessException("分销商不存在！");
        }
        PartnerGiftSearcher searcher = new PartnerGiftSearcher();
        if (partner.getLevel() == 2) {
            searcher.setPartnerId(partner.getId());
        } else if (partner.getLevel() == 1) {
            searcher.setParentId(partner.getId());
        } else if (partner.getLevel() == 0) {
            searcher.setMasterId(partner.getId());
        }
        PageResult<PartnerGift> pager = partnerGiftService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson(partner.getId())));
        result.putPage("gifts", pager, array);
        return result;
    }

}
