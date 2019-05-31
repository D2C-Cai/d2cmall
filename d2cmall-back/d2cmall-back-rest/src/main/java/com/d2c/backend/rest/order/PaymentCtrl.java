package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.order.dto.AbnormalPaymentDto;
import com.d2c.order.model.Payment;
import com.d2c.order.query.AbnormalPaymentSearcher;
import com.d2c.order.service.PaymentService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 异常支付订单
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/rest/order/abnormalpayment")
public class PaymentCtrl extends BaseCtrl<AbnormalPaymentSearcher> {

    @Autowired
    private PaymentService paymentService;

    @Override
    protected Response doList(AbnormalPaymentSearcher searcher, PageModel page) {
        PageResult<Payment> pager = paymentService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(AbnormalPaymentSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(AbnormalPaymentSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(AbnormalPaymentSearcher searcher, PageModel page) {
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

    @RequestMapping(value = "/abnormalpayment", method = RequestMethod.GET)
    public Response findAbnormalPayment(AbnormalPaymentSearcher searcher, PageModel page) {
        searcher.setOrderNum(0);
        searcher.setPayAmount(new BigDecimal(20));
        searcher.setBeginDate(DateUtil.getIntervalDay(new Date(), -10));
        PageResult<AbnormalPaymentDto> pager = paymentService.findAbnormalPayment(searcher, page);
        return new SuccessResponse(pager);
    }

}
