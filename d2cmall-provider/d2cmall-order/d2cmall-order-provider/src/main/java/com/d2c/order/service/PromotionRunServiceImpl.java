package com.d2c.order.service;

import com.d2c.member.model.DiscountSetting;
import com.d2c.member.model.DiscountSetting.DiscountType;
import com.d2c.member.model.Distributor;
import com.d2c.member.service.DiscountSettingService;
import com.d2c.member.service.DistributorService;
import com.d2c.order.handle.*;
import com.d2c.order.model.Order.OrderType;
import com.d2c.order.model.base.IPromotionInterface;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.model.ProductComb;
import com.d2c.product.model.Promotion;
import com.d2c.product.model.Promotion.PromotionScope;
import com.d2c.product.service.FlashPromotionService;
import com.d2c.product.service.ProductCombService;
import com.d2c.product.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("promotionRunService")
@Transactional(readOnly = true, rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
public class PromotionRunServiceImpl implements PromotionRunService {

    @Autowired
    private DistributorService distributorService;
    @Autowired
    private DiscountSettingService discountSettingService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Autowired
    private ProductCombService productCombService;

    @Override
    public PromotionCalculateResult getPromotionsByOrder(Long distributorId, Long orderId,
                                                         List<IPromotionInterface> promotionConditions) {
        PromotionCalculateResult result = new PromotionCalculateResult();
        result.setOrderId(orderId);
        if (distributorId != null && distributorId > 0) {
            Distributor distributor = distributorService.findById(distributorId);
            if (distributor != null && distributor.getStatus() == 1) {
                return this.getDistributorPromotions(distributor, promotionConditions, result);
            }
        }
        return this.getNormalPromotions(promotionConditions, result);
    }

    protected PromotionCalculateResult getNormalPromotions(List<IPromotionInterface> promotionConditions,
                                                           PromotionCalculateResult result) {
        if (promotionConditions == null)
            return result;
        Map<Promotion, Set<PromotionCalculateItem>> xoffYPackage = new HashMap<>();
        for (IPromotionInterface pc : promotionConditions) {
            PromotionCalculateItem calItem = new PromotionCalculateItem(pc);
            boolean productDiscount = false;
            // 针对商品的限时购 - 价格处理
            if (!productDiscount && pc.getFlashPromotionId() != null && pc.getFlashPromotionId() > 0) {
                FlashPromotion flashPromotion = flashPromotionService.findById(pc.getFlashPromotionId());
                if (flashPromotion != null && !flashPromotion.isOver()) {
                    if (calItem.getFlashPrice() != null && calItem.getFlashPrice().compareTo(new BigDecimal(0)) > 0
                            && calItem.getProductPrice().compareTo(calItem.getFlashPrice()) >= 0) {
                        BigDecimal off = calItem.getProductPrice().subtract(calItem.getFlashPrice());
                        calItem.setPromotionPrice(off);
                        calItem.setPromotionAmount(off.multiply(new BigDecimal(calItem.getQuantity())));
                        calItem.setPromotionName(flashPromotion.getName());
                        calItem.setFlashPromotion(flashPromotion);
                        calItem.setFlashStock(pc.getFlashStock());
                        productDiscount = true;
                        result.setLowStock(pc.getFlashStock() <= 0);
                    }
                }
            }
            // 针对商品的促销 - 价格处理（直降或打折）
            if (!productDiscount && pc.getGoodPromotionId() != null && pc.getGoodPromotionId() > 0) {
                Promotion goodPromotion = promotionService.findSimpleById(pc.getGoodPromotionId());
                if (goodPromotion != null && !goodPromotion.isOver()) {
                    calItem.setGoodPromotion(goodPromotion);
                    Object promtionMethod = this.getPromotionMethod(goodPromotion);
                    ProcessPriceBehavior behavior = (ProcessPriceBehavior) promtionMethod;
                    behavior.process(goodPromotion, calItem);
                    productDiscount = true;
                }
            }
            // 针对订单的促销 - 满减 分类
            if (pc.getOrderPromotionId() != null && pc.getOrderPromotionId() > 0) {
                Promotion orderPromotion = promotionService.findSimpleById(pc.getOrderPromotionId());
                if (orderPromotion != null && !orderPromotion.isOver()) {
                    calItem.setOrderPromotion(orderPromotion);
                    Object promtionMethod = this.getPromotionMethod(orderPromotion);
                    if (PromotionScope.ORDER.getCode() == orderPromotion.getPromotionScope()
                            && promtionMethod instanceof XoffYProcessPriceBehavior) {
                        if (xoffYPackage.get(orderPromotion) == null)
                            xoffYPackage.put(orderPromotion, new HashSet<PromotionCalculateItem>());
                        xoffYPackage.get(orderPromotion).add(calItem);
                    }
                }
            }
            result.addItem(calItem);
        }
        // 处理满减
        BigDecimal orderPromotionAmount = new BigDecimal(0);
        for (Promotion promotion : xoffYPackage.keySet()) {
            Set<PromotionCalculateItem> set = xoffYPackage.get(promotion);
            Object promtionMethod = this.getPromotionMethod(promotion);
            XoffYProcessPriceBehavior behavior = (XoffYProcessPriceBehavior) promtionMethod;
            BigDecimal currenPromotion = behavior.process(promotion, set);
            if (currenPromotion.compareTo(new BigDecimal(0)) <= 0) {
                continue;
            }
            // 所有参加满减商品的总金额（减去商品折扣）
            BigDecimal totalProductAmount = new BigDecimal(0);
            for (PromotionCalculateItem item : set) {
                totalProductAmount = totalProductAmount
                        .add(item.getTotalProductAmount().subtract(item.getPromotionAmount()));
            }
            // 金额分摊
            currenPromotion = behavior.splitAmount(currenPromotion, totalProductAmount, promotion, set, result);
            orderPromotionAmount = orderPromotionAmount.add(currenPromotion);
        }
        // 满减优惠金额
        result.setOrderPromotionAmount(orderPromotionAmount);
        result.setTotalAmount(result.getTotalAmount().subtract(orderPromotionAmount));
        return result;
    }

    protected PromotionCalculateResult getDistributorPromotions(Distributor distributor,
                                                                List<IPromotionInterface> promotionConditions, PromotionCalculateResult result) {
        List<Long> productIds = new ArrayList<>();
        List<Long> brandIds = new ArrayList<>();
        for (IPromotionInterface pc : promotionConditions) {
            productIds.add(pc.getProductId());
            brandIds.add(pc.getDesignerId());
        }
        // 所有折扣规则 优先级（经销商 > 折扣组）
        List<DiscountSetting> productSettings = discountSettingService.findDiscountSettings(distributor.getId(),
                DiscountType.PRODUCT.name(), productIds);
        List<DiscountSetting> brandSettings = discountSettingService.findDiscountSettings(distributor.getId(),
                DiscountType.DESIGNER.name(), brandIds);
        List<DiscountSetting> allSettings = discountSettingService.findDiscountSettings(distributor.getId(),
                DiscountType.ALL.name(), null);
        // 颗粒度控制 优先于 折扣归属
        // 对所有的折扣分类 优先级 （商品（经销商 > 折扣组） > 品牌（经销商 > 折扣组） > 全场（经销商 > 折扣组））
        Map<String, DiscountSetting> disCountMap = classifyDiscountSetting(productSettings, brandSettings, allSettings);
        for (IPromotionInterface pc : promotionConditions) {
            PromotionCalculateItem item = processDiscountSetting(disCountMap, pc);
            if (item != null) {
                result.addItem(item);
            }
        }
        // 计算成功后，直接是四舍五入
        boolean freeFee = distributor.getFreeShipFee() == 1;
        if (!freeFee) {
            result.setShipFee(new BigDecimal(10));
        }
        result.setType(OrderType.distribution.name());
        return result;
    }

    @Override
    public PromotionCalculateItem getPromotionByItem(Long distributorId, IPromotionInterface promotionCondition) {
        return this.getNormalPromotionByItem(promotionCondition);
    }

    protected PromotionCalculateItem getNormalPromotionByItem(IPromotionInterface promotionCondition) {
        PromotionCalculateItem calItem = new PromotionCalculateItem(promotionCondition);
        boolean productDiscount = false;
        // 针对商品的限时购 - 价格处理
        if (!productDiscount && promotionCondition.getFlashPromotionId() != null
                && promotionCondition.getFlashPromotionId() > 0) {
            FlashPromotion flashPromotion = flashPromotionService.findById(promotionCondition.getFlashPromotionId());
            if (flashPromotion != null && !flashPromotion.isOver()) {
                if (calItem.getFlashPrice() != null && calItem.getFlashPrice().compareTo(new BigDecimal(0)) > 0
                        && calItem.getProductPrice().compareTo(calItem.getFlashPrice()) >= 0) {
                    BigDecimal off = calItem.getProductPrice().subtract(calItem.getFlashPrice());
                    calItem.setPromotionPrice(off);
                    calItem.setPromotionAmount(off.multiply(new BigDecimal(calItem.getQuantity())));
                    calItem.setPromotionName(flashPromotion.getName());
                    calItem.setFlashPromotion(flashPromotion);
                    calItem.setFlashStock(promotionCondition.getFlashStock());
                    productDiscount = true;
                }
            }
        }
        // 针对商品的促销 - 价格处理（直降或打折）
        if (!productDiscount && promotionCondition.getGoodPromotionId() != null
                && promotionCondition.getGoodPromotionId() > 0) {
            Promotion goodPromotion = promotionService.findSimpleById(promotionCondition.getGoodPromotionId());
            if (goodPromotion != null && !goodPromotion.isOver()) {
                calItem.setGoodPromotion(goodPromotion);
                Object promtionMethod = this.getPromotionMethod(goodPromotion);
                ProcessPriceBehavior behavior = (ProcessPriceBehavior) promtionMethod;
                behavior.process(goodPromotion, calItem);
                productDiscount = true;
            }
        }
        // 针对订单的促销 - 满减 分类
        List<Promotion> choiceOrderPromotions = new ArrayList<>();
        if (promotionCondition.getOrderPromotionId() != null && promotionCondition.getOrderPromotionId() > 0) {
            Promotion orderPromotion = promotionService.findSimpleById(promotionCondition.getOrderPromotionId());
            if (orderPromotion != null && !orderPromotion.isOver()) {
                calItem.setOrderPromotion(orderPromotion);
                Object promtionMethod = this.getPromotionMethod(orderPromotion);
                if (PromotionScope.ORDER.getCode() == orderPromotion.getPromotionScope()
                        && promtionMethod instanceof XoffYProcessPriceBehavior) {
                    choiceOrderPromotions.add(orderPromotion);
                }
            }
        }
        calItem.setChoiceOrderPromotions(choiceOrderPromotions);
        return calItem;
    }

    protected PromotionCalculateItem getDistributorPromotionItem(Long distributorId,
                                                                 IPromotionInterface promotionCondition) {
        List<Long> productIds = new ArrayList<>();
        List<Long> brandIds = new ArrayList<>();
        productIds.add(promotionCondition.getProductId());
        brandIds.add(promotionCondition.getDesignerId());
        // 所有折扣规则 优先级（经销商 > 折扣组）
        List<DiscountSetting> productSettings = discountSettingService.findDiscountSettings(distributorId,
                DiscountType.PRODUCT.name(), productIds);
        List<DiscountSetting> brandSettings = discountSettingService.findDiscountSettings(distributorId,
                DiscountType.DESIGNER.name(), brandIds);
        List<DiscountSetting> allSettings = discountSettingService.findDiscountSettings(distributorId,
                DiscountType.ALL.name(), null);
        // 颗粒度控制 优先于 折扣归属
        // 对所有的折扣分类 优先级 （商品（经销商 > 折扣组） > 品牌（经销商 > 折扣组） > 全场（经销商 > 折扣组））
        Map<String, DiscountSetting> disCountMap = classifyDiscountSetting(productSettings, brandSettings, allSettings);
        PromotionCalculateItem item = processDiscountSetting(disCountMap, promotionCondition);
        // 计算成功后，直接是四舍五入
        return item;
    }

    private Map<String, DiscountSetting> classifyDiscountSetting(List<DiscountSetting> productSettings,
                                                                 List<DiscountSetting> brandSettings, List<DiscountSetting> allSettings) {
        Map<String, DiscountSetting> map = new HashMap<>();
        for (DiscountSetting all : allSettings) {
            map.put(all.getDisType(), all);
        }
        for (DiscountSetting brand : brandSettings) {
            map.put(brand.getDisType() + "-" + brand.getTargetId(), brand);
        }
        for (DiscountSetting product : productSettings) {
            map.put(product.getDisType() + "-" + product.getTargetId(), product);
        }
        return map;
    }

    private PromotionCalculateItem processDiscountSetting(Map<String, DiscountSetting> disCountMap,
                                                          IPromotionInterface promotionCondition) {
        // 计算商品优惠
        DiscountSetting disSetting = null;
        String itemKey = DiscountSetting.DiscountType.ALL.name();
        if (disCountMap.get(itemKey) != null) {
            disSetting = disCountMap.get(itemKey);
        }
        itemKey = DiscountSetting.DiscountType.DESIGNER.name() + "-" + promotionCondition.getDesignerId();
        if (disCountMap.get(itemKey) != null) {
            disSetting = disCountMap.get(itemKey);
        }
        itemKey = DiscountSetting.DiscountType.PRODUCT.name() + "-" + promotionCondition.getProductId();
        if (disCountMap.get(itemKey) != null) {
            disSetting = disCountMap.get(itemKey);
        }
        PromotionCalculateItem item = null;
        if (disSetting != null) {
            item = calculateDiscountSetting(disSetting, promotionCondition);
        } else {
            item = new PromotionCalculateItem(promotionCondition);
            item.setProductPrice(promotionCondition.getOriginalPrice());
            item.setTotalProductAmount(item.getProductPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return item;
    }

    private PromotionCalculateItem calculateDiscountSetting(DiscountSetting discountSetting,
                                                            IPromotionInterface promotionCondition) {
        BigDecimal rate = discountSetting.getDiscount();
        if ((rate.compareTo(new BigDecimal(0)) < 0 || rate.compareTo(new BigDecimal(1)) > 0)) {
            return null;
        }
        PromotionCalculateItem item = new PromotionCalculateItem(promotionCondition);
        item.setProductPrice(promotionCondition.getOriginalPrice());
        item.setQuantity(promotionCondition.getQuantity());
        // 按四舍五入，全程折扣，目前取得是吊牌价的折扣
        BigDecimal promotionPrice = item.getProductPrice().multiply(new BigDecimal(1).subtract(rate));
        item.setPromotionPrice(promotionPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
        BigDecimal promotionAmount = promotionPrice.multiply(new BigDecimal(promotionCondition.getQuantity()))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        item.setTotalProductAmount(item.getProductPrice().multiply(new BigDecimal(promotionCondition.getQuantity())));
        item.setPromotionAmount(promotionAmount);
        item.setPromotionName("经销商折扣" + discountSetting.getDisType());
        return item;
    }

    private Object getPromotionMethod(Promotion pro) {
        Object promotionMethod = null;
        switch (pro.getEnumPromotionType()) {
            case X_OFF_Y_STEP:
                promotionMethod = new XoffYStepMethod();
                break;
            case X_OFF_Y_UNLIMITED:
                promotionMethod = new XoffYUnlimitedMethod();
                break;
            case X_OFF_Y_FULLPCS:
                promotionMethod = new XoffYFullPscMethod();
                break;
            case X_OFF_Y_FULLSTEP:
                promotionMethod = new XoffYFullStepMethod();
                break;
            case X_OFF_Y_PAY:
                promotionMethod = new XoffYPayMethod();
                break;
            case DISCOUNT:
                promotionMethod = new DiscountMethod();
                break;
            case REDUCE_PRICE:
                promotionMethod = new ReducePriceMethod();
                break;
            case APRICE:
                promotionMethod = new ApriceMethod();
                break;
            default:
                promotionMethod = null;
        }
        return promotionMethod;
    }

    /**
     * 组合商品的处理，满减组合商品除外
     *
     * @param promotionConditions
     */
    @Override
    public PromotionCalculateResult getProductCombPromotion(List<IPromotionInterface> promotionConditions) {
        PromotionCalculateResult result = new PromotionCalculateResult();
        // 合计金额
        BigDecimal priceAmount = new BigDecimal(0);
        Long productCombId = null;
        int num = 1;
        for (IPromotionInterface pcItem : promotionConditions) {
            PromotionCalculateItem calItem = new PromotionCalculateItem(pcItem);
            num = pcItem.getQuantity();
            priceAmount = priceAmount.add(pcItem.getProductPrice()).multiply(new BigDecimal(num));
            productCombId = pcItem.getProductCombId();
            result.addItem(calItem);
        }
        ProductComb pcomb = productCombService.findById(productCombId);
        BigDecimal currenPromotion = result.getProductTotalAmount()
                .subtract(pcomb.getPrice().multiply(new BigDecimal(num)));
        // 金额分摊
        List<PromotionCalculateItem> list = result.getItems();
        int i = 1;
        BigDecimal rateResult = new BigDecimal(0);
        for (PromotionCalculateItem item : list) {
            if (list.size() == i && list.size() > 1) {
                item.setOrderPromotionAmount(currenPromotion.subtract(rateResult));
                item.setOrderPromotionName(pcomb.getName());
                break;
            }
            BigDecimal itemAmount = item.getTotalProductAmount().subtract(item.getPromotionAmount());
            BigDecimal ratePromotion = itemAmount.divide(
                    result.getProductTotalAmount().subtract(result.getProductPromotionAmount()), 4,
                    BigDecimal.ROUND_HALF_UP);
            BigDecimal promotionAmount = currenPromotion.multiply(ratePromotion).setScale(2, BigDecimal.ROUND_HALF_UP);
            item.setOrderPromotionAmount(promotionAmount);
            item.setOrderPromotionName("组合商品优惠");
            rateResult = rateResult.add(promotionAmount);
            i++;
        }
        result.setOrderPromotionAmount(currenPromotion);
        result.setOrderPromotionNames(pcomb.getName());
        result.setTotalAmount(result.getTotalAmount().subtract(currenPromotion));
        return result;
    }

}
