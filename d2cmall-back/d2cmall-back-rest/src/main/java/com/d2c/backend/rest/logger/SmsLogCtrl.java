package com.d2c.backend.rest.logger;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.common.base.utils.security.D2CSign;
import com.d2c.logger.model.SmsLog;
import com.d2c.logger.model.SmsLog.SmsLogType;
import com.d2c.logger.query.SmsLogSearcher;
import com.d2c.logger.service.SmsLogService;
import com.d2c.member.service.LoginService;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayBase;
import com.d2c.order.third.payment.alipay.core.pcwap.AlipayCore;
import com.d2c.order.third.payment.alipay.sgin.BASE64;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/rest/sys/smslog")
public class SmsLogCtrl extends BaseCtrl<SmsLogSearcher> {

    @Autowired
    private SmsLogService smsLogService;
    @Autowired
    private LoginService loginService;

    @Override
    protected Response doList(SmsLogSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<SmsLog> pager = new PageResult<>();
        try {
            smsLogService.findBySearcher(searcher, page);
            Future<PageResult<SmsLog>> future = RpcContext.getContext().getFuture();
            pager = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(SmsLogSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(SmsLogSearcher searcher, PageModel page) {
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
    protected Response doHelp(SmsLogSearcher searcher, PageModel page) {
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

    /**
     * 发送验证码(短信加密)
     *
     * @param mobile
     * @param type
     * @param appParams
     * @param nationCode
     * @param source
     * @param terminal
     * @return
     * @throws BusinessException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/send/encrypt", method = RequestMethod.POST)
    public Response sendByEncrypt(@RequestParam(required = true) String mobile,
                                  @RequestParam(required = true) String type, @RequestParam(required = true) String appParams,
                                  String nationCode, String source, String terminal) throws BusinessException, UnsupportedEncodingException {
        SuccessResponse result = new SuccessResponse();
        if (!checkEncrypt(mobile, appParams)) {
            result.setStatus(-1);
            result.setMessage("密钥不正确！");
            return result;
        }
        return this.send(nationCode, mobile, type, source, "sms");
    }

    private SuccessResponse send(String nationCode, String mobile, String type, String source, String method) {
        SuccessResponse result = new SuccessResponse();
        SmsLogType logType = SmsLogType.valueOf(type.toUpperCase());
        if (logType.equals("REGISTER")) {
            logType = SmsLogType.MEMBERMOBILE;
        }
        loginService.doSendValidateMsg(nationCode, mobile, logType, source, getLoginIp(), method);
        result.setMsg("发送成功！");
        return result;
    }

    /**
     * 检查验证码
     *
     * @param code   验证码
     * @param mobile 手机号
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Response check(@RequestParam(required = true) String code, @RequestParam(required = true) String mobile) {
        SuccessResponse result = new SuccessResponse();
        if (StringUtil.hasBlack(new Object[]{mobile, code})) {
            result.setStatus(-1);
            result.setMsg("参数异常");
            return result;
        }
        String message = loginService.doVerify(mobile, code);
        if (!message.equalsIgnoreCase("OK")) {
            result.setStatus(-1);
            result.setMsg(message);
            return result;
        }
        return result;
    }

    private boolean checkEncrypt(String mobile, String appParams) throws UnsupportedEncodingException {
        Map<String, String> mobileString = new HashMap<>();
        mobileString.put("mobile", mobile);
        Map<String, String> sPara = AlipayCore.parasFilter(mobileString);
        String mySign = AlipayBase.BuildMysign(sPara, D2CSign.SECRET_KEY);
        sPara.put("sign", mySign);
        String params = BASE64.encode(AlipayCore.createLinkString(sPara).getBytes("UTF-8"));
        if (params.equalsIgnoreCase(appParams)) {
            return true;
        }
        return false;
    }

}
