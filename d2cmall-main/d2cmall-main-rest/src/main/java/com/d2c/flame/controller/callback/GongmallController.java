package com.d2c.flame.controller.callback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import com.d2c.order.model.PartnerCash;
import com.d2c.order.service.PartnerCashService;
import com.d2c.order.service.tx.PartnerTxService;
import com.d2c.order.third.payment.gongmall.core.GongmallConfig;
import com.d2c.order.third.payment.gongmall.sign.SignHelper;
import com.d2c.util.string.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 工猫通知回调
 */
@RestController
@RequestMapping("/gongmall")
public class GongmallController extends BaseController {

    public final static DateFormat dayFormat = new SimpleDateFormat("yyyy_MM");
    private static String newline = System.getProperty("line.separator");
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerCashService partnerCashService;
    @Autowired
    private GongmallConfig gongmallConfig;
    @Reference
    private PartnerTxService partnerTxService;

    /**
     * 电子合同回调
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/employee/notify", method = RequestMethod.POST)
    public ResponseResult employeeNotify(HttpServletRequest request, String mobile) {
        toLog(StringUtil.mapToString(getRequest().getParameterMap()));
        Partner partner = partnerService.findByLoginCode(mobile);
        if (partner != null) {
            partnerService.doContract(partner.getId(), 1);
        }
        return new ResponseResult();
    }

    private void toLog(final String params) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String fileName = "/mnt/gongmall/" + "gongmall_callback_" + dayFormat.format(new Date()) + ".log";
                File file = new File(fileName);
                FileWriter writer = null;
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    writer = new FileWriter(file, true);
                    writer.write(params + newline);
                    writer.flush();
                } catch (IOException e) {
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
        });
        executor.shutdown();
    }

    /**
     * 提现结果回调
     *
     * @param request
     * @param response
     * @return
     * @throws FileUploadException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/withdraw/notify", method = RequestMethod.POST)
    public ResponseResult withdrawNotify(HttpServletRequest request, HttpServletResponse response)
            throws FileUploadException, UnsupportedEncodingException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        Map<String, String> param = new HashMap<String, String>();
        for (FileItem item : items) {
            if (item.isFormField()) {
                param.put(item.getFieldName(), item.getString("utf-8"));
            }
        }
        String signStr = param.get("sign");
        param.remove("sign");
        String status = param.get("status");
        String requestId = param.get("requestId");
        String amount = param.get("amount");
        String sign = SignHelper.getSign(param, gongmallConfig.getAppSecret());
        if (signStr.equals(sign)) {
            if (status.equals("1")) {
                PartnerCash cash = partnerCashService.findBySn(requestId);
                if (cash != null && cash.getStatus() != 8
                        && cash.getApplyTaxAmount().intValue() == new BigDecimal(amount).intValue()) {
                    partnerTxService.doPaymentCash(cash.getId(), cash.getSn(), "系统", new Date(), "工猫回调");
                }
            }
        }
        return new ResponseResult();
    }

}
