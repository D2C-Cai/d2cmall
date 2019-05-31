package com.d2c.order.service;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CouponDefMapper;
import com.d2c.order.enums.CouponSourceEnum;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.Coupon.CouponStatus;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.util.serial.SerialNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service(value = "couponDefService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CouponDefServiceImpl extends ListServiceImpl<CouponDef> implements CouponDefService {

    @Autowired
    private CouponDefMapper couponDefMapper;
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private CouponDefRelationService couponDefRelationService;
    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    @Cacheable(value = "coupon_def", key = "'coupon_def_'+#id", unless = "#result == null")
    public CouponDef findById(Long id) {
        return this.findOneById(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CouponDef insert(CouponDef couponDef) {
        if (!couponDef.getType().equalsIgnoreCase(CouponDef.CouponType.DISCOUNT.toString())) {
            if (couponDef.getAmount() >= couponDef.getNeedAmount()) {
                couponDef.setNeedAmount(couponDef.getAmount() + 1);
            }
        }
        return save(couponDef);
    }

    @Override
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#couponDef.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(CouponDef couponDef) {
        if (!couponDef.getType().equalsIgnoreCase(CouponDef.CouponType.DISCOUNT.toString())) {
            if (couponDef.getAmount() >= couponDef.getNeedAmount()) {
                couponDef.setNeedAmount(couponDef.getAmount() + 1);
            }
        }
        couponDef.setClaimed(null);
        int result = this.updateNotNull(couponDef);
        if (result > 0) {
            couponDefRelationService.clearProductCouponListCache(couponDef.getId());
        }
        return result;
    }

    @Override
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#id")
    public void delete(Long id) {
        deleteById(id);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#defineId")
    public Coupon doClaimedCoupon(Long defineId, Long memberInfoId, String loginCode, String username, String channel,
                                  boolean msg) {
        Coupon coupon = this.claimedCoupon(defineId, memberInfoId, loginCode, username, channel, null, msg, true);
        if (coupon == null) {
            throw new BusinessException("优惠券已被抢光，下次早点来哦！");
        }
        return coupon;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#defineId")
    public Coupon doClaimedCoupon4Group(Long defineId, Long memberInfoId, String loginCode, String username,
                                        String channel, boolean msg) {
        Coupon coupon = null;
        try {
            coupon = this.claimedCoupon(defineId, memberInfoId, loginCode, username, channel, null, msg, true);
        } catch (BusinessException e) {
        }
        return coupon;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#defineId")
    public Coupon doClaimedCoupon4Buy(Long defineId, Long memberInfoId, String loginCode, String channel, boolean msg) {
        Coupon coupon = this.claimedCoupon(defineId, memberInfoId, loginCode, null, channel, null, msg, false);
        return coupon;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#defineId")
    public int doClaimedWxCoupon(Long defineId, Long memberInfoId, String loginCode, String wxCode, String channel) {
        int count = couponQueryService.countByWxCodeAndDefine(wxCode, defineId);
        if (count <= 0) {
            Coupon coupon = this.claimedCoupon(defineId, memberInfoId, null, loginCode, channel, wxCode, true, true);
            if (coupon == null) {
                throw new BusinessException("优惠券已被抢光，下次早点来哦！");
            }
        }
        return 0;
    }

    private Coupon claimedCoupon(Long defineId, Long memberInfoId, String loginCode, String username, String channel,
                                 String wxCode, boolean msg, boolean check) {
        CouponDef couponDef = couponDefQueryService.findById(defineId);
        if (check) {
            if (couponDef.getFree() == 0) {
                throw new BusinessException("优惠券需要付费，无法直接领取！");
            }
            this.checkCoupon(memberInfoId, loginCode, couponDef);
        }
        if (couponDef.getQuantity() > 0 && couponDefMapper.updateClaimed(couponDef.getId(), 1) > 0) {
            Coupon coupon = new Coupon();
            coupon.setMemberId(memberInfoId == null ? 0L : memberInfoId);
            coupon.setLoginCode(loginCode);
            coupon.setStatus(Coupon.CouponStatus.CLAIMED.toString());
            coupon.setClaimedTime(new Date());
            coupon.setSource(channel);
            coupon.setWxCode(wxCode);
            coupon = couponService.doInsertCoupon(coupon, couponDef, username, channel);
            if (coupon != null && msg) {
                // 优惠券到账提醒
                this.doSendCouponMsg(memberInfoId, loginCode, coupon);
            }
            return coupon;
        }
        return null;
    }

    private void checkCoupon(Long memberInfoId, String loginCode, CouponDef couponDef) {
        if (couponDef == null) {
            throw new BusinessException("优惠券已经下架，下次早点来哦！");
        }
        if (couponDef.getQuantity() > 0 && couponDef.getClaimed() >= couponDef.getQuantity()) {
            throw new BusinessException("优惠券已被抢光，下次早点来哦！");
        }
        if (!(couponDef.getEnable() == 1 && couponDef.checkClaimDate())) {
            throw new BusinessException("优惠券不在领取时间范围内！");
        }
        if (!couponDefQueryService.checkPersonalLimit(couponDef.getId(), couponDef.getClaimLimit(), memberInfoId,
                loginCode)) {
            throw new BusinessException("优惠券每人最多只能领取" + couponDef.getClaimLimit() + "张，不要贪心哦！");
        }
    }

    @Override
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#defineId")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCreatePasswdCoupon(Long defineId, int num) {
        CouponDef couponDef = couponDefQueryService.findById(defineId);
        boolean pass = couponDef.getEnable() == 1 && couponDef.checkClaimDate();
        int success = 1;
        if (pass) {
            success = couponDefMapper.updateClaimed(defineId, num);
            if (success == 0)
                throw new BusinessException("优惠券数量，超过了发放总量了，总量为：" + couponDef.getQuantity());
            // 批量生成密码券 1.密码券生成
            for (int i = 0; i < num; i++) {
                Coupon coupon = new Coupon();
                coupon.setStatus(CouponStatus.UNCLAIMED.toString());
                coupon.setPassword(SerialNumUtil.getRandomNum(8));
                coupon = couponService.doInsertCoupon(coupon, couponDef, "sys", CouponSourceEnum.PASSWD.name());
                if (coupon == null) {
                    continue;
                }
                success = success + 1;
            }
            return success;
        }
        return success;
    }

    private void doSendCouponMsg(Long memberInfoId, String loginCode, Coupon coupon) {
        String smsContent = "亲爱的用户，";
        SimpleDateFormat fmt = new SimpleDateFormat("yy/MM/dd HH:mm");
        if (coupon.getAmount() <= 0)
            return;
        if (coupon.getType().equals(CouponType.DISCOUNT.toString())) {
            float discount = (float) coupon.getAmount() / 10;
            smsContent = smsContent + "恭喜您获得D2C" + (float) (Math.round(discount * 10)) / 10 + "折优惠券一张，请在"
                    + fmt.format(coupon.getExpireDate()) + "之前使用，请进入我的->优惠券中查看";
        } else {
            smsContent = smsContent + "恭喜您获得D2C优惠券一张，面值" + coupon.getAmount() + "元，请在"
                    + fmt.format(coupon.getExpireDate()) + "之前使用 ，请进入我的->优惠券中查看";
        }
        JSONObject obj = new JSONObject();
        obj.put("money", coupon.getAmount());
        String subject = "优惠券到账提醒";
        String content = smsContent;
        PushBean pushBean = new PushBean(memberInfoId, content, 31);
        pushBean.setAppUrl("/coupon/memberCoupon");
        MsgBean msgBean = new MsgBean(memberInfoId, 31, subject, content);
        msgBean.setAppUrl("/coupon/memberCoupon");
        msgBean.setOther(obj.toJSONString());
        msgUniteService.sendPush(pushBean, msgBean);
    }

    @Override
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#defineId")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doMark(Integer mark, Long defineId) {
        int result = couponDefMapper.doMark(mark, defineId);
        if (result > 0) {
            couponDefRelationService.clearProductCouponListCache(defineId);
        }
        return result;
    }

    @Override
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSendById(Long id) {
        return couponDefMapper.sendById(id);
    }

    @Override
    @CacheEvict(value = "coupon_def", allEntries = true, condition = "#result gt 0")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doCloseExpiredCouponDef() {
        return couponDefMapper.doCloseExpired();
    }

    @Override
    @CacheEvict(value = "coupon_def", key = "'coupon_def_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doBindWxCard(String wxCardId, Long id) {
        return couponDefMapper.bindWxCard(wxCardId, id);
    }

}
