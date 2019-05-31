package com.d2c.order.service;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.logger.service.MsgUniteService;
import com.d2c.logger.support.MsgBean;
import com.d2c.logger.support.PushBean;
import com.d2c.mybatis.service.ListServiceImpl;
import com.d2c.order.dao.CouponDefGroupMapper;
import com.d2c.order.dao.CouponGroupMapper;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.model.CouponGroup;
import com.d2c.util.string.RandomUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("couponDefGroupService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class CouponDefGroupServiceImpl extends ListServiceImpl<CouponDefGroup> implements CouponDefGroupService {

    @Autowired
    private CouponGroupMapper couponGroupMapper;
    @Autowired
    private CouponDefGroupMapper couponDefGroupMapper;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponDefGroupQueryService couponDefGroupQueryService;
    @Autowired
    private MsgUniteService msgUniteService;

    @Override
    @Cacheable(value = "coupon_def_group", key = "'coupon_def_group_'+#id", unless = "#result == null")
    public CouponDefGroup findById(Long id) {
        return couponDefGroupMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public CouponDefGroup insert(CouponDefGroup group) {
        group.setCode(String.valueOf(System.currentTimeMillis()));
        String cipher = group.getCipher();
        if (!StringUtils.isEmpty(cipher)) {
            CouponDefGroup couponDefGroup = couponDefGroupMapper.findByCipher(cipher);
            if (couponDefGroup != null) {
                throw new BusinessException("暗码" + cipher + "已经存在了！");
            }
        } else {
            cipher = this.verifyRepeat(RandomUtil.getRandomString(12), null);
            group.setCipher(cipher);
        }
        return save(group);
    }

    @Override
    @CacheEvict(value = "coupon_def_group", key = "'coupon_def_group_'+#group.id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int update(CouponDefGroup group) {
        group.setClaimed(null);
        if (StringUtils.isEmpty(group.getCipher())) {
            throw new BusinessException("暗码不能为空！");
        }
        CouponDefGroup couponDefGroup = couponDefGroupMapper.findByCipher(group.getCipher());
        if (couponDefGroup != null && !couponDefGroup.getId().equals(group.getId())) {
            throw new BusinessException("暗码" + group.getCode() + "已经存在了！");
        }
        return this.updateNotNull(group);
    }

    /**
     * 插入前验证12位code是否重复
     */
    private String verifyRepeat(String code, Long id) {
        CouponDefGroup couponDefGroup = couponDefGroupMapper.findByCipher(code);
        if ((couponDefGroup != null && id == null)
                || (couponDefGroup != null && id != null && !couponDefGroup.getId().equals(id))) {
            verifyRepeat(RandomUtil.getRandomString(12), id);
        }
        return code;
    }

    @Override
    @CacheEvict(value = "coupon_def_group", key = "'coupon_def_group_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateFixDefIds(Long id, String fixDefIds) {
        return couponDefGroupMapper.updateFixDefIds(id, fixDefIds);
    }

    @Override
    @CacheEvict(value = "coupon_def_group", key = "'coupon_def_group_'+#id")
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateRandomDefIds(Long id, String randomDefIds) {
        return couponDefGroupMapper.updateRandomDefIds(id, randomDefIds);
    }

    @Override
    @CacheEvict(value = "coupon_def_group", key = "'coupon_def_group_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doUp(Long id) {
        return couponDefGroupMapper.updateStatus(id, 1);
    }

    @Override
    @CacheEvict(value = "coupon_def_group", key = "'coupon_def_group_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int doDown(Long id) {
        return couponDefGroupMapper.updateStatus(id, 0);
    }

    @Override
    @CacheEvict(value = "coupon_def_group", key = "'coupon_def_group_'+#id")
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public int delete(Long id) {
        return couponDefGroupMapper.updateStatus(id, -1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false, propagation = Propagation.REQUIRED)
    public CouponGroup doClaimedCoupon(Long groupId, Long memberInfoId, String loginCode, String admin,
                                       String channel) {
        CouponDefGroup group = couponDefGroupQueryService.findById(groupId);
        this.checkCouponGroup(memberInfoId, loginCode, group);
        if (group.getQuantity() > 0 && couponDefGroupMapper.updateClaimed(group.getId(), 1) > 0) {
            CouponGroup couponGroup = new CouponGroup();
            couponGroup.setMemberId(memberInfoId);
            couponGroup.setLoginCode(loginCode);
            couponGroup.setGroupId(groupId);
            if (!StringUtils.isEmpty(group.getFixDefIds())) {
                List<Long> fixDefIds = StringUtil.strToLongList(group.getFixDefIds());
                for (Long id : fixDefIds) {
                    this.claimedCoupon(memberInfoId, loginCode, admin, channel, couponGroup, id);
                }
            }
            if (!StringUtils.isEmpty(group.getRandomDefIds())) {
                List<Long> randomDefIds = StringUtil.strToLongList(group.getRandomDefIds());
                Collections.sort(randomDefIds);
                int randomNum = group.getRandomNum();
                if (randomNum >= randomDefIds.size()) {
                    for (Long id : randomDefIds) {
                        this.claimedCoupon(memberInfoId, loginCode, admin, channel, couponGroup, id);
                    }
                } else {
                    List<Integer> selectedDefIds = null;
                    if (!StringUtils.isEmpty(group.getRates())) {
                        selectedDefIds = this.getRandomDefIds(group.getRates(), randomNum);
                    } else {
                        selectedDefIds = new ArrayList<>(RandomUtil.getRandomNum(randomNum, randomDefIds.size()));
                    }
                    Iterator<Integer> selectedIt = selectedDefIds.iterator();
                    while (selectedIt.hasNext()) {
                        Integer index = selectedIt.next();
                        this.claimedCoupon(memberInfoId, loginCode, admin, channel, couponGroup,
                                randomDefIds.get(index));
                    }
                }
            }
            if ((couponGroup.getTotalNum1() + couponGroup.getTotalNum2()) > 0) {
                couponGroup.setNum(couponGroup.getTotalNum1() + couponGroup.getTotalNum2());
                int success = couponGroupMapper.insert(couponGroup);
                if (success > 0) {
                    this.doSendCouponGroupMsg(memberInfoId, loginCode, couponGroup.getTotalNum1(),
                            couponGroup.getTotalNum2(), couponGroup.getAmount());
                }
                return couponGroup;
            } else {
                throw new BusinessException("优惠券已被抢光，下次早点来哦！");
            }
        } else {
            throw new BusinessException("优惠券已被抢光，下次早点来哦！");
        }
    }

    private void claimedCoupon(Long memberInfoId, String loginCode, String username, String channel,
                               CouponGroup couponGroup, Long defineId) {
        Coupon coupon = null;
        coupon = couponDefService.doClaimedCoupon4Group(defineId, memberInfoId, loginCode, username, channel, false);
        if (coupon == null) {
            return;
        }
        if (CouponType.DISCOUNT.name().equals(coupon.getType())) {
            couponGroup.setTotalNum2(couponGroup.getTotalNum2() + 1);
        } else {
            couponGroup.setTotalNum1(couponGroup.getTotalNum1() + 1);
            couponGroup.setAmount(couponGroup.getAmount() + coupon.getAmount());
        }
        if (couponGroup.getNeedAmount() == 0) {
            couponGroup.setNeedAmount(coupon.getNeedAmount());
        } else {
            if (couponGroup.getNeedAmount() > coupon.getNeedAmount()) {
                couponGroup.setNeedAmount(coupon.getNeedAmount());
            }
        }
    }

    private void checkCouponGroup(Long memberInfoId, String loginCode, CouponDefGroup group) {
        if (group.getQuantity() > 0 && group.getClaimed() >= group.getQuantity()) {
            throw new BusinessException("优惠券已被抢光，下次早点来哦！");
        }
        if (group.isOver()) {
            throw new BusinessException("优惠券不在领取范围内！");
        }
        if (!couponDefGroupQueryService.getCheckClaimLimit(group, memberInfoId, loginCode)) {
            throw new BusinessException("优惠券每人最多只能领取" + group.getClaimLimit() + "张，不要贪心哦！");
        }
    }

    private List<Integer> getRandomDefIds(String rate, int randomNum) {
        // 类似 1:1:1
        String[] solutions = rate.split(":");
        int beginRate = 0;
        List<int[]> rates = new ArrayList<>();
        for (int i = 0; i < solutions.length; i++) {
            int begin = Integer.valueOf(solutions[i]);
            int rate1[] = {beginRate, beginRate + begin};
            beginRate = beginRate + begin;
            rates.add(rate1);
        }
        List<Integer> selectedDefIds = new ArrayList<>();
        HashSet<Integer> randomSet = RandomUtil.getRandomNum(randomNum, beginRate);
        Iterator<Integer> randomIt = randomSet.iterator();
        while (randomIt.hasNext()) {
            Integer index = randomIt.next();
            for (int j = 0; j < rates.size(); j++) {
                int[] rate2 = rates.get(j);
                if (index >= rate2[0] && index < rate2[1]) {
                    selectedDefIds.add(j);
                    break;
                }
            }
        }
        return selectedDefIds;
    }

    private void doSendCouponGroupMsg(Long memberInfoId, String mobile, int totalNum1, int totalNum2, int totalAmount) {
        String smsContent = "亲爱的用户，";
        if ((totalNum1 + totalNum2) > 0) {
            smsContent = smsContent + "恭喜您获得D2C优惠券" + (totalNum1 + totalNum2) + "张";
            if (totalNum2 > 0) {
                smsContent = smsContent + "（含折扣券" + totalNum2 + "张）";
            }
            smsContent = smsContent + "，累计面值" + totalAmount + "元，已经放入您的D2C账户，下载D2C APP，进入我的->优惠券中查看 ";
            JSONObject obj = new JSONObject();
            obj.put("money", totalAmount);
            String subject = "优惠券到账提醒";
            String content = smsContent;
            PushBean pushBean = new PushBean(memberInfoId, content, 31);
            pushBean.setAppUrl("/coupon/myCoupon");
            MsgBean msgBean = new MsgBean(memberInfoId, 31, subject, content);
            msgBean.setAppUrl("/coupon/myCoupon");
            msgBean.setOther(obj.toJSONString());
            msgUniteService.sendPush(pushBean, msgBean);
        }
    }

}
