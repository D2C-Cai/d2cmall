package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.model.AwardRabateLog;
import com.d2c.logger.service.AwardRabateLogService;
import com.d2c.member.service.MemberLottoService;
import com.d2c.order.model.AwardRecord;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.RedPacketsItem.BusinessTypeEnum;
import com.d2c.order.service.AwardRecordService;
import com.d2c.order.service.CouponDefService;
import com.d2c.product.model.AwardProduct.AwardType;
import com.d2c.product.service.AwardProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service(protocol = {"dubbo"})
public class AwardTxServiceImpl implements AwardTxService {

    @Autowired
    private MemberLottoService memberLottoService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private AwardProductService awardProductService;
    @Autowired
    private AwardRecordService awardRecordService;
    @Autowired
    private AwardRabateLogService awardRabateLogService;
    @Reference
    private AccountTxService accountTxService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doLotteryFinished(AwardRecord awardRecord) {
        // 抽奖机会-1
        int success = memberLottoService.doDecreaseOpportunity(awardRecord.getMemberId(), awardRecord.getSessionId());
        if (success == 0) {
            throw new BusinessException("你已经没有抽奖次数了。");
        }
        // 奖品数量-1
        success = awardProductService.updateCouponQuantity(awardRecord.getAwardId());
        if (success == 0) {
            throw new BusinessException("奖品已经被抢完了，请重新抽奖。");
        }
        awardRecordService.insert(awardRecord);
        // 赠送奖品
        this.processGiveAwardProduct(awardRecord);
        return success;
    }

    /**
     * 赠送奖品
     *
     * @param awardRecord
     */
    private void processGiveAwardProduct(AwardRecord awardRecord) {
        if (AwardType.OBJECT.name().equals(awardRecord.getAwardProductType())) {
            return;
        }
        if (AwardType.COUPON.name().equals(awardRecord.getAwardProductType())) {
            Long couponDefId = Long.parseLong(awardRecord.getAwardProductParam().split("-")[0]);
            Coupon coupon = couponDefService.doClaimedCoupon(couponDefId, awardRecord.getMemberId(),
                    awardRecord.getLoginNo(), "sys", "抽奖活动", true);
            if (coupon == null) {
                throw new BusinessException("不满足发放条件！\\n请检查优惠券发行总量，单人限领以及限领时间！");
            }
        }
        if (AwardType.RED.name().equals(awardRecord.getAwardProductType())) {
            accountTxService.doSuccessRed(awardRecord.getMemberId(), BusinessTypeEnum.AWARD.name(),
                    new BigDecimal(awardRecord.getAwardProductParam()));
        }
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doRecome(Long memberId, BigDecimal amount, String uniqueMark, String operator) throws Exception {
        if (awardRabateLogService.findByUniqueMark(uniqueMark) != null) {
            throw new Exception("该笔返利记录已经存在");
        }
        accountTxService.doSuccessRed(memberId, BusinessTypeEnum.REBATE.name(), amount);
        AwardRabateLog awardRabateLog = new AwardRabateLog();
        awardRabateLog.setUniqueMark(uniqueMark);
        awardRabateLog.setRabate(amount);
        awardRabateLog.setMemberId(memberId);
        awardRabateLog.setCreator(operator);
        awardRabateLogService.insert(awardRabateLog);
        return 1;
    }

}
