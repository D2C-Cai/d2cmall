package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.enums.PointRuleTypeEnum;
import com.d2c.member.model.MemberDetail;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.model.MemberIntegration.StatusEnum;
import com.d2c.member.query.MemberIntegrationSearcher;
import com.d2c.member.service.MemberDetailService;
import com.d2c.member.service.MemberIntegrationService;
import com.d2c.order.service.tx.PointExchangeTxService;
import com.d2c.product.model.PointProduct;
import com.d2c.product.service.PointProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 会员积分
 *
 * @author wwn
 * @version 3.0
 */
@RestController
@RequestMapping("/v3/api/pointexchange")
public class PointExchangeController extends BaseController {

    @Reference
    private PointExchangeTxService pointExchangeTxService;
    @Autowired
    private PointProductService pointProductService;
    @Autowired
    private MemberDetailService memberDetailService;
    @Autowired
    private MemberIntegrationService memberIntegrationService;

    /**
     * 积分兑换
     *
     * @param pointProductId
     * @param quantity
     * @return
     */
    @RequestMapping(value = "/exchange/{pointProductId}", method = RequestMethod.POST)
    public ResponseResult doExchange(@PathVariable Long pointProductId, Integer quantity) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        if (quantity == null) {
            quantity = 1;
        }
        if (memberInfo.getDistributorId() != null) {
            throw new BusinessException("经销商不能兑换商品！");
        }
        PointProduct product = pointProductService.findById(pointProductId);
        if (product.isOver()) {
            throw new BusinessException("该商品兑换活动已结束！");
        }
        int total = memberIntegrationService.countByExchange(memberInfo.getId(), pointProductId);
        if (product.getLimitCount() < total + quantity) {
            throw new BusinessException("您已超过该商品兑换上限了，试试其他商品吧！");
        }
        MemberDetail memberDetail = memberDetailService.findByMemberInfoId(memberInfo.getId());
        if (memberDetail.getIntegration() < product.getPoint() * quantity) {
            throw new BusinessException("对不起，您的积分不足！");
        }
        Map<String, JSONObject> map = pointExchangeTxService.doExchange(product, memberInfo, quantity);
        for (String key : map.keySet()) {
            result.put(key, map.get(key));
        }
        result.put("pointProduct", product.toJson());
        return result;
    }

    /**
     * 兑换记录
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public ResponseResult recordList(PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        MemberIntegrationSearcher searcher = new MemberIntegrationSearcher();
        searcher.setMemberId(memberInfo.getId());
        searcher.setType(PointRuleTypeEnum.EXCHANGE.name());
        searcher.setStatus(StatusEnum.SUCCESS.getCode());
        PageResult<MemberIntegration> pager = memberIntegrationService.findBySearch(searcher, page);
        JSONArray array = new JSONArray();
        pager.getList().forEach(item -> array.add(item.toJson()));
        result.putPage("records", pager, array);
        return result;
    }

}
