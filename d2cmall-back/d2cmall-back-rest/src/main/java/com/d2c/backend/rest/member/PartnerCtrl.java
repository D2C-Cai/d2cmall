package com.d2c.backend.rest.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.logger.model.PartnerLog;
import com.d2c.logger.service.PartnerLogService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Partner;
import com.d2c.member.query.PartnerSearcher;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.PartnerService;
import com.d2c.order.model.PartnerItem.PartnerLogType;
import com.d2c.order.model.PartnerWithhold;
import com.d2c.order.service.PartnerBillService;
import com.d2c.order.service.PartnerWithholdService;
import com.d2c.order.service.tx.PartnerTxService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/partner")
public class PartnerCtrl extends BaseCtrl<PartnerSearcher> {

    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerBillService partnerBillService;
    @Autowired
    private PartnerLogService partnerLogService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private PartnerWithholdService partnerWithholdService;
    @Reference
    private PartnerTxService partnerTxService;

    @Override
    protected Response doList(PartnerSearcher searcher, PageModel page) {
        PageResult<Partner> pager = partnerService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        List<String> tags = partnerService.findTags();
        result.put("tagsList", tags);
        return result;
    }

    @Override
    protected int count(PartnerSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(PartnerSearcher searcher, PageModel page) {
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
    protected Response doHelp(PartnerSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Partner partner = partnerService.findById(id);
        result.put("partner", partner);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        Partner partner = (Partner) JsonUtil.instance().toObject(data, Partner.class);
        partner.setTotalAmount(null);
        partner.setApplyAmount(null);
        partner.setCashAmount(null);
        partner.setTotalOrderAmount(null);
        partner.setBalanceAmount(null);
        partner.setLastModifyMan(admin.getUsername());
        partnerService.update(partner);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        AdminDto admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        Partner partner = (Partner) JsonUtil.instance().toObject(data, Partner.class);
        MemberInfo member = memberInfoService.findByLoginCode(partner.getLoginCode());
        if (member == null) {
            result.setStatus(-1);
            result.setMsg("账号不存在！");
            return result;
        }
        if (partnerService.findByLoginCode(partner.getLoginCode()) != null) {
            result.setStatus(-1);
            result.setMsg("该分销商已存在！");
            return result;
        }
        partner = new Partner(member, partner.getLevel());
        if (partner.getLevel() == 2) {
            // 试用期30天
            partner.setExpiredDate(DateUtil.getIntervalDay(new Date(), 30));
        }
        partner.setName(member.getDisplayName());
        partner.setHeadPic(member.getHeadPic());
        partner.setLoginCode(member.getLoginCode());
        partner.setMemberId(member.getId());
        partner.setContract(0);
        partner.setTotalAmount(new BigDecimal(0));
        partner.setCashAmount(new BigDecimal(0));
        partner.setTotalOrderAmount(new BigDecimal(0));
        partner.setCreator(admin.getUsername());
        partner = partnerService.insert(partner);
        result.put("partner", partner);
        return result;
    }

    @Override
    protected Response doDelete(Long id) {
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 预存礼包
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/prestore/{id}", method = RequestMethod.POST)
    public Response updatePrestore(@PathVariable Long id, Integer count) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (count == 0) {
            partnerService.cancelPrestore(id, admin.getUsername());
        } else if (count > 0) {
            partnerService.updatePrestore(id, count, admin.getUsername());
        }
        return result;
    }

    /**
     * 更改等级
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/level/{id}", method = RequestMethod.POST)
    public Response updateLevel(@PathVariable Long id, Integer level) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        if (level == null || level < 0 || level > 2) {
            result.setStatus(-1);
            result.setMsg("等级的值异常！");
            return result;
        }
        Partner partner = partnerService.findById(id);
        if (partner.getLevel() > level && partner.getUpgrade() == 0) {
            result.setStatus(-1);
            result.setMsg("该账号不允许升级！");
            return result;
        }
        partnerService.updateLevel(id, level, admin.getUsername());
        return result;
    }

    /**
     * 绑定门店标志
     *
     * @param mark
     * @param ids
     * @return
     */
    @RequestMapping(value = "/bind/storemark/{mark}", method = RequestMethod.POST)
    public Response updateStatus(@PathVariable Integer mark, Long[] ids) {
        SuccessResponse response = new SuccessResponse();
        for (Long id : ids) {
            partnerService.doBindStoreMark(id, mark);
        }
        return response;
    }

    /**
     * 选择运营顾问
     *
     * @param id
     * @param counselorId
     * @return
     */
    @RequestMapping(value = "/select/counselor", method = RequestMethod.POST)
    public Response selectCounselor(Long id, Long counselorId) {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        partnerService.doBindCounselor(id, counselorId, admin.getUsername());
        return result;
    }

    /**
     * 根据状态分组<br/>
     * 直接订单（数量，返利金额，实付金额）<br/>
     * 团队订单（数量，返利金额，实付金额）<br/>
     * 间接团队订单（数量，返利金额，实付金额）<br/>
     * AM订单（数量，返利金额，实付金额）<br/>
     *
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/summary/{id}", method = RequestMethod.GET)
    public Response summary(@PathVariable Long id) throws NotLoginException {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        Partner partner = partnerService.findById(id);
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
     * 分销商操作日志
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response logList(@PathVariable Long id, PageModel page) {
        PageResult<PartnerLog> logList = partnerLogService.findByPartnerId(id, page);
        return new SuccessResponse(logList);
    }

    /**
     * 导入门店退款表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import/withhold", method = RequestMethod.POST)
    public Response importWithhold(HttpServletRequest request) {
        Admin admin = this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String sn = map.get("编号（建议使用日期格式+两位数字，用于判断是否重复导入）") != null
                        ? String.valueOf(map.get("编号（建议使用日期格式+两位数字，用于判断是否重复导入）")) : null;
                String loginCode = map.get("D2C账号") != null ? String.valueOf(map.get("D2C账号")) : null;
                BigDecimal amount = map.get("金额（-）") != null ? new BigDecimal(map.get("金额（-）").toString()) : null;
                String memo = map.get("备注") != null ? map.get("D2C账号").toString() : "";
                if ((StringUtils.isBlank(loginCode) || StringUtils.isBlank(sn))) {
                    errorMsg.append("数据异常，编号:" + sn + "，错误原因：编号和D2C账号不能为空<br/>");
                    return false;
                }
                if (amount == null || amount.compareTo(new BigDecimal(0)) < 0) {
                    errorMsg.append("数据异常，编号:" + sn + "，错误原因：导入金额不能小于0<br/>");
                    return false;
                }
                Partner partner = partnerService.findByLoginCode(loginCode);
                if (partner == null) {
                    errorMsg.append("D2C账号：" + loginCode + "，错误原因：分销商未找到<br/>");
                    return false;
                }
                if (partner.getStoreMark() == 0) {
                    errorMsg.append("D2C账号：" + loginCode + "，错误原因：该分销商没有绑定门店标识<br/>");
                    return false;
                }
                if (partnerWithholdService.findBySn(sn) != null) {
                    errorMsg.append("数据异常，编号:" + sn + "，错误原因：该编号扣款已经导入<br/>");
                    return false;
                }
                PartnerWithhold partnerWithhold = new PartnerWithhold();
                partnerWithhold.setCreator(admin.getUsername());
                partnerWithhold.setPartnerId(partner.getId());
                partnerWithhold.setAmount(amount);
                partnerWithhold.setSn(sn);
                partnerWithhold.setMemo(memo);
                partnerWithholdService.insert(partnerWithhold);
                return true;
            }
        });
    }

    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public Response partnerTag() {
        SuccessResponse result = new SuccessResponse();
        List<String> tags = partnerService.findTags();
        result.put("tagsList", tags);
        return result;
    }

    /**
     * 导入标签分销表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import/tags/relation", method = RequestMethod.POST)
    public Response importPartnerRelation(HttpServletRequest request) {
        this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String loginCode = map.get("D2C账号") != null ? String.valueOf(map.get("D2C账号")) : null;
                String tags = map.get("标签") != null ? map.get("标签").toString() : null;
                if ((StringUtils.isBlank(loginCode))) {
                    errorMsg.append("数据异常，错误原因：D2C账号不能为空<br/>");
                    return false;
                }
                if (StringUtils.isBlank(tags)) {
                    tags = null;
                }
                Partner partner = partnerService.findByLoginCode(loginCode);
                if (partner == null) {
                    errorMsg.append("D2C账号：" + loginCode + "，错误原因：分销商未找到<br/>");
                    return false;
                }
                partnerService.updateTags(partner.getId(), tags);
                return true;
            }
        });
    }

    @RequestMapping(value = "/withhold", method = RequestMethod.POST)
    public Response withhold(Long partnerId, BigDecimal amount, String memo) throws Exception {
        SuccessResponse result = new SuccessResponse();
        if (partnerId == null) {
            throw new BusinessException("分销ID不能为空");
        }
        if (amount == null || amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new BusinessException("扣款金额不能小于等于0");
        }
        Admin admin = this.getLoginedAdmin();
        PartnerWithhold partnerWithhold = new PartnerWithhold();
        partnerWithhold.setCreator(admin.getUsername());
        partnerWithhold.setPartnerId(partnerId);
        partnerWithhold.setAmount(amount);
        String sn = String.valueOf(System.currentTimeMillis());
        partnerWithhold.setSn(sn);
        partnerWithhold.setMemo(memo);
        partnerTxService.doWithhold(partnerWithhold, PartnerLogType.DIFF);
        return result;
    }

    @RequestMapping(value = "/rebate", method = RequestMethod.POST)
    public Response withhold(Long partnerId, BigDecimal amount) throws Exception {
        SuccessResponse result = new SuccessResponse();
        if (partnerId == null) {
            throw new BusinessException("分销ID不能为空");
        }
        if (amount == null || amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new BusinessException("奖励金额不能小于等于0");
        }
        Admin admin = this.getLoginedAdmin();
        partnerTxService.doRebate(partnerId, amount, new BigDecimal(0), admin.getUsername(), partnerId,
                String.valueOf(System.currentTimeMillis()), PartnerLogType.BILL);
        return result;
    }

    /**
     * 是否允许升级
     *
     * @param status
     * @param ids
     * @return
     */
    @RequestMapping(value = "/upgrade/{status}", method = RequestMethod.POST)
    public Response upgrade(@PathVariable Integer status, Long[] ids) {
        SuccessResponse response = new SuccessResponse();
        for (Long id : ids) {
            partnerService.updateUpgrade(id, status);
        }
        return response;
    }

    /**
     * 是否允许提现
     *
     * @param status
     * @param ids
     * @return
     */
    @RequestMapping(value = "/withdraw/{status}", method = RequestMethod.POST)
    public Response withdraw(@PathVariable Integer status, Long[] ids) {
        SuccessResponse response = new SuccessResponse();
        for (Long id : ids) {
            partnerService.updateWithdraw(id, status);
        }
        return response;
    }

}
