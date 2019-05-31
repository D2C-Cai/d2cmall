package com.d2c.order.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CouponMapper;
import com.d2c.order.enums.CouponSourceEnum;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.Coupon.CouponStatus;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.order.model.CouponDef.ManagerStatus;
import com.d2c.order.query.CouponSearcher;
import com.d2c.util.string.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("couponService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CouponServiceImpl extends ListServiceImpl<Coupon> implements CouponService {

    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private StoreService storeService;

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Coupon insert(Coupon coupon) {
        return save(coupon);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Coupon doInsertCoupon(Coupon coupon, CouponDef couponDef, String sendor, String channel) {
        Date now = new Date();
        coupon.setDefineId(couponDef.getId());
        if (couponDef.getType().equals(CouponType.PASSWORD.toString())) {
            coupon.setCode(this.verifyRepeat(RandomUtil.getRandomString(8)));
        } else {
            coupon.setCode("S" + System.currentTimeMillis() + "-" + coupon.getMemberId());
        }
        coupon.setName(couponDef.getName());
        coupon.setType(couponDef.getType());
        coupon.setSource(channel);
        coupon.setCheckAssociation(couponDef.getCheckAssociation());
        coupon.setExclude(couponDef.getExclude());
        if (couponDef.getType().equals(CouponType.CASH.toString())
                || coupon.getStatus().equals(CouponStatus.CLAIMED.toString())) {
            coupon.setManagerStatus(ManagerStatus.SENDED.toString());
            coupon.setSendDate(now);
            coupon.setSendor(sendor);
        } else {
            // 密码券需要手工发放
            coupon.setManagerStatus(ManagerStatus.INIT.toString());
        }
        // 使用期限 优先级 顺延期限> 固定期限
        if (couponDef.getActiveDay() <= 0 && couponDef.getActiveHour() <= 0) {
            coupon.setEnableDate(couponDef.getEnableDate());
            coupon.setExpireDate(couponDef.getExpireDate());
        } else {
            if (coupon.getStatus().equals(CouponStatus.CLAIMED.toString())) {
                Date begin = coupon.getClaimedTime();
                Calendar c = Calendar.getInstance();
                c.setTime(begin);
                c.set(Calendar.DATE, c.get(Calendar.DATE) + couponDef.getActiveDay());
                Date end = c.getTime();
                end = new Date(end.getTime() + 1000L * 60 * 60 * couponDef.getActiveHour());
                coupon.setEnableDate(begin);
                coupon.setExpireDate(end);
            }
        }
        // 若为抽奖性质，则面额随机
        if (couponDef.getRandom() == true) {
            Random rad = new Random();
            coupon.setAmount(rad.nextInt(couponDef.getAmount()) + 1);
        } else {
            coupon.setAmount(couponDef.getAmount());
        }
        coupon.setNeedAmount(couponDef.getNeedAmount());
        if (coupon.getAmount() >= coupon.getNeedAmount() && !coupon.getType().equals(CouponType.DISCOUNT.toString())) {
            coupon.setNeedAmount(coupon.getAmount());
        }
        if (channel != null && channel.equals(CouponSourceEnum.PURCHASE.toString())) {
            coupon.setPrice(couponDef.getPrice());
        }
        coupon.setRemark(couponDef.getRemark());
        coupon.setSubTitle(couponDef.getSubTitle());
        coupon.setTransfer(couponDef.getTransfer());
        coupon.setRedirectUrl(couponDef.getRedirectUrl());
        return this.insert(coupon);
    }

    /**
     * 插入前验证8位code是否重复
     */
    private String verifyRepeat(String code) {
        if (couponMapper.findByCode(code) != null) {
            verifyRepeat(RandomUtil.getRandomString(8));
        }
        return code;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Coupon doConvertCoupon(String password, Long memberInfoId) {
        // 获取一张未过期已生成的密码券
        CouponSearcher couponSearcher = new CouponSearcher();
        couponSearcher.setPassword(password);
        couponSearcher.setStatus(new String[]{CouponStatus.UNCLAIMED.toString()});
        couponSearcher.setType(new String[]{CouponType.PASSWORD.toString()});
        couponSearcher.setManagerStatus(new String[]{ManagerStatus.SENDED.toString()});
        Date now = new Date();
        PageModel page = new PageModel();
        page.setPageSize(1);
        List<Coupon> list = couponMapper.findBySearcher(couponSearcher, page);
        Coupon coupon = null;
        // 获取成功
        if (list != null && list.size() > 0) {
            coupon = list.get(0);
            coupon.setMemberId(memberInfoId);
            coupon.setClaimedTime(now);
            coupon.setStatus(CouponStatus.CLAIMED.toString());
            // 处理使用期限
            CouponDef couponDef = couponDefQueryService.findById(coupon.getDefineId());
            boolean pass = couponDef.getEnable() == 1 && couponDef.checkClaimDate() && couponDefQueryService
                    .checkPersonalLimit(couponDef.getId(), couponDef.getClaimLimit(), memberInfoId, null);
            if (!pass)
                throw new BusinessException("兑换不成功");
            if (couponDef.getActiveDay() <= 0 && couponDef.getActiveHour() <= 0) {
                coupon.setEnableDate(couponDef.getEnableDate());
                coupon.setExpireDate(couponDef.getExpireDate());
            } else {
                Date begin = coupon.getClaimedTime();
                Calendar c = Calendar.getInstance();
                c.setTime(begin);
                c.set(Calendar.DATE, c.get(Calendar.DATE) + couponDef.getActiveDay());
                Date end = c.getTime();
                end = new Date(end.getTime() + 1000L * 60 * 60 * couponDef.getActiveHour());
                coupon.setEnableDate(begin);
                coupon.setExpireDate(end);
            }
            this.updateNotNull(coupon);
            return coupon;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doGetBackCoupon(String loginCode, Long memberInfoId) {
        return couponMapper.doGetBackCoupon(loginCode, memberInfoId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSendCoupon(Long id, String sendor, String sendmark) {
        return couponMapper.doSendCoupon(id, sendor, sendmark);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSendCouponByDefineId(Long defId, String sendor, String sendMark) {
        return couponMapper.doSendCouponByDefineId(defId, sendor, sendMark);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doActivateCoupon(Long couponId, Long memberInfoId) {
        Date now = new Date();
        // 处理使用时间
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if (coupon == null) {
            return 0;
        }
        coupon.setClaimedTime(now);
        CouponDef couponDef = couponDefQueryService.findById(coupon.getDefineId());
        if (couponDef.getActiveDay() <= 0 && couponDef.getActiveHour() <= 0) {
            coupon.setEnableDate(couponDef.getEnableDate());
            coupon.setExpireDate(couponDef.getExpireDate());
        } else {
            Date begin = coupon.getClaimedTime();
            Calendar c = Calendar.getInstance();
            c.setTime(begin);
            c.set(Calendar.DATE, c.get(Calendar.DATE) + couponDef.getActiveDay());
            Date end = c.getTime();
            end = new Date(end.getTime() + 1000L * 60 * 60 * couponDef.getActiveHour());
            coupon.setEnableDate(begin);
            coupon.setExpireDate(end);
        }
        this.updateNotNull(coupon);
        return couponMapper.doActivateCoupon(couponId, memberInfoId, now);
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public BigDecimal doLockCoupon(Long orderId, String orderSn, BigDecimal orderTotalAmount, String code) {
        BigDecimal result = new BigDecimal(0);
        Coupon coupon = couponMapper.findByCode(code);
        if (coupon == null) {
            return result;
        }
        // 目前仅支持现金抵扣
        if (coupon.getStatus().equals(CouponStatus.CLAIMED.toString()) && coupon.getExpireDate().after(new Date())) {
            coupon.setStatus(CouponStatus.LOCKED.toString());
            coupon.setConsumesTime(new Date());
            coupon.setOrderId(orderId);
            coupon.setOrderSn(orderSn);
            int success = couponMapper.doLockCoupon(coupon);
            if (success > 0) {
                return coupon.getCalAmount(orderTotalAmount);
            }
        }
        return result;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doUnlockCoupon(Long orderId) {
        Coupon coupon = couponQueryService.findByOrderId(orderId, Coupon.CouponStatus.LOCKED);
        if (coupon != null) {
            return couponMapper.doUnlockCoupon(orderId, coupon.getId());
        }
        return 0;
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doReleaseCoupon(Long orderId) {
        Coupon coupon = couponQueryService.findByOrderId(orderId, Coupon.CouponStatus.USED);
        if (coupon != null) {
            return couponMapper.doReleaseCoupon(orderId, coupon.getId());
        }
        return 0;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Coupon doUsedCoupon(String code, Long memberInfoId, Long storeId) {
        if (code == null) {
            return null;
        }
        Coupon coupon = couponMapper.findByCode(code.toLowerCase());
        if (coupon != null) {
            if (coupon.getExpired() > 0) {
                throw new BusinessException("优惠券已过期，使用不成功！");
            }
            if (coupon.getStatus().equals(CouponStatus.CLAIMED.toString())) {
                com.d2c.order.model.Store store = storeService.findById(storeId);
                coupon.setConsumesTime(new Date());
                coupon.setStatus(CouponStatus.USED.toString());
                coupon.setStoreId(store.getId());
                coupon.setStoreCode(store.getCode());
                coupon.setStoreName(store.getName());
                couponMapper.doUsedCoupon(coupon);
                return coupon;
            } else {
                throw new BusinessException("优惠券已使用！");
            }
        }
        throw new BusinessException("优惠券不存在！");
    }

    @Override
    @TxTransaction
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doSuccessCoupon(Long couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        // 目前仅支持现金抵扣
        int result = 0;
        if (coupon.getStatus().equals(CouponStatus.LOCKED.toString()) && coupon.getExpireDate().after(new Date())) {
            coupon.setStatus(CouponStatus.USED.toString());
            coupon.setConsumesTime(new Date());
            result = couponMapper.doUsedCoupon(coupon);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doInvalidCoupon(Long id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        int success = 0;
        if (coupon != null && (coupon.getStatus() != null && CouponStatus.valueOf(coupon.getStatus()).getCode() <= 1)) {
            success = couponMapper.doInvalidCoupon(id);
        }
        return success;
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doTransferCoupon(Long memberInfoId, Long couponId) {
        return couponMapper.doTransferCoupon(memberInfoId, couponId);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCloseExpiredCoupon() {
        return couponMapper.doCloseExpiredCoupon();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doCopy2CouponHistory() {
        return couponMapper.doCopy2CouponHistory();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doDeleteExpiredCoupon() {
        return couponMapper.doDeleteExpiredCoupon();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int doTransfer(Long couponId, Long toMemberId, String toLoginCode) {
        return couponMapper.doTransfer(couponId, toMemberId, toLoginCode);
    }

}
