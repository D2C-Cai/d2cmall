package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.order.dto.CollageOrderDto;
import com.d2c.order.enums.PaymentTypeEnum;
import com.d2c.order.model.CollageOrder;
import com.d2c.order.model.CollageOrder.CollageOrderStatus;
import com.d2c.order.query.CollageOrderSearcher;
import com.d2c.order.service.CollageOrderService;
import com.d2c.order.service.tx.CollageTxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拼团订单
 *
 * @author baicai
 */
@RestController
@RequestMapping(value = "/rest/order/collageorder")
public class CollageOrderCtrl extends BaseCtrl<CollageOrderSearcher> {

    @Autowired
    private CollageOrderService collageOrderService;
    @Reference
    private CollageTxService collageTxService;

    @Override
    protected Response doList(CollageOrderSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<CollageOrderDto> pager = collageOrderService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(CollageOrderSearcher searcher) {
        return collageOrderService.countBySearch(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "CollageOrder";
    }

    @Override
    protected List<Map<String, Object>> getRow(CollageOrderSearcher searcher, PageModel page) {
        PageResult<CollageOrderDto> pager = collageOrderService.findBySearch(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> cellsMap;
        for (CollageOrderDto dto : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("拼团商品标题", dto.getProductName());
            cellsMap.put("会员ID", dto.getMemberId());
            cellsMap.put("登录账号", dto.getLoginCode());
            cellsMap.put("拼团单编号", dto.getSn());
            cellsMap.put("支付方式",
                    dto.getPaymentType() != null ? PaymentTypeEnum.getByCode(dto.getPaymentType()).getDisplay() : "");
            cellsMap.put("支付流水号", dto.getPaySn());
            cellsMap.put("支付金额", dto.getPaidAmount());
            cellsMap.put("拼团状态", CollageOrderStatus.getStatus(dto.getStatus()).getValue());
            cellsMap.put("退款时间", dto.getRefundTime() != null ? sdf.format(dto.getRefundTime()) : "");
            cellsMap.put("退款人", dto.getLastModifyMan() != null ? dto.getLastModifyMan() : "");
            cellsMap.put("退款方式", dto.getRefundPaymentType() != null
                    ? PaymentTypeEnum.getByCode(dto.getRefundPaymentType()).getDisplay() : "");
            cellsMap.put("退款交易号", dto.getRefundPaySn() != null ? dto.getRefundPaySn() : "");
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "拼团订单导出";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"拼团商品标题", "会员ID", "登录账号", "拼团单编号", "支付方式", "支付流水号", "支付金额", "拼团状态", "退款时间", "退款人", "退款方式",
                "退款交易号"};
    }

    @Override
    protected Response doHelp(CollageOrderSearcher searcher, PageModel page) {
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

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public Response doRefundSuccess(Long id, String refundMemo, Integer refundPaymentType, String refundPaySn) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = collageOrderService.doRefundSuccess(id, admin.getUsername(), refundMemo, refundPaymentType,
                refundPaySn);
        if (success < 1) {
            result.setMessage("操作不成功");
            result.setStatus(-1);
        }
        return result;
    }

    /**
     * 导入拼团退款表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/refund/import", method = RequestMethod.POST)
    public Response importCollageRefund(HttpServletRequest request) {
        this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String sn = map.get("订单编号").toString();
                CollageOrder collageOrder = collageOrderService.findBySn(sn);
                if (collageOrder == null) {
                    errorMsg.append("第" + row + "行，单号：" + sn + "，拼团订单不存在<br/>");
                    return false;
                }
                String refundAmount = map.get("退款金额").toString();
                if (collageOrder.getPaidAmount().compareTo(new BigDecimal(refundAmount)) != 0) {
                    errorMsg.append("第" + row + "行，支付金额和退款金额不一致<br/>");
                    return false;
                }
                String refundMan = map.get("退款人").toString();
                String refundSn = map.get("退款交易号").toString();
                collageOrderService.doRefundSuccess(collageOrder.getId(), refundMan, "", collageOrder.getPaymentType(),
                        refundSn);
                return true;
            }
        });
    }

    /**
     * 退款至钱包
     *
     * @param collageOrderId
     * @return
     */
    @RequestMapping(value = "/wallet/refund", method = RequestMethod.POST)
    public Response backToWallet(Long id) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = collageTxService.doBackToWallet(id, admin.getUsername());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("退款失败");
        }
        return result;
    }

}
