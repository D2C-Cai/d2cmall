package com.d2c.flame.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberLotto;
import com.d2c.member.model.MemberLotto.LotteryOpportunityEnum;
import com.d2c.member.service.MemberLottoService;
import com.d2c.order.model.AwardRecord;
import com.d2c.order.service.AwardRecordService;
import com.d2c.order.service.tx.AwardTxService;
import com.d2c.product.model.AwardProduct;
import com.d2c.product.model.AwardSession;
import com.d2c.product.service.AwardProductService;
import com.d2c.product.service.AwardSessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/award")
public class AwardController extends BaseController {

    @Autowired
    private AwardRecordService awardRecordService;
    @Autowired
    private MemberLottoService memberLottoService;
    @Autowired
    private AwardProductService awardProductService;
    @Autowired
    private AwardSessionService awardSessionService;
    @Reference
    private AwardTxService awardTxService;

    /**
     * 抽奖页详情
     *
     * @return
     */
    @RequestMapping(value = "/detail/{sessionId}", method = RequestMethod.GET)
    public String awardDetail(ModelMap model, @PathVariable Long sessionId) {
        AwardSession awardSession = awardSessionService.findById(sessionId);
        if (awardSession.getStatus() == 0) {
            throw new BusinessException("该活动已被下架。");
        }
        JSONArray array = new JSONArray();
        List<AwardProduct> awardProduducts = awardProductService.findBySessionIdAndNow(sessionId);
        awardProduducts.forEach(p -> array.add(p.toJson()));
        List<AwardRecord> recentlyList = awardRecordService.findListByRecently(sessionId);
        JSONArray recentlyAttends = new JSONArray();
        recentlyList.forEach(r -> recentlyAttends.add(r.toJson()));
        List<AwardRecord> myAttends = new ArrayList<>();
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            MemberLotto memberLotto = memberLottoService.findByMemberIdAndSessionId(memberInfo.getId(), sessionId);
            JSONObject lotterySource = new JSONObject();
            if (memberLotto == null || !(lotterySource = JSON.parseObject(memberLotto.getLotterySource()))
                    .containsKey(LotteryOpportunityEnum.LOGIN.name())) {
                memberLottoService.updateMemberLottery(memberInfo.getId(), LotteryOpportunityEnum.LOGIN, sessionId);
                lotterySource.put("LOGIN", LotteryOpportunityEnum.LOGIN.getCount());
                memberLotto = memberLottoService.findByMemberIdAndSessionId(memberInfo.getId(), sessionId);
            }
            model.put("lotterySource", lotterySource);
            model.put("ownCount", memberLotto.getOwnCount());
            myAttends = awardRecordService.findByMemberIdAndSessionId(memberInfo.getId(), sessionId);
        } catch (NotLoginException e) {
        }
        model.put("myAttends", myAttends);
        model.put("awardProduducts", array);
        model.put("recentlyAttends", recentlyAttends);
        model.put("sessionId", sessionId);
        return "society/award_detail";
    }

    /**
     * 开始抽奖
     *
     * @return
     */
    @RequestMapping(value = "/start/{sessionId}", method = RequestMethod.POST)
    public String doStartLottery(ModelMap model, @PathVariable Long sessionId) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        AwardSession awardSession = awardSessionService.findById(sessionId);
        if (awardSession.getStatus() == 0) {
            throw new BusinessException("该活动已被下架。");
        }
        if (awardSession.getBeginDate() == null || awardSession.getEndDate() == null) {
            throw new BusinessException("系统参数不正确，请核对抽奖时间参数。");
        }
        // 判断奖品是否处于某个区间,并且活动时间晚于活动截止日
        if (new Date().before(awardSession.getBeginDate())) {
            throw new BusinessException("抽奖尚未开始。");
        }
        AwardRecord awardRecord = null;
        if (new Date().before(awardSession.getEndDate())) {
            MemberLotto memberLotto = memberLottoService.findByMemberIdAndSessionId(memberInfo.getId(), sessionId);
            if (memberLotto == null || memberLotto.getOwnCount() == 0) {
                throw new BusinessException("您已经没有抽奖机会");
            }
            awardRecord = awardRecordService.doStartLottery(memberLotto);
            int result = awardTxService.doLotteryFinished(awardRecord);
            logger.info("开始抽奖的用户id：" + memberInfo.getId() + "，用户的昵称：" + memberInfo.getNickname() + "，抽奖的奖品名称："
                    + awardRecord.getAwardName() + "，领取是否成功：" + result + "，用户的剩余抽奖次数："
                    + (memberLotto.getOwnCount() - result));
        } else {
            throw new BusinessException("抽奖活动已经结束");
        }
        model.put("awardRecord", awardRecord);
        model.put("url", getAwardUrl(awardRecord));
        return "";
    }

    private String getAwardUrl(AwardRecord awardRecord) {
        switch (awardRecord.getAwardProductType()) {
            case "RED":
                return "/check/redPacket";
            case "OBJECT":
                return "";
            case "RABATE":
                return "";
            case "COUPON":
                String[] params = awardRecord.getAwardProductParam().split("-");
                if (params.length == 2 && StringUtils.isNotBlank(params[1])) {
                    return "/detail/product/" + params[1];
                }
                return "/coupon/memberCoupon";
        }
        return "";
    }

}
