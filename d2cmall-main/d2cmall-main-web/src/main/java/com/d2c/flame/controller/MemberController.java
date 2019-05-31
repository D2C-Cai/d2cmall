package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.AssertUt;
import com.d2c.common.mq.enums.MqEnum;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.property.HttpProperties;
import com.d2c.member.dto.MemberDto;
import com.d2c.member.enums.SignRewardEnum;
import com.d2c.member.model.*;
import com.d2c.member.query.MemberDailySignSearcher;
import com.d2c.member.query.MemberIntegrationSearcher;
import com.d2c.member.service.*;
import com.d2c.util.date.DateUtil;
import com.d2c.util.qrcode.QRcodeEntity;
import com.d2c.util.qrcode.QRcodeUtil;
import com.d2c.util.string.LoginUtil;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

    @Autowired
    private HttpProperties httpProperties;
    @Autowired
    private LoginService loginService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private MemberDetailService memberDetailService;
    @Autowired
    private MemberTagRelationService memberTagRelationService;
    @Autowired
    private MemberIntegrationService memberIntegrationService;
    @Autowired
    private MemberLevelService memberLevelService;
    @Autowired
    private MemberDailySignService memberDailySignService;

    /**
     * ajax 验证用户是否登陆
     *
     * @return
     */
    @RequestMapping(value = "/islogin", method = RequestMethod.GET)
    public String getMemberLoginStatus(ModelMap model) {
        MemberDto dto = this.getLoginDto();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        result.put("isD2C", dto.isD2c());
        result.put("isBind", dto.isD2c());
        return "";
    }

    /**
     * 用户标签列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/tag/list", method = RequestMethod.POST)
    public String getTagList(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        List<MemberTagRelation> list = memberTagRelationService.findByMemberId(memberInfo.getId());
        result.put("tagList", list);
        return "";
    }

    /**
     * 验证用户是否是D2C账户
     * <p>
     * 1. 登录 直接获取 2. 未登录 通过mobile获取
     *
     * @param model
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(ModelMap model, String mobile) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberDto dto = null;
        try {
            dto = this.getLoginDto();
            if (!dto.isD2c()) {
                result.setStatus(-2);
                result.setMsg("该账号未绑定手机！");
            } else {
                result.put("mobile", dto.getLoginCode());
            }
        } catch (NotLoginException e) {
            if (StringUtils.isBlank(mobile)) {
                throw new NotLoginException();
            }
            if (!LoginUtil.checkMobile(mobile)) {
                throw new BusinessException("手机号格式不正确！");
            }
            MemberInfo registerMember = memberInfoService.findByLoginCode(mobile);
            if (registerMember == null) {
                result.setStatus(-2);
                result.setMsg("该手机号未注册！");
                return "";
            }
        }
        return "";
    }

    /**
     * 验证密码是否正确
     *
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = "/checkPassword", method = RequestMethod.POST)
    public String checkPassword(String password, ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            if (!DigestUtils.md5Hex(password).equals(memberInfo.getPassword())) {
                throw new BusinessException("您输入的密码不正确！");
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return "";
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(String newPassword, ModelMap model, HttpServletResponse response,
                                 @CookieValue(defaultValue = "", value = Member.CASTGC) String tgc) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        try {
            if (newPassword != null) {
                AssertUt.isPassword(newPassword);
                memberInfo.setPassword(DigestUtils.md5Hex(newPassword));
                int success = memberInfoService.updatePassword(memberInfo.getLoginCode(), memberInfo.getPassword());
                if (success == 1) {
                    Cookie clearTgc = new Cookie(Member.CASTGC, null);
                    response.addCookie(clearTgc);
                }
                loginService.createLoginLog("修改账号密码", memberInfo.getLoginCode(), "修改账号密码成功，passwd:" + newPassword,
                        getLoginIp(), isMobileDevice() ? "WAP" : "PC", null, getUserAgent());
                result.setMsg("密码修改成功");
            } else {
                throw new BusinessException("原始密码不正确");
            }
        } catch (BusinessException e) {
            loginService.createLoginLog("修改账号密码", memberInfo.getLoginCode(),
                    "修改账号密码失败，passwd:" + newPassword + "， 原因" + e.getMessage(), getLoginIp(),
                    isMobileDevice() ? "WAP" : "PC", null, getUserAgent());
            throw new BusinessException(e.getMessage());
        }
        return "member/login";
    }

    /**
     * 注册完毕资料补充
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateProfile", method = RequestMethod.GET)
    public String showUpdateProfile(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        model.put("member", memberInfo);
        return "member/update_profile";
    }

    /**
     * 设置
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String showSetting(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        model.put("member", memberInfo);
        return "member/setting";
    }

    /**
     * 查看个人资料
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String myInfo(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberDetail memberDetail = memberDetailService.findByMemberInfoId(memberInfo.getId());
        MemberDto dto = new MemberDto();
        BeanUtils.copyProperties(memberInfo, dto);
        dto.setMemberDetail(memberDetail);
        model.put("member", dto);
        return "member/my_info";
    }

    /**
     * 修改个人资料
     *
     * @param model
     * @param member
     * @param year
     * @param month
     * @param day
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String updateInfo(ModelMap model, MemberDto member, String year, String month, String day)
            throws UnsupportedEncodingException {
        MemberInfo oldmemberInfo = this.getLoginMemberInfo();
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (StringUtils.isBlank(member.getNickname())) {
            throw new BusinessException("昵称不能为空");
        }
        if (StringUtils.isBlank(member.getMemberDetail().getName())) {
            throw new BusinessException("真实姓名不能为空");
        }
        int nickNameLength = member.getNickname().getBytes("GBK").length;
        int nameLength = member.getMemberDetail().getName().getBytes("GBK").length;
        if (nickNameLength < 4 || nickNameLength > 20) {
            throw new BusinessException("昵称长度不合法。");
        }
        if (nameLength >= 4 && nameLength <= 20) {
            String pattern = "[^a-z\u4e00-\u9fa5]";
            Matcher m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(member.getMemberDetail().getName());
            if (m.find()) {
                throw new BusinessException("真实姓名只允许汉字和英文。");
            }
        } else {
            throw new BusinessException("真实姓名长度不合法。");
        }
        if (oldmemberInfo.getId().equals(member.getId())) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(StringUtils.isBlank(year) ? "1970" : year).append("-")
                    .append(StringUtils.isBlank(month) ? "01" : month).append("-")
                    .append(StringUtils.isBlank(day) ? "01" : day);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                member.setBirthday(simpleDateFormat.parse(stringBuffer.toString()));
            } catch (ParseException e) {
                throw new BusinessException("生日格式不正确");
            }
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(member.getId());
            memberInfo.setSex(member.getSex());
            memberInfo.setNickname(member.getNickname());
            memberInfo.setBirthday(member.getBirthday());
            memberInfo.setEmail(member.getEmail());
            memberInfo.setBackground(member.getBackground());
            memberInfo.setHeadPic(member.getHeadPic());
            this.checkSensitiveWords(memberInfo.getNickname());
            memberInfoService.update(memberInfo);
            memberDetailService.updateByMemberId(memberInfo.getId(), member.getMemberDetail());
            // 清缓存
            this.refreshMemberMQ(memberInfo);
        } else {
            throw new BusinessException("非本人不允许操作个人信息");
        }
        return "";
    }

    private void refreshMemberMQ(MemberInfo member) {
        Map<String, Object> map = new HashMap<>();
        map.put("headPic", member.getHeadPic());
        map.put("nickName", member.getNickname());
        map.put("memberInfoId", member.getId());
        MqEnum.REFRESH_MEMBER.send(map);
    }

    /**
     * 我的密码管理页面
     *
     * @return
     */
    @RequestMapping(value = "/security", method = RequestMethod.GET)
    public String myPassword() {
        return "member/my_password";
    }

    /**
     * 跳转到我的D2C界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        model.put("memberInfo", memberInfo);
        return "member/my";
    }

    /**
     * 我的分享二维码
     *
     * @param model
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/share", method = RequestMethod.GET)
    public String myShare(ModelMap model, Long memberId) {
        if (memberId == null) {
            memberId = this.getLoginMemberInfo().getId();
        }
        MemberInfo member = memberInfoService.findById(memberId);
        String url = "inviteCode:" + member.getRecCode();
        String qCodePath = this.createQRCode(member.getId(), 1, url, member.getRecCode(), 220, 220, "", true);
        model.put("qCodePath", httpProperties.getMobileUrl() + qCodePath);
        model.put("recCode", member.getRecCode());
        return "member/my_share";
    }

    private String createQRCode(Long memberId, Integer type, String url, String code, Integer width, Integer height,
                                String lowerContent, Boolean noLogo) {
        noLogo = noLogo == null ? false : noLogo;
        if (StringUtils.isBlank(code)) {
            return "";
        }
        String folderName1 = "/mnt/www";
        String folderName2 = "/download/member/qrcode";
        String folderName3 = "/" + memberId + "_" + code + ".png";
        File dir = new File(folderName1 + folderName2);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String qCodePath = folderName1 + folderName2 + folderName3;
        File file = new File(qCodePath);
        if (!file.exists()) {
            BufferedImage img = null;
            type = type == null ? 0 : type;
            switch (type) {
                case 0:
                    img = QRcodeUtil.createBarcode(url, width, height);
                    break;
                case 1:
                    img = QRcodeUtil.createQRCode(getQRcodeEntity(url, width, height, lowerContent, noLogo));
                    break;
            }
            try {
                ImageIO.write(img, "png", new File(qCodePath));
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return folderName2 + folderName3;
    }

    private QRcodeEntity getQRcodeEntity(String code, Integer width, Integer height, String lowerContent,
                                         boolean noLogo) {
        QRcodeEntity zxing = new QRcodeEntity();
        zxing.setCharacterSet("UTF-8");
        zxing.setErrorCorrectionLevel(ErrorCorrectionLevel.H);
        zxing.setFormat("png");
        zxing.setMargin(0);
        zxing.setWidth(width);
        zxing.setHeight(height);
        zxing.setContent(code);
        zxing.setLowerContent(lowerContent);
        if (!noLogo) {
            zxing.setLogoPath(getSession().getServletContext().getRealPath("/") + "/static/logo.png");
        }
        return zxing;
    }

    /**
     * 会员积分列表
     *
     * @param page
     * @param searcher
     * @param model
     * @return
     */
    @RequestMapping(value = "/integration/list", method = RequestMethod.GET)
    public String myIntegrationList(PageModel page, MemberIntegrationSearcher searcher, ModelMap model) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        PageResult<MemberIntegration> pager = memberIntegrationService.findBySearch(searcher, page);
        MemberDetail detail = memberDetailService.findByMemberInfoId(memberInfo.getId());
        result.put("detail", detail);
        result.put("memberIntegration", pager);
        model.put("result", result);
        return "";
    }

    /**
     * 获取会员等级 以及距下一级的差距
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/level", method = RequestMethod.GET)
    public String memberLevel(ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberDetail memberDetail = memberDetailService.findByMemberInfoId(memberInfo.getId());
        if (memberDetail != null) {
            MemberLevel nextLevel = memberLevelService.findByLevel(memberDetail.getLevel() + 1);
            result.put("memberInfo", memberInfo);
            result.put("memberDetail", memberDetail);
            if (nextLevel != null) {
                result.put("nextLevel", nextLevel.toJson());
            }
        } else {
            throw new BusinessException("会员不存在！");
        }
        model.put("member", result);
        return "member/my_level";
    }

    /**
     * 版本提示
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/selfservice/buyer", method = RequestMethod.GET)
    public String selfService(ModelMap model) {
        return "member/update_tips";
    }

    /**
     * 会员签到
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    public String doSign(ModelMap model) {
        SuccessResponse result = new SuccessResponse();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberDailySign dailySign = memberDailySignService.doSign(memberInfo.getId(), memberInfo.getLoginCode());
        result.put("dailySign", dailySign);
        model.put("result", result);
        return "";
    }

    /**
     * 签到记录
     *
     * @param page
     * @param searcher
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/sign/records", method = RequestMethod.GET)
    public String signRecord(PageModel page, MemberDailySignSearcher searcher, ModelMap model) throws ParseException {
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
                // 排序是按时间倒序排的
                break;
            }
            list.add(pager.getList().get(i));
        }
        pager.setList(list);
        pager.setTotalCount(list.size());
        model.put("signRecords", pager);
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
        model.put("tomorrowReward", tomorrowReward);
        Map<Integer, Integer> signRewards = SignRewardEnum.getHolder();
        model.put("signRewards", signRewards.values().toArray());
        return "member/sign";
    }

}
