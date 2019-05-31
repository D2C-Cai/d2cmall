package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.dto.LiveRoomDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.Designers;
import com.d2c.member.model.LiveRoom;
import com.d2c.member.query.LiveRoomSearcher;
import com.d2c.member.service.DesignersService;
import com.d2c.member.service.LiveRoomService;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDefGroup;
import com.d2c.order.service.CouponDefGroupService;
import com.d2c.order.service.CouponDefService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/society/liveroom")
public class LiveRoomCtrl extends BaseCtrl<LiveRoomSearcher> {

    @Autowired
    private LiveRoomService liveRoomService;
    @Autowired
    private DesignersService designersService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private CouponDefGroupService couponDefGroupService;

    @Override
    protected Response doList(LiveRoomSearcher searcher, PageModel page) {
        PageResult<LiveRoom> pager = liveRoomService.findBySearcher(searcher, page);
        List<LiveRoomDto> dtos = new ArrayList<>();
        PageResult<LiveRoomDto> liveRoomList = new PageResult<>(page);
        liveRoomList.setTotalCount(pager.getTotalCount());
        for (LiveRoom liveRoom : pager.getList()) {
            LiveRoomDto dto = new LiveRoomDto();
            BeanUtils.copyProperties(liveRoom, dto);
            Designers designers = designersService.findById(liveRoom.getDesignersId());
            if (designers != null) {
                dto.setDesignerNames(designers.getName());
            }
            if (dto.getCouponId() != null) {
                CouponDef couponDef = couponDefService.findById(dto.getCouponId());
                if (couponDef != null) {
                    dto.setCouponName(couponDef.getName());
                }
            } else if (dto.getCouponGroupId() != null) {
                CouponDefGroup couponDefGroup = couponDefGroupService.findById(dto.getCouponGroupId());
                if (couponDefGroup != null) {
                    dto.setCouponName(couponDefGroup.getName());
                }
            }
            dtos.add(dto);
        }
        liveRoomList.setList(dtos);
        return new SuccessResponse(liveRoomList);
    }

    @Override
    protected int count(LiveRoomSearcher searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(LiveRoomSearcher searcher, PageModel page) {
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
    protected Response doHelp(LiveRoomSearcher searcher, PageModel page) {
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

    @RequestMapping(value = "/bind/coupons", method = RequestMethod.POST)
    public Response bindCoupon(Long id, Long couponId, Long couponGroupId) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = 0;
        if (couponId == null && couponGroupId == null) {
            result.setStatus(-1);
            result.setMessage("请先选择要绑定的优惠券或优惠券组！");
            return result;
        }
        if (couponId != null) {
            CouponDef couponDef = couponDefService.findById(couponId);
            if (couponDef != null
                    && (couponDef.getClaimEnd() == null || couponDef.getClaimEnd().compareTo(new Date()) >= 0)) {
                success = liveRoomService.doBindCoupons(id, couponId, admin.getUsername(), null);
            }
        } else {
            CouponDefGroup couponDefGroup = couponDefGroupService.findById(couponGroupId);
            if (couponDefGroup != null && (couponDefGroup.getClaimEnd() == null
                    || couponDefGroup.getClaimEnd().compareTo(new Date()) >= 0)) {
                success = liveRoomService.doBindCoupons(id, null, admin.getUsername(), couponGroupId);
            }
        }
        if (success < 1) {
            result.setStatus(-1);
            result.setMessage("绑定优惠券失败，领用时间已超时！");
        }
        return result;
    }

    @RequestMapping(value = "/cancel/bind", method = RequestMethod.POST)
    public Response cancelCoupon(Long id) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        SuccessResponse result = new SuccessResponse();
        int success = liveRoomService.doBindCoupons(id, null, admin.getUsername(), null);
        if (success < 1) {
            result.setStatus(-1);
        }
        return result;
    }

}
