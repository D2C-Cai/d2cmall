package com.d2c.flame.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.model.ShareRedPackets;
import com.d2c.order.model.ShareRedPacketsGroup;
import com.d2c.order.model.ShareRedPacketsPromotion;
import com.d2c.order.service.ShareRedPacketsGroupService;
import com.d2c.order.service.ShareRedPacketsPromotionService;
import com.d2c.order.service.ShareRedPacketsService;
import com.d2c.order.service.tx.ShareRedTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 分享红包
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping("/v3/api/shareredpackets")
public class RedPacketsController extends BaseController {

    @Autowired
    private ShareRedPacketsService shareRedPacketsService;
    @Autowired
    private ShareRedPacketsGroupService shareRedPacketsGroupService;
    @Autowired
    private ShareRedPacketsPromotionService shareRedPacketsPromotionService;
    @Reference
    private ShareRedTxService shareRedTxService;

    /**
     * 新建拼团
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseResult create() {
        ResponseResult result = new ResponseResult();
        Date now = new Date();
        ShareRedPacketsPromotion nowPromotion = shareRedPacketsPromotionService.findNowPromotion();
        if (nowPromotion == null
                || now.compareTo(nowPromotion.getBeginDate()) < 0 && now.compareTo(nowPromotion.getEndDate()) > 0) {
            throw new BusinessException("活动已经结束！");
        }
        MemberInfo memberInfo = this.getLoginMemberInfo();
        ShareRedPacketsGroup oldGroup = shareRedPacketsGroupService.findByInitiatorMemberId(memberInfo.getId(),
                nowPromotion.getId());
        if (oldGroup != null) {
            result.put("myGroupId", oldGroup.getId());
            throw new BusinessException("每个人只能开团一次哦！");
        }
        ShareRedPacketsGroup shareRedPacketsGroup = shareRedTxService.createGroup(memberInfo.getId(), nowPromotion);
        result.put("shareRedPacketsGroup", shareRedPacketsGroup);
        result.put("mobile", memberInfo.getMobile());
        return result;
    }

    /**
     * 红包详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable Long id) {
        ResponseResult result = new ResponseResult();
        Date now = new Date();
        ShareRedPacketsPromotion nowPromotion = shareRedPacketsPromotionService.findNowPromotion();
        if (nowPromotion == null
                || now.compareTo(nowPromotion.getBeginDate()) < 0 && now.compareTo(nowPromotion.getEndDate()) > 0) {
            throw new BusinessException("活动已经结束！");
        }
        // 此团信息
        ShareRedPacketsGroup group = shareRedPacketsGroupService.findById(id);
        if (group == null) {
            // 初始页面
            try {
                MemberInfo memberInfo = this.getLoginMemberInfo();
                // 我创建过的团 （创团按钮）
                ShareRedPacketsGroup myGroup = shareRedPacketsGroupService.findByInitiatorMemberId(memberInfo.getId(),
                        nowPromotion.getId());
                result.put("myGroupId", myGroup == null ? null : myGroup.getId());
                result.put("myAttend", null);
            } catch (NotLoginException notLoginException) {
            }
            result.put("group", group);
            return result;
        }
        // 此团参团记录
        List<ShareRedPackets> attends = shareRedPacketsService.findByGroupId(id,
                group.getStatus() == 1 ? "money DESC" : "number ASC");
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            // 我创建过的团 （创团按钮）
            ShareRedPacketsGroup myGroup = shareRedPacketsGroupService.findByInitiatorMemberId(memberInfo.getId(),
                    group.getSharePromotionId());
            // 我的此团参团记录
            ShareRedPackets myAttend = null;
            for (ShareRedPackets attend : attends) {
                if (attend.getMemberId().equals(memberInfo.getId())) {
                    myAttend = attend;
                    break;
                }
            }
            result.put("group", group);
            result.put("attends", attends);
            result.put("remaining", group.getMaxNumber() - attends.size());
            result.put("myGroupId", myGroup == null ? null : myGroup.getId());
            result.put("myAttend", myAttend);
            if (group.getStatus() == 1 && myAttend == null) {
                throw new BusinessException("啊哦~ 红包已被其他用户抢先一步！");
            }
            List<ShareRedPackets> myHistory = shareRedPacketsService.findHistory(memberInfo.getId(),
                    group.getSharePromotionId(), 0);
            if (myHistory != null && myHistory.size() > 0 && myAttend == null) {
                throw new BusinessException("您已在其他团参加瓜分，不要贪心哦！");
            }
            // 我满足条件，参团
            if (!memberInfo.getId().equals(group.getInitiatorMemberId()) && (myHistory == null || myHistory.size() == 0)
                    && group.getStatus() == 0) {
                this.attend(group, memberInfo, attends, result);
            }
        } catch (NotLoginException notLoginException) {
            result.put("group", group);
            result.put("attends", attends);
            result.put("remaining", group.getMaxNumber() - attends.size());
        }
        return result;
    }

    /**
     * 参与此团
     *
     * @param group
     * @param memberInfo
     * @param attends
     */
    private void attend(ShareRedPacketsGroup group, MemberInfo memberInfo, List<ShareRedPackets> attends,
                        ResponseResult result) {
        ShareRedPackets myAttend = new ShareRedPackets(group);
        myAttend.setMemberId(memberInfo.getId());
        myAttend.setLoginCode(memberInfo.getLoginCode());
        myAttend.setHeadPic(memberInfo.getHeadPic());
        myAttend.setNickName(memberInfo.getNickname());
        myAttend = shareRedTxService.insertShareRedPackets(myAttend);
        attends.add(myAttend);
        group.setCurrentNumber(attends.size());
        if (attends.size() == group.getMaxNumber()) {
            attends = shareRedPacketsService.findByGroupId(group.getId(), "money DESC");
            group.setStatus(1);
        }
        result.put("group", group);
        result.put("attends", attends);
        result.put("remaining", group.getMaxNumber() - attends.size());
        result.put("myAttend", myAttend);
    }

}
