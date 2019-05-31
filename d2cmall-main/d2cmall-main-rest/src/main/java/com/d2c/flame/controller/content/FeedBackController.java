package com.d2c.flame.controller.content;

import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.content.model.FeedBack;
import com.d2c.content.model.FeedBack.FeedBackType;
import com.d2c.content.service.FeedBackService;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.MemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 意见反馈
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/feedBack")
public class FeedBackController extends BaseController {

    @Autowired
    private FeedBackService feedBackService;

    /**
     * 新增意见反馈
     *
     * @param feedBack
     * @param appTerminal
     * @param appVersion
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseResult insert(FeedBack feedBack, String appTerminal, String appVersion) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo;
        try {
            memberInfo = this.getLoginMemberInfo();
            feedBack.setMemberId(memberInfo.getId());
            feedBack.setLoginCode(memberInfo.getLoginCode());
            feedBack.setNickName(memberInfo.getNickname());
            feedBack.setHeadPic(memberInfo.getHeadPic());
            feedBack.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
            feedBack.setVersion(appVersion);
            if (feedBack.getMobile() != null && feedBack.getMobile().contains("*")) {
                feedBack.setMobile(memberInfo.getLoginCode());
            }
        } catch (NotLoginException e) {
            feedBack.setDevice(DeviceTypeEnum.divisionDevice(appTerminal));
            feedBack.setVersion(appVersion);
            feedBack.setMemberId(0L);
            feedBack.setLoginCode("匿名用户");
        }
        feedBack = feedBackService.insert(feedBack);
        if (feedBack == null || feedBack.getId() == null) {
            throw new BusinessException("信息报错，保存不成功！");
        }
        return result;
    }

    /**
     * 意见反馈类型
     *
     * @return
     */
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ResponseResult types() {
        ResponseResult result = new ResponseResult();
        result.put("feedBackType", FeedBackType.FEEDBACK.getTypes());
        result.put("questionType", FeedBackType.QUESTION.getTypes());
        result.put("complaintType", FeedBackType.COMPLAINT.getTypes());
        return result;
    }

    /**
     * 意见反馈详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        FeedBack feedback = feedBackService.findById(id);
        result.put("feedBack", feedback.toJson());
        return result;
    }

}
