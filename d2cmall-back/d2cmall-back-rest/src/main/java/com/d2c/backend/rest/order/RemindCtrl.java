package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.Remind;
import com.d2c.logger.query.RemindSearcher;
import com.d2c.logger.service.RemindService;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.MemberInfoService;
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
@RequestMapping("/rest/promotion/remind")
public class RemindCtrl extends BaseCtrl<RemindSearcher> {

    @Autowired
    private RemindService remindService;
    @Autowired
    private MemberInfoService memberInfoService;

    @Override
    protected List<Map<String, Object>> getRow(RemindSearcher searcher, PageModel page) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PageResult<Remind> pager = remindService.findBySearcher(searcher, page);
        List<Remind> list = pager.getList();
        if (list != null && list.size() > 0) {
            for (Remind remind : list) {
                if (remind == null) {
                    continue;
                }
                String nickName = "匿名";
                MemberInfo member = this.memberInfoService.findById(remind.getMemberId());
                if (member != null) {
                    nickName = member.getDisplayName();
                }
                cellsMap = new HashMap<>();
                cellsMap.put("创建时间", sdf.format(remind.getCreateDate()));
                cellsMap.put("会员昵称", nickName);
                cellsMap.put("商品名称", remind.getContent());
                cellsMap.put("类型", remind.getRemindTypeName());
                cellsMap.put("手机", remind.getMobile());
                cellsMap.put("邮箱", remind.getMail());
                cellsMap.put("邮件发送时间", remind.getSendDate() == null ? "" : sdf.format(remind.getSendDate()));
                cellsMap.put("短信发送时间", remind.getSmsSendDate() == null ? "" : sdf.format(remind.getSmsSendDate()));
                rowList.add(cellsMap);
            }
        }
        return rowList;
    }

    @Override
    protected int count(RemindSearcher searcher) {
        BeanUt.trimString(searcher);
        return remindService.countBySearcher(searcher);
    }

    @Override
    protected String getFileName() {
        return "会员提醒表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"创建时间", "会员昵称", "商品名称", "类型", "手机", "邮箱", "邮件发送时间", "短信发送时间"};
    }

    @Override
    protected Response doHelp(RemindSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Remind> remindList = remindService.findBySearcher(searcher, page);
        return new SuccessResponse(remindList);
    }

    @Override
    protected Response doList(RemindSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        if (searcher.getType() != null && searcher.getType().length() == 0) {
            searcher.setType(null);
        }
        PageResult<Remind> remindList = remindService.findBySearcher(searcher, page);
        return new SuccessResponse(remindList);
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
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
        return "Remind";
    }

    /**
     * 发送短信提醒
     *
     * @param remindIds
     * @return
     */
    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
    public Response sendSMS(Long[] remindIds) {
        SuccessResponse result = new SuccessResponse();
        int success = remindService.doSendMsg(remindIds);
        if (success > 1) {
            result.setStatus(-1);
            result.setMessage("操作失败");
        }
        return result;
    }

    /**
     * 发送邮件提醒
     *
     * @param remindIds
     * @return
     */
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public Response sendMail(Long[] remindIds) {
        SuccessResponse result = new SuccessResponse();
        int success = remindService.doSendEmail(remindIds);
        if (success > 1) {
            result.setStatus(-1);
            result.setMessage("操作失败");
        }
        return result;
    }

}
