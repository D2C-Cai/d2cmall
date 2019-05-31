package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.DiscountSettingGroupDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.DiscountSettingGroup;
import com.d2c.member.query.DiscountSettingGroupSearcher;
import com.d2c.member.service.DiscountSettingGroupService;
import com.d2c.util.serial.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/member/discountsettinggroup")
public class DiscountSettingGroupCtrl extends BaseCtrl<DiscountSettingGroupSearcher> {

    @Autowired
    private DiscountSettingGroupService discountSettingGroupService;

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
    protected Response doList(DiscountSettingGroupSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<DiscountSettingGroupDto> pager = discountSettingGroupService.findBySearch(searcher, page);
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        SuccessResponse result = new SuccessResponse();
        DiscountSettingGroup discountSettingGroup = discountSettingGroupService.findById(id);
        result.put("discountSettingGroup", discountSettingGroup);
        return result;
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
        SuccessResponse result = new SuccessResponse();
        DiscountSettingGroup discountSettingGroup = (DiscountSettingGroup) JsonUtil.instance().toObject(data,
                DiscountSettingGroup.class);
        BeanUt.trimString(discountSettingGroup.getName());
        Admin admin = this.getLoginedAdmin();
        discountSettingGroup = discountSettingGroupService.insert(discountSettingGroup, admin.getUsername());
        result.put("discountSettingGroup", discountSettingGroup);
        return result;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        SuccessResponse result = new SuccessResponse();
        DiscountSettingGroup discountSettingGroup = (DiscountSettingGroup) JsonUtil.instance().toObject(data,
                DiscountSettingGroup.class);
        BeanUt.trimString(discountSettingGroup.getName());
        discountSettingGroup.setId(id);
        discountSettingGroupService.updateDiscountSettingGroup(discountSettingGroup);
        return result;
    }

    @Override
    protected List<Map<String, Object>> getRow(DiscountSettingGroupSearcher searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(DiscountSettingGroupSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected Response doHelp(DiscountSettingGroupSearcher searcher, PageModel page) {
        return this.doList(searcher, page);
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 折扣组状态更改
     *
     * @throws NotLoginException
     */
    @RequestMapping(value = "/status/{status}", method = RequestMethod.POST)
    public Response status(@PathVariable Integer status, Long[] ids) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long id : ids) {
            discountSettingGroupService.updateStatusById(id, status, admin.getUsername());
        }
        return new SuccessResponse();
    }

    /**
     * 折扣组名更改
     */
    @RequestMapping(value = "/update/name", method = RequestMethod.POST)
    public Response updateName(String name, Long id) {
        discountSettingGroupService.updateNameById(id, name);
        return new SuccessResponse();
    }

    /**
     * 修改折扣组启用或停用时间
     */
    @RequestMapping(value = "/update/date/{id}", method = RequestMethod.POST)
    public Response updateDate(Date beginDate, Date endDate, @PathVariable Long id) {
        if (beginDate.getTime() >= endDate.getTime()) {
            ErrorResponse error = new ErrorResponse("停用时间必须大于启用时间");
            return error;
        }
        discountSettingGroupService.updateDate(id, beginDate, endDate);
        return new SuccessResponse();
    }

}
