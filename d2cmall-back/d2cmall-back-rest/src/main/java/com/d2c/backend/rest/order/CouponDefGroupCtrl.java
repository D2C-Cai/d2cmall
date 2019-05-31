package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.query.CouponDefGroupSearcher;
import com.d2c.order.query.CouponDefSearcher;
import com.d2c.order.service.CouponDefGroupQueryService;
import com.d2c.order.service.CouponDefGroupService;
import com.d2c.order.service.CouponDefQueryService;
import com.d2c.util.serial.JsonUtil;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/coupondefgroup")
public class CouponDefGroupCtrl extends BaseCtrl<CouponDefGroupSearcher> {

    @Autowired
    private CouponDefGroupService couponDefGroupService;
    @Autowired
    private CouponDefGroupQueryService couponDefGroupQueryService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;

    @Override
    protected List<Map<String, Object>> getRow(CouponDefGroupSearcher searcher, PageModel page) {
        return null;
    }

    @Override
    protected int count(CouponDefGroupSearcher searcher) {
        BeanUt.trimString(searcher);
        int count = couponDefGroupQueryService.countBySearch(searcher);
        return count;
    }

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        return null;
    }

    @Override
    protected Response doHelp(CouponDefGroupSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        List<HelpDTO> list = couponDefGroupQueryService.findHelpDtosBySearch(searcher, page);
        return new SuccessResponse(list);
    }

    @Override
    protected Response doList(CouponDefGroupSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<CouponDefGroup> pager = couponDefGroupQueryService.findBySearch(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        if (id == null) {
            return new ErrorResponse("请输入正确的id");
        }
        CouponDefGroup cdg = couponDefGroupQueryService.findById(id);
        if (cdg == null) {
            return new ErrorResponse("没有查找出对应的定义优惠券组");
        }
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.addRecord(cdg);
        return successResponse;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        if (ids != null && ids.length > 0) {
            for (Long cdgId : ids) {
                if (cdgId == null) {
                    continue;
                }
                couponDefGroupService.delete(cdgId);
            }
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        couponDefGroupService.delete(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        CouponDefGroup couponDefGroup = JsonUtil.instance().toObject(data, CouponDefGroup.class);
        BeanUt.trimString(couponDefGroup);
        Admin admin = this.getLoginedAdmin();
        couponDefGroup.setCreator(admin.getUsername());
        try {
            couponDefGroup = couponDefGroupService.insert(couponDefGroup);
            SuccessResponse response = new SuccessResponse();
            response.put("coupondefgroup", couponDefGroup);
            return response;
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        CouponDefGroup couponDefGroup = JsonUtil.instance().toObject(data, CouponDefGroup.class);
        BeanUt.trimString(couponDefGroup);
        this.getLoginedAdmin();
        if (couponDefGroup.getId() == null) {
            return new ErrorResponse("定义组没有确定的id，更新不成功");
        }
        try {
            couponDefGroupService.update(couponDefGroup);
            return new SuccessResponse();
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @Override
    protected String getExportFileType() {
        return null;
    }

    /**
     * 更新操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response edit(CouponDefGroup couponDefGroup) {
        BeanUt.trimString(couponDefGroup);
        try {
            couponDefGroupService.update(couponDefGroup);
            return new SuccessResponse();
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    /**
     * 启动停用
     */
    @RequestMapping(value = "/mark/{mark}", method = RequestMethod.POST)
    public Response doUpOrDown(@PathVariable int mark, Long[] ids) {
        if (mark == 1) {
            for (Long id : ids) {
                couponDefGroupService.doUp(id);
            }
        }
        if (mark == 0) {
            for (Long id : ids) {
                couponDefGroupService.doDown(id);
            }
        }
        return new SuccessResponse();
    }

    @RequestMapping(value = "/toBindGroup", method = RequestMethod.POST)
    public Response toBindGroup(Long groupId, PageModel page, CouponDefSearcher searcher, Integer fix) {
        CouponDefGroup group = this.couponDefGroupQueryService.findById(groupId);
        searcher.setGroupId(null);
        PageResult<CouponDef> pager = couponDefQueryService.findBySearcher(page, searcher);
        SuccessResponse result = new SuccessResponse(pager);
        if (fix == null) {
            fix = 0;
        }
        if (fix == 1) {
            result.put("selected", group.getFixDefIds());
        } else {
            result.put("selected", group.getRandomDefIds());
        }
        return result;
    }

    @RequestMapping(value = "/bind/{groupId}/{defId}", method = RequestMethod.POST)
    public Response doBindGroup(@PathVariable("groupId") Long groupId, @PathVariable("defId") Long defId, Integer fix) {
        SuccessResponse result = new SuccessResponse();
        if (fix == null) {
            result.setStatus(-1);
            result.setMessage("类型不能为空");
            return result;
        }
        if (groupId == null) {
            result.setMessage("优惠券组未指定");
            result.setStatus(-1);
            return result;
        }
        if (defId == null) {
            result.setMessage("优惠券定义未指定");
            result.setStatus(-1);
            return result;
        }
        CouponDefGroup group = this.couponDefGroupQueryService.findById(groupId);
        List<Long> fiexIds = new ArrayList<>();
        List<Long> randomIds = new ArrayList<>();
        if (!StringUtils.isEmpty(group.getFixDefIds())) {
            fiexIds = StringUtil.strToLongList(group.getFixDefIds());
        }
        if (!StringUtils.isEmpty(group.getRandomDefIds())) {
            randomIds = StringUtil.strToLongList(group.getRandomDefIds());
        }
        if (fiexIds.contains(defId)) {
            result.setMessage("优惠券在固定组中已经指定，指定不成功，请到固定组中解除绑定后，再继续操作！！！");
            result.setStatus(-1);
            return result;
        }
        if (randomIds.contains(defId)) {
            result.setMessage("优惠券在随机组中已经指定，指定不成功，请到随机组中解除绑定后，再继续操作！！！");
            result.setStatus(-1);
            return result;
        }
        if (fix == 1) {// 固定
            fiexIds.add(defId);
            Collections.sort(fiexIds);
            couponDefGroupService.updateFixDefIds(groupId, StringUtil.longListToStr(fiexIds));
        } else {
            randomIds.add(defId);
            Collections.sort(randomIds);
            couponDefGroupService.updateRandomDefIds(groupId, StringUtil.longListToStr(randomIds));
        }
        return result;
    }

    @RequestMapping(value = "/unbind/{groupId}/{defId}", method = RequestMethod.POST)
    public Response unBindGroup(@PathVariable("groupId") Long groupId, @PathVariable("defId") Long defId, Integer fix) {
        SuccessResponse result = new SuccessResponse();
        if (fix == null) {
            result.setStatus(-1);
            result.setMessage("类型不能为空");
            return result;
        }
        if (groupId == null) {
            result.setMessage("优惠券组未指定");
            result.setStatus(-1);
            return result;
        }
        if (defId == null) {
            result.setMessage("优惠券定义未指定");
            result.setStatus(-1);
            return result;
        }
        CouponDefGroup group = this.couponDefGroupQueryService.findById(groupId);
        String newIds = null;
        if (fix == 0) {// 固定
            newIds = group.getRandomDefIds();
        } else {
            newIds = group.getFixDefIds();
        }
        newIds = newIds.replaceAll("," + defId, "");
        newIds = newIds.replaceAll(defId + ",", "");
        if (newIds.equals(String.valueOf(defId))) {
            // 最后一个的情况下
            newIds = "";
        }
        if (fix == 0) {// 固定
            couponDefGroupService.updateRandomDefIds(groupId, newIds);
        } else {
            couponDefGroupService.updateFixDefIds(groupId, newIds);
        }
        return result;
    }

    @RequestMapping(value = "/{id}/{type}/delete/{defId}", method = RequestMethod.POST)
    public Response delete(@PathVariable Long id, @PathVariable Integer type, @PathVariable Long defId) {
        CouponDefGroup couponDefGroup = couponDefGroupQueryService.findById(id);
        if (type == 0) {// 固定
            couponDefGroupService.updateFixDefIds(id, this.deleteId(defId, couponDefGroup.getFixDefIds()));
        } else {// 随机
            couponDefGroupService.updateRandomDefIds(id, this.deleteId(defId, couponDefGroup.getRandomDefIds()));
        }
        return new SuccessResponse();
    }

    private String deleteId(Long deleteId, String idsStr) {
        if (deleteId == null) {
            return idsStr;
        }
        List<Long> list = StringUtil.strToLongList(idsStr);
        List<Long> result = new ArrayList<>();
        for (Long id : list) {
            if (!deleteId.equals(id)) {
                result.add(id);
            }
        }
        return StringUtil.longListToStr(result);
    }

}
