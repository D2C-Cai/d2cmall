package com.d2c.flame.controller.member;

import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.d2c.cache.redis.RedisHandler;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.security.Base64Ut;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberCertification;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.query.MemberCertificationSearcher;
import com.d2c.member.service.MemberCertificationService;
import com.d2c.member.third.aliyun.AuthenticateClient;
import com.d2c.util.date.DateUtil;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 实名认证
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/membercertification")
public class CertificationController extends BaseController {

    @Autowired
    private MemberCertificationService memberCertificationService;
    @Autowired
    private RedisHandler<String, Integer> redisHandler;

    /**
     * 身份认证列表
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(MemberCertificationSearcher searcher, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        searcher.setStatus(1);
        PageResult<MemberCertification> pager = memberCertificationService.findBySearcher(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("certifications", pager, array);
        return result;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberCertification certification = memberCertificationService.findOneById(id);
        if (certification == null || !certification.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("该认证信息非本人，删除不成功");
        }
        int success = memberCertificationService.doDelete(id);
        if (success < 1) {
            throw new BusinessException("删除不成功！");
        }
        return result;
    }

    /**
     * 设置默认
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/default/{id}", method = RequestMethod.POST)
    public ResponseResult doDefault(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberCertification certification = memberCertificationService.findOneById(id);
        if (certification == null || !certification.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("该认证信息非本人，设置不成功");
        }
        int success = memberCertificationService.doDefault(id, memberInfo.getId());
        if (success < 1) {
            throw new BusinessException("设置不成功！");
        }
        return result;
    }

    /**
     * 实名认证
     *
     * @param certification
     * @return
     */
    @RequestMapping(value = "/certificate", method = RequestMethod.POST)
    public ResponseResult doCertificate(MemberCertification certification) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        certification.setMemberId(memberInfo.getId());
        certification.setLoginCode(memberInfo.getLoginCode());
        int success = memberCertificationService.doCertificate(certification);
        if (success < 1) {
            throw new BusinessException("认证失败！");
        }
        return result;
    }

    /**
     * 重新上传身份证
     *
     * @param frontPic
     * @param behindPic
     * @param id
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseResult doUpload(String frontPic, String behindPic, Long id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberCertification certification = memberCertificationService.findOneById(id);
        if (certification == null) {
            throw new BusinessException("该实名信息不存在，上传不成功");
        }
        if (!certification.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("该认证信息非本人，上传不成功");
        }
        certification.setFrontPic(frontPic);
        certification.setBehindPic(behindPic);
        int success = memberCertificationService.update(certification);
        if (success < 1) {
            result.setStatus(-1);
            result.setMsg("认证不成功");
        }
        return result;
    }

    /**
     * 活体认证信息（因为执行这步时已经验证了，所以不加次数限制，直接添加）
     *
     * @param certification
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseResult insertCertificate(MemberCertification certification) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (StringUtil.isBlack(certification.getIdentityCard()) || StringUtil.isBlack(certification.getRealName())) {
            throw new BusinessException("身份证和真实姓名不能为空");
        }
        certification.setIdentityCard(Base64Ut.decode(certification.getIdentityCard()));
        if (certification.getAuthenticate() != 1) {
            throw new BusinessException("请先认证");
        }
        if (!certification.getIdentityCard().matches("(^\\d{17}(\\d|X)$)|(^\\d{15}$)")) {
            throw new BusinessException("请填写正确的身份证号");
        }
        if (certification.getId() != null) {
            MemberCertification old = memberCertificationService.findOneById(certification.getId());
            if (old != null && old.getMemberId().equals(memberInfo.getId())) {
                old.setAuthenticate(1);
                if (StringUtil.isNotBlank(certification.getFrontPic())
                        && StringUtil.isNotBlank(certification.getBehindPic())) {
                    old.setFrontPic(certification.getFrontPic());
                    old.setBehindPic(certification.getBehindPic());
                }
                int success = memberCertificationService.update(old);
                if (success < 1) {
                    throw new BusinessException("认证失败");
                }
                certification = old;
            } else {
                throw new BusinessException("该认证信息不存在");
            }
        } else {
            certification.setLoginCode(memberInfo.getLoginCode());
            certification.setMemberId(memberInfo.getId());
            certification = memberCertificationService.doInsert(certification);
        }
        result.put("memberCertification", certification.toJson());
        return result;
    }

    /**
     * 获取bizNo
     *
     * @param certNo
     * @return
     * @throws AlipayApiException
     */
    @RequestMapping(value = "/bizno", method = RequestMethod.POST)
    public ResponseResult getBizNo(String certNo, String name) throws AlipayApiException {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (StringUtil.isBlack(certNo) || StringUtil.isBlack(name)) {
            throw new BusinessException("身份证和真实姓名不能为空");
        }
        certNo = Base64Ut.decode(certNo);
        check(memberInfo.getId());
        Integer count = redisHandler.get("certification_" + memberInfo.getId());
        count = (count == null ? 0 : count);
        if (count >= 3) {
            throw new BusinessException("您今日已超过3次验证，明日再来验证吧");
        }
        String bizNo = AuthenticateClient.getInstance().getBizNo(certNo, name);
        if (StringUtil.isNotBlank(bizNo)) {
            redisHandler.setInSec("certification_" + memberInfo.getId(), count + 1,
                    (DateUtil.getEndOfDay(new Date()).getTime() - new Date().getTime()) / 1000);
        }
        result.put("bizNo", bizNo);
        return result;
    }

    /**
     * 检查活体是否超了
     *
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseResult check() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        check(memberInfo.getId());
        return result;
    }

    /**
     * 活体验证限制
     *
     * @param memberInfoId
     */
    private void check(Long memberInfoId) {
        MemberCertificationSearcher searcher = new MemberCertificationSearcher();
        searcher.setMemberId(memberInfoId);
        searcher.setStatus(1);
        searcher.setAuthenticate(1);
        int count = memberCertificationService.countBySearcher(searcher);
        if (count >= 4) {
            throw new BusinessException("您已超过实名认证信息数量");
        }
    }

}
