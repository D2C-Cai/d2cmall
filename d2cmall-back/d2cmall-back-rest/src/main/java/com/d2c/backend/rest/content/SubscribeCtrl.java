package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.content.model.Subscribe;
import com.d2c.content.query.SubscribeSearcher;
import com.d2c.content.service.SubscribeService;
import com.d2c.logger.service.EmailService;
import com.d2c.logger.service.SmsLogService;
import com.d2c.util.string.LoginUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/subscribe")
public class SubscribeCtrl extends BaseCtrl<SubscribeSearcher> {

    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private SmsLogService smsLogService;
    @Autowired
    private EmailService emailService;

    @Override
    protected List<Map<String, Object>> getRow(SubscribeSearcher searcher, PageModel page) {
        PageResult<Subscribe> pager = subscribeService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Subscribe subscribe : pager.getList()) {
            cellsMap = new HashMap<String, Object>();
            cellsMap.put("订阅来源", subscribe.getSubscribe());
            cellsMap.put("用户名", subscribe.getMemberName());
            cellsMap.put("用户账号", subscribe.getMemberCode());
            cellsMap.put("类型", subscribe.getSubType());
            cellsMap.put("订阅时间", sdf.format(subscribe.getCreateDate()));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(SubscribeSearcher searcher) {
        return subscribeService.countBySearcher(searcher);
    }

    @Override
    protected String getFileName() {
        return "新闻邮箱订阅用户表";
    }

    @Override
    protected String[] getExportTitles() {
        String[] titleNames = {"邮箱", "用户名", "用户账号", "订阅时间"};
        return titleNames;
    }

    @Override
    protected Response doHelp(SubscribeSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doList(SubscribeSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Subscribe> pager = subscribeService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        return "Subscribe";
    }

    /**
     * 发送短信
     *
     * @param idStr
     * @param content
     * @return
     */
    @RequestMapping(value = "/sms", method = {RequestMethod.POST})
    public Response semdSms(String idStr, String content) {
        SuccessResponse result = new SuccessResponse();
        result.setStatus(-1);
        if (StringUtils.isBlank(content)) {
            result.setMessage("发送内容不能为空");
            return result;
        }
        List<Long> ids = StringUtil.strToLongList(idStr);
        Subscribe subscribe = null;
        for (Long id : ids) {
            subscribe = subscribeService.findById(id);
            if (subscribe != null && LoginUtil.checkMobile(subscribe.getSubscribe())) {
                smsLogService.doSendSms("86", subscribe.getSubscribe(), content);
            }
        }
        result.setStatus(1);
        result.setMessage("发送成功！");
        return result;
    }

}
