package com.d2c.order.service.tx;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.service.MemberIntegrationService;
import com.d2c.order.enums.CouponSourceEnum;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.RedPacketsItem;
import com.d2c.order.model.RedPacketsItem.BusinessTypeEnum;
import com.d2c.order.service.CouponDefQueryService;
import com.d2c.order.service.CouponDefService;
import com.d2c.product.model.ExternalCard;
import com.d2c.product.model.PointProduct;
import com.d2c.product.model.PointProduct.PointProductTypeEnum;
import com.d2c.product.service.ExternalCardService;
import com.d2c.product.service.PointProductService;
import com.d2c.util.serial.SerialNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service(protocol = {"dubbo"})
public class PointExchangeTxServiceImpl implements PointExchangeTxService {

    @Autowired
    private PointProductService pointProductService;
    @Autowired
    private MemberIntegrationService memberIntegrationService;
    @Reference
    private AccountTxService accountTxService;
    @Autowired
    private ExternalCardService externalCardService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;

    @Override
    @TxTransaction(isStart = true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, JSONObject> doExchange(PointProduct pointProduct, MemberInfo memberInfo, Integer quantity) {
        Map<String, JSONObject> map = new HashMap<>();
        int success = pointProductService.updateCount(pointProduct.getId(), -1);
        if (success > 0) {
            String sn = SerialNumUtil.buildPointExchangeSn();
            PointProductTypeEnum type = PointProductTypeEnum.valueOf(pointProduct.getType());
            String remark = null;
            Long transactionId = null;
            switch (type) {
                case CARD:
                    success = externalCardService.doUse(memberInfo.getId(), sn, pointProduct.getSourceId(),
                            pointProduct.getId());
                    if (success < 1) {
                        throw new BusinessException("该商品已经兑完！");
                    } else {
                        ExternalCard card = externalCardService.findBySn(memberInfo.getId(), sn);
                        transactionId = card.getId();
                        remark = card.getCode();
                        map.put("card", card.toJson());
                    }
                    break;
                case COUPON:
                    CouponDef couponDef = couponDefQueryService.findById(pointProduct.getProductId());
                    if (couponDef == null || couponDef.getId() == 1) {
                        throw new BusinessException("该优惠券已失效！");
                    }
                    Coupon coupon = couponDefService.doClaimedCoupon(couponDef.getId(), memberInfo.getId(),
                            memberInfo.getLoginCode(), memberInfo.getLoginCode(), CouponSourceEnum.D2CMALL.name(), false);
                    transactionId = coupon.getId();
                    map.put("coupon", coupon.toJson());
                    break;
                case RED:
                    RedPacketsItem redPacketsItem = accountTxService.doSuccessRed(memberInfo.getId(),
                            BusinessTypeEnum.POINT.name(), pointProduct.getAmount().multiply(new BigDecimal(quantity)));
                    if (redPacketsItem == null || redPacketsItem.getId() < 1) {
                        throw new BusinessException("该红包已失效！");
                    }
                    transactionId = redPacketsItem.getId();
                    map.put("redItem", redPacketsItem.toJson());
                    break;
                default:
                    break;
            }
            if (transactionId != null) {
                MemberIntegration memberIntegration = new MemberIntegration(PointRuleTypeEnum.EXCHANGE,
                        memberInfo.getId(), memberInfo.getLoginCode(), transactionId, new Date());
                memberIntegration.setAmount(pointProduct.getAmount());
                memberIntegration.setProductId(pointProduct.getId());
                memberIntegration.setProductName(pointProduct.getName());
                memberIntegration.setProductType(pointProduct.getType());
                memberIntegration.setProductQuantity(quantity);
                memberIntegration.setPic(pointProduct.getIntroPic());
                memberIntegration.setRemark(remark);
                success = memberIntegrationService.addIntegration(memberIntegration, PointRuleTypeEnum.EXCHANGE, null,
                        pointProduct.getPoint() * quantity, "积分兑换商品");
                if (success < 1) {
                    throw new BusinessException("对不起您的积分不足！");
                }
            }
        } else {
            throw new BusinessException("该商品已售罄，请选择其他商品！");
        }
        return map;
    }

}
