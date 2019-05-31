package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.controller.behavior.EventController;
import com.d2c.flame.convert.MemberInfoVOConvert;
import com.d2c.logger.model.RecommendLog;
import com.d2c.logger.query.RecommendLogSearcher;
import com.d2c.logger.service.RecommendLogService;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.dto.MemberInfoDto;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.enums.MemberTypeEnum;
import com.d2c.member.enums.SignRewardEnum;
import com.d2c.member.model.*;
import com.d2c.member.query.MemberDailySignSearcher;
import com.d2c.member.query.MemberShareSearcher;
import com.d2c.member.query.PiliLiveSearcher;
import com.d2c.member.search.model.SearcherMemberFollow;
import com.d2c.member.search.model.SearcherMemberLike;
import com.d2c.member.search.model.SearcherMemberShare;
import com.d2c.member.search.query.MemberShareSearchBean;
import com.d2c.member.search.service.MemberFollowSearcherService;
import com.d2c.member.search.service.MemberLikeSearcherService;
import com.d2c.member.search.service.MemberShareSearcherService;
import com.d2c.member.service.*;
import com.d2c.member.view.MemberInfoVO;
import com.d2c.order.service.tx.AccountTxService;
import com.d2c.product.model.Brand;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.BrandService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.LoginUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

/**
 * 会员
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/member")
public class MemberController extends BaseController {

    private static final String TEST_NUM = "19999999999";
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private BrandService brandService;
    @Reference
    private MemberFollowSearcherService memberFollowSearcherService;
    @Reference
    private MemberShareSearcherService memberShareSearcherService;
    @Autowired
    private RecommendLogService recommendLogService;
    @Autowired
    private DesignersService designersService;
    @Reference
    private MemberLikeSearcherService memberLikeSearcherService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private PiliLiveService piliLiveService;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private EventController eventController;
    @Autowired
    private MemberInfoVOConvert memberInfoVOConvert;
    @Reference
    private AccountTxService accountTxService;
    @Autowired
    private MemberDailySignService memberDailySignService;
    @Autowired
    private MemberDetailService memberDetailService;

    /**
     * 会员交易统计信息
     * <p>
     * API: /v3/api/member/detail/{memberInfoId}
     */
    @RequestMapping(value = "/detail/{memberInfoId}", method = RequestMethod.GET)
    public Response findMemberStat(@PathVariable Long memberInfoId) {
        getLoginMemberInfo();
        MemberInfo memberInfo = memberInfoService.findById(memberInfoId);
        MemberInfoVO view = memberInfoVOConvert.convert(memberInfo);
        return ResultHandler.success(view);
    }

    /**
     * 更新会员信息
     *
     * @param sex        性别
     * @param nickname   昵称
     * @param birthDay   生日
     * @param email      邮箱
     * @param headPic    头像
     * @param background 背景图
     * @return
     */
    @RequestMapping(value = "/update/info", method = RequestMethod.POST)
    public ResponseResult updateInfo(String sex, String nickname, String birthDay, String email, String headPic,
                                     String background) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        memberInfo.setSex(sex);
        if (StringUtil.isNotBlank(nickname)) {
            // 过滤敏感词汇
            this.checkSensitiveWords(nickname);
            memberInfo.setNickname(nickname);
        }
        if (StringUtil.isNotBlank(birthDay)) {
            memberInfo.setBirthday(DateUtil.str2day2(birthDay));
        }
        memberInfo.setEmail(email);
        memberInfo.setHeadPic(headPic);
        memberInfo.setBackground(background);
        memberInfoService.update(memberInfo);
        // 清缓存
        this.refreshMemberMQ(memberInfo);
        return result;
    }

    private void refreshMemberMQ(MemberInfo member) {
        Map<String, Object> map = new HashMap<>();
        map.put("headPic", member.getHeadPic());
        map.put("nickName", member.getNickname());
        map.put("memberInfoId", member.getId());
        MqEnum.REFRESH_MEMBER.send(map);
    }

    /**
     * 手机认证
     *
     * @param mobile
     * @param code
     * @return
     */
    @RequestMapping(value = "/mobile", method = RequestMethod.POST)
    public ResponseResult mobile(@RequestParam(required = true) String mobile,
                                 @RequestParam(required = true) String code) {
        ResponseResult result = new ResponseResult();
        String message = loginService.doVerify(mobile, code);
        if (!message.equalsIgnoreCase("OK")) {
            throw new BusinessException(message);
        }
        return result;
    }

    /**
     * 注册时验证账号是否已存在
     *
     * @param loginCode
     * @return
     */
    @RequestMapping(value = "/check/register", method = RequestMethod.POST)
    public ResponseResult checkRegister(@RequestParam(required = true) String loginCode) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        if (memberInfo != null) {
            throw new BusinessException("该手机号已注册！");
        }
        return result;
    }

    /**
     * 用户是否已注册
     *
     * @param loginCode
     * @return
     */
    @RequestMapping(value = "/check/exist", method = RequestMethod.POST)
    public ResponseResult checkExist(@RequestParam(required = true) String loginCode) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = null;
        if (loginCode.contains("*")) {
            memberInfo = this.getLoginMemberInfo();
        } else {
            memberInfo = memberInfoService.findByLoginCode(loginCode);
        }
        if (memberInfo == null) {
            throw new BusinessException("该手机号未注册！");
        }
        result.put("nationCode", memberInfo.getNationCode());
        return result;
    }

    /**
     * 进行实名认证
     *
     * @param realName
     * @param identityCard
     * @return
     */
    @RequestMapping(value = "/update/realname", method = RequestMethod.POST)
    public ResponseResult updateName(String realName, String identityCard) {
        ResponseResult result = new ResponseResult();
        MemberInfo member = this.getLoginMemberInfo();
        if (StringUtils.isEmpty(realName) || StringUtils.isEmpty(identityCard)) {
            throw new BusinessException("请输入真实姓名和身份证号！");
        }
        memberInfoService.updateRealName(member.getId(), realName, identityCard);
        return result;
    }

    /**
     * 个人主页
     *
     * @param page
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ResponseResult homePage(PageModel page, Long memberId) {
        ResponseResult result = new ResponseResult();
        MemberShareSearcher searcher = new MemberShareSearcher();
        // 用户信息
        MemberInfo memberInfo = null;
        Long myMemberId = 0L;
        if (memberId == null || memberId <= 0) {
            memberInfo = this.getLoginMemberInfo();
            memberId = memberInfo.getId();
        } else {
            memberInfo = memberInfoService.findById(memberId);
            try {
                myMemberId = this.getLoginMemberInfo().getId();
                if (!memberId.equals(myMemberId)) {
                    searcher.setStatus(1);
                }
            } catch (NotLoginException e) {
                searcher.setStatus(1);
            }
        }
        JSONObject json = memberInfo.toJson();
        if (memberInfo.getDesignerId() != null && memberInfo.getDesignerId() > 0) {
            Designers designers = designersService.findById(memberInfo.getDesignerId());
            if (designers != null) {
                json.putAll(designers.toJsonMap());
            }
        }
        result.put("member", json);
        // 买家秀列表
        searcher.setMemberId(memberId);
        PageResult<SearcherMemberShare> pager = memberShareSearcherService.search(searcher.initSearchQuery(), page);
        JSONArray arry = new JSONArray();
        List<SearcherMemberShare> memberShares = pager.getList();
        MemberInfo mymemberInfo = null;
        try {
            mymemberInfo = this.getLoginMemberInfo();
            String[] ids = new String[memberShares.size()];
            for (int i = 0; i < memberShares.size(); i++) {
                ids[i] = mymemberInfo.getId() + "_" + memberShares.get(i).getId();
            }
            Map<Long, SearcherMemberLike> memberLikes = memberLikeSearcherService.findByIds(ids);
            for (SearcherMemberShare share : pager.getList()) {
                Long shareId = share.getId();
                if (memberLikes.containsKey(shareId)) {
                    share.setLiked(1);
                } else {
                    share.setLiked(0);
                }
                arry.add(share.toJson());
            }
        } catch (NotLoginException e) {
            for (SearcherMemberShare share : pager.getList()) {
                share.setLiked(0);
                arry.add(share.toJson());
            }
        }
        result.putPage("memberShares", pager, arry);
        // 直播列表
        PiliLiveSearcher liveSearcher = new PiliLiveSearcher();
        liveSearcher.setMemberId(memberId);
        liveSearcher.setMark(1);
        PageResult<PiliLive> livePager = piliLiveService.findBySearcher(liveSearcher, page);
        List<PiliLive> lives = livePager.getList();
        JSONArray array = new JSONArray();
        lives.forEach(item -> array.add(item.toJson()));
        result.putPage("lives", pager, array);
        return result;
    }

    /**
     * 统计信息
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/info/{memberId}", method = RequestMethod.GET)
    public ResponseResult homeInfo(@PathVariable Long memberId) {
        ResponseResult result = new ResponseResult();
        MemberShareSearchBean search = new MemberShareSearchBean();
        // 用户信息
        MemberInfo memberInfo = null;
        Long myMemberId = 0L;
        if (memberId == null || memberId <= 0) {
            memberInfo = this.getLoginMemberInfo();
            memberId = memberInfo.getId();
        } else {
            memberInfo = memberInfoService.findById(memberId);
            try {
                myMemberId = this.getLoginMemberInfo().getId();
                if (!memberId.equals(myMemberId)) {
                    search.setStatus(1);
                }
            } catch (NotLoginException e) {
                search.setStatus(1);
            }
        }
        JSONObject json = memberInfo.toJson();
        if (memberInfo.getDesignerId() != null && memberInfo.getDesignerId() > 0) {
            Designers designers = designersService.findById(memberInfo.getDesignerId());
            if (designers != null) {
                json.putAll(designers.toJsonMap());
            }
        }
        result.put("member", json);
        // 统计信息
        search.setMemberId(memberId);
        int sharesTotalCount = memberShareSearcherService.count(search);
        int fansTotalCount = memberFollowSearcherService.countByToId(memberId, null);
        int followsTotalCount = memberFollowSearcherService.countByFromId(memberId);
        result.put("sharesTotalCount", sharesTotalCount);
        result.put("fansTotalCount", fansTotalCount);
        result.put("followsTotalCount", followsTotalCount);
        if (myMemberId > 0) {
            SearcherMemberFollow searcherMemberFollow = memberFollowSearcherService
                    .findById(myMemberId + "_" + memberId);
            result.put("follow", searcherMemberFollow == null ? SearcherMemberFollow.FollowType.UNFOLLOW.getCode()
                    : searcherMemberFollow.getFollowType());
        } else {
            result.put("follow", SearcherMemberFollow.FollowType.UNFOLLOW.getCode());
        }
        // 设计师信息
        JSONArray brandArray = new JSONArray();
        if (memberInfo.getDesignerId() != null && memberInfo.getDesignerId() > 0) {
            List<Brand> brands = brandService.findByDesignersId(memberInfo.getDesignerId(), new Integer[]{1});
            brands.forEach(brand -> {
                JSONObject obj = brand.toSimpleJson();
                obj.put("salesProductCount", productSearcherQueryService.countSaleProductByDesigner(brand.getId()));
                brandArray.add(obj);
            });
        }
        result.put("brands", brandArray);
        return result;
    }

    /**
     * 获取该账号的游客
     *
     * @return
     */
    @RequestMapping(value = "/child/list", method = RequestMethod.GET)
    public ResponseResult childList() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        List<Member> children = memberService.findByMemberInfoId(memberInfo.getId());
        JSONArray array = new JSONArray();
        children.forEach(item -> array.add(item.toJson()));
        result.put("children", array);
        return result;
    }

    /**
     * 用户注册（旧）
     *
     * @param password
     * @param rePassword
     * @param nationCode
     * @param loginCode
     * @param appTerminal
     * @param appVersion
     * @param parent_id
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseResult register(@RequestParam(required = true) String password,
                                   @RequestParam(required = true) String rePassword, @RequestParam(required = true) String nationCode,
                                   @RequestParam(required = true) String loginCode, String appTerminal, String appVersion, Long parent_id) {
        ResponseResult result = new ResponseResult();
        // 注册前校验
        String errorStr = loginService.validate(password, rePassword, loginCode);
        if (errorStr != null) {
            throw new BusinessException(errorStr);
        }
        AssertUt.isPassword(password);
        // 推荐人校验
        if (parent_id != null) {
            Partner partner = partnerService.findById(parent_id);
            if (partner == null || partner.getStatus() < 0) {
                parent_id = null;
            }
        }
        // 注册操作
        MemberInfoDto memberInfo = loginService.doRegist(nationCode, loginCode, rePassword,
                MemberTypeEnum.D2CMall.name(), DeviceTypeEnum.divisionDevice(appTerminal), getLoginIp(), parent_id,
                appVersion, null);
        if (memberInfo != null) {
            MemberDto dto = new MemberDto();
            BeanUtils.copyProperties(memberInfo, dto);
            JSONObject json = dto.toJson(memberInfo.getToken());
            if (dto.isD2c()) {
                json = memberInfoService.findMemberDetail(memberInfo.getId(), memberInfo.getDesignerId(), json);
            }
            result.put("member", json);
        } else {
            throw new BusinessException("用户注册不成功！");
        }
        return result;
    }

    /**
     * 用户登录（旧）
     *
     * @param loginCode
     * @param password
     * @param loginDevice
     * @param code
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(@RequestParam(required = true) String loginCode,
                                @RequestParam(required = true) String password, String loginDevice, String code, String appTerminal,
                                String appVersion) {
        ResponseResult result = new ResponseResult();
        // 账号密码
        loginCode = loginCode.toLowerCase();
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        if (memberInfo == null) {
            throw new BusinessException("用户名不存在！");
        }
        if (!DigestUtils.md5Hex(password).equals(memberInfo.getPassword())) {
            loginService.createLoginLog("用户登录失败", loginCode, "用户登录失败，passwd:" + password + "， 原因用户密码不正确！", getLoginIp(),
                    DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
            throw new BusinessException("用户密码不正确！");
        }
        // 设备检验
        if (!loginCode.equals(TEST_NUM)) {
            if (StringUtil.isBlank(loginDevice)) {
                result.setStatus(-3);
                result.setMsg("未知设备无法登录，建议您更新至最新版APP重试！");
                return result;
            }
            if (!loginDevice.equals(memberInfo.getLoginDevice())) {
                if (StringUtil.isBlank(code)) {
                    result.setStatus(-3);
                    result.setMsg("更换设备请输入验证码！");
                    return result;
                } else {
                    String message = loginService.doVerify(loginCode, code);
                    if (!message.equalsIgnoreCase("OK")) {
                        result.setStatus(-2);
                        result.setMsg("验证码不正确！");
                        loginService.createLoginLog("用户登录失败", loginCode, "用户登录失败，code:" + code + "， 原因验证码不正确！",
                                getLoginIp(), DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
                        return result;
                    }
                }
            }
        }
        // 登录操作
        String token = loginService.doLogin(loginCode, DeviceTypeEnum.divisionDevice(appTerminal), getLoginIp(),
                loginDevice, appVersion, null, null);
        MemberDto dto = new MemberDto();
        BeanUtils.copyProperties(memberInfo, dto);
        JSONObject json = dto.toJson(token);
        if (dto.isD2c()) {
            json = memberInfoService.findMemberDetail(memberInfo.getId(), memberInfo.getDesignerId(), json);
        }
        result.put("member", json);
        // 密码过于简单
        result.put("danger", loginService.countDangerPasswd(DigestUtils.md5Hex(password)));
        eventController.onLogin(memberInfo, token);
        return result;
    }

    /**
     * 用户注册
     *
     * @param password
     * @param rePassword
     * @param nationCode
     * @param loginCode
     * @param code
     * @param appTerminal
     * @param appVersion
     * @param parent_id
     * @return
     */
    @RequestMapping(value = "/do/register", method = RequestMethod.POST)
    public ResponseResult doRegister(@RequestParam(required = true) String password,
                                     @RequestParam(required = true) String rePassword, @RequestParam(required = true) String nationCode,
                                     @RequestParam(required = true) String loginCode, @RequestParam(required = true) String code,
                                     String appTerminal, String appVersion, Long parent_id) {
        ResponseResult result = new ResponseResult();
        // 注册前校验
        String errorStr = loginService.validate(password, rePassword, loginCode);
        if (errorStr != null) {
            throw new BusinessException(errorStr);
        }
        AssertUt.isPassword(password);
        // 手机注册
        if (LoginUtil.checkMobile(loginCode)) {
            String message = loginService.doVerify(loginCode, code);
            if (!message.equalsIgnoreCase("OK")) {
                throw new BusinessException(message);
            }
        }
        // 推荐人校验
        if (parent_id != null) {
            Partner partner = partnerService.findById(parent_id);
            if (partner == null || partner.getStatus() < 0) {
                parent_id = null;
            }
        }
        // 注册操作
        MemberInfoDto memberInfo = loginService.doRegist(nationCode, loginCode, rePassword,
                MemberTypeEnum.D2CMall.name(), DeviceTypeEnum.divisionDevice(appTerminal), getLoginIp(), parent_id,
                appVersion, null);
        if (memberInfo != null) {
            MemberDto dto = new MemberDto();
            BeanUtils.copyProperties(memberInfo, dto);
            JSONObject json = dto.toJson(memberInfo.getToken());
            if (dto.isD2c()) {
                json = memberInfoService.findMemberDetail(memberInfo.getId(), memberInfo.getDesignerId(), json);
            }
            result.put("member", json);
        } else {
            throw new BusinessException("用户注册不成功！");
        }
        return result;
    }

    /**
     * 用户登录
     *
     * @param loginCode
     * @param password
     * @param loginDevice
     * @param code
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/do/login", method = RequestMethod.POST)
    public ResponseResult doLogin(@RequestParam(required = true) String loginCode,
                                  @RequestParam(required = true) String password, String loginDevice, String code, String appTerminal,
                                  String appVersion) {
        ResponseResult result = new ResponseResult();
        // 账号密码
        loginCode = loginCode.toLowerCase();
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        if (memberInfo == null) {
            throw new BusinessException("用户名不存在！");
        }
        if (!password.equals(memberInfo.getPassword())) {
            loginService.createLoginLog("用户登录失败", loginCode, "用户登录失败，passwd:" + password + "， 原因用户密码不正确！", getLoginIp(),
                    DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
            throw new BusinessException("用户密码不正确！");
        }
        // 设备检验
        if (!loginCode.equals(TEST_NUM)) {
            if (StringUtil.isBlank(loginDevice)) {
                result.setStatus(-3);
                result.setMsg("未知设备无法登录，建议您更新至最新版APP重试！");
                return result;
            }
            if (!loginDevice.equals(memberInfo.getLoginDevice())) {
                if (StringUtil.isBlank(code)) {
                    result.setStatus(-3);
                    result.setMsg("更换设备请输入验证码！");
                    return result;
                } else {
                    String message = loginService.doVerify(loginCode, code);
                    if (!message.equalsIgnoreCase("OK")) {
                        result.setStatus(-2);
                        result.setMsg("验证码不正确！");
                        loginService.createLoginLog("用户登录失败", loginCode, "用户登录失败，code:" + code + "， 原因验证码不正确！",
                                getLoginIp(), DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
                        return result;
                    }
                }
            }
        }
        // 登录操作
        String token = loginService.doLogin(loginCode, DeviceTypeEnum.divisionDevice(appTerminal), getLoginIp(),
                loginDevice, appVersion, null, null);
        MemberDto dto = new MemberDto();
        BeanUtils.copyProperties(memberInfo, dto);
        JSONObject json = dto.toJson(token);
        if (dto.isD2c()) {
            json = memberInfoService.findMemberDetail(memberInfo.getId(), memberInfo.getDesignerId(), json);
        }
        result.put("member", json);
        // 密码过于简单
        result.put("danger", loginService.countDangerPasswd(password));
        eventController.onLogin(memberInfo, token);
        return result;
    }

    /**
     * 短信验证码登录
     *
     * @param nationCode
     * @param loginCode
     * @param code
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/code/login", method = RequestMethod.POST)
    public ResponseResult doCodeLogin(String nationCode, @RequestParam(required = true) String loginCode,
                                      @RequestParam(required = true) String code, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        loginCode = loginCode.toLowerCase();
        String message = loginService.doVerify(loginCode, code);
        if (!message.equalsIgnoreCase("OK")) {
            loginService.createLoginLog("用户登录失败", loginCode, "用户登录失败，code:" + code + "， 原因验证码不正确！", getLoginIp(),
                    DeviceTypeEnum.divisionDevice(appTerminal), appVersion, null);
            throw new BusinessException(message);
        }
        MemberInfo memberInfo = memberInfoService.findByLoginCode(loginCode);
        String deivice = DeviceTypeEnum.divisionDevice(appTerminal);
        String token = null;
        MemberDto dto = new MemberDto();
        if (memberInfo == null) {
            String password = MD5Util.encodeMD5Hex(loginCode).substring(0, 8);
            MemberInfoDto infoDto = loginService.doRegist(nationCode, loginCode, password,
                    MemberTypeEnum.D2CMall.name(), deivice, getLoginIp(), null, appVersion, null);
            token = infoDto.getToken();
            BeanUtils.copyProperties(infoDto, dto);
            // 密码过于简单
            result.put("danger", 1);
        } else {
            token = loginService.doLogin(loginCode, deivice, getLoginIp(), null, appVersion, null, null);
            BeanUtils.copyProperties(memberInfo, dto);
            // 密码过于简单
            result.put("danger", loginService.countDangerPasswd(memberInfo.getPassword()));
        }
        JSONObject json = dto.toJson(token);
        if (dto.isD2c()) {
            json = memberInfoService.findMemberDetail(dto.getId(), dto.getDesignerId(), json);
        }
        result.put("member", json);
        return result;
    }

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseResult doLogout(HttpServletRequest request, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberDto dto = this.getLoginDto();
        loginService.doLogOut(dto.getUnionId(), dto.getLoginCode(), getRequest().getHeader("accesstoken"),
                DeviceTypeEnum.divisionDevice(appTerminal), getLoginIp(), appVersion, null);
        eventController.onLogout();
        return result;
    }

    /**
     * 用户注销
     *
     * @param code
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult doDelete(@RequestParam(required = true) String code) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        String message = loginService.doVerify(memberInfo.getLoginCode(), code);
        if (!message.equalsIgnoreCase("OK")) {
            throw new BusinessException(message);
        }
        memberInfoService.doDelete(memberInfo.getLoginCode());
        return result;
    }

    /**
     * 绑定D2C会员
     *
     * @param loginCode
     * @param code
     * @param nationCode
     * @param appTerminal
     * @param appVersion
     * @param parent_id
     * @return
     */
    @RequestMapping(value = "/bind/member", method = RequestMethod.POST)
    public ResponseResult doBindMember(@RequestParam(required = true) String loginCode,
                                       @RequestParam(required = true) String code, String nationCode, String appTerminal, String appVersion,
                                       Long parent_id) {
        ResponseResult result = new ResponseResult();
        Member member = this.getLoginMember();
        String message = loginService.doVerify(loginCode, code);
        if (!message.equalsIgnoreCase("OK")) {
            throw new BusinessException(message);
        }
        // 推荐人校验
        if (parent_id != null) {
            Partner partner = partnerService.findById(parent_id);
            if (partner == null || partner.getStatus() < 0) {
                parent_id = null;
            }
        }
        // 绑定操作
        MemberInfoDto memberInfo = loginService.doBindMemberInfo(member, nationCode, loginCode, member.getUnionId(),
                member.getSource(), DeviceTypeEnum.divisionDevice(appTerminal), getLoginIp(), parent_id, appVersion,
                null, member.getToken());
        MemberDto dto = new MemberDto();
        BeanUtils.copyProperties(memberInfo, dto);
        JSONObject json = dto.toJson(memberInfo.getToken());
        if (dto.isD2c()) {
            json = memberInfoService.findMemberDetail(memberInfo.getId(), memberInfo.getDesignerId(), json);
        }
        result.put("member", json);
        return result;
    }

    /**
     * 快速绑定D2C会员
     *
     * @param loginCode
     * @param nationCode
     * @param appTerminal
     * @param appVersion
     * @param parent_id
     * @return
     */
    @RequestMapping(value = "/quick/bind", method = RequestMethod.POST)
    public ResponseResult doQuickBind(@RequestParam(required = true) String loginCode, String nationCode,
                                      String appTerminal, String appVersion, Long parent_id) {
        ResponseResult result = new ResponseResult();
        Member member = this.getLoginMember();
        // 绑定操作
        MemberInfoDto memberInfo = loginService.doBindMemberInfo(member, nationCode, loginCode, member.getUnionId(),
                member.getSource(), DeviceTypeEnum.divisionDevice(appTerminal), getLoginIp(), parent_id, appVersion,
                null, member.getToken());
        MemberDto dto = new MemberDto();
        BeanUtils.copyProperties(memberInfo, dto);
        JSONObject json = dto.toJson(memberInfo.getToken());
        if (dto.isD2c()) {
            json = memberInfoService.findMemberDetail(memberInfo.getId(), memberInfo.getDesignerId(), json);
        }
        result.put("member", json);
        return result;
    }

    /**
     * 会员解绑游客
     *
     * @param unionId
     * @return
     */
    @RequestMapping(value = "/cancel/bind", method = RequestMethod.POST)
    public ResponseResult doCancelBind(@RequestParam(required = true) String unionId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Member member = memberService.findByUnionId(unionId);
        if (!memberInfo.getId().equals(member.getMemberInfoId())) {
            throw new BusinessException("此游客不属于该账号！");
        }
        memberService.doCancelBind(unionId);
        return result;
    }

    /**
     * 更换绑定手机
     *
     * @param newMobile
     * @param code
     * @param appTerminal
     * @param nationCode
     * @return
     */
    @RequestMapping(value = "/change/mobile", method = RequestMethod.POST)
    public ResponseResult doChangeMobile(@RequestParam(required = true) String newMobile,
                                         @RequestParam(required = true) String code, String appTerminal, String nationCode) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        String message = loginService.doVerify(newMobile, code);
        if (!message.equalsIgnoreCase("OK")) {
            throw new BusinessException(message);
        }
        if (StringUtil.isBlack(nationCode)) {
            nationCode = "86";
        }
        // 更换手机
        String token = memberInfoService.doChangeMobile(memberInfo.getId(), memberInfo.getLoginCode(), newMobile,
                nationCode, DeviceTypeEnum.divisionDevice(appTerminal));
        memberInfo.setLoginCode(newMobile);
        memberInfo.setMobile(newMobile);
        MemberDto dto = new MemberDto();
        BeanUtils.copyProperties(memberInfo, dto);
        JSONObject json = dto.toJson(token);
        if (dto.isD2c()) {
            json = memberInfoService.findMemberDetail(memberInfo.getId(), memberInfo.getDesignerId(), json);
        }
        result.put("member", json);
        return result;
    }

    /**
     * 分享有礼
     *
     * @param recCode
     * @return
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.GET)
    public ResponseResult doRecommend(String recCode) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (StringUtils.isEmpty(recCode)) {
            throw new BusinessException("推荐码不能为空！");
        }
        recCode = recCode.toLowerCase();
        MemberInfo recInfo = memberInfoService.findByRecCode(recCode);
        if (memberInfo.getRecId() != null) {
            throw new BusinessException("您已绑定了推荐码，请勿重复绑定！");
        } else if (recInfo == null) {
            throw new BusinessException("推荐码不存在，请重新输入！");
        } else if (recInfo.getId().equals(memberInfo.getId())) {
            throw new BusinessException("不可绑定自己的推荐码！");
        } else if (recInfo.getRecId() != null && recInfo.getRecId().equals(memberInfo.getId())) {
            throw new BusinessException("不可相互绑定！");
        } else {
            accountTxService.doRecommend(memberInfo, recInfo.getId(), "0");
            result.put("recId", recInfo.getId());
        }
        return result;
    }

    /**
     * 返利列表
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/recommend/list", method = RequestMethod.GET)
    public ResponseResult recommendList(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        RecommendLogSearcher searcher = new RecommendLogSearcher();
        searcher.setRecId(memberInfo.getId());
        PageResult<RecommendLog> pager = recommendLogService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toSimpleJson()));
        result.putPage("memberList", pager, array);
        return result;
    }

    /**
     * 会员签到
     *
     * @return
     */
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public ResponseResult doSign() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberDailySign dailySign = memberDailySignService.doSign(memberInfo.getId(), memberInfo.getLoginCode());
        result.put("dailySign", dailySign.toJson());
        return result;
    }

    /**
     * 签到记录
     *
     * @param page
     * @param searcher
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/sign/records", method = RequestMethod.GET)
    public ResponseResult signRecord(PageModel page, MemberDailySignSearcher searcher) throws ParseException {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        if (searcher.getBegainDate() != null) {
            searcher.setBegainDate(DateUtil.getStartOfDay(DateUtil.getIntervalDay(new Date(), -7)));
        }
        PageResult<MemberDailySign> pager = memberDailySignService.findBySearcher(searcher, page);
        List<MemberDailySign> list = new ArrayList<>();
        for (int i = 0; pager.getList() != null && i < pager.getList().size(); i++) {
            // 最新签到不是昨天
            if (i == 0 && DateUtil.daysBetween(pager.getList().get(i).getCreateDate(), new Date()) > 1) {
                break;
            } else if (i == 0 && pager.getList().get(i).getTotalDay() == 1) {
                // 如果最新一天是签到一天的
                list.add(pager.getList().get(i));
                break;
            } else if (i > 0 && DateUtil.daysBetween(pager.getList().get(i).getCreateDate(),
                    pager.getList().get(i - 1).getCreateDate()) > 1) {
                break;
            }
            list.add(pager.getList().get(i));
        }
        pager.setList(list);
        pager.setTotalCount(list.size());
        result.put("signRecords", pager);
        MemberDailySign lastSign = (pager.getList() != null && pager.getList().size() > 0 ? pager.getList().get(0)
                : null);
        // 默认今天是第一天，返回就是第二天
        Integer tomorrowReward = SignRewardEnum.getRewardByDay(SignRewardEnum.SECOND.getDay());
        if (lastSign != null) {
            int betweenDays = DateUtil.daysBetween(lastSign.getCreateDate(), new Date());
            // 最后一次签到距离今天不超过1天的，就表示连续签到了，且明天不是第8天
            if (betweenDays <= 1) {
                if ((lastSign.getTotalDay() + 1 + betweenDays) <= SignRewardEnum.SEVENTH.getDay()) {
                    tomorrowReward = SignRewardEnum.getRewardByDay(lastSign.getTotalDay() + 1 + betweenDays);
                } else {
                    tomorrowReward = SignRewardEnum.getRewardByDay(lastSign.getTotalDay() + 1 + betweenDays - 7);
                }
            }
        }
        result.put("tomorrowReward", tomorrowReward);
        Map<Integer, Integer> signRewards = SignRewardEnum.getHolder();
        result.put("signRewards", signRewards.values().toArray());
        return result;
    }

    /**
     * 最新签到记录
     *
     * @return
     */
    @RequestMapping(value = "/lastest/records", method = RequestMethod.GET)
    public ResponseResult lastestRecord() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberDailySign memberDailySign = memberDailySignService.findLastByMember(memberInfo.getId());
        result.put("memberDailySign", memberDailySign != null ? memberDailySign.toJson() : new JSONObject());
        return result;
    }

    /**
     * 最新签到记录
     *
     * @return
     */
    @RequestMapping(value = "/point", method = RequestMethod.GET)
    public ResponseResult myPoint() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberDetail memberDetail = memberDetailService.findByMemberInfoId(memberInfo.getId());
        if (memberDetail != null) {
            result.put("point", memberDetail.getIntegration());
        }
        return result;
    }

}
