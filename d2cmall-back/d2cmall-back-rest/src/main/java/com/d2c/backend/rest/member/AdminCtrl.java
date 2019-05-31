package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.logger.model.BossDevice;
import com.d2c.logger.service.BossDeviceService;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.dto.ResourceDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.AdminSearcher;
import com.d2c.member.query.ResourceSearcher;
import com.d2c.member.service.AdminService;
import com.d2c.member.service.LoginService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.ResourceService;
import com.d2c.product.service.BrandService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rest/security/admin")
public class AdminCtrl extends BaseCtrl<AdminSearcher> {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private BossDeviceService bossDeviceService;
    @Autowired
    private BrandService brandService;

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response adminLogin(String userName, String password) {
        Response response = null;
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            response = new ErrorResponse("请输入正确的帐号或者密码");
            return response;
        }
        Admin admin = adminService.findByUserName(userName);
        if (admin == null) {
            response = new ErrorResponse("请输入正确的帐号或者密码");
            return response;
        }
        if (admin.getIsAccountEnabled() == false) {
            response = new ErrorResponse("该帐号已停用");
            return response;
        }
        admin = adminLogin(userName, password, admin);
        if (admin == null) {
            logger.error("[管理员登陆] [单点登陆不成功] [名字:" + userName + "]");
            response = new ErrorResponse("请输入正确的帐号或者密码");
            return response;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("[管理员登陆] [单点] [名字:" + userName + "] [tgt:" + admin.getTgt() + "]");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("登陆session:" + getSession().getId());
        }
        response = new SuccessResponse(admin, "登陆成功");
        if (StringUtils.isEmpty(admin.getDepartment()) || StringUtils.isEmpty(admin.getMobile())
                || StringUtils.isEmpty(admin.getName())) {
            response.put("needed", 1);
        } else {
            response.put("needed", 0);
        }
        return response;
    }

    /**
     * 通过单点登陆方式，获取得到Admin
     *
     * @param userName
     * @param password
     * @param oldAdmin
     * @return
     */
    private Admin adminLogin(String userName, String password, Admin oldAdmin) {
        if (oldAdmin.getUsername().equals(userName) && MD5Util.encodeMD5Hex(password).equals(oldAdmin.getPassword())) {
            String tgt = UUID.randomUUID().toString();
            String loginIp = getLoginIp();
            Admin newAdmin = adminService.updateAdminTicket(oldAdmin.getId(), tgt, loginIp);
            logger.info("[管理员登陆] [单点登陆] [名称:" + userName + "] [票据:" + tgt + "] [更新数据库]");
            return newAdmin;
        }
        return null;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Response logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws NotLoginException {
        String tgt = httpRequest.getHeader("accesstoken");
        Admin admin = getLoginedAdmin();
        if (admin != null) {
            adminService.clearAdminTicket(tgt);
            adminService.updateAdminTicket(admin.getId(), "", null);
        }
        return new SuccessResponse();
    }

    /**
     * 门店登录
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/store/login", method = RequestMethod.POST)
    public Response storeLogin(String userName, String password) {
        Response response = null;
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            response = new ErrorResponse("用户名或密码不能为空");
            return response;
        }
        MemberInfo oldMember = memberInfoService.findByLoginCode(userName);
        if (oldMember == null) {
            response = new ErrorResponse("该手机号未注册");
            return response;
        }
        if (oldMember.getStoreId() == null) {
            response = new ErrorResponse("该手机号未绑定门店，请联系客服处理");
            return response;
        }
        if (!MD5Util.encodeMD5Hex(password).equals(oldMember.getPassword())) {
            response = new ErrorResponse("请输入正确的账号和密码");
            return response;
        }
        AdminDto admin = storeLogin(userName);
        if (admin == null) {
            logger.error("[门店管理员登陆] [单点登陆不成功] [名字:" + userName + "]");
            response = new ErrorResponse("请输入正确的帐号或者密码");
            return response;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("[门店管理员登陆] [单点] [名字:" + userName + "] [tgt:" + admin.getTgt() + "]");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("登陆session:" + getSession().getId());
        }
        response = new SuccessResponse(admin, "登陆成功");
        response.put("needed", 0);
        return response;
    }

    private AdminDto storeLogin(String userName) {
        String token = loginService.doLogin(userName, "PC", getLoginIp(), null, null, getUserAgent(), null);
        if (StringUtil.isNotBlank(token)) {
            MemberInfo member = memberInfoService.findByToken(token);
            if (member != null && member.getStoreId() != null) {
                logger.info("[门店管理员登陆] [单点登陆] [名称:" + userName + "] [票据:" + token + "] [更新数据库]");
                return this.getAdminDto(token, member);
            }
        }
        return null;
    }

    /**
     * 设计师登录
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/designer/login", method = RequestMethod.POST)
    public Response designerLogin(String userName, String password) {
        Response response = null;
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            response = new ErrorResponse("用户名或密码不能为空");
            return response;
        }
        MemberInfo oldMember = memberInfoService.findByLoginCode(userName);
        if (oldMember == null) {
            response = new ErrorResponse("该手机号未注册");
            return response;
        }
        if (oldMember.getDesignerId() == null) {
            response = new ErrorResponse("该手机号非设计师账号，请联系客服处理");
            return response;
        }
        if (!MD5Util.encodeMD5Hex(password).equals(oldMember.getPassword())) {
            response = new ErrorResponse("请输入正确的账号和密码");
            return response;
        }
        List<Long> brandIds = brandService.findIdsByDesignersId(oldMember.getDesignerId());
        if (brandIds == null || brandIds.size() == 0) {
            response = new ErrorResponse("您的账号尚未绑定任何品牌");
            return response;
        }
        AdminDto admin = designerLogin(userName);
        if (admin == null) {
            logger.error("[设计师登陆] [单点登陆不成功] [名字:" + userName + "]");
            response = new ErrorResponse("请输入正确的帐号或者密码");
            return response;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("[设计师登陆] [单点] [名字:" + userName + "] [tgt:" + admin.getTgt() + "]");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("登陆session:" + getSession().getId());
        }
        response = new SuccessResponse(admin, "登陆成功");
        response.put("needed", 0);
        return response;
    }

    /**
     * 设计师快速登录
     *
     * @param userName
     * @param code
     * @return
     */
    @RequestMapping(value = "/designer/quciklogin", method = RequestMethod.POST)
    public Response designerQuickLogin(String userName, String code) {
        Response response = null;
        String message = loginService.doVerify(userName, code);
        if (!message.equalsIgnoreCase("OK")) {
            response = new ErrorResponse(message);
            return response;
        }
        MemberInfo oldMember = memberInfoService.findByLoginCode(userName);
        if (oldMember == null) {
            response = new ErrorResponse("该手机号未注册");
            return response;
        }
        if (oldMember.getDesignerId() == null) {
            response = new ErrorResponse("该手机号非设计师账号，请联系客服处理");
            return response;
        }
        AdminDto admin = designerLogin(userName);
        if (admin == null) {
            logger.error("[设计师登陆] [单点登陆不成功] [名字:" + userName + "]");
            response = new ErrorResponse("请输入正确的帐号或者密码");
            return response;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("[设计师登陆] [单点] [名字:" + userName + "] [tgt:" + admin.getTgt() + "]");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("登陆session:" + getSession().getId());
        }
        response = new SuccessResponse(admin, "登陆成功");
        response.put("needed", 0);
        return response;
    }

    private AdminDto designerLogin(String userName) {
        String token = loginService.doLogin(userName, "PC", getLoginIp(), null, null, getUserAgent(), null);
        if (StringUtil.isNotBlank(token)) {
            MemberInfo member = memberInfoService.findByToken(token);
            if (member != null && member.getDesignerId() != null) {
                logger.info("[设计师登陆] [单点登陆] [名称:" + userName + "] [票据:" + token + "] [更新数据库]");
                return this.getAdminDto(token, member);
            }
        }
        return null;
    }

    @RequestMapping(value = "/designer/logout", method = RequestMethod.POST)
    public Response designerLogout(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws NotLoginException {
        String token = httpRequest.getHeader("accesstoken");
        MemberInfo member = memberInfoService.findByToken(token);
        if (member != null) {
            loginService.doLogOut(null, member.getLoginCode(), token, "PC", getLoginIp(), null, getUserAgent());
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/device", method = RequestMethod.POST)
    public Response updateDevice(@RequestParam(required = true) Long memberId, String app, String device,
                                 String deviceLabel, String platform, String clientId) {
        SuccessResponse result = new SuccessResponse();
        BossDevice md = new BossDevice();
        md.setMemberId(memberId);
        md.setApp(app);
        md.setVersion(device);
        md.setDevice(deviceLabel);
        md.setOs(platform);
        md.setIp(this.getLoginIp());
        md.setClientId(clientId);
        if (StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(app) && StringUtil.isNotBlank(device)) {
            bossDeviceService.doInsert(md);
        }
        return result;
    }

    @Override
    protected List<Map<String, Object>> getRow(AdminSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(AdminSearcher searcher) {
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
    protected Response doHelp(AdminSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        List<HelpDTO> admins = adminService.findAllForHelp();
        return new SuccessResponse(admins);
    }

    @Override
    protected Response doList(AdminSearcher searcher, PageModel page) {
        PageResult<AdminDto> pager = adminService.findBySearcher(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = adminService.findById(id);
        result.put("admin", admin);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        Admin admin = this.getLoginedAdmin();
        if (admin.getUsername().equalsIgnoreCase("admin")) {
            // TODO admin添加是否管理员权限
            int success = adminService.deleteById(id);
            if (success > 0) {
                return new SuccessResponse("删除成功");
            }
        }
        return new ErrorResponse("删除不成功");
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = JsonUtil.instance().toObject(data, Admin.class);
        Admin db = adminService.findByUserName(admin.getUsername());
        if (db != null) {
            result.setStatus(-1);
            result.setMessage("出现重复的用户名，请修改");
            return result;
        }
        admin.setPassword(MD5Util.encodeMD5Hex(admin.getPassword()));
        admin = adminService.insert(admin);
        result.put("admin", admin);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = JsonUtil.instance().toObject(data, Admin.class);
        Admin db = adminService.findByUserName(admin.getUsername());
        if (db != null && !admin.getId().equals(db.getId())) {
            result.setStatus(-1);
            result.setMessage("出现重复的用户名，请修改");
            return result;
        }
        adminService.update(admin);
        return result;
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    @RequestMapping(value = "/shownav", method = RequestMethod.POST)
    public Response showNav(HttpServletRequest request) {
        SuccessResponse response = new SuccessResponse();
        ResourceSearcher searcher = new ResourceSearcher();
        searcher.setInternal(0);
        searcher.setDepth(1);
        List<ResourceDto> first = resourceService.findBySearch(searcher);
        String tgt = request.getHeader("accesstoken");
        AdminDto admin = adminService.findAdminByTicket(tgt);
        List<String> roleValues = new ArrayList<>();
        if (admin != null) {
            roleValues.addAll(admin.getRoleValues());
        } else {
            MemberInfo member = memberInfoService.findByToken(tgt);
            if (member == null) {
            } else if (member.getDesignerId() != null) {
                roleValues.add("ROLE_DESIGNER");
            } else if (member.getStoreId() != null) {
                roleValues.add("ROLE_STORE");
            }
        }
        List<com.d2c.member.model.Resource> menus = resourceService.findByRoles(roleValues);
        for (ResourceDto temp : first) {
            // 遍历第一层
            for (com.d2c.member.model.Resource temp2 : menus) {
                // 遍历第二层
                if (temp2.getParentId() != null && temp2.getParentId().equals(temp.getId())) {
                    if (temp.getChildren() == null) {
                        temp.setChildren(new ArrayList<com.d2c.member.model.Resource>());
                    }
                    temp.getChildren().add(temp2);
                }
            }
        }
        response = new SuccessResponse(first);
        if (admin != null) {
            response.put("username", admin.getUsername());
        }
        return response;
    }

    @RequestMapping(value = "/updown/{status}", method = RequestMethod.POST)
    public Response status(HttpServletRequest request, Long id, @PathVariable Boolean status) throws Exception {
        SuccessResponse result = new SuccessResponse();
        Admin admin = adminService.findById(id);
        if (admin == null) {
            result.setStatus(-1);
            result.setMsg("该用户不存在");
        } else {
            admin.setIsAccountEnabled(status);
            adminService.update(admin);
            result.setMsg("操作成功");
        }
        return result;
    }

    @RequestMapping(value = "/lock/{status}", method = RequestMethod.POST)
    public Response lock(HttpServletRequest request, Long id, @PathVariable Boolean status) throws Exception {
        SuccessResponse result = new SuccessResponse();
        Admin admin = adminService.findById(id);
        if (admin == null) {
            result.setStatus(-1);
            result.setMsg("该用户不存在");
        } else {
            admin.setIsAccountLocked(status);
            adminService.update(admin);
            result.setMsg("操作成功");
        }
        return result;
    }

    @RequestMapping(value = "/myInfo", method = RequestMethod.POST)
    public Response myInfo() {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        result.put("admin", admin);
        return result;
    }

    @RequestMapping(value = "/update/info/{id}", method = RequestMethod.POST)
    public Response updateInfo(@PathVariable Long id) throws Exception {
        JSONObject data = this.getJSONObject();
        SuccessResponse result = new SuccessResponse();
        Admin admin = JsonUtil.instance().toObject(data, Admin.class);
        adminService.updateInfo(id, admin.getDepartment(), admin.getName(), admin.getMobile());
        return result;
    }

    @RequestMapping(value = "/updateUserPassword/{id}", method = RequestMethod.POST)
    public Response updatePassword(@PathVariable Long id, String newPassword1, String newPassword2,
                                   HttpServletResponse response) throws IOException {
        SuccessResponse result = new SuccessResponse();
        Admin user = this.adminService.findById(id);
        if (!newPassword1.equals(newPassword2)) {
            result.setMessage("新密码两次输入不一样！");
            result.setStatus(-1);
            return result;
        }
        if (adminService.updatePassword(user.getId(), MD5Util.encodeMD5Hex(newPassword1)) == 1) {
            result.setMessage("密码修改成功！");
        } else {
            result.setMessage("密码修改不成功！");
            result.setStatus(-1);
        }
        return result;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public Response updateMyPassword(String oldPassword, String newPassword1, String newPassword2,
                                     HttpServletResponse response) throws IOException {
        SuccessResponse result = new SuccessResponse();
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword1)
                || StringUtils.isBlank(newPassword2)) {
            result.setStatus(-1);
            result.setMessage("请填写密码！");
        }
        if (!newPassword1.equals(newPassword2)) {
            result.setStatus(-1);
            result.setMessage("新密码两次输入不一样！");
        }
        Admin admin = this.getLoginedAdmin();
        if (adminService.updateMyPassword(admin.getUsername(), MD5Util.encodeMD5Hex(oldPassword),
                MD5Util.encodeMD5Hex(newPassword1)) == 1) {
            result.setMessage("密码修改成功！");
        } else {
            result.setStatus(-1);
            result.setMessage("密码修改不成功！");
        }
        return result;
    }

}
