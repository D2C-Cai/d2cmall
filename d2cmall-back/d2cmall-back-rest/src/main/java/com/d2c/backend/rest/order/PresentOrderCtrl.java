package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.PresentOrder;
import com.d2c.order.query.PresentOrderSearcher;
import com.d2c.order.service.PresentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/presentorder")
public class PresentOrderCtrl extends BaseCtrl<PresentOrderSearcher> {

    @Autowired
    private PresentOrderService presentOrderService;

    @Override
    protected Response doList(PresentOrderSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<PresentOrder> pager = presentOrderService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(PresentOrderSearcher searcher) {
        return presentOrderService.countBySeasrcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "PresentOrder";
    }

    @Override
    protected List<Map<String, Object>> getRow(PresentOrderSearcher searcher, PageModel page) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PageResult<PresentOrder> pager = presentOrderService.findBySearcher(searcher, page);
        for (PresentOrder presentOrder : pager.getList()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("订单编号", presentOrder.getOrderSn());
            map.put("买家账号", presentOrder.getLoginCode());
            map.put("买家会员Id", presentOrder.getBuyMemberInfoId());
            map.put("收礼人会员Id", presentOrder.getReceiveMemberInfoId());
            map.put("收礼人账号", presentOrder.getReceiveLoginCode());
            map.put("订单状态", presentOrder.getOrderStatusName());
            map.put("礼物名称", presentOrder.getProductName());
            map.put("礼物单价", presentOrder.getProductPrice());
            map.put("支付方式", PaymentTypeEnum.getByCode(presentOrder.getPaymentType()));
            map.put("数量", presentOrder.getQuantity());
            map.put("总金额", presentOrder.getTotalAmount());
            map.put("支付流水号", presentOrder.getPaySn());
            map.put("支付金额", presentOrder.getTotalAmount());
            map.put("创建日期", presentOrder.getCreateDate() != null ? sdf.format(presentOrder.getCreateDate()) : "");
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "直播打赏交易表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"订单编号", "买家账号", "买家会员Id", "收礼人会员Id", "收礼人账号", "订单状态", "礼物名称", "礼物单价", "支付方式", "数量", "总金额",
                "支付流水号", "支付金额", "创建日期"};
    }

    @Override
    protected Response doHelp(PresentOrderSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
