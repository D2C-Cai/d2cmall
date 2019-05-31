package com.d2c.backend.rest.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.logger.model.CompensationLog;
import com.d2c.logger.service.CompensationLogService;
import com.d2c.member.model.Admin;
import com.d2c.order.model.CustomerCompensation;
import com.d2c.order.model.CustomerCompensation.CompensationType;
import com.d2c.order.model.OrderItem;
import com.d2c.order.query.CustomerCompensationSearcher;
import com.d2c.order.service.CustomerCompensationService;
import com.d2c.order.service.OrderItemService;
import com.d2c.order.service.tx.CompensationTxService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户端赔偿
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/rest/order/customerCompensation")
public class CustomerCompensationCtrl extends BaseCtrl<CustomerCompensationSearcher> {

    @Autowired
    private CustomerCompensationService customerCompensationService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CompensationLogService compensationLogService;
    @Reference
    private CompensationTxService compensationTxService;

    @Override
    protected Response doList(CustomerCompensationSearcher searcher, PageModel page) {
        PageResult<CustomerCompensation> pager = customerCompensationService.findBySearcher(searcher, page);
        BigDecimal totalAmount = customerCompensationService.sumBySearcher(searcher);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("totalAmount", totalAmount);
        return result;
    }

    @Override
    protected int count(CustomerCompensationSearcher searcher) {
        return customerCompensationService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "customerCompensation";
    }

    @Override
    protected List<Map<String, Object>> getRow(CustomerCompensationSearcher searcher, PageModel page) {
        List<Map<String, Object>> list = new ArrayList<>();
        PageResult<CustomerCompensation> pager = customerCompensationService.findBySearcher(searcher, page);
        for (CustomerCompensation compensation : pager.getList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("订单支付时间", compensation.getTransactionTime() == null ? ""
                    : DateUtil.second2str(compensation.getTransactionTime()));
            map.put("赔偿时间",
                    compensation.getCreateDate() == null ? "" : DateUtil.second2str(compensation.getCreateDate()));
            map.put("D2C账号", compensation.getLoginCode());
            map.put("订单编号", compensation.getTransactionSn());
            map.put("SKU", compensation.getProductSku());
            map.put("预计发货时间",
                    compensation.getEstimateDate() == null ? "" : DateUtil.second2str(compensation.getEstimateDate()));
            map.put("超期时间", compensation.getExpiredDay());
            map.put("交易金额", compensation.getTradeAmount());
            map.put("赔偿金额", compensation.getCompensationAmount());
            map.put("赔偿状态", CustomerCompensation.CompensationStatus.getStatusName(compensation.getStatus()));
            map.put("类型", CustomerCompensation.CompensationType.getTypeName(compensation.getType()));
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "用户赔偿表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"订单支付时间", "赔偿时间", "D2C账号", "订单编号", "SKU", "预计发货时间", "超期时间", "交易金额", "赔偿金额", "赔偿状态",
                "类型"};
    }

    @Override
    protected Response doHelp(CustomerCompensationSearcher searcher, PageModel page) {
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

    /**
     * 审核不通过，关闭赔偿单
     *
     * @param id
     * @param remark
     * @return
     */
    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public Response doClose(Long[] ids, String[] remark) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int successCount = 0;
        for (int i = 0; i < ids.length; i++) {
            try {
                int success = customerCompensationService.doClose(ids[i], admin.getUsername(), remark[i]);
                if (success > 0) {
                    successCount++;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        result.setMessage("操作成功" + successCount + "条，失败" + (ids.length - successCount) + "条");
        return result;
    }

    /**
     * 支付赔偿单
     *
     * @param id
     * @param remark
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public Response doPay(Long[] ids, String[] remark) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int successCount = 0;
        for (int i = 0; i < ids.length; i++) {
            try {
                int success = compensationTxService.doCompensationPay(ids[i], admin.getUsername(), remark[i]);
                if (success > 0) {
                    successCount++;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        result.setMessage("操作成功" + successCount + "条，失败" + (ids.length - successCount) + "条");
        return result;
    }

    /**
     * 手动创建赔偿单
     *
     * @param orderItemId
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Response createCompensation(Long orderItemId) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        OrderItem orderItem = orderItemService.findById(orderItemId);
        int success = customerCompensationService.doOrderItemCompensation(orderItem, admin.getUsername(),
                CompensationType.MANUAL.getCode());
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    /**
     * 修改赔偿金额
     *
     * @param id
     * @param compensationAmount
     * @param remark
     * @return
     */
    @RequestMapping(value = "/update/amount", method = RequestMethod.POST)
    public Response updateAmount(Long id, BigDecimal compensationAmount, String remark) {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        int success = customerCompensationService.updateCompensationAmount(id, compensationAmount, admin.getUsername(),
                remark);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作不成功");
        }
        return result;
    }

    /**
     * 操作日志
     *
     * @param searcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Response findCompensationList(@PathVariable("id") Long id, PageModel page) {
        PageResult<CompensationLog> pager = compensationLogService.findCusCompensation(id, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

}
