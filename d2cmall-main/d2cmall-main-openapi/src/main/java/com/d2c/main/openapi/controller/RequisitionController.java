package com.d2c.main.openapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.convert.ConvertHelper;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.ResultHandler;
import com.d2c.main.openapi.constant.OpenConst;
import com.d2c.main.openapi.controller.base.BaseController;
import com.d2c.main.openapi.security.OpenUserHolder;
import com.d2c.main.openapi.view.RequisitionItemVO;
import com.d2c.openapi.api.entity.OpenUserDO;
import com.d2c.order.model.RequisitionItem;
import com.d2c.order.model.RequisitionItem.ItemType;
import com.d2c.order.query.RequisitionItemSearcher;
import com.d2c.order.service.RequisitionItemService;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.ProductSkuService;
import com.d2c.product.support.UploadProductBean;
import com.d2c.util.string.StringUtil;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 采购单开放接口
 *
 * @author wull
 */
@RestController
@RequestMapping(value = OpenConst.OPEN_PREF + "/requisition")
public class RequisitionController extends BaseController {

    @Autowired
    private RequisitionItemService requisitionItemService;
    @Autowired
    private ProductSkuService productSkuService;

    @ResponseBody
    @RequestMapping(value = "/purchase/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Response purchase() {
        OpenUserDO openUserDO = OpenUserHolder.getOpenUser();
        RequisitionItemSearcher searcher = new RequisitionItemSearcher();
        searcher.setBrandIds(Arrays.asList(new Long[]{openUserDO.getBrandId()}));
        searcher.setTypes(new Integer[]{1, 2, 4});
        PageResult<RequisitionItem> pager = null;
        try {
            pager = requisitionItemService.findBySearcher(searcher, new PageModel(1, PageModel.MAX_PAGE_SIZE));
        } catch (Exception e) {
            return ResultHandler.error("-200", e.getMessage());
        }
        List<RequisitionItemVO> viewList = ConvertHelper.convertList(pager.getList(), RequisitionItemVO.class);
        return ResultHandler.success(viewList);
    }

    @ResponseBody
    @RequestMapping(value = "/delivery", method = RequestMethod.POST)
    public Response delivery(Long requisitionId, String deliveryCorp, String deliverySn, Integer deliveryQuantity,
                             String designerMemo) {
        if (StringUtil.hasBlack(new Object[]{requisitionId, deliveryCorp, deliverySn, deliveryQuantity})) {
            return ResultHandler.error("-100", "必须参数不能为空");
        }
        OpenUserDO openUserDO = OpenUserHolder.getOpenUser();
        RequisitionItem requisitionItem = requisitionItemService.findById(requisitionId);
        if (requisitionItem == null) {
            return ResultHandler.error("-101", "调拨单不存在，请核实调拨单ID是否正确");
        }
        if (requisitionItem.getType() != ItemType.ORDERPCH.getCode()
                && requisitionItem.getType() != ItemType.STOREPCH.getCode()
                && requisitionItem.getType() != ItemType.GOODSPCH.getCode()) {
            return ResultHandler.error("-103", "该调拨单不是采购单");
        }
        if (requisitionItem.getStatus() != 1 && requisitionItem.getStatus() != 2) {
            return ResultHandler.error("-101", "调拨单状态不符");
        }
        if (!requisitionItem.getDesignerId().equals(openUserDO.getBrandId())) {
            return ResultHandler.error("-103", "该调拨单不属于你的品牌");
        }
        if (requisitionItem.getType() == ItemType.ORDERPCH.getCode()
                && requisitionItem.getQuantity() != deliveryQuantity) {
            return ResultHandler.error("-102", "订单采购，发货数量需要要与采购数量一致");
        }
        if ((requisitionItem.getType() != ItemType.STOREPCH.getCode()
                || requisitionItem.getType() != ItemType.GOODSPCH.getCode())
                && (deliveryQuantity > requisitionItem.getQuantity() || deliveryQuantity < 1)) {
            return ResultHandler.error("-102", "发货数量不可大于调拨单或小于1");
        }
        try {
            if (requisitionItem.getStatus() == 1) {
                requisitionItemService.doSign(requisitionId, "品牌方调用接口");
            }
            requisitionItemService.doDelivery(requisitionId, deliveryQuantity, deliverySn, deliveryCorp, "品牌方调用接口");
            if (StringUtils.isNotBlank(designerMemo)) {
                requisitionItemService.updateDesignerMemo(requisitionId, designerMemo, "品牌方调用接口");
            }
        } catch (Exception e) {
            return ResultHandler.error("-200", e.getMessage());
        }
        return ResultHandler.success();
    }

    @ResponseBody
    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public Response updateStore(String externalSn, Integer popStore) {
        if (StringUtil.hasBlack(new Object[]{externalSn, popStore})) {
            return ResultHandler.error("-100", "必须参数不能为空");
        }
        OpenUserDO openUserDO = OpenUserHolder.getOpenUser();
        List<ProductSku> list = productSkuService.findByBrandIdAndExternalSn(openUserDO.getBrandId(), externalSn);
        if (list == null || list.size() == 0) {
            return ResultHandler.error("-101", "外部编码商品不存在");
        }
        if (list.size() > 1) {
            return ResultHandler.error("-200", "外部编码商品存在多个，请联系客服处理");
        }
        ProductSku oldSku = list.get(0);
        if (oldSku.getFreezeStore() > (oldSku.getStore() + popStore)) {
            return ResultHandler.error("-102",
                    "商品库存修改应大于锁定库存，修改不成功，当前锁定库存：" + oldSku.getFreezeStore() + "，自营库存：" + oldSku.getStore());
        }
        UploadProductBean bean = new UploadProductBean();
        bean.setBarCode(oldSku.getBarCode());
        bean.setPopStore(popStore);
        try {
            productSkuService.updatePopStore(bean, "品牌方调用接口");
        } catch (Exception e) {
            return ResultHandler.error("-200", e.getMessage());
        }
        JSONObject obj = new JSONObject();
        obj.put("externalSn", oldSku.getExternalSn());
        obj.put("store", oldSku.getStore());
        obj.put("popstore", popStore);
        obj.put("freezeStore", oldSku.getFreezeStore());
        return ResultHandler.successData(obj);
    }

}
