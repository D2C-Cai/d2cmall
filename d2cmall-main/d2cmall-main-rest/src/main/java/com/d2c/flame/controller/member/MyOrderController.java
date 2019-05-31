package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.member.model.IntegrationRule;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.service.MemberAttentionSearcherService;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.member.service.IntegrationRuleService;
import com.d2c.order.dto.OrderDto;
import com.d2c.order.dto.OrderItemDto;
import com.d2c.order.model.*;
import com.d2c.order.model.Logistics.BusinessType;
import com.d2c.order.model.Order.OrderStatus;
import com.d2c.order.model.OrderItem.ItemStatus;
import com.d2c.order.query.OrderSearcher;
import com.d2c.order.service.*;
import com.d2c.order.service.tx.OrderTxService;
import com.d2c.order.third.kaola.KaolaClient;
import com.d2c.order.third.kaola.reponse.KaolaOrderStatus;
import com.d2c.order.third.kaola.reponse.Track;
import com.d2c.order.third.kaola.reponse.TrackLogistics;
import com.d2c.product.model.FlashPromotion;
import com.d2c.product.model.Product.ProductSource;
import com.d2c.product.model.ProductSku;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.FlashPromotionService;
import com.d2c.product.service.ProductSkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 我的订单
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/myorder")
public class MyOrderController extends BaseController {

    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private LogisticsService logisticsService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;
    @Reference
    private MemberAttentionSearcherService memberAttentionSearcherService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private ProductSkuService productSkuService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private FlashPromotionService flashPromotionService;
    @Reference
    private OrderTxService orderTxService;
    @Autowired
    private IntegrationRuleService integrationRuleService;

    /**
     * 订单列表
     *
     * @param page
     * @param searcher
     * @param index
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(PageModel page, OrderSearcher searcher, Integer index) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.handleIndex(index);
        searcher.setMemberId(memberInfo.getId());
        PageResult<OrderDto> pager = orderQueryService.findMyOrder(memberInfo.getId(), searcher.getOrderSn(),
                searcher.getProductName(), searcher.getStartDate(), searcher.getEndDate(), searcher.getOrderStatus(),
                searcher.getItemStatus(), searcher.getDesignerName(), searcher.getCommented(), page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toDtoJson()));
        result.putPage("orders", pager, array);
        getCommentReward(result);
        return result;
    }

    /**
     * 订单明细列表
     *
     * @param page
     * @param searcher
     * @param itemIndex
     * @return
     */
    @RequestMapping(value = "/item/list", method = RequestMethod.GET)
    public ResponseResult itemList(PageModel page, OrderSearcher searcher, Integer itemIndex) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.handleIndexForItem(itemIndex);
        searcher.setMemberId(memberInfo.getId());
        PageResult<OrderItemDto> pager = orderItemService.findSimpleBySearcher(searcher, page);
        JSONArray items = new JSONArray();
        pager.getList().forEach(item -> items.add(item.toJson()));
        result.putPage("items", pager, items);
        getCommentReward(result);
        return result;
    }

    /**
     * 订单详情
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/{orderSn}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult orderDetail(@PathVariable("orderSn") String orderSn) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderDto order = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
        if (order == null) {
            throw new BusinessException("订单不存在！");
        }
        if (!order.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("该订单非本账号所有！");
        }
        result.put("order", order.toDtoJson());
        getCommentReward(result);
        return result;
    }

    /**
     * 订单明细详情
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/item/{itemId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseResult itemDetail(@PathVariable Long itemId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderItemDto dto = orderItemService.findOrderItemDtoById(itemId);
        if (dto == null) {
            throw new BusinessException("订单不存在！");
        }
        if (!dto.getBuyerMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("该订单非本账号所有！");
        }
        JSONObject obj = dto.toJson();
        Order order = orderService.findById(dto.getOrderId());
        if (order != null) {
            obj.put("order", order.toJson());
        }
        result.put("item", obj);
        getCommentReward(result);
        return result;
    }

    private void getCommentReward(ResponseResult result) {
        IntegrationRule rule = integrationRuleService.findVaildByType(PointRuleTypeEnum.COMMENT.name());
        if (rule != null && rule.getStatus() == 1) {
            result.put("point", PointRuleTypeEnum.COMMENT.calculatePoint(rule.getRatio(), null));
        }
    }

    /**
     * 物流查询
     *
     * @param sn
     * @param com
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logistics/info", method = RequestMethod.GET)
    public ResponseResult logisticsInfo(String sn, String com, Long orderItemId) throws Exception {
        ResponseResult result = new ResponseResult();
        String deliveryCorpName = com;
        LogisticsCompany logisticsCompany = logisticsService.findCompanyByName(com);
        com = (logisticsCompany != null ? logisticsCompany.getCode() : "other");
        Logistics logistics = new Logistics();
        if (orderItemId != null) {
            // 传了orderItemId情形
            OrderItem item = orderItemService.findById(orderItemId);
            if (item == null) {
                throw new BusinessException("该笔订单明细不存在！");
            }
            if (item.getProductSource().equals(ProductSource.KAOLA.name())) {
                // 考拉订单的物流
                KaolaOrderStatus kaolaOrderStatus = KaolaClient.getInstance()
                        .queryOrderStatus(item.getOrderSn() + "-" + item.getWarehouseId());
                for (List<TrackLogistics> list : kaolaOrderStatus.getTrackLogisticss().values()) {
                    if (list.get(0) != null) {
                        TrackLogistics trackLogistics = list.get(0);
                        logistics.setDeliverySn(trackLogistics.getBillno());
                        logistics.setDeliveryCorpName(item.getDeliveryCorpName());
                        logistics.setStatus(trackLogistics.getState());
                        logistics.setType(BusinessType.ORDER.name());
                        JSONArray array = new JSONArray();
                        for (Track track : trackLogistics.getTracks()) {
                            JSONObject obj = new JSONObject();
                            obj.put("context", track.getContext());
                            obj.put("ftime", track.getTimeDetail());
                            obj.put("time", track.getTime());
                            array.add(obj);
                        }
                        logistics.setDeliveryInfo(array.toJSONString());
                        break;
                    }
                }
            } else {
                // 一般订单的物流
                logistics = logisticsService.findBySnAndCom(sn, com, null);
                if (logistics != null) {
                    logistics.setDeliveryCorpName(deliveryCorpName);
                }
            }
        } else {
            // 没传orderItemId情形
            logistics = logisticsService.findBySnAndCom(sn, com, null);
            if (logistics != null) {
                logistics.setDeliveryCorpName(deliveryCorpName);
            }
        }
        JSONObject json = new JSONObject();
        if (logistics != null) {
            json = (JSONObject) JSON.toJSON(logistics);
            if (!StringUtils.isEmpty(logistics.getDeliveryInfo())) {
                json.put("deliveryInfo", JSON.parse(logistics.getDeliveryInfo(), Feature.OrderedField).toString());
            }
        } else {
            logistics = new Logistics();
            logistics.setStatus(-1);
            logistics.setDeliverySn(sn);
            logistics.setDeliveryCode(com);
            json = (JSONObject) JSON.toJSON(logistics);
        }
        result.put("logistics", json);
        return result;
    }

    /**
     * 确认收货
     *
     * @param orderItemId
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ResponseResult confirmOrderItem(Long orderItemId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderItem orderItem = orderItemService.findByIdAndMemberInfoId(orderItemId, memberInfo.getId());
        if (orderItem == null) {
            throw new BusinessException("订单不存在！");
        }
        if (!orderItem.getBuyerMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("该订单非本账号所有！");
        }
        if (!ItemStatus.DELIVERED.name().equals(orderItem.getStatus())) {
            throw new BusinessException("订单状态不匹配！");
        }
        orderItem.setStatus(ItemStatus.SIGNED.name());
        Setting setting = settingService.findByCode(Setting.ORDERAFTERCLOSE);
        orderItemService.doSign(orderItem.getId(), memberInfo.getLoginCode(), "确认收货",
                Integer.parseInt(Setting.defaultValue(setting, new Integer(7)).toString()));
        OrderDto newOrder = orderService.doFindByOrderSnAndMemberInfoId(orderItem.getOrderSn(), memberInfo.getId());
        result.put("order", newOrder.toDtoJson());
        return result;
    }

    /**
     * 取消订单
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseResult cancelOrder(String orderSn) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderDto order = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
        if (order == null) {
            throw new BusinessException("订单不存在！");
        }
        if (!order.getMemberId().equals(memberInfo.getId())) {
            throw new BusinessException("该订单非本账号所有！");
        }
        OrderStatus status = OrderStatus.getStatus(order.getOrderStatus());
        order.setLastModifyMan(memberInfo.getId() + ":" + memberInfo.getLoginCode());
        int success = 0;
        if (status.equals(OrderStatus.Initial) || status.equals(OrderStatus.WaitingForPay)) {
            success = orderTxService.doCloseOrder(order.getId(), memberInfo.getLoginCode(), "用户取消",
                    OrderStatus.UserClose);
        } else {
            throw new BusinessException("订单取消不成功，订单状态为" + status.getName());
        }
        if (success < 1) {
            throw new BusinessException("订单取消不成功！");
        } else {
            OrderDto newOrder = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
            result.put("order", newOrder.toDtoJson());
        }
        return result;
    }

    /**
     * 删除订单
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult deleteOrder(Long orderId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        Order order = orderService.findById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在！");
        }
        int success = orderService.delete(orderId, memberInfo.getLoginCode());
        if (success < 1) {
            throw new BusinessException("订单删除不成功！");
        }
        return result;
    }

    /**
     * 订单和收藏，关注数量统计
     *
     * @return
     */
    @RequestMapping(value = "/counts", method = RequestMethod.GET)
    public ResponseResult counts() {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        // 订单
        Map<String, Object> map = orderItemService.countGroupByStatus(memberInfo.getId());
        result.put("waitForPay", map.get("INIT") != null ? map.get("INIT") : 0);
        result.put("waitForDelivered", map.get("NORMAL") != null ? map.get("NORMAL") : 0);
        result.put("delivered", map.get("DELIVERED") != null ? map.get("DELIVERED") : 0);
        result.put("waitForComment", map.get("WAITCOMMENT") != null ? map.get("WAITCOMMENT") : 0);
        // 收藏和关注
        int collectionCount = memberCollectionSearcherService.countByMemberId(memberInfo.getId());
        int attentionCount = memberAttentionSearcherService.countByMemberId(memberInfo.getId());
        result.put("collectionCount", collectionCount);
        result.put("attentionCount", attentionCount);
        return result;
    }

    /**
     * 再次购买页面
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/buylist/{orderSn}", method = RequestMethod.GET)
    public ResponseResult buyItemList(@PathVariable String orderSn) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        OrderDto orderDto = orderService.doFindByOrderSnAndMemberInfoId(orderSn, memberInfo.getId());
        if (orderDto == null || orderDto.getOrderItems() == null) {
            throw new BusinessException("该订单不存在！");
        }
        JSONArray available = new JSONArray();
        JSONArray disable = new JSONArray();
        for (OrderItem item : orderDto.getOrderItems()) {
            ProductSku productSku = productSkuService.findById(item.getProductSkuId());
            if (productSku != null) {
                SearcherProduct product = productSearcherQueryService.findById(productSku.getProductId().toString());
                JSONObject obj = product.toJson();
                int stock = productSku.getAvailableStore();
                // 限时购商品取限时购库存
                if (product.getStatus() == 6) {
                    FlashPromotion flashPromotion = flashPromotionService.findById(product.getFlashPromotionId());
                    if (flashPromotion != null && !flashPromotion.isOver()) {
                        stock = productSku.getAvailableFlashStore();
                    }
                }
                obj.put("skuId", productSku.getId());
                obj.put("productSkuStore", stock);
                if (stock > 0) {
                    available.add(obj);
                } else {
                    disable.add(obj);
                }
            }
        }
        result.put("available", available);
        result.put("disable", disable);
        return result;
    }

}
