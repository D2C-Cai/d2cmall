package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.model.Account;
import com.d2c.member.model.CollectionCardRecord;
import com.d2c.member.model.CollectionCardRecord.StageEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AccountService;
import com.d2c.member.service.CollectionCardRecordService;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.RedPacketsItem;
import com.d2c.order.model.RedPacketsItem.BusinessTypeEnum;
import com.d2c.order.service.CouponDefService;
import com.d2c.order.service.RedPacketsItemService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service(protocol = {"dubbo"})
public class CollectionCardTxServiceImpl implements CollectionCardTxService {

    private static final Date RED_DATE = DateUtil.str2day("2019-05-21");
    @Autowired
    private AccountService accountService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private RedPacketsItemService redPacketsItemService;
    @Autowired
    private CollectionCardRecordService collectionCardRecordService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSendStageAward(MemberInfo memberInfo, StageEnum stage, JSONObject award) {
        String awardName = null;
        if ("RED".equals(award.getString("type"))) {
            int red = award.getInteger("amount");
            if (red < 0 || red > 100) {
                throw new BusinessException("系统繁忙，请重新抽取！");
            }
            Account account = accountService.findByMemberId(memberInfo.getId());
            accountService.doUpdateRedAmount(account.getId(), new BigDecimal(red), RED_DATE);
            awardName = "抽奖获得" + award.getString("amount") + "元红包";
            RedPacketsItem redPacketsItem = new RedPacketsItem(account, BusinessTypeEnum.COLLECTCARD.name());
            redPacketsItem.setAmount(new BigDecimal(red));
            redPacketsItemService.insert(redPacketsItem);
        } else {
            Long couponDefId = award.getLong("couponId");
            Coupon coupon = couponDefService.doClaimedCoupon(couponDefId, memberInfo.getId(), memberInfo.getLoginCode(),
                    "sys", "集卡活动", true);
            if (coupon == null) {
                throw new BusinessException("不满足发放条件！\\n请检查优惠券发行总量，单人限领以及限领时间！");
            }
        }
        CollectionCardRecord collectionCardRecord = new CollectionCardRecord();
        collectionCardRecord.setMemberId(memberInfo.getId());
        collectionCardRecord.setMemberName(memberInfo.getNickname());
        collectionCardRecord.setStage(stage.name());
        collectionCardRecord.setAwardName(awardName);
        collectionCardRecord.setPromotionId(2L);
        try {
            collectionCardRecordService.insert(collectionCardRecord);
        } catch (Exception e) {
            throw new BusinessException("你已经抽取了该奖励！");
        }
        return 1;
    }

}
