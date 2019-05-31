package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.member.model.MemberIntegration;
import com.d2c.member.query.MemberIntegrationSearcher;
import com.d2c.member.service.MemberIntegrationService;
import com.d2c.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/memberintegration")
public class MemberIntegrationCtrl extends BaseCtrl<MemberIntegrationSearcher> {

    @Autowired
    private MemberIntegrationService memberIntegrationService;

    @Override
    protected Response doList(MemberIntegrationSearcher searcher, PageModel page) {
        PageResult<MemberIntegration> pager = memberIntegrationService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(MemberIntegrationSearcher searcher) {
        return memberIntegrationService.countBySearch(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "memberIntegration";
    }

    @Override
    protected List<Map<String, Object>> getRow(MemberIntegrationSearcher searcher, PageModel page) {
        PageResult<MemberIntegration> pager = memberIntegrationService.findBySearch(searcher, page);
        List<Map<String, Object>> list = new ArrayList<>();
        for (MemberIntegration integration : pager.getList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("获取时间", DateUtil.second2str(integration.getCreateDate()));
            map.put("会员id", integration.getMemberId());
            map.put("会员账号", integration.getLoginCode());
            map.put("业务名称", integration.getTransactionInfo());
            if (integration.getDirection() == -1) {
                map.put("方向", "扣除");
            } else {
                map.put("方向", "增加");
            }
            map.put("业务类型", integration.getTypeName());
            map.put("所获积分", integration.getPoint());
            list.add(map);
        }
        return list;
    }

    @Override
    protected String getFileName() {
        return "会员积分明细表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"获取时间", "会员id", "会员账号", "业务名称", "业务类型", "方向", "所获积分"};
    }

    @Override
    protected Response doHelp(MemberIntegrationSearcher searcher, PageModel page) {
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
