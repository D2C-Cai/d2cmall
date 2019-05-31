package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.PartnerCounselor;
import com.d2c.member.query.PartnerCounselorSearcher;
import com.d2c.member.service.PartnerCounselorService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/partnercounselor")
public class PartnerCounselorCtrl extends BaseCtrl<PartnerCounselorSearcher> {

    @Autowired
    private PartnerCounselorService partnerCounselorService;

    @Override
    protected Response doList(PartnerCounselorSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<PartnerCounselor> pager = partnerCounselorService.findBySearcher(page, searcher);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(PartnerCounselorSearcher searcher) {
        return partnerCounselorService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(PartnerCounselorSearcher searcher, PageModel page) {
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
    protected Response doHelp(PartnerCounselorSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<PartnerCounselor> pager = partnerCounselorService.findBySearcher(page, searcher);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        PartnerCounselor partnerCounselor = partnerCounselorService.findById(id);
        SuccessResponse result = new SuccessResponse();
        result.put("partnerCounselor", partnerCounselor);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        PartnerCounselor partnerCounselor = JsonUtil.instance().toObject(data, PartnerCounselor.class);
        SuccessResponse result = new SuccessResponse();
        partnerCounselorService.update(partnerCounselor);
        return result;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        PartnerCounselor partnerCounselor = JsonUtil.instance().toObject(data, PartnerCounselor.class);
        partnerCounselor = partnerCounselorService.insert(partnerCounselor);
        result.put("partnerCounselor", partnerCounselor);
        return result;
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
     * 上下架
     *
     * @return
     */
    @RequestMapping(value = "/mark", method = RequestMethod.POST)
    public Response doMark(Long id, Integer mark) {
        SuccessResponse result = new SuccessResponse();
        int success = partnerCounselorService.doMark(id, mark);
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("操作失败");
        }
        return result;
    }

}
