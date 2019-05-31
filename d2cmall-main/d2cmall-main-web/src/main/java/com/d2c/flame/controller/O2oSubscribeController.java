package com.d2c.flame.controller;

import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.MemberInfo;
import com.d2c.order.dto.O2oSubscribeDto;
import com.d2c.order.model.O2oSubscribe;
import com.d2c.order.model.O2oSubscribeItem;
import com.d2c.order.model.Store;
import com.d2c.order.query.O2oSubscribeSearcher;
import com.d2c.order.query.StoreSearcher;
import com.d2c.order.service.O2oSubscribeItemService;
import com.d2c.order.service.O2oSubscribeService;
import com.d2c.order.service.StoreService;
import com.d2c.product.model.Brand;
import com.d2c.product.model.Product;
import com.d2c.product.model.ProductSku;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import com.d2c.product.service.ProductSkuService;
import com.d2c.util.serial.SerialNumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/o2oSubscribe")
public class O2oSubscribeController extends BaseController {

    @Autowired
    private O2oSubscribeService o2oSubscribeService;
    @Autowired
    private O2oSubscribeItemService o2oSubscribeItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuService productSkuService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private BrandService brandService;

    /**
     * 我的预约单列表
     *
     * @param model
     * @param type
     * @return
     */
    @RequestMapping(value = "/my/list", method = RequestMethod.GET)
    public String myList(ModelMap model, Integer type) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        O2oSubscribeSearcher searcher = new O2oSubscribeSearcher();
        searcher.setMemberId(memberInfo.getId());
        if (type != null) {
            if (type == 0) {
                searcher.setStatus(new Integer[]{0});
            } else if (type == 1) {
                searcher.setStatus(new Integer[]{1, 2, 3});
            } else if (type == 2) {
                searcher.setStatus(new Integer[]{4});
            } else if (type == 3) {
                searcher.setStatus(new Integer[]{5});
            }
        }
        PageModel page = new PageModel();
        PageResult<O2oSubscribeDto> pager = o2oSubscribeService.findBySearch(searcher, page);
        model.put("pager", pager);
        return "/member/my_subscribe";
    }

    /**
     * 点击门店预约
     *
     * @param model
     * @param skuId
     * @param quantity
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(ModelMap model, Long skuId, Integer quantity) {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        O2oSubscribeDto noSubmit = o2oSubscribeService.findUnSubmit(memberInfo.getId());
        // 初次点击
        if (noSubmit == null) {
            O2oSubscribe old = o2oSubscribeService.findLastO2oSubscribe(memberInfo.getId());
            O2oSubscribe newSubmit = new O2oSubscribe();
            // 根据最近记录预设
            if (old != null) {
                newSubmit.setStoreId(old.getStoreId());
                newSubmit.setName(old.getName());
                newSubmit.setTel(old.getTel());
                newSubmit.setEmail(old.getEmail());
            } else {
                newSubmit.setName(memberInfo.getLoginCode());
                newSubmit.setTel(memberInfo.getMobile());
                newSubmit.setEmail(memberInfo.getEmail());
            }
            newSubmit.setSubmitDate(new Date());
            newSubmit.setMemberId(memberInfo.getId());
            newSubmit.setSn(SerialNumUtil.buildO2oSubscribeSn());
            newSubmit.setStatus(0);
            // 预约单明细
            O2oSubscribeItem item = this.createO2oSubscribeItem(memberInfo, skuId);
            newSubmit = o2oSubscribeService.insert(newSubmit, item);
            noSubmit = o2oSubscribeService.findById(newSubmit.getId());
        } else {
            // 多次点击
            boolean repeated = false;
            List<O2oSubscribeItem> list = noSubmit.getItems();
            for (O2oSubscribeItem item : list) {
                if (item.getProductSkuId().equals(skuId)) {
                    repeated = true;
                    break;
                }
            }
            // 记录不重复的商品
            if (!repeated) {
                O2oSubscribeItem item = this.createO2oSubscribeItem(memberInfo, skuId);
                item.setSubscribeId(noSubmit.getId());
                item = o2oSubscribeItemService.insert(item);
                list.add(item);
                noSubmit.setItems(list);
            }
        }
        // 预计到店时间
        if (noSubmit.getEstimateDate() == null) {
            noSubmit.setEstimateDate(new Date(new Date().getTime() + 1000L * 60 * 60 * 24));
        }
        model.put("flag", false);
        model.put("o2oSubscribe", noSubmit);
        if (isMobileDevice()) {
            StoreSearcher storeSearcher = new StoreSearcher();
            storeSearcher.setStatus(1);
            PageResult<Store> pager = storeService.findBySearch(storeSearcher, new PageModel());
            model.put("stores", pager.getList());
        }
        return "/o2o/subscribe_fitting";
    }

    private O2oSubscribeItem createO2oSubscribeItem(MemberInfo member, Long skuId) {
        ProductSku sku = productSkuService.findById(skuId);
        Product product = productService.findById(sku.getProductId());
        Brand brand = this.brandService.findById(product.getDesignerId());
        if (brand.getSubscribe() == 0 || product.getSubscribe() == 0) {
            throw new BusinessException("此商品不支持门店试衣");
        }
        O2oSubscribeItem o2oSubscribeItem = new O2oSubscribeItem();
        o2oSubscribeItem.setSp1(sku.getSp1());
        o2oSubscribeItem.setSp2(sku.getSp2());
        o2oSubscribeItem.setSp3(sku.getSp3());
        o2oSubscribeItem.setProductImg(product.getProductImageCover());
        o2oSubscribeItem.setProductId(product.getId());
        o2oSubscribeItem.setProductSn(product.getInernalSn());
        o2oSubscribeItem.setProductName(product.getName());
        o2oSubscribeItem.setQuantity(1);
        o2oSubscribeItem.setPrice(sku.getPrice());
        o2oSubscribeItem.setProductSkuId(sku.getId());
        o2oSubscribeItem.setProductSkuSn(sku.getSn());
        o2oSubscribeItem.setMemberId(member.getId());
        o2oSubscribeItem.setMemberName(member.getLoginCode());
        o2oSubscribeItem.setDesignerId(product.getDesignerId());
        o2oSubscribeItem.setDesignerName(product.getDesignerName());
        o2oSubscribeItem.setOriginalPrice(sku.getOriginalCost());
        return o2oSubscribeItem;
    }

    /**
     * 确认预约单
     *
     * @param model
     * @param skuId
     * @param quantity
     * @param subscribeId
     * @return
     */
    @RequestMapping(value = "/confirm/{subscribeId}", method = RequestMethod.GET)
    public String confirm(ModelMap model, Long skuId, Integer quantity, @PathVariable Long subscribeId) {
        this.getLoginMemberInfo();
        O2oSubscribe noSubmit = o2oSubscribeService.findById(subscribeId);
        if (noSubmit.getEstimateDate() == null) {
            // 预约时间需大于3天
            noSubmit.setEstimateDate(new Date(new Date().getTime() + 1000L * 60 * 60 * 24 * 3));
        }
        model.put("flag", true);
        model.put("o2oSubscribe", noSubmit);
        StoreSearcher storeSearcher = new StoreSearcher();
        storeSearcher.setStatus(1);
        PageResult<Store> pager = storeService.findBySearch(storeSearcher, new PageModel());
        model.put("stores", pager.getList());
        return "/o2o/subscribe_fitting";
    }

    /**
     * 生成预约单，提交预约单
     *
     * @param model
     * @param subscribeId
     * @param cartItemIds
     * @param storeId
     * @param o2oSubscribe
     * @return
     */
    @RequestMapping(value = {"/create", "/update"}, method = RequestMethod.POST)
    public String create(ModelMap model, Long subscribeId, String startTime, String endTime,
                         O2oSubscribe o2oSubscribe) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        if (o2oSubscribe.getEstimateDate() == null) {
            throw new BusinessException("预约时间不完整！");
        }
        if (isMobileDevice()) {
            if ("".equals(startTime) || "".equals(endTime)) {
                throw new BusinessException("预约时间不完整！");
            }
            if (!"".equals(startTime)) {
                String[] startTimeSplit = startTime.split(":");
                o2oSubscribe.setStartHour(Integer.parseInt(startTimeSplit[0]));
                o2oSubscribe.setStartMinute(Integer.parseInt(startTimeSplit[1]));
            }
            if (!"".equals(endTime)) {
                String[] endTimeSplit = endTime.split(":");
                o2oSubscribe.setEndHour(Integer.parseInt(endTimeSplit[0]));
                o2oSubscribe.setEndMinute(Integer.parseInt(endTimeSplit[1]));
            }
        }
        O2oSubscribeDto o2o = o2oSubscribeService.findById(subscribeId);
        if (o2oSubscribe.getStoreId() == null) {
            throw new BusinessException("提交不成功，请选择预约试衣门店");
        }
        if (o2o.getStatus() == 0 || o2o.getStatus() == 1) {
            o2oSubscribe.setStatus(1);
            // 删除已取消预约的商品
            List<O2oSubscribeItem> items = o2oSubscribeItemService.findBySubscribeId(subscribeId);
            List<Long> ids = this.getCancelSubscribeProduct(items);
            if (ids.size() > 0) {
                o2oSubscribeItemService.deleteByProductIds(ids);
                throw new BusinessException("部分商品" + " 已取消预约功能！" + "已为您删除该商品预约，请确认再次提交！");
            } else {
                o2oSubscribe.setId(subscribeId);
                o2oSubscribe.setMemberId(o2o.getMemberId());
                o2oSubscribeService.update(o2oSubscribe);
            }
        } else {
            throw new BusinessException("提交不成功，预约单状态已变更！");
        }
        return "";
    }

    private List<Long> getCancelSubscribeProduct(List<O2oSubscribeItem> items) {
        List<Long> list = new ArrayList<>();
        for (O2oSubscribeItem o2o : items) {
            Product product = productService.findById(o2o.getProductId());
            Brand brand = brandService.findById(product.getDesignerId());
            if (product.getSubscribe() == 0 || brand.getSubscribe() == 0) {
                list.add(o2o.getProductId());
            }
        }
        return list;
    }

    /**
     * 取消预约
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public String cancel(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        int success = o2oSubscribeService.doCancelByMemberId(id, memberInfo.getId());
        if (success == 0) {
            throw new BusinessException("取消不成功！");
        }
        return "";
    }

    /**
     * 删除预约
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        int success = o2oSubscribeService.deleteBy(id, memberInfo.getId());
        if (success == 0) {
            throw new BusinessException("删除不成功！");
        }
        return "";
    }

    /**
     * 删除预约明细
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteItem/{id}", method = RequestMethod.GET)
    public String deleteItem(ModelMap model, @PathVariable Long id) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        int success = o2oSubscribeItemService.deleteByIdAndMemberId(id, memberInfo.getId());
        if (success == 0) {
            throw new BusinessException("删除不成功！");
        }
        return "";
    }

    /**
     * 门店预约单列表（门店使用）
     *
     * @param model
     * @param status
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model, O2oSubscribeSearcher searcher, PageModel page) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (memberInfo.getStoreId() == null) {
            throw new BusinessException("没有权限！");
        }
        searcher.setStoreId(memberInfo.getStoreId());
        searcher.setGtStatus(1);
        PageResult<O2oSubscribeDto> pager = o2oSubscribeService.findBySearch(searcher, page);
        model.put("pager", pager);
        Map<String, Integer> countMap = o2oSubscribeService.countGroupByStatus(memberInfo.getStoreId());
        model.put("WaitingForRec", countMap.get("WaitingForRec"));
        model.put("WaitingForServer", countMap.get("WaitingForServer"));
        model.put("FinishForServer", countMap.get("FinishForServer"));
        return "/o2o/subscribe_list";
    }

    /**
     * 门店已开始预约准备
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/startPrepare", method = RequestMethod.POST)
    public String startPrepare(ModelMap model, Long id, Long memberId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        o2oSubscribeService.doReceive(id, memberInfo.getLoginCode());
        return "";
    }

    /**
     * 门店已完成预约准备
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/endPrepare", method = RequestMethod.POST)
    public String endPrepare(ModelMap model, Long id, Long memberId) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        this.getLoginMemberInfo();
        o2oSubscribeService.doReady(id);
        return "";
    }

    /**
     * 服务已完成
     *
     * @param model
     * @param id
     * @param feedback
     * @param payAmount
     * @param payStatus
     * @param actualNumbers
     * @param retailSn
     * @return
     */
    @RequestMapping(value = "/success", method = RequestMethod.POST)
    public String success(ModelMap model, Long id, String feedback, BigDecimal payAmount, Integer payStatus,
                          Integer actualNumbers, String retailSn) {
        SuccessResponse result = new SuccessResponse();
        model.put("result", result);
        MemberInfo memberInfo = this.getLoginMemberInfo();
        o2oSubscribeService.doComplete(id, memberInfo.getLoginCode(), feedback, payAmount, payStatus, actualNumbers,
                retailSn);
        return "";
    }

}
