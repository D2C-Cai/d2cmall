package com.d2c.flame.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.flame.enums.CollageErrorCodeEnum;
import com.d2c.member.enums.DeviceTypeEnum;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.search.model.SearcherMemberCollection;
import com.d2c.member.search.service.MemberCollectionSearcherService;
import com.d2c.order.dto.AddressDto;
import com.d2c.order.dto.CollageGroupDto;
import com.d2c.order.dto.CollageOrderDto;
import com.d2c.order.model.CollageGroup;
import com.d2c.order.model.CollageGroup.GroupStatus;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.model.CollageOrder.CollageOrderStatus;
import com.d2c.order.model.Setting;
import com.d2c.order.query.CollageOrderSearcher;
import com.d2c.order.service.AddressService;
import com.d2c.order.service.CollageGroupService;
import com.d2c.order.service.CollageOrderService;
import com.d2c.order.service.SettingService;
import com.d2c.order.service.tx.CollageTxService;
import com.d2c.product.dto.ProductDto;
import com.d2c.product.model.CollagePromotion;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.product.search.model.SearcherCollagePromotion;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.query.CollagePromotionSearcherBean;
import com.d2c.product.search.service.CollagePromotionSearcherService;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.service.CollagePromotionService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 拼团
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api/collage")
public class CollageController extends BaseController {

    @Autowired
    private CollagePromotionService collagePromotionService;
    @Reference
    private CollagePromotionSearcherService collagePromotionSearcherService;
    @Autowired
    private CollageGroupService collageGroupService;
    @Autowired
    private CollageOrderService collageOrderService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private SettingService settingService;
    @Reference
    private CollageTxService collageTxService;
    @Reference
    private MemberCollectionSearcherService memberCollectionSearcherService;

    /**
     * 拼团活动列表
     *
     * @param page
     * @param searcher
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseResult list(PageModel page, CollagePromotionSearcherBean searcher) {
        ResponseResult result = new ResponseResult();
        PageResult<SearcherCollagePromotion> pager = collagePromotionSearcherService.search(searcher, page);
        List<String> productIds = new ArrayList<>();
        pager.getList().forEach(item -> productIds.add(item.getProductId().toString()));
        Map<Long, SearcherProduct> map = productSearcherQueryService
                .findMapByIds(productIds.toArray(new String[productIds.size()]));
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> {
            JSONObject obj = item.toJson();
            SearcherProduct product = map.get(item.getProductId());
            obj.put("product", product.toJson());
            array.add(obj);
        });
        result.putPage("collageList", pager, array);
        return result;
    }

    /**
     * 拼团详情页
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseResult detail(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        CollagePromotion promotion = collagePromotionService.findById(id);
        result.put("collagePromotion", promotion.toJson());
        // 商品信息
        ProductDto dto = new ProductDto();
        Product product = productService.findById(promotion.getProductId());
        if (product == null || product.getMark() < 0) {
            throw new BusinessException("商品已下架！");
        }
        BeanUtils.copyProperties(product, dto);
        SearcherProduct searcherProduct = productSearcherQueryService.findById(promotion.getProductId().toString());
        // 是否收藏
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            SearcherMemberCollection smc = memberCollectionSearcherService.findByMemberAndProductId(memberInfo.getId(),
                    product.getId());
            if (smc != null && smc.getProductId() != null) {
                dto.setCollectioned(1);
            } else {
                dto.setCollectioned(0);
            }
        } catch (NotLoginException e) {
            dto.setCollectioned(0);
        }
        result.put("product", dto.toJson(searcherProduct));
        // 参团信息
        JSONArray groups = new JSONArray();
        PageResult<CollageGroup> pager = collageGroupService.findVaildByPromotionId(id, new PageModel(1, 2));
        pager.getList().forEach(item -> groups.add(item.toJson()));
        result.put("groupList", groups);
        // 库存信息
        List<ProductSku> productSKUSet = productSkuService.findByProductId(promotion.getProductId());
        JSONArray array = new JSONArray();
        for (ProductSku sku : productSKUSet) {
            array.add(sku.toJson());
        }
        result.put("skuList", array);
        // 全局返利系数
        Setting ratio = settingService.findByCode(Setting.REBATERATIO);
        result.put("ratio", Setting.defaultValue(ratio, new Integer(1)));
        return result;
    }

    /**
     * 拼团详情页显示拼团团队
     *
     * @param id
     * @param page
     * @return
     */
    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
    public ResponseResult groups(@PathVariable("id") Long id, PageModel page) {
        ResponseResult result = new ResponseResult();
        JSONArray groups = new JSONArray();
        PageResult<CollageGroup> pager = collageGroupService.findVaildByPromotionId(id, page);
        pager.getList().forEach(item -> groups.add(item.toJson()));
        result.putPage("groupList", pager, groups);
        return result;
    }

    /**
     * 活动开始提醒
     *
     * @param promotionId
     * @return
     */
    @RequestMapping(value = "/remind/{id}", method = RequestMethod.POST)
    public ResponseResult remind(@PathVariable("id") Long id) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        ResponseResult result = new ResponseResult();
        collagePromotionService.doRemind(memberInfo.getId(), id, memberInfo.getLoginCode());
        return result;
    }

    /**
     * 参团/创团
     *
     * @param promotionId
     * @param skuId
     * @param groupId
     * @param addressId
     * @return
     */
    @RequestMapping(value = "/create/collage", method = RequestMethod.POST)
    public ResponseResult createCollage(@RequestParam(required = true) Long collageId,
                                        @RequestParam(required = true) Long skuId, Long groupId, @RequestParam(required = true) Long addressId,
                                        Long parent_id, String appTerminal, String appVersion, String drawee) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        CollagePromotion promotion = collagePromotionService.findById(collageId);
        Product product = productService.findById(promotion.getProductId());
        ProductSku productSku = productSkuService.findById(skuId);
        checkQuantityAndStatus(productSku, product, 1);
        // 查找未支付的
        CollageOrderDto dto = null;
        CollageGroup group = null;
        if (groupId != null) {
            group = collageGroupService.findById(groupId);
        }
        // 开团参团限制
        checkCollage(memberInfo.getId(), group, promotion);
        AddressDto addressDto = addressService.findById(addressId);
        if (addressDto == null) {
            throw new BusinessException("该地址不存在，请重新选择！");
        }
        if (groupId == null) {
            // 团长开团
            CollageGroup collageGroup = new CollageGroup(memberInfo, promotion);
            CollageOrder collageOrder = new CollageOrder(addressDto, memberInfo, collageGroup, product, productSku,
                    parent_id, DeviceTypeEnum.divisionDevice(appTerminal), appVersion, drawee);
            dto = collageTxService.createCollageGroup(collageGroup, collageOrder);
        } else {
            CollageOrder collageOrder = new CollageOrder(addressDto, memberInfo, group, product, productSku, parent_id,
                    DeviceTypeEnum.divisionDevice(appTerminal), appVersion, drawee);
            dto = collageTxService.createCollageOrder(collageOrder);
        }
        if (dto == null) {
            throw new BusinessException("操作失败！");
        }
        result.put("collageOrder", dto.toDtoJson());
        return result;
    }

    /**
     * 商品信息判断
     *
     * @param productSku
     * @param product
     * @param quantity
     */
    private void checkQuantityAndStatus(ProductSku productSku, Product product, int quantity) {
        // 当前商品下架或者删除, 不能下订单
        if (product == null || product.getMark() <= 0) {
            throw new BusinessException("商品已下架，购买不成功！");
        }
        // 当前商品下架或者删除, 不能下订单
        if (productSku == null || productSku.getStatus() <= 0) {
            throw new BusinessException("商品已下架，购买不成功！");
        }
        // 当前没有销售的库存了, 不能下订单
        if (productSku.getAvailableStore() < quantity) {
            throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue() + "颜色："
                    + productSku.getColorValue() + "，商品库存不足" + quantity + "件，下次早点来哦！");
        }
        if (productSku.getAvailableFlashStore() < quantity) {
            throw new BusinessException("款号：" + productSku.getInernalSn() + "，尺码：" + productSku.getSizeValue() + "颜色："
                    + productSku.getColorValue() + "，商品库存不足" + quantity + "件，下次早点来哦！");
        }
    }

    /**
     * 判断是否能开团参团<br>
     * 限定条件:
     * <li>同一商品同一人只能在进行中只有一个
     * <li>同商品同人只能买N件
     * <li>同账号一天开N次团
     * <li>同账号一天只能参N次团
     *
     * @param memberId
     * @param group
     * @param promotion
     */
    private void checkCollage(Long memberId, CollageGroup group, CollagePromotion promotion) {
        // 活动相关
        if (promotion == null || promotion.isOver() < 1) {
            throw new BusinessException("活动已结束，看看其他的拼团活动吧！");
        }
        if (group != null) {
            // 满人或者关闭或者由于未支付而没关闭实际已超时的团队不能参与
            if (group == null || group.getStatus() != GroupStatus.PROCESS.getCode()
                    || group.getEndDate().before(new Date())) {
                throw new BusinessException("该团队不在开团中，请重新选择团队！");
            }
        } else {
            int count = collageGroupService.countGroupByPromotionId(promotion.getId());
            if (count >= promotion.getProductCreatedLimit()) {
                throw new BusinessException(CollageErrorCodeEnum.COLLAGE_1002.getMessage(),
                        CollageErrorCodeEnum.COLLAGE_1002.getCode());
            }
        }
        // 判断对于该用户是否存在正在进行中的，存在则不能参团开团
        CollageOrder order = collageOrderService.findExistProcess(promotion.getId(), memberId);
        if (order != null) {
            throw new BusinessException(CollageErrorCodeEnum.COLLAGE_1001.getMessage(),
                    CollageErrorCodeEnum.COLLAGE_1001.getCode());
        }
        // 判断是否该活动该用户参团成功的次数超过限制
        CollageOrderSearcher searcher = new CollageOrderSearcher();
        searcher.setMemberId(memberId);
        searcher.setPromotionId(promotion.getId());
        searcher.setStatus(CollageOrderStatus.SUCESS.getCode());
        int successCount = collageOrderService.countBySearch(searcher);
        if (successCount >= promotion.getMemberBuyLimit()) {
            throw new BusinessException("您已经成功拼团该商品" + successCount + "次了，超过上限了，看看其他拼团活动吧！");
        }
    }

    /**
     * 团信息检查
     *
     * @param groupId
     * @param collageId
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public ResponseResult check(Long groupId, Long collageId) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        CollagePromotion promotion = collagePromotionService.findById(collageId);
        CollageGroup group = null;
        if (groupId != null) {
            group = collageGroupService.findById(groupId);
        }
        try {
            checkCollage(memberInfo.getId(), group, promotion);
        } catch (BusinessException e) {
            if (e.getErrCode().equals(CollageErrorCodeEnum.COLLAGE_1001.getCode())) {
                CollageOrder order = collageOrderService.findExistProcess(collageId, memberInfo.getId());
                result.put("collageOrder", order);
            }
            result.setStatus(-1);
            result.put("code", e.getErrCode());
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 我的拼团列表
     *
     * @return
     */
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public ResponseResult myCollageList(PageModel page, CollageOrderSearcher searcher, Integer index) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        searcher.setMemberId(memberInfo.getId());
        searcher.handleIndex(index);
        PageResult<CollageOrderDto> pager = collageOrderService.findMyCollage(page, searcher);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toDtoJson()));
        result.putPage("collageOrders", pager, array);
        return result;
    }

    /**
     * 拼团信息明细
     *
     * @return
     */
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseResult collageOrderDetail(@PathVariable("id") Long id) {
        ResponseResult result = new ResponseResult();
        this.getLoginMemberInfo();
        CollageOrderDto orderDto = collageOrderService.findDtoById(id);
        if (orderDto == null) {
            throw new BusinessException("该拼团信息不存在！");
        }
        result.put("collageOrder", orderDto.toDtoJson());
        CollageGroupDto groupDto = collageGroupService.findDtoById(orderDto.getGroupId());
        result.put("collageGroup", groupDto.toDtoJson());
        CollagePromotion promotion = collagePromotionService.findById(groupDto.getPromotionId());
        result.put("collagePromotion", promotion.toJson());
        return result;
    }

    /**
     * 分享拼团详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/group/{groupId}", method = RequestMethod.GET)
    public ResponseResult groupDetail(@PathVariable("groupId") Long groupId) {
        ResponseResult result = new ResponseResult();
        CollageGroupDto groupDto = collageGroupService.findDtoById(groupId);
        result.put("collageGroup", groupDto.toDtoJson());
        CollagePromotion promotion = collagePromotionService.findById(groupDto.getPromotionId());
        result.put("collagePromotion", promotion.toJson());
        // 商品信息
        ProductDto dto = new ProductDto();
        Product product = productService.findById(promotion.getProductId());
        if (product == null || product.getMark() < 0) {
            throw new BusinessException("商品已下架！");
        }
        BeanUtils.copyProperties(product, dto);
        SearcherProduct searcherProduct = productSearcherQueryService.findById(promotion.getProductId().toString());
        result.put("product", dto.toJson(searcherProduct));
        // 库存信息
        List<ProductSku> productSKUSet = productSkuService.findByProductId(promotion.getProductId());
        JSONArray array = new JSONArray();
        for (ProductSku sku : productSKUSet) {
            array.add(sku.toJson());
        }
        result.put("skuList", array);
        return result;
    }

}
