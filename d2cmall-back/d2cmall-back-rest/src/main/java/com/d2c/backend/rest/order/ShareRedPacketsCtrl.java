package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.order.model.ShareRedPackets;
import com.d2c.order.query.ShareRedPacketsSearcher;
import com.d2c.order.service.ShareRedPacketsService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/order/sharered")
public class ShareRedPacketsCtrl extends BaseCtrl<ShareRedPacketsSearcher> {

    @Autowired
    private ShareRedPacketsService shareRedPacketsService;

    @Override
    protected Response doList(ShareRedPacketsSearcher searcher, PageModel page) {
        PageResult<ShareRedPackets> pager = shareRedPacketsService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(ShareRedPacketsSearcher searcher) {
        return shareRedPacketsService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "ShareRedPackets";
    }

    @Override
    protected List<Map<String, Object>> getRow(ShareRedPacketsSearcher searcher, PageModel page) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        PageResult<ShareRedPackets> pager = shareRedPacketsService.findBySearcher(searcher, page);
        for (ShareRedPackets item : pager.getList()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("创建时间", DateUtil.second2str(item.getCreateDate()));
            map.put("活动ID", item.getSharePromotionId());
            map.put("活动名称", item.getSharePromotionName());
            map.put("组ID", item.getGroupId());
            map.put("身份", item.getInitiator() == 1 ? "团长" : "团员");
            map.put("瓜分金额", item.getMoney());
            map.put("状态", item.getStatus() == 1 ? "组团成功" : "组团中");
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return "分享红包明细";
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return new String[]{"创建时间", "活动ID", "活动名称", "组ID", "身份", "瓜分金额", "状态"};
    }

    @Override
    protected Response doHelp(ShareRedPacketsSearcher searcher, PageModel page) {
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
