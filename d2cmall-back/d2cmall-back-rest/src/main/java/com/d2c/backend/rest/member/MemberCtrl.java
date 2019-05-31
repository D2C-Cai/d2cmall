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
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.logger.service.EmailService;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.enums.MemberTypeEnum;
import com.d2c.member.model.*;
import com.d2c.member.model.DiscountSetting.DiscountType;
import com.d2c.member.query.CrmGroupSearcher;
import com.d2c.member.query.MemberSearcher;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.service.*;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/member/member")
public class MemberCtrl extends BaseCtrl<MemberSearcher> {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private DesignersService designersService;
    @Autowired
    private DistributorService distributorService;
    @Autowired
    private DiscountSettingService discountSettingService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberShareService memberShareService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Autowired
    private CrmGroupService crmGroupService;
    @Autowired
    private LiveRoomService liveRoomService;
    @Autowired
    private MemberDetailService memberDetailService;

    @Override
    protected List<Map<String, Object>> getRow(MemberSearcher searcher, PageModel page) {
        PageResult<MemberInfo> pager = memberInfoService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> cellsMap;
        PageModel crmPage = new PageModel();
        crmPage.setPageSize(PageModel.MAX_PAGE_SIZE);
        PageResult<CrmGroup> crmPager = crmGroupService.findBySearcher(crmPage, new CrmGroupSearcher());
        Map<String, String> crmMap = new HashMap<>();
        for (CrmGroup group : crmPager.getList()) {
            crmMap.put("crm_" + group.getId(), group.getName());
        }
        for (MemberInfo memberInfo : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("ID", memberInfo.getId());
            cellsMap.put("注册时间", memberInfo.getCreateDate() == null ? "" : sdf.format(memberInfo.getCreateDate()));
            cellsMap.put("账号", memberInfo.getLoginCode());
            cellsMap.put("会员昵称", memberInfo.getNickname());
            cellsMap.put("真实姓名", memberInfo.getLoginCode());
            cellsMap.put("性别", memberInfo.getSex());
            cellsMap.put("生日", memberInfo.getBirthday() == null ? "" : sdf.format(memberInfo.getBirthday()));
            cellsMap.put("手机", memberInfo.getMobile());
            cellsMap.put("邮箱", memberInfo.getEmail());
            cellsMap.put("crm接待小组", crmMap.get("crm_" + memberInfo.getCrmGroupId()));
            // TODO
            cellsMap.put("购买次数", "0");
            cellsMap.put("购买金额", "0");
            cellsMap.put("平均客单价", "0");
            cellsMap.put("最后登录时间", "0");
            cellsMap.put("最后购买时间", "0");
            cellsMap.put("登入次数", "0");
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(MemberSearcher searcher) {
        BeanUt.trimString(searcher);
        int count = memberInfoService.countBySearch(searcher); // 通过查询规则查找出对应的数量
        return count;
    }

    @Override
    protected String getFileName() {
        return "会员表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"ID", "账号", "真实姓名", "会员昵称", "性别", "手机", "邮箱", "生日", "购买次数", "购买金额", "平均客单价", "注册时间",
                "最后登录时间", "最后购买时间", "登入次数", "crm接待小组"};
    }

    @Override
    protected Response doHelp(MemberSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(MemberSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        if (searcher.getD2c() != null && searcher.getD2c() == 0) {
            PageResult<Member> pager = memberService.findBySearch(searcher, page);
            return new SuccessResponse(pager);
        }
        PageResult<MemberInfo> pager = memberInfoService.findBySearch(searcher, page);
        List<MemberDto> list = new ArrayList<>();
        for (MemberInfo info : pager.getList()) {
            MemberDto memberDto = new MemberDto();
            BeanUtils.copyProperties(info, memberDto);
            if (info.getDesignerId() != null) {
                Designers designers = designersService.findById(info.getDesignerId());
                memberDto.setDesignersName(designers.getName());
            }
            if (info.getCrmGroupId() != null) {
                CrmGroup crmGroup = crmGroupService.findById(info.getCrmGroupId());
                memberDto.setCrmName(crmGroup.getName());
            }
            MemberDetail detail = memberDetailService.findByMemberInfoId(info.getId());
            memberDto.setMemberDetail(detail);
            list.add(memberDto);
        }
        PageResult<MemberDto> pageResult = new PageResult<>();
        pageResult.setTotalCount(pager.getTotalCount());
        pageResult.setList(list);
        return new SuccessResponse(pageResult);
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = memberInfoService.findById(id);
        result.put("memberInfo", memberInfo);
        return result;
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
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        String rePassword = data.getString("rePassword");
        String password = data.getString("password");
        String loginCode = data.getString("loginCode");
        // 注册前校验
        String errorStr = loginService.validate(rePassword, password, loginCode);
        if (errorStr != null) {
            result.setStatus(-1);
            result.setMessage(errorStr);
            return result;
        }
        // 注册操作
        MemberInfo memberInfo;
        try {
            memberInfo = loginService.doRegist("86", loginCode, rePassword, MemberTypeEnum.D2CMall.name(), "PC",
                    InetAddress.getLocalHost().getHostAddress(), null, null, getUserAgent());
            result.put("member", memberInfo);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        data.remove("members");
        MemberInfo member = JsonUtil.instance().toObject(data, MemberInfo.class);
        SuccessResponse result = new SuccessResponse();
        if (member == null || id == null) {
            result.setStatus(-1);
            return result;
        }
        // 如果生日有个没填写就出错
        if (member.getBirthday() == null) {
            Date birthday = Calendar.getInstance().getTime();
            if (member != null) {
                member.setBirthday(birthday);
            }
        }
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setId(member.getId());
        memberInfo.setSex(member.getSex());
        memberInfo.setNickname(member.getDisplayName());
        memberInfo.setBirthday(member.getBirthday());
        memberInfo.setEmail(member.getEmail());
        memberInfo.setMobile(member.getMobile());
        memberInfo.setType(member.getType());
        try {
            memberInfoService.update(memberInfo);
            this.refreshMemberMQ(member);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            result.setStatus(-1);
        }
        return result;
    }

    private void refreshMemberMQ(MemberInfo member) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("headPic", member.getHeadPic());
        map.put("nickName", member.getNickname());
        map.put("memberInfoId", member.getId());
        MqEnum.REFRESH_MEMBER.send(map);
    }

    @Override
    protected String getExportFileType() {
        return "Member";
    }

    /**
     * 查看子账号
     *
     * @param memberInfoId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/child/{memberInfoId}", method = RequestMethod.POST)
    public Response child(@PathVariable("memberInfoId") Long memberInfoId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        try {
            List<Member> child = memberService.findByMemberInfoId(memberInfoId);
            result.put("data", child);
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 绑定经销商
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bind/distributor", method = RequestMethod.POST)
    public Response bindDistributor(Long[] id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        for (Long memberId : id) {
            Distributor distributor = distributorService.findByMemberInfoId(memberId);
            if (distributor == null) {
                MemberInfo memberInfo = memberInfoService.findById(memberId);
                distributor = new Distributor();
                distributor.setLoginCode(memberInfo.getLoginCode());
                distributor.setMemberId(memberInfo.getId());
                distributor = distributorService.insert(distributor);
                DiscountSetting discountSetting = new DiscountSetting();
                discountSetting.setDisType(DiscountType.ALL.toString());
                discountSetting.setDistributorId(distributor.getId());
                discountSetting.setStatus(1);
                discountSettingService.insert(discountSetting, admin.getUsername());
            } else if (distributor.getStatus() == 0) {
                distributorService.updateStatusById(distributor.getId(), 1, distributor.getMemberId());
            }
            memberInfoService.doBindDistributor(memberId, distributor.getId());
        }
        return result;
    }

    /**
     * 取消经销商
     *
     * @param ids
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bindDistributor/cancel", method = RequestMethod.POST)
    public Response cancelDistributor(Long[] id) throws NotLoginException {
        for (Long memberInfoId : id) {
            memberInfoService.doCancelDistributor(memberInfoId);
            Distributor distributor = distributorService.findByMemberInfoId(memberInfoId);
            distributorService.updateStatusById(distributor.getId(), 0, memberInfoId);
        }
        return new SuccessResponse();
    }

    /**
     * 绑定门店
     *
     * @param memberId
     * @param storeId
     * @return
     */
    @RequestMapping(value = "/bind/store", method = RequestMethod.POST)
    public Response bindStore(String memberId, Long storeId) {
        List<Long> ids = StringUtil.strToLongList(memberId);
        for (Long id : ids) {
            memberInfoService.doBindStore(id, storeId);
        }
        return new SuccessResponse();
    }

    /**
     * 取消门店
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/bindStore/cancel", method = RequestMethod.POST)
    public Response cancelStore(Long[] id) {
        for (Long memberInfoId : id) {
            memberInfoService.doCancelStore(memberInfoId);
        }
        return new SuccessResponse();
    }

    /**
     * 绑定设计师
     *
     * @param memberId
     * @param designerId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bind/designer", method = RequestMethod.POST)
    public Response bindDesigner(String memberId, Long designerId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        if (designerId == null) {
            result.setStatus(0);
            result.setMessage("未登录！操作不成功");
            return result;
        }
        String[] ids = memberId.split(",");
        Long[] memberIds = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            if (StringUtil.isBlack(ids[i])) {
                continue;
            }
            Long id = Long.parseLong(ids[i]);
            memberIds[i] = id;
            Designers designers = designersService.findById(designerId);
            if (designers != null && designers.getMemberId() != null) {
                result.setStatus(-1);
                result.setMessage("设计师：" + designers.getName() + "已绑定会员：" + designers.getLoginCode() + ",请先解绑");
                return result;
            }
            int success = memberInfoService.doBindDesigner(id, designerId, admin.getUsername());
            if (success > 0) {
                liveRoomService.doCreateLiveRoom(id);
            }
        }
        updateDesignerMemberShare(designerId, true, memberIds);
        result.setMessage("绑定成功！");
        return result;
    }

    /**
     * 取消设计师
     *
     * @param id
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bindDesigner/cancel", method = RequestMethod.POST)
    public Response cancelDesigner(Long[] id) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        int count = 0;
        int success = 0;
        for (Long memberInfoId : id) {
            success = memberInfoService.doCancelDesigner(memberInfoId);
            if (success > 0) {
                liveRoomService.doDeleteLiveRoom(memberInfoId);
            }
            count++;
        }
        updateDesignerMemberShare(null, false, id);
        result.setMessage("成功更新" + count + "条，失败" + (id.length - count) + "条");
        return result;
    }

    /**
     * 单个取消设计师
     *
     * @param memberId
     * @param designerId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bindDesigner/cancel/single", method = RequestMethod.POST)
    public Response cancelDesigner(Long memberId, Long designerId) throws NotLoginException {
        this.getLoginedAdmin();
        SuccessResponse successResponse = new SuccessResponse();
        int success = memberInfoService.doCancelDesigner(memberId);
        if (success > 0) {
            liveRoomService.doDeleteLiveRoom(memberId);
        } else {
            successResponse.setStatus(-1);
            successResponse.setMessage("操作不成功");
        }
        updateDesignerMemberShare(null, false, new Long[]{memberId});
        return successResponse;
    }

    /**
     * 刷新买家秀设计师ID
     *
     * @param designerId
     * @param isBind
     * @param ids
     */
    private void updateDesignerMemberShare(Long designerId, boolean isBind, Long[] ids) {
        Designers designers = null;
        if (isBind) {
            designers = designersService.findById(designerId);
        }
        if ((designers != null) || !isBind) {
            PageModel page = new PageModel();
            page.setPageSize(PageModel.MAX_PAGE_SIZE);
            List<Long> memberIds = new ArrayList<>();
            Collections.addAll(memberIds, ids);
            PageResult<SearcherMemberShare> pager = memberShareSearcherService.findByMemberIds(memberIds, page, null);
            for (SearcherMemberShare share : pager.getList()) {
                if (share.getDesignerId() == null && !isBind) {
                    continue;
                }
                memberShareService.updateDesignerId(share.getId(), isBind ? designers.getId() : 0L);
            }
        }
    }

    /**
     * 绑定CRM小组
     *
     * @param ids
     * @param crmReceptionGroupId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bindcrmreceptiongroup", method = RequestMethod.POST)
    public Response bindCrmGroup(Long[] ids, Long crmReceptionGroupId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        for (Long id : ids) {
            memberInfoService.doBindCrmGroup(id, crmReceptionGroupId);
        }
        result.setMessage("绑定成功！");
        return result;
    }

    /**
     * 取消CRM小组
     *
     * @param ids
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/cancel/bindcrmreceptiongroup", method = RequestMethod.POST)
    public Response cancelCrmGroup(Long[] ids) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        for (Long id : ids) {
            memberInfoService.doBindCrmGroup(id, null);
        }
        result.setMessage("解绑成功！");
        return result;
    }

    /**
     * 绑定门店顾客
     *
     * @param ids
     * @param storeGroupId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bind/customer", method = RequestMethod.POST)
    public Response bindCustomer(Long[] ids, Long storeGroupId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        for (Long id : ids) {
            memberInfoService.doBindCustomer(id, storeGroupId);
        }
        result.setMessage("绑定成功！");
        return result;
    }

    /**
     * 取消门店顾客
     *
     * @param ids
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bindCustomer/cancel", method = RequestMethod.POST)
    public Response cancelCustomer(Long[] ids) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        for (Long id : ids) {
            memberInfoService.doBindCustomer(id, null);
        }
        result.setMessage("解绑成功！");
        return result;
    }

    /**
     * 买家秀评价禁言
     *
     * @param memberInfoId
     * @param status
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/blockShareComment/{memberInfoId}", method = RequestMethod.POST)
    public Response restrictShareComment(@PathVariable("memberInfoId") Long memberInfoId, Integer status)
            throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        this.getLoginedAdmin();
        try {
            memberInfoService.doRestrictShareComment(memberInfoId, status);
            result.setMessage(status.intValue() == 1 ? "屏蔽成功！" : "取消屏蔽成功！");
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 设置会员类型
     *
     * @param ids
     * @param type
     * @return
     */
    @RequestMapping(value = "/bindmember/type/{type}", method = RequestMethod.POST)
    public Response updateMmeberType(Long[] ids, @PathVariable Integer type) {
        SuccessResponse result = new SuccessResponse();
        for (long id : ids) {
            MemberInfo memberInfo = memberInfoService.findById(id);
            if (memberInfo != null && memberInfo.getType().intValue() == 2) {
                result.setStatus(-1);
                result.setMessage("设计师账号不可修改标识！");
                return result;
            }
            int success = memberInfoService.updateType(id, type);
            // D2C官方的创建直播房间
            if (type == 3 && success > 0) {
                liveRoomService.doCreateLiveRoom(id);
            } else if (type != 3 && memberInfo.getType() == 3) {
                liveRoomService.doDeleteLiveRoom(id);
            }
            memberShareService.updateRoleByMemberId(id, type == 1 ? 0 : (type == 3 ? 2 : type));
        }
        return result;
    }

    /**
     * 进行实名认证
     *
     * @param realName
     * @param identityCard
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/update/realname", method = RequestMethod.POST)
    public Response updateRealName(Long id, String realName, String identityCard) {
        SuccessResponse result = new SuccessResponse();
        if (StringUtils.isEmpty(realName) || StringUtils.isEmpty(identityCard)) {
            result.setStatus(-1);
            result.setMsg("请输入真实姓名和身份证号！");
            return result;
        }
        memberInfoService.updateRealName(id, realName, identityCard);
        return result;
    }

    /**
     * 重置登录密码
     *
     * @param loginCode
     * @param password1
     * @param password2
     * @param code
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/reset/password", method = RequestMethod.POST)
    public Response resetPassword(String loginCode, String password1, String password2,
                                  @RequestParam(required = true) String code) {
        SuccessResponse result = new SuccessResponse();
        String message = loginService.doVerify(loginCode, code);
        if (!message.equalsIgnoreCase("OK")) {
            result.setStatus(-1);
            result.setMsg(message);
            return result;
        }
        if (!password1.equals(password2)) {
            result.setStatus(-1);
            result.setMsg("两次输入的密码不一致！");
            return result;
        }
        try {
            MemberInfo member = memberInfoService.findByLoginCode(loginCode);
            if (member != null) {
                member.setPassword(DigestUtils.md5Hex(password1));
                memberInfoService.updatePassword(member.getLoginCode(), member.getPassword());
            }
            loginService.createLoginLog("重置账号密码", loginCode, "重置账号密码成功，passwd:" + password1, getLoginIp(), "PC", null,
                    getUserAgent());
            result.setMsg("重置密码成功！");
        } catch (Exception e) {
            result.setStatus(-1);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 检验账号
     *
     * @param loginCode
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public Response check(String loginCode) {
        SuccessResponse result = new SuccessResponse();
        MemberInfo member = memberInfoService.findByLoginCode(loginCode);
        if (member == null) {
            result.setStatus(-1);
            result.setMsg("该账号未注册！");
            return result;
        }
        if (member.getDesignerId() == null) {
            result.setStatus(-1);
            result.setMsg("该账号不是设计师账号！");
            return result;
        }
        return result;
    }

    /**
     * 发送邮件
     *
     * @param memberId
     * @param toAll
     * @param subject
     * @param content
     * @return
     */
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public Response sendMail(String memberId, String toAll, String subject, String content) {
        if (StringUtil.hasBlack(new Object[]{subject, content})) {
            return new SuccessResponse();
        }
        if (StringUtils.isNotBlank(toAll)) {
            // 发送给所有会员
            int pageNumber = 1;
            PageResult<MemberInfo> pager = null;
            PageModel page = new PageModel();
            do {
                page.setPageNumber(pageNumber++);
                pager = memberInfoService.findBySearch(null, page);
                for (MemberInfo member : pager.getList()) {
                    if (member == null || StringUtils.isBlank(member.getEmail())) {
                        continue;
                    }
                    emailService.send(member.getEmail(), subject, content);
                }
            } while (pageNumber <= pager.getPageCount());
        } else {
            // 发送给指定id会员
            String[] ids = memberId.split(",");
            for (int i = 0; i < ids.length; i++) {
                Long id = ids[i] == null ? null : Long.parseLong(ids[i]);
                if (id == null) {
                    continue;
                }
                MemberInfo member = memberInfoService.findById(id);
                if (member == null || StringUtils.isBlank(member.getEmail())) {
                    continue;
                }
                emailService.send(member.getEmail(), subject, content);
            }
        }
        return new SuccessResponse();
    }

    /**
     * 修改手机号
     *
     * @param oldMobile
     * @param newMobile
     * @param nationCode
     * @return
     */
    @RequestMapping(value = "/change/mobile", method = RequestMethod.POST)
    public Response changeMobile(String oldMobile, String newMobile, String nationCode) {
        this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        MemberInfo old = memberInfoService.findByLoginCode(oldMobile);
        if (old == null) {
            result.setStatus(-1);
            result.setMsg("旧账号不存在！");
            return result;
        }
        MemberInfo neww = memberInfoService.findByLoginCode(newMobile);
        if (neww != null) {
            result.setStatus(-1);
            result.setMsg("新账号已存在！");
            return result;
        }
        memberInfoService.doChangeMobile(old.getId(), old.getLoginCode(), newMobile, nationCode,
                DeviceTypeEnum.APPIOS.name());
        return result;
    }

}
